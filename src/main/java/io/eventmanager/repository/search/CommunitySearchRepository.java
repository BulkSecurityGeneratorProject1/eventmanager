package io.eventmanager.repository.search;

import io.eventmanager.domain.Community;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Community entity.
 */
public interface CommunitySearchRepository extends ElasticsearchRepository<Community, Long> {
}
