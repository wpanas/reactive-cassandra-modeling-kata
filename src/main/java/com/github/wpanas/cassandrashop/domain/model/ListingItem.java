package com.github.wpanas.cassandrashop.domain.model;

import org.springframework.data.cassandra.core.mapping.Column;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

abstract class ListingItem {
    @Column
    private final String name;
    @Column
    private final BigDecimal unitPrice;
    @Column
    private final Integer availableUnits;
    @Column
    private final Boolean finished;
    @Column
    private final LocalDate endDate;

    ListingItem(String name, BigDecimal unitPrice, Integer availableUnits, Boolean finished, LocalDate endDate) {
        this.name = name;
        this.unitPrice = unitPrice;
        this.availableUnits = availableUnits;
        this.finished = finished;
        this.endDate = endDate;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public Integer getAvailableUnits() {
        return availableUnits;
    }

    public Boolean getFinished() {
        return finished;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public abstract UUID getItemId();
}
