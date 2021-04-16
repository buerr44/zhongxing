package com.fawvw.cloud.controller;

import com.alibaba.dubbo.config.annotation.Service;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @author HuangGang
 * @create 2021-02-02 20:14
 **/
@Service
public class ControllerUtil {

    public static String fileUpload(String uploadPath,MultipartFile file, HttpServletRequest request) throws IOException {
        //文件夹中通过日期对上传的文件归类保存
        if(file != null && request != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd/");
            String format = sdf.format(new Date());
            File folder = new File(uploadPath + format);
            if (!folder.isDirectory()) {
                folder.mkdirs();
            }
            // 对上传的文件重命名，避免文件重名
            String oldName = file.getOriginalFilename();
            String newName = UUID.randomUUID().toString()
                    + oldName.substring(oldName.lastIndexOf("."), oldName.length());
            // 文件保存
            file.transferTo(new File(folder, newName));
            // 返回上传文件的访问路径
            String realFilePath = request.getScheme() + "://" + request.getServerName()
                    + ":" + request.getServerPort() + "/" + format + newName;
            String filePath = "/" + format + newName;

            return filePath;
        }else{
            return "";
        }
    }
    /**
     *
     * @param pwd
     * @return
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    public static String EncoderByMd5(String pwd) throws NoSuchAlgorithmException, UnsupportedEncodingException {

        MessageDigest md5 = MessageDigest.getInstance("MD5");
        byte[] bytes = md5.digest(pwd.getBytes("utf-8"));
        String newPwd = transform(bytes);
        return newPwd;
    }
    /**
     *
     * @param bytes
     * @return
     */
    private static String transform(byte[] bytes) {
        StringBuffer result = new StringBuffer();
        for (int i = 0; i < bytes.length; i++) {
            int  temp = bytes[i] & 0xff;
            String tempStr = Integer.toHexString(temp);
            if(tempStr.length() < 2) {
                result.append("0"+tempStr);
            }else {
                result.append(tempStr);
            }
        }
        return result.toString();
    }

    /**
     * 自动判断文件类型
     * @param file
     * @return
     * @throws IOException
     */
    public static Workbook getWorkbookAuto(MultipartFile file) throws IOException {
        /** 判断文件的类型，是2003还是2007 */
        boolean isExcel2003 = true;
        if (isExcel2007(file.getOriginalFilename())) {
            isExcel2003 = false;
        }
        BufferedInputStream is = new BufferedInputStream(
                file.getInputStream());
        Workbook wb;
        if (isExcel2003) {
            wb = new HSSFWorkbook(is);
        } else {
            wb = new XSSFWorkbook(is);
        }
        return wb;
    }

    public static boolean isExcel2003(String filePath) {
        return filePath.matches("^.+\\.(?i)(xls)$");
    }

    public static boolean isExcel2007(String filePath) {
        return filePath.matches("^.+\\.(?i)(xlsx)$");
    }

}
