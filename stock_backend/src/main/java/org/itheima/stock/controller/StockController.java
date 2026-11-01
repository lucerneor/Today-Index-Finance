package org.itheima.stock.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.itheima.stock.pojo.domain.InnerMarketDomain;
import org.itheima.stock.pojo.domain.StockBlockDomain;
import org.itheima.stock.service.StockService;
import org.itheima.stock.vo.resp.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@Api(value = "/api/quot", tags = {"定义股票相关接口控制器"})
@RestController
@RequestMapping("/api/quot")
public class StockController {

    @Autowired
    private StockService stockService;

    /**
     * 获取国内大盘最新的数据
     * @return
     */
    @GetMapping("/index/all")
    @ApiOperation(value = "获取国内大盘最新的数据", notes = "获取国内大盘最新的数据", httpMethod = "GET")
    public R<List<InnerMarketDomain>> getInnerMarketInfo(){
        return stockService.getInnerMarketInfo();
    }


    /**
     * 需求说明: 获取沪深两市板块最新数据，以交易总金额降序查询，取前10条数据
     * @return
     */
    @GetMapping("/sector/all")
    public R<List<StockBlockDomain>> sectorAll(){
        return stockService.sectorAllLimit();
    }
}
