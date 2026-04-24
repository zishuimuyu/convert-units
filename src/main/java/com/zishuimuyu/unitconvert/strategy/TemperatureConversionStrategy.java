package com.zishuimuyu.unitconvert.strategy;

import com.zishuimuyu.unitconvert.config.UnitConversionConfig;
import com.zishuimuyu.unitconvert.exception.UnitConversionException;
import com.zishuimuyu.unitconvert.model.ConvertResult;
import com.zishuimuyu.unitconvert.model.UnitEnum;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 温度单位转换策略
 * 
 * 专门处理温度单位的转换，使用特殊的偏移计算公式
 * 
 * @author 紫水木鱼
 * @version 1.0.0
 * @since 2026-04-25
 */
public class TemperatureConversionStrategy implements ConversionStrategy {
    
    private final UnitConversionConfig config;

    public TemperatureConversionStrategy(UnitConversionConfig config) {
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

        if (fromUnit == toUnit) {
            return new ConvertResult<>(value, toUnit.getAbbr(), toUnit.getSingular(), toUnit.getPlural());
        }

        // 从任意温度单位转换到摄氏度（使用统一的中间单位）
        BigDecimal celsiusValue = convertToCelsius(value, fromUnit);

        // 从摄氏度转换到目标温度单位
        BigDecimal result = convertFromCelsius(celsiusValue, toUnit);

        return new ConvertResult<>(result, toUnit.getAbbr(), toUnit.getSingular(), toUnit.getPlural());
    }

    @Override
    public boolean supports(UnitEnum fromUnit, UnitEnum toUnit) {
        return "temperature".equals(fromUnit.getMeasure()) && 
               fromUnit.getMeasure().equals(toUnit.getMeasure());
    }

    /**
     * 将任意温度单位转换为摄氏度
     */
    private BigDecimal convertToCelsius(BigDecimal value, UnitEnum fromUnit) {
        switch (fromUnit) {
            case C:
                // 摄氏度到摄氏度，无需转换
                return value;
            case F:
                // 华氏度转摄氏度: C = (F - 32) × 5/9
                return value.subtract(new BigDecimal("32"))
                    .multiply(new BigDecimal("5"))
                    .divide(new BigDecimal("9"), 10, RoundingMode.HALF_UP);
            case K:
                // 开尔文转摄氏度: C = K - 273.15
                return value.subtract(new BigDecimal("273.15"));
            case R:
                // 兰金温标转摄氏度: 先转为华氏度(R - 459.67)，再转为摄氏度
                BigDecimal fahrenheitValue = value.subtract(new BigDecimal("459.67"));
                return fahrenheitValue.subtract(new BigDecimal("32"))
                    .multiply(new BigDecimal("5"))
                    .divide(new BigDecimal("9"), 10, RoundingMode.HALF_UP);
            default:
                throw new UnitConversionException("未知的温度单位: " + fromUnit.getAbbr(), "UNKNOWN_TEMPERATURE_UNIT");
        }
    }

    /**
     * 从摄氏度转换为任意温度单位
     */
    private BigDecimal convertFromCelsius(BigDecimal celsiusValue, UnitEnum toUnit) {
        switch (toUnit) {
            case C:
                // 摄氏度到摄氏度，无需转换
                return celsiusValue;
            case F:
                // 摄氏度转华氏度: F = C × 9/5 + 32
                return celsiusValue.multiply(new BigDecimal("9"))
                    .divide(new BigDecimal("5"), 10, RoundingMode.HALF_UP)
                    .add(new BigDecimal("32"));
            case K:
                // 摄氏度转开尔文: K = C + 273.15
                return celsiusValue.add(new BigDecimal("273.15"));
            case R:
                // 摄氏度转兰金温标: 先转为华氏度，然后再加上兰金温标的偏移量
                BigDecimal fahrenheitValue = celsiusValue.multiply(new BigDecimal("9"))
                    .divide(new BigDecimal("5"), 10, RoundingMode.HALF_UP)
                    .add(new BigDecimal("32"));
                return fahrenheitValue.add(new BigDecimal("459.67"));
            default:
                throw new UnitConversionException("未知的温度单位: " + toUnit.getAbbr(), "UNKNOWN_TEMPERATURE_UNIT");
        }
    }
}