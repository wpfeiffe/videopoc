package org.poc.videopoc.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the VideoType entity.
 */
public class VideoTypeDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 8, max = 100)
    private String mimeType;

    @NotNull
    @Size(min = 5, max = 30)
    private String typeCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }
    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        VideoTypeDTO videoTypeDTO = (VideoTypeDTO) o;

        if ( ! Objects.equals(id, videoTypeDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "VideoTypeDTO{" +
            "id=" + id +
            ", mimeType='" + mimeType + "'" +
            ", typeCode='" + typeCode + "'" +
            '}';
    }
}
