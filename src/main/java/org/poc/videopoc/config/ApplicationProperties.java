package org.poc.videopoc.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties specific to JHipster.
 *
 * <p>
 *     Properties are configured in the application.yml file.
 * </p>
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties
{
    private String ffmpegFile;
    private String ffprobeFile;
    private String uploadDir;
    private String conversionDir;
    private String samplesDir;

    public String getFfmpegFile()
    {
        return ffmpegFile;
    }

    public void setFfmpegFile(String ffmpegFile)
    {
        this.ffmpegFile = ffmpegFile;
    }

    public String getUploadDir()
    {
        return uploadDir;
    }

    public void setUploadDir(String uploadDir)
    {
        this.uploadDir = uploadDir;
    }

    public String getConversionDir()
    {
        return conversionDir;
    }

    public void setConversionDir(String conversionDir)
    {
        this.conversionDir = conversionDir;
    }

    public String getSamplesDir()
    {
        return samplesDir;
    }

    public void setSamplesDir(String samplesDir)
    {
        this.samplesDir = samplesDir;
    }

    public String getFfprobeFile()
    {
        return ffprobeFile;
    }

    public void setFfprobeFile(String ffprobeFile)
    {
        this.ffprobeFile = ffprobeFile;
    }
}
