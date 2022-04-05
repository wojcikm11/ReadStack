package com.example.Projekt_ReadStack.domain.api.dto;

public class DiscoveryCommentRequest {
    private int discoveryId;
    private String author;
    private String content;

    public DiscoveryCommentRequest(int discoveryId, String author, String content) {
        this.discoveryId = discoveryId;
        this.author = author;
        this.content = content;
    }

    public int getDiscoveryId() {
        return discoveryId;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }
}
