package com.heythere.video;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableEurekaClient
@EnableCircuitBreaker
@EnableJpaAuditing
@SpringBootApplication
public class VideoApplication {
    public static void main(String... args) {
        SpringApplication.run(VideoApplication.class, args);
    }
}
