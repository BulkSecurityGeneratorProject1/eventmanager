package io.eventmanager.repository.search;

import io.eventmanager.domain.UserParticipate;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the UserParticipate entity.
 */
public interface UserParticipateSearchRepository extends ElasticsearchRepository<UserParticipate, Long> {
}
