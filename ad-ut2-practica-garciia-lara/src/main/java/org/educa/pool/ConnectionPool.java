package org.educa.pool;

import org.apache.commons.dbcp.BasicDataSource;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConnectionPool {
    private static BasicDataSource dataSource;

    private ConnectionPool() {
        throw new IllegalStateException();
    }

    public static BasicDataSource getDataSource() {
        if (dataSource == null) {
            Properties properties = new Properties();
            try (InputStream input = ConnectionPool.class.getClassLoader().getResourceAsStream("database.properties")) {
                properties.load(input);

                dataSource = new BasicDataSource();
                dataSource.setUrl(properties.getProperty("db.url"));
                dataSource.setUsername(properties.getProperty("db.username"));
                dataSource.setPassword(properties.getProperty("db.password"));
                dataSource.setMinIdle(Integer.parseInt(properties.getProperty("db.initialSize")));
                dataSource.setMaxIdle(Integer.parseInt(properties.getProperty("db.maxTotal")));

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return dataSource;
    }

}
