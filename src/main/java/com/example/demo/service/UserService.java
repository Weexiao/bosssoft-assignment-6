package com.example.demo.service;

import com.example.demo.entity.po.UserPO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wuhu
 * @since 2022-06-20
 */
public interface UserService extends IService<UserPO> {

    /**
     * 根据用户名查询用户
     * @param username
     * @return UserPO
     */
    UserPO getUserByUsername(String username);

}