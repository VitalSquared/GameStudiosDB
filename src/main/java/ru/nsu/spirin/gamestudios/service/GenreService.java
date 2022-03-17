package ru.nsu.spirin.gamestudios.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.nsu.spirin.gamestudios.model.entity.Genre;
import ru.nsu.spirin.gamestudios.repository.GameRepository;
import ru.nsu.spirin.gamestudios.repository.GenreRepository;
import ru.nsu.spirin.gamestudios.repository.TestRepository;

import java.util.List;

@Service
public class GenreService {
    private final GenreRepository genreRepository;
    private final TestRepository testRepository;
    private final GameRepository gameRepository;

    @Autowired
    public GenreService(GenreRepository genreRepository, TestRepository testRepository, GameRepository gameRepository) {
        this.genreRepository = genreRepository;
        this.testRepository = testRepository;
        this.gameRepository = gameRepository;
    }

    public List<Genre> getAllGenres() {
        return this.genreRepository.findAll();
    }

    public Genre getGenreByID(Long genreID) {
        return this.genreRepository.findByID(genreID);
    }

    public List<Genre> getGenresByGameID(Long gameID) {
        return this.genreRepository.findAllByGameID(gameID);
    }

    public void createNewGenre(Genre genre) {
        if (genre.getName() == null) {
            return;
        }
        this.genreRepository.save(genre);
    }

    public void updateGenre(Long genreID, Genre genre) {
        if (genre.getName() == null) {
            return;
        }
        this.genreRepository.update(genreID, genre);
    }

    public List<Genre> getGenresByTestID(Long testID) {
        return this.genreRepository.findAllByTestID(testID);
    }

    public void deleteGenre(Long genreID) {
        this.testRepository.deleteAllTestGenreByGenreID(genreID);
        this.gameRepository.deleteAllGameGenreByGenreID(genreID);
        this.genreRepository.delete(genreID);
    }
}
