package com.liugang.learn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class SentinelStartDemo2{

    public static void main(String[] args) {
        SpringApplication.run(SentinelStartDemo2.class,args);
    }
}
