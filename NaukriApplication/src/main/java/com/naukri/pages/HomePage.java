package com.naukri.pages;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

import com.naukri.framework.base.BasePage;
import com.naukri.framework.config.ConfigManager;

import io.qameta.allure.Step;

public class HomePage extends BasePage {
    public static final String EXPECTED_TITLE = "Home | Mynaukri";
    public static final String URL_FRACTION = "mnjuser/homepage";

    private final By viewProfileLink = By.xpath("//a[@href='/mnjuser/profile' and normalize-space()='View']");
    private final By searchJobsBox = By.xpath("//span[normalize-space()='Search jobs here']");
    private final By jobRoleInput = By.xpath("//input[@placeholder='Enter keyword / designation / companies']");
    private final By jobLocationInput = By.xpath("//input[@placeholder='Enter location']");
    private final By searchButton = By.xpath("//span[normalize-space()='Search']");
    public HomePage(WebDriver driver, ConfigManager config) {
        super(driver, config);
    }

    @Step("Check home page is loaded")
    public boolean isLoaded() {
        return waits.urlContains(URL_FRACTION);
    }

    @Step("Get home page title")
    public String getPageTitle() {
        waits.titleIs(EXPECTED_TITLE);
        return title();
    }

    @Step("Get home page URL")
    public String getPageUrl() {
        waits.urlContains(URL_FRACTION);
        return currentUrl();
    }

    @Step("Check logged-in profile name is visible: {profileName}")
    public boolean isProfileVisible(String profileName) {
        return isDisplayed(By.xpath("//*[normalize-space()=" + xpathLiteral(profileName) + " or @title="
                + xpathLiteral(profileName) + "]"));
    }

    @Step("Read configured profile contact details")
    public Map<String, String> readUserDetails(String expectedMobile, String expectedEmail) {
        click(viewProfileLink);
        Map<String, String> details = new HashMap<>();
        details.put("mobile", text(By.xpath("//span[normalize-space()=" + xpathLiteral(expectedMobile) + "]")));
        details.put("email", text(By.xpath("//span[normalize-space()=" + xpathLiteral(expectedEmail) + "]")));
        return details;
    }

    @Step("Search job for role {role} in {location}")
    public String searchJob(String role, String location) {
        click(searchJobsBox);
        type(jobRoleInput, role);
        type(jobLocationInput, location);
        visible(jobLocationInput).sendKeys(Keys.TAB);
        click(searchButton);
        By firstResultTitle = By.xpath("(//a[contains(translate(normalize-space(.), 'abcdefghijklmnopqrstuvwxyz', "
                + "'ABCDEFGHIJKLMNOPQRSTUVWXYZ'), " + xpathLiteral(role.toUpperCase()) + ")])[1]");
        return text(firstResultTitle);
    }

    private String xpathLiteral(String value) {
        if (!value.contains("'")) {
            return "'" + value + "'";
        }
        if (!value.contains("\"")) {
            return "\"" + value + "\"";
        }
        return "concat('" + value.replace("'", "', \"'\", '") + "')";
    }
}
