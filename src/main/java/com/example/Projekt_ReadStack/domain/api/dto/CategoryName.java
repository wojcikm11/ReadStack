package com.example.Projekt_ReadStack.domain.api.dto;

public class CategoryName {
    private Integer id;
    private String name;

    public CategoryName(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
