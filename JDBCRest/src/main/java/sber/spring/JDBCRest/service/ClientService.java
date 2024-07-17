package sber.spring.JDBCRest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sber.spring.JDBCRest.entities.Bin;
import sber.spring.JDBCRest.entities.Client;
import sber.spring.JDBCRest.entities.Product;
import sber.spring.JDBCRest.repositories.ClientRepository;

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
