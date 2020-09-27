package com.dfz.validator.component;

import org.hibernate.validator.constraints.ScriptAssert;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * @version V1.0
 * @author: DFZ
 * @description: @ScriptAssert是Hibernate Validator提供的一个脚本约束注解，可以实现垮字段逻辑校验，功能非常之强大
 * @date: 2020/8/24 16:48
 * @Copyright: 2020 www.ztzqzg.com Ltd. All rights reserved.
 * 注意：本内容仅限于中泰证券（上海）资产管理有限公司内部传阅，禁止外泄以及用于其他的商业项目
 */
@ScriptAssert(script = "_this.payTime==_this.createTime", lang = "javascript")
public class PayRequestDto {

    /**
     * 支付完成时间
     */
    @NotEmpty(message="支付完成时间不能空")
    @Size(max=14,message="支付完成时间长度不能超过{max}位")
    private String payTime;

    /**
     * 状态
     */
    @Pattern(regexp = "0[0123]", message = "状态只能为00或01或02或03")
    @NotNull
    private String status;

    private String createTime;

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
