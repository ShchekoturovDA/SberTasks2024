package sber.spring.RestJPA.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sber.spring.RestJPA.entities.Bin;
import sber.spring.RestJPA.entities.ProductBin;
import sber.spring.RestJPA.repositories.BinRepository;
import sber.spring.RestJPA.repositories.ProductBinRepository;

import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class BinService {

    private final BinRepository binRepository;
    private final ProductBinRepository productBinRepository;

    @Autowired
    public BinService(BinRepository binRepository, ProductBinRepository productBinRepository){
        this.binRepository = binRepository;
        this.productBinRepository = productBinRepository;
    }

    public Bin save(){
        Bin bin = new Bin();
        bin.setPromoCode("");
        return binRepository.save(bin);
    }

    public void saveProduct(ProductBin productBin) {
        productBinRepository.save(productBin);
    }

    public Optional<Bin> findById(int binId){
        return binRepository.findById(binId);
    }

    public boolean isExist(int binId){
        return binRepository.existsById(binId);
    }

    public boolean isInBin(int binId, int productId){
        return productBinRepository.findAll().stream().filter(x -> x.getBinId().getId() == binId && x.getProduct().getId() == productId).findAny().isPresent();
    }

    public void add(int binId, int productId){

    }

    public Optional<ProductBin> findProductBin(int binId, int productId) {
        return productBinRepository.findAll().stream().filter(x -> x.getBinId().getId() == binId && x.getProduct().getId() == productId).findAny();
    }

    public void deleteProduct(int binId, int productId) {
        productBinRepository.deleteById(productBinRepository.findAll().stream().filter(x -> x.getBinId().getId() == binId && x.getProduct().getId() == productId).findAny().get().getId());
    }

    public void pay(int binId) {
    Sort
        productBinRepository.findAll()
    }
}
