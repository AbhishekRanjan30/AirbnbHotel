package com.hotel.projects.airBnbApp.dto;

import com.hotel.projects.airBnbApp.entity.Hotel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotelPriceDTO {
    private Hotel hotel;
    private Double price;
}
