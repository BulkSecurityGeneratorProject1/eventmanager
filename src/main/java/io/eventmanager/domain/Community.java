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
 * The Group entity.
 */
@ApiModel(description = "The Group entity.")
@Entity
@Table(name = "community")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "community")
public class Community implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(min = 3, max = 50)
    @Column(name = "community", length = 50, nullable = false)
    private String community;

    @Size(min = 3, max = 255)
    @Column(name = "description", length = 255)
    private String description;

    @Size(min = 3, max = 255)
    @Column(name = "image_url", length = 255)
    private String imageUrl;

    @Column(name = "private_group")
    private Boolean privateGroup;

    @Column(name = "created")
    private ZonedDateTime created;

    @OneToMany(mappedBy = "community")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CommunityMedia> communityMedias = new HashSet<>();

    @OneToMany(mappedBy = "community")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Owner> owners = new HashSet<>();

    @OneToMany(mappedBy = "community")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Belong> belongs = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCommunity() {
        return community;
    }

    public Community community(String community) {
        this.community = community;
        return this;
    }

    public void setCommunity(String community) {
        this.community = community;
    }

    public String getDescription() {
        return description;
    }

    public Community description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Community imageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Boolean isPrivateGroup() {
        return privateGroup;
    }

    public Community privateGroup(Boolean privateGroup) {
        this.privateGroup = privateGroup;
        return this;
    }

    public void setPrivateGroup(Boolean privateGroup) {
        this.privateGroup = privateGroup;
    }

    public ZonedDateTime getCreated() {
        return created;
    }

    public Community created(ZonedDateTime created) {
        this.created = created;
        return this;
    }

    public void setCreated(ZonedDateTime created) {
        this.created = created;
    }

    public Set<CommunityMedia> getCommunityMedias() {
        return communityMedias;
    }

    public Community communityMedias(Set<CommunityMedia> communityMedias) {
        this.communityMedias = communityMedias;
        return this;
    }

    public Community addCommunityMedia(CommunityMedia communityMedia) {
        this.communityMedias.add(communityMedia);
        communityMedia.setCommunity(this);
        return this;
    }

    public Community removeCommunityMedia(CommunityMedia communityMedia) {
        this.communityMedias.remove(communityMedia);
        communityMedia.setCommunity(null);
        return this;
    }

    public void setCommunityMedias(Set<CommunityMedia> communityMedias) {
        this.communityMedias = communityMedias;
    }

    public Set<Owner> getOwners() {
        return owners;
    }

    public Community owners(Set<Owner> owners) {
        this.owners = owners;
        return this;
    }

    public Community addOwner(Owner owner) {
        this.owners.add(owner);
        owner.setCommunity(this);
        return this;
    }

    public Community removeOwner(Owner owner) {
        this.owners.remove(owner);
        owner.setCommunity(null);
        return this;
    }

    public void setOwners(Set<Owner> owners) {
        this.owners = owners;
    }

    public Set<Belong> getBelongs() {
        return belongs;
    }

    public Community belongs(Set<Belong> belongs) {
        this.belongs = belongs;
        return this;
    }

    public Community addBelong(Belong belong) {
        this.belongs.add(belong);
        belong.setCommunity(this);
        return this;
    }

    public Community removeBelong(Belong belong) {
        this.belongs.remove(belong);
        belong.setCommunity(null);
        return this;
    }

    public void setBelongs(Set<Belong> belongs) {
        this.belongs = belongs;
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
        Community community = (Community) o;
        if (community.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), community.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Community{" +
            "id=" + getId() +
            ", community='" + getCommunity() + "'" +
            ", description='" + getDescription() + "'" +
            ", imageUrl='" + getImageUrl() + "'" +
            ", privateGroup='" + isPrivateGroup() + "'" +
            ", created='" + getCreated() + "'" +
            "}";
    }
}
