package com.fawvw.cloud;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.fawvw.cloud.mapper")
public class ZhongxingProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZhongxingProviderApplication.class, args);
    }

}
