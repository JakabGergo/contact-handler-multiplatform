package edu.bbte.idde.jgim2241.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyProvider {
    private static final String DEV_CONFIG_FILE = "/application-dev.properties";
    private static final String PROD_CONFIG_FILE = "/application-prod.properties";
    private static final String DAO_FACTORY_ENV_VAR = "DAO_FACTORY_IMPL";
    private static final Logger LOGGER = LoggerFactory.getLogger(PropertyProvider.class);
    private static final Properties properties;

    static {
        properties = new Properties();

        // the default config is prod (jdbc)
        String configFile = PROD_CONFIG_FILE;

        // update config if dev (mem) is active
        String activeProfile = System.getenv(DAO_FACTORY_ENV_VAR);
        if ("dev".equals(activeProfile)) {
            configFile = DEV_CONFIG_FILE;
        }

        try (InputStream inputStream = PropertyProvider.class.getResourceAsStream(configFile)) {
            properties.load(inputStream);
        } catch (IOException e) {
            LOGGER.error("Configuration error", e);
        }
    }

    public static String getProperty(final String key) {
        return properties.getProperty(key);
    }
}
