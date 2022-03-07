package ru.nsu.spirin.gamestudios.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.nsu.spirin.gamestudios.dao.*;
import ru.nsu.spirin.gamestudios.model.entity.Contract;
import ru.nsu.spirin.gamestudios.model.entity.Employee;
import ru.nsu.spirin.gamestudios.model.entity.Game;
import ru.nsu.spirin.gamestudios.model.entity.Genre;

import java.security.Principal;
import java.sql.SQLException;
import java.util.List;

@Controller
@RequestMapping("/games")
public class GameController {
    private final GameDAO gameDAO;
    private final GenreDAO genreDAO;
    private final EmployeeDAO employeeDAO;
    private final ContractDAO contractDAO;
    private final GameReleaseDAO gameReleaseDAO;
    private final StudioDAO studioDAO;

    @Autowired
    public GameController(GameDAO gameDAO,
                          GenreDAO genreDAO,
                          EmployeeDAO employeeDAO,
                          ContractDAO contractDAO,
                          GameReleaseDAO gameReleaseDAO,
                          StudioDAO studioDAO) {
        this.gameDAO = gameDAO;
        this.genreDAO = genreDAO;
        this.employeeDAO = employeeDAO;
        this.contractDAO = contractDAO;
        this.gameReleaseDAO = gameReleaseDAO;
        this.studioDAO = studioDAO;
    }

    @GetMapping("")
    public String indexGames(Model model, Principal principal) {
        List<Game> games = gameDAO.getAllGames();
        model.addAttribute("games", games);
        return "games/games";
    }

    @GetMapping("/{id}")
    public String viewGame(Model model, Principal principal, @PathVariable(name = "id") Long gameID) {
        model.addAttribute("game", gameDAO.getGameByID(gameID));
        model.addAttribute("contracts", contractDAO.getContractsByGameID(gameID));
        model.addAttribute("releases", gameReleaseDAO.getReleasesByGameID(gameID));
        model.addAttribute("genres", genreDAO.getGenresByGameID(gameID));
        model.addAttribute("employees", employeeDAO.getEmployeesByGameID(gameID));
        return "games/view_game";
    }

    @GetMapping("/new")
    public String newGame(@ModelAttribute("game") Game game,
                              Model model, Principal principal) {
        model.addAttribute("studios", studioDAO.getAllStudios());
        return "/games/new_game";
    }

    @PostMapping("")
    public String create(@ModelAttribute("game") Game game,
                         Model model, Principal principal) throws SQLException {
        gameDAO.newGame(game);
        return "redirect:/games";
    }

    @GetMapping("/{id}/add_genre")
    public String addGenreGet(@ModelAttribute("genre") Genre genre,
                             Model model, Principal principal, @PathVariable(name = "id") Long gameID) {
        model.addAttribute("genres", genreDAO.getAllGenres());
        model.addAttribute("gameID", gameID);
        return "/games/add_genre";
    }

    @PostMapping("/{id}/genre")
    public String addGenrePost(@ModelAttribute("game") Genre genre,
                              Model model, Principal principal, @PathVariable(name = "id") Long gameID) throws SQLException {
        gameDAO.addGenre(gameID, genre.getGenreID());
        return "redirect:/games/{id}";
    }

    @GetMapping("/{id}/remove_genre/{id1}")
    public String removeGenreGet(Model model, Principal principal,
                                @PathVariable(name = "id") Long gameID,
                                @PathVariable(name = "id1") Long genreID) {
        gameDAO.removeGenreFromGame(gameID, genreID);
        return "redirect:/games/{id}";
    }

    @GetMapping("/{id}/add_employee")
    public String addEmployeeGet(@ModelAttribute("employee") Employee employee,
                              Model model, Principal principal, @PathVariable(name = "id") Long gameID) {
        Game game = gameDAO.getGameByID(gameID);
        model.addAttribute("employees", employeeDAO.getEmployeesByStudio(game.getStudioID()));
        model.addAttribute("gameID", gameID);
        return "/games/add_employee";
    }

    @PostMapping("/{id}/employee")
    public String addEmployeePost(@ModelAttribute("employee") Employee employee,
                               Model model, Principal principal, @PathVariable(name = "id") Long gameID) throws SQLException {
        gameDAO.addEmployee(gameID, employee.getEmployeeID());
        return "redirect:/games/{id}";
    }

    @GetMapping("/{id}/remove_employee/{id1}")
    public String removeEmployeeGet(Model model, Principal principal,
                                 @PathVariable(name = "id") Long gameID,
                                 @PathVariable(name = "id1") Long employeeID) {
        gameDAO.removeEmployeeFromGame(gameID, employeeID);
        return "redirect:/games/{id}";
    }

    @GetMapping("/{id}/edit")
    public String editGame(Model model, @PathVariable("id") Long gameID) {
        model.addAttribute("game", gameDAO.getGameByID(gameID));
        return "/games/edit_game";
    }

    @PostMapping("/{id}")
    public String update(@ModelAttribute("game") Game game,
                         Principal principal,
                         @PathVariable("id") Long id) throws SQLException {
        gameDAO.updateGame(id, game);
        return "redirect:/games/{id}";
    }
}
