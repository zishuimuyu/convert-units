package com.zishuimuyu.unitconvert.service;

import com.zishuimuyu.unitconvert.model.ConvertResult;
import com.zishuimuyu.unitconvert.model.ToBestOptions;
import com.zishuimuyu.unitconvert.model.UnitEnum;
import com.zishuimuyu.unitconvert.model.UnitDescription;

import java.math.BigDecimal;
import java.util.List;

/**
 * 单位转换服务接口
 * 
 * 定义了单位转换的基本操作规范
 * 
 * @author 紫水木鱼
 * @version 1.0.0
 * @since 2026-04-24
 */
public interface IUnitConversionService {
    
    /**
     * 将指定数值从源单位转换为目标单位
     * 
     * @param value 待转换的数值
     * @param fromUnit 源单位
     * @param toUnit 目标单位
     * @return 转换结果对象
     * @throws IllegalArgumentException 当单位不兼容或参数无效时抛出
     */
    ConvertResult<BigDecimal> convert(BigDecimal value, UnitEnum fromUnit, UnitEnum toUnit);
    
    /**
     * 检查两个单位是否可以相互转换
     * 
     * @param fromUnit 源单位
     * @param toUnit 目标单位
     * @return 如果可以转换返回true，否则返回false
     */
    boolean canConvertBetween(UnitEnum fromUnit, UnitEnum toUnit);
    
    /**
     * 获取指定单位的详细信息
     * 
     * @param unit 单位枚举
     * @return 单位详细信息
     */
    UnitDescription describeUnit(UnitEnum unit);
    
    /**
     * 获取最适合的单位表示
     * 
     * @param value 待转换的数值
     * @param fromUnit 源单位
     * @return 最佳单位转换结果
     */
    ConvertResult<BigDecimal> convertToBest(BigDecimal value, UnitEnum fromUnit);
    
    /**
     * 获取最适合的单位表示（带选项）
     * 
     * @param value 待转换的数值
     * @param fromUnit 源单位
     * @param options 选项配置
     * @return 最佳单位转换结果
     */
    ConvertResult<BigDecimal> convertToBest(BigDecimal value, UnitEnum fromUnit, ToBestOptions options);
    
    /**
     * 获取所有支持的测量类型
     * 
     * @return 支持的测量类型列表
     */
    List<String> getSupportedMeasures();
    
    /**
     * 获取指定测量类型下的所有可能单位
     * 
     * @param measure 测量类型
     * @return 该测量类型下的所有单位
     */
    List<UnitEnum> getPossibleUnits(String measure);
    
    /**
     * 获取所有支持的单位
     * 
     * @return 所有支持的单位描述列表
     */
    List<UnitDescription> listAllUnits();
    
    /**
     * 获取指定测量类型下的所有单位描述
     * 
     * @param measure 测量类型
     * @return 该测量类型下的所有单位描述
     */
    List<UnitDescription> listUnitsByMeasure(String measure);
    
    /**
     * 设置源单位，用于链式调用
     * 
     * @param value 源数值
     * @param fromUnit 源单位
     * @return 当前服务实例，支持链式调用
     */
    IUnitConversionService from(BigDecimal value, UnitEnum fromUnit);
    
    /**
     * 从已设置的源单位转换到目标单位（链式调用）
     * 
     * @param toUnit 目标单位
     * @return 转换结果对象
     */
    ConvertResult<BigDecimal> to(UnitEnum toUnit);
    
    /**
     * 获取当前源单位可以转换到的所有可能单位
     * 
     * @param measure 可选的测量类型过滤器
     * @return 可能的单位列表
     */
    List<UnitEnum> possibilities(String measure);
    
    /**
     * 获取所有支持的测量类型
     * 
     * @return 测量类型列表
     */
    List<String> measures();
}