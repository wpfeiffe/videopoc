package org.poc.videopoc.service.impl;

import org.poc.videopoc.service.VideoDocumentService;
import org.poc.videopoc.domain.VideoDocument;
import org.poc.videopoc.repository.VideoDocumentRepository;
import org.poc.videopoc.service.dto.VideoDocumentDTO;
import org.poc.videopoc.service.mapper.VideoDocumentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing VideoDocument.
 */
@Service
@Transactional
public class VideoDocumentServiceImpl implements VideoDocumentService{

    private final Logger log = LoggerFactory.getLogger(VideoDocumentServiceImpl.class);
    
    private final VideoDocumentRepository videoDocumentRepository;

    private final VideoDocumentMapper videoDocumentMapper;

    public VideoDocumentServiceImpl(VideoDocumentRepository videoDocumentRepository, VideoDocumentMapper videoDocumentMapper) {
        this.videoDocumentRepository = videoDocumentRepository;
        this.videoDocumentMapper = videoDocumentMapper;
    }

    /**
     * Save a videoDocument.
     *
     * @param videoDocumentDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public VideoDocumentDTO save(VideoDocumentDTO videoDocumentDTO) {
        log.debug("Request to save VideoDocument : {}", videoDocumentDTO);
        VideoDocument videoDocument = videoDocumentMapper.videoDocumentDTOToVideoDocument(videoDocumentDTO);
        videoDocument = videoDocumentRepository.save(videoDocument);
        VideoDocumentDTO result = videoDocumentMapper.videoDocumentToVideoDocumentDTO(videoDocument);
        return result;
    }

    /**
     *  Get all the videoDocuments.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<VideoDocumentDTO> findAll(Pageable pageable) {
        log.debug("Request to get all VideoDocuments");
        Page<VideoDocument> result = videoDocumentRepository.findAll(pageable);
        return result.map(videoDocument -> videoDocumentMapper.videoDocumentToVideoDocumentDTO(videoDocument));
    }

    /**
     *  Get one videoDocument by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public VideoDocumentDTO findOne(Long id) {
        log.debug("Request to get VideoDocument : {}", id);
        VideoDocument videoDocument = videoDocumentRepository.findOne(id);
        VideoDocumentDTO videoDocumentDTO = videoDocumentMapper.videoDocumentToVideoDocumentDTO(videoDocument);
        return videoDocumentDTO;
    }

    /**
     *  Delete the  videoDocument by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete VideoDocument : {}", id);
        videoDocumentRepository.delete(id);
    }
}
