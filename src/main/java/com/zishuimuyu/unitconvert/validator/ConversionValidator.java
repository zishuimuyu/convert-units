package com.zishuimuyu.unitconvert.validator;

import com.zishuimuyu.unitconvert.config.UnitConversionConfig;
import com.zishuimuyu.unitconvert.exception.UnitConversionException;
import com.zishuimuyu.unitconvert.model.UnitEnum;

import java.math.BigDecimal;

/**
 * 单位转换验证器
 * 
 * 提供转换前的参数验证和单位检查功能
 * 
 * @author 紫水木鱼
 * @version 1.0.0
 * @since 2026-04-25
 */
public class ConversionValidator {
    
    private final UnitConversionConfig config;
    
    public ConversionValidator(UnitConversionConfig config) {
        this.config = config != null ? config : UnitConversionConfig.defaults();
    }
    
    /**
     * 验证基本转换参数
     * 
     * @param value 待转换的数值
     * @param fromUnit 源单位
     * @param toUnit 目标单位
     * @throws IllegalArgumentException 当参数为空时抛出
     */
    public void validateBasicParams(BigDecimal value, UnitEnum fromUnit, UnitEnum toUnit) {
        if (value == null) {
            throw new IllegalArgumentException("转换数值不能为空");
        }
        if (fromUnit == null) {
            throw new IllegalArgumentException("源单位不能为空");
        }
        if (toUnit == null) {
            throw new IllegalArgumentException("目标单位不能为空");
        }
    }
    
    /**
     * 验证测量类型是否被加载
     * 
     * @param measure 测量类型
     * @throws UnitConversionException 当测量类型未被加载时抛出
     */
    public void validateMeasureLoaded(String measure) {
        if (!config.shouldLoadMeasure(measure)) {
            throw new UnitConversionException(
                String.format("测量类型 %s 未在配置中包含（部分加载模式）", measure),
                "MEASURE_NOT_LOADED"
            );
        }
    }
    
    /**
     * 验证单位是否被排除
     * 
     * @param unit 要验证的单位
     * @param unitRole 单位角色（源单位或目标单位）
     * @throws UnitConversionException 当单位被排除时抛出
     */
    public void validateUnitNotExcluded(UnitEnum unit, String unitRole) {
        if (config.shouldExcludeUnit(unit)) {
            throw new UnitConversionException(
                String.format("%s %s (%s) 已被配置排除", unitRole, unit.getAbbr(), unit.getSingular()),
                "UNIT_EXCLUDED"
            );
        }
    }
    
    /**
     * 验证数值范围
     * 
     * @param value 要验证的数值
     * @param minValue 最小值（可选）
     * @param maxValue 最大值（可选）
     * @throws IllegalArgumentException 当数值超出范围时抛出
     */
    public void validateValueRange(BigDecimal value, BigDecimal minValue, BigDecimal maxValue) {
        if (minValue != null && value.compareTo(minValue) < 0) {
            throw new IllegalArgumentException(
                String.format("数值 %s 小于最小值 %s", value, minValue)
            );
        }
        if (maxValue != null && value.compareTo(maxValue) > 0) {
            throw new IllegalArgumentException(
                String.format("数值 %s 大于最大值 %s", value, maxValue)
            );
        }
    }
    
    /**
     * 验证测量类型兼容性
     * 
     * @param fromUnit 源单位
     * @param toUnit 目标单位
     * @throws UnitConversionException 当测量类型不兼容时抛出
     */
    public void validateMeasureCompatibility(UnitEnum fromUnit, UnitEnum toUnit) {
        if (!fromUnit.getMeasure().equals(toUnit.getMeasure())) {
            throw new UnitConversionException(
                String.format("无法在不同测量类型之间转换: %s -> %s", 
                    fromUnit.getMeasure(), toUnit.getMeasure()),
                "INCOMPATIBLE_MEASURES"
            );
        }
    }
}