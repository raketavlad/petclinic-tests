package com.example.tests;

import com.example.models.Veterinarian;
import com.example.pages.VetsPage;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;

import java.util.List;

import static com.codeborne.selenide.Selenide.open;

public class VetsPageTest extends BaseTest {

    private static final String HEADLINE = "Veterinarians";

    @Test(description = "Проверка общего количества страниц",
            groups = "smoke")
    public void checkCountPages() {
        VetsPage page = new VetsPage();
        open("/vets.html");

        Assert.assertEquals(page.getLastPageNumber(), vetRepository.getPagesCount());
    }

    @Test(description = "Проверка первой страницы",
            groups = "smoke")
    public void checkFirstPage() {
        VetsPage page = new VetsPage();
        open("/vets.html");

        int pageNumberFromPagination = page.getCurrentPageNumber();
        int pageNumberFromUrl = page.getCurrentPageNumberFromUrl();
        Assert.assertEquals(pageNumberFromUrl, pageNumberFromPagination);

        Assert.assertEquals(pageNumberFromUrl, 1);
        verifyPageContent(page, pageNumberFromUrl);
        Assert.assertFalse(page.hasPreviousPage());
        Assert.assertEquals(page.hasNextPage(), vetRepository.getPagesCount() > 1);
    }

    @Test(description = "Проверка последней страницы",
            groups = "smoke")
    public void checkLastPage() {
        VetsPage page = new VetsPage();
        open("/vets.html");

        page.goToLastPage();
        Assert.assertEquals(page.getCurrentPageNumberFromUrl(), vetRepository.getPagesCount());

        int pageNumberFromPagination = page.getCurrentPageNumber();
        int pageNumberFromUrl = page.getCurrentPageNumberFromUrl();
        Assert.assertEquals(pageNumberFromUrl, pageNumberFromPagination);

        verifyPageContent(page, pageNumberFromUrl);
        Assert.assertTrue(page.hasPreviousPage());
        Assert.assertFalse(page.hasNextPage());
    }

    @Test(description = "Проверка средней страницы (не первой и не последней)",
            groups = "smoke")
    public void checkOtherPages() {
        VetsPage page = new VetsPage();
        open("/vets.html");

        int totalPagesCount = vetRepository.getPagesCount();
        if (totalPagesCount <= 2) {
            throw new SkipException("Количество страниц меньше трёх, проверять нечего!");
        }

        int middlePage = Math.max(2, totalPagesCount / 2);
        int pageNumberFromPagination = page.getCurrentPageNumber();
        int pageNumberFromUrl = page.getCurrentPageNumberFromUrl();
        Assert.assertEquals(middlePage, pageNumberFromUrl, pageNumberFromPagination);

        page.goToPage(middlePage);
        Assert.assertEquals(page.getCurrentPageNumberFromUrl(), middlePage);
        verifyPageContent(page, middlePage);
        Assert.assertTrue(page.hasPreviousPage());
        Assert.assertTrue(page.hasNextPage());
    }

//    @Test(description = "Переход по клику на конкретную страницу")
//    public void


//    @Test(description = "Переход по клику на следующую страницу")
//
//    @Test(description = "Переход по клику на предыдущую страницу")
//
//    @Test(description = "Переход по клику на первую страницу")

    @Test(description = "Метод для проверок")
    public void myTest() {
        VetsPage page = new VetsPage();
        open("/vets.html");
//        System.out.println(page.getTableHeadingText(0));

        Assert.assertTrue(page.hasNextPage());
        page.goToPage(2);
        Assert.assertTrue(page.hasPreviousPage());
    }

    private void verifyPageContent(VetsPage page, int pageNumber) {
        Assert.assertEquals(page.getHeadlineText(), HEADLINE);
        Assert.assertEquals(page.getColumnsCount(), 2);
        Assert.assertEquals(page.getTableHeadingText(0), "Name");
        Assert.assertEquals(page.getTableHeadingText(1), "Specialties");

        List<Veterinarian> expected = vetRepository.getVets(pageNumber);
        int vetsCount = expected.size();
        Assert.assertEquals(page.getDataRowsCount(), vetsCount);

        for (int i = 0; i < vetsCount; i++) {
            Assert.assertEquals(page.getTableCellText(i + 1, 0),
                    expected.get(i).getFullName());
            Assert.assertEquals(page.getTableCellText(i + 1, 1),
                    expected.get(i).getFormattedSpecialities());
        }
    }
}
