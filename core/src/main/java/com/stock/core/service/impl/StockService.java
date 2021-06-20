package com.stock.core.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.stock.core.service.IStockService;
import com.stock.entity.Stock;
import com.stock.mapper.StockMapper;
import org.springframework.stereotype.Service;

@Service
public class StockService extends ServiceImpl<StockMapper, Stock> implements IStockService {
}
