package org.example;

import java.time.LocalDate;
import java.util.*;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import static org.example.TypeRegistry.*;

@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class CivilRegistry {
    @NonNull
    String name;
    Map<LocalDate, List<WriterTypeRegistry>> listRegistry = new TreeMap<>(LocalDate::compareTo);

    /**
     * 3.4.1 Рождение ребенка - передается новорожденный, отец, мать + дата регистрации рождения.
     * Создается запись гражданского действия за дату регистрации.
     */
    public void birthChild(Citizen father, Citizen mother, LocalDate date) {
        addRegistry(BIRTH_REGISTRATION, date, father, mother);
    }

    /**3.4.2 Регистрация свадьбы - передаются мужчина и женщина + дата регистрации свадьбы.
     * Меняется семейный статус у мужчины и женщины. Создается запись гражданского действия за дату регистрации.
    */
    public void registrationMarriage(Citizen man, Citizen woman, LocalDate date) {
        man.setFamilyStatus(FamilyStatus.MARRIED);
        man.setMarriagePartner(woman);
        woman.setFamilyStatus(FamilyStatus.MARRIED);
        woman.setMarriagePartner(man);
        addRegistry(MARRIAGE_REGISTRATION, date, man, woman);
    }

    /**3.4.3 Регистрация развода - передаются мужчина и женщина + дата регистрации развода.
     * Меняется семейный статус у мужчины и женщины. Создается запись гражданского действия за дату регистрации.*/
    public void registrationDivorce(Citizen man, Citizen woman, LocalDate date) {
        man.setFamilyStatus(FamilyStatus.DIVORCED);
        man.setMarriagePartner(null);
        woman.setFamilyStatus(FamilyStatus.DIVORCED);
        woman.setMarriagePartner(null);
        addRegistry(DIVORCE_REGISTRATION, date, man, woman);
    }

    private void addRegistry(TypeRegistry typeRegistry, LocalDate date, Citizen... citizens) {
        if (!listRegistry.containsKey(date)) {
            listRegistry.put(date, new ArrayList<>());
        }
        listRegistry.get(date).add(new WriterTypeRegistry(date, typeRegistry, citizens));
    }

    /**3.4.4 Создать метод получения статистики за дату, формат печати в консоль:
     "Статистика по ЗАГС: [имя загса]
     Дата 20/02/2023: количество свадеб - 1, количество разводов - 2, количество рождений - 5"
    */
    public void getReport() {
        System.out.printf("Статистика по ЗАГС: %s\n", name);
        listRegistry.keySet()
                 .forEach(date -> {
                     System.out.printf("Дата %s: количество свадеб - %d,  количество разводов - %d, "
                                     + "количество рождений - %d\n", date, getCount(date, MARRIAGE_REGISTRATION),
                                     getCount(date, DIVORCE_REGISTRATION), getCount(date, BIRTH_REGISTRATION));
                 });
    }

    private long getCount(LocalDate date, TypeRegistry typeRegistry) {
        return listRegistry.get(date).stream()
                .filter(writer -> typeRegistry.equals(writer.getTypeRegistry()))
                .count();
    }

}
