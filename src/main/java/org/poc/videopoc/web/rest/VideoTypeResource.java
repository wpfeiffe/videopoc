package org.poc.videopoc.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.poc.videopoc.service.VideoTypeService;
import org.poc.videopoc.web.rest.util.HeaderUtil;
import org.poc.videopoc.web.rest.util.PaginationUtil;
import org.poc.videopoc.service.dto.VideoTypeDTO;
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
 * REST controller for managing VideoType.
 */
@RestController
@RequestMapping("/api")
public class VideoTypeResource {

    private final Logger log = LoggerFactory.getLogger(VideoTypeResource.class);

    private static final String ENTITY_NAME = "videoType";
        
    private final VideoTypeService videoTypeService;

    public VideoTypeResource(VideoTypeService videoTypeService) {
        this.videoTypeService = videoTypeService;
    }

    /**
     * POST  /video-types : Create a new videoType.
     *
     * @param videoTypeDTO the videoTypeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new videoTypeDTO, or with status 400 (Bad Request) if the videoType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/video-types")
    @Timed
    public ResponseEntity<VideoTypeDTO> createVideoType(@Valid @RequestBody VideoTypeDTO videoTypeDTO) throws URISyntaxException {
        log.debug("REST request to save VideoType : {}", videoTypeDTO);
        if (videoTypeDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new videoType cannot already have an ID")).body(null);
        }
        VideoTypeDTO result = videoTypeService.save(videoTypeDTO);
        return ResponseEntity.created(new URI("/api/video-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /video-types : Updates an existing videoType.
     *
     * @param videoTypeDTO the videoTypeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated videoTypeDTO,
     * or with status 400 (Bad Request) if the videoTypeDTO is not valid,
     * or with status 500 (Internal Server Error) if the videoTypeDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/video-types")
    @Timed
    public ResponseEntity<VideoTypeDTO> updateVideoType(@Valid @RequestBody VideoTypeDTO videoTypeDTO) throws URISyntaxException {
        log.debug("REST request to update VideoType : {}", videoTypeDTO);
        if (videoTypeDTO.getId() == null) {
            return createVideoType(videoTypeDTO);
        }
        VideoTypeDTO result = videoTypeService.save(videoTypeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, videoTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /video-types : get all the videoTypes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of videoTypes in body
     */
    @GetMapping("/video-types")
    @Timed
    public ResponseEntity<List<VideoTypeDTO>> getAllVideoTypes(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of VideoTypes");
        Page<VideoTypeDTO> page = videoTypeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/video-types");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /video-types/:id : get the "id" videoType.
     *
     * @param id the id of the videoTypeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the videoTypeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/video-types/{id}")
    @Timed
    public ResponseEntity<VideoTypeDTO> getVideoType(@PathVariable Long id) {
        log.debug("REST request to get VideoType : {}", id);
        VideoTypeDTO videoTypeDTO = videoTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(videoTypeDTO));
    }

    /**
     * DELETE  /video-types/:id : delete the "id" videoType.
     *
     * @param id the id of the videoTypeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/video-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteVideoType(@PathVariable Long id) {
        log.debug("REST request to delete VideoType : {}", id);
        videoTypeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
