package com.github.wpanas.cassandrashop.domain.repository;

import com.github.wpanas.cassandrashop.CassandraIntegrationTest;
import com.github.wpanas.cassandrashop.domain.model.Item;
import com.github.wpanas.cassandrashop.domain.model.Purchase;
import com.github.wpanas.cassandrashop.domain.model.PurchaseStatus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.collection.IsMapContaining.hasEntry;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@CassandraIntegrationTest
public class CustomizedPurchaseRepositoryImplTest {
    @Autowired
    private PurchaseRepository purchaseRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Test
    public void savePurchase() {
        int ttl = 24;
        UUID id = UUID.randomUUID();
        UUID itemId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        BigDecimal unitPrice = BigDecimal.ONE;
        Integer quantity = 1;
        Purchase purchase = new Purchase(id, itemId, userId, unitPrice, quantity);

        StepVerifier.create(purchaseRepository.savePurchase(purchase, ttl))
                .expectSubscription()
                .assertNext(saved -> assertThat(saved, is(true)))
                .verifyComplete();

        StepVerifier.create(purchaseRepository.findById(id))
                .expectSubscription()
                .consumeNextWith(purchase1 -> {
                    assertThat(purchase.getId(), is(id));
                    assertThat(purchase.getStatus(), is(PurchaseStatus.ENTERED));
                })
                .verifyComplete();
    }

    @Test
    public void updatePurchase() {
        UUID id = UUID.randomUUID();
        UUID itemId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        BigDecimal unitPrice = BigDecimal.ONE;
        Integer quantity = 1;
        Purchase purchase = new Purchase(id, itemId, userId, unitPrice, quantity, PurchaseStatus.COMPLETED);

        StepVerifier.create(purchaseRepository.updatePurchase(purchase))
                .expectSubscription()
                .consumeNextWith(updated -> assertThat(updated, is(true)))
                .verifyComplete();

        StepVerifier.create(purchaseRepository.findById(id))
                .expectSubscription()
                .consumeNextWith(purchase1 -> {
                    assertThat(purchase.getId(), is(id));
                    assertThat(purchase.getStatus(), is(PurchaseStatus.COMPLETED));
                })
                .verifyComplete();
    }

    @Test
    public void bindPurchaseToItem() {
        UUID itemId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        String name = "ring";
        String description = "unique";
        BigDecimal unitPrice = BigDecimal.ONE;
        Integer offeredUnits = 1;
        Integer availableUnits = 1;
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now();
        Set<String> tags = new TreeSet<>();
        Item item = new Item(itemId, userId, name, description, unitPrice, offeredUnits, availableUnits, startDate, endDate, false, tags);

        itemRepository.saveItem(item).subscribe();

        UUID id = UUID.randomUUID();
        Purchase purchase = new Purchase(id, itemId, userId, unitPrice, availableUnits);

        StepVerifier.create(purchaseRepository.bindPurchaseToItem(purchase, item))
                .expectSubscription()
                .consumeNextWith(updated -> assertThat(updated, is(true)))
                .verifyComplete();

        StepVerifier.create(itemRepository.findById(itemId))
                .expectSubscription()
                .consumeNextWith(item1 -> {
                    assertThat(item1.getPurchases(), hasEntry(purchase.getId(), purchase.getQuantity()));
                    assertThat(item1.getAvailableUnits(), is(0));
                    assertThat(item1.getAuctionFinished(), is(true));
                })
                .verifyComplete();
    }
}