package com.fawvw.cloud.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fawvw.cloud.common.api.StructureApi;
import com.fawvw.cloud.common.api.dto.StaffDTO;
import com.fawvw.cloud.common.api.dto.StructureDTO;
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
 * @create 2021-01-28 20:23
 **/
@Slf4j
@Controller
@RequestMapping("/structure")
public class StructureController {

    @Reference(version = "${dubbo.provider.version}", application = "${dubbo.application.id}")
    private StructureApi structureApi;

    @Value("${web.upload-path}")
    public String uploadPath;

    @RequestMapping("/listByPid")
    @ResponseBody
    public List<StructureDTO> listByPid(Long pid) {
        List<StructureDTO> structureDTOS = new ArrayList<>();

        ApiResult<List<StructureDTO>> apiResult = structureApi.listByPid(pid);
        if (apiResult != null) {
            if (apiResult.isSuccess()) {
                structureDTOS = apiResult.getData();
            } else {
                log.error(apiResult.getErrorCode() + ":" + apiResult.getErrorMsg());
            }
        }

        return structureDTOS;
    }

    @RequestMapping("/upload")
    @ResponseBody
    public String upload(StructureDTO structureDTO, Long pid1, Long pid2, MultipartFile file1, MultipartFile file2, HttpServletRequest request, HttpSession session) {
        String result = "0";

        Long createBy = 0L;
        if(session != null && session.getAttribute("staff") != null){
           createBy = ((StaffDTO)session.getAttribute("staff")).getStaffId();
        }
        structureDTO.setCreateBy(createBy);

        if (structureDTO !=null && structureDTO.getStructureType() == 1 && file1 != null && file2 != null) {
            //添加中类
            structureDTO.setPid(pid1);
            try {
                String pic1 = ControllerUtil.fileUpload(uploadPath,file1, request);
                String pic2 = ControllerUtil.fileUpload(uploadPath,file2, request);
                structureDTO.setPic1(pic1);
                structureDTO.setPic2(pic2);
            } catch (IOException e) {
                log.error(e.getMessage());
                result = "1";
            }
        } else if (structureDTO !=null && structureDTO.getStructureType() == 2 && file1 != null) {
            //添加小类
            structureDTO.setPid(pid2);
            try {
                String pic1 = ControllerUtil.fileUpload(uploadPath,file1, request);
                structureDTO.setPic1(pic1);
            } catch (IOException e) {
                log.error(e.getMessage());
                result = "1";
            }
        } else {
            result = "1";
        }

        if ("0".equals(result)) {
            ApiResult<String> apiResult = structureApi.upload(structureDTO);
            if(apiResult == null){
                result = "1";
            } else if(!apiResult.isSuccess()){
                log.error(apiResult.getErrorCode() + ":" +apiResult.getErrorMsg());
                result = "1";
            }
        }

        return result;
    }

    @RequestMapping("/edit")
    @ResponseBody
    public String edit(StructureDTO structureDTO, Long pid1, Long pid2, MultipartFile file1, MultipartFile file2, HttpServletRequest request, HttpSession session) {
        String result = "0";

        Long lastUpdateBy = 0L;
        if(session != null && session.getAttribute("staff") != null){
            lastUpdateBy = ((StaffDTO)session.getAttribute("staff")).getStaffId();
        }
        structureDTO.setLastUpdateBy(lastUpdateBy);

        if(file1 != null && !file1.isEmpty()){
            try {
                structureDTO.setPic1(ControllerUtil.fileUpload(uploadPath,file1,request));
            } catch (IOException e) {
                log.error(e.getMessage());
                result = "1";
            }
        }
        if(file2 != null && !file2.isEmpty()){
            try {
                structureDTO.setPic2(ControllerUtil.fileUpload(uploadPath,file2,request));
            } catch (IOException e) {
                log.error(e.getMessage());
                result = "1";
            }
        }
        if(structureDTO.getStructureType() == 1){
            structureDTO.setPid(pid1);
        }else if(structureDTO.getStructureType() == 2){
            structureDTO.setPid(pid2);
        }else{
            result = "1";
        }
        if ("0".equals(result)) {
            ApiResult<String> apiResult = structureApi.edit(structureDTO);
            if(apiResult == null){
                result = "1";
            } else if(!apiResult.isSuccess()){
                log.error(apiResult.getErrorCode() + ":" +apiResult.getErrorMsg());
                result = "1";
            }
        }
        return result;
    }

    @RequestMapping("/delete")
    @ResponseBody
    public String delete(Long structureId,HttpSession session){
        String result = "0";

        Long lastUpdateBy = 0L;
        if(session != null && session.getAttribute("staff") != null){
            lastUpdateBy = ((StaffDTO)session.getAttribute("staff")).getStaffId();
        }
        ApiResult<String> apiResult = structureApi.delete(structureId, lastUpdateBy);
        if(apiResult == null){
            result = "1";
        } else if(!apiResult.isSuccess()){
            log.error(apiResult.getErrorCode() + ":" +apiResult.getErrorMsg());
            result = "1";
        }

        return result;
    }

    @RequestMapping("/editSequence")
    @ResponseBody
    public String editSequence(Long structureId,Integer sequence,HttpSession session){
        String result = "0";

        Long lastUpdateBy = 0L;
        if(session != null && session.getAttribute("staff") != null){
            lastUpdateBy = ((StaffDTO)session.getAttribute("staff")).getStaffId();
        }
        ApiResult<String> apiResult = structureApi.editSequence(structureId,sequence,lastUpdateBy);
        if(apiResult == null){
            result = "1";
        } else if(!apiResult.isSuccess()){
            log.error(apiResult.getErrorCode() + ":" +apiResult.getErrorMsg());
            result = "1";
        }

        return result;
    }
}
