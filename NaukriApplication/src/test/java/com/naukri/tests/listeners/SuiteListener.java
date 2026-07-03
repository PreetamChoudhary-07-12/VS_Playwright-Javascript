package com.naukri.tests.listeners;

import java.nio.file.Paths;

import org.testng.ISuite;
import org.testng.ISuiteListener;

import com.naukri.framework.reporting.Log;
import com.naukri.framework.utils.FileUtil;

public class SuiteListener implements ISuiteListener {
    @Override
    public void onStart(ISuite suite) {
        FileUtil.createDirectories(Paths.get("reports", "allure-results"));
        FileUtil.createDirectories(Paths.get("reports", "allure-report"));
        FileUtil.createDirectories(Paths.get("logs"));
        FileUtil.createDirectories(Paths.get("screenshots", "passed"));
        FileUtil.createDirectories(Paths.get("screenshots", "failed"));
        FileUtil.createDirectories(Paths.get("screenshots", "skipped"));
        Log.info("Suite started: " + suite.getName());
    }

    @Override
    public void onFinish(ISuite suite) {
        Log.info("Suite finished: " + suite.getName());
    }
}
