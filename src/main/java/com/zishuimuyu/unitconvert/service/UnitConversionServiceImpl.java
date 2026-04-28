package com.zishuimuyu.unitconvert.service;

import com.zishuimuyu.unitconvert.builder.ConversionBuilder;
import com.zishuimuyu.unitconvert.config.UnitConversionConfig;
import com.zishuimuyu.unitconvert.converter.BestUnitFinder;
import com.zishuimuyu.unitconvert.model.ConvertResult;
import com.zishuimuyu.unitconvert.model.ToBestOptions;
import com.zishuimuyu.unitconvert.model.UnitDescription;
import com.zishuimuyu.unitconvert.model.UnitEnum;
import com.zishuimuyu.unitconvert.strategy.ConversionStrategy;
import com.zishuimuyu.unitconvert.strategy.ConversionStrategyManager;
import com.zishuimuyu.unitconvert.strategy.StandardConversionStrategy;
import com.zishuimuyu.unitconvert.strategy.TemperatureConversionStrategy;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 单位转换服务实现类
 * <P>
 * 提供各种物理量单位之间的转换功能
 * <P>
 * 功能列表：
 * <ul>
 *   <li>提供各种物理量单位之间的转换功能</li>
 *   <li>支持公制、英制等多种单位系统</li>
 *   <li>使用BigDecimal保证计算精度</li>
 *   <li>支持最佳单位查找和自动选择</li>
 *   <li>提供链式调用API</li>
 *   <li>支持单位描述和查询功能</li>
 * </ul>
 * <P>
 * 设计原理：
 * <ol>
 *   <li>使用策略模式管理不同类型的转换逻辑</li>
 *   <li>标准转换策略处理常规单位转换</li>
 *   <li>温度转换策略处理温度单位的特殊转换</li>
 *   <li>通过转换因子和系统间比率实现精确转换</li>
 * </ol>
 * <P>
 * 优势：
 * <ul>
 *   <li>减少存储空间：使用统一的策略管理模式</li>
 *   <li>提高转换精度：使用BigDecimal进行精确计算</li>
 *   <li>易于扩展：通过策略模式轻松添加新的转换类型</li>
 * </ul>
 * <P>
 * 使用场景：
 * <ol>
 *   <li>需要进行单位转换的各种应用场景</li>
 *   <li>科学计算和工程应用</li>
 *   <li>需要高精度转换的应用</li>
 *   <li>支持多种单位系统的国际化应用</li>
 * </ol>
 * <P>
 * 使用示例：
 * <pre>
 * // 创建转换服务实例
 * UnitConversionServiceImpl converter = new UnitConversionServiceImpl();
 * 
 * // 执行单位转换
 * ConvertResult&lt;BigDecimal&gt; result = converter.convert(
 *     new BigDecimal("10"), 
 *     UnitEnum.M, 
 *     UnitEnum.CM
 * );
 * 
 * // 查找最佳单位
 * ConvertResult&lt;BigDecimal&gt; bestResult = converter.convertToBest(
 *     new BigDecimal("1000"), 
 *     UnitEnum.CM
 * );
 * 
 * // 使用链式调用
 * ConvertResult&lt;BigDecimal&gt; chainResult = converter
 *     .from(new BigDecimal("10"), UnitEnum.M)
 *     .to(UnitEnum.CM);
 * </pre>
 * <P>
 * 
 * @author 紫水木鱼
 * @version 1.0.0
 * @since 2026-04-24
 */
public class UnitConversionServiceImpl implements IUnitConversionService {

    /**
     * 转换策略管理器
     * <P>
     * 详细描述字段的用途、取值范围或特殊含义
     * <P>
     * 用途：管理不同类型的转换策略，如标准转换、温度转换等
     * <P>
     * 取值范围：ConversionStrategyManager对象实例
     * <P>
     * 特殊含义：负责选择和执行适当的转换策略
     */
    private final ConversionStrategyManager strategyManager;
    
    /**
     * 最佳单位查找器
     * <P>
     * 详细描述字段的用途、取值范围或特殊含义
     * <P>
     * 用途：用于查找最适合显示的单位
     * <P>
     * 取值范围：BestUnitFinder对象实例
     * <P>
     * 特殊含义：根据数值大小和用户偏好自动选择最合适的单位
     */
    private final BestUnitFinder bestUnitFinder;
    
