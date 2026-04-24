package com.zishuimuyu.unitconvert.model;

/**
 * 单位描述类
 * 
 * 用于描述单位的详细信息
 * 
 * @author 紫水木鱼
 * @version 1.0.0
 * @since 2026-04-24
 */
public class UnitDescription {
    
    private final String abbr;       // 单位缩写
    private final String measure;    // 测量类型
    private final String system;     // 单位系统
    private final String singular;   // 单数形式
    private final String plural;     // 复数形式
    
    /**
     * 构造函数，初始化单位描述对象
     * 
     * @param abbr 单位缩写
     * @param measure 测量类型
     * @param system 单位系统
     * @param singular 单数形式
     * @param plural 复数形式
     */
    public UnitDescription(String abbr, String measure, String system, String singular, String plural) {
        this.abbr = abbr;
        this.measure = measure;
        this.system = system;
        this.singular = singular;
        this.plural = plural;
    }
    
    /**
     * 从UnitEnum创建UnitDescription
     * 
     * @param unitEnum 单位枚举
     * @return 单位描述对象
     */
    public static UnitDescription fromUnitEnum(UnitEnum unitEnum) {
        return new UnitDescription(
            unitEnum.getAbbr(),
            unitEnum.getMeasure(),
            unitEnum.getSystem(),
            unitEnum.getSingular(),
            unitEnum.getPlural()
        );
    }
    
    /**
     * 获取单位缩写
     * 
     * @return 单位缩写
     */
    public String getAbbr() {
        return abbr;
    }
    
    /**
     * 获取测量类型
     * 
     * @return 测量类型
     */
    public String getMeasure() {
        return measure;
    }
    
    /**
     * 获取单位系统
     * 
     * @return 单位系统
     */
    public String getSystem() {
        return system;
    }
    
    /**
     * 获取单数形式
     * 
     * @return 单数形式
     */
    public String getSingular() {
        return singular;
    }
    
    /**
     * 获取复数形式
     * 
     * @return 复数形式
     */
    public String getPlural() {
        return plural;
    }
    
    @Override
    public String toString() {
        return "UnitDescription{" +
                "abbr='" + abbr + '\'' +
                ", measure='" + measure + '\'' +
                ", system='" + system + '\'' +
                ", singular='" + singular + '\'' +
                ", plural='" + plural + '\'' +
                '}';
    }
}