package com.example.demo.controller;


import com.example.demo.service.UserService;
import com.example.demo.common.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

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

    /**
     * 获取用户列表
     * @return
     */
    @GetMapping("/listAll")
    public Result listAll() {
        return Result.ok(userService.list());
    }
}

