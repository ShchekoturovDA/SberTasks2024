package sber.spring.Rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sber.spring.Rest.entities.Client;
import sber.spring.Rest.entities.Product;
import sber.spring.Rest.repositories.ClientRepository;
import sber.spring.Rest.repositories.ProductRepository;

import java.util.Optional;

@Service
public class ClientService {
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private ProductRepository productRepository;

    public long saveClient(Client client){
      return clientRepository.signClient(client);
    }

    public Optional<Client> searchClientRep(long id) {
        return clientRepository.searchClient(id);
    }

    public boolean deleteClientFromRep(long id) {
        return clientRepository.deleteClient(id);
    }

    public long saveProduct(Product product) {
        return productRepository.addProduct(product);
    }
    public void updateProduct(Product product) {
        productRepository.update(product);
    }

    public Optional<Product> searchProductRep(long id) {
        return productRepository.search(id);
    }

    public boolean deleteProductFromRep(long id) {
        return productRepository.delete(id);
    }
}
