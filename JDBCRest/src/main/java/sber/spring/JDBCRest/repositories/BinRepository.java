package sber.spring.JDBCRest.repositories;

import org.springframework.stereotype.Repository;
import sber.spring.JDBCRest.entities.Bin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.Optional;

@Repository
public class BinRepository {
    public static final String JDBC = "jdbc:postgresql://localhost:8079/postgres?currentSchema=my_sch&user=postgres&password=Rattlehead85";

    public int createBin() {
        String insertSql = "INSERT INTO bins (promocode) VALUES(?);";

        try (Connection connection = DriverManager.getConnection(JDBC);
             PreparedStatement prepareStatement = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {
            prepareStatement.setString(1, "");

            prepareStatement.executeUpdate();
            ResultSet rs = prepareStatement.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            } else {
                throw new RuntimeException("Ошибка при получении идентификатора");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<Bin> search(int binId) {
        String selectSql = "SELECT * FROM bins where id_bin = ?";

        try (Connection connection = DriverManager.getConnection(JDBC);
             PreparedStatement prepareStatement = connection.prepareStatement(selectSql)) {
            prepareStatement.setInt(1, binId);

            ResultSet resultSet = prepareStatement.executeQuery();

            if (resultSet.next()) {
                int returnId = resultSet.getInt("id_bin");
                String promocode = resultSet.getString("promocode");
                Bin bin = new Bin(returnId, promocode);

                return Optional.of(bin);
            }

            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isInBin(int binId, int productId) {
        String selectSql = "SELECT * FROM products_bins where (id_bin = ? AND id_product = ?)";

        try (Connection connection = DriverManager.getConnection(JDBC);
             PreparedStatement prepareStatement = connection.prepareStatement(selectSql)) {
            prepareStatement.setInt(1, binId);
            prepareStatement.setInt(2, productId);

            ResultSet resultSet = prepareStatement.executeQuery();

            return resultSet.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int add(int binId, int productId) {

        String insertSql = "INSERT INTO products_bins (id_product, id_bin, quantity) VALUES(?, ?, ?);";

        try (Connection connection = DriverManager.getConnection(JDBC);
             PreparedStatement prepareStatement = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {
            prepareStatement.setInt(1, productId);
            prepareStatement.setInt(2, binId);
            prepareStatement.setInt(3, 1);

            prepareStatement.executeUpdate();
            ResultSet rs = prepareStatement.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            } else {
                throw new RuntimeException("Ошибка при получении идентификатора");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void changeQuantity(int binId, int productId, int quantity) {
        String selectSql = """
                        UPDATE products_bins
                        SET
                        quantity = ?
                        where (id_product = ? AND id_bin = ?);
                """;
        try (Connection connection = DriverManager.getConnection(JDBC);
             PreparedStatement prepareStatement = connection.prepareStatement(selectSql)) {
            prepareStatement.setInt(1, quantity);
            prepareStatement.setInt(2, productId);
            prepareStatement.setInt(3, binId);

            prepareStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean deleteFromBin(int binId, int productId) {
        String selectSql = "DELETE FROM products_bins where (id_product = ? AND id_bin = ?)";

        try (Connection connection = DriverManager.getConnection(JDBC);
             PreparedStatement prepareStatement = connection.prepareStatement(selectSql)) {
            prepareStatement.setInt(1, productId);
            prepareStatement.setInt(2, binId);

            int rows = prepareStatement.executeUpdate();

            return rows > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean pay(int binId) {
        String selectSql = "DELETE FROM products_bins where id_bin = ?";

        try (Connection connection = DriverManager.getConnection(JDBC);
             PreparedStatement prepareStatement = connection.prepareStatement(selectSql)) {
            prepareStatement.setInt(1, binId);

            int rows = prepareStatement.executeUpdate();

            return rows > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
