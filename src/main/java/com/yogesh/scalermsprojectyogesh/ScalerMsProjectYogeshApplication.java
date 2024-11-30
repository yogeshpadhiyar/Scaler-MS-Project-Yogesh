package com.yogesh.scalermsprojectyogesh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
@EnableAspectJAutoProxy
@EnableJpaAuditing
public class ScalerMsProjectYogeshApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScalerMsProjectYogeshApplication.class, args);
    }

}
