package com.vw.zhongxing.service;

import com.vw.zhongxing.model.UserDO;

/**
 * @author Buer44
 */
public interface UserService {
    /**
     * 登录
     * @param username
     * @param password
     * @return
     */
    UserDO login(String username,String password);
}
