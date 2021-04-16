package com.fawvw.cloud.common.api;

import com.fawvw.cloud.common.api.dto.CourseDTO;
import com.fawvw.cloud.common.api.result.ApiResult;

import java.util.List;

/**
 * @author HuangGang
 * @create 2021-01-27 21:05
 **/
public interface CourseApi {
    /**
     * 根据小类id读取课程列表
     * @param structureId
     * @return
     */
    ApiResult<List<CourseDTO>> listCourse(Long structureId);

    /**
     * 上传课程
     * @param courseDTO
     * @return
     */
    ApiResult<String> upload(CourseDTO courseDTO);

    /**
     * 编辑课程
     * @param courseDTO
     * @return
     */
    ApiResult<String> edit(CourseDTO courseDTO);

    /**
     * 删除课程
     * @param courseId
     * @param lastUpdateBy
     * @return
     */
    ApiResult<String> delete(Long courseId,Long lastUpdateBy);

    /**
     * 获取播放量前5课程
     * @return
     */
    ApiResult<List<CourseDTO>> topCourse();
    /**
     * 获取播放量前5教师
     * @return
     */
    ApiResult<List<CourseDTO>> topTeacher();
}
