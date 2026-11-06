package org.itheima.stock.mapper;

import org.apache.ibatis.annotations.Param;
import org.itheima.stock.pojo.domain.StockUpdownDomain;
import org.itheima.stock.pojo.entity.StockRtInfo;

import java.util.Date;
import java.util.List;
import java.util.Map;

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

    /**
     * 查询指定时间点下股票数据集合
     * @param curDate 日期时间
     * @return
     */
    List<StockUpdownDomain> getStockInfoByTime(@Param("curDate") Date curDate);

    /**
     * 统计沪深两市个股最新交易数据，并按涨幅降序排序查询前4条数据
     * @return
     */
    List<StockUpdownDomain> getStockIncrease(@Param("curDate") Date curDate);

    /**
     * 统计指定日期范围内股票涨停或者跌停的数量流水
     * @param startDate 开始时间，一般指的是开盘时间
     * @param endDate 截止时间
     * @param flag 约定：1代表统计涨停 0跌停
     * @return
     */
    List<Map> getStockUpdownCount(@Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("flag") int flag);
}
