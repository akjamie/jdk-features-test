package org.akj.jdbc.repository;

import org.akj.feign.service.model.Amount;
import org.akj.feign.service.model.Product;
import org.akj.jdbc.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductRepository {
    private static final String PRODUCT_FIND_ALL_SQL = "SELECT * FROM products";
    private static final String PRODUCT_FIND_BY_ID_SQL = "SELECT * FROM products where id=?";

    public List<Product> findAll() throws SQLException {
        List<Product> results = new ArrayList<>();

        ResultSet resultSet;
        try (Connection connection = JDBCUtil.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(PRODUCT_FIND_ALL_SQL)) {
            resultSet = preparedStatement.executeQuery();
            // parse query response to object
            doObjectMapping(results, resultSet);
        }

        return results;
    }

    private void doObjectMapping(List<Product> results, ResultSet resultSet) throws SQLException {
        while (null != resultSet && resultSet.next()) {
            Product product = new Product();
            product.setCode(resultSet.getString("code"));
            product.setDescription(resultSet.getString("description"));
            product.setId(resultSet.getString("id"));
            product.setName(resultSet.getString("name"));

            Amount amount = new Amount();
            amount.setAmount(resultSet.getBigDecimal("amount"));
            amount.setCurrency(resultSet.getString("currency"));
            product.setPrice(amount);
            results.add(product);
        }
    }

    public Optional<Product> findById(String id) throws SQLException {
        List<Product> results = new ArrayList<>();
        try (Connection connection = JDBCUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(PRODUCT_FIND_BY_ID_SQL)) {
            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            // parse query response to object
            doObjectMapping(results, resultSet);
        }

        return Optional.ofNullable(results.size() > 0 ? results.get(0) : null);
    }
}
