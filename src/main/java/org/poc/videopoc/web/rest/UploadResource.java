package org.poc.videopoc.web.rest;

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
    private static String UPLOADED_FOLDER = "/Users/wpfeiffe/Work/job/videopoc/documents/upload/";

    private static final Logger log = LoggerFactory.getLogger(UploadResource.class);


    private final VideoDocumentService videoDocumentService;

    public UploadResource(VideoDocumentService videoDocumentService)
    {
        this.videoDocumentService = videoDocumentService;
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
                Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
                Files.write(path, bytes);
                VideoDocumentDTO videoDocumentDTO = new VideoDocumentDTO();

                videoDocumentDTO.setDocumentFilePath(VideoNormalizer.convertToMp4(path.toString()));
                videoDocumentDTO.setDocumentName(file.getOriginalFilename());
                videoDocumentDTO.setSourceFilePath(UPLOADED_FOLDER + file.getOriginalFilename());
                videoDocumentDTO.setVideoTypeTypeCode("mpeg4");
                VideoDocumentDTO result = videoDocumentService.save(videoDocumentDTO);
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



