package io.eventmanager.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * The Media Type entity.
 */
@ApiModel(description = "The Media Type entity.")
@Entity
@Table(name = "media_type")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "mediatype")
public class MediaType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(min = 3, max = 50)
    @Column(name = "media_type", length = 50, nullable = false)
    private String mediaType;

    @Size(min = 3, max = 255)
    @Column(name = "image_url", length = 255)
    private String imageUrl;

    @OneToMany(mappedBy = "mediaType")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Media> media = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMediaType() {
        return mediaType;
    }

    public MediaType mediaType(String mediaType) {
        this.mediaType = mediaType;
        return this;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public MediaType imageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Set<Media> getMedia() {
        return media;
    }

    public MediaType media(Set<Media> media) {
        this.media = media;
        return this;
    }

    public MediaType addMedia(Media media) {
        this.media.add(media);
        media.setMediaType(this);
        return this;
    }

    public MediaType removeMedia(Media media) {
        this.media.remove(media);
        media.setMediaType(null);
        return this;
    }

    public void setMedia(Set<Media> media) {
        this.media = media;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MediaType mediaType = (MediaType) o;
        if (mediaType.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mediaType.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MediaType{" +
            "id=" + getId() +
            ", mediaType='" + getMediaType() + "'" +
            ", imageUrl='" + getImageUrl() + "'" +
            "}";
    }
}
