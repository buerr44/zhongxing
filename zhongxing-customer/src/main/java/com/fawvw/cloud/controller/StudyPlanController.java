package com.fawvw.cloud.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fawvw.cloud.common.api.StructureApi;
import com.fawvw.cloud.common.api.StudyPlanApi;
import com.fawvw.cloud.common.api.dto.PlanCourseDTO;
import com.fawvw.cloud.common.api.dto.StaffDTO;
import com.fawvw.cloud.common.api.dto.StructureDTO;
import com.fawvw.cloud.common.api.dto.StudyPlanDTO;
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
 * @create 2021-03-29 9:09
 **/

@Slf4j
@Controller
@RequestMapping("/studyPlan")
public class StudyPlanController {
    @Reference(version = "${dubbo.provider.version}", application = "${dubbo.application.id}")
    private StudyPlanApi studyPlanApi;

    @Value("${web.upload-path}")
    public String uploadPath;

    @RequestMapping("/listStudyPlan")
    @ResponseBody
    public List<StudyPlanDTO> listStudyPlan() {
        List<StudyPlanDTO> studyPlanDTOS = new ArrayList<>();

        ApiResult<List<StudyPlanDTO>> apiResult = studyPlanApi.listStudyPlan();
        if (apiResult != null) {
            if (apiResult.isSuccess()) {
                studyPlanDTOS = apiResult.getData();
            } else {
                log.error(apiResult.getErrorCode() + ":" + apiResult.getErrorMsg());
            }
        }

        return studyPlanDTOS;
    }


    @RequestMapping("/listPlanCourse")
    @ResponseBody
    public List<PlanCourseDTO> listPlanCourse(Long studyPlanId) {
        List<PlanCourseDTO> planCourseDTOS = new ArrayList<>();

        if(studyPlanId != 0) {
            ApiResult<List<PlanCourseDTO>> apiResult = studyPlanApi.listPlanCourse(studyPlanId);
            if (apiResult != null) {
                if (apiResult.isSuccess()) {
                    planCourseDTOS = apiResult.getData();
                } else {
                    log.error(apiResult.getErrorCode() + ":" + apiResult.getErrorMsg());
                }
            }
        }

        return planCourseDTOS;
    }

    @RequestMapping("/editSequence")
    @ResponseBody
    public String editSequence(PlanCourseDTO planCourseDTO, HttpSession session) {
        String result = "0";

        if(planCourseDTO != null) {
            Long lastUpdateBy = 0L;
            if (session != null && session.getAttribute("staff") != null) {
                lastUpdateBy = ((StaffDTO) session.getAttribute("staff")).getStaffId();
            }
            planCourseDTO.setLastUpdateBy(lastUpdateBy);
            ApiResult<String> apiResult = studyPlanApi.editSequence(planCourseDTO);
            if(apiResult == null){
                result = "1";
            } else if(!apiResult.isSuccess()){
                log.error(apiResult.getErrorCode() + ":" +apiResult.getErrorMsg());
                result = "1";
            }
        }

        return result;
    }

    @RequestMapping("/upload")
    @ResponseBody
    public String upload(StudyPlanDTO studyPlanDTO, MultipartFile file1, MultipartFile file2, HttpServletRequest request, HttpSession session) {
        String result = "0";
        Long createBy = 0L;
        if(session != null && session.getAttribute("staff") != null){
            createBy = ((StaffDTO)session.getAttribute("staff")).getStaffId();
        }
        if(studyPlanDTO != null && file1 != null && file2 != null){
            studyPlanDTO.setCreateBy(createBy);
            try {
                String pic1 = ControllerUtil.fileUpload(uploadPath,file1, request);
                String pic2 = ControllerUtil.fileUpload(uploadPath,file2, request);
                studyPlanDTO.setPic1(pic1);
                studyPlanDTO.setPic2(pic2);
            } catch (IOException e) {
                log.error(e.getMessage());
                result = "1";
            }

            if("0".equals(result)){
                ApiResult<String> apiResult = studyPlanApi.upload(studyPlanDTO);
                if(apiResult == null){
                    result = "1";
                } else if(!apiResult.isSuccess()){
                    log.error(apiResult.getErrorCode() + ":" +apiResult.getErrorMsg());
                    result = "1";
                }
            }
        }
        return result;
    }

