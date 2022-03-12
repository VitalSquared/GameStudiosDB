package ru.nsu.spirin.gamestudios.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.nsu.spirin.gamestudios.model.entity.Employee;
import ru.nsu.spirin.gamestudios.model.entity.Game;
import ru.nsu.spirin.gamestudios.model.entity.GameRelease;
import ru.nsu.spirin.gamestudios.model.entity.Genre;
import ru.nsu.spirin.gamestudios.service.ContractService;
import ru.nsu.spirin.gamestudios.service.EmployeeService;
import ru.nsu.spirin.gamestudios.service.GameReleaseService;
import ru.nsu.spirin.gamestudios.service.GameService;
import ru.nsu.spirin.gamestudios.service.GenreService;
import ru.nsu.spirin.gamestudios.service.PlatformService;
import ru.nsu.spirin.gamestudios.service.StudioService;

import java.sql.SQLException;
import java.util.List;

@Controller
@RequestMapping("/games")
public class GameController {
    private final GameService gameService;
    private final GenreService genreService;
    private final EmployeeService employeeService;
    private final ContractService contractService;
    private final GameReleaseService gameReleaseService;
    private final StudioService studioService;
    private final PlatformService platformService;

    @Autowired
    public GameController(GameService gameService,
                          GenreService genreService,
                          EmployeeService employeeService,
                          ContractService contractService,
                          GameReleaseService gameReleaseService,
                          StudioService studioService,
                          PlatformService platformService) {
        this.gameService = gameService;
        this.genreService = genreService;
        this.employeeService = employeeService;
        this.contractService = contractService;
        this.gameReleaseService = gameReleaseService;
        this.studioService = studioService;
        this.platformService = platformService;
    }

    @GetMapping("")
    public String indexGames(Model model) {
        List<Game> games = gameService.getAllGames();
        model.addAttribute("games", games);
        return "games/games";
    }

    @GetMapping("/{id}")
    public String viewGame(Model model, @PathVariable(name = "id") Long gameID) {
        model.addAttribute("game", gameService.getGameByID(gameID));
        model.addAttribute("contracts", contractService.getContractsByGameID(gameID));
        model.addAttribute("releases", gameReleaseService.getReleasesByGameID(gameID));
        model.addAttribute("genres", genreService.getGenresByGameID(gameID));
        model.addAttribute("employees", employeeService.getEmployeesByGameID(gameID));
        return "games/view_game";
    }

    @GetMapping("/new")
    public String newGame(@ModelAttribute("game") Game game, Model model) {
        model.addAttribute("studios", studioService.getAllStudios());
        return "/games/new_game";
    }

    @PostMapping("")
    public String create(@ModelAttribute("game") Game game) {
        gameService.newGame(game);
        return "redirect:/games";
    }

    @GetMapping("/{id}/add_genre")
    public String addGenreGet(@ModelAttribute("genre") Genre genre, Model model,
                              @PathVariable(name = "id") Long gameID) {
        model.addAttribute("genres", genreService.getAllGenres());
        model.addAttribute("gameID", gameID);
        return "/games/add_genre";
    }

    @PostMapping("/{id}/genre")
    public String addGenrePost(@ModelAttribute("game") Genre genre,
                              @PathVariable(name = "id") Long gameID) {
        gameService.addGenre(gameID, genre.getGenreID());
        return "redirect:/games/{id}";
    }

    @GetMapping("/{id}/remove_genre/{id1}")
    public String removeGenreGet(@PathVariable(name = "id") Long gameID,
                                 @PathVariable(name = "id1") Long genreID) {
        gameService.removeGenreFromGame(gameID, genreID);
        return "redirect:/games/{id}";
    }

    @GetMapping("/{id}/add_employee")
    public String addEmployeeGet(@ModelAttribute("employee") Employee employee, Model model,
                                 @PathVariable(name = "id") Long gameID) {
        Game game = gameService.getGameByID(gameID);
        model.addAttribute("employees", employeeService.getEmployeesByStudio(game.getStudioID()));
        model.addAttribute("gameID", gameID);
        return "/games/add_employee";
    }

    @PostMapping("/{id}/employee")
    public String addEmployeePost(@ModelAttribute("employee") Employee employee,
                               @PathVariable(name = "id") Long gameID) {
        gameService.addEmployee(gameID, employee.getEmployeeID());
        return "redirect:/games/{id}";
    }

    @GetMapping("/{id}/remove_employee/{id1}")
    public String removeEmployeeGet(@PathVariable(name = "id") Long gameID,
                                    @PathVariable(name = "id1") Long employeeID) {
        gameService.removeEmployeeFromGame(gameID, employeeID);
        return "redirect:/games/{id}";
    }

    @GetMapping("/{id}/edit")
    public String editGame(Model model, @PathVariable("id") Long gameID) {
        model.addAttribute("game", gameService.getGameByID(gameID));
        return "/games/edit_game";
    }

    @PostMapping("/{id}")
    public String update(@ModelAttribute("game") Game game,
                         @PathVariable("id") Long id) throws SQLException {
        gameService.updateGame(id, game);
        return "redirect:/games/{id}";
    }

    @GetMapping("/{id}/add_release")
    public String addReleaseGet(@ModelAttribute("release") GameRelease release, Model model,
                                @PathVariable(name = "id") Long gameID) {
        model.addAttribute("contracts", contractService.getContractsByGameID(gameID));
        model.addAttribute("platforms", platformService.getAllPlatforms());
        model.addAttribute("gameID", gameID);
        return "/games/add_release";
    }

    @PostMapping("/{id}/release")
    public String addReleasePost(@ModelAttribute("release") GameRelease release,
                                @PathVariable(name = "id") Long gameID) {
        gameReleaseService.addRelease(gameID, release);
        return "redirect:/games/{id}";
    }

    @GetMapping("/{id}/edit_release/{id1}")
    public String editRelease(Model model, @PathVariable("id") Long gameID, @PathVariable("id1") Long platformID) {
        model.addAttribute("release", gameReleaseService.getReleaseByGameAndPlatform(gameID, platformID));
        model.addAttribute("gameID", gameID);
        model.addAttribute("platformID", platformID);
        return "/games/edit_release";
    }

    @PostMapping("/{id}/edit_release/{id1}/post")
    public String updateRelease(@ModelAttribute("release") GameRelease release,
                                @PathVariable("id") Long gameID, @PathVariable("id1") Long platformID) {
        gameReleaseService.updateRelease(gameID, platformID, release);
        return "redirect:/games/{id}";
    }
}
