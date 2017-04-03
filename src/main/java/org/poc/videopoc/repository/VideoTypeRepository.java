package org.poc.videopoc.repository;

import org.poc.videopoc.domain.VideoType;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the VideoType entity.
 */
@SuppressWarnings("unused")
public interface VideoTypeRepository extends JpaRepository<VideoType,Long> {

}
