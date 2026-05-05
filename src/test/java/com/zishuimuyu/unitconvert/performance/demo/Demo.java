package com.zishuimuyu.unitconvert.performance.demo;

import com.zishuimuyu.unitconvert.builder.ConversionBuilder;
import com.zishuimuyu.unitconvert.chain.ChainConversionContext;
import com.zishuimuyu.unitconvert.model.ConvertResult;
import com.zishuimuyu.unitconvert.model.UnitEnum;
import com.zishuimuyu.unitconvert.service.UnitConversionServiceImpl;

import java.math.BigDecimal;

/**
 * 演示程序
 * 
 * 展示单位转换SDK的新功能和改进
 * 
 * @author 紫水木鱼
 * @version 1.0.0
 * @since 2026-04-25
 */
public class Demo {
    
    public static void main(String[] args) {
        UnitConversionServiceImpl service = new UnitConversionServiceImpl();
        
        System.out.println("=== 单位转换SDK演示程序 ===\n");
        
        // 1. 基本转换演示
        basicConversionDemo(service);
        
        // 2. 链式调用演示
        chainedCallDemo(service);
        
        // 3. 构建器模式演示
        builderPatternDemo(service);
        
        // 4. 温度转换演示
        temperatureConversionDemo(service);
        
        // 5. 策略模式演示
        strategyPatternDemo(service);
    }
    
    /**
     * 基本转换演示
     */
    private static void basicConversionDemo(UnitConversionServiceImpl service) {
        System.out.println("1. 基本转换演示:");
        ConvertResult<BigDecimal> result = service.convert(new BigDecimal("1"), UnitEnum.LENGTH_M, UnitEnum.LENGTH_CM);
        System.out.println("   1米 = " + result.getFormattedValue() + " " + result.getUnit());
        
        result = service.convert(new BigDecimal("100"), UnitEnum.LENGTH_CM, UnitEnum.LENGTH_M);
        System.out.println("   100厘米 = " + result.getFormattedValue() + " " + result.getUnit());
        System.out.println();
    }
    
    /**
     * 链式调用演示
     */
    private static void chainedCallDemo(UnitConversionServiceImpl service) {
        System.out.println("2. 链式调用演示:");
        ChainConversionContext context =
            new ChainConversionContext(service, new BigDecimal("1"), UnitEnum.LENGTH_KM);
        ConvertResult<BigDecimal> result = context.to(UnitEnum.LENGTH_MI);
        System.out.println("   1千米 = " + result.getFormattedValue() + " " + result.getUnit());
        System.out.println();
    }
    
    /**
     * 构建器模式演示
     */
    private static void builderPatternDemo(UnitConversionServiceImpl service) {
        System.out.println("3. 构建器模式演示:");
        ConversionBuilder builder = service.buildConversion(new BigDecimal("1"), UnitEnum.LENGTH_KM);
        ConvertResult<BigDecimal> result = builder.to(UnitEnum.LENGTH_MI); // 英里
        System.out.println("   1千米 = " + result.getFormattedValue() + " " + result.getUnit());
        System.out.println();
    }
    
    /**
     * 温度转换演示
     */
    private static void temperatureConversionDemo(UnitConversionServiceImpl service) {
        System.out.println("4. 温度转换演示:");
        ConvertResult<BigDecimal> result = service.convert(new BigDecimal("0"), UnitEnum.TEMPERATURE_C, UnitEnum.TEMPERATURE_F);
        System.out.println("   0°C = " + result.getFormattedValue() + "°F");
        
        result = service.convert(new BigDecimal("32"), UnitEnum.TEMPERATURE_F, UnitEnum.TEMPERATURE_C);
        System.out.println("   32°F = " + result.getFormattedValue() + "°C");
        
        result = service.convert(new BigDecimal("0"), UnitEnum.TEMPERATURE_C, UnitEnum.TEMPERATURE_K);
        System.out.println("   0°C = " + result.getFormattedValue() + "K");
        System.out.println();
    }
    
    /**
     * 策略模式演示
     */
    private static void strategyPatternDemo(UnitConversionServiceImpl service) {
        System.out.println("5. 策略模式演示:");
        // 演示不同类型的转换使用不同的策略
        ConvertResult<BigDecimal> result = service.convert(new BigDecimal("1"), UnitEnum.LENGTH_M, UnitEnum.LENGTH_FT);
        System.out.println("   1米 = " + result.getFormattedValue() + " 英尺 (使用标准转换策略)");
        
        result = service.convert(new BigDecimal("100"), UnitEnum.TEMPERATURE_C, UnitEnum.TEMPERATURE_F);
        System.out.println("   100°C = " + result.getFormattedValue() + "°F (使用温度转换策略)");
        System.out.println();
    }
}