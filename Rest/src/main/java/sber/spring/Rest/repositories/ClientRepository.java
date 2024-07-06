package sber.spring.Rest.repositories;

import org.springframework.stereotype.Repository;
import sber.spring.Rest.entities.Client;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ClientRepository {
    private List<Client> clientList;
    private int ids;

    public void ClientRepository() {
        clientList = new ArrayList<Client>();
        ids = 0;
    }

    public long signClient(Client client) {
        if (!clientList.stream()
                .filter(x -> x.getLogin() != client.getLogin())
                .findAny()
                .isPresent()) {
            client.setId(generateId());
            clientList.add(client);
            return client.getId();
        } else {
            return 0;
        }
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
}
