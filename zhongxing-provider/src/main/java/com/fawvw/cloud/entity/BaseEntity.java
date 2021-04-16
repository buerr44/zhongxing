package com.fawvw.cloud.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.util.Date;

/**
 * @author HuangGang
 * @create 2021-01-29 16:03
 **/
@Data
public class BaseEntity {

    private String description;
    private Integer status;
    private Long createBy;
    private Date createDate;
    private Long lastUpdateBy;
    private Date lastUpdateDate;
    private Integer rowVersion;
    @TableField(value = "is_valid")
    private Integer valid;
}
