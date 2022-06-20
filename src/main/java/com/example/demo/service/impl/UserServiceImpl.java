package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.entity.po.UserPO;
import com.example.demo.dao.UserMapper;
import com.example.demo.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wuhu
 * @since 2022-06-20
 */
@Service
@Transactional
public class UserServiceImpl extends ServiceImpl<UserMapper, UserPO> implements UserService {

    /**
     * 根据用户名查询用户
     *
     * @param username
     * @return UserPO
     */
    @Override
    public UserPO getUserByUsername(String username) {
        // 创建条件构造器对象
        QueryWrapper<UserPO> queryWrapper = new QueryWrapper<>();
        // 用户名
        queryWrapper.eq("username", username);
        // 查询
        return baseMapper.selectOne(queryWrapper);
    }
}
