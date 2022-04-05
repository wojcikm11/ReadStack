package com.example.Projekt_ReadStack.client.discovery.comment;

import com.example.Projekt_ReadStack.domain.api.dto.DiscoveryBasicInfo;
import com.example.Projekt_ReadStack.domain.api.dto.DiscoveryComment;
import com.example.Projekt_ReadStack.domain.api.dto.DiscoveryCommentRequest;
import com.example.Projekt_ReadStack.domain.api.service.DiscoveryCommentService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.HttpMethodConstraint;
import jakarta.servlet.annotation.ServletSecurity;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/discovery/add_comment")
@ServletSecurity(
        httpMethodConstraints = {
                @HttpMethodConstraint(value = "GET", rolesAllowed = {"USER", "ADMIN"}),
                @HttpMethodConstraint(value = "POST", rolesAllowed = {"USER", "ADMIN"})
        }
)
public class AddDiscoveryCommentController extends HttpServlet {
    DiscoveryCommentService discoveryCommentService = new DiscoveryCommentService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int discoveryId = Integer.parseInt(request.getParameter("id"));
        String author = request.getUserPrincipal().getName();
        String commentContent = request.getParameter("comment");
        if (!commentContent.isBlank()) {
            DiscoveryCommentRequest addedComment = new DiscoveryCommentRequest(discoveryId, author, commentContent);
            discoveryCommentService.save(addedComment);
        }
        response.sendRedirect(request.getContextPath() + "/discovery?id=" + discoveryId);
    }
}
