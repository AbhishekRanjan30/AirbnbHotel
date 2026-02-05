package com.hotel.projects.airBnbApp.service;

import com.hotel.projects.airBnbApp.dto.HotelDto;
import com.hotel.projects.airBnbApp.entity.Hotel;

public interface HotelService {

    HotelDto createNewHotel(HotelDto hotelDto);

    HotelDto getHotelById(Long id);

    HotelDto updateHotelById(Long hotelId, HotelDto hotelDto);

    void deleteHotelById(Long hotelId);

    void activateHotel(Long hotelId);
}
