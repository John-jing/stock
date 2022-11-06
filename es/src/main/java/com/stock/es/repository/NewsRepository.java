package com.stock.es.repository;

import com.stock.entity.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface NewsRepository extends ElasticsearchRepository<News, String> {
  Page<News> findByPostDate(String postDate, Pageable pageable);

  Page<News> findByUrl(String url, Pageable pageable);
}
