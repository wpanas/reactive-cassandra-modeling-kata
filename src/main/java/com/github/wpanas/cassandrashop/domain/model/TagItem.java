package com.github.wpanas.cassandrashop.domain.model;

import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Table
public class TagItem extends ListingItem {
    @PrimaryKey
    private final TagItemKey id;

    @PersistenceConstructor
    private TagItem(String name, BigDecimal unitPrice, Integer availableUnits, Boolean finished, LocalDate endDate, TagItemKey id) {
        super(name, unitPrice, availableUnits, finished, endDate);
        this.id = id;
    }

    public TagItem(String tag, UUID itemId, String name, BigDecimal unitPrice, Integer availableUnits, Boolean auctionFinished, LocalDate endDate) {
        this(name, unitPrice, availableUnits, auctionFinished, endDate, new TagItemKey(tag, itemId));
    }

    @Override
    public UUID getItemId() {
        return this.id.getItemId();
    }

    public TagItemKey getId() {
        return id;
    }
}
