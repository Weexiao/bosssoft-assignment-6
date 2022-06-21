package com.example.demo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.Result;
import com.example.demo.entity.dto.UserRoleDTO;
import com.example.demo.entity.po.RolePO;
import com.example.demo.entity.po.UserPO;
import com.example.demo.entity.vo.RoleVO;
import com.example.demo.entity.vo.UserVO;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

import javax.annotation.Resource;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserServiceTest {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    @Order(1)
    void getUserByUsername() {
        Assert.notNull(userService.getUserByUsername("admin"), "查询失败");
    }

    @Test
    @Order(2)
    void findUserListByPage() {
        UserVO userVO = new UserVO();

        IPage<UserPO> page = new Page<>(userVO.getPageNo(), userVO.getPageSize());
        // 调用分页查询方法
        Assert.isTrue(userService.findUserListByPage(page, userVO).getRecords().size() > 0, "查询失败");
        System.out.println(page.getRecords());
    }

    @Test
    @Order(3)
    void add() {
        UserPO userPO = new UserPO();
        userPO.setUsername("ddddd");
        userPO.setPassword(passwordEncoder.encode("123456"));
        userPO.setNickName("ddddd");
        userPO.setRealName("ddddd");
        userPO.setEmail("2626262626@qq.com");
        userPO.setPhone("13888888888");
        userPO.setGender(1);

        Assert.isTrue(userService.save(userPO), "添加失败");
    }

    @Test
    @Order(4)
    void update() {
        UserPO userPO = userService.getUserByUsername("ddddd");
        userPO.setNickName("bbbbb");
        Assert.isTrue(userService.updateById(userPO), "更新失败");
    }


    @Test
    @Order(5)
    void deleteUserByUserId() {
        UserPO userPO = userService.getUserByUsername("ddddd");

        Assert.isTrue(userService.deleteUserByUserId(userPO.getId()), "删除失败");
    }

    @Test
    @Order(6)
    void getRoleListForAssign(){
        RoleVO roleVO = new RoleVO();

        IPage<RolePO> page = new Page<RolePO>(roleVO.getPageNo(), roleVO.getPageSize());
        // 调用获取分配角色列表的方法
        Assert.isTrue(roleService.findRoleListByUserId(page, roleVO).getRecords().size() > 0, "查询失败");
    }

    @Test
    @Order(7)
    void getRoleListByUserId(){
        Assert.isTrue(roleService.getRoleIdsByUserId(1L).size() > 0, "查询失败");
    }

    @Test
    @Order(8)
    void saveUserRole() {
        UserRoleDTO userRoleDTO = new UserRoleDTO();
        userRoleDTO.setUserId(9L);
        userRoleDTO.setRoleIds(new ArrayList(){{add(3L);}});

        Assert.isTrue(userService.saveUserRole(userRoleDTO.getUserId(), userRoleDTO.getRoleIds()), "更新失败");
    }

    @Test
    @Order(9)
    void updatePassword() {
        UserPO userPO = userService.getUserByUsername("admin");
        userPO.setPassword(passwordEncoder.encode("123456"));

        Assert.isTrue(userService.updatePassword(userPO), "更新失败");
    }
}