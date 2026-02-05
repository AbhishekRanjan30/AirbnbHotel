package com.hotel.projects.airBnbApp.service.impl;

import com.hotel.projects.airBnbApp.dto.HotelDto;
import com.hotel.projects.airBnbApp.entity.Hotel;
import com.hotel.projects.airBnbApp.exception.ResourceNotFoundException;
import com.hotel.projects.airBnbApp.repository.HotelRepository;
import com.hotel.projects.airBnbApp.service.HotelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class HotelServiceImpl implements HotelService {

    private final HotelRepository hotelRepository;
    private final ModelMapper modelMapper;

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
    public void  deleteHotelById(Long hotelId) {
        boolean exist = hotelRepository.existsById(hotelId);
        if(!exist) throw new ResourceNotFoundException("Resource is not found for the Id :- {}" + hotelId);
        hotelRepository.deleteById(hotelId);
    }

    @Override
    public void activateHotel(Long hotelId) {
        log.info("Activating hotel for hotel id :- {} ",hotelId);
        Hotel hotel = hotelRepository
                .findById(hotelId)
                .orElseThrow( ()->
                        new ResourceNotFoundException("Resource not found with the Id : {}"+ hotelId));

        hotel.setActive(true);
        hotelRepository.save(hotel);
    }
}
