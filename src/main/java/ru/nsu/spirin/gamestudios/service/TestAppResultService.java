package ru.nsu.spirin.gamestudios.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.nsu.spirin.gamestudios.model.entity.TestAppResult;
import ru.nsu.spirin.gamestudios.repository.TestAppResultRepository;

import java.util.List;

@Service
public class TestAppResultService {
    private final TestAppResultRepository testAppResultRepository;

    @Autowired
    public TestAppResultService(TestAppResultRepository testAppResultRepository) {
        this.testAppResultRepository = testAppResultRepository;
    }

    public List<TestAppResult> getAllResults() {
        return this.testAppResultRepository.findAll();
    }

    public TestAppResult getResultByID(Long resultID) {
        return this.testAppResultRepository.findByID(resultID);
    }
}
