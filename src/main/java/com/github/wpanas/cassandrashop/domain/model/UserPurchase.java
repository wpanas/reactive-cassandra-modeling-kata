package com.github.wpanas.cassandrashop.domain.model;

import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.math.BigDecimal;
import java.util.UUID;

@Table("user_purchase")
public class UserPurchase {
    @PrimaryKey
    private final UserPurchaseKey id;

    private final UUID itemId;

    private final BigDecimal unitPrice;

    private final Integer quantity;

    @PersistenceConstructor
    private UserPurchase(UserPurchaseKey id, UUID itemId, BigDecimal unitPrice, Integer quantity) {
        this.id = id;
        this.itemId = itemId;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
    }

    public UserPurchase(UUID userId, UUID purchaseId, UUID itemId, BigDecimal unitPrice, Integer quantity) {
        this(new UserPurchaseKey(userId, purchaseId), itemId, unitPrice, quantity);
    }

    public UserPurchaseKey getId() {
        return id;
    }

    public UUID getItemId() {
        return itemId;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public Integer getQuantity() {
        return quantity;
    }
}
