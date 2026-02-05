package com.hotel.projects.airBnbApp.controller;

import com.hotel.projects.airBnbApp.dto.HotelDto;
import com.hotel.projects.airBnbApp.service.HotelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("admin/hotels")
public class HotelController {

    private final HotelService hotelService;

    @PostMapping
    ResponseEntity<HotelDto> createNewHotel(@RequestBody HotelDto hotelDto){
        log.info("Attempting to create new Hotel with name: {} " ,  hotelDto.getName());
        HotelDto hotel = hotelService.createNewHotel(hotelDto);
        return new ResponseEntity<>(hotel, HttpStatus.CREATED);
    }

    @GetMapping("{hotelId}")
    public ResponseEntity<HotelDto> getHotelById(@PathVariable Long hotelId){
        log.info("Attempting getting hotel with id: {} ", hotelId);
        HotelDto hotel = hotelService.getHotelById(hotelId);
        return ResponseEntity.ok(hotel);
    }

    @PutMapping("{hotelId}")
    public ResponseEntity<HotelDto> updateHotelById(@PathVariable Long hotelId,@RequestBody HotelDto hotelDto){
        log.info("Attempting to update the Hotel for the Id :- {} ", hotelId);
        HotelDto updatedDto =  hotelService.updateHotelById(hotelId,hotelDto);
        return ResponseEntity.ok(updatedDto);
    }

    @DeleteMapping("{hotelId}")
    public ResponseEntity<Void> deleteHotelById(@PathVariable Long hotelId){
        log.info("Attempting to delete the hotel for Id :- {}", hotelId);
        hotelService.deleteHotelById(hotelId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("{hotelId}")
    public ResponseEntity<Void> activateHotel(@PathVariable Long hotelId){
        log.info("Attempting Activating the hotel for hotel Id :- {}",hotelId);
        hotelService.activateHotel(hotelId);
        return ResponseEntity.noContent().build();
    }
}
