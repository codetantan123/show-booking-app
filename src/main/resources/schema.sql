DROP TABLE IF EXISTS seat;
CREATE TABLE seat (
    seat_number NUMERIC,
    ticket_number NUMERIC,
    show_number NUMERIC
);

DROP TABLE IF EXISTS ticket;
CREATE TABLE ticket (
    ticket_number NUMERIC,
    booking_time DATETIME,
    buyer_phone NUMERIC,
    show_number NUMERIC
);

DROP TABLE IF EXISTS show;
CREATE TABLE show (
    show_number NUMERIC,
    no_of_rows INT,
    no_per_row INT,
    cancellation_window TIME
);
