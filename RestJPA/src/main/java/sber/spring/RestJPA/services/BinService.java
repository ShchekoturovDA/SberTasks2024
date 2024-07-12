package sber.spring.RestJPA.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sber.spring.RestJPA.entities.Bin;
import sber.spring.RestJPA.entities.Product;
import sber.spring.RestJPA.entities.ProductBin;
import sber.spring.RestJPA.repositories.BinRepository;
import sber.spring.RestJPA.repositories.ProductBinRepository;

import java.util.List;
import java.util.Optional;

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

    public Optional<ProductBin> findByBinProduct(Bin binId, Product product){
        return productBinRepository.findByBinIdAndProduct(binId, product);
    }

    public List<ProductBin> findByBin(Bin binId){
        return productBinRepository.findAllByBinId(binId);
    }


    public void add(int binId, int productId){

    }

    public void deleteProduct(int binId, int productId) {
        productBinRepository.deleteById(productBinRepository.findAll().stream().filter(x -> x.getBinId().getId() == binId && x.getProduct().getId() == productId).findAny().get().getId());
    }

    public void deleteAllByBinId(Bin bin){
        productBinRepository.deleteAllByBinId(bin);
    }

    public void pay(int binId) {

    }
}
