package com.example.models;

import lombok.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

    public String getFormattedSpecialities() {
        Collections.sort(specialities);
        return specialities.isEmpty() ? "none" : String.join(" ", specialities);
    }
}
