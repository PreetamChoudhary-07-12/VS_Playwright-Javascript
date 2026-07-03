package com.naukri.framework.utils;

import java.util.UUID;

public final class DataFaker {
    private DataFaker() {
    }

    public static String uniqueEmail() {
        return "automation+" + UUID.randomUUID() + "@example.com";
    }

    public static String uniqueName(String prefix) {
        return prefix + " " + UUID.randomUUID().toString().substring(0, 8);
    }
}
