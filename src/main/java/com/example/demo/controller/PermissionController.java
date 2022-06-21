package com.example.demo.controller;


import com.example.demo.common.Result;
import com.example.demo.entity.po.PermissionPO;
import com.example.demo.entity.vo.PermissionVO;
import com.example.demo.service.PermissionService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wuhu
 * @since 2022-06-20
 */
@RestController
@RequestMapping("/permission")
public class PermissionController {

    @Resource
    private PermissionService permissionService;

    /**
     * 查询菜单列表
     * @param permissionVO
     * @return
     */
    @GetMapping("/list")
    public Result getMenuList(PermissionVO permissionVO){
        // 查询菜单列表
        List<PermissionPO> list = permissionService.findPermissionList(permissionVO);
        // 返回数据
        return Result.ok(list);
    }

    /**
     * 查询上级菜单列表
     * @return
     */
    @GetMapping("/parent/list")
    public Result getParentList(){
        // 查询上级菜单列表
        List<PermissionPO> list = permissionService.findParentPermissionList();
        // 返回数据
        return Result.ok(list);
    }

    /**
     * 根据id查询菜单信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result getMenuById(@PathVariable Long id){
        return Result.ok(permissionService.getById(id));
    }

    /**
     * 添加菜单
     * @param permissionPO
     * @return
     */
    @PostMapping("/add")
    @PreAuthorize("hasAuthority('sys:menu:add')")
    public Result add(@RequestBody PermissionPO permissionPO){
        if (permissionService.save(permissionPO)){
            return Result.ok().message("菜单添加成功");
        }
        return Result.error().message("菜单添加失败");
    }

    /**
     * 修改菜单
     * @param permissionPO
     * @return
     */
    @PutMapping("/update")
    @PreAuthorize("hasAuthority('sys:menu:edit')")
    public Result update(@RequestBody PermissionPO permissionPO){
        if (permissionService.updateById(permissionPO)){
            return Result.ok().message("菜单修改成功");
        }
        return Result.error().message("菜单修改失败");
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('sys:menu:delete')")
    public Result update(@PathVariable Long id){
        if (permissionService.removeById(id)){
            return Result.ok().message("菜单删除成功");
        }
        return Result.error().message("菜单删除失败");
    }

    /**
     * 检查菜单下是否有子菜单
     * @param id
     * @return
     */
    @GetMapping("/check/{id}")
    @PreAuthorize("hasAuthority('sys:menu:delete')")
    public Result check(@PathVariable Long id){
        if (permissionService.hasChildrenOfPermission(id)){
            return Result.exist().message("该菜单下有子菜单，无法删除");
        }
        return Result.ok().message("该菜单下无子菜单，可以删除");
    }
}

