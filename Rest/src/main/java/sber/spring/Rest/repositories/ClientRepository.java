package sber.spring.Rest.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import sber.spring.Rest.entities.Client;
import sber.spring.Rest.entities.Product;

import java.sql.*;
import java.util.List;
import java.util.Optional;

@Repository
public class ClientRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    ClientRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public int signClient(Client client, int binId) {
        String insertSql = "INSERT INTO my_sch.clients (name_client, login_client, password_client, email, id_bin) VALUES(?, ?, ?, ?, ?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        PreparedStatementCreator preparedStatementCreator = con -> {
            PreparedStatement preparedStatement = con.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, client.getName());
            preparedStatement.setString(2, client.getLogin());
            preparedStatement.setString(3, client.getPassword());
            preparedStatement.setString(4, client.getEmail());
            preparedStatement.setInt(5, binId);
            return  preparedStatement;
        };

        jdbcTemplate.update(preparedStatementCreator, keyHolder);
        return (int) keyHolder.getKeys().get("id_client");


        /*
        try(var connection = DriverManager.getConnection(JDBC);
            var prepareStatement = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)){
            prepareStatement.setString(1, client.getName());
            prepareStatement.setString(2, client.getLogin());
            prepareStatement.setString(3, client.getPassword());
            prepareStatement.setString(4, client.getEmail());
            prepareStatement.setInt(5, binId);

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

        client.setId(generateId());
        client.setBin(bin);
        client.getBin().setId(client.getId());
        clientList.add(client);
        return client.getId();*/
    }

/*    public void updateBin(long clientId, int binId){
        clientList.stream().filter(x -> x.getId() == clientId).findAny().get().setBinId(binId);
    }*/

    public Optional<Client> searchClient(int id) {
        String selectSql = "SELECT * FROM my_sch.clients where id_client = ?";

        PreparedStatementCreator preparedStatementCreator = con -> {
            PreparedStatement preparedStatement = con.prepareStatement(selectSql);
            preparedStatement.setInt(1, id);
            return preparedStatement;
        };

        List<Client> clients = jdbcTemplate.query(preparedStatementCreator, getClientRowMapper());
        return clients.stream().findFirst();

        /*
        try (var connection = DriverManager.getConnection(JDBC);
             var prepareStatement = connection.prepareStatement(selectSql)) {
            prepareStatement.setInt(1, id);

            var resultSet = prepareStatement.executeQuery();

            if (resultSet.next()) {
                int returnId = resultSet.getInt("clientId");
                String name = resultSet.getString("clientName");
                String login = resultSet.getString("clientLogin");
                String password = resultSet.getString("clientPassword");
                String email = resultSet.getString("email");
                int binId = resultSet.getInt("bin_id");
                Client client = new Client(returnId, name, login, password, email, binId);

                return Optional.of(client);
            }

            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


/*        return clientList.stream()
                .filter(x -> x.getId() == id)
                .findAny();*/
    }

    private static RowMapper<Client> getClientRowMapper() {
        return (resultSet, rowNum) -> {
            int id = resultSet.getInt("id_client");
            String name = resultSet.getString("name_client");
            String login = resultSet.getString("login_client");
            String password = resultSet.getString("password_client");
            String email = resultSet.getString("email");
            int binId = resultSet.getInt("id_bin");
            return new Client(id, name, login, password, email, binId);
        };
    }

    public boolean deleteClient(int id) {
        String deleteSql = "DELETE FROM my_sch.clients where id_client = ?";

        PreparedStatementCreator preparedStatementCreator = con -> {
            PreparedStatement preparedStatement = con.prepareStatement(deleteSql);
            preparedStatement.setInt(1, id);

            return preparedStatement;
        };

        int rows = jdbcTemplate.update(preparedStatementCreator);
        return rows > 0;

        /*
        try (var connection = DriverManager.getConnection(JDBC);
             var prepareStatement = connection.prepareStatement(selectSql)) {
            prepareStatement.setInt(1, id);

            var rows = prepareStatement.executeUpdate();

            return rows > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }*/
    }

    public boolean isClient(Client client) {
        String selectSql = "SELECT * FROM my_sch.clients where login_client = ?";

        PreparedStatementCreator preparedStatementCreator = con -> {
            PreparedStatement preparedStatement = con.prepareStatement(selectSql);
            preparedStatement.setString(1, client.getLogin());

            return preparedStatement;
        };

        return jdbcTemplate.query(preparedStatementCreator, getClientRowMapper()).stream().findFirst().isPresent();
    }

        /*
        try (var connection = DriverManager.getConnection(JDBC);
             var prepareStatement = connection.prepareStatement(selectSql)) {
            prepareStatement.setString(1, client.getLogin());

            var resultSet = prepareStatement.executeQuery();

            return resultSet.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }*/
}
