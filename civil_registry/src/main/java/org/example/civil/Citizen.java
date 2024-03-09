package org.example;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Citizen extends Human {
    FamilyStatus familyStatus = FamilyStatus.NOT_MARRIED;
    Citizen marriagePartner;

    public Citizen(@NonNull String name, @NonNull String surname, @NonNull String patronymic, @NonNull Gender gender) {
        super(name, surname, patronymic, gender);
    }
}
