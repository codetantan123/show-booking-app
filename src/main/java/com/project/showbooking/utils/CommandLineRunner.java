package com.project.showbooking.utils;

import com.project.showbooking.service.AdminService;
import com.project.showbooking.service.BuyerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class CommandLineRunner {
    AdminService adminService;
    BuyerService buyerService;

    @Autowired
    public CommandLineRunner(AdminService adminService, BuyerService buyerService) {
        this.adminService = adminService;
        this.buyerService = buyerService;
    }

    public void getInput() {
        System.out.println("Enter command: ");
        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();
        try {
            handleCommand(line.toUpperCase());
        } catch (Exception e) {
            System.out.println("Error handling command: ");
            System.out.println(e.getMessage());
//            e.printStackTrace(System.out);
        }
    }

    public void handleCommand(String commandStr) throws Exception {
        String[] cmdArray = commandStr.split(" ");
        switch (cmdArray[0]) {
            case "SETUP":
                adminService.setupShow(
                        Integer.parseInt(cmdArray[1]),
                        Integer.parseInt(cmdArray[2]),
                        Integer.parseInt(cmdArray[3]));
                break;
            case "VIEW":
                adminService.viewBookings(Long.parseLong(cmdArray[1]));
                break;
            case "AVAILABILITY":
                buyerService.checkSeatAvailability(Long.parseLong(cmdArray[1]));
                break;
            case "BOOK":
                buyerService.bookShow(
                        Long.parseLong(cmdArray[1]),
                        Integer.parseInt(cmdArray[2]),
                        cmdArray[3]);
                break;
            case "CANCEL":
                buyerService.cancelBooking(
                        Long.parseLong(cmdArray[1]),
                        Integer.parseInt(cmdArray[2]));
                break;
            default:
                System.out.println("Command not recognized. Please try again. ");
                break;
        }
    }

    public void mockConsole() throws Exception {
        adminService.setupShow(3, 3, 3);
        buyerService.checkSeatAvailability(1L);
        buyerService.bookShow(1L, 123456, "A2,B3,B1,C2,C1");
        buyerService.checkSeatAvailability(1L);
        buyerService.bookShow(1L, 4321, "B2,C3");
        buyerService.cancelBooking(1L, 123456);
        buyerService.checkSeatAvailability(1L);
        buyerService.bookShow(1L, 987654, "B1,B3");
        adminService.viewBookings(1L);
    }
}
