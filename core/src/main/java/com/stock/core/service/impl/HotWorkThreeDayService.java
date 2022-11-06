package com.stock.core.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.stock.core.service.IHotWordThreeDayService;
import com.stock.entity.HotWordThreeDay;
import com.stock.mapper.HotWordThreeDayMapper;
import org.springframework.stereotype.Service;

@Service
public class HotWorkThreeDayService extends ServiceImpl<HotWordThreeDayMapper, HotWordThreeDay> implements IHotWordThreeDayService {
}
