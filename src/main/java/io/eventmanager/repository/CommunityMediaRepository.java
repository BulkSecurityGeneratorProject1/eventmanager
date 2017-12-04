package io.eventmanager.repository;

import io.eventmanager.domain.CommunityMedia;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the CommunityMedia entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CommunityMediaRepository extends JpaRepository<CommunityMedia, Long> {

}
