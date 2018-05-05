package com.github.wpanas.cassandrashop.domain.repository;

import com.github.wpanas.cassandrashop.domain.model.UserPurchase;
import com.github.wpanas.cassandrashop.domain.model.UserPurchaseKey;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;

public interface UserPurchaseRepository extends ReactiveCassandraRepository<UserPurchase, UserPurchaseKey> {
}
