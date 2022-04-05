package com.example.Projekt_ReadStack.domain.api.service;

import com.example.Projekt_ReadStack.domain.api.dto.ProfileUserDetails;
import com.example.Projekt_ReadStack.domain.api.dto.UserDetailsRequest;
import com.example.Projekt_ReadStack.domain.api.dto.UserRegistration;
import com.example.Projekt_ReadStack.domain.api.dto.UserRegistrationInfo;
import com.example.Projekt_ReadStack.domain.user.*;
import org.apache.commons.codec.digest.DigestUtils;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

public class UserService {
    private UserDao userDao = new UserDao();
    private UserDetailsDao userDetailsDao = new UserDetailsDao();

    public void register(UserRegistration userRegistration) {
        User userToSave = UserMapper.map(userRegistration);
        try {
            hashPasswordWithSha256(userToSave);
            userDao.save(userToSave);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUserDetails(UserDetailsRequest userDetailsRequest) {
        UserDetails userDetailsToSave = UserDetailsMapper.map(userDetailsRequest);
        int userId = getUserIdByUsername(userDetailsRequest.getUsername());
        userDetailsDao.findByUserId(userId).ifPresentOrElse(
                foundUserDetails -> userDetailsDao.update(userDetailsToSave),
                () -> userDetailsDao.create(userDetailsToSave)
        );
    }

    public UserRegistrationInfo getRegistrationInfoByUsername(String username) {
        int userId = getUserIdByUsername(username);
        String registrationDate = userDao.findById(userId).orElseThrow().getFormattedRegistrationDate();
        String accountAge =  userDao.findById(userId).orElseThrow().getFormattedAccountAge();
        return new UserRegistrationInfo(registrationDate, accountAge);
    }

    public UsernameColor findColorByUsername(String username) {
        return userDao.findByUsername(username).orElseThrow().getUsernameColor();
    }

    public Optional<ProfileUserDetails> findDetailsByUsername(String username) {
        int userId = getUserIdByUsername(username);
        return findDetailsById(userId);
    }

    private int getUserIdByUsername(String username) {
        return userDao.findByUsername(username).orElseThrow().getId();
    }

    private Optional<ProfileUserDetails> findDetailsById(int userId) {
        return userDetailsDao.findByUserId(userId).map(UserDetailsMapper::map);
    }

    private void hashPasswordWithSha256(User user) throws NoSuchAlgorithmException {
        String sha256Password = DigestUtils.sha256Hex(user.getPassword());
        user.setPassword(sha256Password);
    }

    private static class UserMapper {
        private static User map(UserRegistration userRegistration) {
            return new User(
                    userRegistration.getUsername(),
                    userRegistration.getEmail(),
                    userRegistration.getPassword(),
                    LocalDateTime.now()
            );
        }
    }

    private static class UserDetailsMapper {
        private static UserDao userDao = new UserDao();

        private static ProfileUserDetails map(UserDetails userDetails) {
            String accountAge =  userDao.findById(userDetails.getUserId()).orElseThrow().getFormattedAccountAge();
            String roleName = userDao.findById(userDetails.getUserId()).orElseThrow().getRoleName();
            String registrationDate = userDao.findById(userDetails.getUserId()).orElseThrow().getFormattedRegistrationDate();
//            UsernameColor usernameColor = userDao.findById(userDetails.getUserId()).orElseThrow().getUsernameColor();
            String gender;
            if (userDetails.getGender() != null) {
                gender = userDetails.getGender().getName();
            } else {
                gender = "";
            }

            return new ProfileUserDetails(
                    userDetails.getUserId(),
                    userDetails.getFirstName(),
                    userDetails.getLastName(),
                    gender,
                    userDetails.getAge(),
                    userDetails.getDescription(),
                    roleName
            );
        }

        private static UserDetails map(UserDetailsRequest userDetailsRequest) {
            return new UserDetails(
                    userDao.findByUsername(userDetailsRequest.getUsername()).orElseThrow().getId(),
                    userDetailsRequest.getFirstName(),
                    userDetailsRequest.getLastName(),
                    userDetailsRequest.getGender(),
                    userDetailsRequest.getAge(),
                    userDetailsRequest.getDescription()
            );
        }

        private static int calculateAccountAgeInDays(UserDetails userDetails) {
            LocalDateTime registrationDate = userDao.findById(userDetails.getUserId()).orElseThrow().getRegistrationDate();
            return Math.toIntExact(ChronoUnit.DAYS.between(registrationDate, LocalDateTime.now()));
        }
    }
}
