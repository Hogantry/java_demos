package com.dfz.bio.client;

import java.io.IOException;
import java.net.Socket;

/**
 * @Author DFZ
 * @Date 2021-12-03 15:43
 * @Description
 */
public class ClientMain {

    private static Socket[] clients = new Socket[30];

    public static void main(String[] args) throws IOException {
        for (int i = 1; i <= 30; i++) {
            clients[i-1] = new Socket("127.0.0.1", 8888);
            System.out.println("client connection:" + i);
        }
    }

}
