package com.github.wpanas.cassandrashop.domain.repository;

import com.github.wpanas.cassandrashop.CassandraIntegrationTest;
import com.github.wpanas.cassandrashop.domain.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@CassandraIntegrationTest
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    public void shouldInsert() {
        final UUID id = UUID.randomUUID();
        final String login = "cassandra";
        final int age = 24;

        final User user = new User(id, login, age);

        final Mono<User> mono = userRepository.save(user);

        StepVerifier.create(mono)
                .expectSubscription()
                .assertNext(user1 -> assertThat(user1.getId(), is(id)))
                .verifyComplete();
    }

    @Test
    public void shouldFetch() {
        final UUID id = UUID.randomUUID();
        final String login = "cassandra_fetch";
        final int age = 24;

        final User user = new User(id, login, age);

        userRepository.save(user).subscribe();

        StepVerifier.create(userRepository.findById(id))
                .expectSubscription()
                .assertNext(user1 -> assertThat(user1.getLogin(), is(login)))
                .verifyComplete();
    }
}