package com.example.Projekt_ReadStack.domain.api.dto;

public class CategoryFullInfo {
    private Integer id;
    private String name;
    private String description;

    public CategoryFullInfo(Integer id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
