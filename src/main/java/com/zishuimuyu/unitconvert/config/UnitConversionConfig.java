package com.zishuimuyu.unitconvert.config;

import com.zishuimuyu.unitconvert.model.UnitEnum;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 单位转换配置类
 * 
 * 用于配置单位转换服务的行为，包括：
 * - 指定需要加载的测量类型（部分加载）
 * - 指定需要排除的单位
 * - 自定义配置选项
 * 
 * @author 紫水木鱼
 * @version 1.0.0
 * @since 2026-04-24
 */
public class UnitConversionConfig {
    
    /**
     * 需要加载的测量类型列表
     * 如果为null或空，则加载所有测量类型
     */
    private final Set<String> includedMeasures;
    
    /**
     * 需要排除的单位列表
     */
    private final Set<UnitEnum> excludedUnits;
    
    /**
     * 是否启用部分加载模式
     */
    private final boolean partialLoading;
    
    /**
     * 配置的不可变视图（用于外部访问）
     */
    private final Set<String> immutableIncludedMeasures;
    private final Set<UnitEnum> immutableExcludedUnits;
    
    /**
     * 私有构造函数，使用Builder创建
     */
    private UnitConversionConfig(Builder builder) {
        this.includedMeasures = builder.includedMeasures.isEmpty() ? null : 
            Collections.unmodifiableSet(new HashSet<>(builder.includedMeasures));
        this.excludedUnits = builder.excludedUnits.isEmpty() ? null : 
            Collections.unmodifiableSet(new HashSet<>(builder.excludedUnits));
        this.partialLoading = builder.partialLoading;
        this.immutableIncludedMeasures = this.includedMeasures;
        this.immutableExcludedUnits = this.excludedUnits;
    }
    
    /**
     * 获取需要加载的测量类型列表
     * 
     * @return 测量类型集合，如果为null表示加载所有
     */
    public Set<String> getIncludedMeasures() {
        return immutableIncludedMeasures;
    }
    
    /**
     * 获取需要排除的单位列表
     * 
     * @return 排除的单位集合
     */
    public Set<UnitEnum> getExcludedUnits() {
        return immutableExcludedUnits;
    }
    
    /**
     * 检查是否启用部分加载模式
     * 
     * @return 如果启用部分加载返回true
     */
    public boolean isPartialLoading() {
        return partialLoading;
    }
    
    /**
     * 检查指定的测量类型是否应该被加载
     * 
     * @param measure 测量类型名称
     * @return 如果应该加载返回true
     */
    public boolean shouldLoadMeasure(String measure) {
        // 如果不是部分加载模式，加载所有测量类型
        if (!partialLoading || includedMeasures == null || includedMeasures.isEmpty()) {
            return true;
        }
        
        // 检查是否在包含列表中（大小写不敏感）
        String normalizedMeasure = measure != null ? measure.toLowerCase() : null;
        return normalizedMeasure != null && includedMeasures.contains(normalizedMeasure);
    }
    
    /**
     * 检查指定的单位是否应该被排除
     * 
     * @param unit 单位枚举
     * @return 如果应该排除返回true
     */
    public boolean shouldExcludeUnit(UnitEnum unit) {
        if (unit == null || excludedUnits == null || excludedUnits.isEmpty()) {
            return false;
        }
        return excludedUnits.contains(unit);
    }
    
    /**
     * 获取配置的字符串表示
     * 
     * @return 配置的字符串表示
     */
    @Override
    public String toString() {
        return "UnitConversionConfig{" +
                "partialLoading=" + partialLoading +
                ", includedMeasures=" + (includedMeasures != null ? includedMeasures : "all") +
                ", excludedUnits=" + (excludedUnits != null ? excludedUnits.size() + " units" : "none") +
                '}';
    }
    
    /**
     * 创建默认配置（加载所有测量类型）
     * 
     * @return 默认配置实例
     */
    public static UnitConversionConfig defaults() {
        return new Builder().build();
    }
    
    /**
     * 创建部分加载配置
     * 
     * @param measures 需要加载的测量类型列表
     * @return 部分加载配置实例
     */
    public static UnitConversionConfig partialLoading(String... measures) {
        return new Builder()
            .partialLoading(true)
            .includeMeasures(measures)
            .build();
    }
    
    /**
     * 配置构建器
     * 
     * 使用构建器模式创建配置实例
     */
    public static class Builder {
        
        private Set<String> includedMeasures = new HashSet<>();
        private Set<UnitEnum> excludedUnits = new HashSet<>();
        private boolean partialLoading = false;
        
        /**
         * 设置部分加载模式
         * 
         * @param partialLoading 是否启用部分加载
         * @return 构建器实例
         */
        public Builder partialLoading(boolean partialLoading) {
            this.partialLoading = partialLoading;
            return this;
        }
        
        /**
         * 添加需要包含的测量类型
         * 
         * @param measures 测量类型数组
         * @return 构建器实例
         */
        public Builder includeMeasures(String... measures) {
            if (measures != null) {
                for (String measure : measures) {
                    if (measure != null && !measure.trim().isEmpty()) {
                        this.includedMeasures.add(measure.trim().toLowerCase());
                    }
                }
            }
            return this;
        }
        
        /**
         * 添加需要包含的测量类型
         * 
         * @param measures 测量类型列表
         * @return 构建器实例
         */
        public Builder includeMeasures(List<String> measures) {
            if (measures != null) {
                for (String measure : measures) {
                    if (measure != null && !measure.trim().isEmpty()) {
                        this.includedMeasures.add(measure.trim().toLowerCase());
                    }
                }
            }
            return this;
        }
        
        /**
         * 添加需要排除的单位
         * 
         * @param units 单位数组
         * @return 构建器实例
         */
        public Builder excludeUnits(UnitEnum... units) {
            if (units != null) {
                this.excludedUnits.addAll(Arrays.asList(units));
            }
            return this;
        }
        
        /**
         * 添加需要排除的单位
         * 
         * @param units 单位列表
         * @return 构建器实例
         */
        public Builder excludeUnits(List<UnitEnum> units) {
            if (units != null) {
                this.excludedUnits.addAll(units);
            }
            return this;
        }
        
        /**
         * 构建配置实例
         * 
         * @return 配置实例
         */
        public UnitConversionConfig build() {
            UnitConversionConfig config = new UnitConversionConfig(this);
            
            // 验证配置
            UnitConversionConfigValidator.ValidationResult validationResult = 
                UnitConversionConfigValidator.validate(config);
            
            // 如果有错误，抛出异常
            if (validationResult.hasErrors()) {
                throw new IllegalStateException(
                    "配置验证失败:\n" + validationResult.getFormattedErrors()
                );
            }
            
            // 如果有警告，输出到控制台
            if (validationResult.hasWarnings()) {
                System.err.println("配置验证警告:\n" + validationResult.getFormattedWarnings());
            }
            
            return config;
        }
    }
}