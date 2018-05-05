package com.github.wpanas.cassandrashop.domain.event;

import com.github.wpanas.cassandrashop.domain.model.*;
import com.github.wpanas.cassandrashop.domain.repository.ItemRepository;
import com.github.wpanas.cassandrashop.domain.repository.TagItemRepository;
import com.github.wpanas.cassandrashop.domain.repository.UserItemRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;

@Service
public class ItemManagement {
    private final ItemRepository itemRepository;
    private final UserItemRepository userItemRepository;
    private final TagItemRepository tagItemRepository;

    public ItemManagement(ItemRepository itemRepository, UserItemRepository userItemRepository, TagItemRepository tagItemRepository) {
        this.itemRepository = itemRepository;
        this.userItemRepository = userItemRepository;
        this.tagItemRepository = tagItemRepository;
    }

    @Async
    @TransactionalEventListener
    public void handleItemPurchasedEvent(ItemPurchasedEvent event) {
        itemRepository.findById(event.getItemId())
                .subscribe(item -> {
                    updateUserItem(event, item);
                    updateTagItems(event, item);
                });
    }

    private void updateTagItems(ItemPurchasedEvent event, Item item) {
        item.getTags().forEach(tag -> tagItemRepository.findById(new TagItemKey(tag, item.getId()))
                .subscribe(tagItem -> {
                    TagItem updatedTagItem = new TagItem(tag, item.getId(), tagItem.getName(), tagItem.getUnitPrice(), event.getAvailableUnits(), event.getAuctionFinished(), tagItem.getEndDate());
                    tagItemRepository.save(updatedTagItem);
                }));
    }

    private void updateUserItem(ItemPurchasedEvent event, Item item) {
        userItemRepository.findById(new UserItemKey(item.getUserId(), item.getId()))
                .subscribe(userItem -> {
                    UserItem updatedUserItem = new UserItem(item.getUserId(), item.getId(), userItem.getName(), userItem.getUnitPrice(), event.getAvailableUnits(), event.getAuctionFinished(), userItem.getEndDate());
                    userItemRepository.save(updatedUserItem);
                });
    }
}
