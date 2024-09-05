package com.nobel.rock.paper.scissors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nobel.rock.paper.scissors.config.Constants;
import com.nobel.rock.paper.scissors.controller.GameController;
import com.nobel.rock.paper.scissors.dao.Game;
import com.nobel.rock.paper.scissors.dao.GameStatistics;
import com.nobel.rock.paper.scissors.enums.Move;
import com.nobel.rock.paper.scissors.service.GameService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
class GameControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GameService gameService;

    @InjectMocks
    private GameController gameController;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void testStartGame() throws Exception {
        Game game = new Game();
        game.setId(1L);
        game.setPlayerOneName("Abbas");
        game.setPlayerTwoName(Constants.PLAYER_TWO_NAME);

        when(gameService.start("Abbas", Constants.PLAYER_TWO_NAME)).thenReturn(game);

        MvcResult result = mockMvc.perform(post("/api/v1/games/start")
                        .param("playerOneName", "Abbas"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.playerOneName").value("Abbas"))
                .andExpect(jsonPath("$.playerTwoName").value(Constants.PLAYER_TWO_NAME))
                .andReturn();

        System.out.println("Response Content: " + result.getResponse().getContentAsString());
    }

    @Test
    void testTerminateGame() throws Exception {
        when(gameService.terminate(1L)).thenReturn("Game terminated successfully");

        mockMvc.perform(patch("/api/v1/games/1/terminate"))
                .andExpect(status().isAccepted())
                .andExpect(content().string("Game terminated successfully"));
    }

    @Test
    void testGetStatistics() throws Exception {
        GameStatistics stats = new GameStatistics();
        stats.setTotalGames(10);
        stats.setPlayerOneWins(5);
        stats.setPlayerTwoWins(3);
        stats.setTies(2);

        when(gameService.getGameStatistics()).thenReturn(stats);

        mockMvc.perform(get("/api/v1/games/statistics"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalGames").value(10))
                .andExpect(jsonPath("$.playerOneWins").value(5))
                .andExpect(jsonPath("$.playerTwoWins").value(3))
                .andExpect(jsonPath("$.ties").value(2));
    }

    @Test
    void testMakeMove() throws Exception {
        when(gameService.makeMove(1L, Move.ROCK)).thenReturn("Player One wins!");

        mockMvc.perform(put("/api/v1/games/1")
                        .param("move", "ROCK"))
                .andExpect(status().isOk())
                .andExpect(content().string("Player One wins!"));
    }

    @Test
    void testGetGame() throws Exception {
        Game game = new Game();
        game.setId(1L);
        game.setPlayerOneName("Abbas");
        game.setPlayerTwoName(Constants.PLAYER_TWO_NAME);

        when(gameService.getGame(1L)).thenReturn(game);

        mockMvc.perform(get("/api/v1/games/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.playerOneName").value("Abbas"))
                .andExpect(jsonPath("$.playerTwoName").value(Constants.PLAYER_TWO_NAME));
    }
}

