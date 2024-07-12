package sber.spring.RestJPA.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sber.spring.RestJPA.entities.Client;
import sber.spring.RestJPA.repositories.ClientRepository;

import java.util.Optional;

@Service
public class ClientService {

    private final ClientRepository clientRepository;

    @Autowired
    public ClientService(ClientRepository clientRepository){
        this.clientRepository = clientRepository;
    }

    public boolean isExists(int id){
        return clientRepository.existsById(id);
    }

    public Client save(Client client){
        return clientRepository.save(client);
    }

    public Optional<Client> findById(int id){
        return clientRepository.findById(id);
    }

    public void delete(int id){
        clientRepository.deleteById(id);
    }

    public boolean uniqueLogin(Client client) {
        return clientRepository.findOneByLogin(client.getLogin()).isPresent();
    }
}
