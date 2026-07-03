package com.naukri.framework.reporting;

import java.io.ByteArrayInputStream;
import java.nio.file.Path;

import io.qameta.allure.Allure;

public final class AllureHelper {
    private AllureHelper() {
    }

    public static void attachScreenshot(String name, byte[] screenshot) {
        Allure.addAttachment(name, "image/png", new ByteArrayInputStream(screenshot), ".png");
    }

    public static void attachText(String name, String content) {
        Allure.addAttachment(name, "text/plain", content);
    }

    public static void attachFilePath(String name, Path path) {
        attachText(name, path.toAbsolutePath().toString());
    }
}
