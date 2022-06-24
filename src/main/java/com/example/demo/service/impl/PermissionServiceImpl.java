package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.dao.UserMapper;
import com.example.demo.entity.po.PermissionPO;
import com.example.demo.dao.PermissionMapper;
import com.example.demo.entity.po.UserPO;
import com.example.demo.entity.vo.PermissionVO;
import com.example.demo.entity.vo.RolePermissionVO;
import com.example.demo.service.PermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.utils.MenuTreeUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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

    @Resource
    private UserMapper userMapper;

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

    /**
     * 查询分配设备树列表
     *
     * @param userId
     * @param roleId
     * @return
     */
    @Override
    public RolePermissionVO getAssignPermissionTree(Long userId, Long roleId) {
        // 查询当前用户信息
        UserPO userPO = userMapper.selectById(userId);
        List<PermissionPO> permissionList = null;
        // 判断当前用户角色，如果是管理员，则查询所有权限，如果不是则只查询自己所拥有的权限
        if (!ObjectUtils.isEmpty(userPO.getIsAdmin()) && userPO.getIsAdmin() == 1) {
            // 查询所有权限
            permissionList = baseMapper.selectList(null);
        } else {
            // 查询当前用户所拥有的权限
            permissionList = baseMapper.findPermissionListByUserId(userId);
        }
        // 生成菜单树
        List<PermissionPO> menuTree = MenuTreeUtils.makeMenuTree(permissionList, 0L);
        // 查询要分配角色的原有权限
        List<PermissionPO> rolePermissionList = baseMapper.findPermissionListByRoleId(roleId);
        // 找出该角色存在的数据
        List<Long> listIds = new ArrayList<Long>();
        Optional.ofNullable(permissionList).orElse(new ArrayList<>())
                .stream()
                .filter(item -> item!=null)
                .forEach(item -> {
                    Optional.ofNullable(rolePermissionList).orElse(new ArrayList<>())
                            .stream()
                            .filter(obj -> obj != null)
                            .forEach(obj -> {
                                if (item.getId().equals(obj.getId())) {
                                    listIds.add(item.getId());
                                    return;
                                }
                            });

                });
        // 创建
        RolePermissionVO rolePermissionVO = new RolePermissionVO();
        rolePermissionVO.setPermissionPOList(menuTree);
        rolePermissionVO.setCheckList(listIds.toArray());
        return rolePermissionVO;
    }

    /**
     * 根据url查询权限
     *
     * @param url
     * @return
     */
    @Override
    public PermissionPO getPermissionByUrl(String url) {
        // 构造条件构造器对象
        QueryWrapper<PermissionPO> queryWrapper = new QueryWrapper<PermissionPO>();
        queryWrapper.eq(!ObjectUtils.isEmpty(url), "url", url);
        return baseMapper.selectOne(queryWrapper);
    }
}
