package com.fawvw.cloud.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @author HuangGang
 * @create 2021-03-01 10:25
 **/
@Data
public class Active extends BaseEntity{
    @TableId(value = "active_id")
    private Long activeId;
    private Integer employeeCode;
    private String employeeName;
    @TableField(value = "employee_idCard")
    private String employeeIdCard;
    private Long userId;
}
