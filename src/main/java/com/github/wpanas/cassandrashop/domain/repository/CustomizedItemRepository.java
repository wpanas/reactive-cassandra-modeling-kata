package com.github.wpanas.cassandrashop.domain.repository;

import com.github.wpanas.cassandrashop.domain.model.Item;
import reactor.core.publisher.Mono;

public interface CustomizedItemRepository {
    Mono<Item> saveItem(Item item);
}
