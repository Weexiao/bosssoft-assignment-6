package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.demo.dao.UserMapper;
import com.example.demo.entity.po.RolePO;
import com.example.demo.dao.RoleMapper;
import com.example.demo.entity.po.UserPO;
import com.example.demo.entity.vo.RoleVO;
import com.example.demo.service.RoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;

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
public class RoleServiceImpl extends ServiceImpl<RoleMapper, RolePO> implements RoleService {

    @Resource
    private UserMapper userMapper;

    /**
     * 根据角色查询角色列表
     *
     * @param page
     * @param roleVO
     * @return
     */
    @Override
    public IPage<RolePO> findRoleListByUserId(IPage<RolePO> page, RoleVO roleVO) {
        // 创建条件构造器
        QueryWrapper<RolePO> queryWrapper = new QueryWrapper<>();
        // 角色名称
        queryWrapper.like(!ObjectUtils.isEmpty(roleVO.getRoleName()),
                "role_name", roleVO.getRoleName());
        // 排序
        queryWrapper.orderByAsc("id");
        // 根据用户Id查询用户信息
        UserPO userPO = userMapper.selectById(roleVO.getUserId());
        // 如果用户不为空，且不是管理员，则只查询自己创建的角色
        if (!ObjectUtils.isEmpty(userPO) && !ObjectUtils.isEmpty(userPO.getIsAdmin())
                && userPO.getIsAdmin() != 1) {
            queryWrapper.eq("create_user", roleVO.getUserId());
        }
        return baseMapper.selectPage(page, queryWrapper);
    }
}
