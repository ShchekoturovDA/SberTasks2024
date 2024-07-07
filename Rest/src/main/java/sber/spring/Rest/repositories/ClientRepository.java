package sber.spring.Rest.repositories;

import org.springframework.stereotype.Repository;
import sber.spring.Rest.entities.Client;
import sber.spring.Rest.entities.Product;

import java.util.ArrayList;
import java.util.LinkedList;
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

    public boolean isInBinInRep(long clientId, long productId) {
        return !clientList.stream()
                .filter(x -> x.getId() == clientId)
                .findAny()
                .get()
                .getBin()
                .getProductList().stream()
                .filter(x -> x.getId() == productId)
                .findAny()
                .isEmpty();

    }


    public void addToBinInRep(long clientId, Product product) {
        for (Client client : clientList){
            if (client.getId() == clientId){
                client.getBin().getProductList().add(product);
            }
        }
    }

    public void changeQuantityInBin(long clientId, long productId, int quantity) {
        for (Client client : clientList){
            if (client.getId() == clientId){
                for (Product product1 : client.getBin().getProductList()){
                    if (product1.getId() == productId){
                        product1.setQuantity(product1.getQuantity() + quantity);
                    }
                }
            }
        }
    }

    public void deleteFromBin(long clientId, long productId) {
        for (Client client : clientList){
            if (client.getId() == clientId){
                client.getBin().getProductList().remove(client.getBin().getProductList().stream()
                        .filter(x -> x.getId() == productId)
                        .findAny()
                        .get());
            }
        }
    }

    public void payForBin(long clientId) {
        clientList.stream().filter(x -> x.getId() == clientId).findAny().get().getBin().setProductList(new LinkedList<Product>());
    }
}
