package com.github.wpanas.cassandrashop.domain.repository;

import com.github.wpanas.cassandrashop.domain.model.User;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;

import java.util.UUID;

public interface UserRepository extends ReactiveCassandraRepository<User, UUID> {
}
