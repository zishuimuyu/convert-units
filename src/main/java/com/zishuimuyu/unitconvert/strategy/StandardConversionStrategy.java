package com.zishuimuyu.unitconvert.strategy;

import com.zishuimuyu.unitconvert.config.UnitConversionConfig;
import com.zishuimuyu.unitconvert.data.ConversionFactors;
import com.zishuimuyu.unitconvert.data.SystemRatios;
import com.zishuimuyu.unitconvert.exception.UnitConversionException;
import com.zishuimuyu.unitconvert.model.ConvertResult;
import com.zishuimuyu.unitconvert.model.UnitEnum;
import com.zishuimuyu.unitconvert.util.PrecisionCalculator;

import java.math.BigDecimal;

/**
 * 标准单位转换策略
 * <P>
 * 处理非温度类单位的转换，使用锚点单位进行转换
 * <P>
 * 功能列表：
 * <ul>
 *   <li>处理非温度类单位的转换</li>
 *   <li>使用锚点单位进行转换</li>
 *   <li>支持同系统内和跨系统间的单位转换</li>
 *   <li>验证单位配置和有效性</li>
 * </ul>
 * <P>
 * 设计原理：
 * <ol>
 *   <li>首先检查配置和单位有效性</li>
 *   <li>获取源单位和目标单位的转换因子</li>
 *   <li>如果在同一系统内，直接使用转换因子进行计算</li>
 *   <li>如果在不同系统间，通过系统间转换比率进行转换</li>
 * </ol>
 * <P>
 * 优势：
 * <ul>
 *   <li>减少存储空间：使用统一的锚点单位系统</li>
 *   <li>提高转换精度：避免多步转换累积误差</li>
 *   <li>易于维护：集中管理转换逻辑</li>
 * </ul>
 * <P>
 * 使用场景：
 * <ol>
 *   <li>处理非温度类单位的转换需求</li>
 *   <li>需要高精度转换的应用场景</li>
 *   <li>支持配置化的单位转换系统</li>
 * </ol>
 * <P>
 * 使用示例：
 * <pre>
 * UnitConversionConfig config = new UnitConversionConfig();
 * StandardConversionStrategy strategy = new StandardConversionStrategy(config);
 * ConvertResult&lt;BigDecimal&gt; result = strategy.convert(
 *     new BigDecimal("10"), 
 *     UnitEnum.M, 
 *     UnitEnum.CM
 * );
 * </pre>
 * <P>
 * 
 * @author 紫水木鱼
 * @version 1.0.0
 * @since 2026-04-25
 */
public class StandardConversionStrategy implements ConversionStrategy {
    
    /**
     * 单位转换配置
     * <P>
     * 详细描述字段的用途、取值范围或特殊含义
     * <P>
     * 用途：用于控制转换行为，如部分加载、单位排除等
     * <P>
     * 取值范围：UnitConversionConfig对象实例
     * <P>
     * 特殊含义：提供配置选项以控制单位转换的行为
     */
    private final UnitConversionConfig config;

    /**
     * 构造函数
     * <P>
     * 详细描述方法的功能、算法逻辑或业务流程
     * <P>
     * 处理逻辑：
     * <ol>
     *   <li>接收单位转换配置参数</li>
     *   <li>将配置保存到实例变量中供后续使用</li>
     * </ol>
     * <P>
     * 参数说明：
     * <ul>
     *   <li>config: 单位转换配置，用于控制转换行为</li>
     * </ul>
     * <P>
     * 返回值说明：
     * <ul>
     *   <li>void: 无返回值，仅初始化对象</li>
     * </ul>
     * <P>
     * 异常情况：
     * <ul>
     *   <li>当config为null时，可能导致NullPointerException</li>
     * </ul>
     * 
     * @param config 单位转换配置
     */
    public StandardConversionStrategy(UnitConversionConfig config) {
        this.config = config;
    }

    /**
     * 执行单位转换
     * <P>
     * 详细描述方法的功能、算法逻辑或业务流程
     * <P>
     * 处理逻辑：
     * <ol>
     *   <li>检查测量类型是否应该被加载（部分加载模式）</li>
     *   <li>检查源单位和目标单位是否被配置排除</li>
     *   <li>获取源单位和目标单位的转换因子</li>
     *   <li>如果转换因子为空，抛出异常</li>
     *   <li>判断是否需要系统间转换</li>
     *   <li>如果是同系统转换，直接使用转换因子进行计算</li>
     *   <li>如果是跨系统转换，调用跨系统转换方法</li>
     *   <li>返回转换结果</li>
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
     *   <li>成功: 返回包含转换结果的ConvertResult对象</li>
     *   <li>失败: 抛出UnitConversionException异常</li>
     * </ul>
     * <P>
     * 异常情况：
     * <ul>
     *   <li>当测量类型未在配置中包含时，抛出MEASURE_NOT_LOADED异常</li>
     *   <li>当源单位或目标单位被配置排除时，抛出UNIT_EXCLUDED异常</li>
     *   <li>当单位未知时，抛出UNKNOWN_UNIT异常</li>
     *   <li>当系统间转换比率未定义时，抛出UNDEFINED_CONVERSION_RATIO异常</li>
     * </ul>
     * 
     * @param value 待转换的数值
     * @param fromUnit 源单位
     * @param toUnit 目标单位
     * @return 转换结果对象
     */
    @Override
    public ConvertResult<BigDecimal> convert(BigDecimal value, UnitEnum fromUnit, UnitEnum toUnit) {
        // 检查测量类型是否应该被加载（部分加载模式）
        String measure = fromUnit.getMeasure();
        if (!config.shouldLoadMeasure(measure)) {
            throw new UnitConversionException(
                String.format("测量类型 %s 未在配置中包含（部分加载模式）", measure),
                "MEASURE_NOT_LOADED"
            );
        }

        // 检查单位是否被排除
        if (config.shouldExcludeUnit(fromUnit)) {
            throw new UnitConversionException(
                String.format("源单位 %s (%s) 已被配置排除", fromUnit.getAbbr(), fromUnit.getSingular()),
                "UNIT_EXCLUDED"
            );
        }

        if (config.shouldExcludeUnit(toUnit)) {
            throw new UnitConversionException(
                String.format("目标单位 %s (%s) 已被配置排除", toUnit.getAbbr(), toUnit.getSingular()),
                "UNIT_EXCLUDED"
            );
        }

        BigDecimal fromFactor = ConversionFactors.getFactor(fromUnit);
        BigDecimal toFactor = ConversionFactors.getFactor(toUnit);

        if (fromFactor == null || toFactor == null) {
            throw new UnitConversionException("未知的单位", "UNKNOWN_UNIT");
        }

        // 检查是否需要系统间转换
        BigDecimal result;
        if (fromUnit.getSystem().equals(toUnit.getSystem())) {
            // 同一系统内的转换
            result = PrecisionCalculator.divide(
                PrecisionCalculator.multiply(value, fromFactor), 
                toFactor
            );
        } else {
            // 系统间转换
            result = performCrossSystemConversion(value, fromUnit, toUnit);
        }

        return new ConvertResult<>(result, toUnit.getAbbr(), toUnit.getSingular(), toUnit.getPlural());
    }

