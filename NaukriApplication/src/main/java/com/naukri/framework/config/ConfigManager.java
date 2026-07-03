package com.naukri.framework.config;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.Properties;

import com.naukri.framework.enums.Environment;

public final class ConfigManager {
    private static final ThreadLocal<ConfigManager> CURRENT = new ThreadLocal<>();

    private final Environment environment;
    private final Properties properties;

    private ConfigManager(Environment environment, Properties properties) {
        this.environment = environment;
        this.properties = properties;
    }

    public static ConfigManager load() {
        return load(System.getProperty("env", "qa"));
    }

    public static ConfigManager load(String envName) {
        Environment environment = Environment.from(envName);
        Properties loadedProperties = new Properties();

        loadRequired(loadedProperties, Paths.get("config", environment.fileName()));

        if (environment != Environment.LOCAL) {
            loadOptionalNonBlank(loadedProperties, Paths.get("config", Environment.LOCAL.fileName()));
        }

        applySystemAndEnvironmentOverrides(loadedProperties);

        ConfigManager manager = new ConfigManager(environment, loadedProperties);
        CURRENT.set(manager);
        return manager;
    }

    public static ConfigManager getInstance() {
        ConfigManager manager = CURRENT.get();
        return manager == null ? load() : manager;
    }

    public Environment environment() {
        return environment;
    }

    public String get(String key) {
        return properties.getProperty(key, "").trim();
    }

    public String getOrDefault(String key, String defaultValue) {
        String value = get(key);
        return value.isEmpty() ? defaultValue : value;
    }

    public int getInt(String key, int defaultValue) {
        String value = get(key);
        return value.isEmpty() ? defaultValue : Integer.parseInt(value);
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        String value = get(key);
        return value.isEmpty() ? defaultValue : Boolean.parseBoolean(value);
    }

    public Duration getDuration(String key, int defaultSeconds) {
        return Duration.ofSeconds(getInt(key, defaultSeconds));
    }

    public boolean hasValue(String key) {
        return !get(key).isEmpty();
    }

    private static void loadRequired(Properties target, Path path) {
        if (!Files.exists(path)) {
            throw new IllegalStateException("Required config file not found: " + path.toAbsolutePath());
        }
        loadInto(target, path, false);
    }

    private static void loadOptionalNonBlank(Properties target, Path path) {
        if (Files.exists(path)) {
            loadInto(target, path, true);
        }
    }

    private static void loadInto(Properties target, Path path, boolean nonBlankOnly) {
        Properties source = new Properties();
        try (InputStream input = Files.newInputStream(path)) {
            source.load(input);
        } catch (IOException e) {
            throw new IllegalStateException("Unable to load config file: " + path.toAbsolutePath(), e);
        }

        for (String key : source.stringPropertyNames()) {
            String value = source.getProperty(key, "").trim();
            if (!nonBlankOnly || !value.isEmpty()) {
                target.setProperty(key, value);
            }
        }
    }

    private static void applySystemAndEnvironmentOverrides(Properties properties) {
        for (String key : properties.stringPropertyNames()) {
            String systemValue = System.getProperty(key);
            if (isPresent(systemValue)) {
                properties.setProperty(key, systemValue.trim());
                continue;
            }

            String envValue = System.getenv(toEnvKey(key));
            if (isPresent(envValue)) {
                properties.setProperty(key, envValue.trim());
            }
        }
    }

    private static String toEnvKey(String key) {
        String normalized = key.replaceAll("([a-z])([A-Z])", "$1_$2");
        return "NAUKRI_" + normalized.toUpperCase().replace('.', '_');
    }

    private static boolean isPresent(String value) {
        return value != null && !value.trim().isEmpty();
    }
}
