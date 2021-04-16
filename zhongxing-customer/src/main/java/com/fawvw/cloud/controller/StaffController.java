package com.fawvw.cloud.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fawvw.cloud.common.api.StaffApi;
import com.fawvw.cloud.common.api.dto.StaffDTO;
import com.fawvw.cloud.common.api.result.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

/**
 * @author HuangGang
 * @create 2021-02-06 15:56
 **/
@Controller
@Slf4j
@RequestMapping("/staff")
public class StaffController {
    @Reference(version = "${dubbo.provider.version}", application = "${dubbo.application.id}")
    private StaffApi staffApi;

    @RequestMapping("/login")
    @ResponseBody
    public String login(String staffName, String password, HttpSession session, HttpServletResponse response){
        String result = "0";

        String newPassword = "";
        try {
            newPassword = ControllerUtil.EncoderByMd5(password);
        } catch (NoSuchAlgorithmException e) {
            log.error(e.getMessage());
            result = "2";
        } catch (UnsupportedEncodingException e) {
            log.error(e.getMessage());
            result = "2";
        }
        ApiResult<StaffDTO> apiResult = staffApi.login(staffName, newPassword);
        if(apiResult != null){
            if(apiResult.isSuccess()){
                StaffDTO staff = apiResult.getData();
                if(staff != null){
                    session.setAttribute("staff",staff);
                    Cookie cookie = new Cookie("staffName", staffName);
                    cookie.setPath("/");
                    response.addCookie(cookie);
                }else {
                    result = "1";
                }
            }else if("1".equals(apiResult.getErrorCode())){
                result = apiResult.getErrorMsg();
            } else {
                log.error(apiResult.getErrorCode() + ":" + apiResult.getErrorMsg());
                result = "1";
            }
        }else {
            result = "1";
        }

        return  result;
    }

    @RequestMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "index";
    }
}
