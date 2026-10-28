package com.example.pages;

import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.codeborne.selenide.Condition.clickable;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class VetsPage extends BasePage {

    private final SelenideElement headline = $("h2");
    private final SelenideElement table = $("#vets");

    private final String pageButton = "//div/span/a[text()='%d']";
    private final SelenideElement firstPageButton = $("[title='First']");
    private final SelenideElement previousPageButton = $("[title='Previous']");
    private final SelenideElement nextPageButton = $("[title='Next']");
    private final SelenideElement lastPageButton = $("[title='Last']");
    private final SelenideElement currentPageNumber = $("div span span");
    private final SelenideElement lastPageNumber = $x("//div/span[contains(text(), ']')]/preceding-sibling::span[1]");

    public String getHeadlineText() {
        return headline.shouldBe(visible).getText();
    }

    public int getDataRowsCount() {
        return table.$$("tbody tr").size();
    }

    public int getColumnsCount() {
        return table.$$("tr").get(0).$$("th").size();
    }

    public String getTableHeadingText(int columnIndex) {
        return table.$$("tr").get(0).$$("th").get(columnIndex).getText();
    }

    public String getTableCellText(int rowIndex, int columnIndex) {
        return table.$$("tr").get(rowIndex).$$("td").get(columnIndex).getText();
    }

    public boolean hasNextPage() {
        return getHexColor(nextPageButton, "color").equals("#0d6efd");
    }

    public boolean hasPreviousPage() {
        return getHexColor(previousPageButton, "color").equals("#0d6efd");
    }

    public VetsPage goToNextPage() {
        nextPageButton.shouldBe(clickable).click();
        return this;
    }

    public VetsPage goToPreviousPage() {
        previousPageButton.shouldBe(clickable).click();
        return this;
    }

    public VetsPage goToLastPage() {
        lastPageButton.shouldBe(clickable).click();
        return this;
    }

    public VetsPage goToFirstPage() {
        firstPageButton.shouldBe(clickable).click();
        return this;
    }

    public VetsPage goToPage(int pageNumber) {
        $x(String.format(pageButton, pageNumber)).shouldBe(clickable).click();
        return this;
    }

    public int getCurrentPageNumber() {
        return Integer.parseInt(currentPageNumber.shouldBe(visible).getText());
    }

    public int getCurrentPageNumberFromUrl() {
        String currentUrl = WebDriverRunner.url();
        Pattern pattern = Pattern.compile("[?&]page=([^&#]*)");
        Matcher matcher = pattern.matcher(currentUrl);
        String value = matcher.find() ? matcher.group(1) : null;
        if (value == null) {
            return 1;
        } else {
            return Integer.parseInt(value);
        }
    }

    public int getLastPageNumber() {
        return Integer.parseInt(lastPageNumber.shouldBe(visible).getText());
    }

    public String getPageButtonColor(int pageNumber) {
        return getHexColor($x(String.format(pageButton, pageNumber)), "color");
    }

    public String getNextPageButtonColor() {
        return getHexColor(nextPageButton, "color");
    }

    public String getPreviousPageButtonColor() {
        return getHexColor(previousPageButton, "color");
    }

    public String getFirstPageButtonColor() {
        return getHexColor(firstPageButton, "color");
    }

    public String getLastPageButtonColor() {
        return getHexColor(lastPageButton, "color");
    }

    public VetsPage hoverToPageNumberButton(int pageNumber) {
        $x(String.format(pageButton, pageNumber)).shouldBe(visible).hover();
        return this;
    }

    public VetsPage hoverToNextPageButton() {
        nextPageButton.shouldBe(visible).hover();
        return this;
    }

    public VetsPage hoverToPreviousPageButton() {
        previousPageButton.shouldBe(visible).hover();
        return this;
    }

    public VetsPage hoverToFirstPageButton() {
        firstPageButton.shouldBe(visible).hover();
        return this;
    }

    public VetsPage hoverToLastPageButton() {
        lastPageButton.shouldBe(visible).hover();
        return this;
    }
}
