package com.zishuimuyu.unitconvert.batch;

import com.zishuimuyu.unitconvert.model.ConvertResult;
import com.zishuimuyu.unitconvert.model.UnitEnum;
import com.zishuimuyu.unitconvert.service.IUnitConversionService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * 批量单位转换器
 * 
 * 提供批量单位转换功能，支持顺序执行和并行执行两种模式：
 * - 顺序执行：逐个执行转换操作，适合少量转换或需要严格控制执行顺序的场景
 * - 并行执行：使用线程池并发执行转换操作，适合大量转换操作，可显著提高性能
 * 
 * 使用示例：
 * <pre>
 * // 创建批量转换器
 * BatchConverter converter = new BatchConverter(service, 8);
 * 
 * // 准备转换请求
 * List<ConversionRequest> requests = Arrays.asList(
 *     ConversionRequest.of(new BigDecimal("1"), UnitEnum.M, UnitEnum.CM),
 *     ConversionRequest.of(new BigDecimal("2"), UnitEnum.KM, UnitEnum.M),
 *     ConversionRequest.of(new BigDecimal("3"), UnitEnum.G, UnitEnum.KG)
 * );
 * 
 * // 并行执行批量转换
 * List<ConvertResult<BigDecimal>> results = converter.convertParallel(requests);
 * 
 * // 关闭转换器
 * converter.shutdown();
 * </pre>
 * 
 * @author 紫水木鱼
 * @version 1.0.0
 * @since 2026-04-25
 */
public class BatchConverter {
    
    /**
     * 底层单位转换服务
     * 用于执行实际的单位转换操作
     */
    private final IUnitConversionService service;
    
    /**
     * 线程池执行器
     * 用于并行执行批量转换任务
     */
    private final ExecutorService executorService;
    
    /**
     * 最大线程数
     * 控制并发执行的线程数量上限
     */
    private final int maxThreads;
    
    /**
     * 创建批量转换器，使用默认线程数
     * 
     * 默认线程数为系统可用的处理器核心数
     * 
     * @param service 单位转换服务实例
     */
    public BatchConverter(IUnitConversionService service) {
        this(service, Runtime.getRuntime().availableProcessors());
    }
    
    /**
     * 创建批量转换器，指定最大线程数
     * 
     * @param service 单位转换服务实例
     * @param maxThreads 最大线程数，必须大于0
     */
    public BatchConverter(IUnitConversionService service, int maxThreads) {
        this.service = service;
        this.maxThreads = maxThreads;
        this.executorService = Executors.newFixedThreadPool(maxThreads);
    }
    
    /**
     * 顺序执行批量转换
     * 
     * 按照请求列表的顺序逐个执行转换操作
     * 适用于转换数量较少或需要严格控制执行顺序的场景
     * 
     * @param conversions 转换请求列表
     * @return 转换结果列表，顺序与请求列表一致
     */
    public List<ConvertResult<BigDecimal>> convertSequential(List<ConversionRequest> conversions) {
        List<ConvertResult<BigDecimal>> results = new ArrayList<>(conversions.size());
        
        for (ConversionRequest request : conversions) {
            try {
                ConvertResult<BigDecimal> result = service.convert(
                    request.getValue(), 
                    request.getFromUnit(), 
                    request.getToUnit()
                );
                results.add(result);
            } catch (Exception e) {
                // 转换失败时返回错误结果
                results.add(new ConvertResult<>(null, "ERROR", "error", "errors"));
            }
        }
        
        return results;
    }
    
    /**
     * 并行执行批量转换
     * 
     * 使用线程池并发执行转换操作，可以显著提高大量转换的性能
     * 结果列表的顺序与请求列表一致
     * 
     * @param conversions 转换请求列表
     * @return 转换结果列表，顺序与请求列表一致
     */
    public List<ConvertResult<BigDecimal>> convertParallel(List<ConversionRequest> conversions) {
        List<Future<ConvertResult<BigDecimal>>> futures = new ArrayList<>(conversions.size());
        
        // 提交所有转换任务到线程池
        for (ConversionRequest request : conversions) {
            Future<ConvertResult<BigDecimal>> future = executorService.submit(() -> {
                try {
                    return service.convert(
                        request.getValue(), 
                        request.getFromUnit(), 
                        request.getToUnit()
                    );
                } catch (Exception e) {
                    // 转换失败时返回错误结果
                    return new ConvertResult<>(null, "ERROR", "error", "errors");
                }
            });
            futures.add(future);
        }
        
        // 等待所有任务完成并收集结果
        List<ConvertResult<BigDecimal>> results = new ArrayList<>(conversions.size());
        for (Future<ConvertResult<BigDecimal>> future : futures) {
            try {
                results.add(future.get());
            } catch (InterruptedException | ExecutionException e) {
                // 任务执行异常时返回错误结果
                results.add(new ConvertResult<>(null, "ERROR", "error", "errors"));
            }
        }
        
        return results;
    }
    
    /**
     * 批量转换为最佳单位
     * 
     * 将多个值从同一源单位转换为各自的最佳显示单位
     * 
     * @param values 待转换的数值列表
     * @param fromUnit 源单位
     * @return 最佳单位转换结果列表
     */
    public List<ConvertResult<BigDecimal>> convertToBest(List<BigDecimal> values, UnitEnum fromUnit) {
        List<ConvertResult<BigDecimal>> results = new ArrayList<>(values.size());
        
        for (BigDecimal value : values) {
            try {
                ConvertResult<BigDecimal> result = service.convertToBest(value, fromUnit);
                results.add(result);
            } catch (Exception e) {
                // 转换失败时返回错误结果
                results.add(new ConvertResult<>(null, "ERROR", "error", "errors"));
            }
        }
        
        return results;
    }
    
    /**
     * 关闭批量转换器
     * 
     * 优雅地关闭线程池，等待所有任务完成
     * 如果任务在60秒内未完成，则强制关闭
     */
    public void shutdown() {
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
    
    /**
     * 转换请求类
     * 
     * 封装单个单位转换请求的所有必要信息
     */
    public static class ConversionRequest {
        
        /**
         * 待转换的数值
         */
        private final BigDecimal value;
        
        /**
         * 源单位
         */
        private final UnitEnum fromUnit;
        
        /**
         * 目标单位
         */
        private final UnitEnum toUnit;
        
        /**
         * 创建转换请求
         * 
         * @param value 待转换的数值
         * @param fromUnit 源单位
         * @param toUnit 目标单位
         */
        public ConversionRequest(BigDecimal value, UnitEnum fromUnit, UnitEnum toUnit) {
            this.value = value;
            this.fromUnit = fromUnit;
            this.toUnit = toUnit;
        }
        
        /**
         * 获取待转换的数值
         * 
         * @return 待转换的数值
         */
        public BigDecimal getValue() {
            return value;
        }
        
        /**
         * 获取源单位
         * 
         * @return 源单位
         */
        public UnitEnum getFromUnit() {
            return fromUnit;
        }
        
        /**
         * 获取目标单位
         * 
         * @return 目标单位
         */
        public UnitEnum getToUnit() {
            return toUnit;
        }
        
        /**
         * 创建转换请求的工厂方法
         * 
         * 提供更简洁的创建方式
         * 
         * @param value 待转换的数值
         * @param fromUnit 源单位
         * @param toUnit 目标单位
         * @return 转换请求实例
         */
        public static ConversionRequest of(BigDecimal value, UnitEnum fromUnit, UnitEnum toUnit) {
            return new ConversionRequest(value, fromUnit, toUnit);
        }
    }
}