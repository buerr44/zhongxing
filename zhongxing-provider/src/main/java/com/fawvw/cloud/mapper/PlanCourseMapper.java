package com.fawvw.cloud.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fawvw.cloud.common.api.dto.PlanCourseDTO;
import com.fawvw.cloud.entity.PlanCourse;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author HuangGang
 * @create 2021-03-29 16:58
 **/
public interface PlanCourseMapper extends BaseMapper<PlanCourse> {
    @Update("UPDATE tb_plancourse set sequence = sequence-1 WHERE studyplan_id = #{studyPlan_id} and sequence BETWEEN #{min} AND #{max} and is_valid = 0;")
    void reduceSequence(@Param("studyPlan_id") Long studyPlanId, @Param("min") Integer oldSequence,  @Param("max")Integer sequence);
    @Update("UPDATE tb_plancourse set sequence = sequence+1 WHERE studyplan_id = #{studyPlan_id} and sequence BETWEEN #{min} AND #{max} and is_valid = 0")
    void addSequence(@Param("studyPlan_id")Long studyPlanId, @Param("min")Integer sequence,  @Param("max")Integer oldSequence);
    @Select("SELECT COUNT(*) FROM `tb_plancourse` WHERE studyPlan_id = #{studyPlan_id} AND is_valid = 0;")
    Integer coutCourse(@Param("studyPlan_id")Long studyPlanId);
    @Select("SELECT t1.*, t2.course_code,t2.course_name FROM (SELECT * FROM `tb_plancourse` WHERE studyPlan_id = #{studyPlan_id} AND is_valid = 0) AS t1 INNER JOIN (SELECT course_id,course_name,course_code FROM tb_course WHERE is_valid = 0) AS t2 ON t1.course_id = t2.course_id;")
    List<PlanCourseDTO> listCourses(@Param("studyPlan_id")Long studyPlanId);
    @Update("UPDATE tb_plancourse set sequence = sequence+1 WHERE studyplan_id = #{studyPlan_id} and sequence >=  #{sequence} and is_valid =0")
    void addSequenceFollow(@Param("studyPlan_id")Long studyPlanId, @Param("sequence")Integer sequence);
    @Update("UPDATE tb_plancourse set sequence = sequence-1 WHERE studyplan_id = #{studyPlan_id} and sequence >  #{sequence} and is_valid =0")
    void reduceSequenceFollow(@Param("studyPlan_id")Long studyPlanId, @Param("sequence")Integer sequence);
}
