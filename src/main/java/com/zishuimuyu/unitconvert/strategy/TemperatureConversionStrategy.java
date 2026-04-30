package com.zishuimuyu.unitconvert.strategy;

import com.zishuimuyu.unitconvert.config.UnitConversionConfig;
import com.zishuimuyu.unitconvert.exception.UnitConversionException;
import com.zishuimuyu.unitconvert.model.ConvertResult;
import com.zishuimuyu.unitconvert.model.UnitEnum;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 温度单位转换策略
 * <P>
 * 专门处理温度单位的转换，使用特殊的偏移计算公式
 * <P>
 * 功能列表：
 * <ul>
 *   <li>专门处理温度单位的转换</li>
 *   <li>使用特殊的偏移计算公式而非简单的比例转换</li>
 *   <li>支持摄氏度、华氏度、开尔文和兰金温标之间的转换</li>
 *   <li>使用摄氏度作为统一的中间单位进行转换</li>
 * </ul>
 * <P>
 * 设计原理：
 * <ol>
 *   <li>使用摄氏度作为统一的中间单位进行温度转换</li>
 *   <li>实现从任意温度单位到摄氏度的转换</li>
 *   <li>实现从摄氏度到任意温度单位的转换</li>
 *   <li>使用特殊的偏移计算公式而非简单的比例转换</li>
 * </ol>
 * <P>
 * 优势：
 * <ul>
 *   <li>减少存储空间：使用统一的中间单位，无需存储所有单位间的直接转换公式</li>
 *   <li>提高转换精度：使用精确的温度转换公式</li>
 *   <li>易于维护：统一的转换逻辑，便于添加新的温度单位</li>
 * </ul>
 * <P>
 * 使用场景：
 * <ol>
 *   <li>需要进行温度单位转换的应用场景</li>
 *   <li>科学计算和工程应用</li>
 *   <li>气象学和物理学相关应用</li>
 * </ol>
 * <P>
 * 使用示例：
 * <pre>
 * UnitConversionConfig config = new UnitConversionConfig();
 * TemperatureConversionStrategy strategy = new TemperatureConversionStrategy(config);
 * ConvertResult&lt;BigDecimal&gt; result = strategy.convert(
 *     new BigDecimal("100"), 
 *     UnitEnum.C, 
 *     UnitEnum.F
 * );
 * </pre>
 * <P>
 * 
 * @author 紫水木鱼
 * @version 1.0.0
 * @since 2026-04-25
 */
public class TemperatureConversionStrategy implements ConversionStrategy {
    
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
    public TemperatureConversionStrategy(UnitConversionConfig config) {
        this.config = config;
    }

    /**
     * 执行温度单位转换
     * <P>
     * 详细描述方法的功能、算法逻辑或业务流程
     * <P>
     * 处理逻辑：
     * <ol>
     *   <li>检查测量类型是否应该被加载（部分加载模式）</li>
     *   <li>检查源单位和目标单位是否被配置排除</li>
     *   <li>如果源单位和目标单位相同，直接返回原值</li>
     *   <li>从任意温度单位转换到摄氏度（使用统一的中间单位）</li>
     *   <li>从摄氏度转换到目标温度单位</li>
     *   <li>返回转换结果</li>
     * </ol>
     * <P>
     * 参数说明：
     * <ul>
     *   <li>value: 待转换的数值</li>
     *   <li>fromUnit: 源温度单位</li>
     *   <li>toUnit: 目标温度单位</li>
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
     *   <li>当单位未知时，抛出UNKNOWN_TEMPERATURE_UNIT异常</li>
     * </ul>
     * 
     * @param value 待转换的数值
     * @param fromUnit 源温度单位
     * @param toUnit 目标温度单位
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

        if (fromUnit == toUnit) {
            return new ConvertResult<>(value, toUnit.getAbbr(), toUnit.getSingular(), toUnit.getPlural());
        }

        // 从任意温度单位转换到摄氏度（使用统一的中间单位）
        BigDecimal celsiusValue = convertToCelsius(value, fromUnit);

        // 从摄氏度转换到目标温度单位
        BigDecimal result = convertFromCelsius(celsiusValue, toUnit);

        return new ConvertResult<>(result, toUnit.getAbbr(), toUnit.getSingular(), toUnit.getPlural());
    }

    /**
     * 检查是否支持指定的温度单位转换
     * <P>
     * 详细描述方法的功能、算法逻辑或业务流程
     * <P>
     * 处理逻辑：
     * <ol>
     *   <li>检查源单位的测量类型是否为温度</li>
     *   <li>检查源单位和目标单位是否属于同一测量类型</li>
     *   <li>如果两者都是温度类型且属于同一测量类型，则返回true</li>
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
     *   <li>true: 支持该温度单位转换（同为温度类型且属于同一测量类型）</li>
     *   <li>false: 不支持该单位转换（非温度类型或不同测量类型）</li>
     * </ul>
     * <P>
     * 异常情况：
     * <ul>
     *   <li>无异常情况</li>
     * </ul>
     * 
     * @param fromUnit 源单位
     * @param toUnit 目标单位
     * @return 是否支持该温度单位转换
     */
    @Override
    public boolean supports(UnitEnum fromUnit, UnitEnum toUnit) {
        return "temperature".equals(fromUnit.getMeasure()) && 
               fromUnit.getMeasure().equals(toUnit.getMeasure());
    }

