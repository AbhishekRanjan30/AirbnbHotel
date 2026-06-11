package com.hotel.projects.airBnbApp.strategy;

import com.hotel.projects.airBnbApp.entity.Inventory;
import lombok.RequiredArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;

@RequiredArgsConstructor
public class UrgencyPricingStratregy implements PricingStratregy{

    private final PricingStratregy wrapped;

    @Override
    public BigDecimal calculatePrice(Inventory inventory) {
        BigDecimal price = wrapped.calculatePrice(inventory);

        LocalDate today = LocalDate.now();
        if(!inventory.getDate().isBefore(today) && inventory.getDate().isBefore(today.plusDays(7))){
            price = price.multiply(BigDecimal.valueOf(1.15));

        }
        return price;
    }
}
