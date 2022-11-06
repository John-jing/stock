package com.stock.es.service.impl;

import com.stock.entity.News;
import com.stock.es.repository.NewsRepository;
import com.stock.es.service.INewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NewsService implements INewsService {

  @Autowired
  private NewsRepository newsRepository;

  @Override
  public Optional<News> findById(String id) {
    return newsRepository.findById(id);
  }

  @Override
  public News save(News blog) {
    return newsRepository.save(blog);
  }

  @Override
  public void delete(News blog) {
    newsRepository.delete(blog);
  }

  @Override
  public Optional<News> findOne(String id) {
    return Optional.empty();
  }

  @Override
  public List<News> findAll() {
    return (List<News>) newsRepository.findAll();
  }

  @Override
  public Page<News> findByPostDate(String postDate, PageRequest pageRequest) {
    return newsRepository.findByPostDate(postDate, pageRequest);
  }

  @Override
  public Page<News> findByUrl(String url, PageRequest pageRequest) {
    return newsRepository.findByUrl(url, pageRequest);
  }
}
