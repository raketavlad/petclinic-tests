package com.example.components;

import com.codeborne.selenide.SelenideElement;
import com.example.pages.ErrorPage;
import com.example.pages.HomePage;
import com.example.pages.FindOwnersPage;
import com.example.pages.VetsPage;

import static com.codeborne.selenide.Condition.clickable;
import static com.codeborne.selenide.Selenide.$;

public class Header {

    private final SelenideElement springButton = $("a.navbar-brand");
    private final SelenideElement homePageButton = $("a[title='home page']");
    private final SelenideElement ownersPageButton = $("a[title='find owners']");
    private final SelenideElement vetsPageButton = $("a[title='veterinarians']");
    private final SelenideElement errorPageButton = $("a[title*='trigger']");

    public HomePage clickSpringButton() {
        springButton.shouldBe(clickable).click();
        return new HomePage();
    }

    public HomePage clickHomePageButton() {
        homePageButton.shouldBe(clickable).click();
        return new HomePage();
    }

    public FindOwnersPage clickOwnersPageButton() {
        ownersPageButton.shouldBe(clickable).click();
        return new FindOwnersPage();
    }

    public VetsPage clickVetsPageButton() {
        vetsPageButton.shouldBe(clickable).click();
        return new VetsPage();
    }

    public ErrorPage clickErrorPageButton() {
        errorPageButton.shouldBe(clickable).click();
        return new ErrorPage();
    }
}
