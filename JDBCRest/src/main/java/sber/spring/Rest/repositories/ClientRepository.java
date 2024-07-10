package sber.spring.Rest.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import sber.spring.Rest.entities.Bin;
import sber.spring.Rest.entities.Client;
import sber.spring.Rest.entities.Product;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ClientRepository {
    private List<Client> clientList = new ArrayList<Client>();
    private int ids = 0;

    public static final String JDBC = "jdbc:postgresql://localhost:8079/postgres?currentSchema=my_sch&user=postgres&password=Rattlehead85";


    public int signClient(Client client, int binId) {
        var insertSql = "INSERT INTO clients (clientName, clientLogin, clientPassword, email, bin_id) VALUES(?, ?, ?, ?, ?);";

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

        /*        client.setId(generateId());
        client.setBin(bin);
        client.getBin().setId(client.getId());
        clientList.add(client);
        return client.getId();*/
    }

/*    public void updateBin(long clientId, int binId){
        clientList.stream().filter(x -> x.getId() == clientId).findAny().get().setBinId(binId);
    }*/

    public Optional<Client> searchClient(int id) {
        var selectSql = "SELECT * FROM clients where clientId = ?";

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

    public boolean deleteClient(int id) {
        var selectSql = "DELETE FROM clients where clientId = ?";

        try (var connection = DriverManager.getConnection(JDBC);
             var prepareStatement = connection.prepareStatement(selectSql)) {
            prepareStatement.setInt(1, id);

            var rows = prepareStatement.executeUpdate();

            return rows > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private long generateId() {
        ids += 1;
        return ids;
    }

    public boolean isClient(Client client) {
        var selectSql = "SELECT * FROM clients where clientLogin = ?";

        try (var connection = DriverManager.getConnection(JDBC);
             var prepareStatement = connection.prepareStatement(selectSql)) {
            prepareStatement.setString(1, client.getLogin());

            var resultSet = prepareStatement.executeQuery();

            return resultSet.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
