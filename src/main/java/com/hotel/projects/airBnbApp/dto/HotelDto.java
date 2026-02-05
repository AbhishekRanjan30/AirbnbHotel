package com.hotel.projects.airBnbApp.dto;

import com.hotel.projects.airBnbApp.entity.HotelContactInfo;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

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
