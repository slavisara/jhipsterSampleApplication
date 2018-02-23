package io.github.jhipster.application.service.impl;

import io.github.jhipster.application.service.WorkplaceService;
import io.github.jhipster.application.domain.Workplace;
import io.github.jhipster.application.repository.WorkplaceRepository;
import io.github.jhipster.application.service.dto.WorkplaceDTO;
import io.github.jhipster.application.service.mapper.WorkplaceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Workplace.
 */
@Service
@Transactional
public class WorkplaceServiceImpl implements WorkplaceService {

    private final Logger log = LoggerFactory.getLogger(WorkplaceServiceImpl.class);

    private final WorkplaceRepository workplaceRepository;

    private final WorkplaceMapper workplaceMapper;

    public WorkplaceServiceImpl(WorkplaceRepository workplaceRepository, WorkplaceMapper workplaceMapper) {
        this.workplaceRepository = workplaceRepository;
        this.workplaceMapper = workplaceMapper;
    }

    /**
     * Save a workplace.
     *
     * @param workplaceDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public WorkplaceDTO save(WorkplaceDTO workplaceDTO) {
        log.debug("Request to save Workplace : {}", workplaceDTO);
        Workplace workplace = workplaceMapper.toEntity(workplaceDTO);
        workplace = workplaceRepository.save(workplace);
        return workplaceMapper.toDto(workplace);
    }

    /**
     * Get all the workplaces.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<WorkplaceDTO> findAll() {
        log.debug("Request to get all Workplaces");
        return workplaceRepository.findAllWithEagerRelationships().stream()
            .map(workplaceMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one workplace by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public WorkplaceDTO findOne(Long id) {
        log.debug("Request to get Workplace : {}", id);
        Workplace workplace = workplaceRepository.findOneWithEagerRelationships(id);
        return workplaceMapper.toDto(workplace);
    }

    /**
     * Delete the workplace by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Workplace : {}", id);
        workplaceRepository.delete(id);
    }
}
