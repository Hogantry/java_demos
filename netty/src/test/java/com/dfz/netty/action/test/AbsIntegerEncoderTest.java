package com.dfz.netty.action.test;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.Assert;
import org.junit.Test;

/**
 * @Author DFZ
 * @Date 2021-10-15 19:07
 * @Description
 */
public class AbsIntegerEncoderTest {

    @Test
    public void testEncoded() {
        ByteBuf buf = Unpooled.buffer();
        for (int i = 1; i < 10; i++) {
            buf.writeInt(i * -1);
        }

        EmbeddedChannel channel = new EmbeddedChannel(
                new AbsIntegerEncoder());
        Assert.assertTrue(channel.writeOutbound(buf));
        Assert.assertTrue(channel.finish());

        // read bytes
        for (int i = 1; i < 10; i++) {
            Assert.assertEquals(Integer.valueOf(i), channel.readOutbound());
        }
        Assert.assertNull(channel.readOutbound());
    }

}