package com.example.tests;

import com.codeborne.selenide.Selenide;
import com.example.pages.ErrorPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ErrorPageTest extends BaseTest {

    private static final String HEADLINE = "Something happened...";
    private static final String TEXT = "An internal server error occurred.";
    private static final String IMAGE_URL = "/resources/images/pets.png";

    @Test(description = "Проверка заголовка страницы ошибки",
            groups = "regress")
    public void checkHeadline() {
        ErrorPage page = new ErrorPage();
        Selenide.open(page.getPath());

        Assert.assertEquals(page.getHeadline(), HEADLINE);
    }

    @Test(description = "Проверка текста на странице ошибки",
            groups = "regress")
    public void checkText() {
        ErrorPage page = new ErrorPage();
        Selenide.open(page.getPath());

        Assert.assertEquals(page.getText(), TEXT);
    }

    @Test(description = "Проверка изображения на странице ошибки",
            groups = "regress")
    public void checkImage() {
        ErrorPage page = new ErrorPage();
        Selenide.open(page.getPath());

        Assert.assertEquals(page.getImageUrl(), getBaseUrl() + IMAGE_URL);
    }
}