package com.example.Projekt_ReadStack.domain.api.dto;

import com.example.Projekt_ReadStack.domain.user.UsernameColor;

import java.time.LocalDateTime;

public class DiscoveryBasicInfo {
    private Integer id;
    private String title;
    private String url;
    private String description;
    private LocalDateTime dateAdded;
    private int voteCount;
    private int commentCount;
    private String author;
    private String voteType;
    private UsernameColor authorColor;

    public DiscoveryBasicInfo(Integer id, String title, String url, String description, LocalDateTime dateAdded,
                              int voteCount, int commentCount, String author, UsernameColor authorColor) {
        this.id = id;
        this.title = title;
        this.url = url;
        this.description = description;
        this.dateAdded = dateAdded;
        this.voteCount = voteCount;
        this.commentCount = commentCount;
        this.author = author;
        this.authorColor = authorColor;
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getDateAdded() {
        return dateAdded;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public String getAuthor() {
        return author;
    }

    public String getVoteType() {
        return voteType;
    }

    public void setVoteType(String voteType) {
        this.voteType = voteType;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public UsernameColor getAuthorColor() {
        return authorColor;
    }
}
