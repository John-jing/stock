package com.stock.core.controller;


import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.stock.core.controller.base.BaseController;
import com.stock.core.service.IHotWordService;
import com.stock.entity.News;
import com.stock.es.service.INewsService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static com.stock.core.controller.base.BaseController.BASE_PATH;


@RequestMapping(value = BASE_PATH + "/news")
@RestController
@Slf4j
public class NewsController extends BaseController {

  @Autowired
  private IHotWordService hotWordService;

  @Autowired
  private INewsService newsService;

  @Value("${spring.elasticsearch.rest.uris}")
  private String esUrl;

  @RequestMapping("/book/{id}")
  public News getBookById(@PathVariable String id) {
    Optional<News> opt = newsService.findById(id);
    News book = opt.get();
    System.out.println(book);
    return book;
  }

  @RequestMapping("/save")
  public void save() throws IOException {
    String url = "/n1/2021/0711/c1004-32154367.html";
    String content = getFormWeb(url);
    News news = News.builder()
            .id(UUID.randomUUID().toString())
            .content(content)
            .url("http://finance.people.com.cn" + url)
            .postDate(DateUtil.formatDateTime(new Date()))
            .build();
    Set<String> words = getWord(content);
    hotWordService.save(news, words);
  }

  @GetMapping
  public String news(@RequestParam(required = false) String path) throws IOException {
    String page = getFormWeb(path);
    return page;
  }

  private String getFormWeb(String path) throws IOException {
    String host = "http://finance.people.com.cn";
    String myPath = Optional.ofNullable(path).orElse("/n1/2021/0711/c1004-32154367.html");
    Element resultDev = Jsoup.connect(host + myPath)
            .userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.114 Safari/537.36") //设置User-Agent
            .timeout(3000)
            .get()
            .body()
            .getElementsByClass("rm_txt_con").get(0);
    if (resultDev != null) {
      return resultDev.text();
    }
    return "";
  }

  private Set<String> getWord(String content) throws IOException {
    if (StrUtil.isBlank(content)) {
      return Collections.emptySet();
    }
    String host = esUrl + "/news/_analyze";
    Map<String, Object> data = Map.of("analyzer", "ik_max_word",
            "text", content);
    HttpResponse response = HttpRequest.post(host).body(JSONUtil.toJsonStr(data), "application/json").execute();
    String tokenStr = response.body();
    if (JSONUtil.isJson(tokenStr)) {
      JSONArray tokens = JSONUtil.parseObj(tokenStr).getJSONArray("tokens");
      List<Tokens> tokensList = tokens.toList(Tokens.class);
      return tokensList.parallelStream().map(Tokens::getToken).collect(Collectors.toSet());
    }
    return Collections.emptySet();
  }

  @AllArgsConstructor
  @Data
  class Tokens {
    String token;
    String type;
    Integer position;
    Integer start_offset;
    Integer end_offset;
  }
}
