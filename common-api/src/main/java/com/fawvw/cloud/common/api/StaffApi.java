package com.fawvw.cloud.common.api;

import com.fawvw.cloud.common.api.dto.StaffDTO;
import com.fawvw.cloud.common.api.result.ApiResult;

/**
 * @author HuangGang
 * @create 2021-02-06 15:56
 **/
public interface StaffApi {
    /**
     * 员工登录
     * @param staffName
     * @param password
     * @return
     */
    ApiResult<StaffDTO> login(String staffName,String password);
}
