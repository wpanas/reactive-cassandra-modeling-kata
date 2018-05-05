package com.github.wpanas.cassandrashop.domain.repository;

import com.datastax.driver.core.RegularStatement;
import com.datastax.driver.core.querybuilder.Batch;
import com.github.wpanas.cassandrashop.domain.model.Item;
import com.github.wpanas.cassandrashop.domain.model.Purchase;
import org.springframework.data.cassandra.core.InsertOptions;
import org.springframework.data.cassandra.core.ReactiveCassandraOperations;
import org.springframework.data.cassandra.core.WriteResult;
import reactor.core.publisher.Mono;

import static com.datastax.driver.core.querybuilder.QueryBuilder.*;

public class CustomizedPurchaseRepositoryImpl implements CustomizedPurchaseRepository {
    private static final String MASTER_ITEM_TABLE = "master_item";
    private static final String PURCHASES = "purchases";
    private static final String AVAILABLE_UNITS = "availableUnits";
    private static final String AUCTION_FINISHED = "auctionFinished";
    private static final String ID = "id";
    private static final String MASTER_PURCHASE_TABLE = "master_purchase";
    private static final String STATUS = "status";
    private final ReactiveCassandraOperations reactiveCassandraOperations;

    public CustomizedPurchaseRepositoryImpl(ReactiveCassandraOperations reactiveCassandraOperations) {
        this.reactiveCassandraOperations = reactiveCassandraOperations;
    }

    @Override
    public Mono<Boolean> savePurchase(Purchase purchase, int ttl) {
        return reactiveCassandraOperations.insert(purchase, InsertOptions.builder().ttl(ttl).build())
                .map(WriteResult::wasApplied);
    }

    @Override
    public Mono<Boolean> updatePurchase(Purchase purchase) {
        Batch batchStatement = batch();

        batchStatement.add(purchaseStatusUpdate());

        return reactiveCassandraOperations.getReactiveCqlOperations()
                .execute(batchStatement.toString(), preparedStatement -> {
                    return preparedStatement.bind(purchase.getStatus().name(), purchase.getId());
                });
    }

    private RegularStatement purchaseStatusUpdate() {
        return update(MASTER_PURCHASE_TABLE)
                .with(set(STATUS, bindMarker()))
                .where(eq(ID, bindMarker()));
    }

    @Override
    public Mono<Boolean> bindPurchaseToItem(Purchase purchase, Item item) {
        int availableUnitsBefore = item.getAvailableUnits();
        int availableUnitsAfter = availableUnitsBefore - purchase.getQuantity();
        boolean auctionFinished = availableUnitsAfter == 0;

        final RegularStatement updateItem = update(MASTER_ITEM_TABLE)
                .with(put(PURCHASES, bindMarker(), bindMarker()))
                .and(set(AVAILABLE_UNITS, bindMarker()))
                .and(set(AUCTION_FINISHED, bindMarker()))
                .where(eq(ID, bindMarker()))
                .onlyIf(eq(AVAILABLE_UNITS, bindMarker()));

        return reactiveCassandraOperations.getReactiveCqlOperations()
                .execute(updateItem.toString(), preparedStatement -> {
                    return preparedStatement.bind(purchase.getId(), purchase.getQuantity(), availableUnitsAfter, auctionFinished, item.getId(), availableUnitsBefore);
                });
    }
}
