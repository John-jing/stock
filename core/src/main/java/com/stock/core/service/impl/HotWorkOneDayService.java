package com.stock.core.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.stock.core.entity.HotWordOneDay;
import com.stock.core.mapper.HotWordOneDayMapper;
import com.stock.core.service.IHotWordOneDayService;
import org.springframework.stereotype.Service;

@Service
public class HotWorkOneDayService extends ServiceImpl<HotWordOneDayMapper, HotWordOneDay> implements IHotWordOneDayService {
}
