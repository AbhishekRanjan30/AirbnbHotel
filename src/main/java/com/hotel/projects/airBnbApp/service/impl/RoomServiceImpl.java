package com.hotel.projects.airBnbApp.service.impl;

import com.hotel.projects.airBnbApp.dto.RoomDto;
import com.hotel.projects.airBnbApp.entity.Hotel;
import com.hotel.projects.airBnbApp.entity.Room;
import com.hotel.projects.airBnbApp.exception.ResourceNotFoundException;
import com.hotel.projects.airBnbApp.repository.HotelRepository;
import com.hotel.projects.airBnbApp.repository.RoomRepository;
import com.hotel.projects.airBnbApp.service.InventoryService;
import com.hotel.projects.airBnbApp.service.RoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    private  final HotelRepository hotelRepository;
    private final RoomRepository roomRepository;
    private final ModelMapper modelMapper;
    private final InventoryService inventoryService;

    @Override
    public RoomDto createRoom(Long hotelId, RoomDto roomDto) {
        log.info("Creating new Room for the Hotel Id :- {} ",hotelId);

        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(()-> new ResourceNotFoundException("Hotel not found with the Hotel Id :- {} "+ hotelId + " Since Then Not possible to create Rooms"));
        Room room = modelMapper.map(roomDto, Room.class);
        room.setHotel(hotel);
        room = roomRepository.save(room);

        if(hotel.isActive()){
            inventoryService.initializeRoomForAYear(room);
        }
        return modelMapper.map(room,RoomDto.class);
    }

    @Override
    public List<RoomDto> getAllRoomsInHotel(Long hotelId) {
        log.info("Creating new Room for the Hotel Id :- {} ",hotelId);
        ifExist(hotelId);
        Hotel hotel = hotelRepository.findById(hotelId).get();
        return hotel.getRooms()
                .stream()
                .map(element -> modelMapper.map(element, RoomDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public RoomDto getRoomById(Long roomId,Long hotelId) {
        log.info("Getting Room for the Room Id :- {} ",roomId);
        ifExist(hotelId);
        Room room = roomRepository
                .findById(roomId)
                .orElseThrow(()->
                        new ResourceNotFoundException("Room is not found with the room id :"+roomId));
        return modelMapper.map(room, RoomDto.class);
    }

    @Override
    public void deleteById(Long roomId) {
        boolean exist = roomRepository.existsById(roomId);
        if(!exist) throw new ResourceNotFoundException("Room is not found for the room id ;- "+ roomId);
        roomRepository.deleteById(roomId);
    }

    public Hotel ifExist(Long hotelId){
        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(()-> new ResourceNotFoundException("Hotel not found with the Hotel Id :- "+ hotelId));
        return hotel;
    }
}
