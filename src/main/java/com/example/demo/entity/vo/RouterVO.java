package com.example.demo.entity.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class RouterVO {
    // 路由地址
    private String path;
    // 路由对应的组件
    private String component;
    // 是否显示
    private boolean alwaysShow;
    // 路由名称
    private String name;
    // 路由meta信息
    private Meta meta;

    @Data
    @AllArgsConstructor
    public class Meta {
        // 标题
        private String title;
        // 图标
        private String icon;
        // 角色列表
        private Object[] roles;
    }

    // 子路由
    private List<RouterVO> children = new ArrayList<RouterVO>();
}
