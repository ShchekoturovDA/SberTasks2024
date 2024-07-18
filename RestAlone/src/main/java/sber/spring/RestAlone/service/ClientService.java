package sber.spring.RestAlone.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sber.spring.RestAlone.entities.Bin;
import sber.spring.RestAlone.entities.Client;
import sber.spring.RestAlone.repositories.ClientRepository;

import java.util.Optional;

@Service
public class ClientService {
    @Autowired
    private ClientRepository clientRepository;

    public long saveClient(Client client, Bin bin) {
        return clientRepository.signClient(client, bin);
    }

    public Optional<Client> searchClientRep(long id) {
        return clientRepository.searchClient(id);
    }

    public boolean deleteClientFromRep(long id) {
        return clientRepository.deleteClient(id);
    }

    public void updateBin(long clientId, Bin bin) {
        clientRepository.updateBin(clientId, bin);
    }

}
