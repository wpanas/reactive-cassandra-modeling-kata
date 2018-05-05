package com.github.wpanas.cassandrashop.domain.repository;

import com.github.wpanas.cassandrashop.domain.model.Item;
import com.github.wpanas.cassandrashop.domain.model.TagItem;
import com.github.wpanas.cassandrashop.domain.model.UserItem;
import org.springframework.data.cassandra.core.CassandraOperations;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

public class CustomizedItemRepositoryImpl implements CustomizedItemRepository {
    private final CassandraOperations cassandraOperations;

    public CustomizedItemRepositoryImpl(CassandraOperations cassandraOperations) {
        this.cassandraOperations = cassandraOperations;
    }

    @Override
    public Mono<Item> saveItem(Item item) {
        return Mono.fromCallable(() -> {
            final UserItem userItem = new UserItem(item.getUserId(), item.getId(), item.getName(), item.getUnitPrice(), item.getAvailableUnits(), item.getAuctionFinished(), item.getEndDate());
            final List<TagItem> tagItems = item.getTags()
                    .stream()
                    .map(tag -> new TagItem(tag, item.getId(), item.getName(), item.getUnitPrice(), item.getAvailableUnits(), item.getAuctionFinished(), item.getEndDate()))
                    .collect(Collectors.toList());

            cassandraOperations
                    .batchOps()
                    .insert(item)
                    .insert(userItem)
                    .insert(tagItems)
                    .execute();

            return item;
        });
    }
}
