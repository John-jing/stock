package com.stock.core.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.stock.core.entity.DailyLimit;
import com.stock.core.mapper.DailyLimitMapper;
import com.stock.core.service.IDailyLimitService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 每日涨/跌停板 服务实现类
 * </p>
 *
 * @author caijinglong
 * @since 2022-11-310 22:56:44
 */
@Service
public class DailyLimitServiceImpl extends ServiceImpl<DailyLimitMapper, DailyLimit> implements IDailyLimitService {

}
