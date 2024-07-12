package sber.spring.RestJPA.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
import sber.spring.RestJPA.entities.Bin;
import sber.spring.RestJPA.entities.Product;
import sber.spring.RestJPA.entities.ProductBin;

import java.util.List;
import java.util.Optional;

public interface ProductBinRepository extends JpaRepository<ProductBin, Integer> {
    Optional<ProductBin> findByBinIdAndProduct(Bin binId, Product product);

    List<ProductBin> findAllByBinId(Bin binId);

    @Modifying
    @Transactional
    void deleteAllByBinId(Bin binId);
}
