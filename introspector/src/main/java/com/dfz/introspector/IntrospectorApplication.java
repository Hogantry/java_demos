package com.dfz.introspector;

import com.dfz.introspector.component.Person;
import com.dfz.introspector.component.PersonService;
import com.sun.tools.javac.util.ArrayUtils;

import java.beans.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @version V1.0
 * @author: DFZ
 * @description: Introspector 内省，主要用于操作 Javabean。 基于JavaBean的规范进行Bean信息描述符的解析，
 *               依据于类的Setter和Getter方法，可以获取到类的描述符。
 *               反射则是运行时获取类信息，有权限的情况下，可操作类的任何信息。
 *               Introspector是反射的一个子集
 *               内省获取到的属性名，只与Setter和Getter方法的方法名有关，与类中的字段无关，具体可参考{@link Person}类
 *               因为所有的类均继承自Object类，所以所有的类都有getClass类，该类会被内省机制处理，因此在使用内省机制时，主要该方法(class属性)的处理
 *               在JavaBeans Introspector使用过程中会启用了一个系统级别的缓存，JDK的Introspector缓存管理是有一定缺陷的，例如当Web
 *               服务器关闭的时候，由于这个缓存中存放着这些Javabean的引用，垃圾回收器不能对Web容器中的JavaBean对象进行回收，出现内存泄露，严重导致
 *               内存溢出，可参考Spring中提供的org.springframework.web.util.IntrospectorCleanupListener或CachedIntrospectionResults的解决方案。
 *
 *               PropertyDescriptor类可关联PropertyEditor类，实现属性的类型转换，在Spring中有大量应用
 * @date: 2020/8/12 09:34
 * @Copyright: 2020 www.ztzqzg.com Ltd. All rights reserved.
 * 注意：本内容仅限于中泰证券（上海）资产管理有限公司内部传阅，禁止外泄以及用于其他的商业项目
 */
public class IntrospectorApplication {

    public static void main(String[] args) throws IntrospectionException {
        BeanInfo beanInfo = Introspector.getBeanInfo(Person.class);
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        Arrays.stream(propertyDescriptors).forEach(propertyDescriptor -> {
            StringBuilder sb = new StringBuilder();
            String name = propertyDescriptor.getName();
            sb.append("name: " + name);
            Method readMethod = propertyDescriptor.getReadMethod();
            if (readMethod != null) {
                sb.append("; readMethod: " + readMethod.getName() + "; returnType: " + readMethod.getReturnType());
            }
            Method writeMethod = propertyDescriptor.getWriteMethod();
            if (writeMethod != null) {
                sb.append("; writeMethod: " + writeMethod.getName() + "; returnType: " + writeMethod.getReturnType());
                if (propertyDescriptor.getName().equals("car")) {
                    System.out.println("car");
                    propertyDescriptor.setPropertyEditorClass(CarPropertyEditor.class);
                    PropertyEditor propertyEditor = propertyDescriptor.createPropertyEditor(null);
                    propertyEditor.setAsText("宝马,10W");
                    Object value = propertyEditor.getValue();
                    Person person = new Person();
                    try {
                        writeMethod.invoke(person, value);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                    System.out.println(person);
                }
            }
            System.out.println(sb.toString());
            System.out.println();
        });

        BeanDescriptor beanDescriptor = beanInfo.getBeanDescriptor();
        System.out.println(beanDescriptor.getName() + ";" + beanDescriptor.getDisplayName() + ";" + beanDescriptor.getBeanClass());
        System.out.println("---------");
        MethodDescriptor[] methodDescriptors = beanInfo.getMethodDescriptors();
        if (methodDescriptors == null) {
            System.out.println("methodDescriptors is empty");
        } else {
            Arrays.asList(methodDescriptors).forEach(methodDescriptor -> {
                System.out.println(methodDescriptor.getMethod().getName());
//                if (methodDescriptor.getParameterDescriptors() == null) {
//                    System.out.println("parameterDescriptors is empty");
//                } else {
//                    Arrays.asList(methodDescriptor.getParameterDescriptors()).forEach(parameterDescriptor -> {
//                        System.out.println(parameterDescriptor.getName());
//                        System.out.println(parameterDescriptor.getShortDescription());
//                    });
//                }
            });
        }

    }

}
