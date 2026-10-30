package com.example.common;

import com.example.exceptions.ConfigException;

import java.util.Locale;

public enum Environment {
    TESTING,
    STAGING,
    PROD;

    public static Environment getProposedEnvironment() {
        String targetEnv = ConfigProvider.get("env", "testing");

        switch (targetEnv.toLowerCase(Locale.ROOT).trim()) {
            case "testing":
                return TESTING;
            case "staging":
                return STAGING;
            case "prod":
                return PROD;
            default:
                throw new ConfigException("Неизвестное окружение: '" + targetEnv + "'");
        }
    }
}