    /**
     * 将任意温度单位转换为摄氏度
     * <P>
     * 详细描述方法的功能、算法逻辑或业务流程
     * <P>
     * 处理逻辑：
     * <ol>
     *   <li>根据源温度单位类型进行不同的转换计算</li>
     *   <li>摄氏度：直接返回原值（无需转换）</li>
     *   <li>华氏度：使用公式 C = (F - 32) × 5/9</li>
     *   <li>开尔文：使用公式 C = K - 273.15</li>
     *   <li>兰金温标：先转为华氏度(R - 459.67)，再转为摄氏度</li>
     *   <li>对于未知单位，抛出异常</li>
     * </ol>
     * <P>
     * 转换公式详解：
     * <ul>
     *   <li>华氏度转摄氏度: C = (F - 32) × 5/9</li>
     *   <li>开尔文转摄氏度: C = K - 273.15</li>
     *   <li>兰金温标转摄氏度: C = (R - 459.67 - 32) × 5/9</li>
     * </ul>
     * <P>
     * 参数说明：
     * <ul>
     *   <li>value: 待转换的温度值</li>
     *   <li>fromUnit: 源温度单位</li>
     * </ul>
     * <P>
     * 返回值说明：
     * <ul>
     *   <li>成功: 返回转换后的摄氏度值</li>
     *   <li>失败: 抛出UnitConversionException异常</li>
     * </ul>
     * <P>
     * 异常情况：
     * <ul>
     *   <li>当单位未知时，抛出UNKNOWN_TEMPERATURE_UNIT异常</li>
     * </ul>
     * 
     * @param value 待转换的温度值
     * @param fromUnit 源温度单位
     * @return 转换后的摄氏度值
     */
    private BigDecimal convertToCelsius(BigDecimal value, UnitEnum fromUnit) {
        switch (fromUnit) {
            case C:
                // 摄氏度到摄氏度，无需转换
                return value;
            case F:
                // 华氏度转摄氏度: C = (F - 32) × 5/9
                return value.subtract(new BigDecimal("32"))
                    .multiply(new BigDecimal("5"))
                    .divide(new BigDecimal("9"), 10, RoundingMode.HALF_UP);
            case K:
                // 开尔文转摄氏度: C = K - 273.15
                return value.subtract(new BigDecimal("273.15"));
            case RA:
                // 兰金温标转摄氏度: C = (R - 491.67) × 5/9 或 C = (R - 459.67 - 32) × 5/9
                return value.subtract(new BigDecimal("459.67"))
                    .subtract(new BigDecimal("32"))
                    .multiply(new BigDecimal("5"))
                    .divide(new BigDecimal("9"), 10, RoundingMode.HALF_UP);
            case RE:
                // 列氏度转摄氏度: C = Re × 5/4
                return value.multiply(new BigDecimal("5"))
                    .divide(new BigDecimal("4"), 10, RoundingMode.HALF_UP);

            case NEWTON_SCALE:
                // 牛氏度转摄氏度: C = N × 100/33
                return value.multiply(new BigDecimal("100"))
                    .divide(new BigDecimal("33"), 10, RoundingMode.HALF_UP);
            case WET_BULB:
                // 湿球温度与摄氏度相同刻度
                return value;
            case DE:
                // 德氏度转摄氏度: C = 100 - De × 2/3
                return new BigDecimal("100").subtract(
                    value.multiply(new BigDecimal("2"))
                        .divide(new BigDecimal("3"), 10, RoundingMode.HALF_UP)
                );
            case PLANCK_TEMP:
                // 普朗克温度转摄氏度: TP × 1.416784×10³² - 273.15
                BigDecimal kelvinValue = value.multiply(new BigDecimal("1.416784e32"));
                return kelvinValue.subtract(new BigDecimal("273.15"));
            case RO:
                // 罗氏度转摄氏度: C = (Rø - 7.5) × 40/21
                return value.subtract(new BigDecimal("7.5"))
                    .multiply(new BigDecimal("40"))
                    .divide(new BigDecimal("21"), 10, RoundingMode.HALF_UP);
            default:
                throw new UnitConversionException("未知的温度单位: " + fromUnit.getAbbr(), "UNKNOWN_TEMPERATURE_UNIT");
        }
    }