    /**
     * 单位转换配置
     * <P>
     * 详细描述字段的用途、取值范围或特殊含义
     * <P>
     * 用途：用于控制单位转换服务的行为，如部分加载、单位排除等
     * <P>
     * 取值范围：UnitConversionConfig对象实例
     * <P>
     * 特殊含义：提供配置选项以控制单位转换的行为
     */
    private final UnitConversionConfig config;

    /**
     * 默认构造函数，使用默认配置
     * <P>
     * 详细描述方法的功能、算法逻辑或业务流程
     * <P>
     * 处理逻辑：
     * <ol>
     *   <li>调用带配置的构造函数</li>
     *   <li>传入默认的单位转换配置</li>
     * </ol>
     * <P>
     * 参数说明：
     * <ul>
     *   <li>无参数，使用默认配置</li>
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
     * @see #UnitConversionServiceImpl(UnitConversionConfig)
     */
    public UnitConversionServiceImpl() {
        this(UnitConversionConfig.defaults());
    }
    
    /**
     * 带配置的构造函数
     * <P>
     * 详细描述方法的功能、算法逻辑或业务流程
     * <P>
     * 处理逻辑：
     * <ol>
     *   <li>检查传入的配置是否为null</li>
     *   <li>如果为null则使用默认配置，否则使用传入的配置</li>
     *   <li>创建转换策略列表</li>
     *   <li>添加标准转换策略实例</li>
     *   <li>添加温度转换策略实例</li>
     *   <li>使用策略列表创建策略管理器</li>
     *   <li>创建最佳单位查找器实例</li>
     * </ol>
     * <P>
     * 参数说明：
     * <ul>
     *   <li>config: 单位转换配置，如果为null则使用默认配置</li>
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
     * @param config 单位转换配置
     */
    public UnitConversionServiceImpl(UnitConversionConfig config) {
        this.config = config != null ? config : UnitConversionConfig.defaults();
        
        // 创建转换策略管理器，注册各种转换策略
        List<ConversionStrategy> strategies = new ArrayList<>();
        strategies.add(new StandardConversionStrategy(this.config));
        strategies.add(new TemperatureConversionStrategy(this.config));
        
        this.strategyManager = new ConversionStrategyManager(strategies);
        this.bestUnitFinder = new BestUnitFinder(this, this.config);
    }

    /**
     * 执行单位转换
     * <P>
     * 详细描述方法的功能、算法逻辑或业务流程
     * <P>
     * 处理逻辑：
     * <ol>
     *   <li>委托给策略管理器执行实际的转换操作</li>
     *   <li>策略管理器会选择适当的转换策略</li>
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
     *   <li>失败: 抛出相应的异常</li>
     * </ul>
     * <P>
     * 异常情况：
     * <ul>
     *   <li>当无法找到合适的转换策略时，可能抛出异常</li>
     *   <li>当单位无效或不支持转换时，抛出UnitConversionException</li>
     * </ul>
     * 
     * @param value 待转换的数值
     * @param fromUnit 源单位
     * @param toUnit 目标单位
     * @return 转换结果对象
     */
    @Override
    public ConvertResult<BigDecimal> convert(BigDecimal value, UnitEnum fromUnit, UnitEnum toUnit) {
        return strategyManager.convert(value, fromUnit, toUnit);
    }

    /**
     * 转换到最佳单位（无选项）
     * <P>
     * 详细描述方法的功能、算法逻辑或业务流程
     * <P>
     * 处理逻辑：
     * <ol>
     *   <li>委托给最佳单位查找器执行查找操作</li>
     *   <li>使用默认选项（null）进行最佳单位查找</li>
     *   <li>返回转换结果</li>
     * </ol>
     * <P>
     * 参数说明：
     * <ul>
     *   <li>value: 待转换的数值</li>
     *   <li>fromUnit: 源单位</li>
     * </ul>
     * <P>
     * 返回值说明：
     * <ul>
     *   <li>成功: 返回包含最佳单位转换结果的ConvertResult对象</li>
     *   <li>失败: 抛出相应的异常</li>
     * </ul>
     * <P>
     * 异常情况：
     * <ul>
     *   <li>当无法找到合适的最佳单位时，可能抛出异常</li>
     *   <li>当单位无效或不支持转换时，抛出UnitConversionException</li>
     * </ul>
     * 
     * @param value 待转换的数值
     * @param fromUnit 源单位
     * @return 包含最佳单位的转换结果对象
     */
    @Override
    public ConvertResult<BigDecimal> convertToBest(BigDecimal value, UnitEnum fromUnit) {
        return bestUnitFinder.findBestUnit(value, fromUnit, null);
    }

