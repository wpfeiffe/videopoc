package org.poc.videopoc.web.rest;

import org.poc.videopoc.VideopocApp;

import org.poc.videopoc.domain.VideoDocument;
import org.poc.videopoc.repository.VideoDocumentRepository;
import org.poc.videopoc.service.VideoDocumentService;
import org.poc.videopoc.service.dto.VideoDocumentDTO;
import org.poc.videopoc.service.mapper.VideoDocumentMapper;
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
 * Test class for the VideoDocumentResource REST controller.
 *
 * @see VideoDocumentResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = VideopocApp.class)
public class VideoDocumentResourceIntTest {

    private static final String DEFAULT_DOCUMENT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_DOCUMENT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SOURCE_FILE_PATH = "AAAAAAAAAA";
    private static final String UPDATED_SOURCE_FILE_PATH = "BBBBBBBBBB";

    private static final String DEFAULT_DOCUMENT_FILE_PATH = "AAAAAAAAAA";
    private static final String UPDATED_DOCUMENT_FILE_PATH = "BBBBBBBBBB";

    @Autowired
    private VideoDocumentRepository videoDocumentRepository;

    @Autowired
    private VideoDocumentMapper videoDocumentMapper;

    @Autowired
    private VideoDocumentService videoDocumentService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restVideoDocumentMockMvc;

    private VideoDocument videoDocument;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        VideoDocumentResource videoDocumentResource = new VideoDocumentResource(videoDocumentService);
        this.restVideoDocumentMockMvc = MockMvcBuilders.standaloneSetup(videoDocumentResource)
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
    public static VideoDocument createEntity(EntityManager em) {
        VideoDocument videoDocument = new VideoDocument()
            .documentName(DEFAULT_DOCUMENT_NAME)
            .sourceFilePath(DEFAULT_SOURCE_FILE_PATH)
            .documentFilePath(DEFAULT_DOCUMENT_FILE_PATH);
        return videoDocument;
    }

    @Before
    public void initTest() {
        videoDocument = createEntity(em);
    }

