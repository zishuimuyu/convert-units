package com.zishuimuyu.unitconvert.converter;

import com.zishuimuyu.unitconvert.config.UnitConversionConfig;
import com.zishuimuyu.unitconvert.model.ConvertResult;
import com.zishuimuyu.unitconvert.model.ToBestOptions;
import com.zishuimuyu.unitconvert.model.UnitEnum;
import com.zishuimuyu.unitconvert.service.IUnitConversionService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * 最佳单位查找器
 * <P>
 * 根据给定的值和原始单位，找到最适合显示的单位
 * <P>
 * 功能列表：
 * <ul>
 *   <li>根据给定的值和原始单位，找到最适合显示的单位</li>
 *   <li>考虑数值大小、用户偏好、系统限制等因素</li>
 *   <li>支持按系统过滤单位（如公制、英制等）</li>
 *   <li>支持排除特定单位的选项</li>
 *   <li>根据数值范围自动选择最佳单位</li>
 * </ul>
 * <P>
 * 设计原理：
 * <ol>
 *   <li>遍历同一测量类型的所有可用单位</li>
 *   <li>排除不符合条件的单位（如被配置排除、不在加载范围内等）</li>
 *   <li>对剩余单位进行转换计算</li>
 *   <li>根据数值范围和目标值选择最佳单位</li>
 * </ol>
 * <P>
 * 优势：
 * <ul>
 *   <li>智能选择：自动选择最合适的显示单位</li>
 *   <li>灵活性：支持多种过滤和选择选项</li>
 *   <li>配置化：可根据配置排除特定单位或测量类型</li>
 * </ul>
 * <P>
 * 使用场景：
 * <ol>
 *   <li>需要自动选择最佳显示单位的应用场景</li>
 *   <li>数据展示时需要选择合适单位的场景</li>
 *   <li>用户界面中显示转换结果的场景</li>
 * </ol>
 * <P>
 * 使用示例：
 * <pre>
 * // 创建最佳单位查找器
 * BestUnitFinder finder = new BestUnitFinder(conversionService, config);
 * 
 * // 查找最佳单位
 * ToBestOptions options = new ToBestOptions();
 * options.setCutOffNumber(1.0);
 * ConvertResult&lt;BigDecimal&gt; result = finder.findBestUnit(
 *     new BigDecimal("1500"), 
 *     UnitEnum.M, 
 *     options
 * );
 * 
 * // 输出结果
 * System.out.println("最佳单位: " + result.getAbbr());
 * System.out.println("转换值: " + result.getVal());
 * </pre>
 * <P>
 * 
 * @author 紫水木鱼
 * @version 1.0.0
 * @since 2026-04-25
 */
public class BestUnitFinder {
    
    /**
     * 单位转换服务接口
     * <P>
     * 详细描述字段的用途、取值范围或特殊含义
     * <P>
     * 用途：用于执行实际的单位转换操作
     * <P>
     * 取值范围：实现了IUnitConversionService接口的服务实例
     * <P>
     * 特殊含义：提供单位转换的核心功能，BestUnitFinder依赖此服务进行实际转换计算
     */
    private final IUnitConversionService conversionService;
    
    /**
     * 单位转换配置
     * <P>
     * 详细描述字段的用途、取值范围或特殊含义
     * <P>
     * 用途：用于控制最佳单位选择的行为
     * <P>
     * 取值范围：UnitConversionConfig配置对象实例
     * <P>
     * 特殊含义：提供配置选项以控制单位转换和最佳单位选择的行为，如单位排除、部分加载等
     */
    private final UnitConversionConfig config;
    
    /**
     * 构造函数
     * <P>
     * 详细描述方法的功能、算法逻辑或业务流程
     * <P>
     * 处理逻辑：
     * <ol>
     *   <li>接收单位转换服务和配置参数</li>
     *   <li>如果配置参数为null，则使用默认配置</li>
     *   <li>初始化内部字段</li>
     * </ol>
     * <P>
     * 参数说明：
     * <ul>
     *   <li>conversionService: 单位转换服务，用于执行实际的转换操作</li>
     *   <li>config: 单位转换配置，用于控制转换行为；如果为null则使用默认配置</li>
     * </ul>
     * <P>
     * 返回值说明：
     * <ul>
     *   <li>void: 无返回值，仅初始化对象</li>
     * </ul>
     * <P>
     * 异常情况：
     * <ul>
     *   <li>当conversionService为null时，可能导致后续操作失败</li>
     * </ul>
     * 
     * @param conversionService 单位转换服务
     * @param config 单位转换配置
     */
    public BestUnitFinder(IUnitConversionService conversionService, UnitConversionConfig config) {
        this.conversionService = conversionService;
        this.config = config != null ? config : UnitConversionConfig.defaults();
    }
    
