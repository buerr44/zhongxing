package com.fawvw.cloud.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.fawvw.cloud.common.api.FlowerApi;
import com.fawvw.cloud.common.api.dto.FlowerPicDTO;
import com.fawvw.cloud.common.api.dto.FlowerWordDTO;
import com.fawvw.cloud.common.api.dto.StaffDTO;
import com.fawvw.cloud.common.api.dto.StudyPlanDTO;
import com.fawvw.cloud.common.api.result.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
 * @create 2021-03-31 10:31
 **/
@Slf4j
@Controller
@RequestMapping("/flower")
public class FlowerController {

    @Reference(version = "${dubbo.provider.version}", application = "${dubbo.application.id}")
    private FlowerApi flowerApi;

    @Value("${web.upload-path}")
    public String uploadPath;

    @RequestMapping("/listPic")
    @ResponseBody
    public List<FlowerPicDTO> listPic() {
        List<FlowerPicDTO> flowerPicDTOS = new ArrayList<>();

        ApiResult<List<FlowerPicDTO>> apiResult = flowerApi.listPic();
        if (apiResult != null) {
            if (apiResult.isSuccess()) {
                flowerPicDTOS = apiResult.getData();
            } else {
                log.error(apiResult.getErrorCode() + ":" + apiResult.getErrorMsg());
            }
        }

        return flowerPicDTOS;
    }

    @RequestMapping("/uploadPic")
    @ResponseBody
    public String uploadPic(Long flowerPicId, MultipartFile file, HttpServletRequest request, HttpSession session) {
        String result = "0";
        Long createBy = 0L;
        if(session != null && session.getAttribute("staff") != null){
            createBy = ((StaffDTO)session.getAttribute("staff")).getStaffId();
        }
        FlowerPicDTO flowerPicDTO = new FlowerPicDTO();
        flowerPicDTO.setFlowerPicId(flowerPicId);
        flowerPicDTO.setCreateBy(createBy);
        try {
            String pic = ControllerUtil.fileUpload(uploadPath,file, request);
            flowerPicDTO.setPic(pic);
        } catch (IOException e) {
            log.error(e.getMessage());
            result = "1";
        }

        if("0".equals(result)){
            ApiResult<String> apiResult = flowerApi.uploadPic(flowerPicDTO);
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
    public String deletePic(Long flowerPicId) {
        String result = "0";

        ApiResult<String> apiResult = flowerApi.deletePic(flowerPicId);
        if(apiResult == null){
            result = "1";
        } else if(!apiResult.isSuccess()){
            log.error(apiResult.getErrorCode() + ":" +apiResult.getErrorMsg());
            result = "1";
        }

        return result;
    }

    @RequestMapping("/listWord")
    @ResponseBody
    public List<FlowerWordDTO> listWord() {
        List<FlowerWordDTO> flowerWordDTOs = new ArrayList<>();

        ApiResult<List<FlowerWordDTO>> apiResult = flowerApi.listWord();
        if (apiResult != null) {
            if (apiResult.isSuccess()) {
                flowerWordDTOs = apiResult.getData();
            } else {
                log.error(apiResult.getErrorCode() + ":" + apiResult.getErrorMsg());
            }
        }

        return flowerWordDTOs;
    }

    @RequestMapping("/uploadWord")
    @ResponseBody
    public String uploadWord(FlowerWordDTO flowerWordDTO, HttpSession session) {
        String result = "0";
        Long createBy = 0L;
        if(session != null && session.getAttribute("staff") != null){
            createBy = ((StaffDTO)session.getAttribute("staff")).getStaffId();
        }
        if(flowerWordDTO != null){
            flowerWordDTO.setCreateBy(createBy);
            ApiResult<String> apiResult = flowerApi.uploadWord(flowerWordDTO);
            if(apiResult == null){
                result = "1";
            } else if(!apiResult.isSuccess()){
                log.error(apiResult.getErrorCode() + ":" +apiResult.getErrorMsg());
                result = "1";
            }
        }

        return result;
    }

    @RequestMapping("/editWord")
    @ResponseBody
    public String editWord(FlowerWordDTO flowerWordDTO, HttpSession session) {
        String result = "0";
        Long lastUpdateBy = 0L;
        if(session != null && session.getAttribute("staff") != null){
            lastUpdateBy = ((StaffDTO)session.getAttribute("staff")).getStaffId();
        }
        if(flowerWordDTO != null){
            flowerWordDTO.setLastUpdateBy(lastUpdateBy);
            ApiResult<String> apiResult = flowerApi.editWord(flowerWordDTO);
            if(apiResult == null){
                result = "1";
            } else if(!apiResult.isSuccess()){
                log.error(apiResult.getErrorCode() + ":" +apiResult.getErrorMsg());
                result = "1";
            }
        }

        return result;
    }

    @RequestMapping("/deleteWord")
    @ResponseBody
    public String deleteWord(Long flowerWordId,HttpSession session) {
        String result = "0";
        Long lastUpdateBy = 0L;
        if(session != null && session.getAttribute("staff") != null){
            lastUpdateBy = ((StaffDTO)session.getAttribute("staff")).getStaffId();
        }
        ApiResult<String> apiResult = flowerApi.deleteWord(flowerWordId,lastUpdateBy);
        if(apiResult == null){
            result = "1";
        } else if(!apiResult.isSuccess()){
            log.error(apiResult.getErrorCode() + ":" +apiResult.getErrorMsg());
            result = "1";
        }

        return result;
    }

}
