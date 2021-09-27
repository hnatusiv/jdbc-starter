package com.dmdev.jdbc.starter.entity;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

public record Flight(Long id,
                     String flightNo,
                     LocalDateTime departureDate,
                     String departureAirportCode,
                     LocalDateTime arrivalDate,
                     String ArrivalAirportCode,
                     Integer aircraftId,
                     String status,
                     List<Ticket> tickets) {
    public Flight(long id, String flight_no, LocalDateTime departure_date, String departure_airport_code, LocalDateTime arrival_date, String arrival_airport_code, int aircraft_id, String status) {
    }
}
