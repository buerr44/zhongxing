package com.fawvw.cloud.common.api;

import com.fawvw.cloud.common.api.dto.UserDTO;
import com.fawvw.cloud.common.api.result.ApiResult;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author HuangGang
 * @create 2021-01-27 14:35
 **/
public interface UserApi {

    ApiResult<Integer[]> getUserData();

    ApiResult<Map<String, List<Object>>> getChartData(Integer status,Date startDate,Date endDate);

    /**
     * 获取用户学习数据
     * @param status
     * @return
     */
    ApiResult<List<UserDTO>> listUserInfo(Integer status);
}
