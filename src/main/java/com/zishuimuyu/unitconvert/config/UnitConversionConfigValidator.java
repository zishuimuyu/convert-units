package com.zishuimuyu.unitconvert.config;

import com.zishuimuyu.unitconvert.model.UnitEnum;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 单位转换配置验证器
 * 
 * 用于验证单位转换配置的有效性，提供详细的错误信息和警告
 * 
 * @author 紫水木鱼
 * @version 1.0.0
 * @since 2026-04-24
 */
public class UnitConversionConfigValidator {
    
    /**
     * 验证配置的有效性
     * 
     * @param config 要验证的配置
     * @return 验证结果，包含错误和警告列表
     */
    public static ValidationResult validate(UnitConversionConfig config) {
        ValidationResult result = new ValidationResult();
        
        if (config == null) {
            result.addError("配置不能为null");
            return result;
        }
        
        // 验证部分加载配置
        validatePartialLoading(config, result);
        
        // 验证排除的单位
        validateExcludedUnits(config, result);
        
        // 验证包含的测量类型
        validateIncludedMeasures(config, result);
        
        return result;
    }
    
    /**
     * 验证部分加载配置
     */
    private static void validatePartialLoading(UnitConversionConfig config, ValidationResult result) {
        if (config.isPartialLoading()) {
            Set<String> includedMeasures = config.getIncludedMeasures();
            
            if (includedMeasures == null || includedMeasures.isEmpty()) {
                result.addWarning("启用了部分加载模式，但没有指定任何包含的测量类型");
            }
        }
    }
    
    /**
     * 验证排除的单位
     */
    private static void validateExcludedUnits(UnitConversionConfig config, ValidationResult result) {
        Set<UnitEnum> excludedUnits = config.getExcludedUnits();
        
        if (excludedUnits == null || excludedUnits.isEmpty()) {
            return;
        }
        
        // 检查排除的单位是否有效
        Set<String> validMeasures = getValidMeasures();
        for (UnitEnum unit : excludedUnits) {
            if (!validMeasures.contains(unit.getMeasure())) {
                result.addWarning("排除的单位 '" + unit.getAbbr() + "' 属于未知的测量类型: " + unit.getMeasure());
            }
        }
    }
    
    /**
     * 验证包含的测量类型
     */
    private static void validateIncludedMeasures(UnitConversionConfig config, ValidationResult result) {
        if (!config.isPartialLoading()) {
            return;
        }
        
        Set<String> includedMeasures = config.getIncludedMeasures();
        if (includedMeasures == null || includedMeasures.isEmpty()) {
            return;
        }
        
        // 获取所有有效的测量类型
        Set<String> validMeasures = getValidMeasures();
        
        // 检查包含的测量类型是否有效
        for (String measure : includedMeasures) {
            if (!validMeasures.contains(measure)) {
                result.addError("无效的测量类型: '" + measure + "'");
            }
        }
        
        // 提供可用的测量类型建议
        if (result.hasErrors()) {
            result.addSuggestion("可用的测量类型: " + String.join(", ", validMeasures));
        }
    }
    
    /**
     * 获取所有有效的测量类型
     * 
     * @return 所有有效的测量类型集合
     */
    private static Set<String> getValidMeasures() {
        Set<String> measures = new HashSet<>();
        for (UnitEnum unit : UnitEnum.values()) {
            measures.add(unit.getMeasure());
        }
        return measures;
    }
    
    /**
     * 验证结果类
     */
    public static class ValidationResult {
        private final List<String> errors = new ArrayList<>();
        private final List<String> warnings = new ArrayList<>();
        private final List<String> suggestions = new ArrayList<>();
        
        /**
         * 添加错误信息
         */
        public void addError(String error) {
            errors.add(error);
        }
        
        /**
         * 添加警告信息
         */
        public void addWarning(String warning) {
            warnings.add(warning);
        }
        
        /**
         * 添加建议信息
         */
        public void addSuggestion(String suggestion) {
            suggestions.add(suggestion);
        }
        
        /**
         * 检查是否有错误
         */
        public boolean hasErrors() {
            return !errors.isEmpty();
        }
        
        /**
         * 检查是否有警告
         */
        public boolean hasWarnings() {
            return !warnings.isEmpty();
        }
        
        /**
         * 获取错误列表
         */
        public List<String> getErrors() {
            return new ArrayList<>(errors);
        }
        
        /**
         * 获取警告列表
         */
        public List<String> getWarnings() {
            return new ArrayList<>(warnings);
        }
        
        /**
         * 获取建议列表
         */
        public List<String> getSuggestions() {
            return new ArrayList<>(suggestions);
        }
        
        /**
         * 检查配置是否有效
         */
        public boolean isValid() {
            return !hasErrors();
        }
        
        /**
         * 获取格式化的错误信息
         */
        public String getFormattedErrors() {
            if (errors.isEmpty()) {
                return "配置验证通过，没有错误";
            }
            
            StringBuilder sb = new StringBuilder("配置验证失败，发现以下错误:\n");
            for (int i = 0; i < errors.size(); i++) {
                sb.append("  ").append(i + 1).append(". ").append(errors.get(i)).append("\n");
            }
            return sb.toString();
        }
        
        /**
         * 获取格式化的警告信息
         */
        public String getFormattedWarnings() {
            if (warnings.isEmpty()) {
                return "";
            }
            
            StringBuilder sb = new StringBuilder("配置验证发现以下警告:\n");
            for (int i = 0; i < warnings.size(); i++) {
                sb.append("  ").append(i + 1).append(". ").append(warnings.get(i)).append("\n");
            }
            return sb.toString();
        }
        
        /**
         * 获取完整的格式化信息
         */
        public String getFormattedMessage() {
            StringBuilder sb = new StringBuilder();
            
            if (hasErrors()) {
                sb.append(getFormattedErrors()).append("\n");
            }
            
            if (hasWarnings()) {
                sb.append(getFormattedWarnings()).append("\n");
            }
            
            if (!suggestions.isEmpty()) {
                sb.append("建议:\n");
                for (int i = 0; i < suggestions.size(); i++) {
                    sb.append("  - ").append(suggestions.get(i)).append("\n");
                }
            }
            
            return sb.toString().trim();
        }
    }
}