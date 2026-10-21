package com.example.common;

import com.example.exceptions.ConfigException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.Properties;

public final class ConfigProvider {
    private static final Properties PROPERTIES = new Properties();

    static {
        load("config.properties");
    }

    private ConfigProvider() {
    }

    private static void load(String fileName) {
        try (InputStream is = ConfigProvider.class.getClassLoader().getResourceAsStream(fileName)) {
            if (is != null) {
                PROPERTIES.load(is);   // более поздний load перезаписывает ранние ключи
            }
        } catch (IOException e) {
            throw new ConfigException("Не удалось загрузить " + fileName, e);
        }
    }

    public static String get(String key, String defaultValue) {
        // Проверяем наличие проперти key в системных переменных JVM
        String sys = System.getProperty(key);
        if (sys != null && !sys.isBlank()) return sys;

        // Проверяем наличие проперти key в системных переменных ОС
        String envKey = key.toUpperCase(Locale.ROOT).replace('.', '_');
        String env = System.getenv(envKey);
        if (env != null && !env.isBlank()) return env;

        // Проверяем наличие проперти key в переменных из файлов
        return PROPERTIES.getProperty(key, defaultValue);
    }

    public static String get(String key) {
        return get(key, null);
    }

    public static String require(String key) {
        String value = get(key);
        if (value == null || value.isBlank()) {
            throw new ConfigException("Обязательный параметр '" + key + "' не задан");
        }
        return value;
    }

    public static boolean getBoolean(String key, boolean def) {
        return Boolean.parseBoolean(get(key, String.valueOf(def)));
    }

    public static int getInt(String key, int def) {
        return Integer.parseInt(get(key, String.valueOf(def)));
    }
}
