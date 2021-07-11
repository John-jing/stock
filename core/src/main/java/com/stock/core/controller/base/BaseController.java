package com.stock.core.controller.base;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import java.util.regex.Pattern;


@RestController
@Slf4j
public class BaseController {

  public static final String BASE_PATH = "/astock/v1";

  protected static final Pattern STOCK_NAME_PATTERN = Pattern.compile("([\\u4E00-\\u9FA5A-Za-z_-]+)\\(([0-9]{6})\\)$");


}
