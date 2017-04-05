package org.poc.videopoc.web.rest;

import org.poc.videopoc.config.ApplicationProperties;
import org.poc.videopoc.service.VideoDocumentService;
import org.poc.videopoc.service.dto.VideoDocumentDTO;
import org.poc.videopoc.service.video.VideoNormalizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Controller for upload
 */
@RestController
@RequestMapping(value="/upload")
public class UploadResource
{
    private static final Logger log = LoggerFactory.getLogger(UploadResource.class);

    private final VideoDocumentService videoDocumentService;
    private final ApplicationProperties applicationProperties;
    private final VideoNormalizer videoNormalizer;

    public UploadResource(VideoDocumentService videoDocumentService, ApplicationProperties applicationProperties, VideoNormalizer videoNormalizer)
    {
        this.videoDocumentService = videoDocumentService;
        this.applicationProperties = applicationProperties;
        this.videoNormalizer = videoNormalizer;
    }

    @PostMapping(value="/singlefile")
    public ResponseEntity singleFile(@RequestParam(value="file", required=true) MultipartFile file)
    {
        if (file.isEmpty())
        {
            log.warn("File not found:" + file.getName());
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        else
        {
            try
            {
                byte[] bytes = file.getBytes();
                Path path = Paths.get(this.applicationProperties.getUploadDir() + file.getOriginalFilename());
                Files.write(path, bytes);
                VideoDocumentDTO videoDocumentDTO = new VideoDocumentDTO();

                videoDocumentDTO.setDocumentFilePath(videoNormalizer.convertToMp4(path.toString()));
                videoDocumentDTO.setDocumentName(file.getOriginalFilename());
                videoDocumentDTO.setSourceFilePath(this.applicationProperties.getUploadDir() + file.getOriginalFilename());
                videoDocumentDTO.setVideoTypeTypeCode("mpeg4");
                VideoDocumentDTO result = this.videoDocumentService.save(videoDocumentDTO);
            }
            catch (Exception e)
            {
                log.error("Error reading bytes from: " + file.getName(), e);
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity(HttpStatus.OK);
        }
    }
}



