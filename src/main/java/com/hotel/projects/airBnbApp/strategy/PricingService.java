package com.hotel.projects.airBnbApp.strategy;

import com.hotel.projects.airBnbApp.entity.Inventory;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;

@Service
public class PricingService {

    public BigDecimal calculateDynamicPricing(Inventory inventory){
        PricingStratregy pricingStrategy = new BasePriceStrategy();

        // apply the Additional strategy
        pricingStrategy = new SurgePricingStratregy(pricingStrategy);
        pricingStrategy = new OccupancyPricingStratregy(pricingStrategy);
        pricingStrategy = new UrgencyPricingStratregy(pricingStrategy);
        pricingStrategy = new HolidayPricingStratregy(pricingStrategy);

        return  pricingStrategy.calculatePrice(inventory);
    }
}
