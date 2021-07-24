package com.example.Projekt_ReadStack.client.category;

import com.example.Projekt_ReadStack.domain.api.dto.CategoryFullInfo;
import com.example.Projekt_ReadStack.domain.api.dto.DiscoveryBasicInfo;
import com.example.Projekt_ReadStack.domain.api.service.CategoryService;
import com.example.Projekt_ReadStack.domain.api.service.DiscoveryService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/category")
public class CategoryController extends HttpServlet {
    private DiscoveryService discoveryService = new DiscoveryService();
    private CategoryService categoryService = new CategoryService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int categoryId = Integer.parseInt(request.getParameter("id"));
        CategoryFullInfo category = categoryService.findById(categoryId).orElseThrow();
        List<DiscoveryBasicInfo> discoveries = discoveryService.findAllByCategory(categoryId);
        request.setAttribute("category", category);
        request.setAttribute("discoveries", discoveries);
        request.getRequestDispatcher("/WEB-INF/views/category.jsp").forward(request, response);
    }
}