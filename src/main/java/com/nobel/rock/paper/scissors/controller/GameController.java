package com.nobel.rock.paper.scissors.controller;

import com.nobel.rock.paper.scissors.config.Constants;
import com.nobel.rock.paper.scissors.dao.Game;
import com.nobel.rock.paper.scissors.dao.GameStatistics;
import com.nobel.rock.paper.scissors.enums.Move;
import com.nobel.rock.paper.scissors.service.GameService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/games")
public class GameController {

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    /**
     * Starts a new game.
     *
     * @param playerOneName The name of player one.
     * @return A ResponseEntity containing the newly created game.
     */
    @ApiOperation(value = "Start New Game")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Game created successfully"),
    })
    @PostMapping(value = "/start")
    public ResponseEntity<Game> start(
            @RequestParam("playerOneName") String playerOneName) {
        return new ResponseEntity<>(gameService.start(playerOneName, Constants.PLAYER_TWO_NAME), HttpStatus.CREATED);
    }

    /**
     * Terminates an existing game by its ID.
     *
     * @param id The ID of the game to terminate.
     * @return A ResponseEntity containing the termination message.
     */
    @ApiOperation(value = "Finish Game")
    @PatchMapping("/{id}/terminate")
    public ResponseEntity<String> terminateGame(@PathVariable Long id) {
        return new ResponseEntity<>(gameService.terminate(id), HttpStatus.ACCEPTED);
    }


    /**
     * Retrieves the statistics of all games played.
     *
     * @return A ResponseEntity containing the game statistics.
     */
    @ApiOperation(value = "Game Statistics")
    @GetMapping("/statistics")
    public ResponseEntity<GameStatistics> getStatistics() {
        GameStatistics statistics = gameService.getGameStatistics();
        return new ResponseEntity<>(statistics, HttpStatus.OK);
    }

    /**
     * Makes a move in an ongoing game.
     *
     * @param id The ID of the game.
     * @param playerOneChoice The move choice of player one.
     * @return A ResponseEntity containing the result of the move.
     */
    @ApiOperation(value = "Make Move in Game")
    @PutMapping("/{gameId}")
    public ResponseEntity<String> makeMove(
            @PathVariable("gameId") Long id,
            @RequestParam("move") Move playerOneChoice) {
        String result = gameService.makeMove(id, playerOneChoice);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * Retrieves the details of a game by its ID.
     *
     * @param id The ID of the game to retrieve.
     * @return A ResponseEntity containing the game details if found, or a NOT_FOUND status if not.
     */
    @ApiOperation(value = "Get Game")
    @GetMapping("/{id}")
    public ResponseEntity<Game> getGame(@PathVariable Long id) {
        Game game = gameService.getGame(id);
        if (game != null) {
            return new ResponseEntity<>(game, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
