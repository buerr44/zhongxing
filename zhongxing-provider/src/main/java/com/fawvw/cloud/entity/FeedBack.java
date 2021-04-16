package com.fawvw.cloud.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @author HuangGang
 * @create 2021-02-06 19:34
 **/
@Data
public class FeedBack extends BaseEntity{
    @TableId(value = "fb_id")
    private Long fbId;
    private Long courseId;
}
