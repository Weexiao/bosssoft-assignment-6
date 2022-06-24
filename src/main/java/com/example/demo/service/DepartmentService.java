package com.example.demo.service;

import com.example.demo.entity.po.DepartmentPO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.entity.vo.DepartmentVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wuhu
 * @since 2022-06-20
 */
public interface DepartmentService extends IService<DepartmentPO> {
    /**
     * 查询部门列表
     * @param departmentVO
     * @return
     */
    List<DepartmentPO> findDepartmentList(DepartmentVO departmentVO);

    /**
     * 查询上级部门列表
     * @return
     */
    List<DepartmentPO> findParentDepartment();

    /**
     * 判断部门下是否有子部门
     * @param id
     * @return
     */
    boolean hasChildOfDepartment(Long id);

    /**
     * 查询部门下是否有某用户
     * @param id
     * @return
     */
    boolean hasUserOfDepartment(Long id);

    /**
     * 根据部门名查询部门
     * @param departmentName
     * @return
     */
    DepartmentPO getDepartmentByName(String departmentName);
}
