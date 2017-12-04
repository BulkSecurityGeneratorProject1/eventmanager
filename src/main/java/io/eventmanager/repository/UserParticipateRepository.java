package io.eventmanager.repository;

import io.eventmanager.domain.UserParticipate;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the UserParticipate entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserParticipateRepository extends JpaRepository<UserParticipate, Long> {

}
