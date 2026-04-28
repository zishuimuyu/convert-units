package com.zishuimuyu.unitconvert.strategy;

import com.zishuimuyu.unitconvert.model.ConvertResult;
import com.zishuimuyu.unitconvert.model.UnitEnum;

import java.math.BigDecimal;

/**
 * 转换策略接口
 * <P>
 * 定义了单位转换的基本操作规范，支持不同类型的转换策略
 * <P>
 * 功能列表：
 * <ul>
 *   <li>定义单位转换的基本操作规范</li>
 *   <li>支持不同类型（如标准、温度等）的转换策略</li>
 *   <li>提供策略优先级机制</li>
 *   <li>支持策略的选择和执行</li>
 * </ul>
 * <P>
 * 设计原理：
 * <ol>
 *   <li>使用策略模式分离不同类型的转换逻辑</li>
 *   <li>通过supports方法确定策略适用性</li>
 *   <li>通过优先级机制控制策略选择顺序</li>
 *   <li>统一的转换接口便于扩展和维护</li>
 * </ol>
 * <P>
 * 优势：
 * <ul>
 *   <li>减少耦合：各转换策略独立实现</li>
 *   <li>易于扩展：可轻松添加新的转换策略</li>
 *   <li>灵活选择：根据单位类型选择合适策略</li>
 * </ul>
 * <P>
 * 使用场景：
 * <ol>
 *   <li>需要不同转换算法的单位转换场景</li>
 *   <li>需要特殊处理逻辑的单位转换（如温度）</li>
 *   <li>支持多种转换方式的系统</li>
 * </ol>
 * <P>
 * 使用示例：
 * <pre>
 * // 实现自定义转换策略
 * public class CustomConversionStrategy implements ConversionStrategy {
 *     {@literal @}Override
 *     public ConvertResult&lt;BigDecimal&gt; convert(BigDecimal value, UnitEnum fromUnit, UnitEnum toUnit) {
 *         // 自定义转换逻辑
 *         return new ConvertResult&lt;&gt;(value, toUnit.getAbbr(), toUnit.getSingular(), toUnit.getPlural());
 *     }
 *     
 *     {@literal @}Override
 *     public boolean supports(UnitEnum fromUnit, UnitEnum toUnit) {
 *         // 自定义支持条件
 *         return true;
 *     }
 * }
 * </pre>
 * <P>
 * 
 * @author 紫水木鱼
 * @version 1.0.0
 * @since 2026-04-25
 */
public interface ConversionStrategy {
    
    /**
     * 执行单位转换
     * <P>
     * 详细描述方法的功能、算法逻辑或业务流程
     * <P>
     * 处理逻辑：
     * <ol>
     *   <li>接收待转换的数值和源/目标单位</li>
     *   <li>根据具体的转换策略执行转换计算</li>
     *   <li>返回包含转换结果和单位信息的对象</li>
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
     *   <li>返回包含转换后数值和目标单位信息的ConvertResult对象</li>
     * </ul>
     * <P>
     * 异常情况：
     * <ul>
     *   <li>具体实现可能抛出UnitConversionException等异常</li>
     * </ul>
     * 
     * @param value 待转换的数值
     * @param fromUnit 源单位
     * @param toUnit 目标单位
     * @return 转换结果对象
     */
    ConvertResult<BigDecimal> convert(BigDecimal value, UnitEnum fromUnit, UnitEnum toUnit);
    
    /**
     * 检查是否支持给定的单位转换
     * <P>
     * 详细描述方法的功能、算法逻辑或业务流程
     * <P>
     * 处理逻辑：
     * <ol>
     *   <li>接收源单位和目标单位</li>
     *   <li>根据具体的策略实现判断是否支持这种转换</li>
     *   <li>返回支持状态</li>
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
     *   <li>true: 策略支持从源单位到目标单位的转换</li>
     *   <li>false: 策略不支持从源单位到目标单位的转换</li>
     * </ul>
     * <P>
     * 异常情况：
     * <ul>
     *   <li>一般不会抛出异常</li>
     * </ul>
     * 
     * @param fromUnit 源单位
     * @param toUnit 目标单位
     * @return 如果支持转换返回true
     */
    boolean supports(UnitEnum fromUnit, UnitEnum toUnit);
    
    /**
     * 获取策略优先级
     * <P>
     * 详细描述方法的功能、算法逻辑或业务流程
     * <P>
     * 处理逻辑：
     * <ol>
     *   <li>返回当前策略的优先级值</li>
     *   <li>优先级高的策略会被优先选择使用</li>
     *   <li>默认实现返回0</li>
     * </ol>
     * <P>
     * 参数说明：
     * <ul>
     *   <li>无参数</li>
     * </ul>
     * <P>
     * 返回值说明：
     * <ul>
     *   <li>返回策略的优先级值，值越大优先级越高</li>
     * </ul>
     * <P>
     * 异常情况：
     * <ul>
     *   <li>不会抛出异常</li>
     * </ul>
     * 
     * @return 优先级值，值越大优先级越高
     */
    default int getPriority() {
        return 0;
    }
}