package org.poc.videopoc.repository;

import org.poc.videopoc.domain.VideoDocument;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the VideoDocument entity.
 */
@SuppressWarnings("unused")
public interface VideoDocumentRepository extends JpaRepository<VideoDocument,Long> {

}
