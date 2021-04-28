package com.fawvw.cloud.common.api.dto;

import lombok.Data;

/**
 * @author HuangGang
 * @create 2021-04-26 14:21
 **/
@Data
public class PopupDTO extends BaseDTO{
    private Long popupId;
    private String url;
    private String pic;
}
