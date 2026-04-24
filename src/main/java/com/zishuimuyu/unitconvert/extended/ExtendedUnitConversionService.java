package com.zishuimuyu.unitconvert.extended;

import com.zishuimuyu.unitconvert.builder.ConversionBuilder;
import com.zishuimuyu.unitconvert.config.UnitConversionConfig;
import com.zishuimuyu.unitconvert.model.ConvertResult;
import com.zishuimuyu.unitconvert.model.ToBestOptions;
import com.zishuimuyu.unitconvert.model.UnitDescription;
import com.zishuimuyu.unitconvert.model.UnitEnum;
import com.zishuimuyu.unitconvert.service.IUnitConversionService;
import com.zishuimuyu.unitconvert.service.UnitConversionServiceImpl;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 扩展单位转换服务
 * 
 * 提供额外的高级功能，如批量转换、批量最佳单位选择、单位组转换等
 * 基于装饰器模式扩展基础功能
 * 
 * @author 紫水木鱼
 * @version 1.0.0
 * @since 2026-04-25
 */
public class ExtendedUnitConversionService implements IUnitConversionService {
    
    private final IUnitConversionService delegate;

    public ExtendedUnitConversionService() {
        this(new UnitConversionServiceImpl());
    }
    
    public ExtendedUnitConversionService(UnitConversionConfig config) {
        this(new UnitConversionServiceImpl(config));
    }
    
    public ExtendedUnitConversionService(IUnitConversionService delegate) {
        this.delegate = delegate;
    }

    /**
     * 批量转换
     * 
     * 对多个值进行相同单位转换
     * 
     * @param values 待转换的值列表
     * @param fromUnit 源单位
     * @param toUnit 目标单位
     * @return 转换结果列表
     */
    public List<ConvertResult<BigDecimal>> convertBatch(List<BigDecimal> values, UnitEnum fromUnit, UnitEnum toUnit) {
        return values.stream()
            .map(value -> delegate.convert(value, fromUnit, toUnit))
            .collect(Collectors.toList());
    }
    
    /**
     * 批量最佳单位转换
     * 
     * 对多个值进行最佳单位选择
     * 
     * @param values 待转换的值列表
     * @param fromUnit 源单位
     * @return 转换结果列表
     */
    public List<ConvertResult<BigDecimal>> convertToBestBatch(List<BigDecimal> values, UnitEnum fromUnit) {
        return values.stream()
            .map(value -> delegate.convertToBest(value, fromUnit))
            .collect(Collectors.toList());
    }
    
    /**
     * 批量最佳单位转换（带选项）
     * 
     * 对多个值进行带选项的最佳单位选择
     * 
     * @param values 待转换的值列表
     * @param fromUnit 源单位
     * @param options 转换选项
     * @return 转换结果列表
     */
    public List<ConvertResult<BigDecimal>> convertToBestBatch(List<BigDecimal> values, UnitEnum fromUnit, ToBestOptions options) {
        return values.stream()
            .map(value -> delegate.convertToBest(value, fromUnit, options))
            .collect(Collectors.toList());
    }
    
    /**
     * 单位组转换
     * 
     * 对多个值进行不同的单位转换
     * 
     * @param valueUnitPairs 值-单位对列表
     * @param toUnit 目标单位
     * @return 转换结果列表
     */
    public List<ConvertResult<BigDecimal>> convertGroup(List<ValueUnitPair> valueUnitPairs, UnitEnum toUnit) {
        return valueUnitPairs.stream()
            .map(pair -> delegate.convert(pair.getValue(), pair.getFromUnit(), toUnit))
            .collect(Collectors.toList());
    }
    
    /**
     * 获取单位转换路径
     * 
     * 查找从一个单位到另一个单位的转换路径（如果有间接转换路径）
     * 
     * @param fromUnit 源单位
     * @param toUnit 目标单位
     * @return 转换路径，如果无法转换则返回null
     */
    public List<UnitEnum> getConversionPath(UnitEnum fromUnit, UnitEnum toUnit) {
        if (delegate.canConvertBetween(fromUnit, toUnit)) {
            return java.util.Arrays.asList(fromUnit, toUnit);
        }
        // 这里可以实现更复杂的路径查找算法
        return null;
    }
    
    /**
     * 转换单位对
     */
    public static class ValueUnitPair {
        private final BigDecimal value;
        private final UnitEnum fromUnit;
        
        public ValueUnitPair(BigDecimal value, UnitEnum fromUnit) {
            this.value = value;
            this.fromUnit = fromUnit;
        }
        
        public BigDecimal getValue() {
            return value;
        }
        
        public UnitEnum getFromUnit() {
            return fromUnit;
        }
    }

    @Override
    public ConvertResult<BigDecimal> convert(BigDecimal value, UnitEnum fromUnit, UnitEnum toUnit) {
        return delegate.convert(value, fromUnit, toUnit);
    }

    @Override
    public ConvertResult<BigDecimal> convertToBest(BigDecimal value, UnitEnum fromUnit) {
        return delegate.convertToBest(value, fromUnit);
    }

    @Override
    public ConvertResult<BigDecimal> convertToBest(BigDecimal value, UnitEnum fromUnit, ToBestOptions options) {
        return delegate.convertToBest(value, fromUnit, options);
    }

    @Override
    public boolean canConvertBetween(UnitEnum fromUnit, UnitEnum toUnit) {
        return delegate.canConvertBetween(fromUnit, toUnit);
    }

    @Override
    public List<String> getSupportedMeasures() {
        return delegate.getSupportedMeasures();
    }

    @Override
    public List<UnitEnum> getPossibleUnits(String measure) {
        return delegate.getPossibleUnits(measure);
    }

    @Override
    public List<UnitDescription> listAllUnits() {
        return delegate.listAllUnits();
    }

    @Override
    public List<UnitDescription> listUnitsByMeasure(String measure) {
        return delegate.listUnitsByMeasure(measure);
    }

    @Override
    public UnitDescription describeUnit(UnitEnum unit) {
        return delegate.describeUnit(unit);
    }

    @Override
    public IUnitConversionService from(BigDecimal value, UnitEnum fromUnit) {
        return delegate.from(value, fromUnit);
    }

    @Override
    public List<UnitEnum> possibilities(String measure) {
        return delegate.possibilities(measure);
    }

    @Override
    public List<String> measures() {
        return delegate.measures();
    }

    @Override
    public UnitDescription lookup(String unitName) {
        return delegate.lookup(unitName);
    }

    @Override
    public ConversionBuilder buildConversion(BigDecimal value, UnitEnum fromUnit) {
        return delegate.buildConversion(value, fromUnit);
    }
}