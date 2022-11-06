package com.stock.core.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.stock.core.service.IHotWordTwoDayService;
import com.stock.entity.HotWordTwoDay;
import com.stock.mapper.HotWordTwoDayMapper;
import org.springframework.stereotype.Service;

@Service
public class HotWorkTwoDayService extends ServiceImpl<HotWordTwoDayMapper, HotWordTwoDay> implements IHotWordTwoDayService {
}
