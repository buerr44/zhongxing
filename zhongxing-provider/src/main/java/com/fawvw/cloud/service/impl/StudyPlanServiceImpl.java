package com.fawvw.cloud.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.fawvw.cloud.common.api.StudyPlanApi;
import com.fawvw.cloud.common.api.dto.PlanCourseDTO;
import com.fawvw.cloud.common.api.dto.StudyPlanDTO;
import com.fawvw.cloud.common.api.result.ApiResult;
import com.fawvw.cloud.entity.PlanCourse;
import com.fawvw.cloud.entity.Structure;
import com.fawvw.cloud.entity.StudyPlan;
import com.fawvw.cloud.mapper.PlanCourseMapper;
import com.fawvw.cloud.mapper.StudyPlanMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author HuangGang
 * @create 2021-03-29 16:51
 **/
@Slf4j
@Service(version = "${dubbo.provider.version}", application = "${dubbo.application.id}")
public class StudyPlanServiceImpl implements StudyPlanApi {
    @Autowired
    private StudyPlanMapper studyPlanMapper;

    @Autowired
    private PlanCourseMapper planCourseMapper;

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public ApiResult<String> editSequence(PlanCourseDTO planCourseDTO) {
        ApiResult<String> apiResult;

        PlanCourse planCourse = planCourseMapper.selectById(planCourseDTO.getPlanCourseId());
        Integer oldSequence = planCourse.getSequence();
        planCourse.setSequence(planCourseDTO.getSequence());
        planCourse.setLastUpdateBy(planCourseDTO.getLastUpdateBy());
        planCourse.setLastUpdateDate(new Date());
        try {
            QueryWrapper<PlanCourse> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("studyPlan_id",planCourseDTO.getStudyPlanId()).eq("sequence",planCourseDTO.getSequence()).eq("is_valid",0);
            List<PlanCourse> planCourses = planCourseMapper.selectList(queryWrapper);
            if(planCourses != null && !planCourses.isEmpty()){
                if (oldSequence < planCourseDTO.getSequence()) {
                    planCourseMapper.reduceSequence(planCourseDTO.getStudyPlanId(), oldSequence + 1, planCourseDTO.getSequence());
                } else {
                    planCourseMapper.addSequence(planCourseDTO.getStudyPlanId(), planCourseDTO.getSequence(), oldSequence - 1);
                }
            }
            planCourseMapper.updateById(planCourse);
            apiResult = ApiResult.success();
        }catch (Exception e){
            log.error(e.getMessage());
            apiResult = ApiResult.error("B0300",e.getMessage());
        }

        return apiResult;
    }

    @Override
    public ApiResult<String> upload(StudyPlanDTO studyPlanDTO) {
        ApiResult<String> apiResult;

        StudyPlan studyPlan = new StudyPlan();
        studyPlan.setStudyPlanName(studyPlanDTO.getStudyPlanName());
        studyPlan.setPic1(studyPlanDTO.getPic1());
        studyPlan.setPic2(studyPlanDTO.getPic2());
        studyPlan.setPlanTask(studyPlanDTO.getPlanTask());
        studyPlan.setPlanType(studyPlanDTO.getPlanType());
        studyPlan.setDescription(studyPlanDTO.getDescription());
        studyPlan.setCreateBy(studyPlanDTO.getCreateBy());
        studyPlan.setCreateDate(new Date());

        try {
            int insert = studyPlanMapper.insert(studyPlan);
            apiResult = ApiResult.success();
        }catch (Exception e){
            log.error(e.getMessage());
            apiResult = ApiResult.error("B0300",e.getMessage());
        }

        return apiResult;
    }

