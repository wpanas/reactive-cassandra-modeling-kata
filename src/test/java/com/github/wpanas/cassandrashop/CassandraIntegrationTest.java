package com.github.wpanas.cassandrashop;

import com.github.wpanas.cassandrashop.config.CassandraConfig;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@ContextConfiguration(classes = {CassandraConfig.class})
@TestExecutionListeners(listeners = {CassandraTestExecutionListener.class, DependencyInjectionTestExecutionListener.class})
public @interface CassandraIntegrationTest {
}
