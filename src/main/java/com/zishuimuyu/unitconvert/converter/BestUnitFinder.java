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
 * 
 * 根据给定的值和原始单位，找到最适合显示的单位
 * 考虑因素包括数值大小、用户偏好、系统限制等
 * 
 * 设计原理：
 * 1. 遍历同一测量类型的所有可用单位
 * 2. 排除不符合条件的单位（如被配置排除、不在加载范围内等）
 * 3. 对剩余单位进行转换计算
 * 4. 根据数值范围和目标值选择最佳单位
 * 
 * @author 紫水木鱼
 * @version 1.0.0
 * @since 2026-04-25
 */
public class BestUnitFinder {
    
    /**
     * 单位转换服务接口
     * 
     * 用于执行实际的单位转换操作
     */
    private final IUnitConversionService conversionService;
    
    /**
     * 单位转换配置
     * 
     * 用于控制最佳单位选择的行为
     */
    private final UnitConversionConfig config;
    
    /**
     * 构造函数
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
     * 
     * 根据过滤条件筛选出符合条件的候选单位
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
     * 
     * 根据数值范围和目标值选择最合适的单位
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
     * 
     * @param val 数值
     * @param target 目标值
     * @return 绝对差值
     */
    private BigDecimal absDifference(BigDecimal val, BigDecimal target) {
        return val.abs().subtract(target).abs();
    }
}