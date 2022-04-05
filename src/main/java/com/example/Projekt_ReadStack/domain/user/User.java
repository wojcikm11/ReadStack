package com.example.Projekt_ReadStack.domain.user;

import com.example.Projekt_ReadStack.logic.user.AccountAgeCalculator;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class User {
    private Integer id;
    private String username;
    private String email;
    private String password;
    private LocalDateTime registrationDate;
    private String roleName;

    public User(String username, String email, String password, LocalDateTime registrationDate, String roleName) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.registrationDate = registrationDate;
        this.roleName = roleName;
    }

    public User(Integer id, String username, String email, String password, LocalDateTime registrationDate, String roleName) {
        this(username, email, password, registrationDate, roleName);
        this.id = id;
    }

    public User(String username, String email, String password, LocalDateTime registrationDate) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.registrationDate = registrationDate;
    }

    public UsernameColor getUsernameColor() {
        if (this.roleName.equalsIgnoreCase("admin")) {
            return UsernameColor.BLUE;
        } else {
            int accountAge = Math.toIntExact(ChronoUnit.DAYS.between(this.registrationDate, LocalDateTime.now()));
            if (accountAge <= 30) {
                return UsernameColor.GREEN;
            } else {
                return UsernameColor.ORANGE;
            }
        }
    }

    public String getFormattedAccountAge() {
        return AccountAgeCalculator.getFormattedAccountAge(registrationDate);
    }

    public String getFormattedRegistrationDate() {
        return registrationDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }

    public Integer getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRoleName() {
        return roleName;
    }
}
