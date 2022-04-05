package com.example.Projekt_ReadStack.domain.api.service;

import com.example.Projekt_ReadStack.domain.api.dto.DiscoveryComment;
import com.example.Projekt_ReadStack.domain.api.dto.DiscoveryCommentRequest;
import com.example.Projekt_ReadStack.domain.comment.Comment;
import com.example.Projekt_ReadStack.domain.comment.CommentDao;
import com.example.Projekt_ReadStack.domain.user.UserDao;
import com.example.Projekt_ReadStack.domain.user.UsernameColor;

import java.time.LocalDateTime;
import java.util.List;

public class DiscoveryCommentService {
    private CommentDao commentDao = new CommentDao();

    public List<DiscoveryComment> findAllByDiscoveryId(int discoveryId) {
        return commentDao.findByDiscoveryId(discoveryId).stream().map(DiscoveryCommentMapper::map).toList();
    }

    public void save(DiscoveryCommentRequest commentRequest) {
        Comment commentToSave = DiscoveryCommentMapper.map(commentRequest);
        commentDao.save(commentToSave);
    }

    private static class DiscoveryCommentMapper {
        private static UserDao userDao = new UserDao();

        private static DiscoveryComment map(Comment comment) {
            String username = userDao.findById(comment.getUserId()).orElseThrow().getUsername();
            String content = comment.getContent();
            LocalDateTime dateAdded = comment.getDateAdded();
            UsernameColor usernameColor = userDao.findById(comment.getUserId()).orElseThrow().getUsernameColor();
            return new DiscoveryComment(username, content, dateAdded, usernameColor);
        }

        private static Comment map(DiscoveryCommentRequest comment) {
            int userId = userDao.findByUsername(comment.getAuthor()).orElseThrow().getId();
            int discoveryId = comment.getDiscoveryId();
            String content = comment.getContent();
            LocalDateTime dateAdded = LocalDateTime.now();
            return new Comment(userId, discoveryId, content, dateAdded);
        }
    }
}
