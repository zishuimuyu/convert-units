package com.zishuimuyu.unitconvert.config;

import com.zishuimuyu.unitconvert.model.UnitEnum;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 单位转换配置属性类
 * 
 * 用于从Spring Boot配置文件中读取单位转换的配置
 * 支持通过application.yml或application.properties配置
 * 
 * 配置示例：
 * unitconvert:
 *   partial-loading: true
 *   included-measures:
 *     - length
 *     - mass
 *     - volume
 *   excluded-units:
 *     - NMI
 *     - FATHOM
 * 
 * @author 紫水木鱼
 * @version 1.0.0
 * @since 2026-04-24
 */
@Component
@ConfigurationProperties(prefix = "unitconvert")
public class UnitConversionProperties {
    
    /**
     * 是否启用部分加载模式
     * 
     * 默认为false，加载所有测量类型
     */
    private boolean partialLoading = false;
    
    /**
     * 需要包含的测量类型列表
     * 
     * 只在partialLoading=true时生效
     */
    private List<String> includedMeasures = new ArrayList<>();
    
    /**
     * 需要排除的单位缩写列表
     * 
     * 支持通过单位缩写排除特定单位
     */
    private List<String> excludedUnits = new ArrayList<>();
    
    /**
     * 获取是否启用部分加载模式
     * 
     * @return 如果启用部分加载返回true
     */
    public boolean isPartialLoading() {
        return partialLoading;
    }
    
    /**
     * 设置是否启用部分加载模式
     * 
     * @param partialLoading 是否启用部分加载
     */
    public void setPartialLoading(boolean partialLoading) {
        this.partialLoading = partialLoading;
    }
    
    /**
     * 获取需要包含的测量类型列表
     * 
     * @return 测量类型列表
     */
    public List<String> getIncludedMeasures() {
        return includedMeasures;
    }
    
    /**
     * 设置需要包含的测量类型列表
     * 
     * @param includedMeasures 测量类型列表
     */
    public void setIncludedMeasures(List<String> includedMeasures) {
        this.includedMeasures = includedMeasures;
    }
    
    /**
     * 获取需要排除的单位缩写列表
     * 
     * @return 单位缩写列表
     */
    public List<String> getExcludedUnits() {
        return excludedUnits;
    }
    
    /**
     * 设置需要排除的单位缩写列表
     * 
     * @param excludedUnits 单位缩写列表
     */
    public void setExcludedUnits(List<String> excludedUnits) {
        this.excludedUnits = excludedUnits;
    }
    
    /**
     * 将属性配置转换为UnitConversionConfig对象
     * 
     * 此方法将Spring Boot配置属性转换为可用的配置对象，
     * 支持将配置文件中的单位缩写转换为UnitEnum枚举
     * 
     * @return UnitConversionConfig配置对象
     */
    public UnitConversionConfig toUnitConversionConfig() {
        UnitConversionConfig.Builder builder = new UnitConversionConfig.Builder();
        
        // 设置部分加载模式
        builder.partialLoading(partialLoading);
        
        // 添加包含的测量类型
        if (includedMeasures != null && !includedMeasures.isEmpty()) {
            builder.includeMeasures(includedMeasures);
        }
        
        // 添加排除的单位（将缩写转换为UnitEnum）
        if (excludedUnits != null && !excludedUnits.isEmpty()) {
            List<UnitEnum> excludedUnitEnums = new ArrayList<>();
            for (String unitAbbr : excludedUnits) {
                if (unitAbbr != null && !unitAbbr.trim().isEmpty()) {
                    try {
                        // 通过缩写查找对应的UnitEnum
                        UnitEnum unit = findUnitByAbbr(unitAbbr.trim());
                        if (unit != null) {
                            excludedUnitEnums.add(unit);
                        }
                    } catch (Exception e) {
                        // 忽略无法识别的单位缩写
                        System.err.println("警告：无法识别的单位缩写: " + unitAbbr);
                    }
                }
            }
            builder.excludeUnits(excludedUnitEnums);
        }
        
        return builder.build();
    }
    
    /**
     * 通过缩写查找对应的UnitEnum
     * 
     * @param abbr 单位缩写
     * @return 对应的UnitEnum，如果找不到返回null
     */
    private UnitEnum findUnitByAbbr(String abbr) {
        for (UnitEnum unit : UnitEnum.values()) {
            if (unit.getAbbr().equalsIgnoreCase(abbr)) {
                return unit;
            }
        }
        return null;
    }
}