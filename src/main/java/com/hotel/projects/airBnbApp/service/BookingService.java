package com.hotel.projects.airBnbApp.service;

import com.hotel.projects.airBnbApp.dto.BookingDto;
import com.hotel.projects.airBnbApp.dto.BookingRequest;
import com.hotel.projects.airBnbApp.dto.GuestDto;
import org.jspecify.annotations.Nullable;

import java.util.List;

public interface BookingService {
    BookingDto initialiseBooking(BookingRequest bookingRequest);

    BookingDto addGuests(Long bookingId, List<GuestDto> guestDtoList);
}
