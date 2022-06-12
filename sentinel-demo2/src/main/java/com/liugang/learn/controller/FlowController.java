package com.liugang.learn.controller;

import com.alibaba.csp.sentinel.*;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.liugang.learn.service.AsyncService;
import com.sun.deploy.security.BlockedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class FlowController {

    @Autowired
    private AsyncService asyncService;

    @GetMapping("/testSentinel")
    @ResponseBody
    public String testSentinel1(){
        String resourceName = "testSentinel1";
        String retVal;
        Entry entry = null;
        try{
            entry = SphU.entry(resourceName, EntryType.IN);
            retVal = "passed";
        }catch (BlockException e){
            retVal = "blocked";
        }
        finally {
            if (entry!=null){
                entry.exit();
            }
        }
        return retVal;
    }

    @GetMapping("/testSentinel2")
    @ResponseBody
    public String testSentinel2(){
        if(SphO.entry("testSentinel2")){
            try{
                //被保护的业务逻辑
                return "sentinel 你好啊！"+System.currentTimeMillis()  ;
            }finally{
                SphO.exit();
            }
        }else{
            //资源访问阻止，被限流或被降级
            //进行相应的处理操作
            return "系统繁忙，请稍后！";
        }
    }

    /**
     * 注解方式定义资源
     * @return value 资源名称
     * 调用被限流/降级/系统保护的时候调用方法
     */
    @SentinelResource(value = "testSentinel3",blockHandler = "blockHandlerForHelloWorld13")
    @GetMapping("/testSentinel3")
    @ResponseBody
    public String testSentinel3(){
        return "testSentinel3:"+System.currentTimeMillis();
    }

    @ResponseBody
    public String blockHandlerForHelloWorld13(BlockException exception){
        exception.printStackTrace();
        return "系统繁忙！";
    }

    @RequestMapping("/testSentinel4")
    @ResponseBody
    public void testSentinel4(){
        AsyncEntry asyncEntry = null;
        try {
            asyncEntry = SphU.asyncEntry("testSentinel4");
            asyncService.doSomethingAsync();
        } catch (BlockException e) {
            System.out.println("系统繁忙，请稍后！");
        }finally {
            if (asyncEntry!=null){
                asyncEntry.exit();
            }
        }
    }

}
