package ru.nsu.spirin.gamestudios.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.nsu.spirin.gamestudios.model.entity.Game;
import ru.nsu.spirin.gamestudios.repository.GameRepository;

import java.util.List;

@Service
public class GameService {
    private final GameRepository gameRepository;

    @Autowired
    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public List<Game> getAllGames() {
        return this.gameRepository.findAll();
    }

    public Game getGameByID(Long gameID) {
        return this.gameRepository.findByID(gameID);
    }

    public void newGame(Game game) {
        this.gameRepository.save(game);
    }

    public void updateGame(Long id, Game game) {
        this.gameRepository.update(id, game);
    }

    public List<Game> getGamesByContractID(Long contractID) {
        return this.gameRepository.findAllByContractID(contractID);
    }

    public void addGenre(Long gameID, Long genreID) {
        this.gameRepository.saveGameGenre(gameID, genreID);
    }

    public void removeGenreFromGame(Long gameID, Long genreID) {
        this.gameRepository.deleteGameGenre(gameID, genreID);
    }

    public void addEmployee(Long gameID, Long employeeID) {
        this.gameRepository.saveGameEmployee(gameID, employeeID);
    }

    public void removeEmployeeFromGame(Long gameID, Long employeeID) {
        this.gameRepository.deleteGameEmployee(gameID, employeeID);
    }
}
