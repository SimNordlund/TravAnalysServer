package com.example.travanalysserver.config;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.*;
import org.springframework.orm.jpa.*;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Map;
import java.util.Objects;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages        = "com.example.travanalysserver.repositorysec",
        entityManagerFactoryRef = "secondaryEntityManagerFactory",
        transactionManagerRef   = "secondaryTransactionManager"
)
public class SecondaryDataSourceConfig {

    private final Environment env;

    public SecondaryDataSourceConfig(Environment env) {
        this.env = env;
    }

    @Bean
    @ConfigurationProperties("secondary.datasource")
    public DataSourceProperties secondaryDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    public DataSource secondaryDataSource() {
        return secondaryDataSourceProperties()
                .initializeDataSourceBuilder()
                .build();
    }

    @Bean(name = "secondaryEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean secondaryEntityManagerFactory(
            EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(secondaryDataSource())
                .packages("com.example.travanalysserver.entitysec")
                .persistenceUnit("secondary")
                .properties(Map.of(
                        "jakarta.persistence.schema-generation.database.action", "update",
                        "hibernate.dialect", env.getProperty("secondary.jpa.database-platform")
                ))
                .build();
    }

    @Bean(name = "secondaryTransactionManager")
    public PlatformTransactionManager secondaryTransactionManager(
            @Qualifier("secondaryEntityManagerFactory") EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }

}