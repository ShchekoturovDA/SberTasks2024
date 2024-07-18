package sber.spring.RestAlone.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import sber.spring.RestAlone.entities.Product;
import sber.spring.RestAlone.repositories.ProductRepository;

import java.util.List;
import java.util.Optional;

@RestController
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

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

    public List<Product> searchProductByNameRep(String name) {
        return productRepository.searchByName(name);
    }


    public void sellProduct(long id, int quantity) {
        productRepository.sell(id, quantity);
    }


}
