package com.fawvw.cloud.common.api.dto;

import lombok.Data;

/**
 * @author HuangGang
 * @create 2021-02-06 19:31
 **/
@Data
public class FeedbackDTO extends BaseDTO{

    private Long fbId;
    private Long courseId;
    private String courseCode;
    private String teacher;
    private Integer role;
}
