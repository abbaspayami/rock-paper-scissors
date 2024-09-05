package com.nobel.rock.paper.scissors.enums;

import lombok.Getter;

@Getter
public enum GameStatus {
    IN_PROGRESS,
    PLAYER_ONE_WINS,
    PLAYER_TWO_WINS,
    TIE,
    TERMINATED,
    UNKNOWN
}
