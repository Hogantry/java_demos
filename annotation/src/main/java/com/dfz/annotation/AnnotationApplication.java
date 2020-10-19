package com.dfz.annotation;

import com.dfz.annotation.common.Bar;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.annotation.Annotation;

/**
 * @version V1.0
 * @author: DFZ
 * @description: 注解相关知识demo
 * @date: 2020/8/3 17:31
 * @Copyright: 2020 www.ztzqzg.com Ltd. All rights reserved.
 * 注意：本内容仅限于中泰证券（上海）资产管理有限公司内部传阅，禁止外泄以及用于其他的商业项目
 */
public class AnnotationApplication {

    public static void main(String[] args) {
        Annotation[] annotations = Bar.class.getAnnotations();

        for (Annotation annotation : annotations) {
            Class<? extends Annotation> annotationType = annotation.annotationType();
            System.out.println("annotation: " + annotationType);
            Annotation[] annotations1 = annotationType.getAnnotations();
            for (Annotation annotation1 : annotations1) {
                System.out.println("annotation1: " + annotation1.annotationType());
            }
            System.out.println("--------------------");
        }

        System.out.println("++++++++++++++++++++");

        Annotation[] annotationsSpring = AnnotationUtils.getAnnotations(Bar.class);
        for (Annotation annotation : annotationsSpring) {
            System.out.println("annotation: " + annotation.annotationType());
        }

    }

}

