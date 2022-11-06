package com.stock.core.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.stock.core.service.IHotWordOneDayService;
import com.stock.entity.HotWordOneDay;
import com.stock.mapper.HotWordOneDayMapper;
import org.springframework.stereotype.Service;

@Service
public class HotWorkOneDayService extends ServiceImpl<HotWordOneDayMapper, HotWordOneDay> implements IHotWordOneDayService {
}
