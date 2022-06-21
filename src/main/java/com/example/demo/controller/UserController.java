package com.example.demo.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.entity.dto.UserRoleDTO;
import com.example.demo.entity.po.RolePO;
import com.example.demo.entity.po.UserPO;
import com.example.demo.entity.vo.RoleVO;
import com.example.demo.entity.vo.UserVO;
import com.example.demo.service.RoleService;
import com.example.demo.service.UserService;
import com.example.demo.common.Result;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;
    @Resource
    private RoleService roleService;
    @Resource
    private PasswordEncoder passwordEncoder;

    /**
     * 获取用户列表
     * @return
     */
    @GetMapping("/listAll")
    public Result listAll() {
        return Result.ok(userService.list());
    }

    /**
     * 查询用户列表
     * @param userVO
     * @return
     */
    @GetMapping("/list")
    public Result list(UserVO userVO) {
        // 创建分页信息
        IPage<UserPO> page = new Page<UserPO>(userVO.getPageNo(), userVO.getPageSize());
        userService.findUserListByPage(page, userVO);
        return Result.ok(page);
    }

    /**
     * 添加用户
     * @param userPO
     * @return
     */
    @PostMapping("/add")
    public Result add(@RequestBody UserPO userPO) {
        // 查询用户
        UserPO item = userService.getUserByUsername(userPO.getUsername());
        // 判断用户是否存在
        if (item != null) {
            return Result.error().message("用户已存在，请重新输入");
        }
        // 密码加密
        userPO.setPassword(passwordEncoder.encode(userPO.getPassword()));
        // 调用保存用户信息的方法
        if (userService.save(userPO)) {
            return Result.ok().message("用户添加成功");
        }
        return Result.error().message("用户添加失败");
    }

    /**
     * 修改用户
     * @param userPO
     * @return
     */
    @PutMapping("/update")
    public Result update(@RequestBody UserPO userPO) {
        System.out.println(userPO);
        // 查询用户
        UserPO item = userService.getUserByUsername(userPO.getUsername());
        System.out.println(item);
        // 判断用户是否存在
        if (item != null && !item.getId().equals(userPO.getId())) {
            return Result.error().message("登录名称已被占用");
        }
        // 调用修改用户信息的方法
        if (userService.updateById(userPO)) {
            return Result.ok().message("用户修改成功");
        }
        return Result.error().message("用户修改失败");
    }

    /**
     * 删除用户
     * @param userId
     * @return
     */
    @DeleteMapping("/delete/{userId}")
    public Result delete(@PathVariable Long userId) {
        // 调用删除用户信息的方法
        if (userService.removeById(userId)) {
            return Result.ok().message("用户删除成功");
        }
        return Result.error().message("用户删除失败");
    }

    /**
     * 获取分配角色列表
     * @param roleVO
     * @return
     */
    @GetMapping("/getRoleListForAssign")
    public Result getRoleListForAssign(RoleVO roleVO) {
        // 创建分页对象
        IPage<RolePO> page = new Page<RolePO>(roleVO.getPageNo(), roleVO.getPageSize());
        // 调用获取分配角色列表的方法
        roleService.findRoleListByUserId(page, roleVO);
        return Result.ok(page);
    }

    /**
     * 根据用户ID查询该用户拥有的角色列表
     * @param userId
     * @return
     */
    @GetMapping("/getRoleListByUserId/{userId}")
    public Result getRoleListByUserId(@PathVariable Long userId) {
        // 调用获取该用户拥有的角色列表的方法
        return Result.ok(roleService.getRoleIdsByUserId(userId));
    }

    /**
     * 分配角色
     * @param userRoleDTO
     * @return
     */
    @PostMapping("/saveUserRole")
    public Result saveUserRole(@RequestBody UserRoleDTO userRoleDTO) {
        // 调用分配角色的方法
        if (userService.saveUserRole(userRoleDTO.getUserId(), userRoleDTO.getRoleIds())) {
            return Result.ok().message("分配角色成功");
        }
        return Result.error().message("分配角色失败");
    }
}

