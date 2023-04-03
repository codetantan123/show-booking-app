package com.project.showbooking.service;

public interface BuyerService {
    void checkSeatAvailability(Long showNumber);

    void bookShow(Long showNumber, int buyerPhoneNumber, String seats) throws Exception;

    void cancelBooking(Long ticketNumber, int phoneNumber) throws Exception;
}
