package edu.bbte.idde.jgim2241.repository.jdbc;

import com.zaxxer.hikari.HikariDataSource;
import edu.bbte.idde.jgim2241.repository.RepositoryException;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.SQLException;

@Component
@Slf4j
@Profile("prod")
public final class ConnectionProvider {
    private final HikariDataSource dataSource = new HikariDataSource();

    @Autowired
    private Config config;

    @PostConstruct
    public void postConstruct() {
        dataSource.setDriverClassName(config.getDriverClassName());
        dataSource.setJdbcUrl(config.getJdbcUrl());
        dataSource.setUsername(config.getUsername());
        dataSource.setPassword(config.getPassword());
        dataSource.setMaximumPoolSize(config.getMaximumPoolSize());
        log.info("Database connection created");
    }

    public Connection getConnection() throws RepositoryException {
        try {
            log.info("Get connection from connection provider");
            return dataSource.getConnection();
        } catch (SQLException e) {
            log.error("Connection getConnection error", e);
            throw new RepositoryException("Get connection from connection provider", e);
        }
    }
}
