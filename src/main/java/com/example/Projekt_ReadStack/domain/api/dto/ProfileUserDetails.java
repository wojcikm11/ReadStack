package com.example.Projekt_ReadStack.domain.api.dto;

import com.example.Projekt_ReadStack.domain.user.UsernameColor;

import java.time.LocalDateTime;

public class ProfileUserDetails {
    private int userId;
    private String firstName;
    private String lastName;
    private String gender;
    private int age;
    private String description;
    private String userRole;

    public ProfileUserDetails(int userId, String firstName, String lastName, String gender, int age, String description,
                              String userRole) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.age = age;
        this.description = description;
        this.userRole = userRole;
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

    public String getGender() {
        return gender;
    }

    public int getAge() {
        return age;
    }

    public String getDescription() {
        return description;
    }

    public String getUserRole() {
        return userRole;
    }
}
