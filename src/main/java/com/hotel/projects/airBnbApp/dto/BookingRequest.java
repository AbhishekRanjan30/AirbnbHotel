package com.hotel.projects.airBnbApp.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class BookingRequest {
    private Long hotelId;
    private Long roomId;
    private LocalDate CheckInDate;
    private LocalDate CheckOutDate;
    private Integer roomsCount;
}
