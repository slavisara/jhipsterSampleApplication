package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.JhipsterSampleApplicationApp;

import io.github.jhipster.application.domain.Workplace;
import io.github.jhipster.application.repository.WorkplaceRepository;
import io.github.jhipster.application.service.WorkplaceService;
import io.github.jhipster.application.service.dto.WorkplaceDTO;
import io.github.jhipster.application.service.mapper.WorkplaceMapper;
import io.github.jhipster.application.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static io.github.jhipster.application.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the WorkplaceResource REST controller.
 *
 * @see WorkplaceResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
public class WorkplaceResourceIntTest {

    private static final Long DEFAULT_RESOURCE_ID = 1L;
    private static final Long UPDATED_RESOURCE_ID = 2L;

    private static final String DEFAULT_WORKPLACE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_WORKPLACE_NAME = "BBBBBBBBBB";

    @Autowired
    private WorkplaceRepository workplaceRepository;

    @Autowired
    private WorkplaceMapper workplaceMapper;

    @Autowired
    private WorkplaceService workplaceService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restWorkplaceMockMvc;

    private Workplace workplace;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final WorkplaceResource workplaceResource = new WorkplaceResource(workplaceService);
        this.restWorkplaceMockMvc = MockMvcBuilders.standaloneSetup(workplaceResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Workplace createEntity(EntityManager em) {
        Workplace workplace = new Workplace()
            .resourceId(DEFAULT_RESOURCE_ID)
            .workplaceName(DEFAULT_WORKPLACE_NAME);
        return workplace;
    }

    @Before
    public void initTest() {
        workplace = createEntity(em);
    }

    @Test
    @Transactional
    public void createWorkplace() throws Exception {
        int databaseSizeBeforeCreate = workplaceRepository.findAll().size();

        // Create the Workplace
        WorkplaceDTO workplaceDTO = workplaceMapper.toDto(workplace);
        restWorkplaceMockMvc.perform(post("/api/workplaces")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workplaceDTO)))
            .andExpect(status().isCreated());

