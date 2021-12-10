package com.dfz.synchronize;

import java.util.concurrent.TimeUnit;

/**
 * @Author DFZ
 * @Date 2021-09-17 17:07
 * @Description
 */
public class DubboSyn {

    public static void main(String[] args) throws InterruptedException {

        Stack stack = new Stack();

        new Thread(()-> {
            try {
                stack.pop();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        TimeUnit.SECONDS.sleep(1);
        stack.push(1);
    }

}
