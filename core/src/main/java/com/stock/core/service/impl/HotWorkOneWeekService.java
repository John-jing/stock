package com.stock.core.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.stock.core.service.IHotWordOneWeekService;
import com.stock.entity.HotWordOneWeek;
import com.stock.mapper.HotWordOneWeekMapper;
import org.springframework.stereotype.Service;

@Service
public class HotWorkOneWeekService extends ServiceImpl<HotWordOneWeekMapper, HotWordOneWeek> implements IHotWordOneWeekService {
}
