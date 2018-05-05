package com.github.wpanas.cassandrashop.domain.model;

import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;

import java.util.Objects;
import java.util.UUID;

@PrimaryKeyClass
public class TagItemKey {
    @PrimaryKeyColumn(ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    private final String tag;

    @PrimaryKeyColumn(ordinal = 1, type = PrimaryKeyType.CLUSTERED, ordering = Ordering.DESCENDING)
    private final UUID itemId;

    public TagItemKey(String tag, UUID itemId) {
        this.tag = tag;
        this.itemId = itemId;
    }

    public String getTag() {
        return tag;
    }

    UUID getItemId() {
        return itemId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TagItemKey)) return false;
        TagItemKey that = (TagItemKey) o;
        return Objects.equals(tag, that.tag) &&
                Objects.equals(itemId, that.itemId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tag, itemId);
    }
}
