package com.project.showbooking.repository;

import com.project.showbooking.model.Seat;
import com.project.showbooking.model.Show;
import com.project.showbooking.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {
    ArrayList<Seat> findAllByShowAndTicketIsNull(Show show);

    Seat findBySeatNumberAndShow(String seatNumber, Show show);

    ArrayList<Seat> findAllByTicket(Ticket ticket);
}
