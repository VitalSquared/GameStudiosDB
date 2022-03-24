package ru.nsu.spirin.gamestudios.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.nsu.spirin.gamestudios.model.entity.GameRelease;
import ru.nsu.spirin.gamestudios.model.entity.Platform;
import ru.nsu.spirin.gamestudios.repository.GameReleaseRepository;
import ru.nsu.spirin.gamestudios.repository.PlatformRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlatformService {
    private final PlatformRepository platformRepository;
    private final GameReleaseRepository gameReleaseRepository;

    @Autowired
    public PlatformService(PlatformRepository platformRepository, GameReleaseRepository gameReleaseRepository) {
        this.platformRepository = platformRepository;
        this.gameReleaseRepository = gameReleaseRepository;
    }

    public List<Platform> getAllPlatforms() {
        return this.platformRepository.findAll();
    }

    public List<Platform> getAllPlatformsExceptGame(Long gameID) {
        List<GameRelease> releases = this.gameReleaseRepository.findAllByGameID(gameID);
        List<Long> platforms = releases.stream().map(GameRelease::getPlatformID).collect(Collectors.toList());
        List<Platform> all = this.platformRepository.findAll();
        return all.stream().filter(x -> !platforms.contains(x.getPlatformID())).collect(Collectors.toList());
    }

    public Platform getPlatformByID(Long platformID) {
        return this.platformRepository.findByID(platformID);
    }

    public void createNewPlatform(Platform platform) {
        if (platform.getName() == null || platform.getPercent() == null || platform.getPercent() < 0 || platform.getPercent() > 100) {
            return;
        }
        this.platformRepository.save(platform);
    }

    public void updatePlatform(Long platformID, Platform platform) {
        if (platform.getName() == null || platform.getPercent() == null || platform.getPercent() < 0 || platform.getPercent() > 100) {
            return;
        }
        this.platformRepository.update(platformID, platform);
    }

    public void deletePlatform(Long platformID) {
        this.gameReleaseRepository.deleteAllByPlatformID(platformID);
        this.platformRepository.delete(platformID);
    }
}
