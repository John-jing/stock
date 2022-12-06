package com.stock.core.http.ths;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.stock.core.http.TimingTendencyClient;
import com.stock.core.http.constants.DataOriginEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

import static com.stock.core.http.constants.HttpConstant.HEADER;

/**
 * @author caijinglong
 * @date 2022-11-13
 */
@Slf4j
@Component
public class ThsClient implements TimingTendencyClient {

  /**
   * 自定义请求头
   */
  private static final Map<String, List<String>> CUSTOM_HEADERS = new HashMap<>() {
    {
      put("Accept", Collections.singletonList("*/*"));
      put("Accept-Encoding", Collections.singletonList("gzip, deflate, br"));
      put("Accept-Language", Collections.singletonList("zh-CN,zh;q=0.9,en;q=0.8"));
      put("Referer", Collections.singletonList("http://stockpage.10jqka.com.cn/"));
      put("Connection", Collections.singletonList("keep-alive"));
      put("Pragma", Collections.singletonList("no-cache"));
      put("Cache-Control", Collections.singletonList("no-cache"));
      put("sec-ch-u", Collections.singletonList("\" Not A;Brand\";v=\"99\", \"Chromium\";v=\"98\", \"Google Chrome\";v=\"98\""));
      put("sec-ch-ua-mobile", Collections.singletonList("?0"));
      put("sec-ch-ua-platform", Collections.singletonList("Windows"));
      put("Sec-Fetch-Site", Collections.singletonList("cross-site"));
      put("Sec-Fetch-Mode", Collections.singletonList("no-cors"));
      put("Sec-Fetch-Dest", Collections.singletonList("script"));
      put("Host", Collections.singletonList("d.10jqka.com.cn"));
    }
  };

  private static final String STOCK_CODE_PREFIX = "hs_";
  /**
   * 分时图
   */
  private static final String STOCK_TENDENCY_PATH = "/v6/time/{stockCode}/defer/last.js";

  /**
   * 日K线
   */
  private static final String STOCK_TENDENCY_OF_DAY_PATH = "/v6/time/{stockCode}/defer/last.js";

  public static void main(String[] args) {
    List<TendencyItemModel> tendencyItemModels = new ThsClient().stockTendencyOfMinute("003007");
    System.out.println(JSONUtil.toJsonStr(tendencyItemModels));
  }

  public static void stockTencencyOfDay() {


  }

  public List<TendencyItemModel> stockTendencyOfMinute(String stockCode) {
    String stockCodeWithPrefix = STOCK_CODE_PREFIX + stockCode;
    String path = StrUtil.format(STOCK_TENDENCY_PATH, Map.of("stockCode", stockCodeWithPrefix));
    String url = DataOriginEnum.THS.getHostname() + path;
    log.debug("======================stockTendencyOfMinute start...{}, url:{}==========================", stockCode, url);
    Map<String, List<String>> headers = new HashMap<>(CUSTOM_HEADERS);
    headers.putAll(HEADER);
    HttpResponse httpResponse = HttpUtil.createGet(url)
            .header(headers)
            .execute();
    if (!httpResponse.isOk()) {
      log.error("stockTendencyOfMinute fail. url:{}, httpResponse:{}", url, httpResponse.toString());
      return Collections.emptyList();
    }

    String body = httpResponse.body();
    body = StrUtil.removePrefix(body, "quotebridge_v6_time_" + stockCodeWithPrefix + "_defer_last(");
    body = StrUtil.removeSuffix(body, ")");
    TendencyModel tendencyModel = JSONUtil.parseObj(body)
            .get(stockCodeWithPrefix, TendencyModel.class);

    if (tendencyModel == null) {
      log.error("stockTendencyOfMinute fail. httpResponse:{}", httpResponse.toString());
      return Collections.emptyList();
    }

    return Arrays.stream(StrUtil.split(tendencyModel.getData(), ";"))
            .map(line -> {
              String[] lineItems = StrUtil.split(line, ",");
              return TendencyItemModel.builder()
                      .timing(Integer.valueOf(lineItems[0]))
                      .price(Double.valueOf(lineItems[1]))
                      .tradingAmount(Long.valueOf(lineItems[2]))
                      .avgPrice(Double.valueOf(lineItems[3]))
                      .tradingVolume(Long.valueOf(lineItems[4]))
                      .build();
            }).collect(Collectors.toList());
  }

}
