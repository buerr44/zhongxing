package com.fawvw.cloud.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author HuangGang
 * @create 2021-03-31 10:53
 **/
@Data
@TableName("tb_flowerpic")
public class FlowerPic extends BaseEntity{
    @TableId(value = "flowerpic_id",type = IdType.INPUT)
    private Long flowerPicId;
    private String pic;
}
