package org.poc.videopoc.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the VideoDocument entity.
 */
public class VideoDocumentDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 8, max = 200)
    private String documentName;

    @Size(min = 10, max = 512)
    private String sourceFilePath;

    @Size(min = 10, max = 512)
    private String documentFilePath;

    private Long videoTypeId;

    private String videoTypeTypeCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }
    public String getSourceFilePath() {
        return sourceFilePath;
    }

    public void setSourceFilePath(String sourceFilePath) {
        this.sourceFilePath = sourceFilePath;
    }
    public String getDocumentFilePath() {
        return documentFilePath;
    }

    public void setDocumentFilePath(String documentFilePath) {
        this.documentFilePath = documentFilePath;
    }

    public Long getVideoTypeId() {
        return videoTypeId;
    }

    public void setVideoTypeId(Long videoTypeId) {
        this.videoTypeId = videoTypeId;
    }

    public String getVideoTypeTypeCode() {
        return videoTypeTypeCode;
    }

    public void setVideoTypeTypeCode(String videoTypeTypeCode) {
        this.videoTypeTypeCode = videoTypeTypeCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        VideoDocumentDTO videoDocumentDTO = (VideoDocumentDTO) o;

        if ( ! Objects.equals(id, videoDocumentDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "VideoDocumentDTO{" +
            "id=" + id +
            ", documentName='" + documentName + "'" +
            ", sourceFilePath='" + sourceFilePath + "'" +
            ", documentFilePath='" + documentFilePath + "'" +
            '}';
    }
}
