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
 * The Event Type entity.
 */
@ApiModel(description = "The Event Type entity.")
@Entity
@Table(name = "event_type")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "eventtype")
public class EventType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(min = 3, max = 50)
    @Column(name = "event_type", length = 50, nullable = false)
    private String eventType;

    @Size(min = 3, max = 255)
    @Column(name = "image_url", length = 255)
    private String imageUrl;

    @OneToMany(mappedBy = "eventType")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Event> events = new HashSet<>();


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEventType() {
        return eventType;
    }

    public EventType eventType(String eventType) {
        this.eventType = eventType;
        return this;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public EventType imageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Set<Event> getEvents() {
        return events;
    }

    public EventType events(Set<Event> events) {
        this.events = events;
        return this;
    }

    public EventType addEvent(Event event) {
        this.events.add(event);
        event.setEventType(this);
        return this;
    }

    public EventType removeEvent(Event event) {
        this.events.remove(event);
        event.setEventType(null);
        return this;
    }

    public void setEvents(Set<Event> events) {
        this.events = events;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        EventType eventType = (EventType) o;
        if (eventType.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), eventType.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EventType{" +
            "id=" + getId() +
            ", eventType='" + getEventType() + "'" +
            ", imageUrl='" + getImageUrl() + "'" +
            "}";
    }
}