    @Test
    @Transactional
    public void createVideoDocument() throws Exception {
        int databaseSizeBeforeCreate = videoDocumentRepository.findAll().size();

        // Create the VideoDocument
        VideoDocumentDTO videoDocumentDTO = videoDocumentMapper.videoDocumentToVideoDocumentDTO(videoDocument);
        restVideoDocumentMockMvc.perform(post("/api/video-documents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(videoDocumentDTO)))
            .andExpect(status().isCreated());

        // Validate the VideoDocument in the database
        List<VideoDocument> videoDocumentList = videoDocumentRepository.findAll();
        assertThat(videoDocumentList).hasSize(databaseSizeBeforeCreate + 1);
        VideoDocument testVideoDocument = videoDocumentList.get(videoDocumentList.size() - 1);
        assertThat(testVideoDocument.getDocumentName()).isEqualTo(DEFAULT_DOCUMENT_NAME);
        assertThat(testVideoDocument.getSourceFilePath()).isEqualTo(DEFAULT_SOURCE_FILE_PATH);
        assertThat(testVideoDocument.getDocumentFilePath()).isEqualTo(DEFAULT_DOCUMENT_FILE_PATH);
    }

    @Test
    @Transactional
    public void createVideoDocumentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = videoDocumentRepository.findAll().size();

        // Create the VideoDocument with an existing ID
        videoDocument.setId(1L);
        VideoDocumentDTO videoDocumentDTO = videoDocumentMapper.videoDocumentToVideoDocumentDTO(videoDocument);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVideoDocumentMockMvc.perform(post("/api/video-documents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(videoDocumentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<VideoDocument> videoDocumentList = videoDocumentRepository.findAll();
        assertThat(videoDocumentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDocumentNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = videoDocumentRepository.findAll().size();
        // set the field null
        videoDocument.setDocumentName(null);

        // Create the VideoDocument, which fails.
        VideoDocumentDTO videoDocumentDTO = videoDocumentMapper.videoDocumentToVideoDocumentDTO(videoDocument);

        restVideoDocumentMockMvc.perform(post("/api/video-documents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(videoDocumentDTO)))
            .andExpect(status().isBadRequest());

        List<VideoDocument> videoDocumentList = videoDocumentRepository.findAll();
        assertThat(videoDocumentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllVideoDocuments() throws Exception {
        // Initialize the database
        videoDocumentRepository.saveAndFlush(videoDocument);

        // Get all the videoDocumentList
        restVideoDocumentMockMvc.perform(get("/api/video-documents?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(videoDocument.getId().intValue())))
            .andExpect(jsonPath("$.[*].documentName").value(hasItem(DEFAULT_DOCUMENT_NAME.toString())))
            .andExpect(jsonPath("$.[*].sourceFilePath").value(hasItem(DEFAULT_SOURCE_FILE_PATH.toString())))
            .andExpect(jsonPath("$.[*].documentFilePath").value(hasItem(DEFAULT_DOCUMENT_FILE_PATH.toString())));
    }

    @Test
    @Transactional
    public void getVideoDocument() throws Exception {
        // Initialize the database
        videoDocumentRepository.saveAndFlush(videoDocument);

        // Get the videoDocument
        restVideoDocumentMockMvc.perform(get("/api/video-documents/{id}", videoDocument.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(videoDocument.getId().intValue()))
            .andExpect(jsonPath("$.documentName").value(DEFAULT_DOCUMENT_NAME.toString()))
            .andExpect(jsonPath("$.sourceFilePath").value(DEFAULT_SOURCE_FILE_PATH.toString()))
            .andExpect(jsonPath("$.documentFilePath").value(DEFAULT_DOCUMENT_FILE_PATH.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingVideoDocument() throws Exception {
        // Get the videoDocument
        restVideoDocumentMockMvc.perform(get("/api/video-documents/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVideoDocument() throws Exception {
        // Initialize the database
        videoDocumentRepository.saveAndFlush(videoDocument);
        int databaseSizeBeforeUpdate = videoDocumentRepository.findAll().size();

        // Update the videoDocument
        VideoDocument updatedVideoDocument = videoDocumentRepository.findOne(videoDocument.getId());
        updatedVideoDocument
            .documentName(UPDATED_DOCUMENT_NAME)
            .sourceFilePath(UPDATED_SOURCE_FILE_PATH)
            .documentFilePath(UPDATED_DOCUMENT_FILE_PATH);
        VideoDocumentDTO videoDocumentDTO = videoDocumentMapper.videoDocumentToVideoDocumentDTO(updatedVideoDocument);

        restVideoDocumentMockMvc.perform(put("/api/video-documents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(videoDocumentDTO)))
            .andExpect(status().isOk());

        // Validate the VideoDocument in the database
        List<VideoDocument> videoDocumentList = videoDocumentRepository.findAll();
        assertThat(videoDocumentList).hasSize(databaseSizeBeforeUpdate);
        VideoDocument testVideoDocument = videoDocumentList.get(videoDocumentList.size() - 1);
        assertThat(testVideoDocument.getDocumentName()).isEqualTo(UPDATED_DOCUMENT_NAME);
        assertThat(testVideoDocument.getSourceFilePath()).isEqualTo(UPDATED_SOURCE_FILE_PATH);
        assertThat(testVideoDocument.getDocumentFilePath()).isEqualTo(UPDATED_DOCUMENT_FILE_PATH);
    }

    @Test
    @Transactional
    public void updateNonExistingVideoDocument() throws Exception {
        int databaseSizeBeforeUpdate = videoDocumentRepository.findAll().size();

        // Create the VideoDocument
        VideoDocumentDTO videoDocumentDTO = videoDocumentMapper.videoDocumentToVideoDocumentDTO(videoDocument);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restVideoDocumentMockMvc.perform(put("/api/video-documents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(videoDocumentDTO)))
            .andExpect(status().isCreated());

        // Validate the VideoDocument in the database
        List<VideoDocument> videoDocumentList = videoDocumentRepository.findAll();
        assertThat(videoDocumentList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteVideoDocument() throws Exception {
        // Initialize the database
        videoDocumentRepository.saveAndFlush(videoDocument);
        int databaseSizeBeforeDelete = videoDocumentRepository.findAll().size();

        // Get the videoDocument
        restVideoDocumentMockMvc.perform(delete("/api/video-documents/{id}", videoDocument.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<VideoDocument> videoDocumentList = videoDocumentRepository.findAll();
        assertThat(videoDocumentList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(VideoDocument.class);
    }
}
