package com.naukri.tests.listeners;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogType;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.naukri.framework.base.BaseTest;
import com.naukri.framework.base.DriverFactory;
import com.naukri.framework.config.ConfigKeys;
import com.naukri.framework.config.ConfigManager;
import com.naukri.framework.reporting.AllureHelper;
import com.naukri.framework.reporting.Log;
import com.naukri.framework.utils.DateTimeUtil;
import com.naukri.framework.utils.FileUtil;

public class TestListener implements ITestListener {
    @Override
    public void onTestSuccess(ITestResult result) {
        ConfigManager config = config(result);
        Log.info("Test passed: " + testName(result));
        if (config.getBoolean(ConfigKeys.SCREENSHOTS_ON_PASS, false)) {
            captureScreenshot(result, "passed");
        }
    }

    @Override
    public void onTestFailure(ITestResult result) {
        ConfigManager config = config(result);
        Log.error("Test failed: " + testName(result), result.getThrowable());

        if (config.getBoolean(ConfigKeys.SCREENSHOTS_ON_FAIL, true)) {
            captureScreenshot(result, "failed");
        }

        WebDriver driver = DriverFactory.getNullable();
        if (driver == null) {
            return;
        }

        if (config.getBoolean(ConfigKeys.BROWSER_LOGS_ON_FAIL, true)) {
            attachBrowserLogs(driver);
        }

        if (config.getBoolean(ConfigKeys.PAGE_SOURCE_ON_FAIL, false)) {
            AllureHelper.attachText("Page source", driver.getPageSource());
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        ConfigManager config = config(result);
        Log.warn("Test skipped: " + testName(result));
        if (config.getBoolean(ConfigKeys.SCREENSHOTS_ON_SKIP, true)) {
            captureScreenshot(result, "skipped");
        }
    }

    private void captureScreenshot(ITestResult result, String statusFolder) {
        WebDriver driver = DriverFactory.getNullable();
        if (!(driver instanceof TakesScreenshot)) {
            return;
        }

        byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
        Path path = Paths.get("screenshots", statusFolder,
                safeName(result.getTestClass().getName() + "_" + result.getMethod().getMethodName())
                        + "_" + DateTimeUtil.fileTimestamp() + ".png");

        FileUtil.writeBytes(path, screenshot);
        AllureHelper.attachScreenshot(statusFolder + " screenshot", screenshot);
        AllureHelper.attachFilePath("Screenshot path", path);
    }

    private void attachBrowserLogs(WebDriver driver) {
        try {
            LogEntries entries = driver.manage().logs().get(LogType.BROWSER);
            AllureHelper.attachText("Browser console logs", entries.getAll().toString());
        } catch (RuntimeException ignored) {
            AllureHelper.attachText("Browser console logs", "Browser log collection is not supported by this driver.");
        }
    }

    private ConfigManager config(ITestResult result) {
        Object instance = result.getInstance();
        if (instance instanceof BaseTest) {
            return ((BaseTest) instance).config();
        }
        return ConfigManager.getInstance();
    }

    private String testName(ITestResult result) {
        return result.getTestClass().getName() + "." + result.getMethod().getMethodName();
    }

    private String safeName(String value) {
        return value.replaceAll("[^a-zA-Z0-9._-]", "_");
    }
}