    /**
     * 转换到最佳单位（带选项）
     * <P>
     * 详细描述方法的功能、算法逻辑或业务流程
     * <P>
     * 处理逻辑：
     * <ol>
     *   <li>委托给最佳单位查找器执行查找操作</li>
     *   <li>使用提供的选项进行最佳单位查找</li>
     *   <li>返回转换结果</li>
     * </ol>
     * <P>
     * 参数说明：
     * <ul>
     *   <li>value: 待转换的数值</li>
     *   <li>fromUnit: 源单位</li>
     *   <li>options: 最佳单位查找选项</li>
     * </ul>
     * <P>
     * 返回值说明：
     * <ul>
     *   <li>成功: 返回包含最佳单位转换结果的ConvertResult对象</li>
     *   <li>失败: 抛出相应的异常</li>
     * </ul>
     * <P>
     * 异常情况：
     * <ul>
     *   <li>当无法找到合适的最佳单位时，可能抛出异常</li>
     *   <li>当单位无效或不支持转换时，抛出UnitConversionException</li>
     * </ul>
     * 
     * @param value 待转换的数值
     * @param fromUnit 源单位
     * @param options 最佳单位查找选项
     * @return 包含最佳单位的转换结果对象
     */
    @Override
    public ConvertResult<BigDecimal> convertToBest(BigDecimal value, UnitEnum fromUnit, ToBestOptions options) {
        return bestUnitFinder.findBestUnit(value, fromUnit, options);
    }

    /**
     * 检查是否可以在两个单位之间进行转换
     * <P>
     * 详细描述方法的功能、算法逻辑或业务流程
     * <P>
     * 处理逻辑：
     * <ol>
     *   <li>委托给策略管理器检查转换可能性</li>
     *   <li>策略管理器会根据单位类型和可用策略判断是否支持转换</li>
     *   <li>返回检查结果</li>
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
     *   <li>true: 支持从源单位到目标单位的转换</li>
     *   <li>false: 不支持从源单位到目标单位的转换</li>
     * </ul>
     * <P>
     * 异常情况：
     * <ul>
     *   <li>无异常情况</li>
     * </ul>
     * 
     * @param fromUnit 源单位
     * @param toUnit 目标单位
     * @return 是否支持转换
     */
    @Override
    public boolean canConvertBetween(UnitEnum fromUnit, UnitEnum toUnit) {
        return strategyManager.canConvertBetween(fromUnit, toUnit);
    }

    /**
     * 描述单位信息
     * <P>
     * 详细描述方法的功能、算法逻辑或业务流程
     * <P>
     * 处理逻辑：
     * <ol>
     *   <li>检查输入的单位是否为null</li>
     *   <li>检查单位是否被配置排除</li>
     *   <li>检查单位所属的测量类型是否应该被加载</li>
     *   <li>如果以上检查都通过，则创建并返回单位描述对象</li>
     * </ol>
     * <P>
     * 参数说明：
     * <ul>
     *   <li>unit: 要描述的单位</li>
     * </ul>
     * <P>
     * 返回值说明：
     * <ul>
     *   <li>成功: 返回包含单位详细信息的UnitDescription对象</li>
     *   <li>失败: 返回null（当单位为null、被排除或测量类型未加载时）</li>
     * </ul>
     * <P>
     * 异常情况：
     * <ul>
     *   <li>无异常情况</li>
     * </ul>
     * 
     * @param unit 要描述的单位
     * @return 单位描述对象
     */
    @Override
    public UnitDescription describeUnit(UnitEnum unit) {
        if (unit == null) {
            return null;
        }
        
        if (config.shouldExcludeUnit(unit)) {
            return null;
        }
        
        if (!config.shouldLoadMeasure(unit.getMeasure())) {
            return null;
        }
        
        return new UnitDescription(
            unit.getAbbr(),
            unit.getMeasure(),
            unit.getSystem(),
            unit.getSingular(),
            unit.getPlural()
        );
    }

