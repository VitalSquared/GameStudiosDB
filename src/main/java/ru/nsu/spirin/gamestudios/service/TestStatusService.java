package ru.nsu.spirin.gamestudios.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.nsu.spirin.gamestudios.model.entity.TestStatus;
import ru.nsu.spirin.gamestudios.repository.TestStatusRepository;

import java.util.List;

@Service
public class TestStatusService {
    private final TestStatusRepository testStatusRepository;

    @Autowired
    public TestStatusService(TestStatusRepository testStatusRepository) {
        this.testStatusRepository = testStatusRepository;
    }

    public List<TestStatus> getAllStatuses() {
        return this.testStatusRepository.findAll();
    }

    public TestStatus getStatusByID(Long statusID) {
        return this.testStatusRepository.findByID(statusID);
    }
}
