package org.akj.jdbc.repository;

import org.akj.feign.service.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ProductRepositoryTest {
    private ProductRepository repository;

    @BeforeEach
    void setUp() {
        repository = new ProductRepository();
    }

    @Test
    void findAll() throws SQLException {
        List<Product> all = repository.findAll();

        assertTrue(all.size() > 0);
    }

    @Test
    void findById() throws SQLException {
        Optional<Product> all = repository.findById("test");
        assertTrue(!all.isPresent());
    }
}