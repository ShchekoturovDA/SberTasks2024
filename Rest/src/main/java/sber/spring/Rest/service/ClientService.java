package sber.spring.Rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sber.spring.Rest.entities.Client;
import sber.spring.Rest.repositories.ClientRepository;
import java.io.File;
import java.util.Optional;

@Service
public class ClientService {
    @Autowired
    private ClientRepository clientRepository;

    public boolean isClient(Client client) {
       return clientRepository.isClient(client);
    }

    public int saveClient(Client client, int binId) {
        return clientRepository.signClient(client, binId);
    }

    public Optional<Client> searchClientRep(int id) {
        return clientRepository.searchClient(id);
    }

    public boolean deleteClientFromRep(int id) {
        return clientRepository.deleteClient(id);
    }
}
