package com.fawvw.cloud.common.api;

import com.fawvw.cloud.common.api.dto.PlanCourseDTO;
import com.fawvw.cloud.common.api.dto.StructureDTO;
import com.fawvw.cloud.common.api.dto.StudyPlanDTO;
import com.fawvw.cloud.common.api.result.ApiResult;

import java.util.List;

/**
 * @author HuangGang
 * @create 2021-03-29 15:59
 **/
public interface StudyPlanApi{
    /**
     * 更改计划内课程排序
     * @param planCourseDTO
     * @return
     */
    ApiResult<String> editSequence(PlanCourseDTO planCourseDTO);

    /**
     * 添加计划
     * @param studyPlanDTO
     * @return
     */
    ApiResult<String> upload(StudyPlanDTO studyPlanDTO);

    /**
     * 编辑计划
     * @param studyPlanDTO
     * @return
     */
    ApiResult<String> edit(StudyPlanDTO studyPlanDTO);

    /**
     * 删除计划
     * @param studyPlanId
     * @param lastUpdateBy
     * @return
     */
    ApiResult<String> deletePlan(Long studyPlanId,Long lastUpdateBy);

    /**
     * 删除计划内课程
     * @param planCourseId
     * @param lastUpdateBy
     * @return
     */
    ApiResult<String> deleteCourse(Long planCourseId,Long studyPlanId,Integer sequence, Long lastUpdateBy);

    /**
     * 添加计划内课程
     * @param planCourseDTO
     * @return
     */
    ApiResult<String> addCourse(PlanCourseDTO planCourseDTO);

    /**
     * 获取所有学习计划
     * @return
     */
    ApiResult<List<StudyPlanDTO>> listStudyPlan();

    /**
     * 读取计划下课程清单
     * @param studyPlanId
     * @return
     */
    ApiResult<List<PlanCourseDTO>> listPlanCourse(Long studyPlanId);
}
