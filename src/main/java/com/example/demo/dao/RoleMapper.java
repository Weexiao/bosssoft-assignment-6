package com.example.demo.dao;

import com.example.demo.entity.po.RolePO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wuhu
 * @since 2022-06-20
 */
public interface RoleMapper extends BaseMapper<RolePO> {

    /**
     * 保存用户权限关系
     * @param roleId
     * @param permissionIds
     * @return
     */
    int saveRolePermission(@Param("roleId") Long roleId, @Param("permissionIds") List<Long> permissionIds);

    /**
     * 判断该角色是否有用户
     * @param roleId
     * @return
     */
    @Select("select count(1) from sys_user_role where role_id = #{roleId}")
    int getRoleCount(Long roleId);

    /**
     * 删除角色权限信息
     * @param roleId
     */
    @Delete("delete from sys_role_permission where role_id = #{roleId}")
    void deleteRolePermissionByRoleId(Long roleId);

    /**
     * 根据用户ID查询已经拥有的角色ID
     * @param userId
     * @return
     */
    @Select("select role_id from sys_user_role where user_id = #{userId}")
    List<Long> getRoleIdsByUserId(Long userId);
}
