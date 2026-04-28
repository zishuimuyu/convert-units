package com.zishuimuyu.unitconvert.exception;

import java.util.Map;

/**
 * 单位转换异常类
 * <P>
 * 提供详细的错误信息和错误代码，便于调试和错误处理
 * <P>
 * 功能列表：
 * <ul>
 *   <li>提供详细的错误信息和错误代码</li>
 *   <li>支持携带额外的错误详情信息</li>
 *   <li>支持嵌套异常信息</li>
 *   <li>便于分类和处理不同类型的转换错误</li>
 * </ul>
 * <P>
 * 设计原理：
 * <ol>
 *   <li>继承RuntimeException以支持非检查型异常</li>
 *   <li>提供错误代码便于程序化处理错误</li>
 *   <li>支持携带详细错误信息便于调试</li>
 *   <li>支持异常链便于追踪错误源头</li>
 * </ol>
 * <P>
 * 优势：
 * <ul>
 *   <li>精确错误定位：通过错误代码快速识别错误类型</li>
 *   <li>丰富错误信息：提供详细的错误描述和附加信息</li>
 *   <li>易于处理：支持程序化错误处理逻辑</li>
 * </ul>
 * <P>
 * 使用场景：
 * <ol>
 *   <li>单位转换过程中发生错误的情况</li>
 *   <li>无效的单位或测量类型</li>
 *   <li>不支持的转换操作</li>
 *   <li>配置错误导致的转换失败</li>
 * </ol>
 * <P>
 * 使用示例：
 * <pre>
 * // 抛出基本异常
 * throw new UnitConversionException("未知的单位", "UNKNOWN_UNIT");
 * 
 * // 抛出带详情的异常
 * Map&lt;String, Object&gt; details = new HashMap&lt;&gt;();
 * details.put("fromUnit", "invalid_unit");
 * details.put("toUnit", "valid_unit");
 * throw new UnitConversionException("无法转换单位", "CONVERSION_ERROR", details);
 * 
 * // 捕获和处理异常
 * try {
 *     // 执行单位转换
 * } catch (UnitConversionException e) {
 *     System.out.println("错误代码: " + e.getErrorCode());
 *     System.out.println("错误详情: " + e.getDetails());
 * }
 * </pre>
 * <P>
 * 
 * @author 紫水木鱼
 * @version 1.0.0
 * @since 2026-04-25
 */
public class UnitConversionException extends RuntimeException {
    
    /**
     * 错误代码
     * <P>
     * 详细描述字段的用途、取值范围或特殊含义
     * <P>
     * 用途：用于标识特定类型的错误，便于程序化处理
     * <P>
     * 取值范围：字符串格式的错误代码，如"UNKNOWN_UNIT"、"CONVERSION_ERROR"等
     * <P>
     * 特殊含义：通过错误代码可以快速识别和分类不同类型的转换错误
     */
    private final String errorCode;
    
    /**
     * 错误详情
     * <P>
     * 详细描述字段的用途、取值范围或特殊含义
     * <P>
     * 用途：存储与错误相关的额外详细信息
     * <P>
     * 取值范围：Map<String, Object>类型的详细信息集合
     * <P>
     * 特殊含义：提供更丰富的错误上下文信息，便于调试和问题诊断
     */
    private final Map<String, Object> details;
    
