package org.itheima.stock.mapper;

import org.itheima.stock.pojo.entity.StockRtInfo;

/**
* @author HP
* @description 针对表【stock_rt_info(个股详情信息表)】的数据库操作Mapper
* @createDate 2026-10-22 21:41:58
* @Entity org.itheima.stock.pojo.entity.StockRtInfo
*/
public interface StockRtInfoMapper {

    int deleteByPrimaryKey(Long id);

    int insert(StockRtInfo record);

    int insertSelective(StockRtInfo record);

    StockRtInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(StockRtInfo record);

    int updateByPrimaryKey(StockRtInfo record);

}
