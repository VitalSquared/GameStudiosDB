package ru.nsu.spirin.gamestudios.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.nsu.spirin.gamestudios.dao.ContractDAO;
import ru.nsu.spirin.gamestudios.dao.GameDAO;
import ru.nsu.spirin.gamestudios.dao.TestDAO;
import ru.nsu.spirin.gamestudios.model.entity.Contract;
import ru.nsu.spirin.gamestudios.model.entity.Game;
import ru.nsu.spirin.gamestudios.model.entity.Test;

import java.security.Principal;
import java.sql.SQLException;
import java.util.List;

@Controller
@RequestMapping("/contracts")
public class ContractController {
    private final ContractDAO contractDAO;
    private final TestDAO testDAO;
    private final GameDAO gameDAO;

    @Autowired
    public ContractController(ContractDAO contractDAO,
                              TestDAO testDAO,
                              GameDAO gameDAO) {
        this.contractDAO = contractDAO;
        this.testDAO = testDAO;
        this.gameDAO = gameDAO;
    }

    @GetMapping("")
    public String indexContracts(Model model, Principal principal) {
        List<Contract> contracts = contractDAO.getAllContracts();
        model.addAttribute("contracts", contracts);
        return "contracts/contracts";
    }

    @GetMapping("/new")
    public String newContract(@ModelAttribute("contract") Contract contract,
                          Model model, Principal principal) {
        model.addAttribute("tests", testDAO.getResultedTests());
        return "/contracts/new_contract";
    }

    @PostMapping("")
    public String create(@ModelAttribute("contract") Contract contract,
                         Model model, Principal principal) throws SQLException {
        contractDAO.newContract(contract);
        return "redirect:/contracts";
    }

    @GetMapping("/{id}")
    public String viewContract(Model model, Principal principal, @PathVariable(name = "id") Long contractID) {
        model.addAttribute("games", gameDAO.getGamesByContractID(contractID));
        model.addAttribute("contract", contractDAO.getContractByID(contractID));
        return "contracts/view_contract";
    }

    @GetMapping("/{id}/add_game")
    public String addGameGet(@ModelAttribute("game") Game game,
                              Model model, Principal principal, @PathVariable(name = "id") Long contractID) {
        model.addAttribute("games", gameDAO.getAllGames());
        model.addAttribute("contractID", contractID);
        return "/contracts/add_game";
    }

    @PostMapping("/{id}/game")
    public String addGamePost(@ModelAttribute("game") Game game,
                         Model model, Principal principal, @PathVariable(name = "id") Long contractID) throws SQLException {
        contractDAO.addGameToContract(contractID, game.getGameID());
        return "redirect:/contracts/{id}";
    }

    @GetMapping("/{id}/remove_game/{id1}")
    public String removeGameGet(Model model, Principal principal,
                                @PathVariable(name = "id") Long contractID,
                                @PathVariable(name = "id1") Long gameID) {
        contractDAO.removeGameFromContract(contractID, gameID);
        return "redirect:/contracts/{id}";
    }

    @GetMapping("/{id}/edit")
    public String editContract(Model model, @PathVariable("id") Long contractID) {
        model.addAttribute("contract", contractDAO.getContractByID(contractID));
        return "/contracts/edit_contract";
    }

    @PostMapping("/{id}")
    public String update(@ModelAttribute("contract") Contract contract,
                         Principal principal,
                         @PathVariable("id") Long id) throws SQLException {
        contractDAO.updateContract(id, contract);
        return "redirect:/contracts/{id}";
    }
}
