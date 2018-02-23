package io.github.jhipster.application.service.mapper;

import io.github.jhipster.application.domain.*;
import io.github.jhipster.application.service.dto.WorkplaceDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Workplace and its DTO WorkplaceDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface WorkplaceMapper extends EntityMapper<WorkplaceDTO, Workplace> {


    @Mapping(target = "resourceIds", ignore = true)
    Workplace toEntity(WorkplaceDTO workplaceDTO);

    default Workplace fromId(Long id) {
        if (id == null) {
            return null;
        }
        Workplace workplace = new Workplace();
        workplace.setId(id);
        return workplace;
    }
}
