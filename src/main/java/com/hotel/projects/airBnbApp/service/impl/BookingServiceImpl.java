package com.hotel.projects.airBnbApp.service.impl;

import com.hotel.projects.airBnbApp.dto.BookingDto;
import com.hotel.projects.airBnbApp.dto.BookingRequest;
import com.hotel.projects.airBnbApp.dto.GuestDto;
import com.hotel.projects.airBnbApp.entity.*;
import com.hotel.projects.airBnbApp.entity.enums.BookingStatus;
import com.hotel.projects.airBnbApp.exception.ResourceNotFoundException;
import com.hotel.projects.airBnbApp.repository.*;
import com.hotel.projects.airBnbApp.service.BookingService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final HotelRepository hotelRepository;
    private final RoomRepository roomRepository;
    private final InventoryRepository inventoryRepository;
    private final ModelMapper modelMapper;
    private final GuestRepository guestRepository;

    @Override
    @Transactional
    public BookingDto initialiseBooking(BookingRequest bookingRequest) {
        log.info("Initialising booking for hotel : {} , room : {}, date {} - {}",bookingRequest.getHotelId(),
                bookingRequest.getRoomId(),bookingRequest.getCheckInDate(),bookingRequest.getCheckOutDate());

        Hotel hotel = hotelRepository.findById(bookingRequest.getHotelId())
                .orElseThrow(() -> new
                        ResourceNotFoundException("Hotel is not found with the Id :- "+ bookingRequest.getHotelId()));

        Room room = roomRepository.findById(bookingRequest.getRoomId())
                .orElseThrow(()-> new
                        ResourceNotFoundException("Room is not found with the Id :- " + bookingRequest.getRoomId()));

       List<Inventory> inventoryList = inventoryRepository.findAndLockAvailableInventory(room.getId(),bookingRequest.getCheckInDate(),bookingRequest.getCheckOutDate(),bookingRequest.getRoomsCount());

       long daysCount = ChronoUnit.DAYS.between(bookingRequest.getCheckInDate(),
               bookingRequest.getCheckOutDate()) + 1;
       if(inventoryList.size() != daysCount){
           throw new IllegalArgumentException("Room is not available anymore");
       }

       // Reserved the room . update the bookedCount of inventory
       for(Inventory inventory : inventoryList){
           inventory.setReservedCount(inventory.getReservedCount() + bookingRequest.getRoomsCount());
       }
       inventoryRepository.saveAll(inventoryList);


        Booking booking = Booking.builder()
                .bookingStatus(BookingStatus.RESERVED)
                .hotel(hotel)
                .room(room)
                .checkInDate(bookingRequest.getCheckInDate())
                .checkOutDate(bookingRequest.getCheckOutDate())
                .user(getCurrentUser())
                .roomsCount(bookingRequest.getRoomsCount())
                .amount(BigDecimal.TEN)
                .build();

        booking = bookingRepository.save(booking);
        return modelMapper.map(booking, BookingDto.class);
    }

    @Override
    @Transactional
    public BookingDto addGuests(Long bookingId, List<GuestDto> guestDtoList) {
        log.info("Adding guest for the booking id : {}", bookingId);

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(()-> new ResourceNotFoundException("Booking not found with the Id :"+ bookingId));

        if(hasBookingExpired(booking)){
            throw new IllegalStateException("Booking has been expired");
        }

        if(booking.getBookingStatus() != BookingStatus.RESERVED){
            throw new IllegalStateException("Booking is not in reserved State, cannot add guests");
        }

        for(GuestDto guests : guestDtoList){
            Guest guest = modelMapper.map(guests , Guest.class);
            guest.setUser(getCurrentUser());
            guest = guestRepository.save(guest);
            booking.getGuests().add(guest);
        }
        booking.setBookingStatus(BookingStatus.GUESTS_ADDED);
        booking = bookingRepository.save(booking);
        return modelMapper.map(booking, BookingDto.class);
    }

    public Boolean hasBookingExpired(Booking booking){
        return booking.getCreatedAt().plusMinutes(10).isBefore(LocalDateTime.now());
    }

    public User getCurrentUser(){
        User user = new User();
        user.setId(1L); // TODO : temporary this will changed
        return user;
    }
}
