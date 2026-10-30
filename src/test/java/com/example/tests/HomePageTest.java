package com.example.tests;

import com.example.pages.HomePage;
import org.testng.Assert;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Selenide.open;

public class HomePageTest extends BaseTest {

    private static final String HEADLINE = "Welcome";
    private static final String IMAGE_URL = "/resources/images/pets.png";

    @Test(description = "Проверка заголовка на главной странице",
            groups = "regress")
    public void checkHeadline() {
        HomePage page = new HomePage();
        open(page.getPath());

        Assert.assertEquals(page.getHeadline(), HEADLINE);
    }

    @Test(description = "Проверка изображения на главной странице",
            groups = "regress")
    public void checkImage() {
        HomePage page = new HomePage();
        open(page.getPath());

        Assert.assertEquals(page.getImageUrl(), getBaseUrl() + IMAGE_URL);
    }
}
