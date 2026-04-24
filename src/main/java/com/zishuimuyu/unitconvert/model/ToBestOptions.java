package com.zishuimuyu.unitconvert.model;

import java.util.Collections;
import java.util.List;

/**
 * toBest方法的选项类
 * 
 * 用于配置自动选择最佳单位时的行为
 * 
 * @author 紫水木鱼
 * @version 1.0.0
 * @since 2026-04-24
 */
public class ToBestOptions {
    
    private final List<UnitEnum> exclude;  // 要排除的单位列表
    private final Double cutOffNumber;     // 截止数值
    private final String system;           // 限制的单位系统
    
    /**
     * 构造函数
     * 
     * @param exclude 要排除的单位列表
     * @param cutOffNumber 截止数值
     * @param system 限制的单位系统
     */
    public ToBestOptions(List<UnitEnum> exclude, Double cutOffNumber, String system) {
        this.exclude = exclude != null ? exclude : Collections.emptyList();
        this.cutOffNumber = cutOffNumber != null ? cutOffNumber : null; // 默认值将在方法中确定
        this.system = system;
    }
    
    /**
     * 获取要排除的单位列表
     * 
     * @return 排除的单位列表
     */
    public List<UnitEnum> getExclude() {
        return exclude;
    }
    
    /**
     * 获取截止数值
     * 
     * @return 截止数值
     */
    public Double getCutOffNumber() {
        return cutOffNumber;
    }
    
    /**
     * 获取限制的单位系统
     * 
     * @return 单位系统
     */
    public String getSystem() {
        return system;
    }
    
    /**
     * 创建默认选项
     * 
     * @return 默认选项实例
     */
    public static ToBestOptions defaults() {
        return new ToBestOptions(null, null, null);
    }
    
    /**
     * 创建带排除单位的选项
     * 
     * @param exclude 要排除的单位列表
     * @return 选项实例
     */
    public static ToBestOptions withExclude(List<UnitEnum> exclude) {
        return new ToBestOptions(exclude, null, null);
    }
    
    /**
     * 创建带截止数值的选项
     * 
     * @param cutOffNumber 截止数值
     * @return 选项实例
     */
    public static ToBestOptions withCutOffNumber(Double cutOffNumber) {
        return new ToBestOptions(null, cutOffNumber, null);
    }
    
    /**
     * 创建带单位系统的选项
     * 
     * @param system 单位系统
     * @return 选项实例
     */
    public static ToBestOptions withSystem(String system) {
        return new ToBestOptions(null, null, system);
    }
}