package com.project.showbooking.service;

public interface AdminService {
    void setupShow(int numberOfRows, int numberOfSeatsPerRow, int cancellationWindow) throws Exception;

    void viewBookings(long showNumber) throws Exception;
}
