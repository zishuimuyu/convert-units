package com.zishuimuyu.unitconvert.config;

import com.zishuimuyu.unitconvert.model.UnitEnum;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 单位转换配置工具类
 * 
 * 提供便捷的方法来创建、验证和管理单位转换配置
 * 
 * @author 紫水木鱼
 * @version 1.0.0
 * @since 2026-04-24
 */
public class UnitConversionConfigUtils {
    
    /**
     * 获取所有可用的测量类型
     * 
     * @return 所有可用的测量类型集合
     */
    public static Set<String> getAvailableMeasures() {
        Set<String> measures = new HashSet<>();
        for (UnitEnum unit : UnitEnum.values()) {
            measures.add(unit.getMeasure());
        }
        return measures;
    }
    
    /**
     * 获取指定测量类型的所有单位
     * 
     * @param measure 测量类型
     * @return 该测量类型的所有单位集合
     */
    public static Set<UnitEnum> getUnitsByMeasure(String measure) {
        Set<UnitEnum> units = new HashSet<>();
        for (UnitEnum unit : UnitEnum.values()) {
            if (unit.getMeasure().equalsIgnoreCase(measure)) {
                units.add(unit);
            }
        }
        return units;
    }
    
    /**
     * 创建只包含指定测量类型的配置
     * 
     * @param measures 要包含的测量类型数组
     * @return 部分加载配置
     */
    public static UnitConversionConfig createPartialLoadingConfig(String... measures) {
        return UnitConversionConfig.partialLoading(measures);
    }
    
    /**
     * 创建排除指定单位的配置
     * 
     * @param units 要排除的单位数组
     * @return 排除单位的配置
     */
    public static UnitConversionConfig createExcludedUnitsConfig(UnitEnum... units) {
        return new UnitConversionConfig.Builder()
            .excludeUnits(units)
            .build();
    }
    
    /**
     * 创建组合配置（部分加载 + 单位排除）
     * 
     * @param includedMeasures 要包含的测量类型数组
     * @param excludedUnits 要排除的单位数组
     * @return 组合配置
     */
    public static UnitConversionConfig createCombinedConfig(String[] includedMeasures, UnitEnum[] excludedUnits) {
        return new UnitConversionConfig.Builder()
            .partialLoading(true)
            .includeMeasures(includedMeasures)
            .excludeUnits(excludedUnits)
            .build();
    }
    
    /**
     * 创建长度单位专用配置
     * 
     * @return 只包含长度单位的配置
     */
    public static UnitConversionConfig createLengthOnlyConfig() {
        return createPartialLoadingConfig("length");
    }
    
    /**
     * 创建质量单位专用配置
     * 
     * @return 只包含质量单位的配置
     */
    public static UnitConversionConfig createMassOnlyConfig() {
        return createPartialLoadingConfig("mass");
    }
    
    /**
     * 创建体积单位专用配置
     * 
     * @return 只包含体积单位的配置
     */
    public static UnitConversionConfig createVolumeOnlyConfig() {
        return createPartialLoadingConfig("volume");
    }
    
    /**
     * 创建常用单位配置（长度、质量、体积）
     * 
     * @return 常用单位配置
     */
    public static UnitConversionConfig createCommonUnitsConfig() {
        return createPartialLoadingConfig("length", "mass", "volume");
    }
    
    /**
     * 创建排除英制单位的配置
     * 
     * @return 排除英制单位的配置
     */
    public static UnitConversionConfig createMetricOnlyConfig() {
        Set<UnitEnum> imperialUnits = new HashSet<>();
        for (UnitEnum unit : UnitEnum.values()) {
            if ("imperial".equalsIgnoreCase(unit.getSystem())) {
                imperialUnits.add(unit);
            }
        }
        return new UnitConversionConfig.Builder()
            .excludeUnits(imperialUnits.toArray(new UnitEnum[0]))
            .build();
    }
    
    /**
     * 验证配置并打印结果
     * 
     * @param config 要验证的配置
     * @return 验证结果
     */
    public static UnitConversionConfigValidator.ValidationResult validateAndPrint(UnitConversionConfig config) {
        UnitConversionConfigValidator.ValidationResult result = 
            UnitConversionConfigValidator.validate(config);
        
        if (result.isValid()) {
            System.out.println("✅ 配置验证通过");
            if (result.hasWarnings()) {
                System.out.println("⚠️  警告信息:");
                for (String warning : result.getWarnings()) {
                    System.out.println("   - " + warning);
                }
            }
        } else {
            System.out.println("❌ 配置验证失败");
            System.out.println(result.getFormattedErrors());
        }
        
        return result;
    }
    
