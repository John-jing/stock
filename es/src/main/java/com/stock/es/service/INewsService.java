package com.stock.es.service;

import com.stock.entity.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

public interface INewsService {

  Optional<News> findById(String id);

  News save(News blog);

  void delete(News blog);

  Optional<News> findOne(String id);

  List<News> findAll();

  Page<News> findByPostDate(String author, PageRequest pageRequest);

  Page<News> findByUrl(String url, PageRequest pageRequest);
}
