package com.hotel.projects.airBnbApp.strategy;

import com.hotel.projects.airBnbApp.entity.Inventory;
import lombok.RequiredArgsConstructor;
import java.math.BigDecimal;

@RequiredArgsConstructor
public class SurgePricingStratregy implements PricingStratregy {

    private final PricingStratregy wrapped;

    @Override
    public BigDecimal calculatePrice(Inventory inventory) {
       BigDecimal price = wrapped.calculatePrice(inventory);
       return price.multiply(inventory.getSurgeFactor());
    }
}
