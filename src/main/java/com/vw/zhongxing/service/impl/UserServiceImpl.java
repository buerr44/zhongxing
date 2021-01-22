package com.vw.zhongxing.service.impl;

import com.vw.zhongxing.dao.UserMapper;
import com.vw.zhongxing.model.UserDO;
import com.vw.zhongxing.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Buer44
 */
@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;

    @Override
    public UserDO login(String username, String password) {
        UserDO user = new UserDO();

        user = userMapper.login(username,password);

        return user;
    }
}
