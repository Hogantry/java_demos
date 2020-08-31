package com.dfz.validator;

import com.dfz.validator.component.PayRequestDto;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

import java.util.Set;

/**
 * @version V1.0
 * @author: DFZ
 * @description: validation
 * @date: 2020/8/24 16:46
 * @Copyright: 2020 www.ztzqzg.com Ltd. All rights reserved.
 * 注意：本内容仅限于中泰证券（上海）资产管理有限公司内部传阅，禁止外泄以及用于其他的商业项目
 */
public class ValidationApplication {

    public static void main(String[] args) {
        PayRequestDto dto = new PayRequestDto();
        // SPI
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<PayRequestDto>> constraints = validator.validate(dto);
        if (constraints.size() > 0) {
            for (ConstraintViolation<PayRequestDto> constraint : constraints) {
                System.out.printf("参数校验失败:%s%n", constraint.getMessage());
            }
        }
    }

}
