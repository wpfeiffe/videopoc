package org.poc.videopoc.service.video;

import org.junit.Test;

import java.io.File;
import java.io.IOException;

/**
 * Test video normalizer class
 */
public class VideoNormalizerUnitTest
{
    @Test
    public void testFileTypes()
    {
        String basePath = "/Users/wpfeiffe/Work/job/videopoc/samples/";
        String[] inputFiles = {
                "sampleavi.avi",
                "samplemov.mov",
                "samplempg.mpg",
                "sampleswf.swf",
                "samplewmv.wmv",
                "big_buck_bunny.mp4",
        };

        try
        {
            for (String filename : inputFiles)
            {
                String value = VideoNormalizer.getFileType(basePath + filename);
                System.out.println(String.format("File type for %s is %s", filename, value));
                assert (value != null);
            }
        }
        catch (IOException e)
        {
            assert (false);
        }
    }
    
    @Test
    public void testConversion()
    {
        String outputFile = null;
        String basePath = "/Users/wpfeiffe/Work/job/videopoc/samples/";
        String[] inputFiles = {
                "sampleavi.avi",
                "samplemov.mov",
                "samplempg.mpg",
                "sampleswf.swf",
                "samplewmv.wmv",
                "big_buck_bunny.mp4",
        };

        try
        {
            for (String filename : inputFiles)
            {
                outputFile = VideoNormalizer.convertToMp4(basePath + filename);
                assert (outputFile != null && !outputFile.equals("ERROR") && new File(outputFile).exists());
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            assert(false);
        }

    }
}
