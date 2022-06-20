package com.example.demo.service.impl;

import com.example.demo.entity.po.PermissionPO;
import com.example.demo.dao.PermissionMapper;
import com.example.demo.service.PermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, PermissionPO> implements PermissionService {

    /**
     * 根据用户ID查询权限列表
     *
     * @param userId
     * @return
     */
    @Override
    public List<PermissionPO> findPermissionListByUserId(Long userId) {
        return baseMapper.findPermissionListByUserId(userId);
    }
}
