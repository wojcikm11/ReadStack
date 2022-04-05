package com.example.Projekt_ReadStack.logic.user;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class AccountAgeCalculator {
    public static String getFormattedAccountAge(LocalDateTime registrationDate) {
        LocalDateTime temporaryDate = registrationDate;
        LocalDateTime currentTime = LocalDateTime.now();

        long years = ChronoUnit.YEARS.between(temporaryDate, currentTime);
        temporaryDate = temporaryDate.plusYears(years);
        long months = ChronoUnit.MONTHS.between(temporaryDate, currentTime);
        temporaryDate = temporaryDate.plusMonths(months);
        long days = ChronoUnit.DAYS.between(temporaryDate, currentTime);
        temporaryDate = temporaryDate.plusDays(days);
        long hours = ChronoUnit.HOURS.between(temporaryDate, currentTime);
        temporaryDate = temporaryDate.plusHours(hours);
        long minutes = ChronoUnit.MINUTES.between(temporaryDate, currentTime);

        String formattedAccountAge;
        if (hours < 1) {
            formattedAccountAge = minutes + " minuty ";
        } else if (days < 1) {
            formattedAccountAge = hours + " godziny " + minutes + " minuty ";
        } else if (months < 1) {
            formattedAccountAge = days + " dni " + hours + " godziny ";
        } else if (years < 1) {
            formattedAccountAge = months + " miesięcy " + days + " dni ";
        } else {
            formattedAccountAge = years + "";
            if (years == 1) {
                formattedAccountAge += " rok ";
            } else {
                formattedAccountAge += " lata ";
            }
            formattedAccountAge += months + " miesiące " + days + " dni ";
        }
        return formattedAccountAge;
    }
}
