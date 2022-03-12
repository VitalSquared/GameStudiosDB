package ru.nsu.spirin.gamestudios.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.nsu.spirin.gamestudios.model.entity.TestApp;
import ru.nsu.spirin.gamestudios.repository.TestAppRepository;

import java.util.List;

@Service
public class TestAppService {
    private final TestAppRepository testAppRepository;

    @Autowired
    public TestAppService(TestAppRepository testAppRepository) {
        this.testAppRepository = testAppRepository;
    }

    public List<TestApp> getAppsForTest(Long testID) {
        return this.testAppRepository.findAllByTestID(testID);
    }

    public TestApp getAppByID(Long appID) {
        return this.testAppRepository.findByID(appID);
    }

    public void acceptApp(Long appID) {
        this.testAppRepository.updateResult(appID, 1L);
    }

    public void declineApp(Long appID) {
        this.testAppRepository.deleteAllAppEmployee(appID);
        this.testAppRepository.deleteApp(appID);
    }

    public void addEmployee(Long appID, Long employeeID) {
        this.testAppRepository.saveAppEmployee(appID, employeeID);
    }

    public void removeEmployee(Long appID, Long employeeID) {
        this.testAppRepository.deleteAppEmployee(appID, employeeID);
    }

    public void successEndResult(Long appID) {
        this.testAppRepository.updateResult(appID, 3L);
    }

    public void failureEndResult(Long appID) {
        this.testAppRepository.updateResult(appID, 2L);
    }
}
