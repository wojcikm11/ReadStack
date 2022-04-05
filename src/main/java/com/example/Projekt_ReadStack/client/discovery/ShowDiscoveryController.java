package com.example.Projekt_ReadStack.client.discovery;

import com.example.Projekt_ReadStack.domain.api.dto.CategoryName;
import com.example.Projekt_ReadStack.domain.api.dto.DiscoveryBasicInfo;
import com.example.Projekt_ReadStack.domain.api.dto.DiscoveryComment;
import com.example.Projekt_ReadStack.domain.api.service.DiscoveryCommentService;
import com.example.Projekt_ReadStack.domain.api.service.DiscoveryService;
import com.example.Projekt_ReadStack.domain.api.service.DiscoveryVoteService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/discovery")
public class ShowDiscoveryController extends HttpServlet {
    DiscoveryService discoveryService = new DiscoveryService();
    DiscoveryCommentService discoveryCommentService = new DiscoveryCommentService();
    DiscoveryVoteService discoveryVoteService = new DiscoveryVoteService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int discoveryId = Integer.parseInt(request.getParameter("id"));
        DiscoveryBasicInfo discoveryToShow = discoveryService.findById(discoveryId).orElseThrow();
        request.setAttribute("discovery", discoveryToShow);
        List<DiscoveryComment> discoveryComments = discoveryCommentService.findAllByDiscoveryId(discoveryId);
        request.setAttribute("comments", discoveryComments);
        colorVote(request, discoveryToShow);

        request.getRequestDispatcher("/WEB-INF/views/discovery-details.jsp").forward(request, response);
    }

    private void colorVote(HttpServletRequest request, DiscoveryBasicInfo discoveryToShow) {
        if (request.getUserPrincipal() != null) {
            String username = request.getUserPrincipal().getName();
            discoveryVoteService.findVote(username, discoveryToShow.getId()).ifPresent(
                    vote -> discoveryToShow.setVoteType(vote.getType())
            );
        }
    }
}