    @Override
    public ApiResult<String> edit(StudyPlanDTO studyPlanDTO) {
        ApiResult<String> apiResult;

        UpdateWrapper<StudyPlan> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("studyPlan_name",studyPlanDTO.getStudyPlanName());
        updateWrapper.set("plan_task",studyPlanDTO.getPlanTask());
        updateWrapper.set("plan_type",studyPlanDTO.getPlanType());
        updateWrapper.set("description",studyPlanDTO.getDescription());
        updateWrapper.set("last_update_by",studyPlanDTO.getLastUpdateBy());
        updateWrapper.set("last_update_date",new Date());
        updateWrapper.eq("studyPlan_id",studyPlanDTO.getStudyPlanId());
        if(studyPlanDTO.getPic1() != null && !"".equals(studyPlanDTO.getPic1().trim())){
            updateWrapper.set("pic1",studyPlanDTO.getPic1());
        }
        if(studyPlanDTO.getPic2() != null && !"".equals(studyPlanDTO.getPic2().trim())){
            updateWrapper.set("pic2",studyPlanDTO.getPic2());
        }
        try{
            studyPlanMapper.update(null,updateWrapper);
            apiResult = ApiResult.success();
        }catch (Exception e){
            log.error(e.getMessage());
            apiResult = ApiResult.error("B0300",e.getMessage());
        }

        return apiResult;
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public ApiResult<String> deletePlan(Long studyPlanId, Long lastUpdateBy) {
        ApiResult<String> apiResult;

        UpdateWrapper<StudyPlan> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("is_valid","-1").set("last_update_by",lastUpdateBy).set("last_update_date",new Date()).eq("studyPlan_id",studyPlanId);
        try{
            studyPlanMapper.update(null,updateWrapper);
            UpdateWrapper<PlanCourse> updateWrapper1 = new UpdateWrapper<>();
            updateWrapper1.set("is_valid","-1").set("last_update_by",lastUpdateBy).set("last_update_date",new Date()).eq("studyPlan_id",studyPlanId);
            planCourseMapper.update(null,updateWrapper1);
            apiResult = ApiResult.success();
        }catch (Exception e){
            log.error(e.getMessage());
            apiResult = ApiResult.error("B0300",e.getMessage());
        }

        return apiResult;
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public ApiResult<String> deleteCourse(Long planCourseId,Long studyPlanId,Integer sequence, Long lastUpdateBy) {
        ApiResult<String> apiResult;

        UpdateWrapper<PlanCourse> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("is_valid",-1).set("last_update_by",lastUpdateBy).set("last_update_date",new Date()).eq("planCourse_id",planCourseId);
        try{
            planCourseMapper.update(null,updateWrapper);
            planCourseMapper.reduceSequenceFollow(studyPlanId,sequence);
            apiResult = ApiResult.success();
        }catch (Exception e){
            log.error(e.getMessage());
            apiResult = ApiResult.error("B0300",e.getMessage());
        }

        return apiResult;
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public ApiResult<String> addCourse(PlanCourseDTO planCourseDTO) {
        ApiResult<String> apiResult;

        QueryWrapper<PlanCourse> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("studyPlan_id",planCourseDTO.getStudyPlanId()).eq("course_id",planCourseDTO.getCourseId()).eq("is_valid",0);
        try {
            List<PlanCourse> planCourses = planCourseMapper.selectList(queryWrapper);
            if(planCourses != null && !planCourses.isEmpty()){
                apiResult = ApiResult.error("B0100","课程已存在");
            }else {
                PlanCourse planCourse = new PlanCourse();
                planCourse.setCourseId(planCourseDTO.getCourseId());
                planCourse.setStudyPlanId(planCourseDTO.getStudyPlanId());
                planCourse.setSequence(planCourseDTO.getSequence());
                planCourse.setCreateBy(planCourseDTO.getCreateBy());
                planCourse.setCreateDate(new Date());
                QueryWrapper<PlanCourse> queryWrapper1 = new QueryWrapper<>();
                queryWrapper1.eq("studyPlan_id",planCourseDTO.getStudyPlanId()).eq("sequence",planCourseDTO.getSequence()).eq("is_valid",0);
                List<PlanCourse> planCourses1 = planCourseMapper.selectList(queryWrapper1);
                if(planCourses1 != null && !planCourses1.isEmpty()){
                    planCourseMapper.addSequenceFollow(planCourseDTO.getStudyPlanId(), planCourseDTO.getSequence());
                }
                planCourseMapper.insert(planCourse);
                apiResult = ApiResult.success();
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            apiResult = ApiResult.error("B0300", e.getMessage());
        }

        return apiResult;
    }

    @Override
    public ApiResult<List<StudyPlanDTO>> listStudyPlan() {
        ApiResult<List<StudyPlanDTO>> apiResult;

        List<StudyPlan> studyPlans = new ArrayList<>();
        List<StudyPlanDTO> studyPlanDTOS = new ArrayList<>();
        QueryWrapper<StudyPlan> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_valid","0");
        try {
            studyPlans = studyPlanMapper.selectList(queryWrapper);
            for (StudyPlan studyPlan : studyPlans) {
                StudyPlanDTO studyPlanDTO = new StudyPlanDTO();
                studyPlanDTO.setStudyPlanId(studyPlan.getStudyPlanId());
                studyPlanDTO.setStudyPlanName(studyPlan.getStudyPlanName());
                studyPlanDTO.setLearnerCount(studyPlan.getLearnerCount());
                studyPlanDTO.setPlanTask(studyPlan.getPlanTask());
                studyPlanDTO.setPlanType(studyPlan.getPlanType());
                studyPlanDTO.setDescription(studyPlan.getDescription());
                studyPlanDTO.setCourseCount(planCourseMapper.coutCourse(studyPlan.getStudyPlanId()));
                studyPlanDTO.setCreateDate(studyPlan.getCreateDate());
                studyPlanDTOS.add(studyPlanDTO);
            }
            apiResult = ApiResult.success(studyPlanDTOS);
        }catch (Exception e){
            log.error(e.getMessage());
            apiResult = ApiResult.error("B0300",e.getMessage());
        }

        return apiResult;
    }

    @Override
    public ApiResult<List<PlanCourseDTO>> listPlanCourse(Long studyPlanId) {
        ApiResult<List<PlanCourseDTO>> apiResult;

        List<PlanCourseDTO> planCourseDTOS = new ArrayList<>();
        try {
            planCourseDTOS = planCourseMapper.listCourses(studyPlanId);
            apiResult = ApiResult.success(planCourseDTOS);
        }catch (Exception e){
            log.error(e.getMessage());
            apiResult = ApiResult.error("B0300",e.getMessage());
        }

        return apiResult;
    }
}
