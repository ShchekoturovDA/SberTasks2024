package sber.spring.RestAlone.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import sber.spring.RestAlone.entities.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class ProductRepository {

    private List<Product> productList = new ArrayList<Product>();
    private int ids = 0;

    public long addProduct(Product product) {
        product.setId(generateId());
        productList.add(product);
        return product.getId();
    }

    private long generateId() {
        ids += 1;
        return ids;
    }

    public void update(Product product) {
        for(Product productFromList : productList){
            if(productFromList.getId() == product.getId()){
                productFromList.setName(product.getName());
                productFromList.setQuantity(product.getQuantity());
                productFromList.setValue(product.getValue());
            }
        }
    }

    public Optional<Product> search(long id) {
        return productList.stream()
                .filter(x -> x.getId() == id)
                .findAny();
    }

    public boolean delete(long id) {
        Product delete = productList.stream()
                .filter(x -> x.getId() == id)
                .findAny()
                .orElse(null);
        if (delete == null){
            return false;
        } else {
            productList.remove(delete);
            return true;
        }
    }

    public List<Product> searchByName(String name) {
        return productList.stream().filter(x -> x.getName().equals(name)).collect(Collectors.toList());
    }

    public void sell(long id, int quantity) {
        Product product = search(id).get();
        product.setQuantity(product.getQuantity() - quantity);
        update(product);
    }
}
