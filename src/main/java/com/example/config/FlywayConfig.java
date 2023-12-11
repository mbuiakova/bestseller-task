package com.example.config;

import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FlywayConfig {

    @Bean
    public FlywayMigrationStrategy noopFlywayMigrationStrategy() {
        return flyway -> System.out.println("I'm not migrating right now.");
    }
}
