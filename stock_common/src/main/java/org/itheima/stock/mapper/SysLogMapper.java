package org.itheima.stock.mapper;

import org.itheima.stock.pojo.entity.SysLog;

/**
* @author HP
* @description 针对表【sys_log(系统日志)】的数据库操作Mapper
* @createDate 2026-10-22 21:41:58
* @Entity org.itheima.stock.pojo.entity.SysLog
*/
public interface SysLogMapper {

    int deleteByPrimaryKey(Long id);

    int insert(SysLog record);

    int insertSelective(SysLog record);

    SysLog selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysLog record);

    int updateByPrimaryKey(SysLog record);

}
