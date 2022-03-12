package ru.nsu.spirin.gamestudios.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.nsu.spirin.gamestudios.model.entity.GameRelease;
import ru.nsu.spirin.gamestudios.repository.GameReleaseRepository;

import java.util.List;

@Service
public class GameReleaseService {
    private final GameReleaseRepository gameReleaseRepository;

    @Autowired
    public GameReleaseService(GameReleaseRepository gameReleaseRepository) {
        this.gameReleaseRepository = gameReleaseRepository;
    }

    public List<GameRelease> getReleasesByGameID(Long gameID) {
        return this.gameReleaseRepository.findAllByGameID(gameID);
    }

    public void addRelease(Long gameID, GameRelease gameRelease) {
       this.gameReleaseRepository.save(gameID, gameRelease);
    }

    public GameRelease getReleaseByGameAndPlatform(Long gameID, Long platformID) {
        return this.gameReleaseRepository.findByGameIDAndPlatformID(gameID, platformID);
    }

    public void updateRelease(Long gameID, Long platformID, GameRelease release) {
        this.gameReleaseRepository.update(gameID, platformID, release);
    }
}
