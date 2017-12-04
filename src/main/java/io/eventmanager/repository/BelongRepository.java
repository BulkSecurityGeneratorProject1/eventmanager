package io.eventmanager.repository;

import io.eventmanager.domain.Belong;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Belong entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BelongRepository extends JpaRepository<Belong, Long> {

}
