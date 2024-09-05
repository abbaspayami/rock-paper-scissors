package com.nobel.rock.paper.scissors.repository;

import com.nobel.rock.paper.scissors.dao.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {

}
