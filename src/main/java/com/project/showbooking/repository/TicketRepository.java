package com.project.showbooking.repository;

import com.project.showbooking.model.Show;
import com.project.showbooking.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    ArrayList<Ticket> findAllByShow(Show show);

    Ticket findByTicketNumber(Long ticketNumber);
}
