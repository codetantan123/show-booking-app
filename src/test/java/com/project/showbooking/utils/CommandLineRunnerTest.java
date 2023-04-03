package com.project.showbooking.utils;

import com.project.showbooking.service.AdminService;
import com.project.showbooking.service.BuyerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class CommandLineRunnerTest {
    @Mock
    private AdminService adminService;

    @Mock
    private BuyerService buyerService;

    @InjectMocks
    private CommandLineRunner commandLineRunner;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getInput_setup() throws Exception {
        String input = "SETUP 3 3 3";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);

        commandLineRunner.getInput();
        verify(adminService, times(1)).setupShow(3, 3, 3);
    }

    @Test
    void getInput_view() throws Exception {
        String input = "VIEW 1";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);

        commandLineRunner.getInput();
        verify(adminService, times(1)).viewBookings(1L);
    }

    @Test
    void getInput_availability() throws Exception {
        String input = "AVAILABILITY 1";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);

        commandLineRunner.getInput();
        verify(buyerService, times(1)).checkSeatAvailability(1L);
    }

    @Test
    void getInput_book() throws Exception {
        String input = "BOOK 1 123456 C2,C1";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);

        commandLineRunner.getInput();
        verify(buyerService, times(1)).bookShow(1L, 123456, "C2,C1");
    }

    @Test
    void getInput_cancel() throws Exception {
        String input = "CANCEL 1 123456";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);

        commandLineRunner.getInput();
        verify(buyerService, times(1)).cancelBooking(1L, 123456);
    }

    @Test
    void handleCommand() {
    }

    @Test
    void mockConsole() {
    }
}