    /**
     * 打印配置信息
     * 
     * @param config 要打印的配置
     */
    public static void printConfigInfo(UnitConversionConfig config) {
        System.out.println("=== 单位转换配置信息 ===");
        System.out.println("部分加载模式: " + (config.isPartialLoading() ? "启用" : "禁用"));
        
        if (config.isPartialLoading()) {
            Set<String> measures = config.getIncludedMeasures();
            if (measures != null && !measures.isEmpty()) {
                System.out.println("包含的测量类型: " + String.join(", ", measures));
            } else {
                System.out.println("包含的测量类型: 无");
            }
        }
        
        Set<UnitEnum> excludedUnits = config.getExcludedUnits();
        if (excludedUnits != null && !excludedUnits.isEmpty()) {
            System.out.println("排除的单位数量: " + excludedUnits.size());
            System.out.println("排除的单位: " + getUnitNames(excludedUnits));
        } else {
            System.out.println("排除的单位: 无");
        }
        
        System.out.println("========================");
    }
    
    /**
     * 获取单位的名称列表
     * 
     * @param units 单位集合
     * @return 单位名称列表
     */
    private static String getUnitNames(Set<UnitEnum> units) {
        StringBuilder sb = new StringBuilder();
        boolean first = true;
        for (UnitEnum unit : units) {
            if (!first) {
                sb.append(", ");
            }
            sb.append(unit.getAbbr()).append(" (").append(unit.getSingular()).append(")");
            first = false;
        }
        return sb.toString();
    }
    
    /**
     * 获取配置的统计信息
     * 
     * @param config 配置对象
     * @return 统计信息字符串
     */
    public static String getConfigStatistics(UnitConversionConfig config) {
        StringBuilder sb = new StringBuilder();
        
        if (config.isPartialLoading()) {
            Set<String> includedMeasures = config.getIncludedMeasures();
            int includedMeasureCount = (includedMeasures != null) ? includedMeasures.size() : 0;
            
            int totalUnits = 0;
            int excludedUnitsCount = 0;
            
            if (includedMeasures != null && !includedMeasures.isEmpty()) {
                for (String measure : includedMeasures) {
                    totalUnits += getUnitsByMeasure(measure).size();
                }
            }
            
            if (config.getExcludedUnits() != null) {
                excludedUnitsCount = config.getExcludedUnits().size();
                totalUnits -= excludedUnitsCount;
            }
            
            sb.append("配置统计:\n");
            sb.append("- 包含测量类型: ").append(includedMeasureCount).append("\n");
            sb.append("- 可用单位总数: ").append(totalUnits).append("\n");
            sb.append("- 排除单位数量: ").append(excludedUnitsCount).append("\n");
            sb.append("- 内存节省: 约").append(calculateMemorySavings(config)).append("%");
        } else {
            int totalUnits = UnitEnum.values().length;
            int excludedUnitsCount = (config.getExcludedUnits() != null) ? 
                config.getExcludedUnits().size() : 0;
            
            sb.append("配置统计:\n");
            sb.append("- 包含测量类型: 全部 (").append(getAvailableMeasures().size()).append(")\n");
            sb.append("- 可用单位总数: ").append(totalUnits - excludedUnitsCount).append("\n");
            sb.append("- 排除单位数量: ").append(excludedUnitsCount).append("\n");
            sb.append("- 内存节省: 约").append(calculateMemorySavings(config)).append("%");
        }
        
        return sb.toString();
    }
    
    /**
     * 计算内存节省百分比
     * 
     * @param config 配置对象
     * @return 节省的内存百分比
     */
    private static int calculateMemorySavings(UnitConversionConfig config) {
        int totalUnits = UnitEnum.values().length;
        int availableUnits = totalUnits;
        
        if (config.isPartialLoading()) {
            availableUnits = 0;
            Set<String> includedMeasures = config.getIncludedMeasures();
            if (includedMeasures != null && !includedMeasures.isEmpty()) {
                for (String measure : includedMeasures) {
                    availableUnits += getUnitsByMeasure(measure).size();
                }
            }
        }
        
        if (config.getExcludedUnits() != null) {
            availableUnits -= config.getExcludedUnits().size();
        }
        
        return Math.round((1.0f - (float)availableUnits / totalUnits) * 100);
    }
    
    /**
     * 创建配置建议
     * 
     * @param useCase 使用场景
     * @return 建议的配置
     */
    public static UnitConversionConfig suggestConfig(String useCase) {
        switch (useCase.toLowerCase()) {
            case "international":
            case "国际化":
                return createCommonUnitsConfig();
                
            case "metric":
            case "公制":
                return createMetricOnlyConfig();
                
            case "scientific":
            case "科学":
                return UnitConversionConfig.defaults();
                
            case "simple":
            case "简单":
                return createLengthOnlyConfig();
                
            default:
                System.out.println("未知的使用场景: " + useCase);
                System.out.println("可用的场景: international, metric, scientific, simple");
                return UnitConversionConfig.defaults();
        }
    }
}