package com.example.pages;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.support.Color;

public class BasePage {

    protected String getHexColor(SelenideElement se, String cssProperty) {
        return Color.fromString(se.getCssValue(cssProperty)).asHex();
    }
}
