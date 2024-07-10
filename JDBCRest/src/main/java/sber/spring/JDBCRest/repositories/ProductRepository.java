package sber.spring.JDBCRest.repositories;

import org.springframework.stereotype.Repository;
import sber.spring.JDBCRest.entities.Product;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ProductRepository {

    public static final String JDBC = "jdbc:postgresql://localhost:8079/postgres?currentSchema=my_sch&user=postgres&password=Rattlehead85";

    public long addProduct(Product product) {
        String insertSql = "INSERT INTO products (name_product, value_product, quantity) VALUES(?, ?, ?);";

        try(Connection connection = DriverManager.getConnection(JDBC);
            PreparedStatement prepareStatement = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)){
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
        }
    }

    public void update(Product product) {
        String selectSql = """
                UPDATE products
                SET
                name_product = ?,
                value_product = ?,
                quantity = ?
                where id_product = ?;
        """;
        try (Connection connection = DriverManager.getConnection(JDBC);
             PreparedStatement prepareStatement = connection.prepareStatement(selectSql)) {
            prepareStatement.setString(1, product.getName());
            prepareStatement.setInt(2, product.getValue());
            prepareStatement.setInt(3, product.getQuantity());
            prepareStatement.setInt(4, product.getId());

            prepareStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<Product> search(int id) {
        String selectSql = "SELECT * FROM products where id_product = ?";

        try (Connection connection = DriverManager.getConnection(JDBC);
             PreparedStatement prepareStatement = connection.prepareStatement(selectSql)) {
            prepareStatement.setInt(1, id);

            ResultSet resultSet = prepareStatement.executeQuery();

            if (resultSet.next()) {
                int returnId = resultSet.getInt("id_product");
                String name = resultSet.getString("name_product");
                int value = resultSet.getInt("value_product");
                int quantity = resultSet.getInt("quantity");
                Product product = new Product(returnId, name, value, quantity);

                return Optional.of(product);
            }

            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean delete(int id) {
        String selectSql = "DELETE FROM products where id_product = ?";

        try (Connection connection = DriverManager.getConnection(JDBC);
             PreparedStatement prepareStatement = connection.prepareStatement(selectSql)) {
            prepareStatement.setInt(1, id);

            int rows = prepareStatement.executeUpdate();

            return rows > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Product> searchByName(String name) {
        String selectSql = "SELECT * FROM products where name_product = ?";

        try (Connection connection = DriverManager.getConnection(JDBC);
             PreparedStatement prepareStatement = connection.prepareStatement(selectSql)) {
            prepareStatement.setString(1, name);

            ResultSet resultSet = prepareStatement.executeQuery();

            List <Product> products = new ArrayList<Product>();
            while (resultSet.next()) {
                int id = resultSet.getInt("id_product");
                String productName = resultSet.getString("name_product");
                int value = resultSet.getInt("value_product");
                int quantity = resultSet.getInt("quantity");
                Product product = new Product(id, productName, value, quantity);
                products.add(product);
            }

            return products;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
