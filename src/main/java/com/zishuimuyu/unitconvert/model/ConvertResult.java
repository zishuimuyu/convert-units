package com.zishuimuyu.unitconvert.model;

import java.math.BigDecimal;

/**
 * 单位转换结果模型类
 * 
 * 该类封装了单位转换操作的结果，包括转换后的数值、单位和描述信息
 * 
 * @author 紫水木鱼
 * @version 1.0.0
 * @since 2026-04-24
 * 
 * @param <T> 转换结果的数值类型，默认为BigDecimal
 */
public class ConvertResult<T> {
    
    /**
     * 转换后的数值
     */
    private T value;
    
    /**
     * 目标单位的缩写
     */
    private String unit;
    
    /**
     * 目标单位的单数形式名称
     */
    private String singular;
    
    /**
     * 目标单位的复数形式名称
     */
    private String plural;

    /**
     * 默认构造函数
     */
    public ConvertResult() {
    }

    /**
     * 带参数的构造函数
     * 
     * @param value 转换后的数值
     * @param unit 目标单位的缩写
     * @param singular 目标单位的单数形式名称
     * @param plural 目标单位的复数形式名称
     */
    public ConvertResult(T value, String unit, String singular, String plural) {
        this.value = value;
        this.unit = unit;
        this.singular = singular;
        this.plural = plural;
    }

    /**
     * 获取转换后的数值
     * 
     * @return 转换后的数值
     */
    public T getValue() {
        return value;
    }

    /**
     * 设置转换后的数值
     * 
     * @param value 转换后的数值
     */
    public void setVal(T value) {
        this.value = value;
    }

    /**
     * 获取目标单位的缩写
     * 
     * @return 目标单位的缩写
     */
    public String getUnit() {
        return unit;
    }

    /**
     * 设置目标单位的缩写
     * 
     * @param unit 目标单位的缩写
     */
    public void setUnit(String unit) {
        this.unit = unit;
    }

    /**
     * 获取目标单位的单数形式名称
     * 
     * @return 目标单位的单数形式名称
     */
    public String getSingular() {
        return singular;
    }

    /**
     * 设置目标单位的单数形式名称
     * 
     * @param singular 目标单位的单数形式名称
     */
    public void setSingular(String singular) {
        this.singular = singular;
    }

    /**
     * 获取目标单位的复数形式名称
     * 
     * @return 目标单位的复数形式名称
     */
    public String getPlural() {
        return plural;
    }

    /**
     * 设置目标单位的复数形式名称
     * 
     * @param plural 目标单位的复数形式名称
     */
    public void setPlural(String plural) {
        this.plural = plural;
    }

    /**
     * 重写toString方法，提供对象的字符串表示
     * 
     * @return 对象的字符串表示
     */
    @Override
    public String toString() {
        return "ConvertResult{" +
                "value=" + value +
                ", unit='" + unit + '\'' +
                ", singular='" + singular + '\'' +
                ", plural='" + plural + '\'' +
                '}';
    }
    
    /**
     * 提供友好的字符串表示，格式为 "数值 单位"
     * 
     * @return 友好的字符串表示
     */
    public String toFriendlyString() {
        return getFormattedValue() + " " + unit;
    }

    /**
     * 获取格式化后的数值字符串，避免科学计数法
     * 
     * @return 格式化后的数值字符串
     */
    public String getFormattedValue() {
        if (value instanceof BigDecimal) {
            BigDecimal bd = (BigDecimal) value;
            // 使用 toPlainString() 避免科学计数法
            return bd.stripTrailingZeros().toPlainString();
        }
        return value.toString();
    }
}