    /**
     * 查找最佳单位
     * <P>
     * 详细描述方法的功能、算法逻辑或业务流程
     * <P>
     * 处理逻辑：
     * <ol>
     *   <li>如果选项参数为null，则使用默认选项</li>
     *   <li>获取符合条件的候选单位列表</li>
     *   <li>如果候选单位为空，则返回原始单位的转换结果</li>
     *   <li>从候选单位中查找最佳单位</li>
     *   <li>返回包含最佳单位和转换值的结果</li>
     * </ol>
     * <P>
     * 参数说明：
     * <ul>
     *   <li>value: 待转换的值</li>
     *   <li>fromUnit: 原始单位</li>
     *   <li>options: 最佳单位选项，控制单位选择的行为</li>
     * </ul>
     * <P>
     * 返回值说明：
     * <ul>
     *   <li>成功: 返回包含最佳单位和转换后值的ConvertResult对象</li>
     *   <li>失败: 可能抛出UnitConversionException等异常</li>
     * </ul>
     * <P>
     * 异常情况：
     * <ul>
     *   <li>可能抛出UnitConversionException等转换异常</li>
     * </ul>
     * 
     * @param value 待转换的值
     * @param fromUnit 原始单位
     * @param options 最佳单位选项
     * @return 转换结果，包含最佳单位和转换后的值
     */
    public ConvertResult<BigDecimal> findBestUnit(BigDecimal value, UnitEnum fromUnit, ToBestOptions options) {
        if (options == null) {
            options = ToBestOptions.defaults();
        }
        
        List<UnitEnum> candidateUnits = getCandidateUnits(fromUnit, options);
        
        if (candidateUnits.isEmpty()) {
            return conversionService.convert(value, fromUnit, fromUnit);
        }
        
        return findBestCandidate(value, fromUnit, candidateUnits, options);
    }
    
    /**
     * 获取候选单位列表
     * <P>
     * 详细描述方法的功能、算法逻辑或业务流程
     * <P>
     * 处理逻辑：
     * <ol>
     *   <li>遍历所有可用的单位枚举值</li>
     *   <li>对每个单位检查是否应包含在候选列表中</li>
     *   <li>将符合条件的单位添加到候选列表</li>
     *   <li>返回候选单位列表</li>
     * </ol>
     * <P>
     * 参数说明：
     * <ul>
     *   <li>fromUnit: 原始单位，用于确定测量类型</li>
     *   <li>options: 最佳单位选项，包含过滤条件</li>
     * </ul>
     * <P>
     * 返回值说明：
     * <ul>
     *   <li>返回符合条件的候选单位列表</li>
     * </ul>
     * <P>
     * 异常情况：
     * <ul>
     *   <li>无异常情况</li>
     * </ul>
     * 
     * @param fromUnit 原始单位
     * @param options 最佳单位选项
     * @return 符合条件的候选单位列表
     */
    private List<UnitEnum> getCandidateUnits(UnitEnum fromUnit, ToBestOptions options) {
        List<UnitEnum> candidates = new ArrayList<>();
        
        for (UnitEnum unit : UnitEnum.values()) {
            if (shouldIncludeUnit(unit, fromUnit, options)) {
                candidates.add(unit);
            }
        }
        
        return candidates;
    }
    
    /**
     * 判断是否应该包含指定单位
     * <P>
     * 详细描述方法的功能、算法逻辑或业务流程
     * <P>
     * 处理逻辑：
     * <ol>
     *   <li>检查单位是否属于同一测量类型</li>
     *   <li>检查单位是否被配置排除</li>
     *   <li>检查单位所属测量类型是否在部分加载范围内</li>
     *   <li>检查单位是否在选项的排除列表中</li>
     *   <li>检查单位是否符合指定的系统要求</li>
     *   <li>如果所有条件都满足，则返回true，否则返回false</li>
     * </ol>
     * <P>
     * 参数说明：
     * <ul>
     *   <li>unit: 待判断的单位</li>
     *   <li>fromUnit: 原始单位，用于比较测量类型</li>
     *   <li>options: 最佳单位选项，包含过滤条件</li>
     * </ul>
     * <P>
     * 返回值说明：
     * <ul>
     *   <li>true: 单位符合所有条件，应该包含在候选列表中</li>
     *   <li>false: 单位不符合条件，不应该包含在候选列表中</li>
     * </ul>
     * <P>
     * 异常情况：
     * <ul>
     *   <li>无异常情况</li>
     * </ul>
     * 
     * @param unit 待判断的单位
     * @param fromUnit 原始单位
     * @param options 最佳单位选项
     * @return 如果应该包含返回true，否则返回false
     */
    private boolean shouldIncludeUnit(UnitEnum unit, UnitEnum fromUnit, ToBestOptions options) {
        if (!unit.getMeasure().equals(fromUnit.getMeasure())) {
            return false;
        }
        
        if (config.shouldExcludeUnit(unit)) {
            return false;
        }
        
        if (config.isPartialLoading() && !config.shouldLoadMeasure(unit.getMeasure())) {
            return false;
        }
        
        if (options.getExclude() != null && options.getExclude().contains(unit)) {
            return false;
        }
        
        if (options.getSystem() != null && !unit.getSystem().equals(options.getSystem())) {
            return false;
        }
        
        return true;
    }
    
