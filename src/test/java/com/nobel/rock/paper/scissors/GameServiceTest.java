package com.nobel.rock.paper.scissors;

import com.nobel.rock.paper.scissors.dao.Game;
import com.nobel.rock.paper.scissors.dao.GameStatistics;
import com.nobel.rock.paper.scissors.enums.GameStatus;
import com.nobel.rock.paper.scissors.enums.Move;
import com.nobel.rock.paper.scissors.exception.GameFinishedException;
import com.nobel.rock.paper.scissors.exception.GameNotFoundException;
import com.nobel.rock.paper.scissors.repository.GameRepository;
import com.nobel.rock.paper.scissors.repository.GameStatisticsRepository;
import com.nobel.rock.paper.scissors.service.impl.GameServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.when;
 class GameServiceTest {
    @Mock
    private GameRepository gameRepository;

    @Mock
    private GameStatisticsRepository gameStatisticsRepository;

    @InjectMocks
    private GameServiceImpl gameService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testStart() {
        String playerOneName = "Player1";
        String playerTwoName = "Player2";
        Game game = new Game(playerOneName, playerTwoName);
        when(gameRepository.save(any(Game.class))).thenReturn(game);

        Game createdGame = gameService.start(playerOneName, playerTwoName);
        assertNotNull(createdGame);
        assertEquals(playerOneName, createdGame.getPlayerOneName());
        assertEquals(playerTwoName, createdGame.getPlayerTwoName());
    }

    @Test
    void testGetGame() throws GameNotFoundException {
        Long gameId = 1L;
        Game game = new Game();
        when(gameRepository.findById(gameId)).thenReturn(Optional.of(game));

        Game retrievedGame = gameService.getGame(gameId);
        assertNotNull(retrievedGame);
        assertEquals(game, retrievedGame);
    }

    @Test
    void testGetGameNotFound() {
        Long gameId = 1L;
        when(gameRepository.findById(gameId)).thenReturn(Optional.empty());

        assertThrows(GameNotFoundException.class, () -> gameService.getGame(gameId));
    }

     @Test
     void testTerminate() throws GameFinishedException {
         Long gameId = 1L;
         Game game = new Game();
         game.setGameStatus(GameStatus.IN_PROGRESS);

         // Mock repository behavior
         when(gameRepository.findById(gameId)).thenReturn(Optional.of(game));

         // We also need to mock the save method
         when(gameRepository.save(any(Game.class))).thenAnswer(invocation -> {
             Game updatedGame = invocation.getArgument(0);
             game.setGameStatus(updatedGame.getGameStatus());  // Update game status to match the one passed to save
             return game;
         });

         String result = gameService.terminate(gameId);
         assertEquals("Game terminated successfully", result);
         assertEquals(GameStatus.TERMINATED, game.getGameStatus());
         verify(gameRepository, times(1)).save(game);
     }

    @Test
    void testGetGameStatistics() {
        GameStatistics stats = new GameStatistics();
        when(gameStatisticsRepository.findAll()).thenReturn(Collections.singletonList(stats));

        GameStatistics retrievedStats = gameService.getGameStatistics();
        assertNotNull(retrievedStats);
        verify(gameStatisticsRepository, times(1)).findAll();
    }

     @Test
     void testMakeMove() {
         Long gameId = 1L;
         Move playerOneMove = Move.ROCK;
         Move playerTwoMove = Move.SCISSORS; // Assuming this is the random move for the test
         Game game = new Game();
         game.setGameStatus(GameStatus.IN_PROGRESS);
         game.setPlayerOneScore(0);
         game.setPlayerTwoScore(0);

         GameStatistics stats = new GameStatistics();  // Initialize GameStatistics
         when(gameRepository.findById(gameId)).thenReturn(Optional.of(game));
         when(gameRepository.save(any(Game.class))).thenReturn(game);
         when(gameStatisticsRepository.findAll()).thenReturn(Collections.singletonList(stats));  // Mocking the repository

         // Mock the random move
         try (MockedStatic<Move> mockedMove = mockStatic(Move.class)) {
             mockedMove.when(Move::getRandomMove).thenReturn(playerTwoMove);

             String result = gameService.makeMove(gameId, playerOneMove);
             assertEquals("Player One wins!", result);
             verify(gameRepository, times(1)).save(game);
             verify(gameStatisticsRepository, times(1)).save(stats);  // Verify the save call
         }
     }

}
