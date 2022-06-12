package com.liugang.learn.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @author amazfit
 * @date 2022-06-12 下午8:53
 **/
@Service
public class AsyncService {

    @Async
    public void doSomethingAsync(){
        System.out.println("async start....");
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("async end...");
    }
}
