package com.stock.core.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.stock.core.entity.HotWordOneMonth;
import com.stock.core.mapper.HotWordOneMonthMapper;
import com.stock.core.service.IHotWordOneMonthService;
import org.springframework.stereotype.Service;

@Service
public class HotWorkOneMonthService extends ServiceImpl<HotWordOneMonthMapper, HotWordOneMonth> implements IHotWordOneMonthService {
}
