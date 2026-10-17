package com.zishuimuyu.unitconvert.performance.demo;

import com.zishuimuyu.unitconvert.builder.ConversionBuilder;
import com.zishuimuyu.unitconvert.chain.ChainConversionContext;
import com.zishuimuyu.unitconvert.concurrent.ConcurrentUnitConversionService;
import com.zishuimuyu.unitconvert.extended.ExtendedUnitConversionService;
import com.zishuimuyu.unitconvert.model.ConvertResult;
import com.zishuimuyu.unitconvert.model.UnitEnum;
import com.zishuimuyu.unitconvert.service.UnitConversionServiceImpl;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * 优化演示类
 * 
 * 展示各种优化特性，包括：
 * - 高性能计算
 * - 并发处理
 * - 批量转换
 * - 扩展功能
 * 
 * @author 紫水木鱼
 * @version 1.0.0
 * @since 2026-04-25
 */
public class OptimizedDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        System.out.println("=== 优化演示程序 ===\n");

        // 1. 演示基本高性能转换
        demonstrateHighPerformanceConversion();

        // 2. 演示并发转换
        demonstrateConcurrentConversion();

        // 3. 演示批量转换
        demonstrateBatchConversion();

        // 4. 演示扩展功能
        demonstrateExtendedFeatures();
        // 1. 基本转换演示
        basicConversionDemo();

        // 2. 链式调用演示
        chainedCallDemo();

        // 3. 构建器模式演示
        builderPatternDemo();

        // 4. 温度转换演示
        temperatureConversionDemo();

        // 5. 策略模式演示
        strategyPatternDemo();
    }

    /**
     * 演示高性能转换
     */
    private static void demonstrateHighPerformanceConversion() {
        System.out.println("--- 高性能转换演示 ---");
        UnitConversionServiceImpl service = new UnitConversionServiceImpl();

        // 预热
        for (int i = 0; i < 1000; i++) {
            service.convert(BigDecimal.ONE, UnitEnum.LENGTH_M, UnitEnum.LENGTH_CM);
        }

        // 性能测试
        int iterations = 10000;
        long startTime = System.nanoTime();
        for (int i = 0; i < iterations; i++) {
            service.convert(BigDecimal.ONE, UnitEnum.LENGTH_M, UnitEnum.LENGTH_CM);
        }
        long endTime = System.nanoTime();
        double avgTime = (endTime - startTime) / 1_000_000.0 / iterations;

        System.out.printf("高性能转换: %d次操作, 平均%.2fμs/次\n\n", iterations, avgTime);
    }

    /**
     * 演示并发转换
     */
    private static void demonstrateConcurrentConversion() throws ExecutionException, InterruptedException {
        System.out.println("--- 并发转换演示 ---");
        ConcurrentUnitConversionService service = new ConcurrentUnitConversionService();

        // 异步转换多个值
        CompletableFuture<?>[] futures = new CompletableFuture[5];
        for (int i = 0; i < 5; i++) {
            final int index = i;
            futures[i] = service.convertAsync(BigDecimal.valueOf(i + 1), UnitEnum.LENGTH_M, UnitEnum.LENGTH_CM)
                .thenAccept(result -> System.out.printf("异步转换 %d米 = %s厘米\n", index + 1, result.getFormattedValue()));
        }

        // 等待所有异步操作完成
        CompletableFuture.allOf(futures).join();
        System.out.println();
    }

    /**
     * 演示批量转换
     */
    private static void demonstrateBatchConversion() {
        System.out.println("--- 批量转换演示 ---");
        ExtendedUnitConversionService service = new ExtendedUnitConversionService();

        List<BigDecimal> values = Arrays.asList(
            BigDecimal.valueOf(1),
            BigDecimal.valueOf(2),
            BigDecimal.valueOf(3),
            BigDecimal.valueOf(4),
            BigDecimal.valueOf(5)
        );

        List<ConvertResult<BigDecimal>> results =
            service.convertBatch(values, UnitEnum.LENGTH_KM, UnitEnum.LENGTH_M);

        for (int i = 0; i < results.size(); i++) {
            System.out.printf("%.0f公里 = %s米\n", values.get(i), results.get(i).getFormattedValue());
        }
        System.out.println();
    }

    /**
     * 演示扩展功能
     */
    private static void demonstrateExtendedFeatures() {
        System.out.println("--- 扩展功能演示 ---");
        ExtendedUnitConversionService service = new ExtendedUnitConversionService();

        // 使用扩展功能进行复杂转换
        List<ExtendedUnitConversionService.ValueUnitPair> pairs = Arrays.asList(
            new ExtendedUnitConversionService.ValueUnitPair(BigDecimal.valueOf(1000), UnitEnum.LENGTH_MM),
            new ExtendedUnitConversionService.ValueUnitPair(BigDecimal.valueOf(1), UnitEnum.LENGTH_M),
            new ExtendedUnitConversionService.ValueUnitPair(BigDecimal.valueOf(0.001), UnitEnum.LENGTH_KM)
        );

        List<ConvertResult<BigDecimal>> results =
            service.convertGroup(pairs, UnitEnum.LENGTH_CM);

        for (int i = 0; i < results.size(); i++) {
            ExtendedUnitConversionService.ValueUnitPair pair = pairs.get(i);
            System.out.printf("%.3f%s = %s%s\n", 
                pair.getValue(), 
                pair.getFromUnit().getAbbr(),
                results.get(i).getFormattedValue(),
                results.get(i).getUnit());
        }
        System.out.println();
    }
    /**
     * 基本转换演示
     */
    private static void basicConversionDemo() {
        System.out.println("1. 基本转换演示:");
        ExtendedUnitConversionService service = new ExtendedUnitConversionService();
        ConvertResult<BigDecimal> result = service.convert(new BigDecimal("1"), UnitEnum.LENGTH_M, UnitEnum.LENGTH_CM);
        System.out.println("   1米 = " + result.getFormattedValue() + " " + result.getUnit());

        result = service.convert(new BigDecimal("100"), UnitEnum.LENGTH_CM, UnitEnum.LENGTH_M);
        System.out.println("   100厘米 = " + result.getFormattedValue() + " " + result.getUnit());
        System.out.println();
    }

    /**
     * 链式调用演示
     */
    private static void chainedCallDemo() {
        System.out.println("2. 链式调用演示:");
        ExtendedUnitConversionService service = new ExtendedUnitConversionService();
        ChainConversionContext context =
                new ChainConversionContext(service, new BigDecimal("1"), UnitEnum.LENGTH_KM);
        ConvertResult<BigDecimal> result = context.to(UnitEnum.LENGTH_MI);
        System.out.println("   1千米 = " + result.getFormattedValue() + " " + result.getUnit());
        System.out.println();
    }

    /**
     * 构建器模式演示
     */
    private static void builderPatternDemo() {
        System.out.println("3. 构建器模式演示:");
        ExtendedUnitConversionService service = new ExtendedUnitConversionService();
        ConversionBuilder builder = service.buildConversion(new BigDecimal("1"), UnitEnum.LENGTH_KM);
        ConvertResult<BigDecimal> result = builder.to(UnitEnum.LENGTH_MI); // 英里
        System.out.println("   1千米 = " + result.getFormattedValue() + " " + result.getUnit());
        System.out.println();
    }

    /**
     * 温度转换演示
     */
    private static void temperatureConversionDemo() {
        System.out.println("4. 温度转换演示:");
        ExtendedUnitConversionService service = new ExtendedUnitConversionService();
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
    private static void strategyPatternDemo() {
        System.out.println("5. 策略模式演示:");
        ExtendedUnitConversionService service = new ExtendedUnitConversionService();
        // 演示不同类型的转换使用不同的策略
        ConvertResult<BigDecimal> result = service.convert(new BigDecimal("1"), UnitEnum.LENGTH_M, UnitEnum.LENGTH_FT);
        System.out.println("   1米 = " + result.getFormattedValue() + " 英尺 (使用标准转换策略)");

        result = service.convert(new BigDecimal("100"), UnitEnum.TEMPERATURE_C, UnitEnum.TEMPERATURE_F);
        System.out.println("   100°C = " + result.getFormattedValue() + "°F (使用温度转换策略)");
        System.out.println();
    }
}