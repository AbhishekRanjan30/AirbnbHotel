package com.hotel.projects.airBnbApp.service;

import com.hotel.projects.airBnbApp.dto.HotelDto;
import com.hotel.projects.airBnbApp.dto.HotelSearchRequest;
import com.hotel.projects.airBnbApp.entity.Room;
import org.springframework.data.domain.Page;

public interface InventoryService {

    void initializeRoomForAYear(Room room);

    void deleteFutureInventories(Room room);

    Page<HotelDto> searchHotels(HotelSearchRequest hotelSearchRequest);
}
