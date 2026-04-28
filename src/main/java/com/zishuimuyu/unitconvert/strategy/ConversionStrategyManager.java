package com.zishuimuyu.unitconvert.strategy;

import com.zishuimuyu.unitconvert.exception.UnitConversionException;
import com.zishuimuyu.unitconvert.model.ConvertResult;
import com.zishuimuyu.unitconvert.model.UnitEnum;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * 转换策略管理器
 * <P>
 * 管理多个转换策略，并按优先级顺序尝试执行转换：
 * <P>
 * 功能列表：
 * <ul>
 *   <li>管理多个转换策略，并按优先级顺序尝试执行转换</li>
 *   <li>维护一个按优先级排序的策略列表</li>
 *   <li>当执行转换时，按优先级从高到低依次尝试每个策略</li>
 *   <li>找到第一个支持该转换的策略后立即执行并返回结果</li>
 *   <li>如果所有策略都不支持该转换，则抛出异常</li>
 *   <li>支持动态添加新的转换策略</li>
 *   <li>自动按优先级排序策略</li>
 * </ul>
 * <P>
 * 设计原理：
 * <ol>
 *   <li>使用策略模式管理不同的转换策略</li>
 *   <li>通过优先级机制控制策略选择顺序</li>
 *   <li>按优先级从高到低依次尝试每个策略</li>
 *   <li>找到第一个支持转换的策略后立即执行并返回结果</li>
 * </ol>
 * <P>
 * 优势：
 * <ul>
 *   <li>支持动态添加新的转换策略</li>
 *   <li>自动按优先级排序策略</li>
 *   <li>提供灵活的转换机制</li>
 *   <li>易于扩展：可轻松添加新的转换策略</li>
 *   <li>高效的策略选择：优先级高的策略优先被尝试</li>
 * </ul>
 * <P>
 * 使用场景：
 * <ol>
 *   <li>需要管理多种转换策略的场景</li>
 *   <li>需要按优先级选择转换策略的场景</li>
 *   <li>需要动态添加转换策略的场景</li>
 * </ol>
 * <P>
 * 使用示例：
 * <pre>
 * // 创建策略列表
 * List<ConversionStrategy> strategies = new ArrayList<>();
 * strategies.add(new TemperatureConversionStrategy(config));
 * strategies.add(new StandardConversionStrategy(config));
 * 
 * // 创建策略管理器
 * ConversionStrategyManager manager = new ConversionStrategyManager(strategies);
 * 
 * // 执行转换
 * ConvertResult&lt;BigDecimal&gt; result = manager.convert(value, fromUnit, toUnit);
 * 
 * // 动态添加新策略
 * manager.addStrategy(new CustomConversionStrategy());
 * </pre>
 * <P>
 * 
 * @author 紫水木鱼
 * @version 1.0.0
 * @since 2026-04-25
 */
public class ConversionStrategyManager {
    
    /**
     * 转换策略列表
     * <P>
     * 详细描述字段的用途、取值范围或特殊含义
     * <P>
     * 用途：存储所有注册的转换策略，按优先级从高到低排序
     * <P>
     * 取值范围：ConversionStrategy接口的实现类列表
     * <P>
     * 特殊含义：按优先级从高到低排序的策略列表，用于策略选择和执行转换操作
     */
    private final List<ConversionStrategy> strategies;

    /**
     * 创建转换策略管理器
     * <P>
     * 详细描述方法的功能、算法逻辑或业务流程
     * <P>
     * 处理逻辑：
     * <ol>
     *   <li>接收转换策略列表参数</li>
     *   <li>复制策略列表到内部字段</li>
     *   <li>按优先级从高到低对策略进行排序</li>
     * </ol>
     * <P>
     * 参数说明：
     * <ul>
     *   <li>strategies: 转换策略列表，会自动按优先级排序</li>
     * </ul>
     * <P>
     * 返回值说明：
     * <ul>
     *   <li>void: 无返回值，仅初始化对象</li>
     * </ul>
     * <P>
     * 异常情况：
     * <ul>
     *   <li>当strategies为null时，可能导致NullPointerException</li>
     * </ul>
     * 
     * @param strategies 转换策略列表，会自动按优先级排序
     */
    public ConversionStrategyManager(List<ConversionStrategy> strategies) {
        // 按优先级排序策略，优先级高的排在前面
        this.strategies = new ArrayList<>(strategies);
        this.strategies.sort(Comparator.comparingInt(ConversionStrategy::getPriority).reversed());
    }

