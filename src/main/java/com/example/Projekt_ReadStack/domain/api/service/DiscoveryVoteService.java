package com.example.Projekt_ReadStack.domain.api.service;

import com.example.Projekt_ReadStack.domain.api.dto.DiscoveryVote;
import com.example.Projekt_ReadStack.domain.discovery.Discovery;
import com.example.Projekt_ReadStack.domain.user.User;
import com.example.Projekt_ReadStack.domain.user.UserDao;
import com.example.Projekt_ReadStack.domain.vote.Vote;
import com.example.Projekt_ReadStack.domain.vote.VoteDao;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class DiscoveryVoteService {
    private VoteDao voteDao = new VoteDao();
    private UserDao userDao = new UserDao();

    public void manageVote(DiscoveryVote discoveryVote) {
        int userId = userDao.findByUsername(discoveryVote.getUsername()).orElseThrow().getId();
        if (newVote(discoveryVote, userId) || differentVote(discoveryVote, userId)) {
            addVote(discoveryVote);
        } else {
            removeVote(discoveryVote);
        }
    }

    public List<DiscoveryVote> findVotesByUsername(String username) {
        int userId = userDao.findByUsername(username).orElseThrow().getId();
        return voteDao.findVotesByUserId(userId).stream().map(VoteMapper::map).toList();
    }

    private boolean newVote(DiscoveryVote discoveryVote, int userId) {
        return voteDao.findVote(userId, discoveryVote.getDiscoveryId()).isEmpty();
    }

    private boolean differentVote(DiscoveryVote discoveryVote, int userId) {
        Vote.Type oldType = voteDao.findVote(userId, discoveryVote.getDiscoveryId()).get().getType();
        Vote.Type newType = Vote.Type.valueOf(discoveryVote.getType());
        return !oldType.equals(newType);
    }

    private void addVote(DiscoveryVote vote) {
        Vote voteToSave = VoteMapper.map(vote);
        voteDao.save(voteToSave);
    }

    private void removeVote(DiscoveryVote vote) {
        Vote voteToRemove = VoteMapper.map(vote);
        voteDao.remove(voteToRemove);
    }

    public static class VoteMapper {
        private static final UserDao userDao = new UserDao();

        public static Vote map(DiscoveryVote vote) {
            Optional<User> user = userDao.findByUsername(vote.getUsername());
            return new Vote(
                    user.orElseThrow().getId(),
                    vote.getDiscoveryId(),
                    Vote.Type.valueOf(vote.getType()),
                    LocalDateTime.now()
            );
        }

        public static DiscoveryVote map(Vote vote) {
            Optional<User> user = userDao.findById(vote.getUserId());
            return new DiscoveryVote(
                    user.orElseThrow().getUsername(),
                    vote.getDiscoveryId(),
                    vote.getType().toString()
            );
        }
    }
}
