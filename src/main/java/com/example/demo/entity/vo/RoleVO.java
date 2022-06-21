package com.example.demo.entity.vo;

import com.example.demo.entity.po.RolePO;
import lombok.Data;


@Data
public class RoleVO extends RolePO {
    private Long pageNo = 1L; // 当前页
    private Long pageSize = 10L; // 每页显示条数
    private Long userId; // 用户id
}
