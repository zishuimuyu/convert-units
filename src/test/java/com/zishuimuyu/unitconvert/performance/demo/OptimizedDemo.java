package com.zishuimuyu.unitconvert.performance.demo;

import com.zishuimuyu.unitconvert.concurrent.ConcurrentUnitConversionService;
import com.zishuimuyu.unitconvert.extended.ExtendedUnitConversionService;
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
    }

    /**
     * 演示高性能转换
     */
    private static void demonstrateHighPerformanceConversion() {
        System.out.println("--- 高性能转换演示 ---");
        UnitConversionServiceImpl service = new UnitConversionServiceImpl();

        // 预热
        for (int i = 0; i < 1000; i++) {
            service.convert(BigDecimal.ONE, UnitEnum.M, UnitEnum.CM);
        }

        // 性能测试
        int iterations = 10000;
        long startTime = System.nanoTime();
        for (int i = 0; i < iterations; i++) {
            service.convert(BigDecimal.ONE, UnitEnum.M, UnitEnum.CM);
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
            futures[i] = service.convertAsync(BigDecimal.valueOf(i + 1), UnitEnum.M, UnitEnum.CM)
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

        List<com.zishuimuyu.unitconvert.model.ConvertResult<BigDecimal>> results = 
            service.convertBatch(values, UnitEnum.KM, UnitEnum.M);

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
            new ExtendedUnitConversionService.ValueUnitPair(BigDecimal.valueOf(1000), UnitEnum.MM),
            new ExtendedUnitConversionService.ValueUnitPair(BigDecimal.valueOf(1), UnitEnum.M),
            new ExtendedUnitConversionService.ValueUnitPair(BigDecimal.valueOf(0.001), UnitEnum.KM)
        );

        List<com.zishuimuyu.unitconvert.model.ConvertResult<BigDecimal>> results = 
            service.convertGroup(pairs, UnitEnum.CM);

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
}