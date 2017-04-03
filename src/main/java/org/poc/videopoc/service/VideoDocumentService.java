package org.poc.videopoc.service;

import org.poc.videopoc.service.dto.VideoDocumentDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing VideoDocument.
 */
public interface VideoDocumentService {

    /**
     * Save a videoDocument.
     *
     * @param videoDocumentDTO the entity to save
     * @return the persisted entity
     */
    VideoDocumentDTO save(VideoDocumentDTO videoDocumentDTO);

    /**
     *  Get all the videoDocuments.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<VideoDocumentDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" videoDocument.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    VideoDocumentDTO findOne(Long id);

    /**
     *  Delete the "id" videoDocument.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
