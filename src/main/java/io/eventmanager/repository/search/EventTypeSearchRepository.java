package io.eventmanager.repository.search;

import io.eventmanager.domain.EventType;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the EventType entity.
 */
public interface EventTypeSearchRepository extends ElasticsearchRepository<EventType, Long> {
}
