package com.example.tests;

import com.codeborne.selenide.WebDriverRunner;
import com.example.models.Veterinarian;
import com.example.pages.VetsPage;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.codeborne.selenide.Selenide.open;

public class VetsPageTest extends BaseTest {

    private static final int PAGE_SIZE = 5;
    private static final String HEADLINE = "Veterinarians";

    @Test(description = "Проверка общего количества страниц")
    public void checkCountPages() {
        VetsPage page = new VetsPage();
        open("/vets.html");

        Assert.assertEquals(page.getLastPageNumber(), getPagesCount());
    }

    @Test(description = "Проверка первой страницы")
    public void checkFirstPage() {
        VetsPage page = new VetsPage();
        open("/vets.html");

        int pageNumber = getCurrentPageNumber();

        Assert.assertEquals(pageNumber, 1);

        Assert.assertEquals(page.getHeadlineText(), HEADLINE);

        Assert.assertEquals(page.getTableHeadingText(0), "Name");
        Assert.assertEquals(page.getTableHeadingText(1), "Specialties");

        List<Veterinarian> vets = getVets(pageNumber);

        Assert.assertEquals(page.getColumnsCount(), 2);
        Assert.assertEquals(page.getRowsCount(), vets.size() + 1);

        int i = 1;
        for (Veterinarian vet : vets) {
            Assert.assertEquals(page.getTableCellText(0, i), vet.getFullName());
            Assert.assertEquals(page.getTableCellText(1, i), vet.getFormattedSpecialities());
            i++;
        }

        Assert.assertFalse(page.hasPreviousPage());

        if (getVetsCount() > 5) {
            Assert.assertTrue(page.hasNextPage());
        }
    }

    @Test(description = "Проверка последней страницы")
    public void checkLastPage() {
        VetsPage page = new VetsPage();
        open("/vets.html");

        page.goToLastPage();

        int pageNumber = getCurrentPageNumber();

        Assert.assertEquals(page.getHeadlineText(), HEADLINE);

        Assert.assertEquals(page.getTableHeadingText(0), "Name");
        Assert.assertEquals(page.getTableHeadingText(1), "Specialties");

        List<Veterinarian> vets = getVets(pageNumber);

        Assert.assertEquals(page.getColumnsCount(), 2);
        Assert.assertEquals(page.getRowsCount(), vets.size() + 1);

        int i = 1;
        for (Veterinarian vet : vets) {
            Assert.assertEquals(page.getTableCellText(0, i), vet.getFullName());
            Assert.assertEquals(page.getTableCellText(1, i), vet.getFormattedSpecialities());
            i++;
        }
        Assert.assertTrue(page.hasPreviousPage());
        Assert.assertFalse(page.hasNextPage());
    }

    @Test(description = "Проверка остальных страниц (не первой и не последней)")
    public void checkOtherPages() {
        VetsPage page = new VetsPage();
        open("/vets.html");

        int pageNumber = 2; // Нужно прокидывать какую-то страницу, наверное через дата провайдер

        if (getPagesCount() > 2) {
            page.goToPage(pageNumber);

            Assert.assertEquals(page.getHeadlineText(), HEADLINE);

            Assert.assertEquals(page.getTableHeadingText(0), "Name");
            Assert.assertEquals(page.getTableHeadingText(1), "Specialties");

            List<Veterinarian> vets = getVets(pageNumber);

            Assert.assertEquals(page.getColumnsCount(), 2);
            Assert.assertEquals(page.getRowsCount(), vets.size() + 1);

            int i = 1;
            for (Veterinarian vet : vets) {
                Assert.assertEquals(page.getTableCellText(0, i), vet.getFullName());
                Assert.assertEquals(page.getTableCellText(1, i), vet.getFormattedSpecialities());
                i++;
            }

            Assert.assertTrue(page.hasPreviousPage());
            Assert.assertTrue(page.hasNextPage());
        } else {
            System.out.println("Количество страниц меньше трёх, проверка не нужна!");
        }
    }


    @Test(description = "Метод для проверок")
    public void myTest() {
        VetsPage page = new VetsPage();
        open("/vets.html");
//        System.out.println(page.getTableHeadingText(0));

        Assert.assertTrue(page.hasNextPage());
        page.goToPage(2);
        Assert.assertTrue(page.hasPreviousPage());
    }

    private List<Veterinarian> getVets(int pageNumber) {
        int offsetRow = (pageNumber - 1) * 5;

        String query = "SELECT id, first_name, last_name FROM vets ORDER BY id LIMIT 5 OFFSET ?";
        List<Veterinarian> vets = new ArrayList<>();
        sqlHelper.execute(query, ps -> {
            ps.setInt(1, offsetRow);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Veterinarian vet = new Veterinarian();
                vet.setId(rs.getInt("id"));
                vet.setFirstName(rs.getString("first_name"));
                vet.setLastName(rs.getString("last_name"));
                vets.add(vet);
            }
            return null;
        });

        String secondQuery = """
                select s.name from vet_specialties vs
                left join specialties s on s.id = vs.specialty_id
                where vs.vet_id = ?
                LIMIT 5 OFFSET ?""";
        for (Veterinarian vet : vets) {
            List<String> list = vet.getSpecialities();
            sqlHelper.execute(secondQuery, ps -> {
                ps.setInt(1, vet.getId());
                ps.setInt(2, offsetRow);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    list.add(rs.getString("name"));
                }
                if (list.isEmpty()) {
                    list.add("none");
                }
                return null;
            });
        }
        return vets;
    }

    private Integer getVetsCount() {
        String query = "SELECT COUNT(*) FROM vets";
        return sqlHelper.execute(query, ps -> {
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
            return null;
        });
    }

    private int getCurrentPageNumber() {
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

    private int getPagesCount() {
        return (getVetsCount() + PAGE_SIZE - 1) / PAGE_SIZE;
    }
}
