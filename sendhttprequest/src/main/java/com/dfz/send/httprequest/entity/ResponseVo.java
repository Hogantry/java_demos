package com.dfz.send.httprequest.entity;

/**
 * @version V1.0
 * @author: DFZ
 * @description: ResponseVo
 * @date: 2020/7/20 16:47
 * @Copyright: 2020 www.ztzqzg.com Ltd. All rights reserved.
 * 注意：本内容仅限于中泰证券（上海）资产管理有限公司内部传阅，禁止外泄以及用于其他的商业项目
 */
public class ResponseVo {

    private Boolean success;
    private String code;
    private Object data;
    private String message;
    private Object extra;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getExtra() {
        return extra;
    }

    public void setExtra(Object extra) {
        this.extra = extra;
    }

    @Override
    public String toString() {
        return "ResponseVo{" +
                "success=" + success +
                ", code='" + code + '\'' +
                ", data=" + data +
                ", message='" + message + '\'' +
                ", extra=" + extra +
                '}';
    }
}
