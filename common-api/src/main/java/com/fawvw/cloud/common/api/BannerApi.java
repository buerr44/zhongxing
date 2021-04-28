package com.fawvw.cloud.common.api;

import com.fawvw.cloud.common.api.dto.BannerDTO;
import com.fawvw.cloud.common.api.result.ApiResult;

import java.util.List;

/**
 * @author HuangGang
 * @create 2021-03-31 18:51
 **/
public interface BannerApi {
    /**
     * 获取所有banner图
     * @return
     */
    ApiResult<List<BannerDTO>> listBanner();

    /**
     * 上传banner图
     * @param bannerDTO
     * @return
     */
    ApiResult<String> upload(BannerDTO bannerDTO);

    /**
     * 删除banner图
     * @param bannerId
     * @return
     */
    ApiResult<String> delete(Long bannerId);

    /**
     * 更新链接
     * @param bannerId
     * @param link
     * @param lastUpdateBy
     * @return
     */
    ApiResult<String> uploadlink(Long bannerId, String link, Long lastUpdateBy);
}