    /**
     * 检查是否支持指定的单位转换
     * <P>
     * 详细描述方法的功能、算法逻辑或业务流程
     * <P>
     * 处理逻辑：
     * <ol>
     *   <li>检查源单位的测量类型是否为温度</li>
     *   <li>如果不为温度且源单位和目标单位属于同一测量类型，则返回true</li>
     *   <li>否则返回false</li>
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
     *   <li>true: 支持该单位转换（非温度且同测量类型）</li>
     *   <li>false: 不支持该单位转换（温度或不同测量类型）</li>
     * </ul>
     * <P>
     * 异常情况：
     * <ul>
     *   <li>无异常情况</li>
     * </ul>
     * 
     * @param fromUnit 源单位
     * @param toUnit 目标单位
     * @return 是否支持该单位转换
     */
    @Override
    public boolean supports(UnitEnum fromUnit, UnitEnum toUnit) {
        // 不支持温度转换（由专门的策略处理）
        return !"temperature".equals(fromUnit.getMeasure()) && 
               fromUnit.getMeasure().equals(toUnit.getMeasure());
    }

    /**
     * 执行跨系统转换
     * <P>
     * 详细描述方法的功能、算法逻辑或业务流程
     * <P>
     * 处理逻辑：
     * <ol>
     *   <li>获取测量类型</li>
     *   <li>构建系统间转换比率的键</li>
     *   <li>从系统比率管理器获取转换比率</li>
     *   <li>如果转换比率为空，抛出异常</li>
     *   <li>将源值转换为源系统的锚点单位</li>
     *   <li>通过系统间比率转换到目标系统的锚点单位</li>
     *   <li>从目标系统的锚点单位转换到目标单位</li>
     *   <li>返回最终转换结果</li>
     * </ol>
     * <P>
     * 转换步骤详解：
     * <ol>
     *   <li>将源值转换为源系统的锚点单位：value * fromFactor</li>
     *   <li>通过系统间比率转换到目标系统的锚点单位：anchorValue * systemRatio</li>
     *   <li>从目标系统的锚点单位转换到目标单位：convertedAnchorValue / toFactor</li>
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
     *   <li>成功: 返回跨系统转换后的BigDecimal值</li>
     *   <li>失败: 抛出UnitConversionException异常</li>
     * </ul>
     * <P>
     * 异常情况：
     * <ul>
     *   <li>当系统间转换比率未定义时，抛出UNDEFINED_CONVERSION_RATIO异常</li>
     * </ul>
     * 
     * @param value 待转换的值
     * @param fromUnit 源单位
     * @param toUnit 目标单位
     * @return 转换后的值
     */
    private BigDecimal performCrossSystemConversion(BigDecimal value, UnitEnum fromUnit, UnitEnum toUnit) {
        String measureType = fromUnit.getMeasure();
        
        // 获取系统间转换比率
        String ratioKey = fromUnit.getSystem() + "_to_" + toUnit.getSystem();
        BigDecimal systemRatio = SystemRatios.getRatio(measureType, ratioKey);
        
        if (systemRatio == null) {
            throw new UnitConversionException("未定义的系统间转换比率: " + ratioKey, "UNDEFINED_CONVERSION_RATIO");
        }
        
        // 转换步骤：
        // 1. 将源值转换为源系统的锚点单位
        BigDecimal fromFactor = ConversionFactors.getFactor(fromUnit);
        BigDecimal anchorValue = PrecisionCalculator.multiply(value, fromFactor);
        
        // 2. 通过系统间比率转换到目标系统的锚点单位
        BigDecimal convertedAnchorValue = PrecisionCalculator.multiply(anchorValue, systemRatio);
        
        // 3. 从目标系统的锚点单位转换到目标单位
        BigDecimal toFactor = ConversionFactors.getFactor(toUnit);
        return PrecisionCalculator.divide(convertedAnchorValue, toFactor);
    }
}