package com.example.Projekt_ReadStack.client.vote;

import com.example.Projekt_ReadStack.domain.api.dto.DiscoveryVote;
import com.example.Projekt_ReadStack.domain.api.service.DiscoveryVoteService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.HttpMethodConstraint;
import jakarta.servlet.annotation.ServletSecurity;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/discovery/vote")
@ServletSecurity(
        httpMethodConstraints = {
                @HttpMethodConstraint(value = "GET", rolesAllowed = "USER")
        }
)
public class DiscoveryVoteController extends HttpServlet {
    private DiscoveryVoteService voteService = new DiscoveryVoteService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DiscoveryVote discoveryVote = createDiscoveryVote(request);
        voteService.manageVote(discoveryVote);
        response.sendRedirect(request.getContextPath());
    }

    private DiscoveryVote createDiscoveryVote(HttpServletRequest request) {
        String username = request.getUserPrincipal().getName();
        int discoveryId = Integer.valueOf(request.getParameter("id"));
        String voteType = request.getParameter("type");
        return new DiscoveryVote(username, discoveryId, voteType);
    }
}

