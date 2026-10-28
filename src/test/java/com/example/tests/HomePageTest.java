package com.example.tests;

import com.codeborne.selenide.Selenide;
import com.example.pages.HomePage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class HomePageTest extends BaseTest {

    private static final String HEADLINE = "Welcome";
    private static final String IMAGE_URL = "/resources/images/pets.png";

    @Test(description = "Проверка заголовка на главной странице",
            groups = "regress")
    public void checkHeadline() {
        HomePage page = new HomePage();
        Selenide.open(page.getPath());

        Assert.assertEquals(page.getHeadline(), HEADLINE);
    }

    @Test(description = "Проверка изображения на главной странице",
            groups = "regress")
    public void checkImage() {
        HomePage page = new HomePage();
        Selenide.open(page.getPath());

        Assert.assertEquals(page.getImageUrl(), getBaseUrl() + IMAGE_URL);
    }
}
