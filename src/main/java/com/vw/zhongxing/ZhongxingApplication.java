package com.vw.zhongxing;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Buer44
 */
@SpringBootApplication
@MapperScan("com.vw.zhongxing.dao")
public class ZhongxingApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZhongxingApplication.class, args);
    }

}
