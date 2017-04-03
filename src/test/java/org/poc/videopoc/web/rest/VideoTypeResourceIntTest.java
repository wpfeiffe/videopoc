package org.poc.videopoc.web.rest;

import org.poc.videopoc.VideopocApp;

import org.poc.videopoc.domain.VideoType;
import org.poc.videopoc.repository.VideoTypeRepository;
import org.poc.videopoc.service.VideoTypeService;
import org.poc.videopoc.service.dto.VideoTypeDTO;
import org.poc.videopoc.service.mapper.VideoTypeMapper;
import org.poc.videopoc.web.rest.errors.ExceptionTranslator;

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

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the VideoTypeResource REST controller.
 *
 * @see VideoTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = VideopocApp.class)
public class VideoTypeResourceIntTest {

    private static final String DEFAULT_MIME_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_MIME_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE_CODE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE_CODE = "BBBBBBBBBB";

    @Autowired
    private VideoTypeRepository videoTypeRepository;

    @Autowired
    private VideoTypeMapper videoTypeMapper;

    @Autowired
    private VideoTypeService videoTypeService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restVideoTypeMockMvc;

    private VideoType videoType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        VideoTypeResource videoTypeResource = new VideoTypeResource(videoTypeService);
        this.restVideoTypeMockMvc = MockMvcBuilders.standaloneSetup(videoTypeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VideoType createEntity(EntityManager em) {
        VideoType videoType = new VideoType()
            .mimeType(DEFAULT_MIME_TYPE)
            .typeCode(DEFAULT_TYPE_CODE);
        return videoType;
    }

    @Before
    public void initTest() {
        videoType = createEntity(em);
    }

    @Test
    @Transactional
    public void createVideoType() throws Exception {
        int databaseSizeBeforeCreate = videoTypeRepository.findAll().size();

        // Create the VideoType
        VideoTypeDTO videoTypeDTO = videoTypeMapper.videoTypeToVideoTypeDTO(videoType);
        restVideoTypeMockMvc.perform(post("/api/video-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(videoTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the VideoType in the database
        List<VideoType> videoTypeList = videoTypeRepository.findAll();
        assertThat(videoTypeList).hasSize(databaseSizeBeforeCreate + 1);
        VideoType testVideoType = videoTypeList.get(videoTypeList.size() - 1);
        assertThat(testVideoType.getMimeType()).isEqualTo(DEFAULT_MIME_TYPE);
        assertThat(testVideoType.getTypeCode()).isEqualTo(DEFAULT_TYPE_CODE);
    }

    @Test
    @Transactional
    public void createVideoTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = videoTypeRepository.findAll().size();

        // Create the VideoType with an existing ID
        videoType.setId(1L);
        VideoTypeDTO videoTypeDTO = videoTypeMapper.videoTypeToVideoTypeDTO(videoType);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVideoTypeMockMvc.perform(post("/api/video-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(videoTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<VideoType> videoTypeList = videoTypeRepository.findAll();
        assertThat(videoTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkMimeTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = videoTypeRepository.findAll().size();
        // set the field null
        videoType.setMimeType(null);

        // Create the VideoType, which fails.
        VideoTypeDTO videoTypeDTO = videoTypeMapper.videoTypeToVideoTypeDTO(videoType);

        restVideoTypeMockMvc.perform(post("/api/video-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(videoTypeDTO)))
            .andExpect(status().isBadRequest());

        List<VideoType> videoTypeList = videoTypeRepository.findAll();
        assertThat(videoTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTypeCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = videoTypeRepository.findAll().size();
        // set the field null
        videoType.setTypeCode(null);

        // Create the VideoType, which fails.
        VideoTypeDTO videoTypeDTO = videoTypeMapper.videoTypeToVideoTypeDTO(videoType);

        restVideoTypeMockMvc.perform(post("/api/video-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(videoTypeDTO)))
            .andExpect(status().isBadRequest());

        List<VideoType> videoTypeList = videoTypeRepository.findAll();
        assertThat(videoTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllVideoTypes() throws Exception {
        // Initialize the database
        videoTypeRepository.saveAndFlush(videoType);

        // Get all the videoTypeList
        restVideoTypeMockMvc.perform(get("/api/video-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(videoType.getId().intValue())))
            .andExpect(jsonPath("$.[*].mimeType").value(hasItem(DEFAULT_MIME_TYPE.toString())))
            .andExpect(jsonPath("$.[*].typeCode").value(hasItem(DEFAULT_TYPE_CODE.toString())));
    }

    @Test
    @Transactional
    public void getVideoType() throws Exception {
        // Initialize the database
        videoTypeRepository.saveAndFlush(videoType);

        // Get the videoType
        restVideoTypeMockMvc.perform(get("/api/video-types/{id}", videoType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(videoType.getId().intValue()))
            .andExpect(jsonPath("$.mimeType").value(DEFAULT_MIME_TYPE.toString()))
            .andExpect(jsonPath("$.typeCode").value(DEFAULT_TYPE_CODE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingVideoType() throws Exception {
        // Get the videoType
        restVideoTypeMockMvc.perform(get("/api/video-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVideoType() throws Exception {
        // Initialize the database
        videoTypeRepository.saveAndFlush(videoType);
        int databaseSizeBeforeUpdate = videoTypeRepository.findAll().size();

        // Update the videoType
        VideoType updatedVideoType = videoTypeRepository.findOne(videoType.getId());
        updatedVideoType
            .mimeType(UPDATED_MIME_TYPE)
            .typeCode(UPDATED_TYPE_CODE);
        VideoTypeDTO videoTypeDTO = videoTypeMapper.videoTypeToVideoTypeDTO(updatedVideoType);

        restVideoTypeMockMvc.perform(put("/api/video-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(videoTypeDTO)))
            .andExpect(status().isOk());

        // Validate the VideoType in the database
        List<VideoType> videoTypeList = videoTypeRepository.findAll();
        assertThat(videoTypeList).hasSize(databaseSizeBeforeUpdate);
        VideoType testVideoType = videoTypeList.get(videoTypeList.size() - 1);
        assertThat(testVideoType.getMimeType()).isEqualTo(UPDATED_MIME_TYPE);
        assertThat(testVideoType.getTypeCode()).isEqualTo(UPDATED_TYPE_CODE);
    }

    @Test
    @Transactional
    public void updateNonExistingVideoType() throws Exception {
        int databaseSizeBeforeUpdate = videoTypeRepository.findAll().size();

        // Create the VideoType
        VideoTypeDTO videoTypeDTO = videoTypeMapper.videoTypeToVideoTypeDTO(videoType);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restVideoTypeMockMvc.perform(put("/api/video-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(videoTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the VideoType in the database
        List<VideoType> videoTypeList = videoTypeRepository.findAll();
        assertThat(videoTypeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteVideoType() throws Exception {
        // Initialize the database
        videoTypeRepository.saveAndFlush(videoType);
        int databaseSizeBeforeDelete = videoTypeRepository.findAll().size();

        // Get the videoType
        restVideoTypeMockMvc.perform(delete("/api/video-types/{id}", videoType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<VideoType> videoTypeList = videoTypeRepository.findAll();
        assertThat(videoTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(VideoType.class);
    }
}
