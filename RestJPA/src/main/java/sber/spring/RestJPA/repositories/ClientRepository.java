package sber.spring.RestJPA.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sber.spring.RestJPA.entities.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {
}
