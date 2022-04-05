package com.example.Projekt_ReadStack.client.profile;

import com.example.Projekt_ReadStack.domain.api.dto.DiscoveryBasicInfo;
import com.example.Projekt_ReadStack.domain.api.dto.ProfileUserDetails;
import com.example.Projekt_ReadStack.domain.api.dto.UserDetailsRequest;
import com.example.Projekt_ReadStack.domain.api.dto.UserRegistrationInfo;
import com.example.Projekt_ReadStack.domain.api.service.DiscoveryService;
import com.example.Projekt_ReadStack.domain.api.service.UserService;
import com.example.Projekt_ReadStack.domain.discovery.DiscoveryDao;
import com.example.Projekt_ReadStack.domain.user.Gender;
import com.example.Projekt_ReadStack.domain.user.UsernameColor;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@WebServlet("/profile")
public class UserProfileController extends HttpServlet {
    UserService userService = new UserService();
    DiscoveryService discoveryService = new DiscoveryService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        request.setAttribute("username", username);
        UsernameColor usernameColor = userService.findColorByUsername(username);
        request.setAttribute("usernameColor", usernameColor);
        UserRegistrationInfo registrationInfo = userService.getRegistrationInfoByUsername(username);
        request.setAttribute("registrationInfo", registrationInfo);
        userService.findDetailsByUsername(username).ifPresent(profileUserDetails -> request.setAttribute("userDetails", profileUserDetails));
        List<DiscoveryBasicInfo> userDiscoveries = discoveryService.findAllByUsername(username);
        if (request.getUserPrincipal() != null) {
            discoveryService.colorVotes(request.getUserPrincipal().getName(), userDiscoveries);
        }
        request.setAttribute("discoveries", userDiscoveries);
        request.getRequestDispatcher("/WEB-INF/views/user-profile.jsp").forward(request, response);
    }
}
