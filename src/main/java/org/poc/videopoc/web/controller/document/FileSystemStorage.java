package org.poc.videopoc.web.controller.document;

import org.hibernate.service.spi.InjectService;
import org.poc.videopoc.domain.VideoDocument;
import org.poc.videopoc.service.VideoDocumentService;
import org.poc.videopoc.service.dto.VideoDocumentDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.io.File;
import java.net.URL;
import java.util.Optional;
import java.util.UUID;

/**
 * Stub implementation that for any UUID returns `logback.xml` file.
 * Replace with real implementation.
 */
@Component
public class FileSystemStorage implements FileStorage {

	private static final Logger log = LoggerFactory.getLogger(FileSystemStorage.class);

    private final VideoDocumentService videoDocumentService;

    public FileSystemStorage(VideoDocumentService videoDocumentService)
    {
        this.videoDocumentService = videoDocumentService;
    }

    @Override
	public Optional<FilePointer> findFile(Integer docId) {
		log.debug("Downloading {}", docId);

        VideoDocumentDTO videoDocumentDTO = this.videoDocumentService.findOne(docId.longValue());
		final File file = new File(videoDocumentDTO.getDocumentFilePath());
		final FileSystemPointer pointer = new FileSystemPointer(file);
		return Optional.of(pointer);
	}

}

