package com.naukri.tests.regression;

import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
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

@Feature("Profile and Job Search")
public class ProfileTests extends BaseTest {
    private HomePage homePage;

    @BeforeMethod(alwaysRun = true)
    public void loginBeforeRegressionTest() {
        skipIfMissing(ConfigKeys.USERNAME);
        skipIfMissing(ConfigKeys.PASSWORD);
        LoginPage loginPage = new LoginPage(DriverFactory.get(), config);
        homePage = loginPage.loginAs(config.get(ConfigKeys.USERNAME), config.get(ConfigKeys.PASSWORD));
    }

    @Test(groups = "regression", retryAnalyzer = RetryAnalyzer.class)
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify home page title and URL after login.")
    public void homePageShouldLoad() {
        Assert.assertTrue(homePage.isLoaded(), "Home page should be loaded.");
        Assert.assertEquals(homePage.getPageTitle(), HomePage.EXPECTED_TITLE);
        Assert.assertTrue(homePage.getPageUrl().contains(HomePage.URL_FRACTION));
    }

    @Test(groups = "regression", retryAnalyzer = RetryAnalyzer.class)
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify configured profile contact details.")
    public void profileContactDetailsShouldMatchConfig() {
        skipIfMissing(ConfigKeys.EXPECTED_MOBILE);
        skipIfMissing(ConfigKeys.EXPECTED_EMAIL);

        Map<String, String> actual = homePage.readUserDetails(
                config.get(ConfigKeys.EXPECTED_MOBILE),
                config.get(ConfigKeys.EXPECTED_EMAIL));

        Map<String, String> expected = new HashMap<>();
        expected.put("mobile", config.get(ConfigKeys.EXPECTED_MOBILE));
        expected.put("email", config.get(ConfigKeys.EXPECTED_EMAIL));

        Assert.assertEquals(actual, expected);
    }

    @Test(groups = "regression", retryAnalyzer = RetryAnalyzer.class)
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify job search by configured role and location.")
    public void jobSearchShouldReturnExpectedResult() {
        String firstResult = homePage.searchJob(config.get(ConfigKeys.JOB_ROLE), config.get(ConfigKeys.JOB_LOCATION));

        Assert.assertTrue(firstResult.contains(config.get(ConfigKeys.JOB_ROLE))
                        || firstResult.contains(config.get(ConfigKeys.EXPECTED_JOB_TITLE)),
                "First job result did not match configured expectations.");
    }

    private void skipIfMissing(String key) {
        if (!config.hasValue(key)) {
            throw new SkipException(key + " is not configured.");
        }
    }
}
