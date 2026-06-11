package com.hotel.projects.airBnbApp.strategy;

import com.hotel.projects.airBnbApp.entity.Inventory;

import java.math.BigDecimal;

public class BasePriceStrategy implements PricingStratregy {
    @Override
    public BigDecimal calculatePrice(Inventory inventory) {

        return inventory.getRoom().getBasePrice();
    }
}
