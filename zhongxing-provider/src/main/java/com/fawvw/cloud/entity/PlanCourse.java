package com.fawvw.cloud.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author HuangGang
 * @create 2021-03-29 16:58
 **/
@Data
@TableName("tb_plancourse")
public class PlanCourse extends BaseEntity {
    @TableId(value = "planCourse_id")
    private Long planCourseId;
    @TableField(value = "studyPlan_id")
    private Long studyPlanId;
    private Long courseId;
    private Integer sequence;
}
