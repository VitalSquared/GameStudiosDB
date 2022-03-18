package ru.nsu.spirin.gamestudios.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.nsu.spirin.gamestudios.model.entity.Contract;
import ru.nsu.spirin.gamestudios.model.entity.TestApp;
import ru.nsu.spirin.gamestudios.repository.ContractRepository;
import ru.nsu.spirin.gamestudios.repository.GameReleaseRepository;
import ru.nsu.spirin.gamestudios.repository.TestAppRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ContractService {
    private final ContractRepository contractRepository;
    private final GameReleaseRepository gameReleaseRepository;
    private final TestAppRepository testAppRepository;

    @Autowired
    public ContractService(ContractRepository contractRepository,
                           GameReleaseRepository gameReleaseRepository,
                           TestAppRepository testAppRepository) {
        this.contractRepository = contractRepository;
        this.gameReleaseRepository = gameReleaseRepository;
        this.testAppRepository = testAppRepository;
    }

    public List<Contract> getAllContracts() {
        return this.contractRepository.findAll();
    }

    public List<Contract> getAllContractsByStudioID(Long studioID) {
        List<Contract> all = this.contractRepository.findAll();
        List<Contract> studioContracts = new ArrayList<>();
        for (var contract : all) {
            TestApp app = this.testAppRepository.findByTestIDAndStudioID(contract.getTestID(), studioID);
            if (app != null && app.getResultID() == 3L) {
                studioContracts.add(contract);
            }
        }
        return studioContracts;
    }

    public Contract getContractByID(Long contractID) {
        return this.contractRepository.findByID(contractID);
    }

    public void createNewContract(Contract contract) {
        if (contract.getDate() == null || contract.getPercent() == null || contract.getTestID() == null ||
            contract.getPercent() < 0 || contract.getPercent() > 100) {
            return;
        }
        this.contractRepository.save(contract);
    }

    public void updateContract(Long contractID, Contract contract) {
        if (contract.getDate() == null || contract.getPercent() == null || contract.getTestID() == null ||
                contract.getPercent() < 0 || contract.getPercent() > 100) {
            return;
        }
        this.contractRepository.update(contractID, contract);
    }

    public List<Contract> getContractsByGameID(Long gameID) {
        return this.contractRepository.findAllByGameID(gameID);
    }

    public void addGameToContract(Long contractID, Long gameID) {
        this.contractRepository.saveGameContract(contractID, gameID);
    }

    public void removeGameFromContract(Long contractID, Long gameID) {
        this.contractRepository.deleteGameContract(contractID, gameID);
    }

    public void deleteContract(Long contractID) {
        this.gameReleaseRepository.deleteAllByContractID(contractID);
        this.contractRepository.deleteAllGameContract(contractID);
        this.contractRepository.delete(contractID);
    }
}
