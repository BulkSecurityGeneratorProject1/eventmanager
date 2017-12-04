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
 * The Event entity.
 */
@ApiModel(description = "The Event entity.")
@Entity
@Table(name = "event")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "event")
public class Event implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(min = 3, max = 120)
    @Column(name = "event", length = 120, nullable = false)
    private String event;

    @Size(min = 3, max = 50)
    @Column(name = "description", length = 50)
    private String description;

    @Column(name = "number_of_places")
    private Integer numberOfPlaces;

    @Column(name = "number_of_places_remaining")
    private Integer numberOfPlacesRemaining;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "start_event")
    private ZonedDateTime startEvent;

    @Column(name = "end_event")
    private ZonedDateTime endEvent;

    @Size(min = 3, max = 255)
    @Column(name = "image_url", length = 255)
    private String imageUrl;

    @Size(min = 3, max = 255)
    @Column(name = "others", length = 255)
    private String others;

    @Column(name = "private_event")
    private Boolean privateEvent;

    @Column(name = "status_event")
    private Boolean statusEvent;

    @Column(name = "created")
    private ZonedDateTime created;

    @ManyToOne
    private EventType eventType;

    @OneToMany(mappedBy = "event")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Comment> comments = new HashSet<>();

    @OneToMany(mappedBy = "event")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Invitation> invitations = new HashSet<>();

    @OneToMany(mappedBy = "event")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<UserParticipate> userParticipates = new HashSet<>();

    @ManyToOne
    private EventMedia eventMedia;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEvent() {
        return event;
    }

    public Event event(String event) {
        this.event = event;
        return this;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getDescription() {
        return description;
    }

    public Event description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getNumberOfPlaces() {
        return numberOfPlaces;
    }

    public Event numberOfPlaces(Integer numberOfPlaces) {
        this.numberOfPlaces = numberOfPlaces;
        return this;
    }

    public void setNumberOfPlaces(Integer numberOfPlaces) {
        this.numberOfPlaces = numberOfPlaces;
    }

    public Integer getNumberOfPlacesRemaining() {
        return numberOfPlacesRemaining;
    }

    public Event numberOfPlacesRemaining(Integer numberOfPlacesRemaining) {
        this.numberOfPlacesRemaining = numberOfPlacesRemaining;
        return this;
    }

    public void setNumberOfPlacesRemaining(Integer numberOfPlacesRemaining) {
        this.numberOfPlacesRemaining = numberOfPlacesRemaining;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Event latitude(Double latitude) {
        this.latitude = latitude;
        return this;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public Event longitude(Double longitude) {
        this.longitude = longitude;
        return this;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public ZonedDateTime getStartEvent() {
        return startEvent;
    }

    public Event startEvent(ZonedDateTime startEvent) {
        this.startEvent = startEvent;
        return this;
    }

    public void setStartEvent(ZonedDateTime startEvent) {
        this.startEvent = startEvent;
    }

    public ZonedDateTime getEndEvent() {
        return endEvent;
    }

    public Event endEvent(ZonedDateTime endEvent) {
        this.endEvent = endEvent;
        return this;
    }

    public void setEndEvent(ZonedDateTime endEvent) {
        this.endEvent = endEvent;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Event imageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getOthers() {
        return others;
    }

    public Event others(String others) {
        this.others = others;
        return this;
    }

    public void setOthers(String others) {
        this.others = others;
    }

    public Boolean isPrivateEvent() {
        return privateEvent;
    }

    public Event privateEvent(Boolean privateEvent) {
        this.privateEvent = privateEvent;
        return this;
    }

    public void setPrivateEvent(Boolean privateEvent) {
        this.privateEvent = privateEvent;
    }

    public Boolean isStatusEvent() {
        return statusEvent;
    }

    public Event statusEvent(Boolean statusEvent) {
        this.statusEvent = statusEvent;
        return this;
    }

    public void setStatusEvent(Boolean statusEvent) {
        this.statusEvent = statusEvent;
    }

    public ZonedDateTime getCreated() {
        return created;
    }

    public Event created(ZonedDateTime created) {
        this.created = created;
        return this;
    }

    public void setCreated(ZonedDateTime created) {
        this.created = created;
    }

    public EventType getEventType() {
        return eventType;
    }

    public Event eventType(EventType eventType) {
        this.eventType = eventType;
        return this;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public Event comments(Set<Comment> comments) {
        this.comments = comments;
        return this;
    }

    public Event addComment(Comment comment) {
        this.comments.add(comment);
        comment.setEvent(this);
        return this;
    }

    public Event removeComment(Comment comment) {
        this.comments.remove(comment);
        comment.setEvent(null);
        return this;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public Set<Invitation> getInvitations() {
        return invitations;
    }

    public Event invitations(Set<Invitation> invitations) {
        this.invitations = invitations;
        return this;
    }

    public Event addInvitation(Invitation invitation) {
        this.invitations.add(invitation);
        invitation.setEvent(this);
        return this;
    }

    public Event removeInvitation(Invitation invitation) {
        this.invitations.remove(invitation);
        invitation.setEvent(null);
        return this;
    }

    public void setInvitations(Set<Invitation> invitations) {
        this.invitations = invitations;
    }

    public Set<UserParticipate> getUserParticipates() {
        return userParticipates;
    }

    public Event userParticipates(Set<UserParticipate> userParticipates) {
        this.userParticipates = userParticipates;
        return this;
    }

    public Event addUserParticipate(UserParticipate userParticipate) {
        this.userParticipates.add(userParticipate);
        userParticipate.setEvent(this);
        return this;
    }

    public Event removeUserParticipate(UserParticipate userParticipate) {
        this.userParticipates.remove(userParticipate);
        userParticipate.setEvent(null);
        return this;
    }

    public void setUserParticipates(Set<UserParticipate> userParticipates) {
        this.userParticipates = userParticipates;
    }

    public EventMedia getEventMedia() {
        return eventMedia;
    }

    public Event eventMedia(EventMedia eventMedia) {
        this.eventMedia = eventMedia;
        return this;
    }

    public void setEventMedia(EventMedia eventMedia) {
        this.eventMedia = eventMedia;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Event event = (Event) o;
        if (event.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), event.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Event{" +
            "id=" + getId() +
            ", event='" + getEvent() + "'" +
            ", description='" + getDescription() + "'" +
            ", numberOfPlaces=" + getNumberOfPlaces() +
            ", numberOfPlacesRemaining=" + getNumberOfPlacesRemaining() +
            ", latitude=" + getLatitude() +
            ", longitude=" + getLongitude() +
            ", startEvent='" + getStartEvent() + "'" +
            ", endEvent='" + getEndEvent() + "'" +
            ", imageUrl='" + getImageUrl() + "'" +
            ", others='" + getOthers() + "'" +
            ", privateEvent='" + isPrivateEvent() + "'" +
            ", statusEvent='" + isStatusEvent() + "'" +
            ", created='" + getCreated() + "'" +
            "}";
    }
}
