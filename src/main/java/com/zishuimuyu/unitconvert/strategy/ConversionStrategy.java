package com.zishuimuyu.unitconvert.strategy;

import com.zishuimuyu.unitconvert.model.ConvertResult;
import com.zishuimuyu.unitconvert.model.UnitEnum;

import java.math.BigDecimal;

/**
 * 转换策略接口
 * 
 * 定义了单位转换的基本操作规范，支持不同类型的转换策略
 * 
 * @author 紫水木鱼
 * @version 1.0.0
 * @since 2026-04-25
 */
public interface ConversionStrategy {
    
    /**
     * 执行单位转换
     * 
     * @param value 待转换的数值
     * @param fromUnit 源单位
     * @param toUnit 目标单位
     * @return 转换结果对象
     */
    ConvertResult<BigDecimal> convert(BigDecimal value, UnitEnum fromUnit, UnitEnum toUnit);
    
    /**
     * 检查是否支持给定的单位转换
     * 
     * @param fromUnit 源单位
     * @param toUnit 目标单位
     * @return 如果支持转换返回true
     */
    boolean supports(UnitEnum fromUnit, UnitEnum toUnit);
    
    /**
     * 获取策略优先级
     * 
     * 优先级高的策略会优先被尝试
     * 
     * @return 优先级值，值越大优先级越高
     */
    default int getPriority() {
        return 0;
    }
}