package sber.spring.RestJPA.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sber.spring.RestJPA.entities.Client;
import sber.spring.RestJPA.repositories.ClientRepository;

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

    public void save(Client client){
        clientRepository.save(client);
    }

    public void findById(int id){
        clientRepository.findById(id);
    }

    public void delete(int id){
        clientRepository.deleteById(id);
    }
}
