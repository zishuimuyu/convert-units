package com.zishuimuyu.unitconvert.performance;

import com.zishuimuyu.unitconvert.model.ConvertResult;
import com.zishuimuyu.unitconvert.model.UnitEnum;
import com.zishuimuyu.unitconvert.service.UnitConversionServiceImpl;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 性能测试类
 * 
 * 用于评估单位转换库的性能表现，包括：
 * - 单线程性能测试
 * - 多线程并发性能测试
 * - 内存使用效率测试
 * - 不同配置下的性能对比
 * 
 * @author 紫水木鱼
 * @version 1.0.0
 * @since 2026-04-25
 */
public class PerformanceTest {

    @Test
    public void singleThreadPerformanceTest() {
        System.out.println("\n=== 单线程性能测试 ===");
        
        UnitConversionServiceImpl service = new UnitConversionServiceImpl();
        
        // 预热
        for (int i = 0; i < 1000; i++) {
            service.convert(BigDecimal.ONE, UnitEnum.M, UnitEnum.CM);
        }
        
        // 性能测试
        int iterations = 100000;
        long startTime = System.nanoTime();
        
        for (int i = 0; i < iterations; i++) {
            ConvertResult<BigDecimal> result = service.convert(BigDecimal.ONE, UnitEnum.M, UnitEnum.CM);
            if (i % 10000 == 0) {
                System.out.println("已完成 " + i + " 次转换");
            }
        }
        
        long endTime = System.nanoTime();
        double totalTimeMs = (endTime - startTime) / 1_000_000.0;
        double avgTimePerConversion = totalTimeMs / iterations * 1000; // microseconds
        
        System.out.printf("总转换次数: %d%n", iterations);
        System.out.printf("总耗时: %.2f ms%n", totalTimeMs);
        System.out.printf("平均每次转换耗时: %.2f μs%n", avgTimePerConversion);
        System.out.printf("每秒转换次数: %.0f%n", iterations / (totalTimeMs / 1000));
    }
    
    @Test
    public void concurrentPerformanceTest() throws InterruptedException {
        System.out.println("\n=== 并发性能测试 ===");
        
        int numThreads = Runtime.getRuntime().availableProcessors();
        int iterationsPerThread = 10000;
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);
        CountDownLatch latch = new CountDownLatch(numThreads);
        
        // 创建多个服务实例以避免并发问题
        UnitConversionServiceImpl[] services = new UnitConversionServiceImpl[numThreads];
        for (int i = 0; i < numThreads; i++) {
            services[i] = new UnitConversionServiceImpl();
        }
        
        long startTime = System.currentTimeMillis();
        
        for (int t = 0; t < numThreads; t++) {
            final int threadIndex = t;
            executor.submit(() -> {
                try {
                    UnitConversionServiceImpl service = services[threadIndex];
                    
                    for (int i = 0; i < iterationsPerThread; i++) {
                        service.convert(BigDecimal.valueOf(i % 1000 + 1), UnitEnum.M, UnitEnum.CM);
                    }
                } finally {
                    latch.countDown();
                }
            });
        }
        
        latch.await();
        long endTime = System.currentTimeMillis();
        
        int totalIterations = numThreads * iterationsPerThread;
        double totalTimeMs = endTime - startTime;
        
        System.out.printf("线程数: %d%n", numThreads);
        System.out.printf("每线程迭代次数: %d%n", iterationsPerThread);
        System.out.printf("总转换次数: %d%n", totalIterations);
        System.out.printf("总耗时: %.2f ms%n", totalTimeMs);
        System.out.printf("吞吐量: %.0f 转换/秒%n", totalIterations / (totalTimeMs / 1000));
        
        executor.shutdown();
        executor.awaitTermination(10, TimeUnit.SECONDS);
    }
}