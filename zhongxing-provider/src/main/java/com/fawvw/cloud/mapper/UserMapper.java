package com.fawvw.cloud.mapper;


import com.fawvw.cloud.common.api.dto.StudyPlanDTO;
import com.fawvw.cloud.common.api.dto.UserDTO;
import com.fawvw.cloud.entity.StudyPlan;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author HuangGang
 * @create 2021-02-22 9:45
 **/
public interface UserMapper{
    @Select("SELECT count(DISTINCT create_by) FROM tb_visit where TO_DAYS(now())-TO_DAYS(create_date) = 0")
    Integer todayVisit();
    @Select("SELECT count(DISTINCT create_by) FROM tb_visit where TO_DAYS(now())-TO_DAYS(create_date) = 1")
    Integer yesterdayVisit();
    @Select("SELECT count(*) FROM tb_user where TO_DAYS(now())-TO_DAYS(create_date) = 0")
    Integer todayUser();
    @Select("SELECT count(*) FROM tb_user where TO_DAYS(now())-TO_DAYS(create_date) = 1")
    Integer yesterdayUser();
    @Select("SELECT count(*) FROM tb_history where TO_DAYS(now())-TO_DAYS(create_date) = 0")
    Integer todayPlay();
    @Select("SELECT count(*) FROM tb_history where TO_DAYS(now())-TO_DAYS(create_date) = 1")
    Integer yesterdayPlay();
    @Select("SELECT count(create_by) FROM `tb_visit`  where TO_DAYS(create_date) BETWEEN TO_DAYS(#{startDate}) and TO_DAYS(#{endDate}) and hour(create_date) BETWEEN #{start} and #{end};")
    Integer getPvByHour(@Param("startDate")Date startDate,@Param("endDate")Date endDate,@Param("start")int start, @Param("end")int end);
    @Select("SELECT count(DISTINCT create_by) FROM `tb_visit` where TO_DAYS(create_date) BETWEEN TO_DAYS(#{startDate}) and TO_DAYS(#{endDate}) and hour(create_date) BETWEEN #{start} and #{end};")
    Integer getUvByHour(@Param("startDate")Date startDate,@Param("endDate")Date endDate, @Param("start")int start, @Param("end")int end);
    @Select("SELECT count(create_by) FROM `tb_visit` where TO_DAYS(create_date) = TO_DAYS(#{searchDate})")
    Integer getPvByDay(@Param("searchDate")Date searchDate);
    @Select("SELECT count(DISTINCT create_by) FROM `tb_visit` where TO_DAYS(create_date) = TO_DAYS(#{searchDate})")
    Integer getUvByDay(@Param("searchDate")Date searchDate);
    @Select("SELECT t1.user_id,t1.nickname,t1.status,t1.employee_code,t1.employee_name,t1.description,t2.total_learn,t3.week_learn,t4.total_finish,t5.it_finish from ((((SELECT u.user_id, u.nickname,IF(ISNULL(ac.user_id ),2,1)  as `status`,ac.employee_code,ac.employee_name,ac.description FROM `tb_user` u LEFT JOIN tb_active ac on u.user_id = ac.user_id) t1 LEFT JOIN (SELECT create_by, sum(learn_time) as total_learn from tb_classhour  GROUP BY create_by) t2  on t1.user_id = t2.create_by) LEFT JOIN (SELECT create_by, sum(learn_time) as week_learn from tb_classhour where  YEARWEEK(DATE_FORMAT(create_date,'%Y-%m-%d')) = YEARWEEK(NOW()) GROUP BY create_by) t3 on t1.user_id = t3.create_by) LEFT JOIN (SELECT create_by, count(DISTINCT course_id) as total_finish FROM tb_history WHERE status = -1 GROUP BY create_by) t4 on t1.user_id = t4.create_by) LEFT JOIN (SELECT h.create_by, count(DISTINCT h.course_id) as it_finish FROM tb_history h INNER JOIN tb_course c on h.status=-1 and c.course_code LIKE 'IT%' and h.course_id = c.course_id GROUP BY h.create_by) t5 on t1.user_id = t5.create_by")
    List<UserDTO> listAllUserInfo();
    @Select("SELECT t1.user_id,t1.nickname,t1.status,t1.employee_code,t1.employee_name,t1.description,t2.total_learn,t3.week_learn,t4.total_finish,t5.it_finish from ((((SELECT u.user_id, u.nickname,IF(ISNULL(ac.user_id ),2,1)  as `status`,ac.employee_code,ac.employee_name,ac.description FROM `tb_user` u LEFT JOIN tb_active ac on u.user_id = ac.user_id) t1 LEFT JOIN (SELECT create_by, sum(learn_time) as total_learn from tb_classhour  GROUP BY create_by) t2  on t1.user_id = t2.create_by) LEFT JOIN (SELECT create_by, sum(learn_time) as week_learn from tb_classhour where  YEARWEEK(DATE_FORMAT(create_date,'%Y-%m-%d')) = YEARWEEK(NOW()) GROUP BY create_by) t3 on t1.user_id = t3.create_by) LEFT JOIN (SELECT create_by, count(DISTINCT course_id) as total_finish FROM tb_history WHERE status = -1 GROUP BY create_by) t4 on t1.user_id = t4.create_by) LEFT JOIN (SELECT h.create_by, count(DISTINCT h.course_id) as it_finish FROM tb_history h INNER JOIN tb_course c on h.status=-1 and c.course_code LIKE 'IT%' and h.course_id = c.course_id GROUP BY h.create_by) t5 on t1.user_id = t5.create_by where t1.status = #{status}")
    List<UserDTO> listUserByStatus(@Param("status")Integer status);
    @Select("SELECT t3.studyPlan_name,CONCAT(COUNT(t4.course_id),'/',COUNT(t3.course_id)) as plan_status FROM (SELECT t2.studyPlan_id,t2.studyPlan_name,pc.course_id from (SELECT t1.studyPlan_id,sp.studyPlan_name from (SELECT studyplan_id from tb_userstudyplan where create_by = #{user_id}) t1 LEFT JOIN tb_studyplan sp ON t1.studyPlan_id = sp.studyPlan_id WHERE sp.is_valid = 0) t2 LEFT JOIN tb_plancourse pc ON t2.studyPlan_id = pc.studyPlan_id WHERE pc.is_valid = 0) t3 LEFT JOIN (SELECT DISTINCT(course_id) as course_id from tb_history WHERE  create_by = #{user_id} AND status = -1) t4 ON t3.course_id = t4.course_id GROUP BY t3.studyPlan_name;")
    List<StudyPlanDTO> getPlanStatusByUser(@Param("user_id")Long userId);
}
