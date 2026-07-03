package com.naukri.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.naukri.framework.base.BasePage;
import com.naukri.framework.config.ConfigManager;

import io.qameta.allure.Step;

public class LoginPage extends BasePage {
    public static final String EXPECTED_TITLE =
            "Jobseeker's Login: Search the Best Jobs available in India & Abroad - Naukri.com";
    public static final String URL_FRACTION = "nlogin/login";
    public static final String DREAM_JOB_TEXT = "Find your dream job now";

    private final By loginLayer = By.id("login_Layer");
    private final By usernameInput = By.id("usernameField");
    private final By passwordInput = By.id("passwordField");
    private final By loginButton = By.xpath("//button[@type='submit' and normalize-space()='Login']");
    private final By googleLoginLabel = By.xpath("//span[normalize-space()='Sign in with Google']");
    private final By jobsLabel = By.xpath("//div[normalize-space()='Jobs']");
    private final By dreamJobHeading = By.xpath("//h1[normalize-space()='Find your dream job now']");

    public LoginPage(WebDriver driver, ConfigManager config) {
        super(driver, config);
    }

    @Step("Check login page is loaded")
    public boolean isLoaded() {
        return waits.urlContains(URL_FRACTION) && isDisplayed(usernameInput);
    }

    @Step("Get login page title")
    public String getPageTitle() {
        waits.titleIs(EXPECTED_TITLE);
        return title();
    }

    @Step("Get login page URL")
    public String getPageUrl() {
        waits.urlContains(URL_FRACTION);
        return currentUrl();
    }

    @Step("Check Google login option is visible")
    public boolean isGoogleLoginVisible() {
        return isDisplayed(googleLoginLabel);
    }

    @Step("Open jobs tab and read dream job heading")
    public String getDreamJobHeading() {
        click(jobsLabel);
        return text(dreamJobHeading);
    }

    @Step("Login as configured Naukri user")
    public HomePage loginAs(String username, String password) {
        if (!isDisplayed(usernameInput) && isDisplayed(loginLayer)) {
            click(loginLayer);
        }
        type(usernameInput, username);
        type(passwordInput, password);
        click(loginButton);
        return new HomePage(driver, config);
    }
}
