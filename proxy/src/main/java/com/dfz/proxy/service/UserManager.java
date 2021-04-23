package com.dfz.proxy.service;

/**
 * @version V1.0
 * @author: DFZ
 * @description: manager
 * @date: 2021/4/8 09:35
 * @Copyright: 2021 www.ztzqzg.com Ltd. All rights reserved.
 * 注意：本内容仅限于中泰证券（上海）资产管理有限公司内部传阅，禁止外泄以及用于其他的商业项目
 */
public interface UserManager {

    void addUser(String userName,String password);
    void delUser(String userName);

}
