package com.stock.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.stock.core.constant.DBConstant;
import com.stock.core.service.*;
import com.stock.entity.HotWordOneDay;
import com.stock.entity.News;
import com.stock.es.service.INewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
public class HotWordService implements IHotWordService {

  @Autowired
  private IHotWordOneDayService hotWordOneDayService;
  @Autowired
  private IHotWordTwoDayService hotWordTwoDayService;
  @Autowired
  private IHotWordThreeDayService hotWordThreeDayService;
  @Autowired
  private IHotWordOneWeekService hotWordOneWeekService;
  @Autowired
  private IHotWordOneMonthService hotWordOneMonthService;

  @Autowired
  private INewsService newsService;

  @Override
  @Transactional
  public boolean save(News news, Set<String> words) {
    newsService.save(news);
    for (String word : words) {
      saveOrUpdateHotWordOneDay(word);
    }
    return true;
  }

  private boolean saveOrUpdateHotWordOneDay(String word) {
    HotWordOneDay hotWordOneDay = hotWordOneDayService.getOne(new LambdaQueryWrapper<HotWordOneDay>()
            .eq(HotWordOneDay::getWord, word)
            .last(DBConstant.LIMIT_ONE));
    if (hotWordOneDay == null) {
      hotWordOneDay = HotWordOneDay.builder().word(word).times(1).build();
    } else {
      hotWordOneDay.setTimes(hotWordOneDay.getTimes() + 1);
    }
    return hotWordOneDayService.saveOrUpdate(hotWordOneDay);
  }


}
