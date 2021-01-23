package com.dfz.validator.service.impl;

import com.dfz.validator.component.PayRequestDto;
import com.dfz.validator.service.PayRequestService;
import org.hibernate.validator.constraints.Length;

import javax.validation.ConstraintViolation;
import java.lang.reflect.Method;
import java.util.Set;

import static com.dfz.validator.ValidationApplication.getExecutableValidator;

/**
 * @ClassName PayRequestServiceImpl
 * @Description TODO
 * @Author dengfazhi
 * @Date 2020/9/27 3:16 下午
 * @Version 1.0
 **/
public class PayRequestServiceImpl implements PayRequestService {
    @Override
    public PayRequestDto getOne(Integer id, String name) throws NoSuchMethodException {
        PayRequestDto result = null;
        // 校验逻辑
        Method currMethod = this.getClass().getMethod("getOne", Integer.class, String.class);
        Set<ConstraintViolation<PayRequestServiceImpl>> validResult = getExecutableValidator().validateParameters(this, currMethod, new Object[]{id, name});
        if (!validResult.isEmpty()) {
            // ... 输出错误详情validResult
            validResult.stream().map(v -> v.getPropertyPath() + " " + v.getMessage() + ": " + v.getInvalidValue()).forEach(System.out::println);
            throw new IllegalArgumentException("参数错误");
        }
        // getOne方法的具体逻辑
        // ...

        // 这里类似SpringAOP，在方法执行前先进行方法参数校验，校验成功才能执行方法。执行之后对方法返回值进行校验
        // 校验成功才能真正返回。

        // 在结果返回之前校验
        Set<ConstraintViolation<PayRequestServiceImpl>> validResult2 = getExecutableValidator().validateReturnValue(this, currMethod, result);
        if (!validResult2.isEmpty()) {
            // ... 输出错误详情validResult
            validResult2.stream().map(v -> v.getPropertyPath() + " " + v.getMessage() + ": " + v.getInvalidValue()).forEach(System.out::println);
            throw new IllegalArgumentException("参数错误");
        }
        return null;
    }

    public void getTwo(Integer id, String name) {

    }
}
