package com.example.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.clickable;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class VetsPage extends BasePage {

    private final SelenideElement headline = $("h2");
    private final SelenideElement table = $("#vets");

    private final SelenideElement firstPageButton = $("[title='First']");
    private final SelenideElement previousPageButton = $("[title='Previous']");
    private final SelenideElement nextPageButton = $("[title='Next']");
    private final SelenideElement lastPageButton = $("[title='Last']");
    private final SelenideElement lastPageNumber = $x("//div/span[contains(text(), ']')]/preceding-sibling::span[1]");



    public String getHeadlineText() {
        return headline.shouldBe(visible).getText();
    }

    public int getRowsCount() {
        return table.$$("tr").size();
    }

    public int getColumnsCount() {
        return table.$$("tr").get(0).$$("th").size();
    }

    public String getTableHeadingText(int columnIndex) {
        return table.$$("tr").get(0).$$("th").get(columnIndex).getText();
    }

    public String getTableCellText(int columnIndex, int rowIndex) {
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
        $x(String.format("//a[text()='%d']", pageNumber)).shouldBe(clickable).click();
        return this;
    }

    public int getCurrentPageNumber() {
        return 0;
    }

    public int getLastPageNumber() {
        return Integer.parseInt(lastPageNumber.shouldBe(visible).getText());
    }
}
