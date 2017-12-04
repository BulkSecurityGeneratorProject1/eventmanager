package io.eventmanager.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * The EventMedia Media entity.
 */
@ApiModel(description = "The EventMedia Media entity.")
@Entity
@Table(name = "event_media")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "eventmedia")
public class EventMedia implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "created")
    private ZonedDateTime created;

    @ManyToOne
    private Media media;

    @OneToMany(mappedBy = "eventMedia")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Event> events = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getCreated() {
        return created;
    }

    public EventMedia created(ZonedDateTime created) {
        this.created = created;
        return this;
    }

    public void setCreated(ZonedDateTime created) {
        this.created = created;
    }

    public Media getMedia() {
        return media;
    }

    public EventMedia media(Media media) {
        this.media = media;
        return this;
    }

    public void setMedia(Media media) {
        this.media = media;
    }

    public Set<Event> getEvents() {
        return events;
    }

    public EventMedia events(Set<Event> events) {
        this.events = events;
        return this;
    }

    public EventMedia addEvent(Event event) {
        this.events.add(event);
        event.setEventMedia(this);
        return this;
    }

    public EventMedia removeEvent(Event event) {
        this.events.remove(event);
        event.setEventMedia(null);
        return this;
    }

    public void setEvents(Set<Event> events) {
        this.events = events;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        EventMedia eventMedia = (EventMedia) o;
        if (eventMedia.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), eventMedia.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EventMedia{" +
            "id=" + getId() +
            ", created='" + getCreated() + "'" +
            "}";
    }
}
