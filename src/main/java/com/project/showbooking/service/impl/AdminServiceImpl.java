package com.project.showbooking.service.impl;

import com.project.showbooking.model.Seat;
import com.project.showbooking.model.Show;
import com.project.showbooking.model.Ticket;
import com.project.showbooking.repository.SeatRepository;
import com.project.showbooking.repository.ShowRepository;
import com.project.showbooking.repository.TicketRepository;
import com.project.showbooking.service.AdminService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@Transactional
public class AdminServiceImpl implements AdminService {
    char[] rows = IntStream.rangeClosed('A', 'Z')
            .mapToObj(c -> "" + (char) c).collect(Collectors.joining()).toCharArray();
    private ShowRepository showRepository;
    private TicketRepository ticketRepository;
    private SeatRepository seatRepository;

    @Autowired
    public AdminServiceImpl(TicketRepository ticketRepository, ShowRepository showRepository, SeatRepository seatRepository) {
        this.ticketRepository = ticketRepository;
        this.showRepository = showRepository;
        this.seatRepository = seatRepository;
    }

    @Override
    public void setupShow(int numberOfRows, int numberOfSeatsPerRow, int cancellationWindow) throws Exception {
        if (numberOfRows > 26 || numberOfSeatsPerRow > 10) {
            throw new Exception("Invalid number of rows or number of seats per row.");
        }
        Show show = new Show(numberOfRows, numberOfSeatsPerRow, cancellationWindow);
        showRepository.save(show);
        generateAvailableSeats(show, numberOfRows, numberOfSeatsPerRow);
        System.out.println("Show " + show.getShowNumber() + " has been setup successfully");
    }

    @Override
    public void viewBookings(long showNumber) throws Exception {
        Show show = showRepository.findByShowNumber(showNumber);
        if (show == null) {
            throw new Exception("Show not found. ");
        }
        System.out.println("Bookings for Show Number " + showNumber + ": ");
        List<Ticket> tickets = show.getTickets();
        for (Ticket ticket : tickets) {
            System.out.println(ticket.toString());
        }
    }

    private void generateAvailableSeats(Show show, int numberOfRows, int numberOfSeatsPerRow) {
        ArrayList<Seat> seats = new ArrayList<>();
        for (int i = 0; i < numberOfRows; i++) {
            for (int j = 1; j < numberOfSeatsPerRow + 1; j++) {
                String seatNumber = String.valueOf(Array.get(rows, i)) + j;
                Seat seat = new Seat(seatNumber, show);
                seats.add(seat);
            }
        }
        seatRepository.saveAll(seats);
    }
}
