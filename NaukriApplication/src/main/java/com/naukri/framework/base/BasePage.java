package com.naukri.framework.base;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import com.naukri.framework.config.ConfigKeys;
import com.naukri.framework.config.ConfigManager;
import com.naukri.framework.core.Waits;

public abstract class BasePage {
    protected final WebDriver driver;
    protected final ConfigManager config;
    protected final Waits waits;

    protected BasePage(WebDriver driver, ConfigManager config) {
        this.driver = driver;
        this.config = config;
        this.waits = new Waits(driver, config.getDuration(ConfigKeys.EXPLICIT_WAIT, 20));
        PageFactory.initElements(driver, this);
    }

    protected WebElement visible(By locator) {
        return waits.visible(locator);
    }

    protected WebElement clickable(By locator) {
        return waits.clickable(locator);
    }

    protected void click(By locator) {
        clickable(locator).click();
    }

    protected void type(By locator, String value) {
        WebElement element = visible(locator);
        element.clear();
        element.sendKeys(value);
    }

    protected String text(By locator) {
        return visible(locator).getText();
    }

    protected boolean isDisplayed(By locator) {
        return waits.isDisplayed(locator);
    }

    protected String title() {
        return driver.getTitle();
    }

    protected String currentUrl() {
        return driver.getCurrentUrl();
    }
}
