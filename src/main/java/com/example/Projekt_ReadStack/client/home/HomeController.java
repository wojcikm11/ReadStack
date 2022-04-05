package com.example.Projekt_ReadStack.client.home;

import com.example.Projekt_ReadStack.domain.api.dto.CategoryName;
import com.example.Projekt_ReadStack.domain.api.service.CategoryService;
import com.example.Projekt_ReadStack.domain.api.dto.DiscoveryBasicInfo;
import com.example.Projekt_ReadStack.domain.api.service.DiscoveryService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("")
public class HomeController extends HttpServlet {
    private DiscoveryService discoveryService = new DiscoveryService();
    private CategoryService categoryService = new CategoryService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<DiscoveryBasicInfo> discoveries = discoveryService.findAll();
        colorVotes(request, discoveries);
        List<CategoryName> categories = categoryService.findAllCategoryNames();

        request.setAttribute("discoveries", discoveries);
        request.setAttribute("categories", categories);
        request.getRequestDispatcher("/WEB-INF/views/index.jsp").forward(request, response);
    }

    private void colorVotes(HttpServletRequest request, List<DiscoveryBasicInfo> discoveries) {
        if (request.getUserPrincipal() != null) {
            String username = request.getUserPrincipal().getName();
            discoveryService.colorVotes(username, discoveries);
        }
    }
}
