package com.naukri.framework.enums;

public enum Environment {
    QA,
    STAGING,
    PROD,
    LOCAL;

    public static Environment from(String value) {
        if (value == null || value.trim().isEmpty()) {
            return QA;
        }
        return Environment.valueOf(value.trim().toUpperCase());
    }

    public String fileName() {
        return "config." + name().toLowerCase() + ".properties";
    }
}
