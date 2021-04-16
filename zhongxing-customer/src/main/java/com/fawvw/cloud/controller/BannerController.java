package com.fawvw.cloud.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fawvw.cloud.common.api.BannerApi;
import com.fawvw.cloud.common.api.FlowerApi;
import com.fawvw.cloud.common.api.dto.BannerDTO;
import com.fawvw.cloud.common.api.dto.FlowerPicDTO;
import com.fawvw.cloud.common.api.dto.StaffDTO;
import com.fawvw.cloud.common.api.result.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author HuangGang
 * @create 2021-03-31 18:49
 **/
@Slf4j
@Controller
@RequestMapping("/banner")
public class BannerController {
    @Reference(version = "${dubbo.provider.version}", application = "${dubbo.application.id}")
    private BannerApi bannerApi;

    @Value("${web.upload-path}")
    public String uploadPath;

    @RequestMapping("/listBanner")
    @ResponseBody
    public List<BannerDTO> listBanner() {
        List<BannerDTO> bannerDTOS = new ArrayList<>();

        ApiResult<List<BannerDTO>> apiResult = bannerApi.listBanner();
        if (apiResult != null) {
            if (apiResult.isSuccess()) {
                bannerDTOS = apiResult.getData();
            } else {
                log.error(apiResult.getErrorCode() + ":" + apiResult.getErrorMsg());
            }
        }

        return bannerDTOS;
    }

    @RequestMapping("/upload")
    @ResponseBody
    public String upload(Long bannerId, MultipartFile file, HttpServletRequest request, HttpSession session) {
        String result = "0";
        Long createBy = 0L;
        if(session != null && session.getAttribute("staff") != null){
            createBy = ((StaffDTO)session.getAttribute("staff")).getStaffId();
        }
        BannerDTO bannerDTO = new BannerDTO();
        bannerDTO.setBannerId(bannerId);
        bannerDTO.setCreateBy(createBy);
        try {
            String pic = ControllerUtil.fileUpload(uploadPath,file, request);
            bannerDTO.setPic(pic);
        } catch (IOException e) {
            log.error(e.getMessage());
            result = "1";
        }

        if("0".equals(result)){
            ApiResult<String> apiResult = bannerApi.upload(bannerDTO);
            if(apiResult == null){
                result = "1";
            } else if(apiResult.isSuccess()){
                result = apiResult.getData();
            } else{
                log.error(apiResult.getErrorCode() + ":" +apiResult.getErrorMsg());
                result = "1";
            }
        }

        return result;
    }

    @RequestMapping("/delete")
    @ResponseBody
    public String delete(Long bannerId) {
        String result = "0";

        ApiResult<String> apiResult = bannerApi.delete(bannerId);
        if(apiResult == null){
            result = "1";
        } else if(!apiResult.isSuccess()){
            log.error(apiResult.getErrorCode() + ":" +apiResult.getErrorMsg());
            result = "1";
        }

        return result;
    }
}