    /**
     * 构造函数 - 基本异常信息
     * <P>
     * 详细描述方法的功能、算法逻辑或业务流程
     * <P>
     * 处理逻辑：
     * <ol>
     *   <li>调用父类构造函数设置异常消息</li>
     *   <li>设置错误代码</li>
     *   <li>将错误详情设为null</li>
     * </ol>
     * <P>
     * 参数说明：
     * <ul>
     *   <li>message: 异常消息文本</li>
     *   <li>errorCode: 错误代码</li>
     * </ul>
     * <P>
     * 返回值说明：
     * <ul>
     *   <li>void: 无返回值，仅初始化异常对象</li>
     * </ul>
     * <P>
     * 异常情况：
     * <ul>
     *   <li>无异常情况</li>
     * </ul>
     * 
     * @param message 异常消息文本
     * @param errorCode 错误代码
     */
    public UnitConversionException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
        this.details = null;
    }
    
    /**
     * 构造函数 - 带详情的异常信息
     * <P>
     * 详细描述方法的功能、算法逻辑或业务流程
     * <P>
     * 处理逻辑：
     * <ol>
     *   <li>调用父类构造函数设置异常消息</li>
     *   <li>设置错误代码</li>
     *   <li>设置错误详情映射</li>
     * </ol>
     * <P>
     * 参数说明：
     * <ul>
     *   <li>message: 异常消息文本</li>
     *   <li>errorCode: 错误代码</li>
     *   <li>details: 错误详情映射</li>
     * </ul>
     * <P>
     * 返回值说明：
     * <ul>
     *   <li>void: 无返回值，仅初始化异常对象</li>
     * </ul>
     * <P>
     * 异常情况：
     * <ul>
     *   <li>无异常情况</li>
     * </ul>
     * 
     * @param message 异常消息文本
     * @param errorCode 错误代码
     * @param details 错误详情映射
     */
    public UnitConversionException(String message, String errorCode, Map<String, Object> details) {
        super(message);
        this.errorCode = errorCode;
        this.details = details;
    }
    
    /**
     * 构造函数 - 带原因的异常信息
     * <P>
     * 详细描述方法的功能、算法逻辑或业务流程
     * <P>
     * 处理逻辑：
     * <ol>
     *   <li>调用父类构造函数设置异常消息和原因</li>
     *   <li>设置错误代码</li>
     *   <li>将错误详情设为null</li>
     * </ol>
     * <P>
     * 参数说明：
     * <ul>
     *   <li>message: 异常消息文本</li>
     *   <li>errorCode: 错误代码</li>
     *   <li>cause: 导致此异常的原因</li>
     * </ul>
     * <P>
     * 返回值说明：
     * <ul>
     *   <li>void: 无返回值，仅初始化异常对象</li>
     * </ul>
     * <P>
     * 异常情况：
     * <ul>
     *   <li>无异常情况</li>
     * </ul>
     * 
     * @param message 异常消息文本
     * @param errorCode 错误代码
     * @param cause 导致此异常的原因
     */
    public UnitConversionException(String message, String errorCode, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
        this.details = null;
    }
    
    /**
     * 构造函数 - 带详情和原因的异常信息
     * <P>
     * 详细描述方法的功能、算法逻辑或业务流程
     * <P>
     * 处理逻辑：
     * <ol>
     *   <li>调用父类构造函数设置异常消息和原因</li>
     *   <li>设置错误代码</li>
     *   <li>设置错误详情映射</li>
     * </ol>
     * <P>
     * 参数说明：
     * <ul>
     *   <li>message: 异常消息文本</li>
     *   <li>errorCode: 错误代码</li>
     *   <li>details: 错误详情映射</li>
     *   <li>cause: 导致此异常的原因</li>
     * </ul>
     * <P>
     * 返回值说明：
     * <ul>
     *   <li>void: 无返回值，仅初始化异常对象</li>
     * </ul>
     * <P>
     * 异常情况：
     * <ul>
     *   <li>无异常情况</li>
     * </ul>
     * 
     * @param message 异常消息文本
     * @param errorCode 错误代码
     * @param details 错误详情映射
     * @param cause 导致此异常的原因
     */
    public UnitConversionException(String message, String errorCode, Map<String, Object> details, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
        this.details = details;
    }
    
    /**
     * 获取错误代码
     * <P>
     * 详细描述方法的功能、算法逻辑或业务流程
     * <P>
     * 处理逻辑：
     * <ol>
     *   <li>返回存储在异常对象中的错误代码</li>
     * </ol>
     * <P>
     * 参数说明：
     * <ul>
     *   <li>无参数</li>
     * </ul>
     * <P>
     * 返回值说明：
     * <ul>
     *   <li>返回错误代码字符串</li>
     * </ul>
     * <P>
     * 异常情况：
     * <ul>
     *   <li>无异常情况</li>
     * </ul>
     * 
     * @return 错误代码
     */
    public String getErrorCode() {
        return errorCode;
    }
    
    /**
     * 获取错误详情
     * <P>
     * 详细描述方法的功能、算法逻辑或业务流程
     * <P>
     * 处理逻辑：
     * <ol>
     *   <li>返回存储在异常对象中的错误详情映射</li>
     * </ol>
     * <P>
     * 参数说明：
     * <ul>
     *   <li>无参数</li>
     * </ul>
     * <P>
     * 返回值说明：
     * <ul>
     *   <li>返回错误详情映射，如果未设置则返回null</li>
     * </ul>
     * <P>
     * 异常情况：
     * <ul>
     *   <li>无异常情况</li>
     * </ul>
     * 
     * @return 错误详情映射
     */
    public Map<String, Object> getDetails() {
        return details;
    }
}