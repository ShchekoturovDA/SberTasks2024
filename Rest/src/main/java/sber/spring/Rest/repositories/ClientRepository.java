package sber.spring.Rest.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import sber.spring.Rest.entities.Client;

import java.sql.*;
import java.util.List;
import java.util.Optional;

@Repository
public class ClientRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    ClientRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int signClient(Client client, int binId) {
        String insertSql = "INSERT INTO clients (name_client, login_client, password_client, email, id_bin) VALUES(?, ?, ?, ?, ?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        PreparedStatementCreator preparedStatementCreator = con -> {
            PreparedStatement preparedStatement = con.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, client.getName());
            preparedStatement.setString(2, client.getLogin());
            preparedStatement.setString(3, client.getPassword());
            preparedStatement.setString(4, client.getEmail());
            preparedStatement.setInt(5, binId);
            return preparedStatement;
        };

        jdbcTemplate.update(preparedStatementCreator, keyHolder);
        return (int) keyHolder.getKeys().get("id_client");
    }

    public Optional<Client> searchClient(int id) {
        String selectSql = "SELECT * FROM clients where id_client = ?";
        List<Client> clients = jdbcTemplate.query(selectSql, getClientRowMapper(), id);
        return clients.stream().findFirst();
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
        String deleteSql = "DELETE FROM clients where id_client = ?";
        int rows = jdbcTemplate.update(deleteSql, id);
        return rows > 0;
    }

    public boolean isClient(Client client) {
        String selectSql = "SELECT * FROM clients where login_client = ?";
        return jdbcTemplate.query(selectSql, getClientRowMapper(), client.getLogin()).stream().findFirst().isPresent();
    }
}
