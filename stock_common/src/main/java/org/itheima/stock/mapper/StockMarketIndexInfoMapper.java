package org.itheima.stock.mapper;

import org.apache.ibatis.annotations.Param;
import org.itheima.stock.pojo.domain.InnerMarketDomain;
import org.itheima.stock.pojo.entity.StockMarketIndexInfo;

import java.util.Date;
import java.util.List;

/**
* @author HP
* @description 针对表【stock_market_index_info(国内大盘数据详情表)】的数据库操作Mapper
* @createDate 2026-10-22 21:41:58
* @Entity org.itheima.stock.pojo.entity.StockMarketIndexInfo
*/
public interface StockMarketIndexInfoMapper {

    int deleteByPrimaryKey(Long id);

    int insert(StockMarketIndexInfo record);

    int insertSelective(StockMarketIndexInfo record);

    StockMarketIndexInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(StockMarketIndexInfo record);

    int updateByPrimaryKey(StockMarketIndexInfo record);

    /**
     * 根据指定时间点查询指定大盘编码最新的数据
     * @param curDate 指定的时间点
     * @param marketCodes 大盘编码集合
     * @return
     */
    List<InnerMarketDomain> getMarketInfo(@Param("curDate") Date curDate, @Param("marketCodes") List<String> marketCodes);
}
