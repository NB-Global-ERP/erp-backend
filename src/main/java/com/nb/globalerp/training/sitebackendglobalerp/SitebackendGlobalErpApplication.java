package com.nb.globalerp.training.sitebackendglobalerp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableAsync
@EnableScheduling
public class SitebackendGlobalErpApplication {

    public static void main(String[] args) {
        SpringApplication.run(SitebackendGlobalErpApplication.class, args);
    }

}
