package com.stock.core.service.stocklist;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.stock.core.entity.Stock;
import com.stock.core.service.StockListComponent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

import static com.stock.core.constant.HttpConstant.HEADER;

/**
 * @author caijinglong
 * @date 2022-10-23
 */
@Slf4j
@Component
public class DefaultStockListComponent implements StockListComponent {

  @Deprecated
  public static void main(String[] args) {
    new DefaultStockListComponent().allStock();
  }

  @Override
  public List<Stock> allStock() {
    List<Stock> szStocks = szStock();

    List<Stock> shStocks = shStock();
    log.debug("szStock size:{}, shStock size:{}", szStocks.size(), shStocks.size());
    List<Stock> allStocks = new ArrayList<>(szStocks);
    allStocks.addAll(shStocks);
    return allStocks;
  }

  private List<Stock> szStock() {
    File file = new File("test.xlsx");
    try {
      String url = "http://www.szse.cn/api/report/ShowReport?SHOWTYPE=xlsx&CATALOGID=1110&TABKEY=tab1&random=" + System.currentTimeMillis();
      HttpUtil.downloadFile(url, file);

      ExcelReader excelReader = ExcelUtil.getReader(file);

      List<List<Object>> rows = excelReader.read();
      log.debug("row title:{}", JSONUtil.toJsonStr(rows.get(0)));
      rows = rows.subList(1, rows.size());
      return rows.stream().map(line -> {
        try {
          return Stock.builder()
                  .code(line.get(4).toString())
                  .name(String.valueOf(line.get(5)))
                  .category("sz")
                  .build();
        } catch (Exception e) {
          log.error("szStock line error, line:{}", JSONUtil.toJsonStr(line), e);
          return null;
        }
      })
              .filter(Objects::nonNull)
              .collect(Collectors.toList());
    } catch (Exception e) {
      log.error("szStock error.", e);
      return new ArrayList<>();
    } finally {
      if (file.exists()) {
        file.delete();
      }
    }
  }

  private List<Stock> shStock() {
    String baseUrl = "http://query.sse.com.cn/security/stock/downloadStockListFile.do?csrcCode=&stockCode=&areaName=&stockType=";
    // 主板 stockType=1
    Map<String, List<String>> headers = new HashMap<>(HEADER);
    headers.put("Referer", Collections.singletonList("http://www.sse.com.cn/assortment/stock/list/share/"));
    HttpResponse httpResponse = HttpUtil.createGet(baseUrl + "1")
            .header(headers)
            .execute();
    String stockStr = httpResponse.body();

    List<Stock> stocks = extractShStocks(stockStr);

    log.debug("stocks size:{}", stocks.size());

    // 科创板 stockType=8
    httpResponse = HttpUtil.createGet(baseUrl + "8")
            .header(headers)
            .execute();
    stockStr = httpResponse.body();
    log.debug(stockStr);
    stocks.addAll(extractShStocks(stockStr));
    return stocks;
  }

  private List<Stock> extractShStocks(String stockStr) {
    try {
      return Arrays.stream(StrUtil.split(stockStr, "\n"))
              .filter(line -> !line.contains("公司代码"))
              .map(line -> {
                String code = StrUtil.subBefore(line, "\t", false);
                String name = StrUtil.subBetween(line, "\t");
                return Stock.builder()
                        .code(code)
                        .name(name)
                        .category("sh")
                        .build();
              }).collect(Collectors.toList());
    } catch (Exception e) {
      log.error("extractShStocks error.", e);
      return new ArrayList<>();
    }
  }

}
