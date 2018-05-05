package com.github.wpanas.cassandrashop.domain.repository;

import com.github.wpanas.cassandrashop.domain.model.Item;
import com.github.wpanas.cassandrashop.domain.model.Purchase;
import reactor.core.publisher.Mono;

public interface CustomizedPurchaseRepository {
        Mono<Boolean> savePurchase(Purchase purchase, int ttl);
        Mono<Boolean> updatePurchase(Purchase purchase);
        Mono<Boolean> bindPurchaseToItem(Purchase purchase, Item item);
}
