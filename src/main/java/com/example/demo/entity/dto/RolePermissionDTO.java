package com.example.demo.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 用于给角色分配权限时保存选中的权限数据
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class RolePermissionDTO {
    private Long roleId; // 角色ID
    private List<Long> permissionIds; // 权限菜单ID列表
}
