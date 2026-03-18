package edu.bbte.idde.jgim2241.repository.jdbc;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Getter
@Configuration
@Profile("prod")
public class Config {
    @Value("${driverClassName}")
    private String driverClassName;

    @Value("${jdbcUrl}")
    private String jdbcUrl;

    @Value("${dbUsername}")
    private String username;

    @Value("${dbPassword}")
    private String password;

    @Value("${maximumPoolSize}")
    private Integer maximumPoolSize;
}
