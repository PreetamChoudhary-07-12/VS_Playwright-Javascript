package com.naukri.framework.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class DateTimeUtil {
    private static final DateTimeFormatter FILE_TIMESTAMP = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss_SSS");

    private DateTimeUtil() {
    }

    public static String fileTimestamp() {
        return LocalDateTime.now().format(FILE_TIMESTAMP);
    }
}
