package com.hotel.projects.airBnbApp.service.impl;

import com.hotel.projects.airBnbApp.dto.HotelDto;
import com.hotel.projects.airBnbApp.dto.HotelInfoDto;
import com.hotel.projects.airBnbApp.dto.RoomDto;
import com.hotel.projects.airBnbApp.entity.Hotel;
import com.hotel.projects.airBnbApp.entity.Room;
import com.hotel.projects.airBnbApp.exception.ResourceNotFoundException;
import com.hotel.projects.airBnbApp.repository.HotelRepository;
import com.hotel.projects.airBnbApp.repository.RoomRepository;
import com.hotel.projects.airBnbApp.service.HotelService;
import com.hotel.projects.airBnbApp.service.InventoryService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class HotelServiceImpl implements HotelService {

    private final HotelRepository hotelRepository;
    private final ModelMapper modelMapper;
    private final InventoryService inventoryService;
    private final RoomRepository roomRepository;

    @Override
    public HotelDto createNewHotel(HotelDto hotelDto) {
        log.info("Creating a new Hotel with name: {} ", hotelDto.getName());
        Hotel hotel = modelMapper.map(hotelDto,Hotel.class);
        hotel.setActive(false);
        hotel = hotelRepository.save(hotel);
        log.info("Created a new Hotel with Id: {} ",hotel.getId());
        return modelMapper.map(hotel,HotelDto.class);
    }

    @Override
    public HotelDto getHotelById(Long id) {
        log.info("Getting hotel by Id: {}",id);
        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Resource not found with the Id ;- "+ id));
        return modelMapper.map(hotel,HotelDto.class);
    }

    @Override
    public HotelDto updateHotelById(Long hotelId, HotelDto hotelDto) {
        Hotel hotel = hotelRepository
                .findById(hotelId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Resource not found with the Id :- "+ hotelId));
        log.info("Updating the hotel for hotel id : {}",  hotelId);
        modelMapper.map(hotelDto,hotel);
        hotel.setId(hotelId);
        hotelRepository.save(hotel);
        return modelMapper.map(hotel, HotelDto.class);
    }

    @Override
    @Transactional
    public void  deleteHotelById(Long hotelId) {
        Hotel hotel = hotelRepository
                .findById(hotelId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Resource not found with the Id :- "+ hotelId));
        for(Room room : hotel.getRooms()){
            inventoryService.deleteFutureInventories(room);
            roomRepository.deleteById(room.getId());
        }
        hotelRepository.deleteById(hotelId);
    }

    @Override
    @Transactional
    public void activateHotel(Long hotelId) {
        log.info("Activating hotel for hotel id :- {} ",hotelId);
        Hotel hotel = hotelRepository
                .findById(hotelId)
                .orElseThrow( ()->
                        new ResourceNotFoundException("Resource not found with the Id :"+ hotelId));

        hotel.setActive(true);
        hotelRepository.save(hotel);

        //Assuming do it once

        for(Room room : hotel.getRooms()){
            inventoryService.initializeRoomForAYear(room);
        }
    }

    @Override
    public HotelInfoDto getHotelInfoById(Long hotelId) {
        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow( () -> new ResourceNotFoundException("Hotel is not found with the Id :- " + hotelId));

        List<RoomDto> rooms = hotel.getRooms()
                .stream()
                .map((element) -> modelMapper.map(element, RoomDto.class))
                .toList();

        return new HotelInfoDto(modelMapper.map(hotel, HotelDto.class), rooms);
    }

    @Override
    public List<HotelDto> getAllHotels() {
        List<Hotel> allHotels = hotelRepository.findAll();
        return allHotels
                .stream()
                .map(hotel ->
                        modelMapper.map(hotel, HotelDto.class))
                .toList();
    }
}
