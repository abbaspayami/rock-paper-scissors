package com.nobel.rock.paper.scissors.repository;

import com.nobel.rock.paper.scissors.dao.GameStatistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameStatisticsRepository extends JpaRepository<GameStatistics, Long> {
}
