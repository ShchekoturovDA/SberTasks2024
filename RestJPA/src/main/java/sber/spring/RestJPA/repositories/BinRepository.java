package sber.spring.RestJPA.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sber.spring.RestJPA.entities.Bin;
import sber.spring.RestJPA.entities.ProductBin;

import java.util.Optional;

@Repository
public interface BinRepository extends JpaRepository<Bin, Integer> {
}
