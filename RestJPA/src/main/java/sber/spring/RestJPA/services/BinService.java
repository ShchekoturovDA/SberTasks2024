package sber.spring.RestJPA.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sber.spring.RestJPA.repositories.BinRepository;
import sber.spring.RestJPA.repositories.ProductBinRepository;

@Service
public class BinService {

    private final BinRepository binRepository;
    private final ProductBinRepository productBinRepository;

    @Autowired
    public BinService(BinRepository binRepository, ProductBinRepository productBinRepository){
        this.binRepository = binRepository;
        this.productBinRepository = productBinRepository;
    }

    public boolean isExist(int binId){
        return binRepository.existsById(binId);
    }

    public boolean isInBin(int binId, int productId){
        return productBinRepository.findAll().stream().filter(x -> x.getBinId().getId() == binId && x.getProduct().getId() == productId).findAny().isPresent();
    }

    public void add(int binId, int productId){

    }

}
