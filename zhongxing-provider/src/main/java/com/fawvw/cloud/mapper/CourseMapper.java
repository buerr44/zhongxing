package com.fawvw.cloud.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fawvw.cloud.common.api.dto.CourseDTO;
import com.fawvw.cloud.entity.Course;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author HuangGang
 * @create 2021-02-04 19:02
 **/
public interface CourseMapper extends BaseMapper<Course> {
    /**
     * 根据分类id获取课程列表
     * @param structureId
     * @return
     */
    @Select("select tb_course.*,tb_structure.structure_name from tb_course,tb_structure where tb_course.structure_id = tb_structure.structure_id and tb_course.is_valid = 0 and tb_course.structure_id = #{structure_id}")
    List<CourseDTO> listCourseByStructureId(@Param("structure_id") Long structureId);
    /**
     * 获取全部课程列表
     * @param structureId
     * @return
     */
    @Select("select tb_course.*,tb_structure.structure_name from tb_course,tb_structure where tb_course.structure_id = tb_structure.structure_id and tb_course.is_valid = 0")
    List<CourseDTO> listAllCourse();
    @Select("select teacher,sum(play_num) as play_num from tb_course where is_valid = 0 group by teacher order by sum(play_num) desc limit 5")
    List<CourseDTO> topTeacher();
}
