package org.poc.videopoc.service.video;

import io.humble.video.*;
import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import org.apache.commons.io.FilenameUtils;
import org.apache.tika.config.TikaConfig;
import org.apache.tika.detect.Detector;
import org.apache.tika.io.TikaInputStream;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;
import org.poc.videopoc.config.ApplicationProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

/**
 * Service to normalize video: verify type and convert to standard mp4
 */
@Service
public class VideoNormalizer
{
    private final Logger log = LoggerFactory.getLogger(VideoNormalizer.class);

    private final ApplicationProperties applicationProperties;

    public VideoNormalizer(ApplicationProperties applicationProperties)
    {
        this.applicationProperties = applicationProperties;
    }

    public void convertFile(String inputFile, String outputFile) throws Exception
    {
        String inputMimeType = this.getFileType(inputFile);

        final Demuxer demuxer = Demuxer.make();
        final Muxer muxer = Muxer.make(outputFile, null, "mp4");

        demuxer.open(inputFile,null, false, true, null, null);

        final MuxerFormat format = MuxerFormat.guessFormat(null, inputFile, null);

        int n = demuxer.getNumStreams();
        final Decoder[] decoders = new Decoder[n];
        for(int i = 0; i < n; i++)
        {
            final DemuxerStream ds = demuxer.getStream(i);
            decoders[i] = ds.getDecoder();
            final Decoder d = decoders[i];

            if (d != null) {
                // neat; we can decode. Now let's see if this decoder can fit into the mp4 format.
                Collection codecs = format.getSupportedCodecs();
                if (!format.getSupportedCodecs().contains(d.getCodecID())) {
                    throw new RuntimeException("Input filename (" + inputFile + ") contains at least one stream with a codec not supported in the output format: " + d.toString());
                }
                if (format.getFlag(MuxerFormat.Flag.GLOBAL_HEADER))
                    d.setFlag(Coder.Flag.FLAG_GLOBAL_HEADER, true);
                d.open(null, null);
                muxer.addNewStream(d);
            }
        }
        muxer.open(null, null);
        final MediaPacket packet = MediaPacket.make();
        while(demuxer.read(packet) >= 0) {
            /**
             * Now we have a packet, but we can only write packets that had decoders we knew what to do with.
             */
            final Decoder d = decoders[packet.getStreamIndex()];
            if (packet.isComplete() && d != null) {
                // check to see if we are using bit stream filters, and if so, filter the audio
                // or video.
                muxer.write(packet, true);
            }
        }

        // It is good practice to close demuxers when you're done to free
        // up file handles. Humble will EVENTUALLY detect if nothing else
        // references this demuxer and close it then, but get in the habit
        // of cleaning up after yourself, and your future girlfriend/boyfriend
        // will appreciate it.
        muxer.close();
        demuxer.close();
    }

    public String getFileType(String origalFilePath) throws IOException
    {
        File file = new File(origalFilePath);
        TikaConfig config = TikaConfig.getDefaultConfig();
        Detector detector = config.getDetector();

        TikaInputStream stream = TikaInputStream.get(file);

        Metadata metadata = new Metadata();
        metadata.add(Metadata.RESOURCE_NAME_KEY, file.getName());
        MediaType mediaType = detector.detect(stream, metadata);
        return mediaType.toString();
    }

    public String convertToMp4(String inputfile) throws Exception
    {
        String convertedFileName = "ERROR";


        // get the file type
        String mimeType = this.getFileType(inputfile);

        // if already mp4
        if (mimeType.equals("video/mp4"))
        {
            return inputfile;
        }

        // build file name to convert to
        File infile = new File(inputfile);
        convertedFileName = this.applicationProperties.getConversionDir() + FilenameUtils.getBaseName(inputfile) + ".mp4";

        // convert the file
        convertFile2(inputfile, convertedFileName);

        return convertedFileName;
    }

    public void convertFile2(String inputFile, String outputFile) throws Exception
    {
        FFmpeg fFmpeg = new FFmpeg(applicationProperties.getFfmpegFile());
        FFprobe fFprobe = new FFprobe(applicationProperties.getFfmpegFile());

        FFmpegBuilder builder = new FFmpegBuilder()
                .setInput(inputFile)
                .overrideOutputFiles(true)
                .addOutput(outputFile)
                .setFormat("mp4")
                .done();

        FFmpegExecutor executor = new FFmpegExecutor(fFmpeg, fFprobe);
        executor.createJob(builder).run();
        
    }

}
