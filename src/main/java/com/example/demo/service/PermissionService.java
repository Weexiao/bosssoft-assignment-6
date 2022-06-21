package com.example.demo.service;

import com.example.demo.entity.po.PermissionPO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.entity.vo.PermissionVO;

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

    /**
     * 查询菜单列表
     * @param permissionVO
     * @return
     */
    List<PermissionPO> findPermissionList(PermissionVO permissionVO);

    /**
     * 查询上级菜单列表
     * @return
     */
    List<PermissionPO> findParentPermissionList();

    /**
     * 查询菜单是否存在子菜单
     * @param permissionId
     * @return
     */
    boolean hasChildrenOfPermission(Long permissionId);
}
