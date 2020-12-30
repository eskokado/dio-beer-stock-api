package org.esk.diobeerstockapi.repositories;

import org.esk.diobeerstockapi.entities.Beer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BeerRepository extends JpaRepository<Beer, Long> {
    Optional<Beer> findByName(String name);
}
