package com.hotel.projects.airBnbApp.strategy;

import com.hotel.projects.airBnbApp.entity.Inventory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@RequiredArgsConstructor
public class HolidayPricingStratregy implements PricingStratregy{

    private final PricingStratregy wrapped;

    @Override
    public BigDecimal calculatePrice(Inventory inventory) {
        BigDecimal price = wrapped.calculatePrice(inventory);
        boolean isTodayHoliday = true; // Call an API to check with local data
        if(isTodayHoliday){
            price = price.multiply(BigDecimal.valueOf(1.25));
        }
        return price;
    }
}
