package com.example.tests;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.example.models.Veterinarian;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import static com.codeborne.selenide.Selenide.*;

public class VeterinariansPageTest extends BaseTest {

    @Test(groups = "smoke")
    public void checkVeterinariansFirstPage() {
        open("/");
        $("a[title='veterinarians']").click();

        // Проверка заголовка
        Assert.assertEquals($("h2").getText(), "Veterinarians");

        SelenideElement table = $("#vets");
        ElementsCollection rows = table.$$("tr");

        // Проверка размеров таблицы
        System.out.println("Количество строк: " + rows.size());
        System.out.println("Количество столбцов: " + rows.get(0).$$("th").size());
        Assert.assertEquals(rows.size(), 6);
        Assert.assertEquals(rows.get(0).$$("th").size(), 2);

        // Проверка заголовков
        Assert.assertEquals(rows.get(0).$$("th").get(0).getText(), "Name");
        Assert.assertEquals(rows.get(0).$$("th").get(1).getText(), "Specialties");

        // Проверка содержимого таблицы
        List<Veterinarian> vets = getVeterinarians();
        for (int i = 1; i < 6; i++) {
            Veterinarian vet = vets.get(i - 1);
            Assert.assertEquals(rows.get(i).$$("td").get(0).getText(), vet.getFirstName() + " " + vet.getLastName());
            Assert.assertEquals(rows.get(i).$$("td").get(1).getText(), vet.getFormattedSpecialities());
        }
    }

    private List<Veterinarian> getVeterinarians() {
        String query = "SELECT id, first_name, last_name FROM vets ORDER BY id";
        List<Veterinarian> veterinarians = new ArrayList<>();
        sqlHelper.execute(query, ps -> {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Veterinarian vet = new Veterinarian();
                vet.setId(rs.getInt("id"));
                vet.setFirstName(rs.getString("first_name"));
                vet.setLastName(rs.getString("last_name"));
                veterinarians.add(vet);
            }
            return null;
        });

        String secondQuery = """
                select s.name from vet_specialties vs
                left join specialties s on s.id = vs.specialty_id
                where vs.vet_id = ?::integer""";
        for (Veterinarian vet : veterinarians) {
            List<String> list = vet.getSpecialities();
            sqlHelper.execute(secondQuery, ps -> {
                ps.setInt(1, vet.getId());
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
        return veterinarians;
    }
}
