package com.hotel.projects.airBnbApp.dto;

import com.hotel.projects.airBnbApp.entity.User;
import com.hotel.projects.airBnbApp.entity.enums.Gender;
import jakarta.persistence.*;
import lombok.Data;

@Data
public class GuestDto {
    private long id;
    private User user;
    private String name;
    private Gender gender;
    private Integer age;
}
