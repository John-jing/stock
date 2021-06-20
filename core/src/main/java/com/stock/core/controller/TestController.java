package com.stock.core.controller;


import cn.hutool.core.util.NumberUtil;
import com.stock.core.service.IStockService;
import com.stock.entity.Stock;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@RequestMapping(value = "/test")
@RestController
@Slf4j
public class TestController {

  private static final Pattern STOCK_NAME_PATTERN = Pattern.compile("([\\u4E00-\\u9FA5A-Za-z_-]+)\\(([0-9]{6})\\)$");

  @Autowired
  private IStockService stockService;

  @GetMapping
  public List<Stock> usrOrder(@RequestParam(required = false) String echostr) throws IOException {
    List<Stock> stocks = getFormWeb();
    stockService.saveBatch(stocks);
    return stocks;
  }

  private List<Stock> getFormWeb() throws IOException {
    Element resultDev = Jsoup.connect("http://www.bestopview.com/stocklist.html")
       .userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.114 Safari/537.36") //设置User-Agent
       .timeout(3000)
       .get()
       .body()
       .getElementsByClass("result").get(0);
    Elements liList = resultDev.child(0).getElementsByTag("li");
    log.info("liList size:{}", liList.size());
    List<Stock> stocks = new ArrayList<>();
    for (Element li : liList) {
      Element a = li.child(0);
      String aText = a.text();
      String aHref = a.attr("href");
      Matcher matcher = STOCK_NAME_PATTERN.matcher(aText);
      if (matcher.matches()) {
        String name = matcher.group(1);
        String code = matcher.group(2);
        Stock stock = Stock.builder()
           .code(NumberUtil.parseInt(code))
           .name(name)
           .type("shang")
           .detailUrl(aHref)
           .build();

        stocks.add(stock);
      }
    }
    return stocks;
  }
}