    /**
     * 链式调用入口 - 设置源值和源单位
     * <P>
     * 详细描述方法的功能、算法逻辑或业务流程
     * <P>
     * 处理逻辑：
     * <ol>
     *   <li>创建链式转换上下文对象</li>
     *   <li>将当前服务实例、待转换值和源单位传递给上下文</li>
     *   <li>返回上下文对象以支持链式调用</li>
     * </ol>
     * <P>
     * 参数说明：
     * <ul>
     *   <li>value: 待转换的数值</li>
     *   <li>fromUnit: 源单位</li>
     * </ul>
     * <P>
     * 返回值说明：
     * <ul>
     *   <li>返回ChainConversionContext实例，支持进一步的链式调用</li>
     * </ul>
     * <P>
     * 异常情况：
     * <ul>
     *   <li>无异常情况</li>
     * </ul>
     * 
     * @param value 待转换的数值
     * @param fromUnit 源单位
     * @return 链式调用上下文对象
     */
    @Override
    public IUnitConversionService from(BigDecimal value, UnitEnum fromUnit) {
        return new com.zishuimuyu.unitconvert.chain.ChainConversionContext(this, value, fromUnit);
    }

    /**
     * 获取可能的单位列表
     * <P>
     * 详细描述方法的功能、算法逻辑或业务流程
     * <P>
     * 处理逻辑：
     * <ol>
     *   <li>检查测量类型参数是否为null</li>
     *   <li>如果不为null，则返回指定测量类型的所有可能单位</li>
     *   <li>如果为null，则返回所有已加载且未被排除的单位</li>
     * </ol>
     * <P>
     * 参数说明：
     * <ul>
     *   <li>measure: 测量类型，如果为null则返回所有单位</li>
     * </ul>
     * <P>
     * 返回值说明：
     * <ul>
     *   <li>返回符合条件的单位枚举列表</li>
     * </ul>
     * <P>
     * 异常情况：
     * <ul>
     *   <li>无异常情况</li>
     * </ul>
     * 
     * @param measure 测量类型
     * @return 可能的单位列表
     */
    @Override
    public List<UnitEnum> possibilities(String measure) {
        if (measure != null) {
            return getPossibleUnits(measure);
        } else {
            List<UnitEnum> allUnits = new ArrayList<>();
            for (UnitEnum unit : UnitEnum.values()) {
                if (config.shouldExcludeUnit(unit)) {
                    continue;
                }
                
                if (!config.shouldLoadMeasure(unit.getMeasure())) {
                    continue;
                }
                
                allUnits.add(unit);
            }
            return allUnits;
        }
    }

    /**
     * 获取支持的测量类型列表
     * <P>
     * 详细描述方法的功能、算法逻辑或业务流程
     * <P>
     * 处理逻辑：
     * <ol>
     *   <li>调用getSupportedMeasures方法获取支持的测量类型</li>
     *   <li>返回测量类型列表</li>
     * </ol>
     * <P>
     * 参数说明：
     * <ul>
     *   <li>无参数</li>
     * </ul>
     * <P>
     * 返回值说明：
     * <ul>
     *   <li>返回支持的测量类型字符串列表</li>
     * </ul>
     * <P>
     * 异常情况：
     * <ul>
     *   <li>无异常情况</li>
     * </ul>
     * 
     * @return 支持的测量类型列表
     */
    @Override
    public List<String> measures() {
        return getSupportedMeasures();
    }
    
