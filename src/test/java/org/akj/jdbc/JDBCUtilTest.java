package org.akj.jdbc;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

class JDBCUtilTest {
    private Connection connection;

    @BeforeEach
    void setUp() throws SQLException {
        String file = JDBCUtil.class.getClassLoader().getSystemResource("jdbc.properties").getFile();
        System.out.println(file);
        connection = JDBCUtil.getConnection();
    }

    @AfterEach
    void tearDown() throws SQLException {
        JDBCUtil.close(connection);
    }

    @Test
    void getConnection() {
        Assertions.assertNotNull(connection);
    }
}