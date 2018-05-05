package com.github.wpanas.cassandrashop.domain.repository;

import com.github.wpanas.cassandrashop.domain.model.TagItem;
import com.github.wpanas.cassandrashop.domain.model.TagItemKey;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;

public interface TagItemRepository extends ReactiveCassandraRepository<TagItem, TagItemKey> {
}
