package ru.nsu.spirin.gamestudios.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.nsu.spirin.gamestudios.model.entity.Test;
import ru.nsu.spirin.gamestudios.model.entity.TestApp;
import ru.nsu.spirin.gamestudios.repository.TestAppRepository;
import ru.nsu.spirin.gamestudios.repository.TestRepository;

import java.util.List;

@Service
public class TestService {
    private final TestRepository testRepository;
    private final TestAppRepository testAppRepository;

    @Autowired
    public TestService(TestRepository testRepository, TestAppRepository testAppRepository) {
        this.testRepository = testRepository;
        this.testAppRepository = testAppRepository;
    }

    public List<Test> getAllTests() {
        return this.testRepository.findAll();
    }

    public List<Test> getResultedTests() {
        return this.testRepository.findAllWithResults();
    }

    public Test getTestByID(Long testID) {
        return this.testRepository.findByID(testID);
    }

    public void newTest(Test test) {
        this.testRepository.save(test);
    }

    public void updateTest(Long id, Test test) {
        this.testRepository.update(id, test);
    }

    public void addGenre(Long testID, Long genreID) {
        this.testRepository.saveTestGenre(testID, genreID);
    }

    public void removeGenre(Long testID, Long genreID) {
        this.testRepository.deleteTestGenre(testID, genreID);
    }

    public void startTest(Long id) {
        this.testAppRepository.deletePendingApps(id);
        this.testRepository.updateTestStatus(id, 1L);
    }

    public void finishTest(Long id) {
        this.testRepository.updateTestStatus(id, 2L);
    }

    public void resultsReadyTest(Long id) {
        List<TestApp> apps = this.testAppRepository.findAllByTestID(id);
        for (var app : apps) {
            if (app.getResultID() == 1) {
                this.testAppRepository.updateResult(app.getAppID(), 2L);
            }
        }
        this.testRepository.updateTestStatus(id, 3L);
    }

    public void cancelTest(Long id) {
        this.testRepository.deleteAllTestGenre(id);
        List<TestApp> apps = this.testAppRepository.findAllByTestID(id);
        for (var app : apps) {
            this.testAppRepository.deleteAllAppEmployee(app.getAppID());
            this.testAppRepository.deleteApp(app.getAppID());
        }
        this.testRepository.updateTestStatus(id, 4L);
    }

    public void deleteTest(Long id) {
        this.testRepository.deleteAllTestGenre(id);
        this.testRepository.delete(id);
    }
}
