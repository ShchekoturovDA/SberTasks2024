package sber.spring.JDBCRest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sber.spring.JDBCRest.entities.Product;
import sber.spring.JDBCRest.repositories.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

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


}
