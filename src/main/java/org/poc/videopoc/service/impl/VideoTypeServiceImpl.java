package org.poc.videopoc.service.impl;

import org.poc.videopoc.service.VideoTypeService;
import org.poc.videopoc.domain.VideoType;
import org.poc.videopoc.repository.VideoTypeRepository;
import org.poc.videopoc.service.dto.VideoTypeDTO;
import org.poc.videopoc.service.mapper.VideoTypeMapper;
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
 * Service Implementation for managing VideoType.
 */
@Service
@Transactional
public class VideoTypeServiceImpl implements VideoTypeService{

    private final Logger log = LoggerFactory.getLogger(VideoTypeServiceImpl.class);
    
    private final VideoTypeRepository videoTypeRepository;

    private final VideoTypeMapper videoTypeMapper;

    public VideoTypeServiceImpl(VideoTypeRepository videoTypeRepository, VideoTypeMapper videoTypeMapper) {
        this.videoTypeRepository = videoTypeRepository;
        this.videoTypeMapper = videoTypeMapper;
    }

    /**
     * Save a videoType.
     *
     * @param videoTypeDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public VideoTypeDTO save(VideoTypeDTO videoTypeDTO) {
        log.debug("Request to save VideoType : {}", videoTypeDTO);
        VideoType videoType = videoTypeMapper.videoTypeDTOToVideoType(videoTypeDTO);
        videoType = videoTypeRepository.save(videoType);
        VideoTypeDTO result = videoTypeMapper.videoTypeToVideoTypeDTO(videoType);
        return result;
    }

    /**
     *  Get all the videoTypes.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<VideoTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all VideoTypes");
        Page<VideoType> result = videoTypeRepository.findAll(pageable);
        return result.map(videoType -> videoTypeMapper.videoTypeToVideoTypeDTO(videoType));
    }

    /**
     *  Get one videoType by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public VideoTypeDTO findOne(Long id) {
        log.debug("Request to get VideoType : {}", id);
        VideoType videoType = videoTypeRepository.findOne(id);
        VideoTypeDTO videoTypeDTO = videoTypeMapper.videoTypeToVideoTypeDTO(videoType);
        return videoTypeDTO;
    }

    /**
     *  Delete the  videoType by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete VideoType : {}", id);
        videoTypeRepository.delete(id);
    }
}
