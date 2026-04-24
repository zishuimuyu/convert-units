package com.zishuimuyu.unitconvert.chain;

import com.zishuimuyu.unitconvert.builder.ConversionBuilder;
import com.zishuimuyu.unitconvert.model.ConvertResult;
import com.zishuimuyu.unitconvert.model.ToBestOptions;
import com.zishuimuyu.unitconvert.model.UnitDescription;
import com.zishuimuyu.unitconvert.model.UnitEnum;
import com.zishuimuyu.unitconvert.service.IUnitConversionService;

import java.math.BigDecimal;
import java.util.List;

/**
 * 线程安全的链式转换上下文
 * 
 * 解决了原实现中链式调用的线程安全问题
 * 每次链式调用都创建独立的上下文对象
 * 
 * @author 紫水木鱼
 * @version 1.0.0
 * @since 2026-04-25
 */
public class ChainConversionContext implements IUnitConversionService {
    
    private final IUnitConversionService service;
    private final BigDecimal value;
    private final UnitEnum fromUnit;
    
    public ChainConversionContext(IUnitConversionService service, BigDecimal value, UnitEnum fromUnit) {
        if (value == null || fromUnit == null) {
            throw new IllegalArgumentException("源数值和源单位不能为空");
        }
        this.service = service;
        this.value = value;
        this.fromUnit = fromUnit;
    }
    
    /**
     * 执行单位转换，将已设置的源单位转换为目标单位
     * 
     * @param toUnit 目标单位
     * @return 转换结果
     */
    public ConvertResult<BigDecimal> to(UnitEnum toUnit) {
        if (toUnit == null) {
            throw new IllegalArgumentException("目标单位不能为空");
        }
        return service.convert(value, fromUnit, toUnit);
    }
    
    /**
     * 转换为最适合的单位
     * 
     * @return 最佳单位转换结果
     */
    public ConvertResult<BigDecimal> toBest() {
        return service.convertToBest(value, fromUnit);
    }
    
    @Override
    public ConvertResult<BigDecimal> convert(BigDecimal value, UnitEnum fromUnit, UnitEnum toUnit) {
        return service.convert(value, fromUnit, toUnit);
    }
    
    @Override
    public ConvertResult<BigDecimal> convertToBest(BigDecimal value, UnitEnum fromUnit) {
        return service.convertToBest(value, fromUnit);
    }
    
    @Override
    public ConvertResult<BigDecimal> convertToBest(BigDecimal value, UnitEnum fromUnit, ToBestOptions options) {
        return service.convertToBest(value, fromUnit, options);
    }
    
    @Override
    public List<String> getSupportedMeasures() {
        return service.getSupportedMeasures();
    }
    
    @Override
    public List<UnitEnum> getPossibleUnits(String measure) {
        return service.getPossibleUnits(measure);
    }
    
    @Override
    public List<UnitDescription> listAllUnits() {
        return service.listAllUnits();
    }
    
    @Override
    public List<UnitDescription> listUnitsByMeasure(String measure) {
        return service.listUnitsByMeasure(measure);
    }
    
    @Override
    public IUnitConversionService from(BigDecimal value, UnitEnum fromUnit) {
        return new ChainConversionContext(service, value, fromUnit);
    }
    
    @Override
    public List<UnitEnum> possibilities(String measure) {
        return service.possibilities(measure);
    }
    
    @Override
    public boolean canConvertBetween(UnitEnum fromUnit, UnitEnum toUnit) {
        return service.canConvertBetween(fromUnit, toUnit);
    }
    
    @Override
    public UnitDescription lookup(String unitName) {
        return service.lookup(unitName);
    }
    
    @Override
    public ConversionBuilder buildConversion(BigDecimal value, UnitEnum fromUnit) {
        return service.buildConversion(value, fromUnit);
    }
    
    @Override
    public List<String> measures() {
        return service.measures();
    }
    
    @Override
    public UnitDescription describeUnit(UnitEnum unit) {
        return service.describeUnit(unit);
    }
}