package com.fawvw.cloud.common.api.dto;

import lombok.Data;

/**
 * @author HuangGang
 * @create 2021-03-01 9:23
 **/
@Data
public class EmployeeDTO extends BaseDTO{
    private Long activeId;
    private Integer employeeCode;
    private String employeeName;
    private String employeeIdCard;
    private Long userId;
    private String nickname;
}
