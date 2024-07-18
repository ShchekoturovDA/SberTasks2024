package sber.spring.RestAlone.repositories;

import org.springframework.stereotype.Repository;
import sber.spring.RestAlone.entities.Bin;
import sber.spring.RestAlone.entities.Client;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ClientRepository {
    private List<Client> clientList = new ArrayList<Client>();
    private int ids = 0;

    public long signClient(Client client, Bin bin) {
        if (!clientList.stream()
                .filter(x -> x.getLogin() != client.getLogin())
                .findAny()
                .isPresent()) {
            client.setId(generateId());
            client.setBin(bin);
            client.getBin().setId(client.getId());
            clientList.add(client);
            return client.getId();
        } else {
            return 0;
        }
    }

    public void updateBin(long clientId, Bin bin) {
        clientList.stream().filter(x -> x.getId() == clientId).findAny().get().setBin(bin);
    }

    public Optional<Client> searchClient(long id) {
        return clientList.stream()
                .filter(x -> x.getId() == id)
                .findAny();
    }

    public boolean deleteClient(long id) {
        Optional<Client> delete = clientList.stream()
                .filter(x -> x.getId() == id)
                .findAny();
        if (delete.isPresent()) {
            clientList.remove(delete.get());
        }
        return delete.isPresent();
    }

    private long generateId() {
        ids += 1;
        return ids;
    }

}
