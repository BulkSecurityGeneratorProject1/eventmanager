package io.eventmanager.repository.search;

import io.eventmanager.domain.MediaType;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the MediaType entity.
 */
public interface MediaTypeSearchRepository extends ElasticsearchRepository<MediaType, Long> {
}
