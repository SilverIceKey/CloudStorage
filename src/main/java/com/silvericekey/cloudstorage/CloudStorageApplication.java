package com.silvericekey.cloudstorage;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author SilverIceKey
 */
@SpringBootApplication
@MapperScan("com.silvericekey.cloudstorage.dao.mapper")
@ComponentScan(basePackages = {"com.silvericekey.cloudstorage.config", "com.silvericekey.cloudstorage.controller"})
public class CloudStorageApplication {

    public static void main(String[] args) {
        SpringApplication.run(CloudStorageApplication.class, args);
    }

}
