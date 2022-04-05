package com.example.Projekt_ReadStack.client.profile;

import com.example.Projekt_ReadStack.domain.api.dto.ProfileUserDetails;
import com.example.Projekt_ReadStack.domain.api.dto.UserDetailsRequest;
import com.example.Projekt_ReadStack.domain.api.service.UserService;
import com.example.Projekt_ReadStack.domain.user.Gender;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.HttpMethodConstraint;
import jakarta.servlet.annotation.ServletSecurity;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Optional;

@WebServlet("/profile/edit")
@ServletSecurity(
        httpMethodConstraints = {
                @HttpMethodConstraint(value = "GET", rolesAllowed = {"USER", "ADMIN"}),
                @HttpMethodConstraint(value = "POST", rolesAllowed = {"USER", "ADMIN"})
        }
)
public class EditProfileController extends HttpServlet {
    UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        showForm(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        Gender gender = null;
        if (request.getParameter("gender") != null) {
            gender = Gender.valueOf(request.getParameter("gender"));
        }
        int age;
        if (request.getParameter("age").isBlank()) {
            age = 0;
        } else {
            age = Integer.parseInt(request.getParameter("age"));
        }
        String description = request.getParameter("description");
        if (incorrectAge(age)) {
            String message = "Podano zły wiek, spróbuj ponownie";
            request.setAttribute("incorrectAge", message);
            doGet(request, response);
//            showForm(request, response);
        } else {
            String username = request.getUserPrincipal().getName();
            UserDetailsRequest userDetails = new UserDetailsRequest(username, firstName, lastName, gender, age, description);
            userService.saveUserDetails(userDetails);
            response.sendRedirect(request.getContextPath() + "/profile?username=" + username);
        }
    }

    private void showForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String loggedUser = request.getUserPrincipal().getName();
        Optional<ProfileUserDetails> userDetails = userService.findDetailsByUsername(loggedUser);
        userDetails.ifPresent(profileUserDetails -> request.setAttribute("userDetails", profileUserDetails));
        request.setAttribute("genders", Gender.values());
        request.getRequestDispatcher("/WEB-INF/views/edit-profile.jsp").forward(request, response);
    }

    private boolean incorrectAge(int age) {
        return age < 0 || age >= 150;
    }
}
