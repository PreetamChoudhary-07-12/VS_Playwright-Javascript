package com.naukri.framework.utils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public final class FileUtil {
    private FileUtil() {
    }

    public static void createDirectories(Path path) {
        try {
            Files.createDirectories(path);
        } catch (IOException e) {
            throw new IllegalStateException("Unable to create directory: " + path, e);
        }
    }

    public static Path writeBytes(Path path, byte[] bytes) {
        try {
            createDirectories(path.getParent());
            return Files.write(path, bytes);
        } catch (IOException e) {
            throw new IllegalStateException("Unable to write file: " + path, e);
        }
    }

    public static List<String> readLines(Path path) {
        try {
            return Files.readAllLines(path, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new IllegalStateException("Unable to read file: " + path, e);
        }
    }
}
