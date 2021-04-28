package com.fawvw.cloud.common.api;

import com.fawvw.cloud.common.api.dto.PopupDTO;
import com.fawvw.cloud.common.api.result.ApiResult;

/**
 * @author HuangGang
 * @create 2021-04-26 14:20
 **/
public interface PopupApi {
    /**
     * 获取弹窗信息
     * @return
     */
    ApiResult<PopupDTO> getPopupInfo();

    /**
     * 弹窗图片上传
     * @param pic
     * @param lastUpdateBy
     * @return
     */
    ApiResult<String> uploadPic(String pic, Long lastUpdateBy);

    /**
     * 删除弹窗图片
     * @return
     */
    ApiResult<String> deletePic(Long lastUpdateBy);

    /**
     * 更改弹窗开关状态
     * @param isValid
     * @return
     */
    ApiResult<String> changeStatus(Integer status,Long lastUpdateBy);

    /**
     * 上传超链接
     * @param url
     * @return
     */
    ApiResult<String> uploadUrl(String url,Long lastUpdateBy);
}
