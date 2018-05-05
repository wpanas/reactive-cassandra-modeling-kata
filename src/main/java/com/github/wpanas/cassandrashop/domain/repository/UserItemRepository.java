package com.github.wpanas.cassandrashop.domain.repository;

import com.github.wpanas.cassandrashop.domain.model.UserItem;
import com.github.wpanas.cassandrashop.domain.model.UserItemKey;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;

public interface UserItemRepository extends ReactiveCassandraRepository<UserItem, UserItemKey> {
}
