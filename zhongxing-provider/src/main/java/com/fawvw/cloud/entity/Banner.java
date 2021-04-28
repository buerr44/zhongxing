package com.fawvw.cloud.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author HuangGang
 * @create 2021-03-31 18:57
 **/
@Data
@TableName("tb_maquee")
public class Banner extends BaseEntity {
    @TableId(value = "maquee_id",type = IdType.INPUT)
    private Long bannerId;
    private String pic;
    private Integer sequence;
    private String link;
}
