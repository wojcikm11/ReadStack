package com.example.Projekt_ReadStack.domain.user;

public enum UsernameColor {
    GREEN("username-green"), ORANGE("username-orange"), BLUE("username-blue");

    private String cssClassName;

    UsernameColor(String cssClassName) {
        this.cssClassName = cssClassName;
    }

    public String getCssClassName() {
        return cssClassName;
    }
}
