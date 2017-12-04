package io.eventmanager.repository;

import io.eventmanager.domain.EventMedia;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the EventMedia entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EventMediaRepository extends JpaRepository<EventMedia, Long> {

}
