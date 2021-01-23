package com.dfz.validator.service;

import com.dfz.validator.component.PayRequestDto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @InterfaceName PayRequestService
 * @Description TODO
 * @Author dengfazhi
 * @Date 2020/9/27 3:15 下午
 * @Version 1.0
 **/
public interface PayRequestService {

    @NotNull PayRequestDto getOne(@NotNull @Min(1) Integer id, String name) throws NoSuchMethodException;

}
