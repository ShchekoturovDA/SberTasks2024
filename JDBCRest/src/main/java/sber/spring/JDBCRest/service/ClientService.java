package sber.spring.JDBCRest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sber.spring.JDBCRest.entities.Bin;
import sber.spring.JDBCRest.entities.Client;
import sber.spring.JDBCRest.entities.Product;
import sber.spring.JDBCRest.repositories.BinRepository;
import sber.spring.JDBCRest.repositories.ClientRepository;
import sber.spring.JDBCRest.repositories.ProductRepository;
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

    public Optional<Bin> searchBinRep(int binId) {
        return binRepository.search(binId);
    }

    public boolean isInBin(int binId, int productId) {
        return binRepository.isInBin(binId, productId);
    }

    public int addToBin(int binId, int productId) {
        return binRepository.add(binId, productId);
    }

    public void changeQuantity(int binId, int productId, int quantity) {
        binRepository.changeQuantity(binId, productId, quantity);
    }

    public boolean deleteProductFromBin(int binId, int productId) {
         return binRepository.deleteFromBin(binId, productId);
    }

    public boolean pay(int binId) {
        return binRepository.pay(binId);
    }

}
