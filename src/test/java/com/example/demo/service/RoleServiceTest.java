package com.example.demo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.entity.dto.RolePermissionDTO;
import com.example.demo.entity.po.RolePO;
import com.example.demo.entity.vo.RoleVO;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import javax.annotation.Resource;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RoleServiceTest {

    @Resource
    private RoleService roleService;
    @Resource
    private PermissionService permissionService;

    @Test
    @Order(1)
    void findRoleListByUserId() {
        RoleVO roleVO = new RoleVO();

        IPage<RolePO> page = new Page<>(roleVO.getPageNo(), roleVO.getPageSize());
        // 调用分页查询方法
        Assert.isTrue(roleService.findRoleListByUserId(page, roleVO).getRecords().size() > 0, "查询失败");
        System.out.println(page.getRecords());
    }

    @Test
    @Order(2)
    void add(){
        RolePO rolePO = new RolePO();
        rolePO.setRoleCode("MY_TEST");
        rolePO.setRoleName("测试角色");
        rolePO.setCreateUser(1L);

        Assert.isTrue(roleService.save(rolePO), "添加失败");
    }

    @Test
    @Order(3)
    void update(){
        RolePO rolePO = roleService.getById(6L);
        rolePO.setRoleCode("MY_TEST_UPDATE");

        Assert.isTrue(roleService.updateById(rolePO), "更新失败");
    }

    @Test
    @Order(4)
    void getAssignPermissionTree(){
        Assert.notNull(permissionService.getAssignPermissionTree(1L, 1L), "查询失败");
    }

    @Test
    @Order(5)
    void saveRolePermission() {
        RolePermissionDTO rolePermissionDTO = new RolePermissionDTO();
        rolePermissionDTO.setRoleId(5L);
        rolePermissionDTO.setPermissionIds(new ArrayList(){{add(1L);add(2L);add(3L);}});
        Assert.isTrue(roleService.saveRolePermission(rolePermissionDTO.getRoleId(), rolePermissionDTO.getPermissionIds()), "添加失败");
    }

    @Test
    @Order(6)
    void getRoleCount() {
        Assert.isTrue(roleService.getRoleCount(1L), "查询失败");
    }

    @Test
    @Order(7)
    void deleteRoleById() {
        Assert.isTrue(roleService.deleteRoleById(6L), "删除失败");
    }

    @Test
    @Order(8)
    void getRoleIdsByUserId() {
        Assert.notNull(roleService.getRoleIdsByUserId(1L), "查询失败");
    }
}