package org.poc.videopoc.service.video;

import io.humble.video.*;
import org.junit.Test;

/**
 * Unit test conversion
 */
public class HumbleConverterUnitTest
{
    @Test
    public void doConversion()
    {
        boolean success = true;

        String inputFile = "/Users/wpfeiffe/Work/job/videopoc/samples/sampleavi.avi";
        String outputFile = "/Users/wpfeiffe/Work/job/videopoc/samples/converted/sampleavi.mp4";

        try
        {
            final Demuxer demuxer = Demuxer.make();
            final Muxer muxer = Muxer.make(outputFile, null, "mp4");

            demuxer.open(inputFile,null, false, true, null, null);

            final MuxerFormat format = MuxerFormat.guessFormat("avi", null, null);

            int n = demuxer.getNumStreams();
            final Decoder[] decoders = new Decoder[n];
            for(int i = 0; i < n; i++)
            {
                final DemuxerStream ds = demuxer.getStream(i);
                decoders[i] = ds.getDecoder();
                final Decoder d = decoders[i];

                if (d != null) {
                    // neat; we can decode. Now let's see if this decoder can fit into the mp4 format.
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
            assert (success);

        }
        catch (Exception e)
        {
            assert (false);
        }
    }
}
