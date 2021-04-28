package com.fawvw.cloud.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fawvw.cloud.common.api.PopupApi;
import com.fawvw.cloud.common.api.dto.BannerDTO;
import com.fawvw.cloud.common.api.dto.PopupDTO;
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
 * @create 2021-04-26 14:20
 **/
@Slf4j
@Controller
@RequestMapping("/popup")
public class PopupController {
    @Reference(version = "${dubbo.provider.version}", application = "${dubbo.application.id}")
    private PopupApi popupApi;

    @Value("${web.upload-path}")
    public String uploadPath;

    @RequestMapping("/getPopupInfo")
    @ResponseBody
    public PopupDTO getPopupInfo() {
        PopupDTO popupDTO = new PopupDTO();

        ApiResult<PopupDTO> apiResult = popupApi.getPopupInfo();
        if (apiResult != null) {
            if (apiResult.isSuccess()) {
                popupDTO = apiResult.getData();
            } else {
                log.error(apiResult.getErrorCode() + ":" + apiResult.getErrorMsg());
            }
        }

        return popupDTO;
    }

    @RequestMapping("/uploadPic")
    @ResponseBody
    public String uploadPic(MultipartFile file, HttpServletRequest request, HttpSession session) {
        String result = "0";
        Long lastUpdateBy = 0L;
        if(session != null && session.getAttribute("staff") != null){
            lastUpdateBy = ((StaffDTO)session.getAttribute("staff")).getStaffId();
        }
        String pic = "";
        try {
            pic = ControllerUtil.fileUpload(uploadPath,file, request);
        } catch (IOException e) {
            log.error(e.getMessage());
            result = "1";
        }

        if("0".equals(result)){
            ApiResult<String> apiResult = popupApi.uploadPic(pic,lastUpdateBy);
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

    @RequestMapping("/deletePic")
    @ResponseBody
    public String deletePic(HttpSession session) {
        String result = "0";

        Long lastUpdateBy = 0L;
        if(session != null && session.getAttribute("staff") != null){
            lastUpdateBy = ((StaffDTO)session.getAttribute("staff")).getStaffId();
        }
        ApiResult<String> apiResult = popupApi.deletePic(lastUpdateBy);
        if(apiResult == null){
            result = "1";
        } else if(!apiResult.isSuccess()){
            log.error(apiResult.getErrorCode() + ":" +apiResult.getErrorMsg());
            result = "1";
        }

        return result;
    }

    @RequestMapping("/changeStatus")
    @ResponseBody
    public String changeStatus(Integer status,HttpSession session) {
        String result = "0";

        Long lastUpdateBy = 0L;
        if(session != null && session.getAttribute("staff") != null){
            lastUpdateBy = ((StaffDTO)session.getAttribute("staff")).getStaffId();
        }
        ApiResult<String> apiResult = popupApi.changeStatus(status,lastUpdateBy);
        if(apiResult == null){
            result = "1";
        } else if(!apiResult.isSuccess()){
            log.error(apiResult.getErrorCode() + ":" +apiResult.getErrorMsg());
            result = "1";
        }

        return result;
    }

    @RequestMapping("/uploadUrl")
    @ResponseBody
    public String uploadUrl(String url, HttpSession session) {
        String result = "0";

        Long lastUpdateBy = 0L;
        if(session != null && session.getAttribute("staff") != null){
            lastUpdateBy = ((StaffDTO)session.getAttribute("staff")).getStaffId();
        }
        ApiResult<String> apiResult = popupApi.uploadUrl(url,lastUpdateBy);
        if(apiResult == null){
            result = "1";
        } else if(!apiResult.isSuccess()){
            log.error(apiResult.getErrorCode() + ":" +apiResult.getErrorMsg());
            result = "1";
        }

        return result;
    }
}
