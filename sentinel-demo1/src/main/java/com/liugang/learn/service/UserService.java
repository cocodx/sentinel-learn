package com.liugang.learn.service;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.liugang.learn.model.User;
import org.springframework.stereotype.Service;

/**
 * 对getUser 进行限流
 * 使用抛出异常的方式，进行埋点
 */
@Service
public class UserService {

    public static final String USER_RES="userResource";

    public User getUser(Long uid){
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
        }finally {
            if (entry!=null){
                entry.exit();
            }
        }
        return null;
    }
}