    /**
     * 根据名称查找单位
     * <P>
     * 详细描述方法的功能、算法逻辑或业务流程
     * <P>
     * 处理逻辑：
     * <ol>
     *   <li>检查输入的单位名称是否为null或空</li>
     *   <li>去除名称两端的空白字符</li>
     *   <li>遍历所有单位枚举</li>
     *   <li>跳过被排除或未加载的单位</li>
     *   <li>匹配缩写、单数形式或复数形式</li>
     *   <li>如果找到匹配项，返回单位描述</li>
     *   <li>如果未找到匹配项，返回null</li>
     * </ol>
     * <P>
     * 参数说明：
     * <ul>
     *   <li>unitName: 单位名称（缩写、单数或复数形式）</li>
     * </ul>
     * <P>
     * 返回值说明：
     * <ul>
     *   <li>成功: 返回匹配单位的描述</li>
     *   <li>失败: 返回null（未找到匹配的单位）</li>
     * </ul>
     * <P>
     * 异常情况：
     * <ul>
     *   <li>无异常情况</li>
     * </ul>
     * 
     * @param unitName 单位名称
     * @return 单位描述
     */
    @Override
    public UnitDescription lookup(String unitName) {
        if (unitName == null || unitName.trim().isEmpty()) {
            return null;
        }
        
        String trimmedName = unitName.trim();
        
        for (UnitEnum unit : UnitEnum.values()) {
            if (config.shouldExcludeUnit(unit)) {
                continue;
            }
            
            if (!config.shouldLoadMeasure(unit.getMeasure())) {
                continue;
            }
            
            if (unit.getAbbr().equalsIgnoreCase(trimmedName)) {
                return describeUnit(unit);
            }
            
            if (unit.getSingular().equalsIgnoreCase(trimmedName)) {
                return describeUnit(unit);
            }
            
            if (unit.getPlural().equalsIgnoreCase(trimmedName)) {
                return describeUnit(unit);
            }
        }
        
        return null;
    }

    /**
     * 获取支持的测量类型列表
     * <P>
     * 详细描述方法的功能、算法逻辑或业务流程
     * <P>
     * 处理逻辑：
     * <ol>
     *   <li>创建一个空的测量类型列表</li>
     *   <li>遍历所有单位枚举</li>
     *   <li>跳过被排除或未加载的单位</li>
     *   <li>如果测量类型尚未添加到列表中，则添加它</li>
     *   <li>返回去重后的测量类型列表</li>
     * </ol>
     * <P>
     * 参数说明：
     * <ul>
     *   <li>无参数</li>
     * </ul>
     * <P>
     * 返回值说明：
     * <ul>
     *   <li>返回支持的测量类型字符串列表</li>
     * </ul>
     * <P>
     * 异常情况：
     * <ul>
     *   <li>无异常情况</li>
     * </ul>
     * 
     * @return 支持的测量类型列表
     */
    @Override
    public List<String> getSupportedMeasures() {
        List<String> measures = new ArrayList<>();
        
        for (UnitEnum unit : UnitEnum.values()) {
            String measure = unit.getMeasure();
            
            if (config.shouldExcludeUnit(unit)) {
                continue;
            }
            
            if (!config.shouldLoadMeasure(measure)) {
                continue;
            }
            
            if (!measures.contains(measure)) {
                measures.add(measure);
            }
        }
        
        return measures;
    }

    /**
     * 获取指定测量类型的所有可能单位
     * <P>
     * 详细描述方法的功能、算法逻辑或业务流程
     * <P>
     * 处理逻辑：
     * <ol>
     *   <li>创建一个空的单位列表</li>
     *   <li>遍历所有单位枚举</li>
     *   <li>过滤出指定测量类型的单位</li>
     *   <li>跳过被排除或未加载的单位</li>
     *   <li>将符合条件的单位添加到列表中</li>
     *   <li>返回单位列表</li>
     * </ol>
     * <P>
     * 参数说明：
     * <ul>
     *   <li>measure: 测量类型</li>
     * </ul>
     * <P>
     * 返回值说明：
     * <ul>
     *   <li>返回指定测量类型的所有可能单位列表</li>
     * </ul>
     * <P>
     * 异常情况：
     * <ul>
     *   <li>无异常情况</li>
     * </ul>
     * 
     * @param measure 测量类型
     * @return 可能的单位列表
     */
    @Override
    public List<UnitEnum> getPossibleUnits(String measure) {
        List<UnitEnum> units = new ArrayList<>();
        
        for (UnitEnum unit : UnitEnum.values()) {
            if (!unit.getMeasure().equals(measure)) {
                continue;
            }
            
            if (config.shouldExcludeUnit(unit)) {
                continue;
            }
            
            if (!config.shouldLoadMeasure(measure)) {
                continue;
            }
            
            units.add(unit);
        }
        
        return units;
    }

