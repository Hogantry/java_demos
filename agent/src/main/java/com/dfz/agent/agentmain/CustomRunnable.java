package com.dfz.agent.agentmain;

/**
 * @Author DFZ
 * @Date 2021-08-19 20:15
 * @Description
 */
public class CustomRunnable implements Runnable {

    Runnable delegate;

    public CustomRunnable(Runnable delegate) {
        this.delegate = delegate;
    }

    @Override
    public void run() {
        System.out.println("welcome to my custom runnable");
        delegate.run();
    }

}
