package sber.spring.Rest.repositories;

import org.springframework.stereotype.Repository;
import sber.spring.Rest.entities.Bin;
import sber.spring.Rest.entities.Product;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Repository
public class BinRepository {
    private List<Bin> binList = new ArrayList<Bin>();

    public Bin createBin() {
        Bin bin = new Bin();
        bin.setProductList(new ArrayList<Product>());
        binList.add(bin);
        return bin;
    }

    public Optional<Bin> search(long binId) {
        return binList.stream().filter(x -> x.getId() == binId).findAny();
    }

    public boolean isInBin(long binId, long productId){
        return !binList.stream().filter(x -> x.getId() == binId).findAny().get().getProductList().stream().filter(x -> x.getId() == productId).findAny().isEmpty();
    }

    public void add(long binId, Product product) {
        product.setQuantity(1);
        binList.stream().filter(x -> x.getId() == binId).findAny().get().getProductList().add(product);
    }

    public void changeQuantity(long binId, long productId, int quantity) {
        binList.stream().filter(x -> x.getId() == binId).findAny().get().getProductList().stream().filter(x -> x.getId() == productId).findAny().get().setQuantity(quantity);
    }

    public void deleteFromBin(long binId, long productId) {
        binList.stream()
                .filter(x -> x.getId() == binId)
                .findAny()
                .get().getProductList().remove(binList.stream()
                        .filter(x -> x.getId() == binId)
                        .findAny()
                        .get().getProductList().stream()
                                .filter(x -> x.getId() == productId)
                                .findAny()
                                .get());
    }

    public void pay(long binId) {
        binList.stream().filter(x -> x.getId() == binId).findAny().get().setProductList(new LinkedList<Product>());
    }


    public Bin getBin(long binId) {
        return binList.stream().filter(x -> x.getId() == binId).findAny().get();
    }
}
