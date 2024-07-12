package sber.spring.RestJPA.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sber.spring.RestJPA.entities.Product;
import sber.spring.RestJPA.repositories.ProductRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService (ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    public boolean isExist(int id){
        return productRepository.existsById(id);

    }

    public Product save(Product product){
       return productRepository.save(product);
    }

    public void delete(int id){
        productRepository.deleteById(id);
    }

    public Optional<Product> getById(int id){
        return productRepository.findById(id);
    }

    public List<Product> findAllByName(String name){
        return productRepository.findByName(name);
    }

    public void sell(int quantity, int productId){
        productRepository.sell(quantity, productId);
    }
}
