package com.zishuimuyu.unitconvert.concurrent;

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
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.ForkJoinPool;

/**
 * 并发单位转换服务
 * 
 * 提供异步转换功能，支持高并发场景下的单位转换需求
 * 通过CompletableFuture实现非阻塞的异步操作
 * 
 * 设计特点：
 * - 线程安全：无共享可变状态
 * - 高性能：利用ForkJoinPool进行任务调度
 * - 异步操作：支持异步转换操作，提高响应性
 * 
 * @author 紫水木鱼
 * @version 1.0.0
 * @since 2026-04-25
 */
public class ConcurrentUnitConversionService implements IUnitConversionService {
    
    /**
     * 底层单位转换服务实现
     */
    private final IUnitConversionService delegate;
    
    /**
     * 异步执行器
     */
    private final Executor executor;

    /**
     * 默认构造函数，使用默认配置和公共ForkJoinPool
     */
    public ConcurrentUnitConversionService() {
        this(new UnitConversionServiceImpl(), ForkJoinPool.commonPool());
    }
    
    /**
     * 带配置的构造函数
     * 
     * @param config 单位转换配置
     */
    public ConcurrentUnitConversionService(UnitConversionConfig config) {
        this(new UnitConversionServiceImpl(config), ForkJoinPool.commonPool());
    }
    
    /**
     * 完整构造函数
     * 
     * @param delegate 底层转换服务
     * @param executor 异步执行器
     */
    public ConcurrentUnitConversionService(IUnitConversionService delegate, Executor executor) {
        this.delegate = delegate;
        this.executor = executor != null ? executor : ForkJoinPool.commonPool();
    }

    /**
     * 异步执行单位转换
     * 
     * @param value 待转换的值
     * @param fromUnit 源单位
     * @param toUnit 目标单位
     * @return CompletableFuture包装的转换结果
     */
    public CompletableFuture<ConvertResult<BigDecimal>> convertAsync(BigDecimal value, UnitEnum fromUnit, UnitEnum toUnit) {
        return CompletableFuture.supplyAsync(() -> delegate.convert(value, fromUnit, toUnit), executor);
    }
    
    /**
     * 异步执行最佳单位转换
     * 
     * @param value 待转换的值
     * @param fromUnit 源单位
     * @return CompletableFuture包装的转换结果
     */
    public CompletableFuture<ConvertResult<BigDecimal>> convertToBestAsync(BigDecimal value, UnitEnum fromUnit) {
        return CompletableFuture.supplyAsync(() -> delegate.convertToBest(value, fromUnit), executor);
    }
    
    /**
     * 异步执行带选项的最佳单位转换
     * 
     * @param value 待转换的值
     * @param fromUnit 源单位
     * @param options 转换选项
     * @return CompletableFuture包装的转换结果
     */
    public CompletableFuture<ConvertResult<BigDecimal>> convertToBestAsync(BigDecimal value, UnitEnum fromUnit, ToBestOptions options) {
        return CompletableFuture.supplyAsync(() -> delegate.convertToBest(value, fromUnit, options), executor);
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