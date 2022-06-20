package com.example.demo.controller;


import com.example.demo.common.Result;
import com.example.demo.entity.po.DepartmentPO;
import com.example.demo.entity.vo.DepartmentVO;
import com.example.demo.service.DepartmentService;
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
@RequestMapping("/department")
public class DepartmentController {

    @Resource
    private DepartmentService departmentService;

    /**
     * 获取部门列表
     * @param departmentVO
     * @return
     */
    @GetMapping("/list")
    public Result findDepartmentList(DepartmentVO departmentVO) {
        return Result.ok(departmentService.findDepartmentList(departmentVO));
    }

    /**
     * 获取上级部门列表
     * @return
     */
    @GetMapping("/parent/list")
    public Result findParentDepartment() {
        return Result.ok(departmentService.findParentDepartment());
    }

    /**
     * 添加部门
     * @param departmentPO
     * @return
     */
    @PostMapping("/add")
    public Result addDepartment(@RequestBody DepartmentPO departmentPO) {
        if (departmentService.save(departmentPO)) {
            return Result.ok().message("部门添加成功");
        }else {
            return Result.error().message("部门添加失败");
        }
    }

    /**
     * 修改部门
     * @param departmentPO
     * @return
     */
    @PutMapping("/update")
    public Result updateDepartment(@RequestBody DepartmentPO departmentPO) {
        if (departmentService.updateById(departmentPO)) {
            return Result.ok().message("部门修改成功");
        }else {
            return Result.error().message("部门修改失败");
        }
    }

    /**
     * 查询某个部门下是否有子部门
     * @param id
     * @return
     */
    @GetMapping("/check/{id}")
    public Result hasChildOfDepartment(@PathVariable Long id) {
        if (departmentService.hasChildOfDepartment(id)) {
            return Result.ok().message("该部门下有子部门");
        }else {
            return Result.error().message("该部门下没有子部门");
        }
    }

    /**
     * 删除部门
     * @param id
     * @return
     */
    @DeleteMapping("/delete/{id}")
    public Result deleteDepartment(@PathVariable Long id) {
        if (departmentService.removeById(id)) {
            return Result.ok().message("部门删除成功");
        }else {
            return Result.error().message("部门删除失败");
        }
    }
}

