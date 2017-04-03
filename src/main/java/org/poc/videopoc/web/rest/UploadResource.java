package org.poc.videopoc.web.rest;

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
    private static String UPLOADED_FOLDER = "/Users/wpfeiffe/Work/job/videopoc/upload/";

    private static final Logger log = LoggerFactory.getLogger(UploadResource.class);

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
            }
            catch (IOException e)
            {
                log.error("Error reading bytes from: " + file.getName(), e);
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity(HttpStatus.OK);

        }

    }

}



