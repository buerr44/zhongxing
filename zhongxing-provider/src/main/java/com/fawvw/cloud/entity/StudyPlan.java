package com.fawvw.cloud.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author HuangGang
 * @create 2021-03-29 16:54
 **/
@Data
@TableName("tb_studyplan")
public class StudyPlan extends BaseEntity{
    @TableId(value = "studyPlan_id")
    private Long studyPlanId;
    @TableField(value = "studyPlan_Name")
    private String studyPlanName;
    private String pic1;
    private String pic2;
    private Integer learnerCount;
    private Integer planType;
    private String planTask;
}
