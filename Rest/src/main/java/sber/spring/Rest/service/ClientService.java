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
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private BinRepository binRepository;

    public int saveClient(Client client) {
        if (!clientRepository.isClient(client)) {
            return clientRepository.signClient(client, binRepository.createBin());
        } else {
            return 0;
        }
    }

    public void updateBin(int clientId){
        clientRepository.updateBin(clientId, binRepository.getBin(clientId));
    }

    public Optional<Client> searchClientRep(int id) {
        return clientRepository.searchClient(id);
    }

    public boolean deleteClientFromRep(int id) {
        return clientRepository.deleteClient(id);
    }

    public long saveProduct(Product product) {
        return productRepository.addProduct(product);
    }
    public void updateProduct(Product product) {
        productRepository.update(product);
    }

    public Optional<Product> searchProductRep(int id) {
        return productRepository.search(id);
    }

    public boolean deleteProductFromRep(int id) {
        return productRepository.delete(id);
    }

    public List<Product> searchProductByNameRep(String name) {
        return productRepository.searchByName(name);
    }



    public void sellProduct(int id, int quantity) {
        productRepository.sell(id, quantity);
    }

    public Optional<Bin> searchBinRep(long binId) {
        return binRepository.search(binId);
    }

    public boolean isInBin(long binId, long productId) {
        return binRepository.isInBin(binId, productId);
    }

    public void addToBin(int binId, int productId) {
        binRepository.add(binId, productRepository.search(productId).get());
        clientRepository.updateBin(binId, binRepository.getBin(binId));
    }

    public void changeQuantity(int binId, int productId, int quantity) {
        binRepository.changeQuantity(binId, productId, quantity);
        clientRepository.updateBin(binId, binRepository.getBin(binId));
    }

    public void deleteProductFromBin(int binId, int productId) {
        binRepository.deleteFromBin(binId, productId);
        clientRepository.updateBin(binId, binRepository.getBin(binId));
    }

    public void pay(int binId) {
        binRepository.pay(binId);
        clientRepository.updateBin(binId, binRepository.getBin(binId));
    }
}
