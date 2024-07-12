package sber.spring.RestJPA.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sber.spring.RestJPA.entities.ProductBin;

public interface ProductBinRepository extends JpaRepository<ProductBin, Integer> {
}
