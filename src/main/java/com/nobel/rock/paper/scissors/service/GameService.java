package com.nobel.rock.paper.scissors.service;

import com.nobel.rock.paper.scissors.dao.Game;
import com.nobel.rock.paper.scissors.dao.GameStatistics;
import com.nobel.rock.paper.scissors.enums.Move;

public interface GameService {

    Game start(String playerOneName, String playerTwoName);

    String makeMove(Long gameId, Move playerOneMove);

    String terminate(Long id);

   GameStatistics getGameStatistics();

    Game getGame(Long id);

}
