package com.example.pages;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.support.Color;

import static com.codeborne.selenide.Condition.visible;

public class BasePage {

    protected String getHexColor(SelenideElement se, String cssProperty) {
        return Color.fromString(se.shouldBe(visible).getCssValue(cssProperty)).asHex();
    }
}
