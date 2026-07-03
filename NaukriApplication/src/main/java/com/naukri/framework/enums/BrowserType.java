package com.naukri.framework.enums;

public enum BrowserType {
    CHROME,
    FIREFOX,
    EDGE;

    public static BrowserType from(String value) {
        if (value == null || value.trim().isEmpty()) {
            return CHROME;
        }
        return BrowserType.valueOf(value.trim().toUpperCase());
    }
}
