package com.zishuimuyu.unitconvert.model;

/**
 * 单位描述类
 * <P>
 * 用于描述单位的详细信息
 * <P>
 * 功能列表：
 * <ul>
 *   <li>存储单位的详细信息，包括缩写、测量类型、系统、单复数形式</li>
 *   <li>提供从UnitEnum创建UnitDescription的工厂方法</li>
 *   <li>提供访问器方法获取单位属性</li>
 *   <li>提供toString方法用于调试和日志记录</li>
 * </ul>
 * <P>
 * 设计原理：
 * <ol>
 *   <li>采用不可变值对象模式，所有字段都是final的</li>
 *   <li>提供工厂方法便于从UnitEnum创建实例</li>
 *   <li>遵循JavaBean规范提供getter方法</li>
 * </ol>
 * <P>
 * 优势：
 * <ul>
 *   <li>不可变性：保证对象状态的一致性和线程安全</li>
 *   <li>信息完整：包含单位的所有相关信息</li>
 *   <li>易用性：提供便捷的创建方法和访问器</li>
 * </ul>
 * <P>
 * 使用场景：
 * <ol>
 *   <li>在单位转换服务中返回单位的详细信息</li>
 *   <li>单位信息展示和查询</li>
 *   <li>单位验证和兼容性检查</li>
 *   <li>调试和日志记录</li>
 * </ol>
 * <P>
 * 
 * @author 紫水木鱼
 * @version 1.0.0
 * @since 2026-04-24
 */
public class UnitDescription {
    
    /**
     * 单位缩写
     * <P>
     * 详细描述字段的用途、取值范围或特殊含义
     * <P>
     * 用途：存储单位的简短表示形式，通常为国际通用的标准缩写
     * <P>
     * 取值范围：字符串，如"m"、"kg"、"s"等
     * <P>
     * 特殊含义：作为单位的主要标识符，在转换过程中用于识别和显示单位
     */
    private final String abbr;
    
    /**
     * 测量类型
     * <P>
     * 详细描述字段的用途、取值范围或特殊含义
     * <P>
     * 用途：标识单位所属的物理量类型
     * <P>
     * 取值范围：字符串，如"length"、"mass"、"time"、"temperature"等
     * <P>
     * 特殊含义：用于单位兼容性检查，只有相同测量类型的单位才能相互转换
     */
    private final String measure;
    
    /**
     * 单位系统
     * <P>
     * 详细描述字段的用途、取值范围或特殊含义
     * <P>
     * 用途：标识单位所属的度量系统
     * <P>
     * 取值范围：字符串，如"metric"、"imperial"、"binary"等
     * <P>
     * 特殊含义：用于跨系统转换计算，区分公制、英制等不同度量系统
     */
    private final String system;
    
    /**
     * 单数形式
     * <P>
     * 详细描述字段的用途、取值范围或特殊含义
     * <P>
     * 用途：存储单位的单数表达形式
     * <P>
     * 取值范围：字符串，如"米"、"kilogram"等
     * <P>
     * 特殊含义：用于结果展示和用户界面显示，提供更友好的单位名称
     */
    private final String singular;
    
    /**
     * 复数形式
     * <P>
     * 详细描述字段的用途、取值范围或特殊含义
     * <P>
     * 用途：存储单位的复数表达形式
     * <P>
     * 取值范围：字符串，如"米"、"kilograms"等
     * <P>
     * 特殊含义：用于结果展示和用户界面显示，提供更友好的单位名称
     */
    private final String plural;
    
    /**
     * 构造函数，初始化单位描述对象
     * <P>
     * 详细描述方法的功能、算法逻辑或业务流程
     * <P>
     * 处理逻辑：
     * <ol>
     *   <li>接收单位的各项属性参数</li>
     *   <li>将参数分别赋值给对应的私有字段</li>
     *   <li>完成单位描述对象的初始化</li>
     * </ol>
     * <P>
     * 参数说明：
     * <ul>
     *   <li>abbr: 单位缩写，用于标识单位的简短形式</li>
     *   <li>measure: 测量类型，标识单位所属的物理量类型</li>
     *   <li>system: 单位系统，标识单位所属的度量系统</li>
     *   <li>singular: 单数形式，单位的单数表达形式</li>
     *   <li>plural: 复数形式，单位的复数表达形式</li>
     * </ul>
     * <P>
     * 返回值说明：
     * <ul>
     *   <li>void: 无返回值，仅初始化对象</li>
     * </ul>
     * <P>
     * 异常情况：
     * <ul>
     *   <li>无异常情况</li>
     * </ul>
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
     * <P>
     * 详细描述方法的功能、算法逻辑或业务流程
     * <P>
     * 处理逻辑：
     * <ol>
     *   <li>接收UnitEnum参数</li>
     *   <li>调用UnitEnum的getter方法获取各项属性</li>
     *   <li>使用获取的属性创建新的UnitDescription实例</li>
     *   <li>返回新创建的UnitDescription实例</li>
     * </ol>
     * <P>
     * 参数说明：
     * <ul>
     *   <li>unitEnum: 单位枚举，从中提取单位信息</li>
     * </ul>
     * <P>
     * 返回值说明：
     * <ul>
     *   <li>返回基于UnitEnum创建的UnitDescription对象</li>
     * </ul>
     * <P>
     * 异常情况：
     * <ul>
     *   <li>如果unitEnum为null，可能会抛出NullPointerException</li>
     * </ul>
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
     * <P>
     * 详细描述方法的功能、算法逻辑或业务流程
     * <P>
     * 处理逻辑：
     * <ol>
     *   <li>直接返回已初始化的单位缩写字段</li>
     * </ol>
     * <P>
     * 参数说明：
     * <ul>
     *   <li>无参数</li>
     * </ul>
     * <P>
     * 返回值说明：
     * <ul>
     *   <li>返回单位的缩写字符串</li>
     * </ul>
     * <P>
     * 异常情况：
     * <ul>
     *   <li>无异常情况</li>
     * </ul>
     * 
     * @return 单位缩写
     */
    public String getAbbr() {
        return abbr;
    }
    
