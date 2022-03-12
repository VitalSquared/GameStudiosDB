package ru.nsu.spirin.gamestudios.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.nsu.spirin.gamestudios.model.entity.Genre;
import ru.nsu.spirin.gamestudios.repository.GenreRepository;

import java.util.List;

@Service
public class GenreService {
    private final GenreRepository genreRepository;

    @Autowired
    public GenreService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    public List<Genre> getAllGenres() {
        return this.genreRepository.findAll();
    }

    public Genre getGenreByID(Long genreID) {
        return this.genreRepository.findByID(genreID);
    }

    public List<Genre> getGenresByGameID(Long gameID) {
        return this.genreRepository.getGenresByGameID(gameID);
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
        return this.genreRepository.getGenresByTestID(testID);
    }
}
