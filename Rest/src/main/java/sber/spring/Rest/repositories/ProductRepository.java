package sber.spring.Rest.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import sber.spring.Rest.entities.Product;

import java.sql.*;
import java.util.List;
import java.util.Optional;

@Repository
public class ProductRepository {

//    public static final String JDBC = "jdbc:postgresql://localhost:8079/postgres?currentSchema=my_sch&user=postgres&password=Rattlehead85";
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    ProductRepository(JdbcTemplate jdbcTemplate){
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

/*
        try(var connection = DriverManager.getConnection(JDBC);
            var prepareStatement = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)){
            prepareStatement.setString(1, product.getName());
            prepareStatement.setInt(2, product.getValue());
            prepareStatement.setInt(3, product.getQuantity());

            prepareStatement.executeUpdate();
            ResultSet rs = prepareStatement.getGeneratedKeys();
            if(rs.next()) {
                return rs.getInt(1);
            } else {
                throw new RuntimeException("Ошибка при получении идентификатора");
            }
        } catch (SQLException e){
            throw new RuntimeException(e);
        }*/
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

//        var insertSql = "INSERT INTO products (productName, productValue, quantity) VALUES(?, ?, ?);";

        PreparedStatementCreator preparedStatementCreator = con -> {
            PreparedStatement preparedStatement = con.prepareStatement(updateSql);
            preparedStatement.setString(1, product.getName());
            preparedStatement.setInt(2, product.getValue());
            preparedStatement.setInt(3, product.getQuantity());
            preparedStatement.setInt(4, product.getId());
            return preparedStatement;
        };

        int rows = jdbcTemplate.update(preparedStatementCreator);

//        return rows > 0;

/*        try (var connection = DriverManager.getConnection(JDBC);
             var prepareStatement = connection.prepareStatement(selectSql)) {
            prepareStatement.setString(1, product.getName());
            prepareStatement.setInt(2, product.getValue());
            prepareStatement.setInt(3, product.getQuantity());
            prepareStatement.setInt(4, product.getId());

            prepareStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }*/
    }

    public Optional<Product> search(int id) {
        var selectSql = "SELECT * FROM my_sch.products where id_product = ?";

        PreparedStatementCreator preparedStatementCreator = con -> {
            PreparedStatement preparedStatement = con.prepareStatement(selectSql);
            preparedStatement.setInt(1, id);

            return preparedStatement;
        };

        RowMapper<Product> rowMapper = getProductRowMapper();

        List<Product> listProduct = jdbcTemplate.query(preparedStatementCreator, rowMapper);
        return listProduct.stream().findFirst();

/*        try (var connection = DriverManager.getConnection(JDBC);
             var prepareStatement = connection.prepareStatement(selectSql)) {
            prepareStatement.setInt(1, id);

            var resultSet = prepareStatement.executeQuery();

            if (resultSet.next()) {
                int returnId = resultSet.getInt("productId");
                String name = resultSet.getString("productName");
                int value = resultSet.getInt("productValue");
                int quantity = resultSet.getInt("quantity");
                Product product = new Product(returnId, name, value, quantity);

                return Optional.of(product);
            }

            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }*/
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

        PreparedStatementCreator preparedStatementCreator = con -> {
            PreparedStatement preparedStatement = con.prepareStatement(deleteSql);
            preparedStatement.setInt(1, id);

            return preparedStatement;
        };

        int rows = jdbcTemplate.update(preparedStatementCreator);
        return rows > 0;

/*
        try (var connection = DriverManager.getConnection(JDBC);
             var prepareStatement = connection.prepareStatement(deleteSql)) {
            prepareStatement.setInt(1, id);

            var rows = prepareStatement.executeUpdate();

            return rows > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }*/
    }

    public List<Product> searchByName(String name) {
        var selectSql = "SELECT * FROM my_sch.products where name_product = ?";

        PreparedStatementCreator preparedStatementCreator = con -> {
            PreparedStatement preparedStatement = con.prepareStatement(selectSql);
            preparedStatement.setString(1, name);

            return preparedStatement;
        };

        RowMapper<Product> rowMapper = getProductRowMapper();

        return jdbcTemplate.query(preparedStatementCreator, rowMapper);


        /*try (var connection = DriverManager.getConnection(JDBC);
             var prepareStatement = connection.prepareStatement(selectSql)) {
            prepareStatement.setString(1, name);

            var resultSet = prepareStatement.executeQuery();

            List <Product> products = new ArrayList<Product>();
            while (resultSet.next()) {
                int id = resultSet.getInt("productId");
                String productName = resultSet.getString("productName");
                int value = resultSet.getInt("productValue");
                int quantity = resultSet.getInt("quantity");
                Product product = new Product(id, productName, value, quantity);
                products.add(product);
            }

            return products;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }*/
    }


    public void sell(int id, int quantity) {
        Product product = search(id).get();
        product.setQuantity(product.getQuantity() - quantity);
        update(product);
    }

}
