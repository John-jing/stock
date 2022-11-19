package com.stock.core.listener;

import com.stock.core.schedule.DfcfJob;
import com.stock.core.schedule.StockJob;
import com.stock.core.schedule.ThsJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author caijinglong
 * @date 2022-11-09
 */
@Slf4j
@Service
public class InitListener implements ApplicationListener<ContextRefreshedEvent> {

  @Resource
  private StockJob stockJob;

  @Resource
  private DfcfJob dfcfJob;

  @Resource
  private ThsJob thsJob;

  @Override
  public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
    if (contextRefreshedEvent.getApplicationContext().getParent() != null) {
      // 保证只执行一次
      return;
    }

    try {
      stockJob.syncStockList();
    } catch (Exception e) {
      log.error("syncStockList", e);
    }

    try {
      dfcfJob.dailyRaisingLimit();
    } catch (Exception e) {
      log.error("syncDailyLimit", e);
    }

    try {
      dfcfJob.dailyDownLimit();
    } catch (Exception e) {
      log.error("syncDailyLimit", e);
    }

    try {
      dfcfJob.syncQsPool();
    } catch (Exception e) {
      log.error("syncDailyLimit", e);
    }

    try {
      thsJob.syncTimingTendency();
    } catch (Exception e) {
      log.error("syncTimingTendency", e);
    }

  }
}
