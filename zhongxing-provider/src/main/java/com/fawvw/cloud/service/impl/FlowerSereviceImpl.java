package com.fawvw.cloud.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.fawvw.cloud.common.api.FlowerApi;
import com.fawvw.cloud.common.api.dto.FlowerPicDTO;
import com.fawvw.cloud.common.api.dto.FlowerWordDTO;
import com.fawvw.cloud.common.api.result.ApiResult;
import com.fawvw.cloud.entity.FlowerPic;
import com.fawvw.cloud.entity.FlowerWord;
import com.fawvw.cloud.mapper.FlowerPicMapper;
import com.fawvw.cloud.mapper.FlowerWordMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author HuangGang
 * @create 2021-03-31 10:55
 **/
@Slf4j
@Service(version = "${dubbo.provider.version}", application = "${dubbo.application.id}")
public class FlowerSereviceImpl implements FlowerApi {

    @Autowired
    private FlowerPicMapper flowerPicMapper;
    @Autowired
    private FlowerWordMapper flowerWordMapper;

    @Override
    public ApiResult<List<FlowerPicDTO>> listPic() {
        ApiResult<List<FlowerPicDTO>> apiResult;

        try {
            List<FlowerPicDTO> flowerPicDTOS = flowerPicMapper.listPic();
            apiResult = ApiResult.success(flowerPicDTOS);
        } catch (Exception e){
            log.error(e.getMessage());
            apiResult = ApiResult.error("B0300",e.getMessage());
        }

        return apiResult;
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public ApiResult<String> uploadPic(FlowerPicDTO flowerPicDTO) {
        ApiResult<String> apiResult;

        FlowerPic flowerPic = new FlowerPic();
        flowerPic.setFlowerPicId(flowerPicDTO.getFlowerPicId());
        flowerPic.setPic(flowerPicDTO.getPic());
        flowerPic.setCreateBy(flowerPicDTO.getCreateBy());
        flowerPic.setCreateDate(new Date());
        try {
            FlowerPic flowerPic1 = flowerPicMapper.selectById(flowerPicDTO.getFlowerPicId());
            if (flowerPic1 == null) {
                flowerPicMapper.insert(flowerPic);
            } else {
                flowerPicMapper.updateById(flowerPic);
            }
            FlowerPic flowerPic2 =  flowerPicMapper.selectById(flowerPicDTO.getFlowerPicId());
            apiResult = ApiResult.success(flowerPic2.getPic());
        }catch (Exception e){
            log.error(e.getMessage());
            apiResult = ApiResult.error("B0300",e.getMessage());
        }

        return apiResult;
    }

    @Override
    public ApiResult<String> deletePic(Long flowerPicId) {
        ApiResult<String> apiResult;

        try {
            flowerPicMapper.deleteById(flowerPicId);
            apiResult = ApiResult.success();
        } catch (Exception e){
            log.error(e.getMessage());
            apiResult = ApiResult.error("B0300",e.getMessage());
        }

        return apiResult;
    }

    @Override
    public ApiResult<List<FlowerWordDTO>> listWord() {
        ApiResult<List<FlowerWordDTO>> apiResult;

        try {
            List<FlowerWordDTO> flowerWordDTOS = flowerWordMapper.listWord();
            apiResult = ApiResult.success(flowerWordDTOS);
        } catch (Exception e){
            log.error(e.getMessage());
            apiResult = ApiResult.error("B0300",e.getMessage());
        }

        return apiResult;
    }

    @Override
    public ApiResult<String> uploadWord(FlowerWordDTO flowerWordDTO) {
        ApiResult<String> apiResult;

        FlowerWord flowerWord = new FlowerWord();
        flowerWord.setHalf1(flowerWordDTO.getHalf1());
        flowerWord.setHalf2(flowerWordDTO.getHalf2());
        flowerWord.setCreateBy(flowerWordDTO.getCreateBy());
        flowerWord.setCreateDate(new Date());
        try {
            flowerWordMapper.insert(flowerWord);
            apiResult = ApiResult.success();
        }catch (Exception e){
            log.error(e.getMessage());
            apiResult = ApiResult.error("B0300",e.getMessage());
        }

        return apiResult;
    }

    @Override
    public ApiResult<String> editWord(FlowerWordDTO flowerWordDTO) {
        ApiResult<String> apiResult;

        FlowerWord flowerWord = new FlowerWord();
        flowerWord.setFlowerWordId(flowerWordDTO.getFlowerWordId());
        flowerWord.setHalf1(flowerWordDTO.getHalf1());
        flowerWord.setHalf2(flowerWordDTO.getHalf2());
        flowerWord.setLastUpdateBy(flowerWordDTO.getLastUpdateBy());
        flowerWord.setLastUpdateDate(new Date());
        try {
            flowerWordMapper.updateById(flowerWord);
            apiResult = ApiResult.success();
        }catch (Exception e){
            log.error(e.getMessage());
            apiResult = ApiResult.error("B0300",e.getMessage());
        }

        return apiResult;
    }

    @Override
    public ApiResult<String> deleteWord(Long flowerWordId,Long lastUpdateBy) {
        ApiResult<String> apiResult;

        UpdateWrapper<FlowerWord> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("is_valid","-1").set("last_update_by",lastUpdateBy).eq("flowerword_id",flowerWordId);
        try {
            flowerWordMapper.update(null,updateWrapper);
            apiResult = ApiResult.success();
        }catch (Exception e){
            log.error(e.getMessage());
            apiResult = ApiResult.error("B0300",e.getMessage());
        }

        return apiResult;
    }
}
