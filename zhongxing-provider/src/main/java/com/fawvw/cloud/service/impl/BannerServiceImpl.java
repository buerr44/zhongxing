package com.fawvw.cloud.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.fawvw.cloud.common.api.BannerApi;
import com.fawvw.cloud.common.api.dto.BannerDTO;
import com.fawvw.cloud.common.api.dto.FlowerPicDTO;
import com.fawvw.cloud.common.api.result.ApiResult;
import com.fawvw.cloud.entity.Banner;
import com.fawvw.cloud.entity.FlowerPic;
import com.fawvw.cloud.mapper.BannerMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author HuangGang
 * @create 2021-03-31 19:01
 **/
@Slf4j
@Service(version = "${dubbo.provider.version}", application = "${dubbo.application.id}")
public class BannerServiceImpl implements BannerApi {
    @Autowired
    private BannerMapper bannerMapper;

    @Override
    public ApiResult<List<BannerDTO>> listBanner() {
        ApiResult<List<BannerDTO>> apiResult;

        try {
            List<BannerDTO> bannerDTOS = bannerMapper.listBanner();
            apiResult = ApiResult.success(bannerDTOS);
        } catch (Exception e){
            log.error(e.getMessage());
            apiResult = ApiResult.error("B0300",e.getMessage());
        }

        return apiResult;
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public ApiResult<String> upload(BannerDTO bannerDTO) {
        ApiResult<String> apiResult;

        Banner banner = new Banner();
        banner.setBannerId(bannerDTO.getBannerId());
        banner.setPic(bannerDTO.getPic());
        banner.setSequence(bannerDTO.getBannerId().intValue());
        banner.setCreateBy(bannerDTO.getCreateBy());
        banner.setCreateDate(new Date());
        try {
            Banner banner1= bannerMapper.selectById(bannerDTO.getBannerId());
            if (banner1 == null) {
                bannerMapper.insert(banner);
            } else {
                bannerMapper.updateById(banner);
            }
            Banner banner2 =  bannerMapper.selectById(bannerDTO.getBannerId());
            apiResult = ApiResult.success(banner2.getPic());
        }catch (Exception e){
            log.error(e.getMessage());
            apiResult = ApiResult.error("B0300",e.getMessage());
        }

        return apiResult;
    }

    @Override
    public ApiResult<String> delete(Long bannerId) {
        ApiResult<String> apiResult;

        try {
            bannerMapper.deleteById(bannerId);
            apiResult = ApiResult.success();
        } catch (Exception e){
            log.error(e.getMessage());
            apiResult = ApiResult.error("B0300",e.getMessage());
        }

        return apiResult;
    }
}
