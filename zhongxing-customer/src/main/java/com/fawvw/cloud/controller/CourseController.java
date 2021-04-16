package com.fawvw.cloud.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fawvw.cloud.common.api.CourseApi;
import com.fawvw.cloud.common.api.dto.CourseDTO;
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
 * @create 2021-01-27 21:04
 **/
@Slf4j
@Controller
@RequestMapping("/course")
public class CourseController {

    @Reference(version = "${dubbo.provider.version}", application = "${dubbo.application.id}")
    private CourseApi courseApi;

    @Value("${web.upload-path}")
    private String uploadPath;

    @RequestMapping("/list")
    @ResponseBody
    public List<CourseDTO> listCourse(Long structureId){
        List<CourseDTO> courseDTOS = new ArrayList<>();

        ApiResult<List<CourseDTO>> apiResult = courseApi.listCourse(structureId);
        if(apiResult != null){
            if(apiResult.isSuccess()){
                courseDTOS = apiResult.getData();
            }else {
                log.error(apiResult.getErrorCode() + ":" + apiResult.getErrorMsg());
            }
        }

        return courseDTOS;
    }

    @RequestMapping("/upload")
    @ResponseBody
    public String upload(CourseDTO courseDTO, MultipartFile file1, MultipartFile file2, HttpServletRequest request, HttpSession session){
        String result = "0";

        Long createBy = 0L;
        if(session != null && session.getAttribute("staff") != null){
            createBy = ((StaffDTO)session.getAttribute("staff")).getStaffId();
        }
        courseDTO.setCreateBy(createBy);
        if(file1 == null || file1.isEmpty() || file2 == null || file2.isEmpty()){
            result = "2";
        }else{
            try {
                courseDTO.setVideo(ControllerUtil.fileUpload(uploadPath,file1,request));
                courseDTO.setCover(ControllerUtil.fileUpload(uploadPath,file2,request));
            } catch (IOException e) {
                log.error(e.getMessage());
                result = "1";
            }
        }
        if("0".equals(result)){
            ApiResult<String> apiResult = courseApi.upload(courseDTO);
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
    public String edit(CourseDTO courseDTO, MultipartFile file1, MultipartFile file2, HttpServletRequest request, HttpSession session){
        String result = "0";

        Long lastUpdateBy = 0L;
        if(session != null && session.getAttribute("staff") != null){
            lastUpdateBy = ((StaffDTO)session.getAttribute("staff")).getStaffId();
        }
        courseDTO.setLastUpdateBy(lastUpdateBy);
        if(file1 != null && !file1.isEmpty()){
            try {
                courseDTO.setVideo(ControllerUtil.fileUpload(uploadPath,file1,request));
            } catch (IOException e) {
                log.error(e.getMessage());
                result = "1";
            }
        }
        if(file2 != null && !file2.isEmpty()){
            try {
                courseDTO.setCover(ControllerUtil.fileUpload(uploadPath,file2,request));
            } catch (IOException e) {
                log.error(e.getMessage());
                result = "1";
            }
        }

        if("0".equals(result)){
            ApiResult<String> apiResult = courseApi.edit(courseDTO);
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
    public String delete(Long courseId,HttpSession session){
        String result = "0";

        Long lastUpdateBy = 0L;
        if(session != null && session.getAttribute("staff") != null){
            lastUpdateBy = ((StaffDTO)session.getAttribute("staff")).getStaffId();
        }
        ApiResult<String> apiResult = courseApi.delete(courseId, lastUpdateBy);
        if(apiResult == null){
            result = "1";
        } else if(!apiResult.isSuccess()){
            log.error(apiResult.getErrorCode() + ":" +apiResult.getErrorMsg());
            result = "1";
        }

        return result;
    }

    @RequestMapping("/topCourse")
    @ResponseBody
    public List<CourseDTO> topCourse(){
        List<CourseDTO> courseDTOS = new ArrayList<>();

        ApiResult<List<CourseDTO>> apiResult = courseApi.topCourse();
        if(apiResult != null){
            if(apiResult.isSuccess()){
                courseDTOS = apiResult.getData();
            }else {
                log.error(apiResult.getErrorCode() + ":" + apiResult.getErrorMsg());
            }
        }

        return courseDTOS;
    }

    @RequestMapping("/topTeacher")
    @ResponseBody
    public List<CourseDTO> topTeacher(){
        List<CourseDTO> courseDTOS = new ArrayList<>();

        ApiResult<List<CourseDTO>> apiResult = courseApi.topTeacher();
        if(apiResult != null){
            if(apiResult.isSuccess()){
                courseDTOS = apiResult.getData();
            }else {
                log.error(apiResult.getErrorCode() + ":" + apiResult.getErrorMsg());
            }
        }

        return courseDTOS;
    }
}
