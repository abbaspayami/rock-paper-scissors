package com.nobel.rock.paper.scissors.enums;

import java.util.Random;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum Move {
    ROCK, PAPER, SCISSORS;

    private static final Logger LOGGER = LoggerFactory.getLogger(Move.class);
    private static final Random RANDOM = new Random();

    public static Move getRandomMove() {
        Move move = values()[RANDOM.nextInt(values().length)];
        LOGGER.info("Computer chose: {}", move);
        return move;
    }
}
