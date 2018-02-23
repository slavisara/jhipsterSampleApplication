package io.github.jhipster.application.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Workplace.
 */
@Entity
@Table(name = "workplace")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Workplace implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "resource_id", nullable = false)
    private Long resourceId;

    @Column(name = "workplace_name")
    private String workplaceName;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "workplace_workplace",
               joinColumns = @JoinColumn(name="workplaces_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="workplaces_id", referencedColumnName="id"))
    private Set<Workplace> workplaces = new HashSet<>();

    @ManyToMany(mappedBy = "workplaces")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Workplace> resourceIds = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getResourceId() {
        return resourceId;
    }

    public Workplace resourceId(Long resourceId) {
        this.resourceId = resourceId;
        return this;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }

    public String getWorkplaceName() {
        return workplaceName;
    }

    public Workplace workplaceName(String workplaceName) {
        this.workplaceName = workplaceName;
        return this;
    }

    public void setWorkplaceName(String workplaceName) {
        this.workplaceName = workplaceName;
    }

    public Set<Workplace> getWorkplaces() {
        return workplaces;
    }

    public Workplace workplaces(Set<Workplace> workplaces) {
        this.workplaces = workplaces;
        return this;
    }

    public Workplace addWorkplace(Workplace workplace) {
        this.workplaces.add(workplace);
        workplace.getResourceIds().add(this);
        return this;
    }

    public Workplace removeWorkplace(Workplace workplace) {
        this.workplaces.remove(workplace);
        workplace.getResourceIds().remove(this);
        return this;
    }

    public void setWorkplaces(Set<Workplace> workplaces) {
        this.workplaces = workplaces;
    }

    public Set<Workplace> getResourceIds() {
        return resourceIds;
    }

    public Workplace resourceIds(Set<Workplace> workplaces) {
        this.resourceIds = workplaces;
        return this;
    }

    public Workplace addResourceId(Workplace workplace) {
        this.resourceIds.add(workplace);
        workplace.getWorkplaces().add(this);
        return this;
    }

    public Workplace removeResourceId(Workplace workplace) {
        this.resourceIds.remove(workplace);
        workplace.getWorkplaces().remove(this);
        return this;
    }

    public void setResourceIds(Set<Workplace> workplaces) {
        this.resourceIds = workplaces;
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
        Workplace workplace = (Workplace) o;
        if (workplace.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), workplace.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Workplace{" +
            "id=" + getId() +
            ", resourceId=" + getResourceId() +
            ", workplaceName='" + getWorkplaceName() + "'" +
            "}";
    }
}
