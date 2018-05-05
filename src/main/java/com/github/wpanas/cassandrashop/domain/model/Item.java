package com.github.wpanas.cassandrashop.domain.model;

import com.github.wpanas.cassandrashop.domain.event.ItemPurchasedEvent;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;
import org.springframework.data.domain.AbstractAggregateRoot;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

import static com.datastax.driver.core.DataType.Name.DATE;

@Table("master_item")
public class Item extends AbstractAggregateRoot<Item> {
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
    private final Set<String> tags;
    private final Map<UUID, Integer> purchases;

    public Item(UUID id, UUID userId, String name, String description, BigDecimal unitPrice, Integer offeredUnits, Integer availableUnits, LocalDate startDate, LocalDate endDate, Boolean auctionFinished, Set<String> tags) {
        this(id, userId, name, description, unitPrice, offeredUnits, availableUnits, startDate, endDate, auctionFinished, tags, Collections.emptyMap());
    }

    @PersistenceConstructor
    private Item(UUID id, UUID userId, String name, String description, BigDecimal unitPrice, Integer offeredUnits, Integer availableUnits, LocalDate startDate, LocalDate endDate, Boolean auctionFinished, Set<String> tags, Map<UUID, Integer> purchases) {
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
        this.tags = ImmutableSet.copyOf(Optional.ofNullable(tags).orElse(Collections.emptySet()));
        this.purchases = ImmutableMap.copyOf(Optional.ofNullable(purchases).orElse(Collections.emptyMap()));
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

    public Set<String> getTags() {
        return tags;
    }

    public Map<UUID, Integer> getPurchases() {
        return purchases;
    }

    public Item completePurchase(int availableUnitsAfter, boolean auctionFinished) {
        registerEvent(new ItemPurchasedEvent(getId(), availableUnitsAfter, auctionFinished));
        return this;
    }
}
