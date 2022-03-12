package ru.nsu.spirin.gamestudios.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.nsu.spirin.gamestudios.model.entity.Contract;
import ru.nsu.spirin.gamestudios.repository.ContractRepository;

import java.util.List;

@Service
public class ContractService {
    private final ContractRepository contractRepository;

    @Autowired
    public ContractService(ContractRepository contractRepository) {
        this.contractRepository = contractRepository;
    }

    public List<Contract> getAllContracts() {
        return this.contractRepository.findAll();
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
}
