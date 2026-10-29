package com.example.tests;

import com.example.components.Header;
import com.example.pages.ErrorPage;
import com.example.pages.FindOwnersPage;
import com.example.pages.VetsPage;
import org.testng.Assert;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Selenide.open;

public class HeaderTest extends BaseTest {

    @Test(description = "Переход на главную кнопкой Spring из Хедера",
            groups = "smoke")
    public void checkSpringButton() {
        Header header = new Header();
        open("/oups");
        header.clickSpringButton();
        Assert.assertEquals(getCurrentUrl(), getBaseUrl());
    }

    @Test(description = "Переход на главную кнопкой Home из Хедера",
            groups = "smoke")
    public void checkHomePageButton() {
        Header header = new Header();
        open("/oups");

        header.clickHomePageButton();
        Assert.assertEquals(getCurrentUrl(), getBaseUrl());
    }

    @Test(description = "Переход на страницу владельцев по кнопке из Хедера",
            groups = "smoke")
    public void checkOwnersPageButton() {
        Header header = new Header();
        open("/oups");

        FindOwnersPage page = header.clickOwnersPageButton();
        Assert.assertEquals(getCurrentUrl(), getBaseUrl() + page.getPath());
    }

    @Test(description = "Переход на страницу ветеринаров по кнопке из Хедера",
            groups = "smoke")
    public void checkVetsPageButton() {
        Header header = new Header();
        open("/oups");

        VetsPage page = header.clickVetsPageButton();
        Assert.assertEquals(getCurrentUrl(), getBaseUrl() + page.getPath());
    }

    @Test(description = "Переход на страницу ошибки по кнопке из Хедера",
            groups = "smoke")
    public void checkErrorPage() {
        Header header = new Header();
        open("/");

        ErrorPage page = header.clickErrorPageButton();
        Assert.assertEquals(getCurrentUrl(), getBaseUrl() + page.getPath());
    }
}
