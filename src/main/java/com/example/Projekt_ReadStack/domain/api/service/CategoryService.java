package com.example.Projekt_ReadStack.domain.api.service;

import com.example.Projekt_ReadStack.domain.api.dto.CategoryFullInfo;
import com.example.Projekt_ReadStack.domain.api.dto.CategoryName;
import com.example.Projekt_ReadStack.domain.category.Category;
import com.example.Projekt_ReadStack.domain.category.CategoryDao;

import java.util.List;
import java.util.Optional;

public class CategoryService {
    private final CategoryDao categoryDao = new CategoryDao();

    public Optional<CategoryFullInfo> findById(int categoryId) {
        return categoryDao.findById(categoryId).map(CategoryFullInfoMapper::map);
    }

    public List<CategoryName> findAllCategoryNames() {
        return categoryDao.findAll().stream().map(CategoryNameMapper::map).toList();
    }

    private static class CategoryNameMapper {
        private static CategoryName map(Category category) {
            Integer id = category.getId();
            String name = category.getName();
            return new CategoryName(id, name);
        }
    }

    private static class CategoryFullInfoMapper {
        private static CategoryFullInfo map(Category category) {
            Integer id = category.getId();
            String name = category.getName();
            String description = category.getDescription();
            return new CategoryFullInfo(id, name, description);
        }
    }
}
