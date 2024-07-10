package sber.spring.RestAlone.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import sber.spring.RestAlone.entities.Bin;
import sber.spring.RestAlone.entities.Client;
import sber.spring.RestAlone.entities.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ClientRepository {
    private List<Client> clientList = new ArrayList<Client>();
    private int ids = 0;

    public long signClient(Client client, Bin bin) {
        client.setId(generateId());
        client.setBin(bin);
        client.getBin().setId(client.getId());
        clientList.add(client);
        return client.getId();
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
