package com.example.demo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.entity.po.UserPO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.entity.vo.UserVO;

import java.util.List;

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

    /**
     * 分页查询用户信息
     * @param page
     * @param userVO
     * @return
     */
    IPage<UserPO> findUserListByPage(IPage<UserPO> page, UserVO userVO);

    /**
     * 删除用户信息
     * @param userId
     * @return
     */
    boolean deleteUserByUserId(Long userId);

    /**
     * 分配角色
     * @param userId
     * @param roleIds
     * @return
     */
    boolean saveUserRole(Long userId, List<Long> roleIds);
}
