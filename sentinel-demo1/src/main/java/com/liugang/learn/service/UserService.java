package com.liugang.learn.service;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.liugang.learn.model.User;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Collections;

/**
 * 对getUser 进行限流
 * 使用抛出异常的方式，进行埋点
 */
@Service
public class UserService {

    public static final String USER_RES="userResource";

    @PostConstruct
    public void initUserService(){
        //定义热点限流的规则，对第一个参数设置qps限流模式，阈值为5
        FlowRule rule = new FlowRule();
        rule.setResource(USER_RES);
        //限流类型qps
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        //设置阈值,每秒最多通过的请求个数
        rule.setCount(2);
        //设置哪个调用方
        rule.setLimitApp(RuleConstant.LIMIT_APP_DEFAULT);
        //基于调用关系的流量控制
        rule.setStrategy(RuleConstant.CONTROL_BEHAVIOR_DEFAULT);
        // 流控策略
        rule.setControlBehavior(RuleConstant.CONTROL_BEHAVIOR_DEFAULT);
        FlowRuleManager.loadRules(Collections.singletonList(rule));
    }

    public Object getUser(Long uid){
        Entry entry = null;
        try{
            //流控代码
            entry = SphU.entry(USER_RES);
            //业务代码
            User user = new User();
            user.setId(uid);
            user.setUserName("user-"+uid);
            return user;
        }catch (BlockException e){
            //被限流了
            System.out.println("[getUser] has been protected! Time="+System.currentTimeMillis());
            return "系统繁忙，请稍后！"; //降低处理
        }finally {
            if (entry!=null){
                entry.exit();
            }
        }
    }
}
