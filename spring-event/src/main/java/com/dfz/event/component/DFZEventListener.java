package com.dfz.event.component;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @Author DFZ
 * @Date 2021-07-01 17:27
 * @Description
 */
//@Component
public class DFZEventListener implements ApplicationListener {
    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        System.out.println(event.getClass().getSimpleName());
    }
}
