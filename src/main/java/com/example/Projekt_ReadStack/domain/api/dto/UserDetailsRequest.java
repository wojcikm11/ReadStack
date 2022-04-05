package com.example.Projekt_ReadStack.domain.api.dto;

import com.example.Projekt_ReadStack.domain.user.Gender;

public class UserDetailsRequest {
    private String username;
    private String firstName;
    private String lastName;
    private Gender gender;
    private int age;
    private String description;

    public UserDetailsRequest(String username, String firstName, String lastName, Gender gender, int age, String description) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.age = age;
        this.description = description;
    }

    public String getUsername() {
        return username;
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
}
