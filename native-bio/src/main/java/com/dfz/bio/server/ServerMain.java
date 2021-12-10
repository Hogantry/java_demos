package com.dfz.bio.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @Author DFZ
 * @Date 2021-12-03 15:42
 * @Description
 */
public class ServerMain {

    public static void main(String[] args) throws IOException, InterruptedException {
        ServerSocket server = new ServerSocket(8888, 1);

        int acceptCount = 0;

        while(true)
        {
            Socket client = server.accept();

            acceptCount++;

            System.out.println("new connection has connected, num=" + acceptCount);

            Thread.sleep(2 * 1000);
        }
    }

}
