package com.example.Projekt_ReadStack.domain.comment;

import java.time.LocalDateTime;

public class Comment {
    private Integer id;
    private Integer userId;
    private Integer discoveryId;
    private String content;
    private LocalDateTime dateAdded;

    public Comment(Integer userId, Integer discoveryId, String content, LocalDateTime dateAdded) {
        this.userId = userId;
        this.discoveryId = discoveryId;
        this.content = content;
        this.dateAdded = dateAdded;
    }

    public Comment(Integer id, Integer userId, Integer discoveryId, String content, LocalDateTime dateAdded) {
        this(userId, discoveryId, content, dateAdded);
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public Integer getUserId() {
        return userId;
    }

    public Integer getDiscoveryId() {
        return discoveryId;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getDateAdded() {
        return dateAdded;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
