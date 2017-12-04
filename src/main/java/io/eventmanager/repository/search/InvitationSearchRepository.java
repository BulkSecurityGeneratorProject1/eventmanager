package io.eventmanager.repository.search;

import io.eventmanager.domain.Invitation;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Invitation entity.
 */
public interface InvitationSearchRepository extends ElasticsearchRepository<Invitation, Long> {
}
