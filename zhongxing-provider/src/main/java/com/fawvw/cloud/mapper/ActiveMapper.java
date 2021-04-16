package com.fawvw.cloud.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fawvw.cloud.common.api.dto.EmployeeDTO;
import com.fawvw.cloud.entity.Active;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author HuangGang
 * @create 2021-03-01 10:27
 **/
public interface ActiveMapper extends BaseMapper<Active> {
    @Select("SELECT  ac.active_id, ac.employee_code,ac.employee_name,ac.employee_idCard,ac.description,ac.user_id,u.nickname FROM `tb_active` ac LEFT JOIN tb_user u ON ac.user_id = u.user_id WHERE ac.is_valid = 0")
    List<EmployeeDTO> listAllEmployee();
    @Select("SELECT  ac.active_id, ac.employee_code,ac.employee_name,ac.employee_idCard,ac.description,ac.user_id,u.nickname FROM `tb_active` ac INNER JOIN tb_user u ON ac.user_id = u.user_id WHERE ac.is_valid = 0")
    List<EmployeeDTO> listActiveEmployee();
    @Select("SELECT  active_id, employee_code,employee_name,employee_idCard,description FROM `tb_active` WHERE user_id = 0 or ISNULL( user_id ) and is_valid = 0")
    List<EmployeeDTO> listNotActiveEmployee();
}
