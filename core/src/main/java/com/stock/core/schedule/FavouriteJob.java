package com.stock.core.schedule;

import com.stock.core.analyze.dto.TimingTendencyAnalyzeDTO;
import com.stock.core.entity.Stock;
import com.stock.core.http.ths.TendencyItemModel;
import com.stock.core.listener.InitListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.stock.core.analyze.KLineAnalyze.TIMING_TENDENCY_TASK;

/**
 * K线图
 *
 * @author caijinglong
 * @date 2022-10-23
 */
@Slf4j
@Component
@EnableScheduling
public class KLineJob {

  public static final Map<String, Stock> HOT_STOCK = new ConcurrentHashMap<>(25);

  public static final Map<String, Stock> NORMAL_STOCK = new ConcurrentHashMap<>(2500);

  public static final Map<String, Stock> COLD_STOCK = new ConcurrentHashMap<>(2500);

  private static final LocalDate DAILY = LocalDate.now();

  /**
   * 分时图
   */
  @Scheduled(cron = "30 0/1 * * * ?")
  public void timingTendencyHotListSync() {
    log.debug("======================timingTendencyHotListSync start...==========================");
    // 同步分时图
    HOT_STOCK.forEach((code, stock) -> {
      List<TendencyItemModel> tendencyItemModels = InitListener.randomClient().stockTendencyOfMinute(stock.getCategory(), code, stock.getName());
      TIMING_TENDENCY_TASK.offer(new TimingTendencyAnalyzeDTO(DAILY, code, stock.getName(), tendencyItemModels, 3));
    });
  }

  @Scheduled(cron = "30 * * * * ?")
  public void timingTendencyNormalListSync() {
    log.debug("======================timingTendencyNormalListSync start...==========================");
    NORMAL_STOCK.forEach((code, stock) -> {
      List<TendencyItemModel> tendencyItemModels = InitListener.randomClient().stockTendencyOfMinute(stock.getCategory(), code, stock.getName());
      TIMING_TENDENCY_TASK.offer(new TimingTendencyAnalyzeDTO(DAILY, code, stock.getName(), tendencyItemModels, 2));
    });
  }

  @Scheduled(cron = "30 0/10 * * * ?")
  public void timingTendencyColdListSync() {
    log.debug("======================timingTendencyColdListSync start...==========================");
    COLD_STOCK.forEach((code, stock) -> {
      List<TendencyItemModel> tendencyItemModels = InitListener.randomClient().stockTendencyOfMinute(stock.getCategory(), code, stock.getName());
      TIMING_TENDENCY_TASK.offer(new TimingTendencyAnalyzeDTO(DAILY, code, stock.getName(), tendencyItemModels, 1));
    });
  }

}
