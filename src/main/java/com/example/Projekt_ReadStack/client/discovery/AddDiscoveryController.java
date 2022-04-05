package com.example.Projekt_ReadStack.client.discovery;

import com.example.Projekt_ReadStack.domain.api.dto.CategoryName;
import com.example.Projekt_ReadStack.domain.api.dto.DiscoverySaveRequest;
import com.example.Projekt_ReadStack.domain.api.service.CategoryService;
import com.example.Projekt_ReadStack.domain.api.service.DiscoveryService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.HttpMethodConstraint;
import jakarta.servlet.annotation.ServletSecurity;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/discovery/add")
@ServletSecurity(
        httpMethodConstraints = {
                @HttpMethodConstraint(value = "GET", rolesAllowed = {"USER", "ADMIN"}),
                @HttpMethodConstraint(value = "POST", rolesAllowed = {"USER", "ADMIN"})
        }
)
public class AddDiscoveryController extends HttpServlet {
    private DiscoveryService discoveryService = new DiscoveryService();
    private CategoryService categoryService = new CategoryService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<CategoryName> categories = categoryService.findAllCategoryNames();
        request.setAttribute("categories", categories);
        request.getRequestDispatcher("/WEB-INF/views/add-discovery.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DiscoverySaveRequest discoverySaveRequest = createSaveRequest(request);
        discoveryService.add(discoverySaveRequest);
        response.sendRedirect(request.getContextPath());
    }

    private DiscoverySaveRequest createSaveRequest(HttpServletRequest request) {
        String loggedInUsername = request.getUserPrincipal().getName();
        String title = request.getParameter("title");
        String url = request.getParameter("url");
        String description = request.getParameter("description");
        int categoryId = Integer.valueOf(request.getParameter("categoryId"));
        return new DiscoverySaveRequest(title, url, description, categoryId, loggedInUsername);
    }
}
