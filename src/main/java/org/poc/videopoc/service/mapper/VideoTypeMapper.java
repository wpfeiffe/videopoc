package org.poc.videopoc.service.mapper;

import org.poc.videopoc.domain.*;
import org.poc.videopoc.service.dto.VideoTypeDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity VideoType and its DTO VideoTypeDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface VideoTypeMapper {

    VideoTypeDTO videoTypeToVideoTypeDTO(VideoType videoType);

    List<VideoTypeDTO> videoTypesToVideoTypeDTOs(List<VideoType> videoTypes);

    VideoType videoTypeDTOToVideoType(VideoTypeDTO videoTypeDTO);

    List<VideoType> videoTypeDTOsToVideoTypes(List<VideoTypeDTO> videoTypeDTOs);
    /**
     * generating the fromId for all mappers if the databaseType is sql, as the class has relationship to it might need it, instead of
     * creating a new attribute to know if the entity has any relationship from some other entity
     *
     * @param id id of the entity
     * @return the entity instance
     */
     
    default VideoType videoTypeFromId(Long id) {
        if (id == null) {
            return null;
        }
        VideoType videoType = new VideoType();
        videoType.setId(id);
        return videoType;
    }
    

}
