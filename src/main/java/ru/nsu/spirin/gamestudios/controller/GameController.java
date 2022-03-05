package ru.nsu.spirin.gamestudios.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.nsu.spirin.gamestudios.dao.GameDAO;
import ru.nsu.spirin.gamestudios.model.entity.Game;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/games")
public class GameController {
    private final GameDAO gameDAO;

    @Autowired
    public GameController(GameDAO gameDAO) {
        this.gameDAO = gameDAO;
    }

    @GetMapping("")
    public String indexContracts(Model model, Principal principal) {
        List<Game> games = gameDAO.getAllGames();
        model.addAttribute("games", games);
        return "games/games";
    }
}
