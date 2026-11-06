package org.itheima.stock.service;

import org.itheima.stock.pojo.domain.InnerMarketDomain;
import org.itheima.stock.pojo.domain.StockBlockDomain;
import org.itheima.stock.pojo.domain.StockUpdownDomain;
import org.itheima.stock.vo.resp.PageResult;
import org.itheima.stock.vo.resp.R;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

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

    /**
     * 分页查询最新的股票交易数据
     * @param page  当前页
     * @param pageSize  每页大小
     * @return
     */
    R<PageResult<StockUpdownDomain>> getStockInfoBypage(Integer page, Integer pageSize);

    /**
     * 统计沪深两市个股最新交易数据，并按涨幅降序排序查询前4条数据
     * @return
     */
    R<List<StockUpdownDomain>> getStockIncrease();

    /**
     * 统计最新股票交易日内每分钟的涨跌停的股票数量
     * @return
     */
    R<Map<String, List>> getStockUpDownCount();

    /**
     * 导出指定页码的最新股票信息
     * @param page 当前页
     * @param pageSize 每页大小
     * @param response
     */
    void exportStockUpDownInfo(Integer page, Integer pageSize, HttpServletResponse response);
}
