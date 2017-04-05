package org.poc.videopoc.web.controller.document;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Optional;

import static org.springframework.http.HttpHeaders.IF_MODIFIED_SINCE;
import static org.springframework.http.HttpHeaders.IF_NONE_MATCH;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.HEAD;

@RestController
@RequestMapping("/document")
public class DocumentController
{

	private final FileStorage storage;

	@Autowired
	public DocumentController(FileStorage storage) {
		this.storage = storage;
	}

	@RequestMapping(method = {GET, HEAD}, value = "/{docId}")
	public ResponseEntity<Resource> redirect(
			HttpMethod method,
			@PathVariable Integer docId,
			@RequestHeader(IF_NONE_MATCH) Optional<String> requestEtagOpt,
			@RequestHeader(IF_MODIFIED_SINCE) Optional<Date> ifModifiedSinceOpt
			) {
		return findExistingFile(method, docId)
				.map(file -> file.redirect(requestEtagOpt, ifModifiedSinceOpt))
				.orElseGet(() -> new ResponseEntity<>(NOT_FOUND));
	}

	@RequestMapping(method = {GET, HEAD}, value = "/{docId}/{filename}")
	public ResponseEntity<Resource> download(
			HttpMethod method,
			@PathVariable Integer docId,
			@RequestHeader(IF_NONE_MATCH) Optional<String> requestEtagOpt,
			@RequestHeader(IF_MODIFIED_SINCE) Optional<Date> ifModifiedSinceOpt
			) {
		return findExistingFile(method, docId)
				.map(file -> file.handle(requestEtagOpt, ifModifiedSinceOpt))
				.orElseGet(() -> new ResponseEntity<>(NOT_FOUND));
	}

	private Optional<ExistingFile> findExistingFile(HttpMethod method, @PathVariable Integer docId) {
		return storage
				.findFile(docId)
				.map(pointer -> new ExistingFile(method, pointer, docId));
	}

}
