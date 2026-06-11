package com.hotel.projects.airBnbApp.dto;

import com.hotel.projects.airBnbApp.entity.HotelContactInfo;
import lombok.Data;


@Data
public class HotelDto {
    private Long id;
    private String name;
    private String city;
    private String[] photos;
    private String[] amenities;
    private HotelContactInfo contactInfo;
    private boolean active;
}
