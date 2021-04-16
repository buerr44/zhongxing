package com.fawvw.cloud.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.fawvw.cloud.common.api.FeedbackApi;
import com.fawvw.cloud.common.api.dto.FeedbackDTO;
import com.fawvw.cloud.common.api.result.ApiResult;
import com.fawvw.cloud.mapper.FeedbackMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * @author HuangGang
 * @create 2021-02-06 20:09
 **/
@Slf4j
@Service(version = "${dubbo.provider.version}", application = "${dubbo.application.id}")
public class FeedbackServiceImpl implements FeedbackApi {
    @Autowired
    private FeedbackMapper feedbackMapper;


    @Override
    public ApiResult<List<FeedbackDTO>> list(Integer feedbackType, Integer userType) {
        ApiResult<List<FeedbackDTO>> apiResult;

        List<FeedbackDTO> feedbackDTOS = new ArrayList<>();
        try{
            if(feedbackType == -1 && userType == -1){
                feedbackDTOS = feedbackMapper.listAll();
            }else if(feedbackType == -1 && userType != -1){
                feedbackDTOS = feedbackMapper.listByUserType(userType);
            }else if(feedbackType != -1 && userType == -1){
                feedbackDTOS = feedbackMapper.listByStatus(feedbackType);
            }else{
                feedbackDTOS = feedbackMapper.listByUserTypeAndStatus(userType,feedbackType);
            }
            apiResult = ApiResult.success(feedbackDTOS);
        }catch (Exception e){
            log.error(e.getMessage());
            apiResult = ApiResult.error("B0300",e.getMessage());
        }

        return apiResult;
    }
}
