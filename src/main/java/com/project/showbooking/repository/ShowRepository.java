package com.project.showbooking.repository;

import com.project.showbooking.model.Show;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShowRepository extends JpaRepository<Show, Long> {
    Show findByShowNumber(Long showNumber);
}
