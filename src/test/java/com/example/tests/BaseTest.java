package com.example.tests;

import com.codeborne.selenide.Configuration;
import com.example.common.ConfigProvider;
import com.example.common.SqlHelper;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

public class BaseTest {

    protected SqlHelper sqlHelper;

    @BeforeClass(alwaysRun = true)
    @Parameters("browser")
    public void setUp(@Optional("chrome") String browser) {
        Configuration.browser = browser;
//        Configuration.headless = true;
        Configuration.baseUrl = ConfigProvider.require("base.url");

        sqlHelper = new SqlHelper(ConfigProvider.get("db.url"),
                ConfigProvider.get("db.user"),
                ConfigProvider.get("db.password"));
    }
}
