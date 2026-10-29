package com.example.tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import com.example.common.ConfigProvider;
import com.example.common.SqlHelper;
import com.example.common.VetRepository;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

public class BaseTest {

    protected SqlHelper sqlHelper;
    protected VetRepository vetRepository;

    @BeforeClass(alwaysRun = true)
    @Parameters("browser")
    public void setUp(@Optional("chrome") String browser) {
        Configuration.browser = browser;
        Configuration.headless = true;
        Configuration.baseUrl = ConfigProvider.require("base.url");

        sqlHelper = new SqlHelper(ConfigProvider.get("db.url"),
                ConfigProvider.get("db.user"),
                ConfigProvider.get("db.password"));
        vetRepository = new VetRepository(sqlHelper);
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        Selenide.closeWebDriver();
    }

    protected String getBaseUrl() {
        return Configuration.baseUrl;
    }

    protected String getCurrentUrl() {
        String url = WebDriverRunner.url();
        if (url.endsWith("/")) {
            return url.substring(0, url.length() - 1);
        }
        return url;
    }
}