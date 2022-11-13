package com.stock.core.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.stock.core.entity.Stock;
import com.stock.core.mapper.StockMapper;
import com.stock.core.service.IStockService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 股票 服务实现类
 * </p>
 *
 * @author caijinglong
 * @since 2022-11-310 22:56:44
 */
@Service
public class StockServiceImpl extends ServiceImpl<StockMapper, Stock> implements IStockService {

}
