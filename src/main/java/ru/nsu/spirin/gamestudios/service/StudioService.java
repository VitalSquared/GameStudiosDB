package ru.nsu.spirin.gamestudios.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.nsu.spirin.gamestudios.model.entity.Studio;
import ru.nsu.spirin.gamestudios.repository.StudioRepository;

import java.sql.SQLException;
import java.util.List;

@Service
public class StudioService {
    private final StudioRepository studioRepository;

    @Autowired
    public StudioService(StudioRepository studioRepository) {
        this.studioRepository = studioRepository;
    }

    public Studio getStudioByID(Long studioID) {
        return this.studioRepository.findByID(studioID);
    }

    public List<Studio> getStudiosListByID(Long studioID) {
        return this.studioRepository.findAllByID(studioID);
    }

    public List<Studio> getAllStudios() {
        return this.studioRepository.findAll();
    }

    public void newStudio(Studio studio) throws SQLException {
        if (studio.getName() == null || studio.getAddress() == null) {
            return;
        }
        this.studioRepository.save(studio);
    }

    public void updateStudio(Long id, Studio studio) throws SQLException {
        if (studio.getAddress() == null || studio.getName() == null) {
            return;
        }
        this.studioRepository.update(id, studio);
    }

    public void removeStudio(Long studioID) throws SQLException {
        this.studioRepository.delete(studioID);
    }
}
