package org.code.bluetick.persistence.repository;

import org.code.bluetick.persistence.model.TravelDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TravelDetailRepository extends JpaRepository<TravelDetail, Long> {
}
