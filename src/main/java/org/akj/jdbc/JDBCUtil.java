package org.akj.jdbc;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

@Slf4j
public class JDBCUtil {
    private static HikariDataSource hikariDataSource;

    static {
        Properties properties = new Properties();
        String file = JDBCUtil.class.getClassLoader().getSystemResource("jdbc.properties").getFile();

        HikariConfig config = new HikariConfig(file);
        hikariDataSource = new HikariDataSource(config);
    }

    public static Connection getConnection() throws SQLException {
        return hikariDataSource.getConnection();
    }

    public static void close(Connection connection) throws SQLException {
        if (null != connection) {
            connection.close();
        }
    }

}


