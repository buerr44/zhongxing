package com.fawvw.cloud.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fawvw.cloud.common.api.StaffApi;
import com.fawvw.cloud.common.api.dto.StaffDTO;
import com.fawvw.cloud.common.api.result.ApiResult;
import com.fawvw.cloud.entity.Staff;
import com.fawvw.cloud.mapper.StaffMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author HuangGang
 * @create 2021-02-06 16:38
 **/
@Slf4j
@Service(version = "${dubbo.provider.version}", application = "${dubbo.application.id}")
public class StaffServiceImpl implements StaffApi {
    @Autowired
    private StaffMapper staffMapper;


    @Override
    public ApiResult<StaffDTO> login(String staffName, String password) {
        ApiResult<StaffDTO> apiResult;

        QueryWrapper<Staff> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("staff_name", staffName).eq("is_valid", "0");
        try {
            Staff staff = staffMapper.selectOne(queryWrapper);
            if (staff == null) {
                apiResult = ApiResult.error("1", "用户名不存在");
            } else if (staff.getRole() != 1) {
                apiResult = ApiResult.error("1", "当前用户无管理员权限");
            } else if (!staff.getPassword().equals(password)) {
                apiResult = ApiResult.error("1", "密码错误");
            } else {
                StaffDTO staffDTO = new StaffDTO();
                staffDTO.setStaffId(staff.getStaffId());
                staffDTO.setStaffName(staffName);
                staffDTO.setPassword(password);
                staffDTO.setRole(staff.getRole());
                apiResult = ApiResult.success(staffDTO);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            apiResult = ApiResult.error("B0300", e.getMessage());
        }

        return apiResult;
    }
}
