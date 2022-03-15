package com.silvericekey.cloudstorage;

import com.silvericekey.cloudstorage.common.Constants;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

/**
 * @author SilverIceKey
 */
@SpringBootApplication
@MapperScan(Constants.SERVICE_PACKAGE+".features.*.mapper")
public class CloudStorageApplication {

    public static void main(String[] args) {
        SpringApplication.run(CloudStorageApplication.class, args);
    }

}
