package io.eventmanager.repository.search;

import io.eventmanager.domain.CommunityMedia;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the CommunityMedia entity.
 */
public interface CommunityMediaSearchRepository extends ElasticsearchRepository<CommunityMedia, Long> {
}
