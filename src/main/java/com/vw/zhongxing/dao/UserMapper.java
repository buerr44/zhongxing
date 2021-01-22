package com.vw.zhongxing.dao;

import com.vw.zhongxing.model.UserDO;
import org.apache.ibatis.annotations.Param;

/**
 * @author Buer44
 */
public interface UserMapper {
    /**
     * 登录
     * @param username
     * @param password
     * @return
     */
    UserDO login(@Param("username") String username, @Param("password") String password);
}
