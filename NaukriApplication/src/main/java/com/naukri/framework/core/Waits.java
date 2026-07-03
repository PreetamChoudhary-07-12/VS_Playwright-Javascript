package com.naukri.framework.core;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Waits {
    private final WebDriver driver;
    private final Duration timeout;

    public Waits(WebDriver driver, Duration timeout) {
        this.driver = driver;
        this.timeout = timeout;
    }

    public WebElement visible(By locator) {
        return webDriverWait().until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public WebElement clickable(By locator) {
        return webDriverWait().until(ExpectedConditions.elementToBeClickable(locator));
    }

    public boolean titleIs(String expectedTitle) {
        return webDriverWait().until(ExpectedConditions.titleIs(expectedTitle));
    }

    public boolean urlContains(String urlFraction) {
        return webDriverWait().until(ExpectedConditions.urlContains(urlFraction));
    }

    public boolean isDisplayed(By locator) {
        return fluentWait().until(driver -> {
            try {
                return driver.findElement(locator).isDisplayed();
            } catch (NoSuchElementException | StaleElementReferenceException e) {
                return false;
            }
        });
    }

    private WebDriverWait webDriverWait() {
        return new WebDriverWait(driver, timeout);
    }

    private FluentWait<WebDriver> fluentWait() {
        return new FluentWait<>(driver)
                .withTimeout(timeout)
                .pollingEvery(Duration.ofMillis(500))
                .ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class);
    }
}
