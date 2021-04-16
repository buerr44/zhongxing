package com.fawvw.cloud.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @author HuangGang
 * @create 2021-01-29 16:04
 **/
@Data
public class Structure extends BaseEntity{
    @TableId(value = "structure_id")
    private Long structureId;
    @TableField(value = "structure_code")
    private String structureCode;
    @TableField(value = "structure_name")
    private String structureName;
    private Long pid;
    private String pic1;
    private String pic2;
    private Integer sequence;
    @TableField(value = "click_num")
    private Long clickNum;
    private String harvest;
    private String teacher;
}
