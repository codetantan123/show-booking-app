package com.project.showbooking.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "seat")
@NoArgsConstructor
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String seatNumber;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ticket_number")
    private Ticket ticket;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "show_number", nullable = false)
    private Show show;

    public Seat(String seatNumber, Show show) {
        this.seatNumber = seatNumber;
        this.show = show;
    }
}
