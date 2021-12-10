package com.dfz.nio.server;

import com.dfz.nio.util.CodecUtil;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

/**
 * @version V1.0
 * @author: DFZ
 * @description: nio 服务端
 * @date: 2019-12-09 10:28
 * @Copyright: 2019 www.ztzqzg.com Ltd. All rights reserved.
 * 注意：本内容仅限于中泰证券（上海）资产管理有限公司内部传阅，禁止外泄以及用于其他的商业项目
 */
public class NioServer {
    private ServerSocketChannel serverSocketChannel;
    private Selector selector;

    public NioServer() throws IOException {
        // 打开 Server Socket Channel
        serverSocketChannel = ServerSocketChannel.open();
        // 配置为非阻塞
        serverSocketChannel.configureBlocking(false);
        // 绑定 Server port
        serverSocketChannel.bind(new InetSocketAddress(8080));
        // 创建 Selector
        selector = Selector.open();
        // 注册 Server Socket Channel 到 Selector
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println("Server 启动完成");

        handleKeys();
    }

    @SuppressWarnings("Duplicates")
    private void handleKeys() throws IOException {
        while (true) {
            // 通过 Selector 选择 Channel
            int selectNums = selector.select(30 * 1000L);
            if (selectNums == 0) {
                continue;
            }

            // 遍历可选择的 Channel 的 SelectionKey 集合
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                // 移除下面要处理的 SelectionKey
                iterator.remove();
                // 忽略无效的 SelectionKey
                if (!key.isValid()) {
                    continue;
                }

                handleKey(key);
            }
        }
    }

    private void handleKey(SelectionKey key) throws IOException {
        // 接受连接就绪
        if (key.isAcceptable()) {
            handleAcceptableKey(key);
        }
        // 读就绪
        if (key.isReadable()) {
            handleReadableKey(key);
        }
    }

    private void handleAcceptableKey(SelectionKey key) throws IOException {
        // 接受 Client Socket Channel
        SocketChannel clientSocketChannel = ((ServerSocketChannel) key.channel()).accept();
        // 配置为非阻塞
        clientSocketChannel.configureBlocking(false);
        // log
        System.out.println("接受新的 Channel");
        clientSocketChannel.write(ByteBuffer.wrap("欢迎链接到server".getBytes(StandardCharsets.UTF_8)));
        // 注册 Client Socket Channel 到 Selector
        clientSocketChannel.register(selector, SelectionKey.OP_READ);

    }

    private void handleReadableKey(SelectionKey key) throws IOException {
        // Client Socket Channel
        SocketChannel clientSocketChannel = (SocketChannel) key.channel();
        // 读取数据
        ByteBuffer readBuffer = CodecUtil.read(clientSocketChannel);
        // 处理连接已经断开的情况
        if (readBuffer == null) {
            key.cancel();
            System.out.println("断开 Channel");
            return;
        }
        // 打印数据
        if (readBuffer.position() > 0) {
            String content = CodecUtil.newString(readBuffer);
            System.out.println("读取数据：" + content);

            clientSocketChannel.write(ByteBuffer.wrap(content.getBytes(StandardCharsets.UTF_8)));
        }
    }

    public static void main(String[] args) throws IOException {
        NioServer server = new NioServer();
    }
}
