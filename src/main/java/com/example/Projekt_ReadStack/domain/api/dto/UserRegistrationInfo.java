package com.example.Projekt_ReadStack.domain.api.dto;

public class UserRegistrationInfo {
    private String registrationDate;
    private String accountAge;

    public UserRegistrationInfo(String registrationDate, String accountAge) {
        this.registrationDate = registrationDate;
        this.accountAge = accountAge;
    }

    public String getRegistrationDate() {
        return registrationDate;
    }

    public String getAccountAge() {
        return accountAge;
    }
}
