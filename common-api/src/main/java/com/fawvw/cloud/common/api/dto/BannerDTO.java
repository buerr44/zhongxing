package com.fawvw.cloud.common.api.dto;

import lombok.Data;

/**
 * @author HuangGang
 * @create 2021-03-31 18:50
 **/
@Data
public class BannerDTO extends BaseDTO{
    private Long bannerId;
    private String pic;
}
