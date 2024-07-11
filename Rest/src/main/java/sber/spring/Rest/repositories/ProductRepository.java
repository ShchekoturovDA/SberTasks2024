package sber.spring.Rest.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import sber.spring.Rest.entities.Product;
import sber.spring.Rest.entities.Sold;

import java.sql.*;
import java.util.List;
import java.util.Optional;

@Repository
public class ProductRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    ProductRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public long addProduct(Product product) {
        var insertSql = "INSERT INTO my_sch.products (name_product, value_product, quantity) VALUES(?, ?, ?);";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        PreparedStatementCreator preparedStatementCreator = con -> {
            PreparedStatement preparedStatement = con.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, product.getName());
            preparedStatement.setInt(2, product.getValue());
            preparedStatement.setInt(3, product.getQuantity());

            return preparedStatement;
        };

        jdbcTemplate.update(preparedStatementCreator, keyHolder);

        return (int) keyHolder.getKeys().get("id_product");
    }

    public void update(Product product) {
        var updateSql = """
                        UPDATE my_sch.products
                        SET
                        name_product = ?,
                        value_product = ?,
                        quantity = ?
                        where id_product = ?;
                """;
        int rows = jdbcTemplate.update(updateSql, product.getName(), product.getValue(), product.getQuantity(), product.getId());
    }

    public Optional<Product> search(int id) {
        var selectSql = "SELECT * FROM my_sch.products where id_product = ?";
        RowMapper<Product> rowMapper = getProductRowMapper();

        List<Product> listProduct = jdbcTemplate.query(selectSql, rowMapper, id);
        return listProduct.stream().findFirst();
    }

    private static RowMapper<Product> getProductRowMapper() {
        return (resultSet, rowNum) -> {
            int id = resultSet.getInt("id_product");
            String name = resultSet.getString("name_product");
            int value = resultSet.getInt("value_product");
            int quantity = resultSet.getInt("quantity");
            return new Product(id, name, value, quantity);
        };
    }

    public boolean delete(int id) {
        var deleteSql = "DELETE FROM my_sch.products where id_product = ?";
        int rows = jdbcTemplate.update(deleteSql, id);
        return rows > 0;
    }

    public List<Product> searchByName(String name) {
        var selectSql = "SELECT * FROM my_sch.products where name_product = ?";
        RowMapper<Product> rowMapper = getProductRowMapper();

        return jdbcTemplate.query(selectSql, rowMapper, name);
    }


    public void sell(Sold sold) {
        var putSql = "UPDATE my_sch.products SET quantity = quantity - ? WHERE id_product = ?";
        jdbcTemplate.update(putSql, sold.getQuantity(), sold.getId());
    }

}
