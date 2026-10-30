package com.example.models;

import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@EqualsAndHashCode
public class Veterinarian {

    private int id;
    private String firstName;
    private String lastName;
    @Builder.Default
    private List<String> specialities = new ArrayList<>();

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public String getFormattedSpecialities() {
        return specialities.isEmpty()
                ? "none"
                : specialities.stream().sorted().collect(Collectors.joining(" "));
    }
}
