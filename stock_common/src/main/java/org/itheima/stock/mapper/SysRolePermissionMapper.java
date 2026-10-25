package org.itheima.stock.mapper;

import org.itheima.stock.pojo.entity.SysRolePermission;

/**
* @author HP
* @description 针对表【sys_role_permission(角色权限表)】的数据库操作Mapper
* @createDate 2026-10-22 21:41:58
* @Entity org.itheima.stock.pojo.entity.SysRolePermission
*/
public interface SysRolePermissionMapper {

    int deleteByPrimaryKey(Long id);

    int insert(SysRolePermission record);

    int insertSelective(SysRolePermission record);

    SysRolePermission selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysRolePermission record);

    int updateByPrimaryKey(SysRolePermission record);

}
