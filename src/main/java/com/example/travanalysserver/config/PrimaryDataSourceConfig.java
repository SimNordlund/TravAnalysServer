package com.example.travanalysserver.config;

import jakarta.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.*;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Map;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages            = "com.example.travanalysserver.repository",          // your primary repo package
        entityManagerFactoryRef = "primaryEntityManagerFactory",
        transactionManagerRef   = "primaryTransactionManager"
)

public class PrimaryDataSourceConfig {

    @Bean
    @Primary                                                                    //Changed!
    @ConfigurationProperties("spring.datasource")
    public DataSourceProperties primaryDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @Primary                                                                    //Changed!
    public DataSource primaryDataSource() {
        return primaryDataSourceProperties()
                .initializeDataSourceBuilder()
                .build();
    }

    @Bean(name = "primaryEntityManagerFactory")
    @Primary                                                                    //Changed!
    public LocalContainerEntityManagerFactoryBean primaryEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            Environment env) {
        return builder
                .dataSource(primaryDataSource())
                .packages("com.example.travanalysserver.entity")               // your primary entity package
                .persistenceUnit("primary")
                .properties(Map.of(
                        "hibernate.hbm2ddl.auto",       env.getProperty("spring.jpa.hibernate.ddl-auto"),
                        "hibernate.dialect",            env.getProperty("spring.jpa.database-platform")
                ))
                .build();
    }

    @Bean(name = "primaryTransactionManager")
    @Primary                                                                    //Changed!
    public PlatformTransactionManager primaryTransactionManager(
            @Qualifier("primaryEntityManagerFactory") EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }

}
