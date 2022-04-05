package com.example.Projekt_ReadStack.domain.api.service;

import com.example.Projekt_ReadStack.domain.api.dto.DiscoveryBasicInfo;
import com.example.Projekt_ReadStack.domain.api.dto.DiscoverySaveRequest;
import com.example.Projekt_ReadStack.domain.api.dto.DiscoveryVote;
import com.example.Projekt_ReadStack.domain.comment.CommentDao;
import com.example.Projekt_ReadStack.domain.discovery.Discovery;
import com.example.Projekt_ReadStack.domain.discovery.DiscoveryDao;
import com.example.Projekt_ReadStack.domain.user.User;
import com.example.Projekt_ReadStack.domain.user.UserDao;
import com.example.Projekt_ReadStack.domain.user.UserDetailsDao;
import com.example.Projekt_ReadStack.domain.user.UsernameColor;
import com.example.Projekt_ReadStack.domain.vote.VoteDao;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class DiscoveryService {
    private final DiscoveryDao discoveryDao = new DiscoveryDao();
    private final UserDao userDao = new UserDao();
    private final VoteDao voteDao = new VoteDao();

    public void add(DiscoverySaveRequest discoverySaveRequest) {
        Discovery discoveryToSave = DiscoveryMapper.map(discoverySaveRequest);
        discoveryDao.save(discoveryToSave);
    }

    public Optional<DiscoveryBasicInfo> findById(int discoveryId) {
        return discoveryDao.findById(discoveryId).map(DiscoveryMapper::map);
    }

    public List<DiscoveryBasicInfo> findAll() {
        return discoveryDao.findAll().stream().map(DiscoveryMapper::map).toList();
    }

    public List<DiscoveryBasicInfo> findAllByCategory(int categoryId) {
        return discoveryDao.findByCategory(categoryId).stream().map(DiscoveryMapper::map).toList();
    }

    public List<DiscoveryBasicInfo> findAllByUsername(String username) {
        int userId = userDao.findByUsername(username).orElseThrow().getId();
        return discoveryDao.findByUserId(userId).stream().map(DiscoveryMapper::map).toList();
    }

    public void colorVotes(String username, List<DiscoveryBasicInfo> discoveries) {
        int userId = userDao.findByUsername(username).orElseThrow().getId();
        List<DiscoveryVote> votesByUserId = voteDao.findVotesByUserId(userId).stream().map(DiscoveryVoteService.VoteMapper::map).toList();
        for (DiscoveryBasicInfo discovery : discoveries) {
            for (DiscoveryVote userDiscoveryVote : votesByUserId) {
                if (discovery.getId().equals(userDiscoveryVote.getDiscoveryId())) {
                    discovery.setVoteType(userDiscoveryVote.getType());
                    break;
                }
            }
        }
    }

    private static class DiscoveryMapper {
        private static final UserDao userDao = new UserDao();
        private static final VoteDao voteDao = new VoteDao();
        private static final CommentDao commentDao = new CommentDao();

        private static DiscoveryBasicInfo map(Discovery discovery) {
            Integer id = discovery.getId();
            String title = discovery.getTitle();
            String url = discovery.getUrl();
            String description = discovery.getDescription();;
            LocalDateTime dateAdded = discovery.getDateAdded();
            int voteCount = voteDao.countByDiscoveryId(discovery.getId());
            int commentCount = commentDao.countByDiscoveryId(discovery.getId());
            String author = userDao.findById(discovery.getUserId()).orElseThrow().getUsername();
            UsernameColor usernameColor = userDao.findById(discovery.getUserId()).orElseThrow().getUsernameColor();
            return new DiscoveryBasicInfo(id, title, url, description, dateAdded, voteCount, commentCount, author, usernameColor);
        }

        private static Discovery map(DiscoverySaveRequest ds) {
            return new Discovery(
                ds.getTitle(),
                ds.getUrl(),
                ds.getDescription(),
                LocalDateTime.now(),
                ds.getCategoryId(),
                userDao.findByUsername(ds.getAuthor()).map(User::getId).orElseThrow()
            );
        }
    }
}
