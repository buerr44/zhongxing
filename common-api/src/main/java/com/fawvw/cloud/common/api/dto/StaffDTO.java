package com.fawvw.cloud.common.api.dto;

import lombok.Data;

/**
 * @author HuangGang
 * @create 2021-02-02 20:34
 **/
@Data
public class StaffDTO extends BaseDTO{

    private Long staffId;
    private String staffName;
    private String password;
    private Integer sex;
    private Integer role;
}
