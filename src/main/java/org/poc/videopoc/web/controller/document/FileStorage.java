package org.poc.videopoc.web.controller.document;

import java.util.Optional;

public interface FileStorage {
	Optional<FilePointer> findFile(Integer docId);
}
