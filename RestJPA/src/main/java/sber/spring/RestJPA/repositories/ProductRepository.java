package sber.spring.RestJPA.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import sber.spring.RestJPA.entities.Product;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findByName(String name);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(value = "UPDATE Product p SET p.quantity = p.quantity - ?1 WHERE p.id = ?2")
    int sell(Integer quantity, Integer productId);
}
