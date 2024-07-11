package sber.spring.Rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sber.spring.Rest.entities.Bin;
import sber.spring.Rest.repositories.BinRepository;

import java.util.Optional;

@Service
public class BinService {
    @Autowired
    private BinRepository binRepository;

    public int createBin() {
        return binRepository.createBin();
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

    @Transactional
    public boolean pay(int binId) {
        return binRepository.pay(binId);
    }

}
