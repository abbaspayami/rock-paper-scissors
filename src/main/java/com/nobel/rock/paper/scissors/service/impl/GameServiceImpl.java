package com.nobel.rock.paper.scissors.service.impl;

import com.nobel.rock.paper.scissors.config.Constants;
import com.nobel.rock.paper.scissors.dao.Game;
import com.nobel.rock.paper.scissors.dao.GameStatistics;
import com.nobel.rock.paper.scissors.enums.GameStatus;
import com.nobel.rock.paper.scissors.enums.Move;
import com.nobel.rock.paper.scissors.exception.GameFinishedException;
import com.nobel.rock.paper.scissors.exception.GameNotFoundException;
import com.nobel.rock.paper.scissors.repository.GameRepository;
import com.nobel.rock.paper.scissors.repository.GameStatisticsRepository;
import com.nobel.rock.paper.scissors.service.GameService;
import org.springframework.stereotype.Service;

@Service
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;
    private final GameStatisticsRepository gameStatisticsRepository;

    public GameServiceImpl(GameRepository gameRepository, GameStatisticsRepository gameStatisticsRepository) {
        this.gameRepository = gameRepository;
        this.gameStatisticsRepository = gameStatisticsRepository;

        if (gameStatisticsRepository.count() == 0) {
            gameStatisticsRepository.save(new GameStatistics());
        }
    }

    /**
     * Starts a new game with the specified player names.
     *
     * @param playerOneName The name of player one.
     * @param playerTwoName The name of player two.
     * @return The newly created game.
     */
    @Override
    public Game start(String playerOneName, String playerTwoName) {
        Game game = new Game(playerOneName, playerTwoName);
        return gameRepository.save(game);
    }

    /**
     * Makes a move in an ongoing game.
     *
     * @param gameId The ID of the game.
     * @param playerOneMove The move of player one.
     * @return The result of the move.
     */
    @Override
    public String makeMove(Long gameId, Move playerOneMove) {

        Game game = getGame(gameId);
        validateGameStatus(game);

        Move playerTwoMove = Move.getRandomMove();
        game.setPlayerOneMove(playerOneMove);
        game.setPlayerTwoMove(playerTwoMove);

        String result = determineWinner(playerOneMove, playerTwoMove);
        updateGameResult(game, result);
        gameRepository.save(game);
        updateStatistics(result);

        return result;
    }

    /**
     * Determines the winner of the game based on the moves made by the players.
     *
     * @param playerOneMove The move of player one.
     * @param playerTwoMove The move of player two.
     * @return The result string indicating the winner.
     */
    private String determineWinner(Move playerOneMove, Move playerTwoMove) {
        if (playerOneMove == playerTwoMove) return Constants.TIE_RESULT;
        if ((playerOneMove == Move.ROCK && playerTwoMove == Move.SCISSORS) ||
                (playerOneMove == Move.SCISSORS && playerTwoMove == Move.PAPER) ||
                (playerOneMove == Move.PAPER && playerTwoMove == Move.ROCK)) {
            return Constants.PLAYER_ONE_WINS_RESULT;
        }
        return Constants.PLAYER_TWO_WINS_RESULT;
    }

    /**
     * Updates the game result based on the outcome of the moves.
     *
     * @param game The game to update.
     * @param result The result of the game.
     */
    private void updateGameResult(Game game, String result) {
        switch (result) {
            case "Player One wins!":
                game.setPlayerOneScore(game.getPlayerOneScore() + 1);
                game.setGameStatus(GameStatus.PLAYER_ONE_WINS);
                break;
            case "Player Two wins!":
                game.setPlayerTwoScore(game.getPlayerTwoScore() + 1);
                game.setGameStatus(GameStatus.PLAYER_TWO_WINS);
                break;
            case "It's a tie!":
                game.setGameStatus(GameStatus.TIE);
                break;
            default:
                game.setGameStatus(GameStatus.UNKNOWN);
                break;
        }
    }

    /**
     * Updates the game statistics after a game result.
     *
     * @param result The result of the game.
     */
    private void updateStatistics(String result) {
        GameStatistics stats = gameStatisticsRepository.findAll().getFirst();
        stats.setTotalGames(stats.getTotalGames() + 1);

        switch (result) {
            case "Player One wins!":
                stats.setPlayerOneWins(stats.getPlayerOneWins() + 1);
                break;
            case "Player Two wins!":
                stats.setPlayerTwoWins(stats.getPlayerTwoWins() + 1);
                break;
            case "It's a tie!":
                stats.setTies(stats.getTies() + 1);
                break;
            default:
                break;
        }

        gameStatisticsRepository.save(stats);
    }


    /**
     * Terminates an existing game by its ID.
     *
     * @param id The ID of the game to terminate.
     * @return A message indicating the termination was successful.
     */
    @Override
    public String terminate(Long id) {
        Game game = this.getGame(id);
        this.validateGameStatus(game);
        game.setGameStatus(GameStatus.TERMINATED);
        gameRepository.save(game);  // Ensure this line is present
        return "Game terminated successfully";
    }

    /**
     * Retrieves the statistics of all games played.
     *
     * @return The game statistics.
     */
    @Override
    public GameStatistics getGameStatistics() {
        return gameStatisticsRepository.findAll().getFirst();
    }

    /**
     * Retrieves the details of a game by its ID.
     *
     * @param id The ID of the game to retrieve.
     * @return The game details.
     * @throws GameNotFoundException If the game is not found.
     */
    @Override
    public Game getGame(Long id) throws GameNotFoundException {
        return gameRepository.findById(id)
                .orElseThrow(() -> new GameNotFoundException("Game not found"));
    }

    /**
     * Validates if the game status allows for further actions.
     *
     * @param game The game to validate.
     * @throws GameFinishedException If the game is already finished.
     */
    private void validateGameStatus(Game game) throws GameFinishedException {
        if (GameStatus.TERMINATED.equals(game.getGameStatus())) {
            throw new GameFinishedException("Game is already finished");
        }
    }

}
