package com.naukri.tests.smoke;

import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;

import com.naukri.framework.base.BaseTest;
import com.naukri.framework.base.DriverFactory;
import com.naukri.framework.config.ConfigKeys;
import com.naukri.framework.core.RetryAnalyzer;
import com.naukri.pages.HomePage;
import com.naukri.pages.LoginPage;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;

@Feature("Login")
public class LoginSmokeTest extends BaseTest {
    @Test(groups = "smoke", retryAnalyzer = RetryAnalyzer.class)
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that the Naukri login page loads with expected URL and title.")
    public void loginPageShouldLoad() {
        LoginPage loginPage = new LoginPage(DriverFactory.get(), config);

        Assert.assertTrue(loginPage.isLoaded(), "Login page should be loaded.");
        Assert.assertEquals(loginPage.getPageTitle(), LoginPage.EXPECTED_TITLE);
        Assert.assertTrue(loginPage.getPageUrl().contains(LoginPage.URL_FRACTION));
    }

    @Test(groups = "smoke", retryAnalyzer = RetryAnalyzer.class)
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify that the Google login option is visible.")
    public void googleLoginOptionShouldBeVisible() {
        LoginPage loginPage = new LoginPage(DriverFactory.get(), config);

        Assert.assertTrue(loginPage.isGoogleLoginVisible(), "Google login option should be visible.");
    }

    @Test(groups = "smoke", retryAnalyzer = RetryAnalyzer.class)
    @Severity(SeverityLevel.BLOCKER)
    @Description("Login with configured user and verify profile is displayed.")
    public void configuredUserShouldLogin() {
        skipIfMissing(ConfigKeys.USERNAME);
        skipIfMissing(ConfigKeys.PASSWORD);
        skipIfMissing(ConfigKeys.EXPECTED_PROFILE_NAME);

        LoginPage loginPage = new LoginPage(DriverFactory.get(), config);
        HomePage homePage = loginPage.loginAs(config.get(ConfigKeys.USERNAME), config.get(ConfigKeys.PASSWORD));

        Assert.assertTrue(homePage.isLoaded(), "Home page should load after login.");
        Assert.assertTrue(homePage.isProfileVisible(config.get(ConfigKeys.EXPECTED_PROFILE_NAME)),
                "Configured profile should be visible after login.");
    }

    private void skipIfMissing(String key) {
        if (!config.hasValue(key)) {
            throw new SkipException(key + " is not configured.");
        }
    }
}
