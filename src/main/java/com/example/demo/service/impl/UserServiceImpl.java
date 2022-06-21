package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.entity.po.UserPO;
import com.example.demo.dao.UserMapper;
import com.example.demo.entity.vo.UserVO;
import com.example.demo.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;

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

    /**
     * 分页查询用户信息
     *
     * @param page
     * @param userVO
     * @return
     */
    @Override
    public IPage<UserPO> findUserListByPage(IPage<UserPO> page, UserVO userVO) {
        // 创建条件构造器对象
        QueryWrapper<UserPO> queryWrapper = new QueryWrapper<>();
        // 部门编号
        queryWrapper.eq(
                !ObjectUtils.isEmpty(userVO.getDepartmentId()),
                "dept_id", userVO.getDepartmentId()
        );
        // 用户名
        queryWrapper.like(
                !ObjectUtils.isEmpty(userVO.getUsername()),
                "username", userVO.getUsername()
        );
        // 真实姓名
        queryWrapper.like(
                !ObjectUtils.isEmpty(userVO.getRealName()),
                "real_name", userVO.getRealName()
        );
        // 电话
        queryWrapper.like(
                !ObjectUtils.isEmpty(userVO.getPhone()),
                "phone", userVO.getPhone()
        );
        // 查询并返回数据
        return baseMapper.selectPage(page, queryWrapper);
    }

    /**
     * 删除用户信息
     *
     * @param userId
     * @return
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public boolean deleteUserByUserId(Long userId) {
        // 查询
        UserPO item = baseMapper.selectById(userId);
        // 删除用户角色关系
        baseMapper.deleteUserRoleByUserId(userId);
        // 删除用户
        if (baseMapper.deleteById(userId) > 0) {
            // 判断用户是否存在
            return true;
        }
        return false;
    }

    /**
     * 分配角色
     *
     * @param userId
     * @param roleIds
     * @return
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public boolean saveUserRole(Long userId, List<Long> roleIds) {
        // 删除用户角色关系
        baseMapper.deleteUserRoleByUserId(userId);
        // 添加用户角色关系
        return baseMapper.saveUserRole(userId, roleIds) > 0;
    }

    /**
     * 修改用户密码
     *
     * @param userPO
     * @return
     */
    @Override
    public boolean updatePassword(UserPO userPO) {
        // 创建条件构造器对象
        QueryWrapper<UserPO> queryWrapper = new QueryWrapper<>();
        // 用户名
        queryWrapper.eq("username", userPO.getUsername());
        // 查询
        UserPO item = baseMapper.selectOne(queryWrapper);
        // 判断用户是否存在
        if (ObjectUtils.isEmpty(item)) {
            return false;
        }
        // 修改密码
        item.setPassword(userPO.getPassword());
        // 修改
        return baseMapper.updateById(item) > 0;
    }
}
