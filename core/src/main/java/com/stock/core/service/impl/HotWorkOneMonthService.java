package com.stock.core.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.stock.core.service.IHotWordOneMonthService;
import com.stock.entity.HotWordOneMonth;
import com.stock.mapper.HotWordOneMonthMapper;
import org.springframework.stereotype.Service;

@Service
public class HotWorkOneMonthService extends ServiceImpl<HotWordOneMonthMapper, HotWordOneMonth> implements IHotWordOneMonthService {
}
