package com.fawvw.cloud.common.api.dto;

import lombok.Data;

/**
 * @author HuangGang
 * @create 2021-03-29 10:37
 **/
@Data
public class StudyPlanDTO extends BaseDTO{

    private Long studyPlanId;
    private String studyPlanName;
    private String pic1;
    private String pic2;
    private Integer courseCount;
    private Integer learnerCount;
    private Integer planType;
    private String planTask;
    private String planStatus;

}
