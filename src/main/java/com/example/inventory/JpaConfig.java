package com.example.inventory;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;


/**
 * This file may not be needed
 */
@Configuration
@EnableJpaRepositories(
        entityManagerFactoryRef = "inventoryEntityManager",
        transactionManagerRef = "inventorySqlTransactionManager",
        basePackages = {"com.example.inventory"})
@EntityScan(basePackages = {
        "com.example.inventory"})
public class JpaConfig {

    private final JpaProperties jpaProperties;

    @Autowired
    public JpaConfig(JpaProperties jpaProperties) {
        //All the Jpa properties in the currently active properties file
        this.jpaProperties = jpaProperties;
    }

    /**
     * Get the datasource related properties from the currently active properties file
     *
     * @return properties
     */
    @Bean
    @ConfigurationProperties("spring.datasource")
    public DataSourceProperties dataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    public DataSource dataSourceForPostGresql() {
        return dataSourceProperties()
                .initializeDataSourceBuilder()
                .build();
    }

    @Bean(name = "inventoryEntityManager")
    public EntityManagerFactory entityManagerFactory() {
        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSourceForPostGresql());
        entityManagerFactoryBean.setPackagesToScan(
                "com.example.inventory"
        );
        entityManagerFactoryBean.setJpaVendorAdapter(vendorAdapter);
        entityManagerFactoryBean.setJpaPropertyMap(jpaProperties.getProperties());
        entityManagerFactoryBean.setPersistenceUnitName("inventory");
        entityManagerFactoryBean.afterPropertiesSet();

        return entityManagerFactoryBean.getObject();
    }

    @Bean(name = "inventorySqlTransactionManager")
    public JpaTransactionManager transactionManager(EntityManagerFactory inventoryEntityManager) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(inventoryEntityManager);

        return transactionManager;
    }


}

