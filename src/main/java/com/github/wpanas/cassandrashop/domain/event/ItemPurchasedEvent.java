package com.github.wpanas.cassandrashop.domain.event;

import java.util.Objects;
import java.util.UUID;

public class ItemPurchasedEvent {
    private final UUID itemId;
    private final Integer availableUnits;
    private final Boolean auctionFinished;

    public ItemPurchasedEvent(UUID itemId, Integer availableUnits, Boolean auctionFinished) {
        this.itemId = itemId;
        this.availableUnits = availableUnits;
        this.auctionFinished = auctionFinished;
    }

    public UUID getItemId() {
        return itemId;
    }

    Integer getAvailableUnits() {
        return availableUnits;
    }

    Boolean getAuctionFinished() {
        return auctionFinished;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ItemPurchasedEvent)) return false;
        ItemPurchasedEvent that = (ItemPurchasedEvent) o;
        return Objects.equals(itemId, that.itemId) &&
                Objects.equals(availableUnits, that.availableUnits) &&
                Objects.equals(auctionFinished, that.auctionFinished);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemId, availableUnits, auctionFinished);
    }
}
