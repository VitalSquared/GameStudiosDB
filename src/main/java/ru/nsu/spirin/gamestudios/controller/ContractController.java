package ru.nsu.spirin.gamestudios.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.nsu.spirin.gamestudios.dao.ContractDAO;
import ru.nsu.spirin.gamestudios.model.entity.Contract;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/contracts")
public class ContractController {
    private final ContractDAO contractDAO;

    @Autowired
    public ContractController(ContractDAO contractDAO) {
        this.contractDAO = contractDAO;
    }

    @GetMapping("")
    public String indexContracts(Model model, Principal principal) {
        List<Contract> contracts = contractDAO.getAllContracts();
        model.addAttribute("contracts", contracts);
        return "contracts/contracts";
    }
}
