package com.nobel.rock.paper.scissors.dao;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class GameStatistics {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer totalGames;
    private Integer playerOneWins;
    private Integer playerTwoWins;
    private Integer ties;

    // Constructors
    public GameStatistics() {
        this.totalGames = 0;
        this.playerOneWins = 0;
        this.playerTwoWins = 0;
        this.ties = 0;
    }

}