        // Validate the Workplace in the database
        List<Workplace> workplaceList = workplaceRepository.findAll();
        assertThat(workplaceList).hasSize(databaseSizeBeforeCreate + 1);
        Workplace testWorkplace = workplaceList.get(workplaceList.size() - 1);
        assertThat(testWorkplace.getResourceId()).isEqualTo(DEFAULT_RESOURCE_ID);
        assertThat(testWorkplace.getWorkplaceName()).isEqualTo(DEFAULT_WORKPLACE_NAME);
    }

    @Test
    @Transactional
    public void createWorkplaceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = workplaceRepository.findAll().size();

        // Create the Workplace with an existing ID
        workplace.setId(1L);
        WorkplaceDTO workplaceDTO = workplaceMapper.toDto(workplace);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWorkplaceMockMvc.perform(post("/api/workplaces")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workplaceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Workplace in the database
        List<Workplace> workplaceList = workplaceRepository.findAll();
        assertThat(workplaceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkResourceIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = workplaceRepository.findAll().size();
        // set the field null
        workplace.setResourceId(null);

        // Create the Workplace, which fails.
        WorkplaceDTO workplaceDTO = workplaceMapper.toDto(workplace);

        restWorkplaceMockMvc.perform(post("/api/workplaces")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workplaceDTO)))
            .andExpect(status().isBadRequest());

        List<Workplace> workplaceList = workplaceRepository.findAll();
        assertThat(workplaceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllWorkplaces() throws Exception {
        // Initialize the database
        workplaceRepository.saveAndFlush(workplace);

        // Get all the workplaceList
        restWorkplaceMockMvc.perform(get("/api/workplaces?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(workplace.getId().intValue())))
            .andExpect(jsonPath("$.[*].resourceId").value(hasItem(DEFAULT_RESOURCE_ID.intValue())))
            .andExpect(jsonPath("$.[*].workplaceName").value(hasItem(DEFAULT_WORKPLACE_NAME.toString())));
    }

    @Test
    @Transactional
    public void getWorkplace() throws Exception {
        // Initialize the database
        workplaceRepository.saveAndFlush(workplace);

        // Get the workplace
        restWorkplaceMockMvc.perform(get("/api/workplaces/{id}", workplace.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(workplace.getId().intValue()))
            .andExpect(jsonPath("$.resourceId").value(DEFAULT_RESOURCE_ID.intValue()))
            .andExpect(jsonPath("$.workplaceName").value(DEFAULT_WORKPLACE_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingWorkplace() throws Exception {
        // Get the workplace
        restWorkplaceMockMvc.perform(get("/api/workplaces/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWorkplace() throws Exception {
        // Initialize the database
        workplaceRepository.saveAndFlush(workplace);
        int databaseSizeBeforeUpdate = workplaceRepository.findAll().size();

        // Update the workplace
        Workplace updatedWorkplace = workplaceRepository.findOne(workplace.getId());
        // Disconnect from session so that the updates on updatedWorkplace are not directly saved in db
        em.detach(updatedWorkplace);
        updatedWorkplace
            .resourceId(UPDATED_RESOURCE_ID)
            .workplaceName(UPDATED_WORKPLACE_NAME);
        WorkplaceDTO workplaceDTO = workplaceMapper.toDto(updatedWorkplace);

        restWorkplaceMockMvc.perform(put("/api/workplaces")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workplaceDTO)))
            .andExpect(status().isOk());

        // Validate the Workplace in the database
        List<Workplace> workplaceList = workplaceRepository.findAll();
        assertThat(workplaceList).hasSize(databaseSizeBeforeUpdate);
        Workplace testWorkplace = workplaceList.get(workplaceList.size() - 1);
        assertThat(testWorkplace.getResourceId()).isEqualTo(UPDATED_RESOURCE_ID);
        assertThat(testWorkplace.getWorkplaceName()).isEqualTo(UPDATED_WORKPLACE_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingWorkplace() throws Exception {
        int databaseSizeBeforeUpdate = workplaceRepository.findAll().size();

        // Create the Workplace
        WorkplaceDTO workplaceDTO = workplaceMapper.toDto(workplace);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restWorkplaceMockMvc.perform(put("/api/workplaces")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workplaceDTO)))
            .andExpect(status().isCreated());

        // Validate the Workplace in the database
        List<Workplace> workplaceList = workplaceRepository.findAll();
        assertThat(workplaceList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteWorkplace() throws Exception {
        // Initialize the database
        workplaceRepository.saveAndFlush(workplace);
        int databaseSizeBeforeDelete = workplaceRepository.findAll().size();

        // Get the workplace
        restWorkplaceMockMvc.perform(delete("/api/workplaces/{id}", workplace.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Workplace> workplaceList = workplaceRepository.findAll();
        assertThat(workplaceList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Workplace.class);
        Workplace workplace1 = new Workplace();
        workplace1.setId(1L);
        Workplace workplace2 = new Workplace();
        workplace2.setId(workplace1.getId());
        assertThat(workplace1).isEqualTo(workplace2);
        workplace2.setId(2L);
        assertThat(workplace1).isNotEqualTo(workplace2);
        workplace1.setId(null);
        assertThat(workplace1).isNotEqualTo(workplace2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(WorkplaceDTO.class);
        WorkplaceDTO workplaceDTO1 = new WorkplaceDTO();
        workplaceDTO1.setId(1L);
        WorkplaceDTO workplaceDTO2 = new WorkplaceDTO();
        assertThat(workplaceDTO1).isNotEqualTo(workplaceDTO2);
        workplaceDTO2.setId(workplaceDTO1.getId());
        assertThat(workplaceDTO1).isEqualTo(workplaceDTO2);
        workplaceDTO2.setId(2L);
        assertThat(workplaceDTO1).isNotEqualTo(workplaceDTO2);
        workplaceDTO1.setId(null);
        assertThat(workplaceDTO1).isNotEqualTo(workplaceDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(workplaceMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(workplaceMapper.fromId(null)).isNull();
    }
}
