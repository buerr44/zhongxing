package com.fawvw.cloud.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fawvw.cloud.common.api.CourseApi;
import com.fawvw.cloud.common.api.EmployeeApi;
import com.fawvw.cloud.common.api.dto.CourseDTO;
import com.fawvw.cloud.common.api.dto.EmployeeDTO;
import com.fawvw.cloud.common.api.dto.StaffDTO;
import com.fawvw.cloud.common.api.result.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.tomcat.util.ExceptionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.transform.Result;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * @author HuangGang
 * @create 2021-03-01 10:52
 **/
@Slf4j
@Controller
@RequestMapping("/employee")
public class EmployeeController {
    @Reference(version = "${dubbo.provider.version}", application = "${dubbo.application.id}")
    private EmployeeApi employeeApi;

    @RequestMapping("/list")
    @ResponseBody
    public List<EmployeeDTO> listEmployee(Integer status){
        List<EmployeeDTO> employeeDTOS = new ArrayList<>();

        ApiResult<List<EmployeeDTO>> apiResult = employeeApi.listEmployee(status);
        if(apiResult != null){
            if(apiResult.isSuccess()){
                employeeDTOS = apiResult.getData();
            }else {
                log.error(apiResult.getErrorCode() + ":" + apiResult.getErrorMsg());
            }
        }

        return employeeDTOS;
    }

    @RequestMapping("/add")
    @ResponseBody
    public String addEmployee(EmployeeDTO employeeDTO, HttpSession session){
        String result = "0";

        Long createBy = 0L;
        if(session != null && session.getAttribute("staff") != null){
            createBy = ((StaffDTO)session.getAttribute("staff")).getStaffId();
        }
        employeeDTO.setCreateBy(createBy);
        ApiResult<String> apiResult = employeeApi.addEmployee(employeeDTO);
        if(apiResult == null){
            result = "2";
        }else if(!apiResult.isSuccess()){
            log.error(apiResult.getErrorCode() + ":" +apiResult.getErrorMsg());
           result = "1";
        }

        return result;
    }

    @RequestMapping("/edit")
    @ResponseBody
    public String editEmployee(EmployeeDTO employeeDTO, HttpSession session){
        String result = "0";

        Long lastUpdateBy = 0L;
        if(session != null && session.getAttribute("staff") != null){
            lastUpdateBy = ((StaffDTO)session.getAttribute("staff")).getStaffId();
        }
        employeeDTO.setLastUpdateBy(lastUpdateBy);
        ApiResult<String> apiResult = employeeApi.editEmployee(employeeDTO);
        if(apiResult == null){
            result = "1";
        } else if(!apiResult.isSuccess()){
            log.error(apiResult.getErrorCode() + ":" +apiResult.getErrorMsg());
            result = "1";
        }

        return result;
    }

    @RequestMapping("/delete")
    @ResponseBody
    public String deleteEmployee(Long activeId, HttpSession session){
        String result = "0";

        Long lastUpdateBy = 0L;
        if(session != null && session.getAttribute("staff") != null){
            lastUpdateBy = ((StaffDTO)session.getAttribute("staff")).getStaffId();
        }

        ApiResult<String> apiResult = employeeApi.deleteEmployee(activeId,lastUpdateBy);
        if(apiResult == null){
            result = "1";
        } else if(!apiResult.isSuccess()){
            log.error(apiResult.getErrorCode() + ":" +apiResult.getErrorMsg());
            result = "1";
        }

        return result;
    }

    @RequestMapping("/upload")
    @ResponseBody
    public String upload(MultipartFile file,HttpSession session) throws Exception{
        String result = "0";

        List<EmployeeDTO> employeeDTOS = new ArrayList<>();
        Long createBy = 0L;
        if(session != null && session.getAttribute("staff") != null){
            createBy = ((StaffDTO)session.getAttribute("staff")).getStaffId();
        }
        if (!file.isEmpty()) {
            Workbook workbook = ControllerUtil.getWorkbookAuto(file);
            Sheet sheet = workbook.getSheetAt(0);
            int rows = sheet.getPhysicalNumberOfRows();
            for (int i = 1;i<rows;i++){
                Row row = sheet.getRow(i);
                EmployeeDTO employeeDTO = new EmployeeDTO();
                employeeDTO.setEmployeeCode(Integer.parseInt(row.getCell(0).toString().split("\\.")[0]));
                employeeDTO.setEmployeeName(row.getCell(1).toString());
                employeeDTO.setEmployeeIdCard(row.getCell(2).toString().split("\\.")[0]);
                employeeDTO.setDescription(row.getCell(3).toString());
                employeeDTO.setCreateBy(createBy);
                employeeDTOS.add(employeeDTO);
            }
        }
        if(!employeeDTOS.isEmpty()){
            ApiResult<String> apiResult = employeeApi.uploadEmployee(employeeDTOS);
            if(apiResult == null || !apiResult.isSuccess()){
                log.error(apiResult.getErrorCode() + ":" +apiResult.getErrorMsg());
                throw new Exception("数据存储出错");
            }
        }
        return result;
    }

    @RequestMapping(value = "/downModel",method = RequestMethod.GET,produces="application/json")
    public void downModel(HttpServletResponse response, HttpServletRequest request) {
        String fileName = request.getParameter("fileName");
        ServletOutputStream out;
        response.setContentType("multipart/form-data");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        try{
//            String path = ClassUtils.getDefaultClassLoader().getResource("").getPath();
//            String filePath = path + "static/" +fileName;
//            String userAgent = request.getHeader("User-Agent");
//            if (userAgent.contains("MSIE") || userAgent.contains("Trident")) {
//                fileName = URLEncoder.encode(fileName, "UTF-8");
//            } else {
//                // 非IE浏览器的处理：
//                fileName = new String((fileName).getBytes("UTF-8"), "ISO-8859-1");
//            }
//            filePath = URLDecoder.decode(filePath, "UTF-8");
            response.setHeader("Content-Disposition", "attachment;fileName=" + fileName);
//            FileInputStream inputStream = new FileInputStream(filePath);
            InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("static/"+fileName);
            out = response.getOutputStream();
            int b = 0;
            byte[] buffer = new byte[1024];
            while ((b = inputStream.read(buffer)) != -1) {
                out.write(buffer, 0, b);
            }
            inputStream.close();

            if (out != null) {
                out.flush();
                out.close();
            }
        }catch (Exception e){
            log.error(e.getMessage());
        }
    }
}
