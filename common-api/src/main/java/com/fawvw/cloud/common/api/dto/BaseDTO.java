package com.fawvw.cloud.common.api.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author HuangGang
 * @create 2021-01-27 21:12
 **/
@Data
public class BaseDTO implements Serializable{

    private String description;
    private Integer status;
    private Long createBy;
    private Date createDate;
    private Long lastUpdateBy;
    private Date lastUpdateDate;
    private Integer rowVersion;
    private Integer valid;
}
