package ru.nsu.spirin.gamestudios.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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

import javax.validation.Valid;
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

    @RequestMapping(path = "", method = RequestMethod.GET)
    public String indexGames(Model model) {
        List<Game> games = gameService.getAllGames();
        model.addAttribute("games", games);
        return "games/games";
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public String viewGame(Model model, @PathVariable(name = "id") Long gameID) {
        model.addAttribute("game", gameService.getGameByID(gameID));
        model.addAttribute("contracts", contractService.getContractsByGameID(gameID));
        model.addAttribute("releases", gameReleaseService.getReleasesByGameID(gameID));
        model.addAttribute("genres", genreService.getGenresByGameID(gameID));
        model.addAttribute("employees", employeeService.getEmployeesByGameID(gameID));
        return "games/view_game";
    }

    @RequestMapping(path = "/new", method = RequestMethod.GET)
    public String newGame(@ModelAttribute("game") Game game, Model model) {
        model.addAttribute("studios", studioService.getAllStudios());
        return "/games/new_game";
    }

    @RequestMapping(path = "/new", method = RequestMethod.POST)
    public String createGame(@Valid @ModelAttribute("game") Game game, BindingResult bindingResult, Model model) {
        model.addAttribute("studios", studioService.getAllStudios());

        if (bindingResult.hasErrors()) {
            return "/games/new_game";
        }

        gameService.newGame(game);
        return "redirect:/games";
    }

    @RequestMapping(path = "/{id}/edit", method = RequestMethod.GET)
    public String editGame(Model model, @PathVariable("id") Long gameID) {
        model.addAttribute("gameID", gameID);
        model.addAttribute("game", gameService.getGameByID(gameID));
        return "/games/edit_game";
    }

    @RequestMapping(path = "/{id}/edit", method = RequestMethod.POST)
    public String updateGame(@Valid @ModelAttribute("game") Game game,
                             BindingResult bindingResult,
                             Model model,
                             @PathVariable("id") Long gameID)  {
        model.addAttribute("gameID", gameID);

        if (bindingResult.hasErrors()) {
            return "/games/edit_game";
        }

        gameService.updateGame(gameID, game);
        return "redirect:/games/{id}";
    }

    @RequestMapping(path = "/{id}/add_genre", method = RequestMethod.GET)
    public String addGenreGet(@ModelAttribute("genre") Genre genre, Model model,
                              @PathVariable(name = "id") Long gameID) {
        model.addAttribute("genres", genreService.getAllGenres());
        model.addAttribute("gameID", gameID);
        return "/games/add_genre";
    }

    @RequestMapping(path = "/{id}/genre", method = RequestMethod.POST)
    public String addGenrePost(@ModelAttribute("game") Genre genre,
                              @PathVariable(name = "id") Long gameID) {
        gameService.addGenre(gameID, genre.getGenreID());
        return "redirect:/games/{id}";
    }

    @RequestMapping(path = "/{id}/remove_genre/{id1}", method = RequestMethod.GET)
    public String removeGenreGet(@PathVariable(name = "id") Long gameID,
                                 @PathVariable(name = "id1") Long genreID) {
        gameService.removeGenreFromGame(gameID, genreID);
        return "redirect:/games/{id}";
    }

    @RequestMapping(path = "/{id}/add_employee", method = RequestMethod.GET)
    public String addEmployeeGet(@ModelAttribute("employee") Employee employee, Model model,
                                 @PathVariable(name = "id") Long gameID) {
        Game game = gameService.getGameByID(gameID);
        model.addAttribute("employees", employeeService.getEmployeesByStudio(game.getStudioID()));
        model.addAttribute("gameID", gameID);
        return "/games/add_employee";
    }

    @RequestMapping(path = "/{id}/employee", method = RequestMethod.POST)
    public String addEmployeePost(@ModelAttribute("employee") Employee employee,
                               @PathVariable(name = "id") Long gameID) {
        gameService.addEmployee(gameID, employee.getEmployeeID());
        return "redirect:/games/{id}";
    }

    @RequestMapping(path = "/{id}/remove_employee/{id1}", method = RequestMethod.GET)
    public String removeEmployeeGet(@PathVariable(name = "id") Long gameID,
                                    @PathVariable(name = "id1") Long employeeID) {
        gameService.removeEmployeeFromGame(gameID, employeeID);
        return "redirect:/games/{id}";
    }

    @RequestMapping(path = "/{id}/new_release", method = RequestMethod.GET)
    public String addReleaseGet(@ModelAttribute("release") GameRelease release, Model model,
                                @PathVariable(name = "id") Long gameID) {
        model.addAttribute("contracts", contractService.getContractsByGameID(gameID));
        model.addAttribute("platforms", platformService.getAllPlatforms());
        model.addAttribute("gameID", gameID);
        return "/games/new_release";
    }

    @RequestMapping(path = "/{id}/new_release", method = RequestMethod.POST)
    public String addReleasePost(@Valid @ModelAttribute("release") GameRelease release,
                                 BindingResult bindingResult,
                                 Model model,
                                 @PathVariable(name = "id") Long gameID) {
        model.addAttribute("gameID", gameID);
        model.addAttribute("contracts", contractService.getContractsByGameID(gameID));
        model.addAttribute("platforms", platformService.getAllPlatforms());

        if (bindingResult.hasErrors()) {
            return "/games/new_release";
        }

        gameReleaseService.addRelease(gameID, release);
        return "redirect:/games/{id}";
    }

    @RequestMapping(path = "/{id}/edit_release/{id1}", method = RequestMethod.GET)
    public String editRelease(Model model, @PathVariable("id") Long gameID, @PathVariable("id1") Long platformID) {
        model.addAttribute("release", gameReleaseService.getReleaseByGameAndPlatform(gameID, platformID));
        model.addAttribute("gameID", gameID);
        model.addAttribute("platformID", platformID);
        return "/games/edit_release";
    }

    @RequestMapping(path = "/{id}/edit_release/{id1}", method = RequestMethod.POST)
    public String updateRelease(@Valid @ModelAttribute("release") GameRelease release,
                                BindingResult bindingResult, Model model,
                                @PathVariable("id") Long gameID, @PathVariable("id1") Long platformID) {
        model.addAttribute("gameID", gameID);
        model.addAttribute("platformID", platformID);

        if (bindingResult.hasErrors()) {
            return "/games/edit_release";
        }

        gameReleaseService.updateRelease(gameID, platformID, release);
        return "redirect:/games/{id}";
    }
}
