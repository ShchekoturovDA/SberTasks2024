package sber.spring.JDBCRest.repositories;

import org.springframework.stereotype.Repository;
import sber.spring.JDBCRest.entities.Client;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.Optional;

@Repository
public class ClientRepository {
    public static final String JDBC = "jdbc:postgresql://localhost:8079/postgres?currentSchema=my_sch&user=postgres&password=Rattlehead85";


    public int signClient(Client client, int binId) {
        String insertSql = "INSERT INTO clients (name_client, login_client, password_client, email, id_bin) VALUES(?, ?, ?, ?, ?);";

        try (Connection connection = DriverManager.getConnection(JDBC);
             PreparedStatement prepareStatement = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {
            prepareStatement.setString(1, client.getName());
            prepareStatement.setString(2, client.getLogin());
            prepareStatement.setString(3, client.getPassword());
            prepareStatement.setString(4, client.getEmail());
            prepareStatement.setInt(5, binId);

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

    public Optional<Client> searchClient(int id) {
        String selectSql = "SELECT * FROM clients where id_client = ?";

        try (Connection connection = DriverManager.getConnection(JDBC);
             PreparedStatement prepareStatement = connection.prepareStatement(selectSql)) {
            prepareStatement.setInt(1, id);

            ResultSet resultSet = prepareStatement.executeQuery();

            if (resultSet.next()) {
                int returnId = resultSet.getInt("id_client");
                String name = resultSet.getString("name_client");
                String login = resultSet.getString("login_client");
                String password = resultSet.getString("password_client");
                String email = resultSet.getString("email");
                int binId = resultSet.getInt("id_bin");
                Client client = new Client(returnId, name, login, password, email, binId);

                return Optional.of(client);
            }

            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean deleteClient(int id) {
        String selectSql = "DELETE FROM clients where id_client = ?";

        try (Connection connection = DriverManager.getConnection(JDBC);
             PreparedStatement prepareStatement = connection.prepareStatement(selectSql)) {
            prepareStatement.setInt(1, id);

            int rows = prepareStatement.executeUpdate();

            return rows > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isClient(Client client) {
        String selectSql = "SELECT * FROM clients where login_client = ?";

        try (Connection connection = DriverManager.getConnection(JDBC);
             PreparedStatement prepareStatement = connection.prepareStatement(selectSql)) {
            prepareStatement.setString(1, client.getLogin());

            ResultSet resultSet = prepareStatement.executeQuery();

            return resultSet.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
