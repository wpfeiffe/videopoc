package org.poc.videopoc.service;

import org.poc.videopoc.service.dto.VideoTypeDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing VideoType.
 */
public interface VideoTypeService {

    /**
     * Save a videoType.
     *
     * @param videoTypeDTO the entity to save
     * @return the persisted entity
     */
    VideoTypeDTO save(VideoTypeDTO videoTypeDTO);

    /**
     *  Get all the videoTypes.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<VideoTypeDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" videoType.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    VideoTypeDTO findOne(Long id);

    /**
     *  Delete the "id" videoType.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
