package com.example.demo.service;

import com.example.demo.entity.po.PermissionPO;
import com.example.demo.entity.vo.PermissionVO;
import com.example.demo.entity.vo.RolePermissionVO;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import javax.annotation.Resource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PermissionServiceTest {

    @Resource
    private PermissionService permissionService;

    @Test
    @Order(1)
    void findPermissionListByUserId() {
        List<PermissionPO> permissionList = permissionService.findPermissionListByUserId(1L);
        Assert.notNull(permissionList, "查询用户权限列表失败");
        System.out.println(permissionList);
    }

    @Test
    @Order(2)
    void findPermissionList() {
        PermissionVO permissionVO = new PermissionVO();
        List<PermissionPO> permissionList = permissionService.findPermissionList(permissionVO);
        Assert.notNull(permissionList, "查询菜单列表失败");
        System.out.println(permissionList);
    }

    @Test
    @Order(3)
    void findParentPermissionList() {
        List<PermissionPO> permissionList = permissionService.findParentPermissionList();
        Assert.notNull(permissionList, "查询上级菜单列表失败");
        System.out.println(permissionList);
    }

    @Test
    @Order(4)
    void getMenuById() {
        PermissionPO permission = permissionService.getById(1L);
        Assert.notNull(permission, "查询菜单失败");
        System.out.println(permission);
    }

    @Test
    @Order(5)
    void add() {
        PermissionPO permission = new PermissionPO();
        permission.setName("测试");
        permission.setUrl("/mytest");
        permission.setParentId(0L);
        permission.setType(0);
        permission.setLabel("测试");
        permission.setParentName("顶级菜单");
        permission.setCode("mytest");
        permission.setPath("/mytest");

        Assert.isTrue(permissionService.save(permission), "添加菜单失败");
    }

    @Test
    @Order(6)
    void update() {
        PermissionPO permission = permissionService.getById(31L);
        permission.setName("测试更新");

        Assert.isTrue(permissionService.updateById(permission), "更新菜单失败");
    }

    @Test
    @Order(7)
    void delete() {
        PermissionPO permission = permissionService.getById(31L);
        Assert.isTrue(permissionService.removeById(permission), "删除菜单失败");
    }

    @Test
    @Order(8)
    void hasChildrenOfPermission() {
        Assert.isTrue(permissionService.hasChildrenOfPermission(1L), "查询菜单是否存在子菜单失败");
    }

    @Test
    @Order(9)
    void getAssignPermissionTree() {
        RolePermissionVO rolePermissionVO = permissionService.getAssignPermissionTree(1L, 1L);
        Assert.notNull(rolePermissionVO, "查询分配设备树列表失败");
        System.out.println(rolePermissionVO);
    }
}