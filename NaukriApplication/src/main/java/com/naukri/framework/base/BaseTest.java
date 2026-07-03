package com.naukri.framework.base;

import java.lang.reflect.Method;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.naukri.framework.config.ConfigKeys;
import com.naukri.framework.config.ConfigManager;
import com.naukri.framework.reporting.Log;

public abstract class BaseTest {
    protected ConfigManager config;

    @BeforeSuite(alwaysRun = true)
    public void beforeSuite() {
        config = ConfigManager.load();
        Log.info("Loaded configuration for environment: " + config.environment());
    }

    @BeforeMethod(alwaysRun = true)
    public void beforeMethod(Method method) {
        config = ConfigManager.getInstance();
        Log.info("Starting test: " + method.getName());
        DriverFactory.initDriver(config);
        DriverFactory.get().get(config.get(ConfigKeys.BASE_URL));
    }

    @AfterMethod(alwaysRun = true)
    public void afterMethod(Method method) {
        Log.info("Finished test: " + method.getName());
        DriverFactory.quit();
    }

    public WebDriver driver() {
        return DriverFactory.get();
    }

    public ConfigManager config() {
        return config;
    }

    protected boolean hasCredentials() {
        return config.hasValue(ConfigKeys.USERNAME) && config.hasValue(ConfigKeys.PASSWORD);
    }
}
