package com.example.common;

import java.util.Locale;

public enum Environment {
    TESTING,
    STAGING,
    PROD;

    public static Environment getProposedEnvironment() {
        String targetEnv = System.getProperty("env", "testing");

        if (targetEnv == null) {
            return TESTING;
        }

        switch (targetEnv.toLowerCase(Locale.ROOT).trim()) {
            case "staging":
                return STAGING;
            case "prod":
                return PROD;
            default:
                System.out.println("Выбрано дефолтное окружение: TESTING"); // Нужно написать нормально в логи
                return TESTING;
        }
    }
}
