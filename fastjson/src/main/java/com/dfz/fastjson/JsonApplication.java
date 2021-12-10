package com.dfz.fastjson;

import com.alibaba.fastjson.JSON;

import java.util.HashMap;

/**
 * @author DFZ
 * @Title:
 * @Package
 * @Description:
 * @date 2021-06-0810:03
 */
public class JsonApplication {

    public static void main(String[] args) {
        HashMap<String, Integer> map = new HashMap<>(16);
        map.put(null, 0);
        String s = JSON.toJSONString(map);
        System.out.println(s);
    }

}
