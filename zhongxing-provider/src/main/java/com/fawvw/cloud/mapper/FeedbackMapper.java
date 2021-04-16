package com.fawvw.cloud.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fawvw.cloud.common.api.dto.FeedbackDTO;
import com.fawvw.cloud.entity.FeedBack;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author HuangGang
 * @create 2021-02-06 19:40
 **/
public interface FeedbackMapper extends BaseMapper<FeedBack> {
    /**
     * 获取全部反馈
     * @return
     */
    @Select("SELECT fb.*,tb_course.course_code,tb_course.teacher FROM (SELECT tb_feedback.*,tb_user.role as role from tb_feedback,tb_user WHERE tb_feedback.create_by = tb_user.user_id and tb_feedback.is_valid = 0) as fb LEFT JOIN tb_course on fb.course_id = tb_course.course_id")
    List<FeedbackDTO> listAll();

    /**
     * 根据用户类型获取反馈
     * @param userType
     * @return
     */
    @Select("SELECT fb.*,tb_course.course_code as course_code,tb_course.teacher as teacher FROM (SELECT tb_feedback.*,tb_user.role as role from tb_feedback,tb_user WHERE tb_feedback.create_by = tb_user.user_id and tb_feedback.is_valid = 0 and tb_user.role = #{role}) as fb LEFT JOIN tb_course on fb.course_id = tb_course.course_id")
    List<FeedbackDTO> listByUserType(@Param("role") Integer userType);

    /**
     * 根据反馈类型获取反馈
     * @param status
     * @return
     */
    @Select("SELECT fb.*,tb_course.course_code as course_code,tb_course.teacher as teacher FROM (SELECT tb_feedback.*,tb_user.role as role from tb_feedback,tb_user WHERE tb_feedback.create_by = tb_user.user_id and tb_feedback.is_valid = 0 and tb_feedback.`status`=#{status}) as fb LEFT JOIN tb_course on fb.course_id = tb_course.course_id")
    List<FeedbackDTO> listByStatus(@Param("status") Integer status);

    /**
     * 根据用户类型及反馈类型获取反馈
     * @param userType
     * @param status
     * @return
     */
    @Select("SELECT fb.*,tb_course.course_code as course_code,tb_course.teacher as teacher FROM (SELECT tb_feedback.*,tb_user.role as role from tb_feedback,tb_user WHERE tb_feedback.create_by = tb_user.user_id and tb_feedback.is_valid = 0 and tb_user.role = #{role} and tb_feedback.`status`=#{status}) as fb LEFT JOIN tb_course on fb.course_id = tb_course.course_id")
    List<FeedbackDTO> listByUserTypeAndStatus(@Param("role") Integer userType,@Param("status") Integer status);
}
