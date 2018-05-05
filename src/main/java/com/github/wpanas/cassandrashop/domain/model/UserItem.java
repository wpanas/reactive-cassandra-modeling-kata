package com.github.wpanas.cassandrashop.domain.model;

import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Table
public class UserItem extends ListingItem {
    @PrimaryKey
    private final UserItemKey id;

    @PersistenceConstructor
    private UserItem(String name, BigDecimal unitPrice, Integer availableUnits, Boolean finished, LocalDate endDate, UserItemKey id) {
        super(name, unitPrice, availableUnits, finished, endDate);
        this.id = id;
    }

    public UserItem(
            UUID userId,
            UUID itemId,
            String name,
            BigDecimal unitPrice,
            Integer availableUnits,
            Boolean finished,
            LocalDate endDate
            ) {
        this(name, unitPrice, availableUnits, finished, endDate, new UserItemKey(userId, itemId));
    }

    public UserItemKey getId() {
        return id;
    }

    @Override
    public UUID getItemId() {
        return id.getItemId();
    }
}
