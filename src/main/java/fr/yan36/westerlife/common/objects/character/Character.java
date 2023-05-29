package fr.yan36.westerlife.common.objects.character;

import fr.yan36.westerlife.common.objects.justice.Conviction;

import java.util.ArrayList;
import java.util.List;

public class Character {
    public enum Gender { MALE, FEMALE }

    private String firstNames;
    private String lastName;
    private String nationality;
    private Gender gender;
    private String birthPlace;
    private String birthDate;

    private List<Diploma> diplomas;
    private List<Conviction> convictions;
    //WIP: private List<Job> jobs;
    private List<Permis> permis;
    //WIP: private List<Car> personalCars;

    public Character(String firstNames, String lastName, String nationality, Gender gender, String birthPlace, String birthDate, List<Diploma> diplomas, List<Permis> permis) {
        this.firstNames = firstNames;
        this.lastName = lastName;
        this.nationality = nationality;
        this.gender = gender;
        this.birthPlace = birthPlace;
        this.birthDate = birthDate;
        this.diplomas = diplomas;
        this.permis = permis;
    }



}
