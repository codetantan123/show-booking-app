package com.project.showbooking.service.impl;

import com.project.showbooking.model.Show;
import com.project.showbooking.repository.SeatRepository;
import com.project.showbooking.repository.ShowRepository;
import com.project.showbooking.repository.TicketRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AdminServiceImplTest {
    @Mock
    private ShowRepository showRepository;

    @Mock
    private TicketRepository ticketRepository;

    @Mock
    private SeatRepository seatRepository;

    @InjectMocks
    private AdminServiceImpl adminService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSetupShow() throws Exception {
        int numberOfRows = 5;
        int numberOfSeatsPerRow = 5;
        int cancellationWindow = 60;

        when(showRepository.save(any())).thenReturn(new Show());
        when(seatRepository.saveAll(any())).thenReturn(any());

        adminService.setupShow(numberOfRows, numberOfSeatsPerRow, cancellationWindow);

        verify(showRepository, times(1)).save(any());
        verify(seatRepository, times(1)).saveAll(any());
    }

    @Test(expected = Exception.class)
    public void testSetupShowInvalidInput() throws Exception {
        int numberOfRows = 27;
        int numberOfSeatsPerRow = 10;
        int cancellationWindow = 60;

        adminService.setupShow(numberOfRows, numberOfSeatsPerRow, cancellationWindow);
    }

    @Test
    public void testViewBookings() throws Exception {
        long showNumber = 1L;
        Show testShow = new Show();
        testShow.setTickets(new ArrayList<>());

        when(showRepository.findByShowNumber(anyLong())).thenReturn(testShow);

        adminService.viewBookings(showNumber);

        verify(showRepository, times(1)).findByShowNumber(anyLong());
    }

    @Test(expected = Exception.class)
    public void testViewBookingsNotFound() throws Exception {
        long showNumber = 1L;

        Mockito.when(showRepository.findByShowNumber(Mockito.anyLong())).thenReturn(null);

        adminService.viewBookings(showNumber);
    }
}