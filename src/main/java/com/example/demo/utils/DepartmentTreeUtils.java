package com.example.demo.utils;

import com.example.demo.entity.po.DepartmentPO;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DepartmentTreeUtils {
    /**
     * 生成部门树
     * @param departmentList
     * @param parentId
     * @return
     */
    public static List<DepartmentPO> makeDepartmentTree(List<DepartmentPO> departmentList, Long parentId) {
        // 创建集合保存部门信息
        List<DepartmentPO> list = new ArrayList<DepartmentPO>();
        // 如果departmentList不为空，则遍历，否则使用新的集合
        Optional.ofNullable(departmentList).orElse(new ArrayList<DepartmentPO>())
                .stream().filter(item -> item != null && item.getPid().equals(parentId))
                .forEach(item -> {
                    // 创建部门对象
                    DepartmentPO department = new DepartmentPO();
                    // 复制部门属性
                    BeanUtils.copyProperties(item, department);
                    // 设置子部门
                    department.setChildren(makeDepartmentTree(departmentList, item.getId()));
                    // 添加到集合
                    list.add(department);
                });
        return list;
    }
}
