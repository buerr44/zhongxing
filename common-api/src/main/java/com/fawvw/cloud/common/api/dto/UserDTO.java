package com.fawvw.cloud.common.api.dto;

import lombok.Data;

import java.util.Map;

/**
 * @author HuangGang
 * @create 2021-03-01 14:58
 **/
@Data
public class UserDTO extends BaseDTO{

    private Long userId;
    private String nickname;
    private Integer employeeCode;
    private String employeeName;
    private String description;
    private Integer totalLearn;
    private Integer weekLearn;
    private Integer totalFinish;
    private Integer itFinish;
    private Map<String,String> studyPlan;
}
