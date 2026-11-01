package org.itheima.stock.pojo.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@ApiModel("股票板块domain")
public class StockBlockDomain {
    @ApiModelProperty("公司数量")
    private Integer companyNum;
    @ApiModelProperty("交易量")
    private Long tradeAmt;
    @ApiModelProperty("模块编码")
    private String code;
    @ApiModelProperty("平均价")
    private BigDecimal avgPrice;
    @ApiModelProperty("板块名称")
    private String name;
    @ApiModelProperty("当前日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date curDate;
    @ApiModelProperty("交易金额")
    private BigDecimal tradeVol;
    @ApiModelProperty("涨跌率")
    private BigDecimal updownRate;
}
