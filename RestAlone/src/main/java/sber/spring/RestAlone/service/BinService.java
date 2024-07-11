package sber.spring.RestAlone.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sber.spring.RestAlone.entities.Bin;
import sber.spring.RestAlone.entities.Product;
import sber.spring.RestAlone.repositories.BinRepository;

import java.util.Optional;

@Service
public class BinService {

    @Autowired
    private BinRepository binRepository;

    public Bin createBin() {
        return binRepository.createBin();
    }

    public void addToBin(long binId, Product product) {
        binRepository.add(binId, product);
    }

    public boolean isInBin(long binId, long productId) {
        return binRepository.isInBin(binId, productId);
    }

    public void changeQuantity(long binId, long productId, int quantity) {
        binRepository.changeQuantity(binId, productId, quantity);
    }

    public void deleteProductFromBin(long binId, long productId) {
        binRepository.deleteFromBin(binId, productId);
    }

    public void pay(long binId) {
        binRepository.pay(binId);
    }

    public Optional<Bin> searchBinRep(long binId) {
        return binRepository.search(binId);
    }
}
