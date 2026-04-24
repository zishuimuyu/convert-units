package com.zishuimuyu.unitconvert.strategy;

import com.zishuimuyu.unitconvert.exception.UnitConversionException;
import com.zishuimuyu.unitconvert.model.ConvertResult;
import com.zishuimuyu.unitconvert.model.UnitEnum;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * 转换策略管理器
 * 
 * 管理多个转换策略，并按优先级顺序尝试执行转换
 * 
 * @author 紫水木鱼
 * @version 1.0.0
 * @since 2026-04-25
 */
public class ConversionStrategyManager {
    
    private final List<ConversionStrategy> strategies;

    public ConversionStrategyManager(List<ConversionStrategy> strategies) {
        // 按优先级排序策略
        this.strategies = new ArrayList<>(strategies);
        this.strategies.sort(Comparator.comparingInt(ConversionStrategy::getPriority).reversed());
    }

    /**
     * 添加策略
     */
    public void addStrategy(ConversionStrategy strategy) {
        strategies.add(strategy);
        strategies.sort(Comparator.comparingInt(ConversionStrategy::getPriority).reversed());
    }

    /**
     * 执行转换
     */
    public ConvertResult<BigDecimal> convert(BigDecimal value, UnitEnum fromUnit, UnitEnum toUnit) {
        for (ConversionStrategy strategy : strategies) {
            if (strategy.supports(fromUnit, toUnit)) {
                return strategy.convert(value, fromUnit, toUnit);
            }
        }
        
        throw new UnitConversionException(
            String.format("无法在 %s (%s) 和 %s (%s) 之间进行转换", 
                fromUnit.getAbbr(), fromUnit.getMeasure(),
                toUnit.getAbbr(), toUnit.getMeasure()),
            "CONVERSION_NOT_SUPPORTED"
        );
    }

    /**
     * 检查是否支持转换
     */
    public boolean canConvertBetween(UnitEnum fromUnit, UnitEnum toUnit) {
        return strategies.stream()
            .anyMatch(strategy -> strategy.supports(fromUnit, toUnit));
    }
}