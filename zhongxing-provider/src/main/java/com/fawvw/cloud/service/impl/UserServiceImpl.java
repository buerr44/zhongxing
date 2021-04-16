package com.fawvw.cloud.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.fawvw.cloud.common.api.UserApi;
import com.fawvw.cloud.common.api.dto.StudyPlanDTO;
import com.fawvw.cloud.common.api.dto.UserDTO;
import com.fawvw.cloud.common.api.result.ApiResult;
import com.fawvw.cloud.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author HuangGang
 * @create 2021-01-27 18:48
 **/
@Service(version = "${dubbo.provider.version}", application = "${dubbo.application.id}")
@Slf4j
public class UserServiceImpl implements UserApi {
    @Autowired
    private UserMapper userMapper;

    @Override
    @Transactional(readOnly = true)
    public ApiResult<Integer[]> getUserData() {
        ApiResult<Integer[]> apiResult;

        Integer[] arr = new Integer[6];
        try {
            arr[0] = userMapper.todayVisit();
            arr[1] = userMapper.yesterdayVisit();
            arr[2] = userMapper.todayUser();
            arr[3] = userMapper.yesterdayUser();
            arr[4] = userMapper.todayPlay();
            arr[5] = userMapper.yesterdayPlay();
            apiResult = ApiResult.success(arr);
        }catch (Exception e){
            log.error(e.getMessage());
            apiResult = ApiResult.error("B0300",e.getMessage());
        }

        return apiResult;
    }

    @Override
    @Transactional(readOnly = true)
    public ApiResult<Map<String, List<Object>>> getChartData(Integer status,Date startDate,Date endDate) {
        ApiResult<Map<String, List<Object>>> apiResult;

        Map<String, List<Object>> map = new HashMap<>();
        List<Object> xRay = new ArrayList<>();
        List<Object> pv = new ArrayList<>();
        List<Object> uv = new ArrayList<>();

        try {
            if(status == 1){
                for (int i = 0; i < 12; i++) {
                    pv.add(userMapper.getPvByHour(startDate,endDate,2*i,2*i+1));
                    uv.add(userMapper.getUvByHour(startDate,endDate,2*i,2*i+1));
                    xRay.add(2*i+"时-"+(2*(i+1))+"时");
                }
            }else {
                Calendar calBegin = Calendar.getInstance();
                calBegin.setTime(startDate);
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                while (!endDate.before(calBegin.getTime()))  {
                    // 根据日历的规则，为给定的日历字段添加或减去指定的时间量
                    xRay.add(formatter.format(calBegin.getTime()));
                    pv.add(userMapper.getPvByDay(calBegin.getTime()));
                    uv.add(userMapper.getUvByDay(calBegin.getTime()));
                    calBegin.add(Calendar.DAY_OF_MONTH, 1);
                }
            }
            map.put("xRay",xRay);
            map.put("pv",pv);
            map.put("uv",uv);
           apiResult = ApiResult.success(map);
        }catch (Exception e){
            log.error(e.getMessage());
            apiResult = ApiResult.error("B0300",e.getMessage());
        }

        return apiResult;
    }

    @Override
    @Transactional(readOnly = true)
    public ApiResult<List<UserDTO>> listUserInfo(Integer status) {
        ApiResult<List<UserDTO>> apiResult;

        List<UserDTO> userDTOS = new ArrayList<>();
        try {
            if (status == 0) {
                userDTOS = userMapper.listAllUserInfo();
                userDTOS = getPlanStatus(userDTOS);
            } else {
                userDTOS = userMapper.listUserByStatus(status);
                userDTOS = getPlanStatus(userDTOS);
            }
            apiResult = ApiResult.success(userDTOS);
        }catch (Exception e){
            log.error(e.getMessage());
            apiResult = ApiResult.error("B0300",e.getMessage());
        }

        return apiResult;
    }

    private List<UserDTO> getPlanStatus(List<UserDTO> userDTOS){
        if(userDTOS != null && !userDTOS.isEmpty()){
            for (UserDTO userDTO : userDTOS) {
                List<StudyPlanDTO> planDTOS = userMapper.getPlanStatusByUser(userDTO.getUserId());
                Map<String,String> planStatus = new HashMap<>();
                for (StudyPlanDTO planDTO : planDTOS) {
                    planStatus.put(planDTO.getStudyPlanName(),planDTO.getPlanStatus());
                }
                if(planStatus.isEmpty()){
                    userDTO.setStudyPlan(null);
                }else {
                    userDTO.setStudyPlan(planStatus);
                }
            }
        }
        return userDTOS;
    }
}
