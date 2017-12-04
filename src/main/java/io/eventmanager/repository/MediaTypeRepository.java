package io.eventmanager.repository;

import io.eventmanager.domain.MediaType;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the MediaType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MediaTypeRepository extends JpaRepository<MediaType, Long> {

}
