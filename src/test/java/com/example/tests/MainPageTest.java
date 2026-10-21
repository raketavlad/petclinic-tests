package com.example.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class MainPageTest extends BaseTest {

    @Test(groups = "smoke")
    public void checkHeadline() {
        open("/");

        String text = $("h2").getText();

        Assert.assertEquals(text, "Welcome");
    }
}
