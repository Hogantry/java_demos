package com.dfz.classloader;

import java.util.LinkedList;
import java.util.Stack;

/**
 * @version V1.0
 * @author: DFZ
 * @description:
 * @date: 2020/6/30 16:31
 * @Copyright: 2020 www.ztzqzg.com Ltd. All rights reserved.
 * 注意：本内容仅限于中泰证券（上海）资产管理有限公司内部传阅，禁止外泄以及用于其他的商业项目
 */
public class CQueue {

    private LinkedList<Integer> stack1;
    private LinkedList<Integer> stack2;

    public CQueue() {
        stack1 = new LinkedList<>();
        stack2 = new LinkedList<>();
    }

    public void appendTail(int value) {
        stack1.push(value);
    }

    public int deleteHead() {
        if (!stack2.isEmpty()) {
            return stack2.pop();
        } else {
            if (stack1.isEmpty()) {
                return -1;
            } else {
                while (!stack1.isEmpty()) {
                   stack2.push(stack1.pop());
                }
                return stack2.pop();
            }
        }
    }

    public static void main(String[] args) {
        CQueue obj = new CQueue();
        obj.appendTail(1);
        obj.appendTail(2);
        obj.appendTail(3);
        System.out.println(obj.deleteHead());
        obj.appendTail(4);
        obj.appendTail(5);
        obj.appendTail(6);
        System.out.println(obj.deleteHead());
        System.out.println(obj.deleteHead());
        System.out.println(obj.deleteHead());
        System.out.println(obj.deleteHead());
        System.out.println(obj.deleteHead());
        System.out.println(obj.deleteHead());
        System.out.println(obj.deleteHead());
        System.out.println(obj.deleteHead());
        System.out.println(obj.deleteHead());
        System.out.println(obj.deleteHead());
        System.out.println(obj.deleteHead());
    }

}
