package com.github.wpanas.cassandrashop.domain.model;

import com.datastax.driver.core.DataType;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.math.BigDecimal;
import java.util.UUID;

@Table("master_purchase")
public class Purchase {
    @PrimaryKey
    private final UUID id;

    private final UUID itemId;

    private final UUID userId;

    private final BigDecimal unitPrice;

    private final Integer quantity;

    @CassandraType(type = DataType.Name.TEXT)
    private final PurchaseStatus status;

    public Purchase(UUID id, UUID itemId, UUID userId, BigDecimal unitPrice, Integer quantity) {
        this(id, itemId, userId, unitPrice, quantity, PurchaseStatus.ENTERED);
    }

    @PersistenceConstructor
    public Purchase(UUID id, UUID itemId, UUID userId, BigDecimal unitPrice, Integer quantity, PurchaseStatus status) {
        this.id = id;
        this.itemId = itemId;
        this.userId = userId;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.status = status;
    }

    public UUID getId() {
        return id;
    }

    public UUID getItemId() {
        return itemId;
    }

    public UUID getUserId() {
        return userId;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public PurchaseStatus getStatus() {
        return status;
    }
}
