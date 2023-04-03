package com.project.showbooking.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@Table(name = "show")
@NoArgsConstructor
public class Show {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long showNumber;
    @Column(name = "no_of_rows")
    private int numberOfRows;
    @Column(name = "no_per_row")
    private int numOfSeatsPerRow;
    private int cancellationWindow; //Cancellation window in minutes
    @OneToMany(mappedBy = "show", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Ticket> tickets;
    @OneToMany(mappedBy = "show", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Seat> seats;

    public Show(int numberOfRows, int numberOfSeatsPerRow, int cancellationWindow) {
        this.numberOfRows = numberOfRows;
        this.numOfSeatsPerRow = numberOfSeatsPerRow;
        this.cancellationWindow = cancellationWindow;
    }
}
