package com.example.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class HomePage extends BasePage {

    private static final String PATH = "/";

    private final SelenideElement headline = $("h2");
    private final SelenideElement image = $("img.img-responsive");

    public String getPath() {
        return PATH;
    }

    public String getHeadline() {
        return headline.shouldBe(visible).getText();
    }


    public String getImageUrl() {
        return image.shouldBe(visible).getAttribute("src");
    }
}
