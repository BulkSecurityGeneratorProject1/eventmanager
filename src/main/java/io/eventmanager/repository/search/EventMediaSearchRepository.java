package io.eventmanager.repository.search;

import io.eventmanager.domain.EventMedia;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the EventMedia entity.
 */
public interface EventMediaSearchRepository extends ElasticsearchRepository<EventMedia, Long> {
}
