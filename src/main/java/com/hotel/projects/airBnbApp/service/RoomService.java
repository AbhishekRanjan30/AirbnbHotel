package com.hotel.projects.airBnbApp.service;

import com.hotel.projects.airBnbApp.dto.RoomDto;
import org.jspecify.annotations.Nullable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface RoomService {
    RoomDto createRoom(Long hotelId, RoomDto roomDto);

    List<RoomDto> getAllRoomsInHotel(Long hotelId);

    RoomDto getRoomById(Long roomId,Long hotelId);

    void deleteById(Long roomId);
}
