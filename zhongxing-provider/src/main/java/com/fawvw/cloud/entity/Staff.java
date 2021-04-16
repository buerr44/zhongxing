package com.fawvw.cloud.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @author HuangGang
 * @create 2021-02-06 16:39
 **/
@Data
public class Staff extends BaseEntity{
    @TableId(value = "staff_id")
    private Long staffId;
    private String staffName;
    private String password;
    private Integer role;
}
