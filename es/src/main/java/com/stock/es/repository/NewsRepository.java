package com.stock.es.repository;

import com.stock.entity.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface NewsRepository extends ElasticsearchRepository<News, String> {
  Page<News> findByAuthor(String author, Pageable pageable);

  Page<News> findByTitle(String title, Pageable pageable);
}
