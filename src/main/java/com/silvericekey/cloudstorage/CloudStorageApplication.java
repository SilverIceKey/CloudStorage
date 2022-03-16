package com.silvericekey.cloudstorage;

import com.silvericekey.cloudstorage.common.Constants;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import springfox.documentation.oas.annotations.EnableOpenApi;

/**
 * @author SilverIceKey
 */
@EnableOpenApi
@SpringBootApplication
@MapperScan(Constants.SERVICE_PACKAGE+".features.*.mapper")
public class CloudStorageApplication {

    public static void main(String[] args) {
        SpringApplication.run(CloudStorageApplication.class, args);
    }

}
