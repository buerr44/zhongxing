package com.fawvw.cloud.common.api;

import com.fawvw.cloud.common.api.dto.FlowerPicDTO;
import com.fawvw.cloud.common.api.dto.FlowerWordDTO;
import com.fawvw.cloud.common.api.result.ApiResult;

import java.util.List;

/**
 * @author HuangGang
 * @create 2021-03-31 10:34
 **/
public interface FlowerApi {
    /**
     * 获取所有底片
     * @return
     */
    ApiResult<List<FlowerPicDTO>> listPic();

    /**
     * 上传底图
     * @param flowerPicDTO
     * @return
     */
    ApiResult<String> uploadPic(FlowerPicDTO flowerPicDTO);

    /**
     * 删除底图
     * @param flowerPicId
     * @return
     */
    ApiResult<String> deletePic(Long flowerPicId);

    /**
     * 获取所有文案
     * @return
     */
    ApiResult<List<FlowerWordDTO>> listWord();

    /**
     * 上传文案
     * @param flowerWordDTO
     * @return
     */
    ApiResult<String> uploadWord(FlowerWordDTO flowerWordDTO);

    /**
     * 编辑文案
     * @param flowerWordDTO
     * @return
     */
    ApiResult<String> editWord(FlowerWordDTO flowerWordDTO);

    /**
     * 删除文案
     * @param flowerWordId
     * @return
     */
    ApiResult<String> deleteWord(Long flowerWordId,Long lastUpdateBy);
}
