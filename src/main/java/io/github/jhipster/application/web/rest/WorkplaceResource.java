package io.github.jhipster.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.application.service.WorkplaceService;
import io.github.jhipster.application.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.application.web.rest.util.HeaderUtil;
import io.github.jhipster.application.service.dto.WorkplaceDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Workplace.
 */
@RestController
@RequestMapping("/api")
public class WorkplaceResource {

    private final Logger log = LoggerFactory.getLogger(WorkplaceResource.class);

    private static final String ENTITY_NAME = "workplace";

    private final WorkplaceService workplaceService;

    public WorkplaceResource(WorkplaceService workplaceService) {
        this.workplaceService = workplaceService;
    }

    /**
     * POST  /workplaces : Create a new workplace.
     *
     * @param workplaceDTO the workplaceDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new workplaceDTO, or with status 400 (Bad Request) if the workplace has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/workplaces")
    @Timed
    public ResponseEntity<WorkplaceDTO> createWorkplace(@Valid @RequestBody WorkplaceDTO workplaceDTO) throws URISyntaxException {
        log.debug("REST request to save Workplace : {}", workplaceDTO);
        if (workplaceDTO.getId() != null) {
            throw new BadRequestAlertException("A new workplace cannot already have an ID", ENTITY_NAME, "idexists");
        }
        WorkplaceDTO result = workplaceService.save(workplaceDTO);
        return ResponseEntity.created(new URI("/api/workplaces/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /workplaces : Updates an existing workplace.
     *
     * @param workplaceDTO the workplaceDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated workplaceDTO,
     * or with status 400 (Bad Request) if the workplaceDTO is not valid,
     * or with status 500 (Internal Server Error) if the workplaceDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/workplaces")
    @Timed
    public ResponseEntity<WorkplaceDTO> updateWorkplace(@Valid @RequestBody WorkplaceDTO workplaceDTO) throws URISyntaxException {
        log.debug("REST request to update Workplace : {}", workplaceDTO);
        if (workplaceDTO.getId() == null) {
            return createWorkplace(workplaceDTO);
        }
        WorkplaceDTO result = workplaceService.save(workplaceDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, workplaceDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /workplaces : get all the workplaces.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of workplaces in body
     */
    @GetMapping("/workplaces")
    @Timed
    public List<WorkplaceDTO> getAllWorkplaces() {
        log.debug("REST request to get all Workplaces");
        return workplaceService.findAll();
        }

    /**
     * GET  /workplaces/:id : get the "id" workplace.
     *
     * @param id the id of the workplaceDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the workplaceDTO, or with status 404 (Not Found)
     */
    @GetMapping("/workplaces/{id}")
    @Timed
    public ResponseEntity<WorkplaceDTO> getWorkplace(@PathVariable Long id) {
        log.debug("REST request to get Workplace : {}", id);
        WorkplaceDTO workplaceDTO = workplaceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(workplaceDTO));
    }

    /**
     * DELETE  /workplaces/:id : delete the "id" workplace.
     *
     * @param id the id of the workplaceDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/workplaces/{id}")
    @Timed
    public ResponseEntity<Void> deleteWorkplace(@PathVariable Long id) {
        log.debug("REST request to delete Workplace : {}", id);
        workplaceService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
