package com.stock.core.http.sse;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.stock.core.entity.Stock;
import com.stock.core.http.Client;
import com.stock.core.http.constants.DataOriginEnum;
import com.stock.core.http.szse.model.SgtResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.*;

import static com.stock.core.http.constants.HttpConstant.HEADER;

/**
 * @author caijinglong
 * @date 2022-11-13
 */
@Slf4j
@Component
public class SzseClient implements Client {

  /**
   * 自定义请求头
   */
  private static final Map<String, List<String>> CUSTOM_HEADERS = new HashMap<>() {
    {
      put("Accept", Collections.singletonList("application/json, text/javascript, */*; q=0.01"));
      put("Accept-Encoding", Collections.singletonList("gzip, deflate"));
      put("Accept-Language", Collections.singletonList("zh-CN,zh;q=0.9,en;q=0.8"));
      put("Cache-Control", Collections.singletonList("no-cache"));
      put("Content-Type", Collections.singletonList("application/json"));
      put("host", Collections.singletonList("www.szse.cn"));
      put("Pragma", Collections.singletonList("no-cache"));
      put("Referer", Collections.singletonList("http://www.szse.cn/szhk/hkbussiness/underlylist/"));
      put("Connection", Collections.singletonList("keep-alive"));
      put("X-Request-Type", Collections.singletonList("ajax"));
      put("X-Requested-With", Collections.singletonList("XMLHttpRequest"));
      put("user-agent", Collections.singletonList("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/98.0.4758.102 Safari/537.36"));
    }
  };

  /**
   * 深港通
   */
  private static final String SGT_PATH = "/api/report/ShowReport/data";


  public static void main(String[] args) {
    List<Stock> list = new SzseClient().sgtStocks();
    System.out.println(JSONUtil.toJsonStr(list));
  }

  public List<Stock> sgtStocks() {
    String url = DataOriginEnum.SZSE.getHostname() + SGT_PATH;
    log.debug("======================sgtStocks start..., url:{}==========================", url);
    Map<String, List<String>> headers = new HashMap<>(CUSTOM_HEADERS);
    headers.putAll(HEADER);
    int pageNo = 1;
    int pageCount = 0;
    Map<String, Object> queryMap = queryMap(pageNo);

    List<Stock> sgtStocks = new ArrayList<>();
    do {
      queryMap.put("PAGENO", pageNo);
      HttpResponse httpResponse = HttpUtil.createGet(url)
              .form(queryMap)
              .header(headers)
              .execute();
      if (!httpResponse.isOk()) {
        log.error("sgtStocks fail. url:{}, httpResponse:{}", url, httpResponse.toString());
        continue;
      }
      if (JSONUtil.isJsonArray(httpResponse.body())) {
        JSONArray array = JSONUtil.parseArray(httpResponse.body());
        SgtResponse sgtResponse = JSONUtil.toBean(JSONUtil.toJsonStr(array.get(0)), SgtResponse.class);
        if (sgtResponse.getMetadata() != null && sgtResponse.getMetadata().getPagecount() != null) {
          pageCount = sgtResponse.getMetadata().getPagecount();
        }
        if (CollUtil.isNotEmpty(sgtResponse.getData())) {
          sgtResponse.getData()
                  .forEach(item -> sgtStocks.add(Stock.builder()
                          .name(item.getZqjc())
                          .code(item.getZqdm())
                          .category("sgt")
                          .build()));
        }
      }
      pageNo++;

      if (pageNo >= 100) {
        break;
      }
    } while (pageNo <= pageCount);
    return sgtStocks;
  }

  private Map<String, Object> queryMap(int pageNo) {
    Map<String, Object> queryMap = new HashMap<>();
    queryMap.put("SHOWTYPE", "JSON");
    queryMap.put("CATALOGID", "SGT_GGTBDQD");
    queryMap.put("TABKEY", "tab1");
    queryMap.put("PAGENO", pageNo);
    queryMap.put("random", RandomUtil.randomDouble(0, 1));
    return queryMap;
  }


}
