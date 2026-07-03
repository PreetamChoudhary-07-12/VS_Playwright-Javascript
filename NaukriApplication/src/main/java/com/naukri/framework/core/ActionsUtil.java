package com.naukri.framework.core;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

public class ActionsUtil {
    private final WebDriver driver;
    private final Actions actions;

    public ActionsUtil(WebDriver driver) {
        this.driver = driver;
        this.actions = new Actions(driver);
    }

    public void moveTo(By locator) {
        actions.moveToElement(driver.findElement(locator)).perform();
    }

    public void click(By locator) {
        actions.click(driver.findElement(locator)).perform();
    }

    public void type(By locator, String value) {
        actions.sendKeys(driver.findElement(locator), value).perform();
    }

    public void dragAndDrop(By source, By target) {
        actions.dragAndDrop(driver.findElement(source), driver.findElement(target)).perform();
    }
}