    /**
     * 按测量类型列出单位描述
     * <P>
     * 详细描述方法的功能、算法逻辑或业务流程
     * <P>
     * 处理逻辑：
     * <ol>
     *   <li>创建一个空的单位描述列表</li>
     *   <li>遍历所有单位枚举</li>
     *   <li>过滤出指定测量类型的单位</li>
     *   <li>跳过被排除或未加载的单位</li>
     *   <li>为每个符合条件的单位创建描述并添加到列表中</li>
     *   <li>返回单位描述列表</li>
     * </ol>
     * <P>
     * 参数说明：
     * <ul>
     *   <li>measure: 测量类型</li>
     * </ul>
     * <P>
     * 返回值说明：
     * <ul>
     *   <li>返回指定测量类型的所有单位描述列表</li>
     * </ul>
     * <P>
     * 异常情况：
     * <ul>
     *   <li>无异常情况</li>
     * </ul>
     * 
     * @param measure 测量类型
     * @return 单位描述列表
     */
    @Override
    public List<UnitDescription> listUnitsByMeasure(String measure) {
        List<UnitDescription> descriptions = new ArrayList<>();
        
        for (UnitEnum unit : UnitEnum.values()) {
            if (!unit.getMeasure().equals(measure)) {
                continue;
            }
            
            if (config.shouldExcludeUnit(unit)) {
                continue;
            }
            
            if (!config.shouldLoadMeasure(measure)) {
                continue;
            }
            
            descriptions.add(describeUnit(unit));
        }
        
        return descriptions;
    }

    /**
     * 列出所有单位描述
     * <P>
     * 详细描述方法的功能、算法逻辑或业务流程
     * <P>
     * 处理逻辑：
     * <ol>
     *   <li>创建一个空的单位描述列表</li>
     *   <li>遍历所有单位枚举</li>
     *   <li>跳过被排除或未加载的单位</li>
     *   <li>为每个符合条件的单位创建描述并添加到列表中</li>
     *   <li>返回所有单位描述列表</li>
     * </ol>
     * <P>
     * 参数说明：
     * <ul>
     *   <li>无参数</li>
     * </ul>
     * <P>
     * 返回值说明：
     * <ul>
     *   <li>返回所有已加载且未被排除的单位描述列表</li>
     * </ul>
     * <P>
     * 异常情况：
     * <ul>
     *   <li>无异常情况</li>
     * </ul>
     * 
     * @return 所有单位描述列表
     */
    @Override
    public List<UnitDescription> listAllUnits() {
        List<UnitDescription> descriptions = new ArrayList<>();
        
        for (UnitEnum unit : UnitEnum.values()) {
            if (config.shouldExcludeUnit(unit)) {
                continue;
            }
            
            if (!config.shouldLoadMeasure(unit.getMeasure())) {
                continue;
            }
            
            descriptions.add(describeUnit(unit));
        }
        
        return descriptions;
    }

    /**
     * 构建转换器
     * <P>
     * 详细描述方法的功能、算法逻辑或业务流程
     * <P>
     * 处理逻辑：
     * <ol>
     *   <li>创建一个新的转换构建器实例</li>
     *   <li>将当前服务实例、待转换值和源单位传递给构建器</li>
     *   <li>返回构建器实例以支持流畅的API调用</li>
     * </ol>
     * <P>
     * 参数说明：
     * <ul>
     *   <li>value: 待转换的数值</li>
     *   <li>fromUnit: 源单位</li>
     * </ul>
     * <P>
     * 返回值说明：
     * <ul>
     *   <li>返回ConversionBuilder实例，支持进一步的构建操作</li>
     * </ul>
     * <P>
     * 异常情况：
     * <ul>
     *   <li>无异常情况</li>
     * </ul>
     * 
     * @param value 待转换的数值
     * @param fromUnit 源单位
     * @return 转换构建器实例
     */
    @Override
    public ConversionBuilder buildConversion(BigDecimal value, UnitEnum fromUnit) {
        return new ConversionBuilder(this, value, fromUnit);
    }
}