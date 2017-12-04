package io.eventmanager.repository.search;

import io.eventmanager.domain.Owner;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Owner entity.
 */
public interface OwnerSearchRepository extends ElasticsearchRepository<Owner, Long> {
}
