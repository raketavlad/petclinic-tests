package com.example.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class ErrorPage extends BasePage {

    private static final String PATH = "/oups";

    private final SelenideElement headline = $("h2");
    private final SelenideElement text = $("p > span");
    private final SelenideElement image = $("div.xd-container > img");

    public String getPath() {
        return PATH;
    }

    public String getHeadline() {
        return headline.shouldBe(visible).getText();
    }

    public String getText() {
        return text.shouldBe(visible).getText();
    }

    public String getImageUrl() {
        return image.shouldBe(visible).getAttribute("src");
    }
}