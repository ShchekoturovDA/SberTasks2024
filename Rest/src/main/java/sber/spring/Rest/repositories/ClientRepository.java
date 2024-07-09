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


    public long signClient(Client client, Bin bin) {
        var insertSql = "INSERT INTO clients (clientName, clientLogin, clientPassword, email) VALUES(?, ?, ?);";

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
        }

        /*        client.setId(generateId());
        client.setBin(bin);
        client.getBin().setId(client.getId());
        clientList.add(client);
        return client.getId();*/
    }

    public void updateBin(long clientId, Bin bin){
        clientList.stream().filter(x -> x.getId() == clientId).findAny().get().setBin(bin);
    }

    public Optional<Client> searchClient(long id) {
        return clientList.stream()
                .filter(x -> x.getId() == id)
                .findAny();
    }

    public boolean deleteClient(long id) {
        Client delete = clientList.stream()
                .filter(x -> x.getId() == id)
                .findAny()
                .orElse(null);
        if (delete == null){
            return false;
        } else {
            clientList.remove(delete);
            return true;
        }
    }

    private long generateId() {
        ids += 1;
        return ids;
    }

    public boolean isClient(Client client) {
        return clientList.stream()
                .filter(x -> x.getLogin() != client.getLogin())
                .findAny()
                .isPresent();
    }
}
