package sber.spring.Rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sber.spring.Rest.entities.Bin;
import sber.spring.Rest.entities.Client;
import sber.spring.Rest.entities.Product;
import sber.spring.Rest.repositories.BinRepository;
import sber.spring.Rest.repositories.ClientRepository;
import sber.spring.Rest.repositories.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {
    @Autowired
    private ClientRepository clientRepository;

    public int saveClient(Client client, int binId) {
        if (!clientRepository.isClient(client)) {
            return clientRepository.signClient(client, binId);
        } else {
            return 0;
        }
    }

    public Optional<Client> searchClientRep(int id) {
        return clientRepository.searchClient(id);
    }

    public boolean deleteClientFromRep(int id) {
        return clientRepository.deleteClient(id);
    }




}
