package org.itheima.stock.service;

import org.itheima.stock.pojo.domain.InnerMarketDomain;
import org.itheima.stock.pojo.domain.StockBlockDomain;
import org.itheima.stock.vo.resp.R;

import java.util.List;

/**
 * 股票服务接口
 */
public interface StockService {

    /**
     * 获取国内大盘最新的数据
     * @return
     */
    R<List<InnerMarketDomain>> getInnerMarketInfo();

    /**
     * 获取沪深两市板块最新数据，以交易总金额降序查询，取前10条数据
     * @return
     */
    R<List<StockBlockDomain>> sectorAllLimit();
}
