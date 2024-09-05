package com.nobel.rock.paper.scissors.dao;

import com.nobel.rock.paper.scissors.enums.GameStatus;
import com.nobel.rock.paper.scissors.enums.Move;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private GameStatus gameStatus;

    private String playerOneName;
    private Integer playerOneScore;

    private String playerTwoName;
    private Integer playerTwoScore;

    @Enumerated(EnumType.STRING)
    private Move playerOneMove;

    @Enumerated(EnumType.STRING)
    private Move playerTwoMove;

    public Game(String playerOneName) {
        this.playerOneName = playerOneName;
        this.playerOneScore = 0;
        this.playerTwoScore = 0;
        this.gameStatus = GameStatus.IN_PROGRESS;
    }
    public Game(String playerOneName, String playerTwoName) {
        this.playerOneName = playerOneName;
        this.playerTwoName = playerTwoName;
        this.playerOneScore = 0;
        this.playerTwoScore = 0;
        this.gameStatus = GameStatus.IN_PROGRESS;
    }


}
