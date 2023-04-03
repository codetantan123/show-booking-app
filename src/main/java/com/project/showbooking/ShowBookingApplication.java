package com.project.showbooking;

import com.project.showbooking.utils.CommandLineRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ShowBookingApplication {

    static CommandLineRunner commandLineRunner;

    @Autowired
    public ShowBookingApplication(CommandLineRunner commandLineRunner) {
        this.commandLineRunner = commandLineRunner;
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(ShowBookingApplication.class, args);
        commandLineRunner.mockConsole();
        while (true) {
            commandLineRunner.getInput();
        }
    }
}
