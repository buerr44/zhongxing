package com.fawvw.cloud.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fawvw.cloud.common.api.UserApi;
import com.fawvw.cloud.common.api.dto.UserDTO;
import com.fawvw.cloud.common.api.result.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.net.AprEndpoint;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author HuangGang
 * @create 2021-01-27 18:48
 **/
@Controller
@Slf4j
@RequestMapping("/user")
public class UserController {
    @Reference(version = "${dubbo.provider.version}", application = "${dubbo.application.id}")
    private UserApi userApi;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
    }

    @RequestMapping("/getUserData")
    @ResponseBody
    public Integer[] getUserData(){
        Integer[] arr = new Integer[6];

        ApiResult<Integer[]> apiResult = userApi.getUserData();
        if(apiResult != null){
            if(apiResult.isSuccess()){
               arr = apiResult.getData();
            }else {
                log.error(apiResult.getErrorCode() + ":" + apiResult.getErrorMsg());
            }
        }

        return arr;
    }

    @RequestMapping("/getChartData")
    @ResponseBody
    public Map<String,List<Object>> getChartData(Integer status,Date startDate,Date endDate){
        Map<String,List<Object>> map = new HashMap<>();

        ApiResult<Map<String,List<Object>>> apiResult = userApi.getChartData(status,startDate,endDate);
        if(apiResult != null){
            if(apiResult.isSuccess()){
                map = apiResult.getData();
            }else {
                log.error(apiResult.getErrorCode() + ":" + apiResult.getErrorMsg());
            }
        }

        return map;
    }

    @RequestMapping("/listUser")
    @ResponseBody
    public List<UserDTO> listUserInfo(Integer status){
        List<UserDTO> userDTOS = new ArrayList<>();

        ApiResult<List<UserDTO>> apiResult = userApi.listUserInfo(status);
        if(apiResult != null){
            if(apiResult.isSuccess()){
                userDTOS = apiResult.getData();
            }else {
                log.error(apiResult.getErrorCode() + ":" + apiResult.getErrorMsg());
            }
        }

        return userDTOS;
    }
}
