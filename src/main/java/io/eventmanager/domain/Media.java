package io.eventmanager.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * The Media entity.
 */
@ApiModel(description = "The Media entity.")
@Entity
@Table(name = "media")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "media")
public class Media implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(min = 3, max = 50)
    @Column(name = "media", length = 50, nullable = false)
    private String media;

    @NotNull
    @Size(min = 3, max = 255)
    @Column(name = "media_url", length = 255, nullable = false)
    private String mediaUrl;

    @Column(name = "created")
    private ZonedDateTime created;

    @ManyToOne
    private MediaType mediaType;

    @OneToMany(mappedBy = "media")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<EventMedia> eventMedias = new HashSet<>();

    @OneToMany(mappedBy = "media")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CommunityMedia> communityMedias = new HashSet<>();


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMedia() {
        return media;
    }

    public Media media(String media) {
        this.media = media;
        return this;
    }

    public void setMedia(String media) {
        this.media = media;
    }

    public String getMediaUrl() {
        return mediaUrl;
    }

    public Media mediaUrl(String mediaUrl) {
        this.mediaUrl = mediaUrl;
        return this;
    }

    public void setMediaUrl(String mediaUrl) {
        this.mediaUrl = mediaUrl;
    }

    public ZonedDateTime getCreated() {
        return created;
    }

    public Media created(ZonedDateTime created) {
        this.created = created;
        return this;
    }

    public void setCreated(ZonedDateTime created) {
        this.created = created;
    }

    public MediaType getMediaType() {
        return mediaType;
    }

    public Media mediaType(MediaType mediaType) {
        this.mediaType = mediaType;
        return this;
    }

    public void setMediaType(MediaType mediaType) {
        this.mediaType = mediaType;
    }

    public Set<EventMedia> getEventMedias() {
        return eventMedias;
    }

    public Media eventMedias(Set<EventMedia> eventMedias) {
        this.eventMedias = eventMedias;
        return this;
    }

    public Media addEventMedia(EventMedia eventMedia) {
        this.eventMedias.add(eventMedia);
        eventMedia.setMedia(this);
        return this;
    }

    public Media removeEventMedia(EventMedia eventMedia) {
        this.eventMedias.remove(eventMedia);
        eventMedia.setMedia(null);
        return this;
    }

    public void setEventMedias(Set<EventMedia> eventMedias) {
        this.eventMedias = eventMedias;
    }

    public Set<CommunityMedia> getCommunityMedias() {
        return communityMedias;
    }

    public Media communityMedias(Set<CommunityMedia> communityMedias) {
        this.communityMedias = communityMedias;
        return this;
    }

    public Media addCommunityMedia(CommunityMedia communityMedia) {
        this.communityMedias.add(communityMedia);
        communityMedia.setMedia(this);
        return this;
    }

    public Media removeCommunityMedia(CommunityMedia communityMedia) {
        this.communityMedias.remove(communityMedia);
        communityMedia.setMedia(null);
        return this;
    }

    public void setCommunityMedias(Set<CommunityMedia> communityMedias) {
        this.communityMedias = communityMedias;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Media media = (Media) o;
        if (media.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), media.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Media{" +
            "id=" + getId() +
            ", media='" + getMedia() + "'" +
            ", mediaUrl='" + getMediaUrl() + "'" +
            ", created='" + getCreated() + "'" +
            "}";
    }
}
