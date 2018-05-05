package com.github.wpanas.cassandrashop.domain.repository;

import com.github.wpanas.cassandrashop.domain.model.Item;
import com.github.wpanas.cassandrashop.domain.model.UserItem;
import org.springframework.data.cassandra.core.CassandraOperations;
import reactor.core.publisher.Mono;

public class CustomizedItemRepositoryImpl implements CustomizedItemRepository {
    private final CassandraOperations cassandraOperations;

    public CustomizedItemRepositoryImpl(CassandraOperations cassandraOperations) {
        this.cassandraOperations = cassandraOperations;
    }

    @Override
    public Mono<Item> saveItem(Item item) {
        return Mono.fromCallable(() -> {
            final UserItem userItem = new UserItem(item.getUserId(), item.getId(), item.getName(), item.getUnitPrice(), item.getAvailableUnits(), item.getAuctionFinished(), item.getEndDate());
            cassandraOperations
                    .batchOps()
                    .insert(item)
                    .insert(userItem)
                    .execute();

            return item;
        });
    }
}
