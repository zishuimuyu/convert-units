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
 * 
 * 提供各种物理量单位之间的转换功能
 * 支持公制、英制等多种单位系统
 * 使用BigDecimal保证计算精度
 * 
 * 实现原理：
 * 1. 使用策略模式管理不同类型的转换逻辑
 * 2. 标准转换策略处理常规单位转换
 * 3. 温度转换策略处理温度单位的特殊转换
 * 4. 通过转换因子和系统间比率实现精确转换
 * 
 * @author 紫水木鱼
 * @version 1.0.0
 * @since 2026-04-24
 */
public class UnitConversionServiceImpl implements IUnitConversionService {

    /**
     * 转换策略管理器
     * 
     * 管理不同类型的转换策略，如标准转换、温度转换等
     */
    private final ConversionStrategyManager strategyManager;
    
    /**
     * 最佳单位查找器
     * 
     * 用于查找最适合显示的单位
     */
    private final BestUnitFinder bestUnitFinder;
    
    /**
     * 单位转换配置
     * 
     * 用于控制单位转换服务的行为，如部分加载、单位排除等
     */
    private final UnitConversionConfig config;

    /**
     * 默认构造函数，使用默认配置
     */
    public UnitConversionServiceImpl() {
        this(UnitConversionConfig.defaults());
    }
    
    /**
     * 带配置的构造函数
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

    @Override
    public ConvertResult<BigDecimal> convert(BigDecimal value, UnitEnum fromUnit, UnitEnum toUnit) {
        return strategyManager.convert(value, fromUnit, toUnit);
    }

    @Override
    public ConvertResult<BigDecimal> convertToBest(BigDecimal value, UnitEnum fromUnit) {
        return bestUnitFinder.findBestUnit(value, fromUnit, null);
    }

    @Override
    public ConvertResult<BigDecimal> convertToBest(BigDecimal value, UnitEnum fromUnit, ToBestOptions options) {
        return bestUnitFinder.findBestUnit(value, fromUnit, options);
    }

    @Override
    public boolean canConvertBetween(UnitEnum fromUnit, UnitEnum toUnit) {
        return strategyManager.canConvertBetween(fromUnit, toUnit);
    }

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

    @Override
    public IUnitConversionService from(BigDecimal value, UnitEnum fromUnit) {
        return new com.zishuimuyu.unitconvert.chain.ChainConversionContext(this, value, fromUnit);
    }

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

    @Override
    public List<String> measures() {
        return getSupportedMeasures();
    }
    
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

    @Override
    public ConversionBuilder buildConversion(BigDecimal value, UnitEnum fromUnit) {
        return new ConversionBuilder(this, value, fromUnit);
    }
}