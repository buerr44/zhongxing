package com.fawvw.cloud.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.fawvw.cloud.common.api.CourseApi;
import com.fawvw.cloud.common.api.dto.CourseDTO;
import com.fawvw.cloud.common.api.result.ApiResult;
import com.fawvw.cloud.entity.Course;
import com.fawvw.cloud.entity.Structure;
import com.fawvw.cloud.mapper.CourseMapper;
import com.fawvw.cloud.mapper.StructureMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author HuangGang
 * @create 2021-01-27 21:29
 **/
@Slf4j
@Service(version = "${dubbo.provider.version}", application = "${dubbo.application.id}")
public class CourseServiceImpl implements CourseApi {
    @Autowired
    private CourseMapper courseMapper;
    @Autowired
    private StructureMapper structureMapper;

    @Override
    public ApiResult<List<CourseDTO>> listCourse(Long structureId) {
        ApiResult<List<CourseDTO>> apiResult = new ApiResult<>();

        List<CourseDTO> courseDTOS = new ArrayList<CourseDTO>();
        try{
            if(structureId == 0){
                courseDTOS = courseMapper.listAllCourse();
            }else{
                courseDTOS = courseMapper.listCourseByStructureId(structureId);
            }
            apiResult = ApiResult.success(courseDTOS);
        }catch (Exception e){
            log.error(e.getMessage());
            apiResult = ApiResult.error("B0300",e.getMessage());
        }

        return apiResult;
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public ApiResult<String> upload(CourseDTO courseDTO) {
        ApiResult<String> apiResult;

        Course course = new Course();
        course.setCourseName(courseDTO.getCourseName());
        course.setSequence(courseDTO.getSequence());
        course.setCover(courseDTO.getCover());
        course.setStructureId(courseDTO.getStructureId());
        course.setVideo(courseDTO.getVideo());
        course.setDuration(courseDTO.getDuration());
        course.setTeacher(courseDTO.getTeacher());
        course.setCreateBy(courseDTO.getCreateBy());
        course.setCreateDate(new Date());
        course.setPlayNum(0L);
        //生成课程编码
        Structure structure = structureMapper.selectById(courseDTO.getStructureId());
        if(structure != null){
            if(courseDTO.getSequence() < 10){
                course.setCourseCode(structure.getStructureCode() + "0" + courseDTO.getSequence());
            }else{
                course.setCourseCode(structure.getStructureCode() + courseDTO.getSequence());
            }
        }
        try{
            int insert = courseMapper.insert(course);
            if(insert == 1){
                apiResult = ApiResult.success();
            }else{
                apiResult = ApiResult.error("B0300","未正常插入数据");
            }
        }catch (Exception e){
            log.error(e.getMessage());
            apiResult = ApiResult.error("B0300",e.getMessage());
        }

        return apiResult;
    }

    @Override
    public ApiResult<String> edit(CourseDTO courseDTO) {
        ApiResult<String> apiResult;

        UpdateWrapper<Course> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("course_name",courseDTO.getCourseName());
        updateWrapper.set("sequence",courseDTO.getSequence());
        updateWrapper.set("teacher",courseDTO.getTeacher());
        updateWrapper.set("duration",courseDTO.getDuration());
        updateWrapper.set("last_update_by",courseDTO.getLastUpdateBy());
        updateWrapper.set("last_update_date",new Date());
        if(courseDTO.getVideo() != null && !"".equals(courseDTO.getVideo().trim())){
            updateWrapper.set("video",courseDTO.getVideo());
        }
        if(courseDTO.getCover() != null && !"".equals(courseDTO.getCover().trim())){
            updateWrapper.set("cover",courseDTO.getCover());
        }
        if(courseDTO.getCourseCode() != null) {
            if (courseDTO.getSequence() < 10) {
                updateWrapper.set("course_code",courseDTO.getCourseCode().substring(0,courseDTO.getCourseCode().length()-2) + "0" + courseDTO.getSequence());
            }else{
                updateWrapper.set("course_code",courseDTO.getCourseCode().substring(0,courseDTO.getCourseCode().length()-2) + courseDTO.getSequence());
            }
        }
        updateWrapper.eq("course_id",courseDTO.getCourseId());
        try{
            int update = courseMapper.update(null, updateWrapper);
            if(update == 1){
                apiResult = ApiResult.success();
            }else{
                apiResult = ApiResult.error("B0300", "未正常修改数据");
            }
        } catch (Exception e){
            log.error(e.getMessage());
            apiResult = ApiResult.error("B0300",e.getMessage());
        }

        return apiResult;
    }

    @Override
    public ApiResult<String> delete(Long courseId, Long lastUpdateBy) {
        ApiResult<String> apiResult;

        try{
            UpdateWrapper<Course> updateWrapper = new UpdateWrapper<>();
            updateWrapper.set("is_valid","-1").set("last_update_by",lastUpdateBy).set("last_update_date",new Date()).eq("course_id",courseId);
            int update = courseMapper.update(null, updateWrapper);
            if (update == 1) {
                apiResult = ApiResult.success();
            }else{
                apiResult = ApiResult.error("B0300", "未正常修改数据");
            }
        }catch (Exception e){
            log.error(e.getMessage());
            apiResult = ApiResult.error("B0300",e.getMessage());
        }

        return apiResult;
    }

    @Override
    public ApiResult<List<CourseDTO>> topCourse() {
        ApiResult<List<CourseDTO>> apiResult;

        QueryWrapper<Course> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("play_num");
        queryWrapper.eq("is_valid","0");
        queryWrapper.last(" limit 5");
        List<CourseDTO> courseDTOS = new ArrayList<>();
        try{
            List<Course> courses = courseMapper.selectList(queryWrapper);
            if(courses != null && !courses.isEmpty()){
                for (Course course : courses) {
                    CourseDTO courseDTO = new CourseDTO();
                    courseDTO.setCourseName(course.getCourseName());
                    courseDTO.setPlayNum(course.getPlayNum());
                    courseDTOS.add(courseDTO);
                }
            }
            apiResult = ApiResult.success(courseDTOS);
        }catch (Exception e){
            log.error(e.getMessage());
            apiResult = ApiResult.error("B0300",e.getMessage());
        }

        return apiResult;
    }

    @Override
    public ApiResult<List<CourseDTO>> topTeacher() {
        ApiResult<List<CourseDTO>> apiResult;

        try{
            List<CourseDTO> courseDTOS = courseMapper.topTeacher();
            apiResult = ApiResult.success(courseDTOS);
        }catch (Exception e){
            log.error(e.getMessage());
            apiResult = ApiResult.error("B0300",e.getMessage());
        }

        return apiResult;
    }
}
