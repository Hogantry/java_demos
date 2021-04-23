package com.dfz.validator;

import com.dfz.validator.component.PayRequestDto;
import com.dfz.validator.service.impl.PayRequestServiceImpl;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.executable.ExecutableValidator;
import java.util.Set;

/**
 * @version V1.0
 * @author: DFZ
 * @description:  validation-api 2.0 支持的所有注解
 *                  注解             支持类型	                            含义	                                         null值是否校验
 *               @AssertFalse       bool                               元素必须是false	                                否
 *               @AssertTrue        bool                               元素必须是true	                                    否
 *               @DecimalMax        Number的子类型（浮点数除外）以及String	元素必须是一个数字，且值必须<=最大值	                否
 *               @DecimalMin        同上                                元素必须是一个数字，且值必须>=最大值	                否
 *               @Max               同上                                同上	                                            否
 *               @Min               同上                                同上	                                            否
 *               @Digits            同上                                元素构成是否合法（整数部分和小数部分）	                否
 *               @Future            时间类型(包括JSR310)                 元素必须为一个「将来」（不包含相等）的日期(比较精确到毫秒)	否
 *               @Past              同上                                元素必须为一个「过去」（不包含相等）的日期(比较精确到毫秒)	否
 *               @NotNull           any                                元素不能为null	                                    是
 *               @Null              any                                元素必须为null	                                    是
 *               @Pattern           字符串                              元素需符合指定的正则表达式	                        否
 *               @Size              String/Collection/Map/Array	       元素「大小」需在指定范围中	                        否
 *               @Email             字符串                              元素必须为电子邮箱地址	                            否
 *               @NotEmpty          容器类型                            集合的Size必须大于0	                                是
 *               @NotBlank          字符串                              字符串必须包含至少一个非空白的字符	                    是
 *               @Positive          数字类型                            元素必须为正数（不包括0）	                            否
 *               @PositiveOrZero    同上                               同上（包括0）	                                    否
 *               @Negative          同上                               元素必须为负数（不包括0）	                            否
 *               @NegativeOrZero    同上                               同上（包括0）	                                    否
 *               @PastOrPresent     时间类型                            在@Past基础上包括相等	                            否
 *               @FutureOrPresent   时间类型                            在@Futrue基础上包括相等	                            否
 *
 *               validation-api 1.0：提供13个注解，支持对java bean进行校验 ---> Hibernate Validator 4.X
 *               1.1：支持方法级验证(入参或返回值的验证)，使用「EL表达式」的错误消息插值，让错误消息动态化起来（强依赖于ElManager） ---> Hibernate Validator 5.X
 *               2.0：新增内建的注解类型（共9个，Hibernate中的被移除）。 ---> Hibernate Validator 5.X
 *
 *               备注：el规范的api GAV是javax.el-api，tomcat默认对其有实现，而且tomcat默认支持的jsp的el表达式，即是该api的实现。tomcat-embed-el
 *               Java EE改名为Jakarta EE，所以新推出的jakarta.validation-api 3.0，与原来的2.0代码一模一样，只是改了包名。
 *               Hibernate Validator 7.X依赖3.0的api，而6.x依赖的api为2.0，只是改了依赖的GAV，包名并没有改。
 *
 * @date: 2020/8/24 16:46
 * @Copyright: 2020 www.ztzqzg.com Ltd. All rights reserved.
 * 注意：本内容仅限于中泰证券（上海）资产管理有限公司内部传阅，禁止外泄以及用于其他的商业项目
 */
public class ValidationApplication {

    public static void main(String[] args) throws NoSuchMethodException {
//        validateJavaBean();
        validateMethod();
    }

    /**
     * 对java bean进行校验
     */
    private static void validateJavaBean() {
        PayRequestDto dto = new PayRequestDto();
        dto.setPayTime("ad");
        Validator validator = getValidator();
        // 校验Java Bean（解析注解） 返回校验结果
        Set<ConstraintViolation<PayRequestDto>> result = validator.validate(dto);
        // 输出校验结果
        result.stream().map(v -> v.getPropertyPath() + " " + v.getMessage() + ": " + v.getInvalidValue()).forEach(System.out::println);
    }

    /**
     * 对方法的参数或返回值进行校验：
     *  1. 如果该方法时从父类/接口继承而来的，则该方法不能加注解，所有的校验条件注解从父类/接口方法中获取，或者该方法的注解与父类/接口方法中的注解完全一致
     *  2. 如果该方法不是从父类/接口继承而来，则该方法可以加注解。
     * @throws NoSuchMethodException
     */
    private static void validateMethod() throws NoSuchMethodException {
        PayRequestServiceImpl payRequestService = new PayRequestServiceImpl();
        payRequestService.getOne(0, "DFZ");
    }

    /**
     * 用于Java Bean校验的校验器
     * Validator是线程安全的，推荐使用单例模式
     * @return
     */
    private static Validator getValidator() {
        // 1、使用【默认配置】得到一个校验工厂  这个配置可以来自于provider、SPI提供
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        // 2、得到一个校验器
        return validatorFactory.getValidator();
    }

    /**
     * 用于方法校验的校验器
     * @return
     */
    public static ExecutableValidator getExecutableValidator() {
        return getValidator().forExecutables();
    }

    public static <T> void printViolations(Set<ConstraintViolation<T>> violations) {
        violations.stream().map(v -> v.getPropertyPath() + " " + v.getMessage() + ": " + v.getInvalidValue()).forEach(System.out::println);
    }

}
