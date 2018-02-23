package io.github.jhipster.application.service;

import io.github.jhipster.application.service.dto.WorkplaceDTO;
import java.util.List;

/**
 * Service Interface for managing Workplace.
 */
public interface WorkplaceService {

    /**
     * Save a workplace.
     *
     * @param workplaceDTO the entity to save
     * @return the persisted entity
     */
    WorkplaceDTO save(WorkplaceDTO workplaceDTO);

    /**
     * Get all the workplaces.
     *
     * @return the list of entities
     */
    List<WorkplaceDTO> findAll();

    /**
     * Get the "id" workplace.
     *
     * @param id the id of the entity
     * @return the entity
     */
    WorkplaceDTO findOne(Long id);

    /**
     * Delete the "id" workplace.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
