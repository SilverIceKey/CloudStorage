package com.silvericekey.cloudstorage;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author SilverIceKey
 */
@SpringBootApplication
@MapperScan("com.silvericekey.cloudstorage.features")
@ComponentScan(basePackages = {"com.silvericekey.cloudstorage.config", "com.silvericekey.cloudstorage.features"})
public class CloudStorageApplication {

    public static void main(String[] args) {
        SpringApplication.run(CloudStorageApplication.class, args);
    }

}
