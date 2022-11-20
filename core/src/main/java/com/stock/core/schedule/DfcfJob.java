package com.stock.core.schedule;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.stock.core.entity.DailyLimit;
import com.stock.core.entity.DfcfQsPool;
import com.stock.core.export.TxtExport;
import com.stock.core.http.constants.DFCFConstant;
import com.stock.core.http.constants.DataOriginEnum;
import com.stock.core.model.dfcf.DailyLimitModel;
import com.stock.core.model.dfcf.PageModel;
import com.stock.core.service.IDailyLimitService;
import com.stock.core.service.IDfcfQsPoolService;
import com.stock.core.utils.GitUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static com.stock.core.http.constants.HttpConstant.HEADER;

/**
 * 东方财富
 * 涨停板同步、
 * 强势股池同步
 *
 * @author caijinglong
 * @date 2022-10-23
 */
@Slf4j
@Component
@EnableScheduling
public class DfcfJob {

  private static final LocalDate LOCAL_DATE = LocalDate.now().minusDays(1);

  @Resource
  private IDailyLimitService dailyLimitService;

  @Resource
  private IDfcfQsPoolService dfcfQsPoolService;

  @Resource
  private TxtExport txtExport;

  @Scheduled(cron = "0 0 17 * * ?")
  public void dailyRaisingLimit() {
    LocalDate daily = LOCAL_DATE;
    boolean isRaise = true;
    Map<String, String> queryMap = new HashMap<>();
    queryMap.put("date", daily.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
    try {
      Thread.sleep(1000 + RandomUtil.randomInt(-500, 500));
      log.debug("======================doSyncRaisingLimit start...{}==========================", daily);
      String path = "/getTopicZTPool";
      List<DailyLimitModel> dailyRaisingLimitModels = getFromDFCF(path, queryMap, Object.class);
      List<DailyLimit> dailyRaisingLimits = toDailyLimitEntity(daily, dailyRaisingLimitModels, isRaise);
      try {
        dailyLimitService.saveBatch(dailyRaisingLimits);
      } catch (Exception e) {
        log.error("doSyncRaisingLimit save error", e);
      }
      log.debug("======================doSyncRaisingLimit end.==========================");

      log.debug("======================doSyncRaisingLimit export start...{}==========================", daily);
      try {
        txtExport.export(JSONUtil.parseArray(dailyRaisingLimits),
                "dailyRaisingLimit", LOCAL_DATE.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
      } catch (Exception e) {
        log.error("doSyncRaisingLimit export error", e);
      }
      log.debug("======================doSyncRaisingLimit export end.==========================");

      log.debug("======================GitUtils.push start...{}==========================", daily);
      try {
        GitUtils.pushByBat(Optional.of("doSyncRaisingLimit"));
      } catch (Exception e) {
        log.error("doSyncRaisingLimit export error", e);
      }
      log.debug("======================GitUtils.push end.==========================");

    } catch (Exception e) {
      log.error("doSyncRaisingLimit error.", e);
    }

  }

  @Scheduled(cron = "0 0 17 * * ?")
  public void dailyDownLimit() {
    LocalDate daily = LOCAL_DATE;
    boolean isRaise = false;
    Map<String, String> queryMap = new HashMap<>();
    queryMap.put("date", daily.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
    List<DailyLimit> dailyDownLimits = Collections.emptyList();
    try {
      Thread.sleep(1000 + RandomUtil.randomInt(-500, 500));
      String path = "/getTopicDTPool";
      log.debug("======================doSyncDownLimit start...{}==========================", daily);
      List<DailyLimitModel> dailyDownLimitModels = getFromDFCF(path, queryMap, Object.class);
      dailyDownLimits = toDailyLimitEntity(daily, dailyDownLimitModels, isRaise);
      dailyLimitService.saveBatch(dailyDownLimits);
      log.debug("======================doSyncDownLimit end.==========================");
    } catch (Exception e) {
      log.error("doSyncDownLimit error.", e);
    }

    log.debug("======================doSyncRaisingLimit export start...{}==========================", daily);
    try {
      txtExport.export(JSONUtil.parseArray(dailyDownLimits),
              "dailyDownLimits", LOCAL_DATE.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
    } catch (Exception e) {
      log.error("doSyncRaisingLimit export error", e);
    }
    log.debug("======================doSyncRaisingLimit export end.==========================");

    log.debug("======================GitUtils.push start...{}==========================", daily);
    try {
      GitUtils.pushByBat(Optional.of("doSyncRaisingLimit"));
    } catch (Exception e) {
      log.error("doSyncRaisingLimit export error", e);
    }
    log.debug("======================GitUtils.push end.==========================");

  }

  /**
   * 强势股池
   */
  @Scheduled(cron = "0 0 17 * * ?")
  public void syncQsPool() {
    LocalDate daily = LOCAL_DATE;
    List<DfcfQsPool> dfcfQsPools = Collections.emptyList();
    try {
      Thread.sleep(1000 + RandomUtil.randomInt(-500, 500));
      log.debug("======================syncQsPool start...{}==========================", daily);
      String path = "/getTopicQSPool";
      Map<String, String> queryMap = new HashMap<>();
      queryMap.put("date", daily.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
      List<DailyLimitModel> dailyRaisingLimitModels = getFromDFCF(path, queryMap, Object.class);

      dfcfQsPools = toQsPool(daily, dailyRaisingLimitModels);
      dfcfQsPoolService.saveBatch(dfcfQsPools);
      log.debug("======================syncQsPool end.==========================");
    } catch (Exception e) {
      log.error("syncQsPool error.", e);
    }

    log.debug("======================doSyncRaisingLimit export start...{}==========================", daily);
    try {
      txtExport.export(JSONUtil.parseArray(dfcfQsPools),
              "dailyDownLimits", LOCAL_DATE.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
    } catch (Exception e) {
      log.error("doSyncRaisingLimit export error", e);
    }
    log.debug("======================doSyncRaisingLimit export end.==========================");

    log.debug("======================GitUtils.push start...{}==========================", daily);
    try {
      GitUtils.pushByBat(Optional.of("doSyncRaisingLimit"));
    } catch (Exception e) {
      log.error("doSyncRaisingLimit export error", e);
    }
    log.debug("======================GitUtils.push end.==========================");

  }

  /**
   * 涨停板、强势股池
   *
   * @param query
   * @return
   */
  private List<DailyLimitModel> getFromDFCF(String path, Map<String, String> query, Class<?> responseType) {
    Map<String, Object> queryMap = new HashMap<>();
//    queryMap.put("cb", "callbackdata6878480");
    queryMap.put("cb", "");
    queryMap.put("ut", "7eea3edcaed734bea9cbfc24409ed989");
    queryMap.put("dpt", "wz.ztzt");
    queryMap.put("Pageindex", 0);
    queryMap.put("pagesize", 320);
    queryMap.put("_", String.valueOf(System.currentTimeMillis()));
    queryMap.put("sort", "amount:asc");
//    queryMap.put("date", daily.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
    queryMap.putAll(query);
//    String path = isRaise ? "/getTopicZTPool" : "/getTopicDTPool";
    String url = DataOriginEnum.DFCF.getHostname() + path;
    HttpRequest httpRequest = HttpUtil.createGet(url);
    httpRequest.form(queryMap);
    httpRequest.header(HEADER);
    String body = httpRequest.execute().body();
    log.debug("getFromDFCF resulr:{}", body);
    PageModel pageModel = JSONUtil.toBean(body, PageModel.class);
    if (pageModel == null || pageModel.getData() == null) {
      log.info("getFromDFCF path:{}, query:{} return. pageModel or data is null", path, query);
      return Collections.emptyList();
    }

    Integer count = pageModel.getData().getTc();
    if (count > 0 && CollUtil.isEmpty(pageModel.getData().getPool())) {
      log.error("getFromDFCF path:{}, query:{} error. count:{}, but data.pool is empty", path, query, count);
      return Collections.emptyList();
    }
    log.info("getFromDFCF path:{}, count:{}", path, count);
    return pageModel.getData().getPool();

  }

  private List<DailyLimit> toDailyLimitEntity(LocalDate daily, List<DailyLimitModel> dailyLimitModels, boolean isRaise) {
    return dailyLimitModels.stream().map(dailyLimitModel -> new DailyLimit()
            .setDaily(daily)
            .setIsRaise(isRaise)
            .setAmount(dailyLimitModel.getAmount())
            .setCode(dailyLimitModel.getC())
            .setName(dailyLimitModel.getN())
            .setTurnoverRate(dailyLimitModel.getHs())
            .setFailTimes(isRaise ? dailyLimitModel.getZbc() : dailyLimitModel.getOc())
            .setFfCap(dailyLimitModel.getLtsz())
            .setMktCap(dailyLimitModel.getTshare())
            .setIndustry(dailyLimitModel.getHybk())
            .setLimitDays(isRaise ? dailyLimitModel.getLbc() : dailyLimitModel.getDays())
            .setLimitFund(dailyLimitModel.getFund())
            .setLimitStatistic(JSONUtil.toJsonStr(dailyLimitModel.getZttj()))
            .setLimitTimeFirst(LocalTime.parse(append(dailyLimitModel.getFbt()), DateTimeFormatter.ofPattern("HHmmss")))
            .setLimitTimeLast(LocalTime.parse(append(dailyLimitModel.getLbt()), DateTimeFormatter.ofPattern("HHmmss")))
            .setPriceLimit(dailyLimitModel.getZdp())
            .setPrice(dailyLimitModel.getP() / 1000.0)).collect(Collectors.toList());
  }

  private List<DfcfQsPool> toQsPool(LocalDate daily, List<DailyLimitModel> dailyLimitModels) {
    return dailyLimitModels.stream().map(dailyLimitModel -> new DfcfQsPool()
            .setDaily(daily)
            .setAmount(dailyLimitModel.getAmount())
            .setCode(dailyLimitModel.getC())
            .setName(dailyLimitModel.getN())
            .setTurnoverRate(dailyLimitModel.getHs())
            .setFfCap(dailyLimitModel.getLtsz())
            .setMktCap(dailyLimitModel.getTshare())
            .setIndustry(dailyLimitModel.getHybk())
            .setLimitStatistic(JSONUtil.toJsonStr(dailyLimitModel.getZttj()))
            .setPriceLimit(dailyLimitModel.getZdp())
            .setPriceHighLimit(dailyLimitModel.getZtp() / 1000.0)
            .setPrice(dailyLimitModel.getP() / 1000.0)
            .setQrr(dailyLimitModel.getLb())
            .setIsNewHigh(dailyLimitModel.getNh() != null && 1 == dailyLimitModel.getNh())
            .setRemark(DFCFConstant.CC_MAP.get(dailyLimitModel.getCc()))
    ).collect(Collectors.toList());
  }

  private CharSequence append(String fbt) {
    if (fbt == null) {
      return "000000";
    }
    if (fbt.length() == 5) {
      return "0" + fbt;
    }
    return fbt;
  }


}
