package com.fawvw.cloud.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @author HuangGang
 * @create 2021-02-04 18:56
 **/
@Data
public class Course extends BaseEntity{
    @TableId(value = "course_id")
    private Long courseId;
    private String courseCode;
    private String courseName;
    private String video;
    private Long structureId;
    private Long playNum;
    private Integer duration;
    private Integer sequence;
    private String cover;
    private Integer price;
    private Long examId;
    private String teacher;
}
