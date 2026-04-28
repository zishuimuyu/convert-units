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
 * <P>
 * 提供异步转换功能，支持高并发场景下的单位转换需求
 * <P>
 * 功能列表：
 * <ul>
 *   <li>提供异步转换功能，支持高并发场景下的单位转换需求</li>
 *   <li>通过CompletableFuture实现非阻塞的异步操作</li>
 *   <li>支持异步的单位转换操作</li>
 *   <li>支持异步的最佳单位转换操作</li>
 *   <li>线程安全的并发处理</li>
 * </ul>
 * <P>
 * 设计原理：
 * <ol>
 *   <li>使用装饰器模式包装基础转换服务</li>
 *   <li>利用CompletableFuture实现异步操作</li>
 *   <li>通过Executor进行任务调度</li>
 *   <li>确保线程安全的并发处理</li>
 * </ol>
 * <P>
 * 优势：
 * <ul>
 *   <li>线程安全：无共享可变状态</li>
 *   <li>高性能：利用ForkJoinPool进行任务调度</li>
 *   <li>异步操作：支持异步转换操作，提高响应性</li>
 *   <li>非阻塞：通过CompletableFuture实现非阻塞操作</li>
 * </ul>
 * <P>
 * 使用场景：
 * <ol>
 *   <li>高并发场景下的单位转换需求</li>
 *   <li>需要异步处理转换任务的场景</li>
 *   <li>对响应时间敏感的应用</li>
 * </ol>
 * <P>
 * 使用示例：
 * <pre>
 * // 创建并发转换服务
 * ConcurrentUnitConversionService concurrentService = new ConcurrentUnitConversionService();
 * 
 * // 异步执行转换
 * CompletableFuture&lt;ConvertResult&lt;BigDecimal&gt;&gt; future = concurrentService
 *     .convertAsync(new BigDecimal("10"), UnitEnum.M, UnitEnum.KM);
 * 
 * // 获取异步结果
 * ConvertResult&lt;BigDecimal&gt; result = future.join();
 * System.out.println("转换结果: " + result.getVal() + " " + result.getAbbr());
 * 
 * // 并行执行多个转换
 * List&lt;CompletableFuture&lt;ConvertResult&lt;BigDecimal&gt;&gt;&gt; futures = Arrays.asList(
 *     concurrentService.convertAsync(new BigDecimal("10"), UnitEnum.M, UnitEnum.KM),
 *     concurrentService.convertAsync(new BigDecimal("5"), UnitEnum.KM, UnitEnum.M),
 *     concurrentService.convertAsync(new BigDecimal("100"), UnitEnum.CM, UnitEnum.M)
 * );
 * 
 * // 等待所有异步操作完成
 * CompletableFuture&lt;Void&gt; allFutures = CompletableFuture.allOf(
 *     futures.toArray(new CompletableFuture[0])
 * );
 * allFutures.join();
 * 
 * // 获取所有结果
 * List&lt;ConvertResult&lt;BigDecimal&gt;&gt; results = futures.stream()
 *     .map(CompletableFuture::join)
 *     .collect(Collectors.toList());
 * </pre>
 * <P>
 * 
 * @author 紫水木鱼
 * @version 1.0.0
 * @since 2026-04-25
 */
public class ConcurrentUnitConversionService implements IUnitConversionService {
    
    /**
     * 底层单位转换服务实现
     * <P>
     * 详细描述字段的用途、取值范围或特殊含义
     * <P>
     * 用途：被装饰的基础转换服务，用于执行实际的转换操作
     * <P>
     * 取值范围：实现了IUnitConversionService接口的服务实例
     * <P>
     * 特殊含义：通过委托模式复用基础服务功能，在其基础上提供异步能力
     */
    private final IUnitConversionService delegate;
    
    /**
     * 异步执行器
     * <P>
     * 详细描述字段的用途、取值范围或特殊含义
     * <P>
     * 用途：用于执行异步转换任务的执行器
     * <P>
     * 取值范围：实现了Executor接口的执行器实例
     * <P>
     * 特殊含义：负责调度和执行异步转换操作，如果未指定则使用公共ForkJoinPool
     */
    private final Executor executor;

    /**
     * 默认构造函数，使用默认配置和公共ForkJoinPool
     * <P>
     * 详细描述方法的功能、算法逻辑或业务流程
     * <P>
     * 处理逻辑：
     * <ol>
     *   <li>创建默认的UnitConversionServiceImpl实例</li>
     *   <li>使用公共ForkJoinPool作为异步执行器</li>
     *   <li>使用这些参数初始化并发服务</li>
     * </ol>
     * <P>
     * 参数说明：
     * <ul>
     *   <li>无参数</li>
     * </ul>
     * <P>
     * 返回值说明：
     * <ul>
     *   <li>void: 无返回值，仅初始化对象</li>
     * </ul>
     * <P>
     * 异常情况：
     * <ul>
     *   <li>无异常情况</li>
     * </ul>
     * 
     */
    public ConcurrentUnitConversionService() {
        this(new UnitConversionServiceImpl(), ForkJoinPool.commonPool());
    }
    
