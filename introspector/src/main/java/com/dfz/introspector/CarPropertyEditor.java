package com.dfz.introspector;

import com.dfz.introspector.component.Car;

import java.beans.PropertyEditorSupport;

/**
 * @ClassName CarPropertyEditor
 * @Description CarPropertyEditor
 * @Author dengfazhi
 * @Date 2019-03-30 19:11
 * @Version 1.0
 **/
public class CarPropertyEditor extends PropertyEditorSupport {

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        String[] strings = text.split(",");
        Car car = new Car();
        car.setBread(strings[0]);
        car.setPrice(strings[1]);
        setValue(car);
    }
}
