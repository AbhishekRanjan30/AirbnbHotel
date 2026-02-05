package com.hotel.projects.airBnbApp.controller;

import com.hotel.projects.airBnbApp.dto.RoomDto;
import com.hotel.projects.airBnbApp.service.RoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("admin/hotels/{hotelId}/rooms")
@RequiredArgsConstructor
@Slf4j
public class RoomController {

    private final RoomService roomService;

    @PostMapping
    ResponseEntity<RoomDto> createRoom(@PathVariable Long hotelId, @RequestBody RoomDto roomDto){
        log.info("Attempting creating room for the Hotel Id :- {} " , hotelId);
        RoomDto room = roomService.createRoom(hotelId,roomDto);
        return new ResponseEntity<>(room,HttpStatus.CREATED);
    }

    @GetMapping()
    ResponseEntity<List<RoomDto>> getAllRoomsInHotel(@PathVariable Long hotelId){
    return ResponseEntity.ok(roomService.getAllRoomsInHotel(hotelId));
    }

    @GetMapping("/{roomId}")
    ResponseEntity<RoomDto> getRoomById(@PathVariable Long roomId, @PathVariable Long hotelId){
        return ResponseEntity.ok(roomService.getRoomById(roomId,hotelId));
    }

    @DeleteMapping("/{roomId}")
    ResponseEntity<Void> deleteRoomById(@PathVariable Long roomId){
        roomService.deleteById(roomId);
        return ResponseEntity.noContent().build();
    }
}
