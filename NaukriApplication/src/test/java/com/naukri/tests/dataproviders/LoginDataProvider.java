package com.naukri.tests.dataproviders;

import java.nio.file.Paths;
import java.util.List;

import org.testng.annotations.DataProvider;

import com.naukri.framework.utils.FileUtil;

public final class LoginDataProvider {
    private LoginDataProvider() {
    }

    @DataProvider(name = "csvUsers")
    public static Object[][] csvUsers() {
        List<String> rows = FileUtil.readLines(Paths.get("src", "main", "resources", "testdata", "users.csv"));
        return rows.stream()
                .skip(1)
                .filter(row -> !row.trim().isEmpty())
                .map(row -> row.split(",", -1))
                .map(values -> new Object[] {values[0].trim(), values[1].trim(), values[2].trim()})
                .toArray(Object[][]::new);
    }
}
