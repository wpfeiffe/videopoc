package org.poc.videopoc.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.poc.videopoc.service.VideoDocumentService;
import org.poc.videopoc.web.rest.util.HeaderUtil;
import org.poc.videopoc.web.rest.util.PaginationUtil;
import org.poc.videopoc.service.dto.VideoDocumentDTO;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing VideoDocument.
 */
@RestController
@RequestMapping("/api")
public class VideoDocumentResource {

    private final Logger log = LoggerFactory.getLogger(VideoDocumentResource.class);

    private static final String ENTITY_NAME = "videoDocument";
        
    private final VideoDocumentService videoDocumentService;

    public VideoDocumentResource(VideoDocumentService videoDocumentService) {
        this.videoDocumentService = videoDocumentService;
    }

    /**
     * POST  /video-documents : Create a new videoDocument.
     *
     * @param videoDocumentDTO the videoDocumentDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new videoDocumentDTO, or with status 400 (Bad Request) if the videoDocument has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/video-documents")
    @Timed
    public ResponseEntity<VideoDocumentDTO> createVideoDocument(@Valid @RequestBody VideoDocumentDTO videoDocumentDTO) throws URISyntaxException {
        log.debug("REST request to save VideoDocument : {}", videoDocumentDTO);
        if (videoDocumentDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new videoDocument cannot already have an ID")).body(null);
        }
        VideoDocumentDTO result = videoDocumentService.save(videoDocumentDTO);
        return ResponseEntity.created(new URI("/api/video-documents/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /video-documents : Updates an existing videoDocument.
     *
     * @param videoDocumentDTO the videoDocumentDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated videoDocumentDTO,
     * or with status 400 (Bad Request) if the videoDocumentDTO is not valid,
     * or with status 500 (Internal Server Error) if the videoDocumentDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/video-documents")
    @Timed
    public ResponseEntity<VideoDocumentDTO> updateVideoDocument(@Valid @RequestBody VideoDocumentDTO videoDocumentDTO) throws URISyntaxException {
        log.debug("REST request to update VideoDocument : {}", videoDocumentDTO);
        if (videoDocumentDTO.getId() == null) {
            return createVideoDocument(videoDocumentDTO);
        }
        VideoDocumentDTO result = videoDocumentService.save(videoDocumentDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, videoDocumentDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /video-documents : get all the videoDocuments.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of videoDocuments in body
     */
    @GetMapping("/video-documents")
    @Timed
    public ResponseEntity<List<VideoDocumentDTO>> getAllVideoDocuments(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of VideoDocuments");
        Page<VideoDocumentDTO> page = videoDocumentService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/video-documents");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /video-documents/:id : get the "id" videoDocument.
     *
     * @param id the id of the videoDocumentDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the videoDocumentDTO, or with status 404 (Not Found)
     */
    @GetMapping("/video-documents/{id}")
    @Timed
    public ResponseEntity<VideoDocumentDTO> getVideoDocument(@PathVariable Long id) {
        log.debug("REST request to get VideoDocument : {}", id);
        VideoDocumentDTO videoDocumentDTO = videoDocumentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(videoDocumentDTO));
    }

    /**
     * DELETE  /video-documents/:id : delete the "id" videoDocument.
     *
     * @param id the id of the videoDocumentDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/video-documents/{id}")
    @Timed
    public ResponseEntity<Void> deleteVideoDocument(@PathVariable Long id) {
        log.debug("REST request to delete VideoDocument : {}", id);
        videoDocumentService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
