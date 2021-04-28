package com.fawvw.cloud.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @author HuangGang
 * @create 2021-04-26 14:44
 **/
@Data
public class Popup extends BaseEntity{
    @TableId(value = "popup_id",type = IdType.INPUT)
    private Long popupId;
    private String url;
    private String pic;
}
