package com.zishuimuyu.unitconvert.builder;

import com.zishuimuyu.unitconvert.model.ConvertResult;
import com.zishuimuyu.unitconvert.model.UnitEnum;
import com.zishuimuyu.unitconvert.service.IUnitConversionService;

import java.math.BigDecimal;

/**
 * 单位转换构建器
 * <P>
 * 提供流畅的API用于构建单位转换操作，线程安全
 * <P>
 * 功能列表：
 * <ul>
 *   <li>提供流畅的API用于构建单位转换操作</li>
 *   <li>支持链式调用语法</li>
 *   <li>封装单位转换的复杂性</li>
 *   <li>线程安全，可在多线程环境中使用</li>
 * </ul>
 * <P>
 * 设计原理：
 * <ol>
 *   <li>采用建造者模式（Builder Pattern）封装转换逻辑</li>
 *   <li>通过不可变的内部状态确保线程安全</li>
 *   <li>提供清晰的API接口</li>
 *   <li>支持流畅的调用风格</li>
 * </ol>
 * <P>
 * 优势：
 * <ul>
 *   <li>提供更清晰的API调用方式</li>
 *   <li>支持链式调用，代码更简洁</li>
 *   <li>线程安全，可以在多线程环境中使用</li>
 *   <li>封装了转换的复杂性</li>
 * </ul>
 * <P>
 * 使用场景：
 * <ol>
 *   <li>需要进行单位转换操作的场景</li>
 *   <li>希望使用流畅API的场景</li>
 *   <li>需要在多线程环境中进行转换的场景</li>
 * </ol>
 * <P>
 * 使用示例：
 * <pre>
 * // 基本用法
 * ConversionBuilder builder = service.buildConversion(new BigDecimal("1"), UnitEnum.M);
 * ConvertResult&lt;BigDecimal&gt; result = builder.to(UnitEnum.CM);
 * 
 * // 链式调用
 * ConvertResult&lt;BigDecimal&gt; result = service.buildConversion(new BigDecimal("100"), UnitEnum.CM)
 *     .to(UnitEnum.M);
 * 
 * // 一次性转换
 * ConvertResult&lt;BigDecimal&gt; result = service.convert(new BigDecimal("1"), UnitEnum.M, UnitEnum.CM);
 * </pre>
 * <P>
 * 
 * @author 紫水木鱼
 * @version 1.0.0
 * @since 2026-04-25
 */
public class ConversionBuilder {
    
    /**
     * 底层单位转换服务
     * <P>
     * 详细描述字段的用途、取值范围或特殊含义
     * <P>
     * 用途：用于执行实际的单位转换操作
     * <P>
     * 取值范围：实现了IUnitConversionService接口的服务实例
     * <P>
     * 特殊含义：通过委托模式将实际转换工作交给底层服务处理
     */
    private final IUnitConversionService service;
    
    /**
     * 源数值
     * <P>
     * 详细描述字段的用途、取值范围或特殊含义
     * <P>
     * 用途：存储待转换的原始数值
     * <P>
     * 取值范围：BigDecimal类型的数值
     * <P>
     * 特殊含义：表示需要进行单位转换的原始数值，作为转换操作的输入
     */
    private final BigDecimal value;
    
    /**
     * 源单位
     * <P>
     * 详细描述字段的用途、取值范围或特殊含义
     * <P>
     * 用途：存储待转换的原始单位
     * <P>
     * 取值范围：UnitEnum枚举值
     * <P>
     * 特殊含义：表示原始数值的单位，作为转换操作的起始单位
     */
    private final UnitEnum fromUnit;
    
    /**
     * 创建转换构建器
     * <P>
     * 详细描述方法的功能、算法逻辑或业务流程
     * <P>
     * 处理逻辑：
     * <ol>
     *   <li>接收单位转换服务、待转换数值和源单位参数</li>
     *   <li>将参数赋值给内部字段</li>
     * </ol>
     * <P>
     * 参数说明：
     * <ul>
     *   <li>service: 单位转换服务实例，用于执行实际转换操作</li>
     *   <li>value: 待转换的数值</li>
     *   <li>fromUnit: 源单位</li>
     * </ul>
     * <P>
     * 返回值说明：
     * <ul>
     *   <li>void: 无返回值，仅初始化对象</li>
     * </ul>
     * <P>
     * 异常情况：
     * <ul>
     *   <li>当参数为null时，可能导致后续操作失败</li>
     * </ul>
     * 
     * @param service 单位转换服务实例
     * @param value 待转换的数值
     * @param fromUnit 源单位
     */
    public ConversionBuilder(IUnitConversionService service, BigDecimal value, UnitEnum fromUnit) {
        this.service = service;
        this.value = value;
        this.fromUnit = fromUnit;
    }
    
    /**
     * 转换到指定的目标单位
     * <P>
     * 详细描述方法的功能、算法逻辑或业务流程
     * <P>
     * 处理逻辑：
     * <ol>
     *   <li>接收目标单位参数</li>
     *   <li>使用底层服务执行从源单位到目标单位的转换操作</li>
     *   <li>返回转换结果</li>
     * </ol>
     * <P>
     * 参数说明：
     * <ul>
     *   <li>toUnit: 目标单位，表示转换后的单位</li>
     * </ul>
     * <P>
     * 返回值说明：
     * <ul>
     *   <li>返回转换结果，包含转换后的数值和单位信息</li>
     * </ul>
     * <P>
     * 异常情况：
     * <ul>
     *   <li>如果转换失败，可能抛出UnitConversionException等异常</li>
     * </ul>
     * 
     * @param toUnit 目标单位
     * @return 转换结果，包含转换后的数值和单位信息
     */
    public ConvertResult<BigDecimal> to(UnitEnum toUnit) {
        return service.convert(value, fromUnit, toUnit);
    }
}