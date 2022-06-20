package com.example.demo.service;

import com.example.demo.entity.po.PermissionPO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wuhu
 * @since 2022-06-20
 */
public interface PermissionService extends IService<PermissionPO> {

    /**
     * 根据用户ID查询权限列表
     * @param userId
     * @return
     */
    List<PermissionPO> findPermissionListByUserId(Long userId);
}
