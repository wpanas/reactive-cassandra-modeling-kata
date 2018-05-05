package com.github.wpanas.cassandrashop.domain.repository;

import com.github.wpanas.cassandrashop.domain.model.Purchase;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;

import java.util.UUID;

public interface PurchaseRepository extends ReactiveCassandraRepository<Purchase, UUID>, CustomizedPurchaseRepository {

}
