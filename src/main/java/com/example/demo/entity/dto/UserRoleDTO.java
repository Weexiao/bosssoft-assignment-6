package com.example.demo.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 用于给用户分配角色时保存选中的角色信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRoleDTO {

    private Long userId;
    private List<Long> roleIds;

}
