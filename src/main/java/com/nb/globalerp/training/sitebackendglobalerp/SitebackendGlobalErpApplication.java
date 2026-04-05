package com.nb.globalerp.training.sitebackendglobalerp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableConfigurationProperties
@ConfigurationPropertiesScan
@SpringBootApplication
@EnableScheduling
@EnableAsync
public class SitebackendGlobalErpApplication {

    public static void main(String[] args) {
        SpringApplication.run(SitebackendGlobalErpApplication.class, args);
    }

}

