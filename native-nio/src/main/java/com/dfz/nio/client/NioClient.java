package com.dfz.nio.client;

import com.dfz.nio.util.CodecUtil;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

/**
 * @version V1.0
 * @author: DFZ
 * @description: nio 客户端
 * @date: 2019-12-09 10:48
 * @Copyright: 2019 www.ztzqzg.com Ltd. All rights reserved.
 * 注意：本内容仅限于中泰证券（上海）资产管理有限公司内部传阅，禁止外泄以及用于其他的商业项目
 */
public class NioClient {
    private SocketChannel clientSocketChannel;
    private Selector selector;

    public NioClient() throws IOException {
        // 打开 Client Socket Channel
        clientSocketChannel = SocketChannel.open();
        // 配置为非阻塞
        clientSocketChannel.configureBlocking(false);
        // 创建 Selector
        selector = Selector.open();
        // 连接服务器
        clientSocketChannel.connect(new InetSocketAddress(8080));
        // 注册 Server Socket Channel 到 Selector
        clientSocketChannel.register(selector, SelectionKey.OP_CONNECT);

        new Thread(() -> {
            try {
                handleKeys();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        System.out.println("Client 启动完成");
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

    private synchronized void handleKey(SelectionKey key) throws IOException {
        // 接受连接就绪
        if (key.isConnectable()) {
            handleConnectableKey(key);
        }
        // 读就绪
        if (key.isReadable()) {
            handleReadableKey(key);
        }
    }

    private void handleConnectableKey(SelectionKey key) throws IOException {
        SocketChannel channel = (SocketChannel) key.channel();
        // 完成连接
        if (!channel.isConnectionPending()) {
            System.out.println("未完成链接");
            return;
        }
        channel.finishConnect();
        if (channel == clientSocketChannel) {
            System.out.println("same channel");
        }
        // 注册 Client Socket Channel 到 Selector
        channel.register(selector, SelectionKey.OP_READ);

    }

    @SuppressWarnings("Duplicates")
    private void handleReadableKey(SelectionKey key) {
        // Client Socket Channel
        SocketChannel clientSocketChannel = (SocketChannel) key.channel();
        // 读取数据
        ByteBuffer readBuffer = CodecUtil.read(clientSocketChannel);
        // 打印数据
        // 写入模式下
        if (readBuffer.position() > 0) {
            String content = CodecUtil.newString(readBuffer);
            System.out.println("读取数据：" + content);
        }
    }

    public synchronized void send(String content) throws IOException {
        // 打印数据
        System.out.println("写入数据：" + content);
        clientSocketChannel.write(ByteBuffer.wrap(content.getBytes(StandardCharsets.UTF_8)));
    }

    public static void main(String[] args) throws IOException {
        NioClient client = new NioClient();
        new Thread(() -> {
            for (int i = 0; i < 30; i++) {
                try {
                    client.send("nihao: " + i);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }
}
