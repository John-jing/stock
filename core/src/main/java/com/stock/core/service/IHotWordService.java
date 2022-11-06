package com.stock.core.service;

import com.stock.entity.News;

import java.util.Set;

public interface IHotWordService {

  boolean save(News news, Set<String> words);
}
