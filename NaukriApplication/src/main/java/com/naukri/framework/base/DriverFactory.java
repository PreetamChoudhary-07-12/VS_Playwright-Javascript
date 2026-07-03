package com.naukri.framework.base;

import java.net.MalformedURLException;
import java.net.URI;
import java.time.Duration;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.naukri.framework.config.ConfigKeys;
import com.naukri.framework.config.ConfigManager;
import com.naukri.framework.enums.BrowserType;

import io.github.bonigarcia.wdm.WebDriverManager;

public final class DriverFactory {
    private static final ThreadLocal<WebDriver> DRIVER = new ThreadLocal<>();

    private DriverFactory() {
    }

    public static void initDriver(ConfigManager config) {
        if (DRIVER.get() != null) {
            return;
        }

        WebDriver driver = createDriver(config);
        DRIVER.set(driver);
        configureDriver(driver, config);
    }

    public static WebDriver get() {
        WebDriver driver = DRIVER.get();
        if (driver == null) {
            throw new IllegalStateException("Driver is not initialized for this thread.");
        }
        return driver;
    }

    public static WebDriver getNullable() {
        return DRIVER.get();
    }

    public static void quit() {
        WebDriver driver = DRIVER.get();
        if (driver != null) {
            driver.quit();
            DRIVER.remove();
        }
    }

    private static WebDriver createDriver(ConfigManager config) {
        BrowserType browser = BrowserType.from(config.getOrDefault(ConfigKeys.BROWSER, "chrome"));
        boolean remote = config.getBoolean(ConfigKeys.REMOTE, false);
        boolean headless = config.getBoolean(ConfigKeys.HEADLESS, false);

        if (remote) {
            return createRemoteDriver(config, browser, headless);
        }

        return createLocalDriver(config, browser, headless);
    }

    private static WebDriver createRemoteDriver(ConfigManager config, BrowserType browser, boolean headless) {
        String gridUrl = config.get(ConfigKeys.GRID_URL);
        if (gridUrl.isEmpty()) {
            throw new IllegalArgumentException("remote=true requires gridUrl.");
        }

        try {
            return new RemoteWebDriver(URI.create(gridUrl).toURL(), capabilities(config, browser, headless));
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("Invalid gridUrl: " + gridUrl, e);
        }
    }

    private static WebDriver createLocalDriver(ConfigManager config, BrowserType browser, boolean headless) {
        switch (browser) {
            case FIREFOX:
                WebDriverManager.firefoxdriver().setup();
                return new FirefoxDriver((FirefoxOptions) capabilities(config, browser, headless));
            case EDGE:
                WebDriverManager.edgedriver().setup();
                return new EdgeDriver((EdgeOptions) capabilities(config, browser, headless));
            case CHROME:
            default:
                WebDriverManager.chromedriver().setup();
                return new ChromeDriver((ChromeOptions) capabilities(config, browser, headless));
        }
    }

    private static MutableCapabilities capabilities(ConfigManager config, BrowserType browser, boolean headless) {
        int width = config.getInt(ConfigKeys.WINDOW_WIDTH, 1366);
        int height = config.getInt(ConfigKeys.WINDOW_HEIGHT, 768);

        switch (browser) {
            case FIREFOX:
                FirefoxOptions firefox = new FirefoxOptions();
                if (headless) {
                    firefox.addArguments("-headless");
                }
                firefox.addArguments("--width=" + width, "--height=" + height);
                return firefox;
            case EDGE:
                EdgeOptions edge = new EdgeOptions();
                edge.addArguments("--window-size=" + width + "," + height);
                if (headless) {
                    edge.addArguments("--headless=new");
                }
                return edge;
            case CHROME:
            default:
                ChromeOptions chrome = new ChromeOptions();
                chrome.addArguments("--window-size=" + width + "," + height);
                chrome.addArguments("--disable-notifications");
                chrome.addArguments("--disable-popup-blocking");
                if (headless) {
                    chrome.addArguments("--headless=new");
                }
                return chrome;
        }
    }

    private static void configureDriver(WebDriver driver, ConfigManager config) {
        int width = config.getInt(ConfigKeys.WINDOW_WIDTH, 1366);
        int height = config.getInt(ConfigKeys.WINDOW_HEIGHT, 768);
        driver.manage().window().setSize(new Dimension(width, height));
        driver.manage().deleteAllCookies();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(config.getInt(ConfigKeys.IMPLICIT_WAIT, 0)));
        driver.manage().timeouts().pageLoadTimeout(config.getDuration(ConfigKeys.PAGE_LOAD_TIMEOUT, 45));
    }
}
