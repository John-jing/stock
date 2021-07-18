package com.stock.core.controller;


import cn.hutool.http.HttpUtil;
import com.stock.core.controller.base.BaseController;
import com.stock.core.service.IStockService;
import com.stock.entity.News;
import com.stock.es.service.INewsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

import static com.stock.core.controller.base.BaseController.BASE_PATH;


@RequestMapping(value = BASE_PATH + "/news")
@RestController
@Slf4j
public class NewsController extends BaseController {

  @Autowired
  private IStockService stockService;

  @Autowired
  private INewsService newsService;

  @RequestMapping("/book/{id}")
  public News getBookById(@PathVariable String id) {
    Optional<News> opt = newsService.findById(id);
    News book = opt.get();
    System.out.println(book);
    return book;
  }

  @RequestMapping("/save")
  public void Save() {
    News book = new News("1", "ES入门教程", "程裕强", "2018-10-01");
    System.out.println(book);
    newsService.save(book);
  }

  @GetMapping
  public String news() throws IOException {
    String page = getFormWeb();
    return page;
  }

  private String getFormWeb() throws IOException {
    String pageStr = HttpUtil.get("http://finance.people.com.cn/n1/2021/0711/c1004-32154367.html", StandardCharsets.UTF_8);

    return pageStr;
  }
}
