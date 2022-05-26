package com.liugang.learn.controller;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.EntryType;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FlowController {

    @GetMapping("/testSentinel")
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
}
