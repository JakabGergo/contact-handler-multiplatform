package edu.bbte.idde.jgim2241.repository.jdbc;

import com.zaxxer.hikari.HikariDataSource;
import edu.bbte.idde.jgim2241.util.PropertyProvider;
import edu.bbte.idde.jgim2241.repository.RepositoryException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;

public final class ConnectionProvider {
    private static ConnectionProvider instance;
    private final HikariDataSource dataSource = new HikariDataSource();
    private static final Logger LOGGER = LoggerFactory.getLogger(ConnectionProvider.class);

    private ConnectionProvider() {
        dataSource.setDriverClassName(PropertyProvider.getProperty("jdbc.driverClass"));
        dataSource.setJdbcUrl(PropertyProvider.getProperty("jdbc.url"));
        dataSource.setUsername(PropertyProvider.getProperty("jdbc.username"));
        dataSource.setPassword(PropertyProvider.getProperty("jdbc.password"));
        dataSource.setMaximumPoolSize(Integer.parseInt(PropertyProvider.getProperty("connectionPool.poolSize")));
        LOGGER.info("Database connection created");
    }

    public Connection getConnection() throws RepositoryException {
        try {
            LOGGER.info("Get connection from connection provider");
            return dataSource.getConnection();
        } catch (SQLException e) {
            LOGGER.error("Connection getConnection error", e);
            throw new RepositoryException("Get connection from connection provider", e);
        }
    }

    public static synchronized ConnectionProvider getInstance() {
        if (instance == null) {
            instance = new ConnectionProvider();
        }
        return instance;
    }
}
