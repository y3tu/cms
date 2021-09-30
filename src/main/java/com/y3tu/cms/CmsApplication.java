package com.y3tu.cms;

import com.y3tu.tools.web.annotation.EnableCors;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 启动类
 *
 * @author y3tu
 */
@SpringBootApplication
@EnableCors
public class CmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(CmsApplication.class, args);
    }

}
