package com.github.wpanas.cassandrashop.domain.model;

import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static com.datastax.driver.core.DataType.Name.DATE;

@Table("master_item")
public class Item {
    @PrimaryKey
    private final UUID id;
    private final UUID userId;
    private final String name;
    private final String description;
    private final BigDecimal unitPrice;
    private final Integer offeredUnits;
    private final Integer availableUnits;
    @CassandraType(type = DATE)
    private final LocalDate startDate;
    @CassandraType(type = DATE)
    private final LocalDate endDate;
    private final Boolean auctionFinished;

    public Item(UUID id, UUID userId, String name, String description, BigDecimal unitPrice, Integer offeredUnits, Integer availableUnits, LocalDate startDate, LocalDate endDate, Boolean auctionFinished) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.description = description;
        this.unitPrice = unitPrice;
        this.offeredUnits = offeredUnits;
        this.availableUnits = availableUnits;
        this.startDate = startDate;
        this.endDate = endDate;
        this.auctionFinished = auctionFinished;
    }

    public UUID getId() {
        return id;
    }

    public UUID getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public Integer getOfferedUnits() {
        return offeredUnits;
    }

    public Integer getAvailableUnits() {
        return availableUnits;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public Boolean getAuctionFinished() {
        return auctionFinished;
    }
}
