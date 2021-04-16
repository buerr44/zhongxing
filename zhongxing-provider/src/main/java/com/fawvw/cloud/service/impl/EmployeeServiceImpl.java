package com.fawvw.cloud.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.fawvw.cloud.common.api.EmployeeApi;
import com.fawvw.cloud.common.api.dto.EmployeeDTO;
import com.fawvw.cloud.common.api.result.ApiResult;
import com.fawvw.cloud.entity.Active;
import com.fawvw.cloud.mapper.ActiveMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author HuangGang
 * @create 2021-03-01 10:42
 **/
@Slf4j
@Service(version = "${dubbo.provider.version}", application = "${dubbo.application.id}")
public class EmployeeServiceImpl implements EmployeeApi {
    @Autowired
    private ActiveMapper activeMapper;

    @Override
    public ApiResult<List<EmployeeDTO>> listEmployee(Integer status) {
        ApiResult<List<EmployeeDTO>> apiResult;

        List<EmployeeDTO> employeeDTOS = new ArrayList<>();
        try {
            if (status == 0) {
                employeeDTOS = activeMapper.listAllEmployee();
            } else if (status == 1) {
                employeeDTOS = activeMapper.listActiveEmployee();
            } else {
                employeeDTOS = activeMapper.listNotActiveEmployee();
            }
            if(employeeDTOS != null && !employeeDTOS.isEmpty()){
                for (EmployeeDTO employeeDTO : employeeDTOS) {
                    if(employeeDTO != null) {
                        if (employeeDTO.getUserId() == null || employeeDTO.getUserId() == 0) {
                            employeeDTO.setStatus(2);
                        } else {
                            employeeDTO.setStatus(1);
                        }
                    }
                }
            }
            apiResult = ApiResult.success(employeeDTOS);
        }catch (Exception e){
            log.error(e.getMessage());
            apiResult = ApiResult.error("B0300",e.getMessage());
        }

        return apiResult;
    }

    @Override
    public ApiResult<String> addEmployee(EmployeeDTO employeeDTO) {
        ApiResult<String> apiResult;

        Active active = new Active();
        active.setEmployeeCode(employeeDTO.getEmployeeCode());
        active.setEmployeeName(employeeDTO.getEmployeeName());
        active.setEmployeeIdCard(employeeDTO.getEmployeeIdCard());
        active.setDescription(employeeDTO.getDescription());
        active.setUserId(0L);
        active.setCreateBy(employeeDTO.getCreateBy());
        active.setCreateDate(new Date());
        try{
            int insert = activeMapper.insert(active);
            if(insert == 1){
                apiResult = ApiResult.success();
            }else{
                apiResult = ApiResult.error("B0300","数据库插入异常");
            }
        }catch (Exception e){
            log.error(e.getMessage());
            apiResult = ApiResult.error("B0300",e.getMessage());
        }

        return apiResult;
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public ApiResult<String> uploadEmployee(List<EmployeeDTO> employeeDTOs) {
        ApiResult<String> apiResult;

        int k = 0;
        try{
            if(employeeDTOs != null && !employeeDTOs.isEmpty()){
                for (EmployeeDTO employeeDTO : employeeDTOs) {
                    QueryWrapper<Active> queryWrapper = new QueryWrapper<>();
                    queryWrapper.eq("employee_code",employeeDTO.getEmployeeCode());
                    if(activeMapper.selectOne(queryWrapper) == null) {
                        Active active = new Active();
                        active.setEmployeeCode(employeeDTO.getEmployeeCode());
                        active.setEmployeeName(employeeDTO.getEmployeeName());
                        active.setEmployeeIdCard(employeeDTO.getEmployeeIdCard());
                        active.setDescription(employeeDTO.getDescription());
                        active.setUserId(0L);
                        active.setCreateBy(employeeDTO.getCreateBy());
                        active.setCreateDate(new Date());
                        k += activeMapper.insert(active);
                    }else{
                        k++;
                    }
                }
                if(k == employeeDTOs.size()) {
                    apiResult = ApiResult.success();
                }else{
                    apiResult = ApiResult.error("B0300","未正常插入数据");
                }
            }else{
                apiResult = ApiResult.error("B0300","入参不能为空");
            }
        }catch (Exception e){
            log.error(e.getMessage());
            apiResult = ApiResult.error("B0300",e.getMessage());
        }

        return apiResult;
    }

    @Override
    public ApiResult<String> editEmployee(EmployeeDTO employeeDTO) {
        ApiResult<String> apiResult;

        UpdateWrapper<Active> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("employee_code",employeeDTO.getEmployeeCode());
        updateWrapper.set("employee_name",employeeDTO.getEmployeeName());
        updateWrapper.set("employee_idCard",employeeDTO.getEmployeeIdCard());
        updateWrapper.set("description",employeeDTO.getDescription());
        updateWrapper.set("last_update_by",employeeDTO.getLastUpdateBy());
        updateWrapper.set("last_update_date",new Date());
        updateWrapper.eq("active_id",employeeDTO.getActiveId());
        try{
            int update = activeMapper.update(null, updateWrapper);
            if(update == 1){
                apiResult = ApiResult.success();
            }else{
                apiResult = ApiResult.error("B0300", "未正常修改数据");
            }
        } catch (Exception e){
            log.error(e.getMessage());
            apiResult = ApiResult.error("B0300",e.getMessage());
        }

        return apiResult;
    }

    @Override
    public ApiResult<String> deleteEmployee(Long activeId, Long lastUpdateBy) {
        ApiResult<String> apiResult;

        try{
            UpdateWrapper<Active> updateWrapper = new UpdateWrapper<>();
            updateWrapper.set("is_valid","-1").set("last_update_by",lastUpdateBy).set("last_update_date",new Date()).eq("active_id",activeId);
            int update = activeMapper.update(null, updateWrapper);
            if (update == 1) {
                apiResult = ApiResult.success();
            }else{
                apiResult = ApiResult.error("B0300", "未正常修改数据");
            }
        }catch (Exception e){
            log.error(e.getMessage());
            apiResult = ApiResult.error("B0300",e.getMessage());
        }

        return apiResult;
    }

}
