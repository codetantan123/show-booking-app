package com.project.showbooking.service.impl;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.project.showbooking.model.Seat;
import com.project.showbooking.model.Show;
import com.project.showbooking.model.Ticket;
import com.project.showbooking.repository.SeatRepository;
import com.project.showbooking.repository.ShowRepository;
import com.project.showbooking.repository.TicketRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class BuyerServiceImplTest {

    @Mock
    private ShowRepository showRepository;

    @Mock
    private TicketRepository ticketRepository;

    @Mock
    private SeatRepository seatRepository;

    @InjectMocks
    private BuyerServiceImpl buyerService;

    private Show show;

    private Ticket ticket;

    private Seat seat;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        show = new Show(5, 5, 10);
        ticket = new Ticket(show, 1234567890, LocalDateTime.now());
        seat = new Seat("A1", show);
    }

    @Test
    public void testCheckSeatAvailability() {
        when(showRepository.findByShowNumber(Mockito.anyLong())).thenReturn(show);
        List<Seat> seats = new ArrayList<>();
        seats.add(seat);
        when(seatRepository.findAllByShowAndTicketIsNull(Mockito.any())).thenReturn((ArrayList<Seat>) seats);
        buyerService.checkSeatAvailability(1L);
        assertTrue(outContent.toString().contains("A1"));
    }

    @Test
    public void testBookShow() throws Exception {
        when(showRepository.findByShowNumber(Mockito.anyLong())).thenReturn(show);
        when(ticketRepository.save(Mockito.any(Ticket.class))).thenReturn(ticket);
        when(seatRepository.findBySeatNumberAndShow(Mockito.anyString(), Mockito.any())).thenReturn(seat);
        buyerService.bookShow(1L, 1234567890, "A1");
        verify(seatRepository, times(1)).save(any());
        assertTrue(outContent.toString().contains("Show has been booked successfully"));
    }

    @Test(expected = Exception.class)
    public void testCancelBooking_exceededCancellationWindow() throws Exception {
        when(ticketRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(ticket));
        ticket.setBookingTime(LocalDateTime.now().minusMinutes(15));
        buyerService.cancelBooking(1L, 1234567890);
    }

    @Test(expected = Exception.class)
    public void testCancelBooking_ticketNotFound() throws Exception {
        when(ticketRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        buyerService.cancelBooking(1L, 1234567890);
    }

    @Test
    public void testCancelBooking() throws Exception {
        List<Seat> seats = new ArrayList<>();
        seats.add(seat);
        ticket.setSeats(seats);
        when(ticketRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(ticket));
        when(seatRepository.save(Mockito.any(Seat.class))).thenReturn(any());
        buyerService.cancelBooking(1L, 1234567890);
        assertTrue(outContent.toString().contains("Booking has been cancelled successfully"));
    }
}