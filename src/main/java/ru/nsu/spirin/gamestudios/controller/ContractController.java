package ru.nsu.spirin.gamestudios.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.nsu.spirin.gamestudios.model.entity.Contract;
import ru.nsu.spirin.gamestudios.model.entity.Employee;
import ru.nsu.spirin.gamestudios.model.entity.Game;
import ru.nsu.spirin.gamestudios.model.entity.account.Account;
import ru.nsu.spirin.gamestudios.service.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/contracts")
public class ContractController {
    private final ContractService contractService;
    private final TestService testService;
    private final GameService gameService;
    private final StudioService studioService;
    private final AccountService accountService;
    private final EmployeeService employeeService;

    @Autowired
    public ContractController(ContractService contractService,
                              TestService testService,
                              GameService gameService,
                              StudioService studioService,
                              AccountService accountService,
                              EmployeeService employeeService) {
        this.contractService = contractService;
        this.testService = testService;
        this.gameService = gameService;
        this.studioService = studioService;
        this.accountService = accountService;
        this.employeeService = employeeService;
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'GENERAL_DIRECTOR', 'STUDIO_DIRECTOR', 'DEVELOPER')")
    @RequestMapping(path = "", method = RequestMethod.GET)
    public String indexContracts(Model model, Principal principal) {
        String userName = principal.getName();
        Account account = this.accountService.findAccountByEmail(userName);
        Employee employee = this.employeeService.getEmployeeByID(account.getEmployeeID());

        List<Contract> contracts;
        if (employee.getStudioID() == 0) {
            contracts = this.contractService.getAllContracts();
        }
        else {
            contracts = this.contractService.getAllContractsByStudioID(employee.getStudioID());
        }
        model.addAttribute("contracts", contracts);
        return "contracts/contracts";
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'GENERAL_DIRECTOR')")
    @RequestMapping(path = "/new", method = RequestMethod.GET)
    public String newContract(@ModelAttribute("contract") Contract contract, Model model) {
        model.addAttribute("tests", this.testService.getResultedTests());
        return "/contracts/new_contract";
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'GENERAL_DIRECTOR')")
    @RequestMapping(path = "/new", method = RequestMethod.POST)
    public String createContract(@Valid @ModelAttribute("contract") Contract contract, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/contracts/new_contract";
        }
        this.contractService.createNewContract(contract);
        return "redirect:/contracts";
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'GENERAL_DIRECTOR', 'STUDIO_DIRECTOR', 'DEVELOPER')")
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public String viewContract(Model model, @PathVariable(name = "id") Long contractID) {
        model.addAttribute("games", this.gameService.getGamesByContractID(contractID));
        model.addAttribute("contract", this.contractService.getContractByID(contractID));
        model.addAttribute("all_studios", this.studioService.getAllStudios());
        return "contracts/view_contract";
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'GENERAL_DIRECTOR')")
    @RequestMapping(path = "/{id}/add_game", method = RequestMethod.GET)
    public String addGameGet(@ModelAttribute("game") Game game, Model model,
                             @PathVariable(name = "id") Long contractID) {
        model.addAttribute("games", this.gameService.getAllGames());
        model.addAttribute("contractID", contractID);
        return "/contracts/add_game";
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'GENERAL_DIRECTOR')")
    @RequestMapping(path = "/{id}/game", method = RequestMethod.POST)
    public String addGamePost(@ModelAttribute("game") Game game, @PathVariable(name = "id") Long contractID) {
        this.contractService.addGameToContract(contractID, game.getGameID());
        return "redirect:/contracts/{id}";
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'GENERAL_DIRECTOR')")
    @RequestMapping(path = "/{id}/remove_game/{id1}", method = RequestMethod.GET)
    public String removeGameGet(@PathVariable(name = "id") Long contractID,
                                @PathVariable(name = "id1") Long gameID) {
        this.contractService.removeGameFromContract(contractID, gameID);
        return "redirect:/contracts/{id}";
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'GENERAL_DIRECTOR')")
    @RequestMapping(path = "/{id}/edit", method = RequestMethod.GET)
    public String editContract(Model model, @PathVariable("id") Long contractID) {
        model.addAttribute("contractID", contractID);
        model.addAttribute("contract", this.contractService.getContractByID(contractID));
        return "/contracts/edit_contract";
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'GENERAL_DIRECTOR')")
    @RequestMapping(path = "/{id}/edit", method = RequestMethod.POST)
    public String updateContract(@ModelAttribute("contract") Contract contract,
                                 BindingResult bindingResult,
                                 Model model,
                                 @PathVariable("id") Long contractID) {
        model.addAttribute("contractID", contractID);

        if (bindingResult.hasErrors()) {
            return "/contracts/edit_contract";
        }

        this.contractService.updateContract(contractID, contract);
        return "redirect:/contracts/{id}";
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(path = "/{id}/delete", method = RequestMethod.GET)
    public String deleteContract(@PathVariable("id") Long contractID) {
        this.contractService.deleteContract(contractID);
        return "redirect:/contracts";
    }
}
