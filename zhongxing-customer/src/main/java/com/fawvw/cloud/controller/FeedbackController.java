package com.fawvw.cloud.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fawvw.cloud.common.api.FeedbackApi;
import com.fawvw.cloud.common.api.dto.FeedbackDTO;
import com.fawvw.cloud.common.api.result.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * @author HuangGang
 * @create 2021-02-06 19:30
 **/
@Controller
@Slf4j
@RequestMapping("/feedback")
public class FeedbackController {
    @Reference(version = "${dubbo.provider.version}", application = "${dubbo.application.id}")
    private FeedbackApi feedbackApi;

    @RequestMapping("/list")
    @ResponseBody
    public List<FeedbackDTO> list(Integer feedbackType,Integer userType){
        List<FeedbackDTO> feedbackDTOS = new ArrayList<>();

        ApiResult<List<FeedbackDTO>> apiResult = feedbackApi.list(feedbackType, userType);
        if(apiResult != null){
            if(apiResult.isSuccess()){
                feedbackDTOS = apiResult.getData();
            }else{
                log.error(apiResult.getErrorCode() + ":" + apiResult.getErrorMsg());
            }
        }

        return feedbackDTOS;
    }
}