    /**
     * 从候选单位中查找最佳单位
     * <P>
     * 详细描述方法的功能、算法逻辑或业务流程
     * <P>
     * 处理逻辑：
     * <ol>
     *   <li>获取目标值（来自选项的截止数值或默认为1）</li>
     *   <li>初始化合适的转换结果列表</li>
     *   <li>遍历所有候选单位，对每个单位执行转换操作</li>
     *   <li>计算转换结果的绝对值</li>
     *   <li>根据条件判断是否将结果加入合适结果列表（值大于等于目标值，或在合理范围内）</li>
     *   <li>如果合适结果列表为空，则返回原始单位的转换结果</li>
     *   <li>选择最接近目标值的转换结果作为最佳结果</li>
     *   <li>返回最佳转换结果</li>
     * </ol>
     * <P>
     * 参数说明：
     * <ul>
     *   <li>value: 待转换的值</li>
     *   <li>fromUnit: 原始单位</li>
     *   <li>candidates: 候选单位列表</li>
     *   <li>options: 最佳单位选项，包含目标值等参数</li>
     * </ul>
     * <P>
     * 返回值说明：
     * <ul>
     *   <li>返回包含最佳单位和转换值的结果</li>
     * </ul>
     * <P>
     * 异常情况：
     * <ul>
     *   <li>转换过程中的异常会被捕获并忽略</li>
     * </ul>
     * 
     * @param value 待转换的值
     * @param fromUnit 原始单位
     * @param candidates 候选单位列表
     * @param options 最佳单位选项
     * @return 包含最佳单位和转换值的结果
     */
    private ConvertResult<BigDecimal> findBestCandidate(BigDecimal value, UnitEnum fromUnit, 
                                                      List<UnitEnum> candidates, ToBestOptions options) {
        BigDecimal targetValue = options.getCutOffNumber() != null ? new BigDecimal(options.getCutOffNumber()) : BigDecimal.ONE;
        int scale = 10;
        RoundingMode roundingMode = RoundingMode.HALF_UP;
        
        List<ConvertResult<BigDecimal>> suitableResults = new ArrayList<>();
        ConvertResult<BigDecimal> fromUnitResult = null;
        
        for (UnitEnum unit : candidates) {
            try {
                ConvertResult<BigDecimal> result = conversionService.convert(value, fromUnit, unit);
                BigDecimal absoluteValue = result.getValue().abs();
                
                if (unit.equals(fromUnit)) {
                    fromUnitResult = result;
                }
                
                // 添加到合适结果的条件：值大于等于目标值，或值在合理范围内（0.1到1000之间，避免过小或过大）
                if (absoluteValue.compareTo(targetValue) >= 0 || 
                    (absoluteValue.compareTo(new BigDecimal("0.1")) >= 0 && 
                     absoluteValue.compareTo(new BigDecimal("1000")) <= 0)) {
                    suitableResults.add(result);
                }
            } catch (Exception e) {
                // 忽略转换异常
            }
        }
        
        if (suitableResults.isEmpty()) {
            return conversionService.convert(value, fromUnit, fromUnit);
        }
        
        // 选择最接近目标值的单位，如果没有明确的最优选择，则选择最小的合理值
        return suitableResults.stream()
            .min(Comparator.comparing(r -> absDifference(r.getValue(), targetValue)))
            .orElse(suitableResults.get(0));
    }
    
    /**
     * 计算数值与目标值的绝对差
     * <P>
     * 详细描述方法的功能、算法逻辑或业务流程
     * <P>
     * 处理逻辑：
     * <ol>
     *   <li>计算输入数值的绝对值</li>
     *   <li>从绝对值中减去目标值</li>
     *   <li>计算结果的绝对值以获得差值</li>
     *   <li>返回绝对差值</li>
     * </ol>
     * <P>
     * 参数说明：
     * <ul>
     *   <li>val: 输入数值</li>
     *   <li>target: 目标值</li>
     * </ul>
     * <P>
     * 返回值说明：
     * <ul>
     *   <li>返回数值与目标值的绝对差</li>
     * </ul>
     * <P>
     * 异常情况：
     * <ul>
     *   <li>当任一参数为null时，可能抛出NullPointerException</li>
     * </ul>
     * 
     * @param val 数值
     * @param target 目标值
     * @return 绝对差值
     */
    private BigDecimal absDifference(BigDecimal val, BigDecimal target) {
        return val.abs().subtract(target).abs();
    }
}