    /**
     * 带配置的构造函数
     * <P>
     * 详细描述方法的功能、算法逻辑或业务流程
     * <P>
     * 处理逻辑：
     * <ol>
     *   <li>接收单位转换配置参数</li>
     *   <li>使用配置创建UnitConversionServiceImpl实例</li>
     *   <li>使用公共ForkJoinPool作为异步执行器</li>
     *   <li>使用这些参数初始化并发服务</li>
     * </ol>
     * <P>
     * 参数说明：
     * <ul>
     *   <li>config: 单位转换配置</li>
     * </ul>
     * <P>
     * 返回值说明：
     * <ul>
     *   <li>void: 无返回值，仅初始化对象</li>
     * </ul>
     * <P>
     * 异常情况：
     * <ul>
     *   <li>当config为null时，可能导致后续操作失败</li>
     * </ul>
     * 
     * @param config 单位转换配置
     */
    public ConcurrentUnitConversionService(UnitConversionConfig config) {
        this(new UnitConversionServiceImpl(config), ForkJoinPool.commonPool());
    }
    
    /**
     * 完整构造函数
     * <P>
     * 详细描述方法的功能、算法逻辑或业务流程
     * <P>
     * 处理逻辑：
     * <ol>
     *   <li>接收底层转换服务和异步执行器参数</li>
     *   <li>将底层转换服务赋值给内部字段</li>
     *   <li>如果异步执行器为null，则使用公共ForkJoinPool</li>
     *   <li>否则使用提供的异步执行器</li>
     * </ol>
     * <P>
     * 参数说明：
     * <ul>
     *   <li>delegate: 底层转换服务，用于执行实际的转换操作</li>
     *   <li>executor: 异步执行器，用于调度异步任务；如果为null则使用公共ForkJoinPool</li>
     * </ul>
     * <P>
     * 返回值说明：
     * <ul>
     *   <li>void: 无返回值，仅初始化对象</li>
     * </ul>
     * <P>
     * 异常情况：
     * <ul>
     *   <li>当delegate为null时，可能导致后续操作失败</li>
     * </ul>
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
     * <P>
     * 详细描述方法的功能、算法逻辑或业务流程
     * <P>
     * 处理逻辑：
     * <ol>
     *   <li>接收待转换的值、源单位和目标单位</li>
     *   <li>使用异步执行器提交转换任务</li>
     *   <li>在异步线程中执行实际的转换操作</li>
     *   <li>返回CompletableFuture包装的转换结果</li>
     * </ol>
     * <P>
     * 参数说明：
     * <ul>
     *   <li>value: 待转换的值</li>
     *   <li>fromUnit: 源单位</li>
     *   <li>toUnit: 目标单位</li>
     * </ul>
     * <P>
     * 返回值说明：
     * <ul>
     *   <li>返回CompletableFuture包装的转换结果</li>
     * </ul>
     * <P>
     * 异常情况：
     * <ul>
     *   <li>如果转换过程中出现错误，CompletableFuture将包含相应的异常</li>
     * </ul>
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
     * <P>
     * 详细描述方法的功能、算法逻辑或业务流程
     * <P>
     * 处理逻辑：
     * <ol>
     *   <li>接收待转换的值和源单位</li>
     *   <li>使用异步执行器提交最佳单位转换任务</li>
     *   <li>在异步线程中执行实际的最佳单位转换操作</li>
     *   <li>返回CompletableFuture包装的转换结果</li>
     * </ol>
     * <P>
     * 参数说明：
     * <ul>
     *   <li>value: 待转换的值</li>
     *   <li>fromUnit: 源单位</li>
     * </ul>
     * <P>
     * 返回值说明：
     * <ul>
     *   <li>返回CompletableFuture包装的转换结果</li>
     * </ul>
     * <P>
     * 异常情况：
     * <ul>
     *   <li>如果转换过程中出现错误，CompletableFuture将包含相应的异常</li>
     * </ul>
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
     * <P>
     * 详细描述方法的功能、算法逻辑或业务流程
     * <P>
     * 处理逻辑：
     * <ol>
     *   <li>接收待转换的值、源单位和转换选项</li>
     *   <li>使用异步执行器提交带选项的最佳单位转换任务</li>
     *   <li>在异步线程中执行实际的带选项最佳单位转换操作</li>
     *   <li>返回CompletableFuture包装的转换结果</li>
     * </ol>
     * <P>
     * 参数说明：
     * <ul>
     *   <li>value: 待转换的值</li>
     *   <li>fromUnit: 源单位</li>
     *   <li>options: 转换选项，用于控制最佳单位选择的行为</li>
     * </ul>
     * <P>
     * 返回值说明：
     * <ul>
     *   <li>返回CompletableFuture包装的转换结果</li>
     * </ul>
     * <P>
     * 异常情况：
     * <ul>
     *   <li>如果转换过程中出现错误，CompletableFuture将包含相应的异常</li>
     * </ul>
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