    /**
     * 获取测量类型
     * <P>
     * 详细描述方法的功能、算法逻辑或业务流程
     * <P>
     * 处理逻辑：
     * <ol>
     *   <li>直接返回已初始化的测量类型字段</li>
     * </ol>
     * <P>
     * 参数说明：
     * <ul>
     *   <li>无参数</li>
     * </ul>
     * <P>
     * 返回值说明：
     * <ul>
     *   <li>返回单位的测量类型字符串</li>
     * </ul>
     * <P>
     * 异常情况：
     * <ul>
     *   <li>无异常情况</li>
     * </ul>
     * 
     * @return 测量类型
     */
    public String getMeasure() {
        return measure;
    }
    
    /**
     * 获取单位系统
     * <P>
     * 详细描述方法的功能、算法逻辑或业务流程
     * <P>
     * 处理逻辑：
     * <ol>
     *   <li>直接返回已初始化的单位系统字段</li>
     * </ol>
     * <P>
     * 参数说明：
     * <ul>
     *   <li>无参数</li>
     * </ul>
     * <P>
     * 返回值说明：
     * <ul>
     *   <li>返回单位所属的系统字符串</li>
     * </ul>
     * <P>
     * 异常情况：
     * <ul>
     *   <li>无异常情况</li>
     * </ul>
     * 
     * @return 单位系统
     */
    public String getSystem() {
        return system;
    }
    
    /**
     * 获取单数形式
     * <P>
     * 详细描述方法的功能、算法逻辑或业务流程
     * <P>
     * 处理逻辑：
     * <ol>
     *   <li>直接返回已初始化的单数形式字段</li>
     * </ol>
     * <P>
     * 参数说明：
     * <ul>
     *   <li>无参数</li>
     * </ul>
     * <P>
     * 返回值说明：
     * <ul>
     *   <li>返回单位的单数形式字符串</li>
     * </ul>
     * <P>
     * 异常情况：
     * <ul>
     *   <li>无异常情况</li>
     * </ul>
     * 
     * @return 单数形式
     */
    public String getSingular() {
        return singular;
    }
    
    /**
     * 获取复数形式
     * <P>
     * 详细描述方法的功能、算法逻辑或业务流程
     * <P>
     * 处理逻辑：
     * <ol>
     *   <li>直接返回已初始化的复数形式字段</li>
     * </ol>
     * <P>
     * 参数说明：
     * <ul>
     *   <li>无参数</li>
     * </ul>
     * <P>
     * 返回值说明：
     * <ul>
     *   <li>返回单位的复数形式字符串</li>
     * </ul>
     * <P>
     * 异常情况：
     * <ul>
     *   <li>无异常情况</li>
     * </ul>
     * 
     * @return 复数形式
     */
    public String getPlural() {
        return plural;
    }
    
    /**
     * 返回对象的字符串表示形式
     * <P>
     * 详细描述方法的功能、算法逻辑或业务流程
     * <P>
     * 处理逻辑：
     * <ol>
     *   <li>按照固定格式拼接所有字段的值</li>
     *   <li>返回包含对象所有信息的字符串</li>
     * </ol>
     * <P>
     * 参数说明：
     * <ul>
     *   <li>无参数</li>
     * </ul>
     * <P>
     * 返回值说明：
     * <ul>
     *   <li>返回包含对象所有字段信息的字符串表示</li>
     * </ul>
     * <P>
     * 异常情况：
     * <ul>
     *   <li>无异常情况</li>
     * </ul>
     * 
     * @return 对象的字符串表示形式
     */
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