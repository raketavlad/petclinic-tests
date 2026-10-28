package com.example.common;

import com.example.models.Veterinarian;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public final class VetRepository {

    private static final int PAGE_SIZE = 5;

    private final SqlHelper sqlHelper;

    public VetRepository(SqlHelper sqlHelper) {
        this.sqlHelper = sqlHelper;
    }

    public List<Veterinarian> getVets(int pageNumber) {
        int offsetRow = (pageNumber - 1) * PAGE_SIZE;

        String query = "SELECT id, first_name, last_name FROM vets ORDER BY id LIMIT ? OFFSET ?";
        List<Veterinarian> vets = new ArrayList<>();
        sqlHelper.execute(query, ps -> {
            ps.setInt(1, PAGE_SIZE);
            ps.setInt(2, offsetRow);
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
                join specialties s on s.id = vs.specialty_id
                where vs.vet_id = ?""";
        for (Veterinarian vet : vets) {
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
        return vets;
    }

    public int getVetsCount() {
        return sqlHelper.execute("SELECT COUNT(*) FROM vets", ps -> {
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1);
        });
    }

    public int getPagesCount() {
        return (getVetsCount() + PAGE_SIZE - 1) / PAGE_SIZE;
    }
}