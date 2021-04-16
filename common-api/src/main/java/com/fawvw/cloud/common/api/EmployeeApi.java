package com.fawvw.cloud.common.api;

import com.fawvw.cloud.common.api.dto.EmployeeDTO;
import com.fawvw.cloud.common.api.result.ApiResult;

import java.util.List;

/**
 * @author HuangGang
 * @create 2021-03-01 10:40
 **/
public interface EmployeeApi {
    /**
     * 根据认证状态读取数据
     * @param status
     * @return
     */
    ApiResult<List<EmployeeDTO>> listEmployee(Integer status);

    /**
     * 添加企业员工信息
     * @return
     */
    ApiResult<String> addEmployee(EmployeeDTO employeeDTO);

    /**
     * 批量导入
     * @param employeeDTOs
     * @return
     */
    ApiResult<String> uploadEmployee(List<EmployeeDTO> employeeDTOs);
    /**
     * 编辑员工信息
     * @param employeeDTO
     * @return
     */
    ApiResult<String> editEmployee(EmployeeDTO employeeDTO);

    /**
     * 删除员工信息
     * @param activeId
     * @param lastUpdateBy
     * @return
     */
    ApiResult<String> deleteEmployee(Long activeId, Long lastUpdateBy);
}
