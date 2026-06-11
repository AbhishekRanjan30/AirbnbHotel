package com.hotel.projects.airBnbApp.strategy;

import com.hotel.projects.airBnbApp.entity.Inventory;

import java.math.BigDecimal;

public interface PricingStratregy {

    public BigDecimal calculatePrice(Inventory inventory);
}
