package com.example.demo.utils;

import com.example.demo.entity.po.PermissionPO;
import com.example.demo.entity.vo.RouterVO;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * 生成菜单树
 */
public class MenuTreeUtils {

    /**
     * 生成路由
     * @param menuList 菜单列表
     * @param parentId 父级菜单编号
     * @return
     */
    public static List<RouterVO> makeRouter(List<PermissionPO> menuList, Long parentId) {
        // 创建集合保存路由列表
        List<RouterVO> routerList = new ArrayList<RouterVO>();
        // 如果menuList不为空，则使用菜单列表，否则直接创建集合对象，等同于if else
        Optional.ofNullable(menuList).orElse(new ArrayList<PermissionPO>())
                // 筛选不为空的菜单和菜单父id相同的数据
                .stream().filter(item -> item != null && item.getParentId().equals(parentId))
                .forEach(
                        item -> {
                            // 创建路由对象
                            RouterVO router = new RouterVO();
                            router.setName(item.getName()); // 路由名称
                            router.setPath(item.getPath()); // 路由地址
                            // 判断是否为一级菜单
                            if (item.getParentId().equals(0L)) {
                                router.setComponent("Layout"); // 一级菜单组件
                                router.setAlwaysShow(true); // 是否显示
                            } else {
                                router.setComponent(item.getUrl()); // 具体菜单组件
                                router.setAlwaysShow(false); // 折叠路由
                            }
                            // 创建meta对象
                            router.setMeta(
                                    router.new Meta(
                                            item.getLabel(), // 标题
                                            item.getIcon(), // 图标
                                            item.getCode().split(",") // 角色编码
                                    )
                            );
                            // 递归生成路由
                            List<RouterVO> children = makeRouter(menuList, item.getId());
                            router.setChildren(children);  // 设置子路由
                            // 将路由信息添加到集合中
                            routerList.add(router);
                        }
                );
        return routerList;
    }

    /**
     * 生成菜单树
     * @param menuList 菜单列表
     * @param parentId 父级菜单编号
     * @return
     */
    public static List<PermissionPO> makeMenuTree(List <PermissionPO> menuList, Long parentId) {
        // 创建集合保存菜单列表
        List<PermissionPO> permissionTree = new ArrayList<PermissionPO>();
        // 如果menuList不为空，则使用菜单列表，否则直接创建集合对象，等同于if else
        Optional.ofNullable(menuList).orElse(new ArrayList<PermissionPO>())
                // 筛选不为空的菜单和菜单父id相同的数据
                .stream().filter(item ->
                        item != null && Objects.equals(item.getParentId(), parentId) // item可能为null
                )
                .forEach(
                        item -> {
                            // 创建菜单权限对象
                            PermissionPO permission = new PermissionPO();
                            // 复制属性
                            BeanUtils.copyProperties(item, permission);
                            // 获取每一个item的下级菜单，递归生成菜单树
                            List<PermissionPO> children = makeMenuTree(menuList, item.getId());
                            permission.setChildren(children); // 设置子菜单
                            // 将菜单信息添加到集合中
                            permissionTree.add(permission);
                        }
                );
        return permissionTree;
    }
}
