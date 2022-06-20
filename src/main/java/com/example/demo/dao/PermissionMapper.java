package com.example.demo.dao;

import com.example.demo.entity.po.PermissionPO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wuhu
 * @since 2022-06-20
 */
public interface PermissionMapper extends BaseMapper<PermissionPO> {

    /**
     * 根据用户ID查询权限列表
     * @param userId
     * @return
     */
    List<PermissionPO> findPermissionListByUserId(Long userId);
}
