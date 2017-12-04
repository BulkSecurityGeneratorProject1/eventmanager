package io.eventmanager.repository.search;

import io.eventmanager.domain.Belong;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Belong entity.
 */
public interface BelongSearchRepository extends ElasticsearchRepository<Belong, Long> {
}
