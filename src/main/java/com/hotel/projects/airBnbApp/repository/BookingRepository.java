package com.hotel.projects.airBnbApp.repository;

import com.hotel.projects.airBnbApp.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking,Long> {
}
