package com.example.demo.entity.vo;

import com.example.demo.entity.po.PermissionPO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RolePermissionVO {

    /**
     * 菜单数据
     */
    private List<PermissionPO> permissionPOList = new ArrayList<>();

    /**
     * 该角色原来分配的菜单数据
     */
    private Object[] checkList;

}
