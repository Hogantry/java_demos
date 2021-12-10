package com.dfz.synchronize;

import java.util.LinkedList;

/**
 * @Author DFZ
 * @Date 2021-09-17 17:08
 * @Description
 */
public class Stack {

    LinkedList list = new LinkedList();

    public synchronized void push(Object x) {
        synchronized (list) {
            list.addLast(x);
            notify();
        }
    }

    public synchronized Object pop() throws InterruptedException {
        synchronized (list) {
            if (list.size() <= 0) {
                wait();
            }
            return list.removeLast();
        }
    }

}
