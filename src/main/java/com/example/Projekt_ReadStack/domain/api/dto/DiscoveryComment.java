package com.example.Projekt_ReadStack.domain.api.dto;

import com.example.Projekt_ReadStack.domain.user.UsernameColor;

import java.time.LocalDateTime;

public class DiscoveryComment {
    private String username;
    private String content;
    private LocalDateTime dateAdded;
    private UsernameColor usernameColor;

    public DiscoveryComment(String username, String content, LocalDateTime dateAdded, UsernameColor usernameColor) {
        this.username = username;
        this.content = content;
        this.dateAdded = dateAdded;
        this.usernameColor = usernameColor;
    }

    public String getUsername() {
        return username;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getDateAdded() {
        return dateAdded;
    }

    public UsernameColor getUsernameColor() {
        return usernameColor;
    }
}