    /**
     * 添加新的转换策略
     * <P>
     * 详细描述方法的功能、算法逻辑或业务流程
     * <P>
     * 处理逻辑：
     * <ol>
     *   <li>将新的转换策略添加到策略列表</li>
     *   <li>重新按优先级对所有策略进行排序</li>
     * </ol>
     * <P>
     * 参数说明：
     * <ul>
     *   <li>strategy: 要添加的转换策略</li>
     * </ul>
     * <P>
     * 返回值说明：
     * <ul>
     *   <li>void: 无返回值</li>
     * </ul>
     * <P>
     * 异常情况：
     * <ul>
     *   <li>当strategy为null时，可能导致NullPointerException</li>
     * </ul>
     * 
     * @param strategy 要添加的转换策略
     */
    public void addStrategy(ConversionStrategy strategy) {
        strategies.add(strategy);
        // 重新按优先级排序
        strategies.sort(Comparator.comparingInt(ConversionStrategy::getPriority).reversed());
    }

    /**
     * 执行单位转换
     * <P>
     * 详细描述方法的功能、算法逻辑或业务流程
     * <P>
     * 处理逻辑：
     * <ol>
     *   <li>按优先级顺序遍历所有转换策略</li>
     *   <li>检查当前策略是否支持指定的单位转换</li>
     *   <li>如果策略支持转换，则执行转换操作并返回结果</li>
     *   <li>如果当前策略不支持转换，则尝试下一个策略</li>
     *   <li>如果所有策略都不支持转换，则抛出UnitConversionException异常</li>
     * </ol>
     * <P>
     * 参数说明：
     * <ul>
     *   <li>value: 待转换的数值</li>
     *   <li>fromUnit: 源单位</li>
     *   <li>toUnit: 目标单位</li>
     * </ul>
     * <P>
     * 返回值说明：
     * <ul>
     *   <li>成功: 返回转换结果对象</li>
     *   <li>失败: 抛出UnitConversionException异常</li>
     * </ul>
     * <P>
     * 异常情况：
     * <ul>
     *   <li>当没有策略支持该转换时，抛出UnitConversionException异常</li>
     * </ul>
     * 
     * @param value 待转换的数值
     * @param fromUnit 源单位
     * @param toUnit 目标单位
     * @return 转换结果
     * @throws UnitConversionException 当没有策略支持该转换时抛出
     */
    public ConvertResult<BigDecimal> convert(BigDecimal value, UnitEnum fromUnit, UnitEnum toUnit) {
        // 按优先级顺序尝试每个策略
        for (ConversionStrategy strategy : strategies) {
            if (strategy.supports(fromUnit, toUnit)) {
                return strategy.convert(value, fromUnit, toUnit);
            }
        }
        
        // 没有策略支持该转换，抛出异常
        throw new UnitConversionException(
            String.format("无法在 %s (%s) 和 %s (%s) 之间进行转换", 
                fromUnit.getAbbr(), fromUnit.getMeasure(),
                toUnit.getAbbr(), toUnit.getMeasure()),
            "CONVERSION_NOT_SUPPORTED"
        );
    }

    /**
     * 检查是否支持指定的单位转换
     * <P>
     * 详细描述方法的功能、算法逻辑或业务流程
     * <P>
     * 处理逻辑：
     * <ol>
     *   <li>遍历所有转换策略</li>
     *   <li>检查是否有任何策略支持指定的单位转换</li>
     *   <li>如果有策略支持，则返回true</li>
     *   <li>如果没有任何策略支持，则返回false</li>
     * </ol>
     * <P>
     * 参数说明：
     * <ul>
     *   <li>fromUnit: 源单位</li>
     *   <li>toUnit: 目标单位</li>
     * </ul>
     * <P>
     * 返回值说明：
     * <ul>
     *   <li>true: 有策略支持该单位转换</li>
     *   <li>false: 没有策略支持该单位转换</li>
     * </ul>
     * <P>
     * 异常情况：
     * <ul>
     *   <li>无异常情况</li>
     * </ul>
     * 
     * @param fromUnit 源单位
     * @param toUnit 目标单位
     * @return 如果有策略支持该转换返回true，否则返回false
     */
    public boolean canConvertBetween(UnitEnum fromUnit, UnitEnum toUnit) {
        return strategies.stream()
            .anyMatch(strategy -> strategy.supports(fromUnit, toUnit));
    }
}