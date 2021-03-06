package com.dfz.netty.action.test;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.TooLongFrameException;
import org.junit.Assert;
import org.junit.Test;

/**
 * @Author DFZ
 * @Date 2021-10-15 19:08
 * @Description
 */
public class FrameChunkDecoderTest {

    @Test
    public void testFrameChunkDecoded() {
        ByteBuf buf = Unpooled.buffer();
        for (int i = 0; i < 9; i++) {
            buf.writeByte(i);
        }
        ByteBuf input = buf.duplicate();
        EmbeddedChannel channel = new EmbeddedChannel(
                new FrameChunkDecoder(3));

        Assert.assertTrue(channel.writeInbound(input.readBytes(2)));
        try {
            channel.writeInbound(input.readBytes(4));
            Assert.fail();
        } catch (TooLongFrameException e) {
            // expected exception
        }

        Assert.assertTrue(channel.writeInbound(input.readBytes(3)));

        Assert.assertTrue(channel.finish());
        // Read frames
        ByteBuf read = channel.readInbound();

        Assert.assertEquals(buf.readSlice(2), read);
        read.release();
        read = channel.readInbound();
        Assert.assertEquals(buf.skipBytes(4).readSlice(3), read);
        read.release();
        buf.release();
    }

}