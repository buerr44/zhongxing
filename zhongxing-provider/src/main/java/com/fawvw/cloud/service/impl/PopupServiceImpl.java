package com.fawvw.cloud.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.fawvw.cloud.common.api.PopupApi;
import com.fawvw.cloud.common.api.dto.BannerDTO;
import com.fawvw.cloud.common.api.dto.PopupDTO;
import com.fawvw.cloud.common.api.result.ApiResult;
import com.fawvw.cloud.entity.Popup;
import com.fawvw.cloud.mapper.PopupMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;


/**
 * @author HuangGang
 * @create 2021-04-26 14:46
 **/
@Slf4j
@Service(version = "${dubbo.provider.version}", application = "${dubbo.application.id}")
public class PopupServiceImpl implements PopupApi {
    @Autowired
    private PopupMapper popupMapper;

    @Override
    public ApiResult<PopupDTO> getPopupInfo() {
        ApiResult<PopupDTO> apiResult;

        try {
            Popup popup = popupMapper.selectById(1);
            PopupDTO popupDTO = new PopupDTO();
            if(popup != null){
                popupDTO.setUrl(popup.getUrl());
                popupDTO.setPic(popup.getPic());
                popupDTO.setStatus(popup.getStatus());
            }
            apiResult = ApiResult.success(popupDTO);
        } catch (Exception e){
            log.error(e.getMessage());
            apiResult = ApiResult.error("B0300",e.getMessage());
        }

        return apiResult;
    }

    @Override
    public ApiResult<String> uploadPic(String pic, Long lastUpdateBy) {
        ApiResult<String> apiResult;

        UpdateWrapper<Popup> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("pic",pic);
        updateWrapper.set("last_update_by",lastUpdateBy);
        updateWrapper.set("last_update_date",new Date());
        updateWrapper.eq("popup_id",1);
        try {
            popupMapper.update(null,updateWrapper);
            apiResult = ApiResult.success(pic);
        } catch (Exception e){
            log.error(e.getMessage());
            apiResult = ApiResult.error("B0300",e.getMessage());
        }

        return apiResult;
    }

    @Override
    public ApiResult<String> deletePic(Long lastUpdateBy) {
        ApiResult<String> apiResult;

        UpdateWrapper<Popup> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("pic","");
        updateWrapper.set("last_update_by",lastUpdateBy);
        updateWrapper.set("last_update_date",new Date());
        updateWrapper.eq("popup_id",1);
        try {
            popupMapper.update(null,updateWrapper);
            apiResult = ApiResult.success();
        } catch (Exception e){
            log.error(e.getMessage());
            apiResult = ApiResult.error("B0300",e.getMessage());
        }

        return apiResult;
    }

    @Override
    public ApiResult<String> changeStatus(Integer status,Long lastUpdateBy) {
        ApiResult<String> apiResult;

        UpdateWrapper<Popup> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("status",status);
        updateWrapper.set("last_update_by",lastUpdateBy);
        updateWrapper.set("last_update_date",new Date());
        updateWrapper.eq("popup_id",1);
        try {
            popupMapper.update(null,updateWrapper);
            apiResult = ApiResult.success();
        } catch (Exception e){
            log.error(e.getMessage());
            apiResult = ApiResult.error("B0300",e.getMessage());
        }

        return apiResult;
    }

    @Override
    public ApiResult<String> uploadUrl(String url,Long lastUpdateBy) {
        ApiResult<String> apiResult;

        UpdateWrapper<Popup> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("url",url);
        updateWrapper.set("last_update_by",lastUpdateBy);
        updateWrapper.set("last_update_date",new Date());
        updateWrapper.eq("popup_id",1);
        try {
            popupMapper.update(null,updateWrapper);
            apiResult = ApiResult.success();
        } catch (Exception e){
            log.error(e.getMessage());
            apiResult = ApiResult.error("B0300",e.getMessage());
        }

        return apiResult;
    }
}
