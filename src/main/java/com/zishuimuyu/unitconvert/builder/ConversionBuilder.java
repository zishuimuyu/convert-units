package com.zishuimuyu.unitconvert.builder;

import com.zishuimuyu.unitconvert.model.ConvertResult;
import com.zishuimuyu.unitconvert.model.UnitEnum;
import com.zishuimuyu.unitconvert.service.IUnitConversionService;

import java.math.BigDecimal;

/**
 * 单位转换构建器
 * 
 * 提供流畅的API用于构建单位转换操作，线程安全
 * 
 * @author 紫水木鱼
 * @version 1.0.0
 * @since 2026-04-25
 */
public class ConversionBuilder {
    
    private final IUnitConversionService service;
    private final BigDecimal value;
    private final UnitEnum fromUnit;
    
    public ConversionBuilder(IUnitConversionService service, BigDecimal value, UnitEnum fromUnit) {
        this.service = service;
        this.value = value;
        this.fromUnit = fromUnit;
    }
    
    /**
     * 转换到指定的目标单位
     * 
     * @param toUnit 目标单位
     * @return 转换结果
     */
    public ConvertResult<BigDecimal> to(UnitEnum toUnit) {
        return service.convert(value, fromUnit, toUnit);
    }
}