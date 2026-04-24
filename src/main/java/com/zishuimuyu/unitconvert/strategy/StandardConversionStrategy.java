package com.zishuimuyu.unitconvert.strategy;

import com.zishuimuyu.unitconvert.config.UnitConversionConfig;
import com.zishuimuyu.unitconvert.data.ConversionFactors;
import com.zishuimuyu.unitconvert.data.SystemRatios;
import com.zishuimuyu.unitconvert.exception.UnitConversionException;
import com.zishuimuyu.unitconvert.model.ConvertResult;
import com.zishuimuyu.unitconvert.model.UnitEnum;
import com.zishuimuyu.unitconvert.util.PrecisionCalculator;

import java.math.BigDecimal;

/**
 * 标准单位转换策略
 * 
 * 处理非温度类单位的转换，使用锚点单位进行转换
 * 支持同系统内和跨系统间的单位转换
 * 
 * 设计原理：
 * 1. 首先检查配置和单位有效性
 * 2. 获取源单位和目标单位的转换因子
 * 3. 如果在同一系统内，直接使用转换因子进行计算
 * 4. 如果在不同系统间，通过系统间转换比率进行转换
 * 
 * @author 紫水木鱼
 * @version 1.0.0
 * @since 2026-04-25
 */
public class StandardConversionStrategy implements ConversionStrategy {
    
    /**
     * 单位转换配置
     * 
     * 用于控制转换行为，如部分加载、单位排除等
     */
    private final UnitConversionConfig config;

    /**
     * 构造函数
     * 
     * @param config 单位转换配置
     */
    public StandardConversionStrategy(UnitConversionConfig config) {
        this.config = config;
    }

    @Override
    public ConvertResult<BigDecimal> convert(BigDecimal value, UnitEnum fromUnit, UnitEnum toUnit) {
        // 检查测量类型是否应该被加载（部分加载模式）
        String measure = fromUnit.getMeasure();
        if (!config.shouldLoadMeasure(measure)) {
            throw new UnitConversionException(
                String.format("测量类型 %s 未在配置中包含（部分加载模式）", measure),
                "MEASURE_NOT_LOADED"
            );
        }

        // 检查单位是否被排除
        if (config.shouldExcludeUnit(fromUnit)) {
            throw new UnitConversionException(
                String.format("源单位 %s (%s) 已被配置排除", fromUnit.getAbbr(), fromUnit.getSingular()),
                "UNIT_EXCLUDED"
            );
        }

        if (config.shouldExcludeUnit(toUnit)) {
            throw new UnitConversionException(
                String.format("目标单位 %s (%s) 已被配置排除", toUnit.getAbbr(), toUnit.getSingular()),
                "UNIT_EXCLUDED"
            );
        }

        BigDecimal fromFactor = ConversionFactors.getFactor(fromUnit);
        BigDecimal toFactor = ConversionFactors.getFactor(toUnit);

        if (fromFactor == null || toFactor == null) {
            throw new UnitConversionException("未知的单位", "UNKNOWN_UNIT");
        }

        // 检查是否需要系统间转换
        BigDecimal result;
        if (fromUnit.getSystem().equals(toUnit.getSystem())) {
            // 同一系统内的转换
            result = PrecisionCalculator.divide(
                PrecisionCalculator.multiply(value, fromFactor), 
                toFactor
            );
        } else {
            // 系统间转换
            result = performCrossSystemConversion(value, fromUnit, toUnit);
        }

        return new ConvertResult<>(result, toUnit.getAbbr(), toUnit.getSingular(), toUnit.getPlural());
    }

    @Override
    public boolean supports(UnitEnum fromUnit, UnitEnum toUnit) {
        // 不支持温度转换（由专门的策略处理）
        return !"temperature".equals(fromUnit.getMeasure()) && 
               fromUnit.getMeasure().equals(toUnit.getMeasure());
    }

    /**
     * 执行跨系统转换
     * 
     * 使用系统间转换比率处理不同系统间的单位转换
     * 
     * @param value 待转换的值
     * @param fromUnit 源单位
     * @param toUnit 目标单位
     * @return 转换后的值
     */
    private BigDecimal performCrossSystemConversion(BigDecimal value, UnitEnum fromUnit, UnitEnum toUnit) {
        String measureType = fromUnit.getMeasure();
        
        // 获取系统间转换比率
        String ratioKey = fromUnit.getSystem() + "_to_" + toUnit.getSystem();
        BigDecimal systemRatio = SystemRatios.getRatio(measureType, ratioKey);
        
        if (systemRatio == null) {
            throw new UnitConversionException("未定义的系统间转换比率: " + ratioKey, "UNDEFINED_CONVERSION_RATIO");
        }
        
        // 转换步骤：
        // 1. 将源值转换为源系统的锚点单位
        BigDecimal fromFactor = ConversionFactors.getFactor(fromUnit);
        BigDecimal anchorValue = PrecisionCalculator.multiply(value, fromFactor);
        
        // 2. 通过系统间比率转换到目标系统的锚点单位
        BigDecimal convertedAnchorValue = PrecisionCalculator.multiply(anchorValue, systemRatio);
        
        // 3. 从目标系统的锚点单位转换到目标单位
        BigDecimal toFactor = ConversionFactors.getFactor(toUnit);
        return PrecisionCalculator.divide(convertedAnchorValue, toFactor);
    }
}