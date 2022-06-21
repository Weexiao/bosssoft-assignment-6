package com.example.demo.service;

import com.example.demo.dao.UserMapper;
import com.example.demo.entity.po.DepartmentPO;
import com.example.demo.entity.vo.DepartmentVO;
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
class DepartmentServiceTest {

    @Resource
    private DepartmentService departmentService;
    @Resource
    private UserMapper userMapper;

    @Test
    @Order(1)
    void findDepartmentList() {
        DepartmentVO departmentVO = new DepartmentVO();
        List<DepartmentPO> departmentList = departmentService.findDepartmentList(departmentVO);
        Assert.notNull(departmentList, "查询部门列表失败");
        System.out.println(departmentList);
    }

    @Test
    @Order(2)
    void findParentDepartment() {
        List<DepartmentPO> departmentList = departmentService.findParentDepartment();
        Assert.notNull(departmentList, "查询上级部门列表失败");
        System.out.println(departmentList);
    }

    @Test
    @Order(3)
    void hasChildOfDepartment() {
        boolean hasChildOfDepartment = departmentService.hasChildOfDepartment(1L);
        Assert.isTrue(hasChildOfDepartment, "查询部门是否有子部门失败");
        System.out.println(hasChildOfDepartment);
    }

    @Test
    @Order(4)
    void hasUserOfDepartment() {
        boolean hasUserOfDepartment = departmentService.hasUserOfDepartment(1L);
        Assert.isTrue(hasUserOfDepartment, "查询部门是否有用户失败");
        System.out.println(hasUserOfDepartment);
    }

    @Test
    @Order(5)
    void add() {
        DepartmentVO departmentVO = new DepartmentVO();
        departmentVO.setDepartmentName("测试部门");
        departmentVO.setPhone("13935026149");
        departmentVO.setAddress("测试地址");
        departmentVO.setPid(0L);
        departmentVO.setParentName("顶级部门");
        departmentVO.setOrderNum(0);

        Assert.isTrue(departmentService.save(departmentVO), "添加部门失败");
    }

    @Test
    @Order(6)
    void update() {
        DepartmentVO departmentVO = new DepartmentVO();
        departmentVO.setId(8L);
        departmentVO.setDepartmentName("测试部门");
        departmentVO.setPhone("13935026149");
        departmentVO.setAddress("测试地址");
        departmentVO.setPid(0L);
        departmentVO.setParentName("顶级部门");
        departmentVO.setOrderNum(0);

        Assert.isTrue(departmentService.updateById(departmentVO), "修改部门失败");
    }

    @Test
    @Order(7)
    void delete() {
        Assert.isTrue(departmentService.removeById(8L), "删除部门失败");
    }
}