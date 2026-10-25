package org.itheima.stock.mapper;

import org.apache.ibatis.annotations.Param;
import org.itheima.stock.pojo.entity.SysUser;

/**
* @author HP
* @description 针对表【sys_user(用户表)】的数据库操作Mapper
* @createDate 2026-10-22 21:41:58
* @Entity org.itheima.stock.pojo.entity.SysUser
*/
public interface SysUserMapper {

    int deleteByPrimaryKey(Long id);

    int insert(SysUser record);

    int insertSelective(SysUser record);

    SysUser selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysUser record);

    int updateByPrimaryKey(SysUser record);

    SysUser findUserInfoByUserName(@Param("userName") String userName);

}
