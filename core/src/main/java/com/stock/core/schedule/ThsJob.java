package com.stock.core.schedule;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.stock.core.entity.Stock;
import com.stock.core.service.IStockService;
import com.stock.core.service.stocklist.DefaultStockListComponent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 东方财富-强势股池
 *
 * @author caijinglong
 * @date 2022-10-23
 */
@Slf4j
@Component
@EnableScheduling
public class DfcfQsPoolJob {

  @Resource
  private DefaultStockListComponent defaultStockListComponent;

  @Resource
  private IStockService stockService;

  @Scheduled(cron = "0 0 10 * * ?")
  public void syncStockList() {
    log.debug("======================syncStockList start...==========================");
    long t1 = System.currentTimeMillis();
    List<Stock> stocks = defaultStockListComponent.allStock();
    log.debug("======================syncStockList all stock size:{}==========================", stocks.size());

    List<String> existStockCodes = stockService.list(new LambdaQueryWrapper<Stock>()
            .select(Stock::getCode))
            .stream()
            .map(Stock::getCode)
            .collect(Collectors.toList());
    List<Stock> newStocks = stocks.stream()
            .filter(stock -> !existStockCodes.contains(stock.getCode()))
            .filter(stock -> StrUtil.isNotBlank(stock.getName()))
            .collect(Collectors.toList());

    stockService.saveBatch(newStocks);

    List<String> allStockCodes = stocks.stream()
            .map(Stock::getCode)
            .collect(Collectors.toList());
    existStockCodes.removeAll(allStockCodes);

    if (!existStockCodes.isEmpty()) {
      stockService.remove(new LambdaQueryWrapper<Stock>()
              .in(Stock::getCode, existStockCodes));
    }

    long t2 = System.currentTimeMillis();
    log.debug("======================syncStockList end. cost:{}..==========================", t2 - t1);
  }

}
