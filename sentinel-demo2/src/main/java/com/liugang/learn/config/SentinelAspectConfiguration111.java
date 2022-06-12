package com.liugang.learn.config;

import com.alibaba.csp.sentinel.annotation.aspectj.SentinelResourceAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author amazfit
 * @date 2022-06-12 下午7:24
 **/
@Configuration
public class SentinelAspectConfiguration111 {

    @Bean
    public SentinelResourceAspect sentinelAspectConfiguration(){
        return new SentinelResourceAspect();
    }
}
