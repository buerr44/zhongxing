package com.fawvw.cloud.common.api.dto;

import lombok.Data;

/**
 * @author HuangGang
 * @create 2021-03-29 10:47
 **/
@Data
public class PlanCourseDTO extends CourseDTO{
    private Long planCourseId;
    private Long studyPlanId;
}
