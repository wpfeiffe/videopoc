package org.poc.videopoc.service.mapper;

import org.poc.videopoc.domain.*;
import org.poc.videopoc.service.dto.VideoDocumentDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity VideoDocument and its DTO VideoDocumentDTO.
 */
@Mapper(componentModel = "spring", uses = {VideoTypeMapper.class, })
public interface VideoDocumentMapper {

    @Mapping(source = "videoType.id", target = "videoTypeId")
    @Mapping(source = "videoType.typeCode", target = "videoTypeTypeCode")
    VideoDocumentDTO videoDocumentToVideoDocumentDTO(VideoDocument videoDocument);

    List<VideoDocumentDTO> videoDocumentsToVideoDocumentDTOs(List<VideoDocument> videoDocuments);

    @Mapping(source = "videoTypeId", target = "videoType")
    VideoDocument videoDocumentDTOToVideoDocument(VideoDocumentDTO videoDocumentDTO);

    List<VideoDocument> videoDocumentDTOsToVideoDocuments(List<VideoDocumentDTO> videoDocumentDTOs);
    /**
     * generating the fromId for all mappers if the databaseType is sql, as the class has relationship to it might need it, instead of
     * creating a new attribute to know if the entity has any relationship from some other entity
     *
     * @param id id of the entity
     * @return the entity instance
     */
     
    default VideoDocument videoDocumentFromId(Long id) {
        if (id == null) {
            return null;
        }
        VideoDocument videoDocument = new VideoDocument();
        videoDocument.setId(id);
        return videoDocument;
    }
    

}
