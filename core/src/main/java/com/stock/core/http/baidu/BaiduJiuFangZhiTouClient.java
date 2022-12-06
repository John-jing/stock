package com.stock.core.http.baidu;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.stock.core.http.TimingTendencyClient;
import com.stock.core.http.constants.DataOriginEnum;
import com.stock.core.http.ths.TendencyItemModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;

import static com.stock.core.http.constants.HttpConstant.HEADER;

/**
 * @author caijinglong
 * @date 2022-11-13
 */
@Slf4j
@Component
public class BaiduClient implements TimingTendencyClient {

  /**
   * 自定义请求头
   */
  private static final Map<String, List<String>> CUSTOM_HEADERS = new HashMap<>() {
    {
      put("Accept", Collections.singletonList("*/*"));
      put("Accept-Encoding", Collections.singletonList("gzip, deflate, br"));
      put("Accept-Language", Collections.singletonList("zh-CN,zh;q=0.9,en;q=0.8"));
      // wd 替换为code编码
      put("Referer", Collections.singletonList("https://www.baidu.com/s?ie=utf-8&f=8&rsv_bp=1&tn=baidu&wd=002875&oq=%25E5%2589%258D%25E7%25AB%25AF%2520%25E5%25BB%25BA%25E7%25AB%258Bwebsocket&rsv_pq=acfca3be00006714&rsv_t=1018STczISYNZAp0y5iwwyDpOLQ4tjn0%2BNMu%2FjXbSX8if28Srh8w6Co1ZKE&rqlang=cn&rsv_enter=1&rsv_dl=tb&rsv_sug3=9&rsv_sug1=2&rsv_sug7=100&rsv_n=2&rsv_sug2=0&rsv_btype=t&inputT=7295&rsv_sug4=7385"));
      put("Connection", Collections.singletonList("keep-alive"));
      put("authority", Collections.singletonList("opendata.baidu.com"));
      put("Pragma", Collections.singletonList("no-cache"));
      put("Cache-Control", Collections.singletonList("no-cache"));
      put("sec-ch-ua", Collections.singletonList("\" Not A;Brand\";v=\"99\", \"Chromium\";v=\"98\", \"Google Chrome\";v=\"98\""));
      put("sec-ch-ua-mobile", Collections.singletonList("?0"));
      put("sec-ch-ua-platform", Collections.singletonList("Windows"));
      put("user-agent", Collections.singletonList("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/98.0.4758.102 Safari/537.36"));
      put("Sec-Fetch-Site", Collections.singletonList("cross-site"));
      put("Sec-Fetch-Mode", Collections.singletonList("no-cors"));
      put("Sec-Fetch-Dest", Collections.singletonList("script"));
    }
  };

  /**
   * 分时图
   */
  private static final String BAIDU_OPEN_DATA_PATH = "/api.php";


  public static void main(String[] args) throws UnsupportedEncodingException {
    System.out.println(URLDecoder.decode("%E7%99%BE%E5%BA%A6%E8%82%A1%E5%B8%82%E9%80%9A", "UTF-8"));
    List<TendencyItemModel> list = new BaiduClient().stockTendencyOfMinute("600050", "中国联通");
    System.out.println(JSONUtil.toJsonStr(list));
  }

  public List<TendencyItemModel> stockTendencyOfMinute(String stockCode, String stockName) {
    String url = DataOriginEnum.BAIDU_OPEN_DATA.getHostname() + BAIDU_OPEN_DATA_PATH;
    log.debug("======================stockTendencyOfMinute start...{}, url:{}==========================", stockCode, url);
    Map<String, List<String>> headers = new HashMap<>(CUSTOM_HEADERS);
    headers.putAll(HEADER);
    long timestamp = System.currentTimeMillis();
    Map<String, Object> queryMap = queryMap(stockCode, stockName);
    String bodyCover = "jsonp_" + timestamp + "_70606";
    queryMap.put("cb", bodyCover);
    HttpResponse httpResponse = HttpUtil.createGet(url)
            .form(queryMap)
            .header(headers)
            .execute();
    if (!httpResponse.isOk()) {
      log.error("stockTendencyOfMinute fail. url:{}, httpResponse:{}", url, httpResponse.toString());
      return Collections.emptyList();
    }

    String body = httpResponse.body();
    body = StrUtil.unWrap(body, bodyCover + "(", ")");
    JSONObject jsonObject = JSONUtil.parseObj(body);
    JSONArray jsonArray = jsonObject.getJSONArray("Result");
    List<TendencyItemModel> tendencyItemModels = new ArrayList<>();
    if (!jsonArray.isEmpty()) {
      jsonObject = JSONUtil.parseObj(jsonArray.get(0));
      jsonObject = jsonObject.getJSONObject("DisplayData");
      jsonObject = jsonObject.getJSONObject("resultData");
      jsonObject = jsonObject.getJSONObject("tplData");
      jsonObject = jsonObject.getJSONObject("result");
      jsonObject = jsonObject.getJSONObject("tab");
      String timingTendencyStr = jsonObject.getStr("p");
      String[] timingTendencys = StrUtil.split(timingTendencyStr, ";");
      for (String timingTendency : timingTendencys) {
        String[] timingTendencyItems = StrUtil.split(timingTendency, ",");
        Long tradingVolume = parseTradingVolume(timingTendencyItems[5]);
        Double avgPrice = Double.valueOf(timingTendencyItems[6]);
        TendencyItemModel tendencyItemModel = TendencyItemModel.builder()
                .timing(Integer.valueOf(StrUtil.removeAll(timingTendencyItems[0], ":")))
                .price(Double.valueOf(timingTendencyItems[1]))
                .tradingVolume(tradingVolume)
                .avgPrice(Double.valueOf(timingTendencyItems[6]))
                .tradingAmount((long) (tradingVolume * avgPrice))
                .build();
        tendencyItemModels.add(tendencyItemModel);
      }
    }
    return tendencyItemModels;
  }


  private Map<String, Object> queryMap(String code, String name) {
    Map<String, Object> queryMap = new HashMap<>();
    queryMap.put("resource_id", "5353");
    queryMap.put("group", "quotation_minute_ab");
    queryMap.put("query", code);
    queryMap.put("code", code);
    queryMap.put("all", 1);
    queryMap.put("pointType", "string");
    queryMap.put("dsp", "pc");
    queryMap.put("requestType", "async");
    queryMap.put("stockName", name);
    queryMap.put("provideName", "百度股市通");
    queryMap.put("sid", "36554_37517_37772_34812_37778_37760_37742_26350_37788");
    queryMap.put("cb", "jsonp_" + System.currentTimeMillis() + "_70606");
    return queryMap;
  }

  private Long parseTradingVolume(String tradingVolumeStr) {
    if (tradingVolumeStr == null) {
      return 0L;
    }
    try {
      if (tradingVolumeStr.contains("万")) {
        return (long) (10000 * Double.parseDouble(StrUtil.removeSuffix(tradingVolumeStr, "万")));
      } else {
        return (long) Double.parseDouble(tradingVolumeStr);
      }
    } catch (Exception e) {
      log.error("parseTradingVolume fail.", e);
      return 0L;
    }

  }

}
