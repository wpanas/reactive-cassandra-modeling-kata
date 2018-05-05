package com.github.wpanas.cassandrashop.domain.repository;

import com.github.wpanas.cassandrashop.domain.model.Item;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;

import java.util.UUID;

public interface ItemRepository extends ReactiveCassandraRepository<Item, UUID>, CustomizedItemRepository {
}
