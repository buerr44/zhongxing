package com.fawvw.cloud.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author HuangGang
 * @create 2021-03-31 15:04
 **/
@Data
@TableName("tb_flowerword")
public class FlowerWord extends BaseEntity{
    @TableId(value = "flowerword_id")
    private Long flowerWordId;
    private String half1;
    private String half2;
}
