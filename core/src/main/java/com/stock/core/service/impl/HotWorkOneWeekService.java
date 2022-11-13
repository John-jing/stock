package com.stock.core.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.stock.core.entity.HotWordOneWeek;
import com.stock.core.mapper.HotWordOneWeekMapper;
import com.stock.core.service.IHotWordOneWeekService;
import org.springframework.stereotype.Service;

@Service
public class HotWorkOneWeekService extends ServiceImpl<HotWordOneWeekMapper, HotWordOneWeek> implements IHotWordOneWeekService {
}
