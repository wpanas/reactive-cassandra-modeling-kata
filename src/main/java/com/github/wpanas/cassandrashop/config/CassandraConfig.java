package com.github.wpanas.cassandrashop.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.AbstractReactiveCassandraConfiguration;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.repository.config.EnableReactiveCassandraRepositories;

@Configuration
@EnableReactiveCassandraRepositories(basePackages = {"com.github.wpanas.cassandrashop.domain"})
public class CassandraConfig extends AbstractReactiveCassandraConfiguration {

    @Override
    protected String getContactPoints() {
        return "127.0.0.1";
    }

    @Override
    protected int getPort() {
        return 9143;
    }

    @Override
    public SchemaAction getSchemaAction() {
        return SchemaAction.RECREATE;
    }

    @Override
    protected String getKeyspaceName() {
        return "shop";
    }

    @Override
    public String[] getEntityBasePackages() {
        return new String[]{"com.github.wpanas.cassandrashop.domain"};
    }
}
