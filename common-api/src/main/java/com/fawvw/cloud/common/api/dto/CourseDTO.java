package com.fawvw.cloud.common.api.dto;

import lombok.Data;

/**
 * @author HuangGang
 * @create 2021-01-27 21:06
 **/
@Data
public class CourseDTO extends BaseDTO{

    private Long courseId;
    private String courseCode;
    private String courseName;
    private Long structureId;
    private String structureName;
    private String teacher;
    private Long playNum;
    private Integer duration;
    private Integer sequence;
    private String video;
    private String cover;
    private Integer price;
    private Long examId;

}
