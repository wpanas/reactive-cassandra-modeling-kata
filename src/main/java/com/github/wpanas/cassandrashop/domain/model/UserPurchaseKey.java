package com.github.wpanas.cassandrashop.domain.model;

import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@PrimaryKeyClass
public class UserPurchaseKey implements Serializable {
    @PrimaryKeyColumn(ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    private final UUID userId;

    @PrimaryKeyColumn(ordinal = 1, type = PrimaryKeyType.CLUSTERED, ordering = Ordering.DESCENDING)
    private final UUID purchaseId;

    public UserPurchaseKey(UUID userId, UUID purchaseId) {
        this.userId = userId;
        this.purchaseId = purchaseId;
    }

    public UUID getUserId() {
        return userId;
    }

    public UUID getPurchaseId() {
        return purchaseId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserPurchaseKey)) return false;
        UserPurchaseKey that = (UserPurchaseKey) o;
        return Objects.equals(userId, that.userId) &&
                Objects.equals(purchaseId, that.purchaseId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, purchaseId);
    }
}
