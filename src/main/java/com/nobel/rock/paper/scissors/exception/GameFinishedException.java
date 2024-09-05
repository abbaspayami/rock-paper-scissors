package com.nobel.rock.paper.scissors.exception;

public class GameFinishedException extends RuntimeException {

    public GameFinishedException(String message) {
        super(message);
    }
}