    /**
     * 从摄氏度转换为任意温度单位
     * <P>
     * 详细描述方法的功能、算法逻辑或业务流程
     * <P>
     * 处理逻辑：
     * <ol>
     *   <li>根据目标温度单位类型进行不同的转换计算</li>
     *   <li>摄氏度：直接返回原值（无需转换）</li>
     *   <li>华氏度：使用公式 F = C × 9/5 + 32</li>
     *   <li>开尔文：使用公式 K = C + 273.15</li>
     *   <li>兰金温标：先转为华氏度，然后再加上兰金温标的偏移量</li>
     *   <li>对于未知单位，抛出异常</li>
     * </ol>
     * <P>
     * 转换公式详解：
     * <ul>
     *   <li>摄氏度转华氏度: F = C × 9/5 + 32</li>
     *   <li>摄氏度转开尔文: K = C + 273.15</li>
     *   <li>摄氏度转兰金温标: R = (C × 9/5 + 32) + 459.67</li>
     * </ul>
     * <P>
     * 参数说明：
     * <ul>
     *   <li>celsiusValue: 摄氏度值</li>
     *   <li>toUnit: 目标温度单位</li>
     * </ul>
     * <P>
     * 返回值说明：
     * <ul>
     *   <li>成功: 返回转换后的目标温度值</li>
     *   <li>失败: 抛出UnitConversionException异常</li>
     * </ul>
     * <P>
     * 异常情况：
     * <ul>
     *   <li>当单位未知时，抛出UNKNOWN_TEMPERATURE_UNIT异常</li>
     * </ul>
     * 
     * @param celsiusValue 摄氏度值
     * @param toUnit 目标温度单位
     * @return 转换后的目标温度值
     */
    private BigDecimal convertFromCelsius(BigDecimal celsiusValue, UnitEnum toUnit) {
        switch (toUnit) {
            case C:
                // 摄氏度到摄氏度，无需转换
                return celsiusValue;
            case F:
                // 摄氏度转华氏度: F = C × 9/5 + 32
                return celsiusValue.multiply(new BigDecimal("9"))
                    .divide(new BigDecimal("5"), 10, RoundingMode.HALF_UP)
                    .add(new BigDecimal("32"));
            case K:
                // 摄氏度转开尔文: K = C + 273.15
                return celsiusValue.add(new BigDecimal("273.15"));

            case RE:
                // 摄氏度转列氏度: Re = C × 4/5
                return celsiusValue.multiply(new BigDecimal("4"))
                    .divide(new BigDecimal("5"), 10, RoundingMode.HALF_UP);
            case RO:
                // 摄氏度转罗氏度: Rø = C × 21/40 + 7.5
                return celsiusValue.multiply(new BigDecimal("21"))
                    .divide(new BigDecimal("40"), 10, RoundingMode.HALF_UP)
                    .add(new BigDecimal("7.5"));
            case NEWTON_SCALE:
                // 摄氏度转牛氏度: N = C × 33/100
                return celsiusValue.multiply(new BigDecimal("33"))
                    .divide(new BigDecimal("100"), 10, RoundingMode.HALF_UP);
            case WET_BULB:
                // 湿球温度与摄氏度相同刻度
                return celsiusValue;
            case DE:
                // 摄氏度转德氏度: De = (100 - C) × 3/2
                return new BigDecimal("100").subtract(celsiusValue)
                    .multiply(new BigDecimal("3"))
                    .divide(new BigDecimal("2"), 10, RoundingMode.HALF_UP);
            case PLANCK_TEMP:
                // 摄氏度转普朗克温度: (C + 273.15) / 1.416784×10³²
                BigDecimal kelvinValue = celsiusValue.add(new BigDecimal("273.15"));
                return kelvinValue.divide(new BigDecimal("1.416784e32"), 10, RoundingMode.HALF_UP);
            case RA:
                // 摄氏度转兰金温标: R = C × 9/5 + 491.67
                return celsiusValue.multiply(new BigDecimal("9"))
                    .divide(new BigDecimal("5"), 10, RoundingMode.HALF_UP)
                    .add(new BigDecimal("491.67"));
            default:
                throw new UnitConversionException("未知的温度单位: " + toUnit.getAbbr(), "UNKNOWN_TEMPERATURE_UNIT");
        }
    }
}