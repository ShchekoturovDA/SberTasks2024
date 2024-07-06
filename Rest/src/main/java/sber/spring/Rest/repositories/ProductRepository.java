package sber.spring.Rest.repositories;

import sber.spring.Rest.entities.Client;
import sber.spring.Rest.entities.Product;

import java.util.List;
import java.util.Optional;

public class ProductRepository {
    private List<Product> productList;
    private int ids;

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
        Product update = productList.stream()
                .filter(x -> x.getId() == product.getId())
                .findAny()
                .orElse(null);
        if (update != null){
            productList.remove(update);
            productList.add(product);
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
}
