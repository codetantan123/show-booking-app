package com.project.showbooking.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Entity
@Table(name = "ticket")
@NoArgsConstructor
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long ticketNumber;
    private LocalDateTime bookingTime;
    @Column(unique = true)
    private int buyerPhone;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "show_number")
    private Show show;
    @OneToMany(mappedBy = "ticket", fetch = FetchType.LAZY)
    private List<Seat> seats;

    public Ticket(Show show, int buyerPhoneNumber, LocalDateTime bookingTime) {
        this.show = show;
        this.buyerPhone = buyerPhoneNumber;
        this.bookingTime = bookingTime;
    }

    @Override
    public String toString() {
        String seatNumbers = seats.stream().map(Seat::getSeatNumber)
                .collect(Collectors.joining(", "));
        return "Ticket Number " + ticketNumber + " | Buyer Phone Number: " + buyerPhone + " | Seat Numbers: " + seatNumbers;
    }
}
