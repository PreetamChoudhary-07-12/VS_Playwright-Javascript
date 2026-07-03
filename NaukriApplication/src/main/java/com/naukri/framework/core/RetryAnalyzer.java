package com.naukri.framework.core;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

import io.qameta.allure.Allure;

public class RetryAnalyzer implements IRetryAnalyzer {
    private int retryCount;
    private final int maxRetryCount = Integer.parseInt(System.getProperty("retry.count", "0"));

    @Override
    public boolean retry(ITestResult result) {
        if (retryCount < maxRetryCount) {
            retryCount++;
            Allure.label("retried", "true");
            return true;
        }
        return false;
    }
}
