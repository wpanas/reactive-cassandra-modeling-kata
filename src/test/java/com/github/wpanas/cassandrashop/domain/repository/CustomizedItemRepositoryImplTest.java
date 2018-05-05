package com.github.wpanas.cassandrashop.domain.repository;

import com.github.wpanas.cassandrashop.CassandraIntegrationTest;
import com.github.wpanas.cassandrashop.domain.model.Item;
import com.github.wpanas.cassandrashop.domain.model.TagItemKey;
import com.github.wpanas.cassandrashop.domain.model.UserItemKey;
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
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@CassandraIntegrationTest
public class CustomizedItemRepositoryImplTest {
    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private UserItemRepository userItemRepository;

    @Autowired
    private TagItemRepository tagItemRepository;

    @Test
    public void saveItem() {
        final UUID itemId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        String name = "Item name";
        String description = "Item description";
        BigDecimal unitPrice = BigDecimal.ONE;
        Integer offeredUnits = 1;
        Integer availableUnits = 1;
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now();
        Set<String> tags = new TreeSet<>();
        final String tag = "wooden";
        tags.add(tag);
        tags.add("crisp");
        tags.add("rocky");
        final Item item = new Item(itemId, userId, name, description, unitPrice, offeredUnits, availableUnits, startDate, endDate, true, tags);

        StepVerifier.create(itemRepository.saveItem(item))
                .expectSubscription()
                .consumeNextWith(savedItem -> assertThat(savedItem.getId(), is(item.getId())))
                .verifyComplete();

        final UserItemKey userItemKey = new UserItemKey(item.getUserId(), item.getId());
        StepVerifier.create(userItemRepository.findById(userItemKey))
                .expectSubscription()
                .consumeNextWith(userItem -> {
                    assertThat(userItem.getId(), is(userItemKey));
                    assertThat(userItem.getItemId(), is(item.getId()));
                    assertThat(userItem.getId().getUserId(), is(item.getUserId()));
                })
                .verifyComplete();

        final TagItemKey tagItemKey = new TagItemKey(tag, item.getId());
        StepVerifier.create(tagItemRepository.findById(tagItemKey))
                .expectSubscription()
                .consumeNextWith(tagItem -> {
                    assertThat(tagItem.getId(), is(tagItemKey));
                    assertThat(tagItem.getItemId(), is(item.getId()));
                    assertThat(tagItem.getName(), is(item.getName()));
                })
                .verifyComplete();
    }
}