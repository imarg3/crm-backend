package org.code.bluetick.persistence.repository;

import org.code.bluetick.persistence.model.Traveller;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TravallerRepository extends JpaRepository<Traveller, Long> {
}
