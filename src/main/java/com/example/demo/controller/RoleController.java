package com.example.demo.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.Result;
import com.example.demo.entity.po.RolePO;
import com.example.demo.entity.vo.RoleVO;
import com.example.demo.service.RoleService;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wuhu
 * @since 2022-06-20
 */
@RestController
@RequestMapping("/role")
public class RoleController {

    @Resource
    private RoleService roleService;

    /**
     * 分页查询角色列表
     * @param roleVO
     * @return
     */
    @GetMapping("/list")
    public Result findRoleListByUserId(RoleVO roleVO) {
        // 创建分页对象
        IPage<RolePO> page = new Page<>(roleVO.getPageNo(), roleVO.getPageSize());
        // 调用分页查询方法
        roleService.findRoleListByUserId(page, roleVO);
        // 返回结果
        return Result.ok(page);
    }

    /**
     * 添加角色
     * @param rolePO
     * @return
     */
    @PostMapping("/add")
    public Result add(@RequestBody RolePO rolePO) {
        if (roleService.save(rolePO)) {
            return Result.ok().message("角色添加成功");
        }
        return Result.error().message("角色添加失败");
    }

    /**
     * 修改角色
     * @param rolePO
     * @return
     */
    @PutMapping("/update")
    public Result update(@RequestBody RolePO rolePO) {
        if (roleService.updateById(rolePO)) {
            return Result.ok().message("角色修改成功");
        }
        return Result.error().message("角色修改失败");
    }

    /**
     * 删除角色
     * @param id
     * @return
     */
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Long id) {
        if (roleService.removeById(id)) {
            return Result.ok().message("角色删除成功");
        }
        return Result.error().message("角色删除失败");
    }
}

