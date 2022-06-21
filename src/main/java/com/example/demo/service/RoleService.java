package com.example.demo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.demo.entity.po.RolePO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.entity.vo.RoleVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 * Note: 非管理员只能查询自己创建的角色列表
 * @author wuhu
 * @since 2022-06-20
 */
public interface RoleService extends IService<RolePO> {

    /**
     * 根据角色查询角色列表
     * @param page
     * @param roleVO
     * @return
     */
    IPage<RolePO> findRoleListByUserId(IPage<RolePO> page, RoleVO roleVO);

    /**
     * 保存用户权限关系
     * @param roleId
     * @param permissionIds
     * @return
     */
    boolean saveRolePermission(Long roleId, List<Long> permissionIds);

    /**
     * 检查角色是否被分配
     * @param roleId
     * @return
     */
    boolean getRoleCount(Long roleId);

    /**
     * 根据id删除用户信息
     * @param id
     * @return
     */
    boolean deleteRoleById(Long id);

    /**
     * 根据用户ID查询已经拥有的角色ID
     * @param userId
     * @return
     */
    List<Long> getRoleIdsByUserId(Long userId);
}
