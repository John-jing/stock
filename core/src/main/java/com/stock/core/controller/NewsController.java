package com.stock.core.controller;


import cn.hutool.http.HttpUtil;
import com.stock.core.controller.base.BaseController;
import com.stock.core.service.IStockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static com.stock.core.controller.base.BaseController.BASE_PATH;


@RequestMapping(value = BASE_PATH + "/news")
@RestController
@Slf4j
public class NewsController extends BaseController {

  @Autowired
  private IStockService stockService;

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
