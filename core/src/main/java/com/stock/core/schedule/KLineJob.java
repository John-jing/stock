package com.stock.core.schedule;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.stock.core.entity.Stock;
import com.stock.core.entity.TimingTendency;
import com.stock.core.http.ths.TendencyItemModel;
import com.stock.core.http.ths.ThsClient;
import com.stock.core.service.IStockService;
import com.stock.core.service.ITimingTendencyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.stock.core.constant.DBConstant.LIMIT_ONE;

/**
 * 同花顺
 *
 * @author caijinglong
 * @date 2022-10-23
 */
@Slf4j
@Component
@EnableScheduling
public class CandlestickChartJob {

  @Resource
  private ITimingTendencyService timingTendencyService;

  @Resource
  private IStockService stockService;

  @Scheduled(cron = "0 0 10 * * ?")
  public void syncTimingTendency() {
    log.debug("======================syncTimingTendency start...==========================");
    long t1 = System.currentTimeMillis();
    List<Stock> stocks = stockService.list(new LambdaQueryWrapper<Stock>().last("limit 10"));
    LocalDate daily = LocalDate.now();
    log.debug("======================syncTimingTendency all stock size:{}==========================", stocks.size());

    List<TimingTendency> timingTendencies = new ArrayList<>();
    List<TimingTendency> updateList = new ArrayList<>();
    for (Stock stock : stocks) {
      try {
        List<TendencyItemModel> tendencyItemModels = ThsClient.stockTendencyOfMinute(stock.getCode());
        Thread.sleep(RandomUtil.randomInt(50, 100));

        TimingTendency timingTendency = timingTendencyService.getOne(new LambdaQueryWrapper<TimingTendency>()
                .select(TimingTendency::getCode, TimingTendency::getId)
                .eq(TimingTendency::getCode, stock.getCode())
                .eq(TimingTendency::getDaily, daily)
                .last(LIMIT_ONE)
        );
        if (timingTendency != null) {
          timingTendency.setDetail(JSONUtil.toJsonStr(tendencyItemModels));
          updateList.add(timingTendency);
        } else {
          timingTendency = TimingTendency.builder()
                  .code(stock.getCode())
                  .name(stock.getName())
                  .daily(daily)
                  .detail(JSONUtil.toJsonStr(tendencyItemModels))
                  .build();

          timingTendencies.add(timingTendency);
        }
      } catch (Exception e) {
        log.error("syncTimingTendency fail.", e);
      }

    }

    if (!updateList.isEmpty()) {
      timingTendencyService.updateBatchById(updateList);
    }

    if (!timingTendencies.isEmpty()) {
      timingTendencyService.saveBatch(timingTendencies);
    }
    long t2 = System.currentTimeMillis();
    log.debug("======================syncStockList end. cost:{}..==========================", t2 - t1);
  }

}
