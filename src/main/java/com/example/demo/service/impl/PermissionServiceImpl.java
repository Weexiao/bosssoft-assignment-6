package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.entity.po.PermissionPO;
import com.example.demo.dao.PermissionMapper;
import com.example.demo.entity.vo.PermissionVO;
import com.example.demo.service.PermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.utils.MenuTreeUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wuhu
 * @since 2022-06-20
 */
@Service
@Transactional
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, PermissionPO> implements PermissionService {

    /**
     * 根据用户ID查询权限列表
     *
     * @param userId
     * @return
     */
    @Override
    public List<PermissionPO> findPermissionListByUserId(Long userId) {
        return baseMapper.findPermissionListByUserId(userId);
    }

    /**
     * 查询菜单列表
     *
     * @param permissionVO
     * @return
     */
    @Override
    public List<PermissionPO> findPermissionList(PermissionVO permissionVO) {
        // 构造条件构造器对象
        QueryWrapper<PermissionPO> queryWrapper = new QueryWrapper<PermissionPO>();
        // 排序
        queryWrapper.orderByAsc("order_num");
        // 调用查询菜单列表的方法
        List<PermissionPO> permissionList = baseMapper.selectList(queryWrapper);
        // 生成菜单树
        List<PermissionPO> menuTree = MenuTreeUtils.makeMenuTree(permissionList, 0L);
        // 返回数据
        return menuTree;
    }

    /**
     * 查询上级菜单列表
     *
     * @return
     */
    @Override
    public List<PermissionPO> findParentPermissionList() {
        QueryWrapper<PermissionPO> queryWrapper = new QueryWrapper<PermissionPO>();
        // 只查询Type为目录的菜单(type=0 or type=1)
        queryWrapper.in("type", Arrays.asList(0, 1));
        // 排序
        queryWrapper.orderByAsc("order_num");
        // 查询菜单数据
        List<PermissionPO> permissionList = baseMapper.selectList(queryWrapper);

        // 构造顶级菜单信息，如果数据库中无数据，则显示顶级菜单信息
        PermissionPO permissionPO = new PermissionPO();
        permissionPO.setId(0L);
        permissionPO.setName("顶级菜单");
        permissionPO.setParentId(-1L);

        // 将顶级菜单添加到菜单列表中
        permissionList.add(permissionPO);

        // 生成菜单数据
        List<PermissionPO> menuTree = MenuTreeUtils.makeMenuTree(permissionList, -1L);
        // 返回数据
        return menuTree;
    }

    /**
     * 查询菜单是否存在子菜单
     *
     * @param permissionId
     * @return
     */
    @Override
    public boolean hasChildrenOfPermission(Long permissionId) {
        // 构造条件构造器对象
        QueryWrapper<PermissionPO> queryWrapper = new QueryWrapper<PermissionPO>();
        // 查询条件：父级菜单ID
        queryWrapper.eq("parent_id", permissionId);
        // 判断数据是否大于0，大于0则true
        return baseMapper.selectCount(queryWrapper) > 0;
    }
}
