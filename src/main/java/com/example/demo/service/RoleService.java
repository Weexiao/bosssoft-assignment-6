package com.example.demo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.demo.entity.po.RolePO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.entity.vo.RoleVO;

/**
 * <p>
 *  服务类
 * </p>
 * Note: 非管理员只能查询自己创建的角色列表
 * @author wuhu
 * @since 2022-06-20
 */
public interface RoleService extends IService<RolePO> {

    /**
     * 根据角色查询角色列表
     * @param page
     * @param roleVO
     * @return
     */
    IPage<RolePO> findRoleListByUserId(IPage<RolePO> page, RoleVO roleVO);

}