    @RequestMapping("/edit")
    @ResponseBody
    public String edit(StudyPlanDTO studyPlanDTO, MultipartFile file1, MultipartFile file2, HttpServletRequest request, HttpSession session) {
        String result = "0";
        Long lastUpdateBy = 0L;
        if(session != null && session.getAttribute("staff") != null){
            lastUpdateBy = ((StaffDTO)session.getAttribute("staff")).getStaffId();
        }
        if(studyPlanDTO != null){
            studyPlanDTO.setLastUpdateBy(lastUpdateBy);
            if(file1 != null && !file1.isEmpty()){
                try {
                    studyPlanDTO.setPic1(ControllerUtil.fileUpload(uploadPath,file1,request));
                } catch (IOException e) {
                    log.error(e.getMessage());
                    result = "1";
                }
            }
            if(file2 != null && !file2.isEmpty()){
                try {
                    studyPlanDTO.setPic2(ControllerUtil.fileUpload(uploadPath,file2,request));
                } catch (IOException e) {
                    log.error(e.getMessage());
                    result = "1";
                }
            }
            if ("0".equals(result)) {
                ApiResult<String> apiResult = studyPlanApi.edit(studyPlanDTO);
                if(apiResult == null){
                    result = "1";
                } else if(!apiResult.isSuccess()){
                    log.error(apiResult.getErrorCode() + ":" +apiResult.getErrorMsg());
                    result = "1";
                }
            }
        }

        return result;
    }

    @RequestMapping("/deletePlan")
    @ResponseBody
    public String deletePlan(Long studyPlanId,HttpSession session) {
        String result = "0";

        Long lastUpdateBy = 0L;
        if(session != null && session.getAttribute("staff") != null){
            lastUpdateBy = ((StaffDTO)session.getAttribute("staff")).getStaffId();
        }
        ApiResult<String> apiResult = studyPlanApi.deletePlan(studyPlanId, lastUpdateBy);
        if(apiResult == null){
            result = "1";
        } else if(!apiResult.isSuccess()){
            log.error(apiResult.getErrorCode() + ":" +apiResult.getErrorMsg());
            result = "1";
        }
        return result;
    }

    @RequestMapping("/deleteCourse")
    @ResponseBody
    public String deleteCourse(Long planCourseId,Long studyPlanId,Integer sequence,HttpSession session) {
        String result = "0";
        Long lastUpdateBy = 0L;
        if(session != null && session.getAttribute("staff") != null){
            lastUpdateBy = ((StaffDTO)session.getAttribute("staff")).getStaffId();
        }
        ApiResult<String> apiResult = studyPlanApi.deleteCourse(planCourseId,studyPlanId,sequence, lastUpdateBy);
        if(apiResult == null){
            result = "1";
        } else if(!apiResult.isSuccess()){
            log.error(apiResult.getErrorCode() + ":" +apiResult.getErrorMsg());
            result = "1";
        }
        return result;
    }

    @RequestMapping("/addCourse")
    @ResponseBody
    public String addCourse(PlanCourseDTO planCourseDTO,HttpSession session) {
        String result = "0";
        Long createBy = 0L;
        if(session != null && session.getAttribute("staff") != null){
            createBy = ((StaffDTO)session.getAttribute("staff")).getStaffId();
        }
        if(planCourseDTO != null) {
            planCourseDTO.setCreateBy(createBy);
            ApiResult<String> apiResult = studyPlanApi.addCourse(planCourseDTO);
            if(apiResult == null){
                result = "1";
            } else if(!apiResult.isSuccess() && apiResult.getErrorCode().equals("B0100")){
                result = "2";
            } else if(!apiResult.isSuccess() && apiResult.getErrorCode().equals("B0300")){
                log.error(apiResult.getErrorCode() + ":" +apiResult.getErrorMsg());
                result = "1";
            }
        }
        return result;
    }

}
