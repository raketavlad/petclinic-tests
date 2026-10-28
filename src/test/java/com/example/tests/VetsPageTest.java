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
    private static final String ACTIVE_BUTTON_COLOR = "#0d6efd";
    private static final String HOVER_BUTTON_COLOR = "#0a58ca";


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

        page.goToPage(middlePage);
        Assert.assertEquals(page.getCurrentPageNumberFromUrl(), middlePage);
        verifyPageContent(page, middlePage);
        Assert.assertTrue(page.hasPreviousPage());
        Assert.assertTrue(page.hasNextPage());
    }

    @Test(description = "Переход по клику на конкретную страницу",
            groups = "smoke")
    public void checkTransitionToPage() {
        VetsPage page = new VetsPage();
        open("/vets.html");

        int middlePage = Math.max(2, vetRepository.getPagesCount() / 2);
        page.goToPage(middlePage);
        Assert.assertEquals(page.getCurrentPageNumber(), middlePage);
    }

    @Test(description = "Переход по клику на следующую страницу",
            groups = "smoke")
    public void checkTransitionToNextPage() {
        VetsPage page = new VetsPage();
        open("/vets.html");

        if (!page.hasNextPage()) {
            throw new SkipException("Для текущей страницы (" + page.getCurrentPageNumber() + ") отсутствует слудующая");
        }
        int expectedPageNumber = page.getCurrentPageNumber() + 1;
        page.goToNextPage();
        Assert.assertEquals(page.getCurrentPageNumber(), expectedPageNumber);
    }

    @Test(description = "Переход по клику на предыдущую страницу",
            groups = "smoke")
    public void checkTransitionToPreviousPage() {
        VetsPage page = new VetsPage();
        open("/vets.html");

        page.goToLastPage();
        if (!page.hasPreviousPage()) {
            throw new SkipException("Для текущей страницы (" + page.getCurrentPageNumber() + ") отсутствует слудующая");
        }
        int expectedPageNumber = page.getCurrentPageNumber() - 1;
        page.goToPreviousPage();
        Assert.assertEquals(page.getCurrentPageNumber(), expectedPageNumber);
    }

    @Test(description = "Переход по клику на первую страницу",
            groups = "smoke")
    public void checkTransitionToFirstPage() {
        VetsPage page = new VetsPage();
        open("/vets.html");

        page.goToLastPage();
        page.goToFirstPage();
        Assert.assertEquals(page.getCurrentPageNumber(), 1);
    }

    @Test(description = "Проверка активного цвета кнопок перехода по страницам",
            groups = "regress")
    public void checkPageButtonsColor() {
        VetsPage page = new VetsPage();
        open("/vets.html");

        if (page.getLastPageNumber() < 2) {
            throw new SkipException("Для vets всего одна страница, проверять нечего!");
        }
        Assert.assertEquals(page.getPageButtonColor(page.getCurrentPageNumber() + 1), ACTIVE_BUTTON_COLOR);
        Assert.assertEquals(page.getNextPageButtonColor(), ACTIVE_BUTTON_COLOR);
        Assert.assertEquals(page.getLastPageButtonColor(), ACTIVE_BUTTON_COLOR);

        page.goToNextPage();

        Assert.assertEquals(page.getPageButtonColor(page.getCurrentPageNumber() - 1), ACTIVE_BUTTON_COLOR);
        Assert.assertEquals(page.getPreviousPageButtonColor(), ACTIVE_BUTTON_COLOR);
        Assert.assertEquals(page.getFirstPageButtonColor(), ACTIVE_BUTTON_COLOR);
    }

    @Test(description = "Проверка активного цвета кнопок перехода по страницам ПРИ ХОВЕРЕ",
            groups = "regress")
    public void checkPageButtonsColorWithHover() {
        VetsPage page = new VetsPage();
        open("/vets.html");

        if (page.getLastPageNumber() < 2) {
            throw new SkipException("Для vets всего одна страница, проверять нечего!");
        }

        int nextPageNumber = page.getCurrentPageNumber() + 1;
        Assert.assertEquals(page.hoverToPageNumberButton(nextPageNumber).getPageButtonColor(nextPageNumber),
                HOVER_BUTTON_COLOR);
        Assert.assertEquals(page.hoverToNextPageButton().getNextPageButtonColor(),
                HOVER_BUTTON_COLOR);
        Assert.assertEquals(page.hoverToLastPageButton().getLastPageButtonColor(),
                HOVER_BUTTON_COLOR);

        page.goToNextPage();

        int previousPageNumber = page.getCurrentPageNumber() - 1;
        Assert.assertEquals(page.hoverToPageNumberButton(previousPageNumber).getPageButtonColor(previousPageNumber),
                HOVER_BUTTON_COLOR);
        Assert.assertEquals(page.hoverToPreviousPageButton().getPreviousPageButtonColor(),
                HOVER_BUTTON_COLOR);
        Assert.assertEquals(page.hoverToFirstPageButton().getFirstPageButtonColor(),
                HOVER_BUTTON_COLOR);
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
