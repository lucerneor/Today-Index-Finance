package org.itheima.stock.service.impl;

import com.alibaba.excel.EasyExcel;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.itheima.stock.mapper.StockBlockRtInfoMapper;
import org.itheima.stock.mapper.StockMarketIndexInfoMapper;
import org.itheima.stock.mapper.StockRtInfoMapper;
import org.itheima.stock.pojo.domain.InnerMarketDomain;
import org.itheima.stock.pojo.domain.StockBlockDomain;
import org.itheima.stock.pojo.domain.StockInfoConfig;
import org.itheima.stock.pojo.domain.StockUpdownDomain;
import org.itheima.stock.service.StockService;
import org.itheima.stock.utils.DateTimeUtil;
import org.itheima.stock.vo.resp.PageResult;
import org.itheima.stock.vo.resp.R;
import org.itheima.stock.vo.resp.ResponseCode;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 股票服务实现
 */
@Service
@Slf4j
public class StockServiceImpl implements StockService {


    @Autowired
    private StockInfoConfig stockInfoConfig;

    @Autowired
    private StockMarketIndexInfoMapper stockMarketIndexInfoMapper;

    @Autowired
    private StockBlockRtInfoMapper stockBlockRtInfoMapper;

    @Autowired
    private StockRtInfoMapper stockRtInfoMapper;

    /**
     * 获取国内大盘最新的数据
     *
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
        List<InnerMarketDomain> data = stockMarketIndexInfoMapper.getMarketInfo(curDate, mCodes);
        // 4.封装并响应
        R<List<InnerMarketDomain>> ok = R.ok(data);
        System.out.println(ok.toString());
        return ok;

    }


    /**
     * 需求说明: 沪深两市板块分时行情数据查询，以交易时间和交易总金额降序查询，取前10条数据
     *
     * @return
     */
    @Override
    public R<List<StockBlockDomain>> sectorAllLimit() {

        //获取股票最新交易时间点
        Date lastDate = DateTimeUtil.getLastDate4Stock(DateTime.now()).toDate();
        //TODO mock数据,后续删除
        lastDate = DateTime.parse("2021-12-21 14:30:00", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();
        //1.调用mapper接口获取数据
        List<StockBlockDomain> infos = stockBlockRtInfoMapper.sectorAllLimit(lastDate);
        //2.组装数据
        if (CollectionUtils.isEmpty(infos)) {
            return R.error(ResponseCode.NO_RESPONSE_DATA.getMessage());
        }
        return R.ok(infos);
    }

    /**
     * 分页查询最新的股票交易数据
     *
     * @param page     当前页
     * @param pageSize 每页大小
     * @return
     */
    @Override
    public R<PageResult<StockUpdownDomain>> getStockInfoBypage(Integer page, Integer pageSize) {
        //1.获取最新的股票交易时间点
        Date curDate = DateTimeUtil.getLastDate4Stock(DateTime.now()).toDate();
        // mock data 等后续完成股票采集job工程，再将代码删除即可
        curDate = DateTime.parse("2021-12-30 09:42:00", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();
        //2.设置PageHelper分页参数
        PageHelper.startPage(page, pageSize);
        //3.调用Mapper查询
        List<StockUpdownDomain> pageData = stockRtInfoMapper.getStockInfoByTime(curDate);
        //4.组装PageResult对象
        PageInfo<StockUpdownDomain> pageInfo = new PageInfo<>(pageData);
        PageResult<StockUpdownDomain> pageResult = new PageResult<>(pageInfo);
        //5.响应数据
        return R.ok(pageResult);
    }

    @Override
    /**
     * 统计沪深两市个股最新交易数据，并按涨幅降序排序查询前4条数据
     * @return
     */
    public R<List<StockUpdownDomain>> getStockIncrease() {
        //1.获取最新的交易时间点
        Date curDate = DateTimeUtil.getLastDate4Stock(DateTime.now()).toDate();
        // mock data 等后续完成股票采集job工程，再将代码删除即可
        curDate = DateTime.parse("2021-12-30 09:42:00", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();
        //2.调用Mapper查询
        List<StockUpdownDomain> increaseList = stockRtInfoMapper.getStockIncrease(curDate);
        //3.响应数据
        return R.ok(increaseList);
    }

    /**
     * 统计最新股票交易日内每分钟的涨跌停的股票数量
     *
     * @return
     */
    @Override
    public R<Map<String, List>> getStockUpDownCount() {
        //1.获取最新股票的交易时间点（截止时间）
        DateTime curDateTime = DateTimeUtil.getLastDate4Stock(DateTime.now());
        //假数据
        curDateTime = DateTime.parse("2022-07-07 14:44:00", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss"));
        Date endDate = curDateTime.toDate();
        //2.获取最新交易时间对应的开盘时间点
        Date startDate = DateTimeUtil.getOpenDate(curDateTime).toDate();
        //3.统计涨停数据(约定 1涨停 0跌停)
        List<Map> upList = stockRtInfoMapper.getStockUpdownCount(startDate, endDate, 1);
        //4.统计跌停
        List<Map> downList = stockRtInfoMapper.getStockUpdownCount(startDate, endDate, 0);
        //5.组装数据
        HashMap<String, List> info = new HashMap<>();
        info.put("upList", upList);
        info.put("downLsit", downList);
        return R.ok(info);
    }

    /**
     * 导出指定页码的最新股票信息
     *
     * @param page     当前页
     * @param pageSize 每页大小
     * @param response
     */
    @Override
    public void exportStockUpDownInfo(Integer page, Integer pageSize, HttpServletResponse response) {
        //1.获取分页的数据
        R<PageResult<StockUpdownDomain>> r = this.getStockInfoBypage(page, pageSize);
        List<StockUpdownDomain> rows = r.getData().getRows();
        //2.将数据导出到excel中
        //这里注意 使用swagger会出现各种问题 直接使用postman
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        //这里URLEncoder.encoder可以防止中文乱码 当然和easyExcel没有关系
        try {
            String fileName = URLEncoder.encode("股票信息表","UTF-8");
            response.setHeader("Content-Disposition","attachment;filename="+ fileName + ".xlsx");
            EasyExcel.write(response.getOutputStream(), StockUpdownDomain.class).sheet("股票涨幅信息").doWrite(rows);
        } catch (IOException e) {
            log.error("当前页码：{}，每页大小：{}，当前时间点：{}，异常信息：{}",page,pageSize,DateTime.now().toString("yyyy-MM-dd HH:mm:ss"),e.getMessage());
            //通知前端异常，稍后重试
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            R<Object> error = R.error(ResponseCode.ERROR);
            try {
                String jsonData = new ObjectMapper().writeValueAsString(error);
                response.getWriter().write(jsonData);
            } catch (IOException ex) {
                log.error("exportStockUpDownInfo:响应错误信息失败，时间：{}",DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));
            }
        }

    }
}
