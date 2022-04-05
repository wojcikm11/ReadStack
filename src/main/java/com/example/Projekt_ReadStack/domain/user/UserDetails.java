package com.example.Projekt_ReadStack.domain.user;

public class UserDetails {
    private int id;
    private int userId;
    private String firstName;
    private String lastName;
    private Gender gender;
    private int age;
    private String description;

    public UserDetails(int userId, String firstName, String lastName, Gender gender, int age, String description) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.age = age;
        this.description = description;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public UserDetails(int id, int userId, String firstName, String lastName, Gender gender, int age, String description) {
        this(userId, firstName, lastName, gender, age, description);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Gender getGender() {
        return gender;
    }

    public int getAge() {
        return age;
    }

    public String getDescription() {
        return description;
    }

    public void setId(int id) {
        this.id = id;
    }
}