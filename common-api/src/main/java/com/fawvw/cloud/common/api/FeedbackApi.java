package com.fawvw.cloud.common.api;

import com.fawvw.cloud.common.api.dto.FeedbackDTO;
import com.fawvw.cloud.common.api.result.ApiResult;

import java.util.List;

/**
 * @author HuangGang
 * @create 2021-02-06 19:31
 **/
public interface FeedbackApi {
    /**
     * 获取反馈清单
     * @param feedbackType
     * @param userType
     * @return
     */
    ApiResult<List<FeedbackDTO>> list(Integer feedbackType,Integer userType);
}
