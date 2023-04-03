package com.project.showbooking.service.impl;

import com.project.showbooking.model.Seat;
import com.project.showbooking.model.Show;
import com.project.showbooking.model.Ticket;
import com.project.showbooking.repository.SeatRepository;
import com.project.showbooking.repository.ShowRepository;
import com.project.showbooking.repository.TicketRepository;
import com.project.showbooking.service.BuyerService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class BuyerServiceImpl implements BuyerService {
    private ShowRepository showRepository;
    private TicketRepository ticketRepository;
    private SeatRepository seatRepository;

    @Autowired
    public BuyerServiceImpl(TicketRepository ticketRepository, ShowRepository showRepository, SeatRepository seatRepository) {
        this.ticketRepository = ticketRepository;
        this.showRepository = showRepository;
        this.seatRepository = seatRepository;
    }

    @Override
    public void checkSeatAvailability(Long showNumber) {
        Show show = showRepository.findByShowNumber(showNumber);
        ArrayList<Seat> seats = seatRepository.findAllByShowAndTicketIsNull(show);
        String seatNumbers = seats.stream().map(Seat::getSeatNumber)
                .collect(Collectors.joining(", "));
        System.out.println("Available seats for Show Number " + showNumber + ": ");
        System.out.println(seatNumbers);
    }

    @Override
    public void bookShow(Long showNumber, int buyerPhoneNumber, String seats) throws Exception {
        Show show = showRepository.findByShowNumber(showNumber);
        if (show == null) {
            throw new Exception("Show not found");
        }
        Ticket ticket = new Ticket(show, buyerPhoneNumber, LocalDateTime.now());
        ticketRepository.save(ticket);
        reserveSeats(show, ticket, seats);
        System.out.println("Show has been booked successfully. (Ticket Number: " + ticket.getTicketNumber() + ")");
    }

    @Override
    public void cancelBooking(Long ticketNumber, int phoneNumber) throws Exception {
        Optional<Ticket> ticket = ticketRepository.findById(ticketNumber);
        if (ticket.isPresent()) {
            if (checkWithinCancellationWindow(ticket.get())) {
                unreserveSeats(ticket.get());
                ticketRepository.delete(ticket.get());
                System.out.println("Booking has been cancelled successfully. (Ticket Number: " + ticketNumber + ")");
            } else {
                throw new Exception("Exceeded cancellation window. ");
            }
        } else {
            throw new Exception("Ticket not found. ");
        }
    }

    private void reserveSeats(Show show, Ticket ticket, String seatStr) {
        String[] seatNumbers = seatStr.split(",");
        for (String seatNumber : seatNumbers) {
            Seat seat = seatRepository.findBySeatNumberAndShow(seatNumber, show);
            if (seat.getTicket() == null) {
                seat.setTicket(ticket);
                seatRepository.save(seat);
            }
        }
    }

    private void unreserveSeats(Ticket ticket) {
        List<Seat> seats = ticket.getSeats();
        for (Seat seat : seats) {
            seat.setTicket(null);
            seatRepository.save(seat);
        }
    }

    private boolean checkWithinCancellationWindow(Ticket ticket) {
        Duration cancellationWindow = Duration.ofMinutes(ticket.getShow().getCancellationWindow());
        long cancellationMinutes = cancellationWindow.toMinutes();
        LocalDateTime current = LocalDateTime.now();
        LocalDateTime bookingTime = ticket.getBookingTime();
        if (cancellationMinutes >= Duration.between(bookingTime, current).toMinutes()) {
            return true;
        } else {
            return false;
        }
    }
}
