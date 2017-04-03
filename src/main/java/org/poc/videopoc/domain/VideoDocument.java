package org.poc.videopoc.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A VideoDocument.
 */
@Entity
@Table(name = "video_document")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class VideoDocument implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(min = 8, max = 200)
    @Column(name = "document_name", length = 200, nullable = false)
    private String documentName;

    @Size(min = 10, max = 512)
    @Column(name = "source_file_path", length = 512)
    private String sourceFilePath;

    @Size(min = 10, max = 512)
    @Column(name = "document_file_path", length = 512)
    private String documentFilePath;

    @ManyToOne
    private VideoType videoType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDocumentName() {
        return documentName;
    }

    public VideoDocument documentName(String documentName) {
        this.documentName = documentName;
        return this;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public String getSourceFilePath() {
        return sourceFilePath;
    }

    public VideoDocument sourceFilePath(String sourceFilePath) {
        this.sourceFilePath = sourceFilePath;
        return this;
    }

    public void setSourceFilePath(String sourceFilePath) {
        this.sourceFilePath = sourceFilePath;
    }

    public String getDocumentFilePath() {
        return documentFilePath;
    }

    public VideoDocument documentFilePath(String documentFilePath) {
        this.documentFilePath = documentFilePath;
        return this;
    }

    public void setDocumentFilePath(String documentFilePath) {
        this.documentFilePath = documentFilePath;
    }

    public VideoType getVideoType() {
        return videoType;
    }

    public VideoDocument videoType(VideoType videoType) {
        this.videoType = videoType;
        return this;
    }

    public void setVideoType(VideoType videoType) {
        this.videoType = videoType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        VideoDocument videoDocument = (VideoDocument) o;
        if (videoDocument.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, videoDocument.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "VideoDocument{" +
            "id=" + id +
            ", documentName='" + documentName + "'" +
            ", sourceFilePath='" + sourceFilePath + "'" +
            ", documentFilePath='" + documentFilePath + "'" +
            '}';
    }
}
