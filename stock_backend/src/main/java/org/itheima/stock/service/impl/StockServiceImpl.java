package org.itheima.stock.service.impl;

import org.itheima.stock.mapper.StockBlockRtInfoMapper;
import org.itheima.stock.mapper.StockMarketIndexInfoMapper;
import org.itheima.stock.pojo.domain.InnerMarketDomain;
import org.itheima.stock.pojo.domain.StockBlockDomain;
import org.itheima.stock.pojo.domain.StockInfoConfig;
import org.itheima.stock.service.StockService;
import org.itheima.stock.utils.DateTimeUtil;
import org.itheima.stock.vo.resp.R;
import org.itheima.stock.vo.resp.ResponseCode;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

/**
 * 股票服务实现
 */
@Service
public class StockServiceImpl implements StockService {
    
    
    @Autowired
    private StockInfoConfig stockInfoConfig;

    @Autowired
    private StockMarketIndexInfoMapper stockMarketIndexInfoMapper;

    @Autowired
    private StockBlockRtInfoMapper stockBlockRtInfoMapper;

    /**
     * 获取国内大盘最新的数据
     * @return
     */
    @Override
    public R<List<InnerMarketDomain>> getInnerMarketInfo() {
        // 1.获取股票最新的交易时间点（精确到分钟，秒和毫秒设置为0）
        Date curDate = DateTimeUtil.getLastDate4Stock(DateTime.now()).toDate();
        // mock data 等后续完成股票采集job工程，再将代码删除即可
        curDate = DateTime.parse("2021-12-28 14:00:00", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();
        // 2.获取大盘编码集合
        List<String> mCodes = stockInfoConfig.getInner();
        // 3.调用mapper查询数据
        List<InnerMarketDomain> data = stockMarketIndexInfoMapper.getMarketInfo(curDate,mCodes);
        // 4.封装并响应
        R<List<InnerMarketDomain>> ok = R.ok(data);
        System.out.println(ok.toString());
        return ok;

    }


    /**
     *需求说明: 沪深两市板块分时行情数据查询，以交易时间和交易总金额降序查询，取前10条数据
     * @return
     */
    @Override
    public R<List<StockBlockDomain>> sectorAllLimit() {

        //获取股票最新交易时间点
        Date lastDate = DateTimeUtil.getLastDate4Stock(DateTime.now()).toDate();
        //TODO mock数据,后续删除
        lastDate=DateTime.parse("2021-12-21 14:30:00", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();
        //1.调用mapper接口获取数据
        List<StockBlockDomain> infos=stockBlockRtInfoMapper.sectorAllLimit(lastDate);
        //2.组装数据
        if (CollectionUtils.isEmpty(infos)) {
            return R.error(ResponseCode.NO_RESPONSE_DATA.getMessage());
        }
        return R.ok(infos);
    }
}
