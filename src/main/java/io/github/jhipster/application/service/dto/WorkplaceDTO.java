package io.github.jhipster.application.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Workplace entity.
 */
public class WorkplaceDTO implements Serializable {

    private Long id;

    @NotNull
    private Long resourceId;

    private String workplaceName;

    private Set<WorkplaceDTO> workplaces = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getResourceId() {
        return resourceId;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }

    public String getWorkplaceName() {
        return workplaceName;
    }

    public void setWorkplaceName(String workplaceName) {
        this.workplaceName = workplaceName;
    }

    public Set<WorkplaceDTO> getWorkplaces() {
        return workplaces;
    }

    public void setWorkplaces(Set<WorkplaceDTO> workplaces) {
        this.workplaces = workplaces;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        WorkplaceDTO workplaceDTO = (WorkplaceDTO) o;
        if(workplaceDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), workplaceDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "WorkplaceDTO{" +
            "id=" + getId() +
            ", resourceId=" + getResourceId() +
            ", workplaceName='" + getWorkplaceName() + "'" +
            "}";
    }
}
