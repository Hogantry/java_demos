package com.dfz.netty.action.test;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

/**
 * @Author DFZ
 * @Date 2021-10-13 10:39
 * @Description
 *
 * 如何使用单元测试
 *
 * 三个使用EmbeddedChannel类的测试类，需要弄清楚每行代码的含义
 *
 */
public class AbsIntegerEncoder extends MessageToMessageEncoder<ByteBuf> {
    @Override
    protected void encode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        while (in.readableBytes() >= 4) {
            int value = Math.abs(in.readInt());
            out.add(value);
        }
    }
}
