package com.hotel.projects.airBnbApp.dto;

import com.hotel.projects.airBnbApp.entity.Guest;
import com.hotel.projects.airBnbApp.entity.Hotel;
import com.hotel.projects.airBnbApp.entity.Room;
import com.hotel.projects.airBnbApp.entity.User;
import com.hotel.projects.airBnbApp.entity.enums.BookingStatus;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Data
public class BookingDto {
    private Long id;
    private Integer roomsCount;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private BookingStatus bookingStatus;
    private Set<GuestDto> guests;
}
