package com.dfz.event;

import com.dfz.event.component.DFZEventListener;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.context.event.ContextStoppedEvent;

import java.io.IOException;

/**
 * @Author DFZ
 * @Date 2021-07-01 17:21
 * @Description
 *  AnnotationConfigApplicationContext 默认只会发出 {@link ContextRefreshedEvent} 事件，
 *  必须手动调用 start/stop/close 方法，才会发出 {@link ContextStartedEvent} / {@link ContextStoppedEvent} / {@link ContextClosedEvent}
 *  事件
 */
public class EventApplication {

    public static void main(String[] args) throws IOException, InterruptedException {
//        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DFZEventListener.class);
//        context.start();
//        context.stop();
//        context.close();
//        System.in.read();

//         基本可以确定，ContextRefreshedEvent事件必定发生在ContextStartedEvent事件之前
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(DFZEventListener.class);
        context.refresh();
        context.start();
        context.stop();
        context.close();
        System.in.read();
    }

}
