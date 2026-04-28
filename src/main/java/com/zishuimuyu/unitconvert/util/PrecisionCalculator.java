package com.zishuimuyu.unitconvert.util;

import java.math.BigDecimal;
import java.math.MathContext;

/**
 * 高精度计算器
 * <P>
 * 提供高性能的BigDecimal运算操作
 * <P>
 * 功能列表：
 * <ul>
 *   <li>提供高性能的BigDecimal运算操作</li>
 *   <li>通过预设精度和上下文优化计算性能</li>
 *   <li>提供安全的四则运算操作</li>
 *   <li>包含参数验证和异常处理</li>
 * </ul>
 * <P>
 * 设计原理：
 * <ol>
 *   <li>使用预设的MathContext统一计算精度</li>
 *   <li>提供安全的算术运算方法，包含参数验证</li>
 *   <li>统一异常处理和错误消息</li>
 *   <li>优化BigDecimal运算性能</li>
 * </ol>
 * <P>
 * 优势：
 * <ul>
 *   <li>提高计算性能：使用统一的精度设置避免重复创建MathContext</li>
 *   <li>确保精度一致：所有运算使用相同的精度和舍入模式</li>
 *   <li>安全性：包含参数验证和适当的异常处理</li>
 * </ul>
 * <P>
 * 使用场景：
 * <ol>
 *   <li>需要高精度计算的单位转换场景</li>
 *   <li>金融、科学计算等对精度要求高的应用</li>
 *   <li>需要统一精度控制的批量计算场景</li>
 * </ol>
 * <P>
 * 使用示例：
 * <pre>
 * // 使用高精度计算器进行运算
 * BigDecimal result1 = PrecisionCalculator.multiply(
 *     new BigDecimal("10.5"), 
 *     new BigDecimal("2.3")
 * );
 * 
 * BigDecimal result2 = PrecisionCalculator.divide(
 *     new BigDecimal("10.5"), 
 *     new BigDecimal("2.1")
 * );
 * 
 * BigDecimal result3 = PrecisionCalculator.add(
 *     new BigDecimal("10.5"), 
 *     new BigDecimal("2.3")
 * );
 * </pre>
 * <P>
 * 
 * @author 紫水木鱼
 * @version 1.0.0
 * @since 2026-04-25
 */
public class PrecisionCalculator {
    
    /**
     * 预设的数学上下文，用于控制计算精度
     * <P>
     * 详细描述字段的用途、取值范围或特殊含义
     * <P>
     * 用途：用于控制所有BigDecimal运算的精度和舍入模式
     * <P>
     * 取值范围：MathContext对象，精度为10，舍入模式为HALF_UP
     * <P>
     * 特殊含义：通过预设统一的数学上下文提高运算性能并确保精度一致性
     */
    public static final MathContext MATH_CONTEXT = new MathContext(10, java.math.RoundingMode.HALF_UP);
    
    /**
     * 安全的乘法运算
     * <P>
     * 详细描述方法的功能、算法逻辑或业务流程
     * <P>
     * 处理逻辑：
     * <ol>
     *   <li>检查两个操作数是否为null</li>
     *   <li>如果任一操作数为null，抛出IllegalArgumentException</li>
     *   <li>使用预设的数学上下文执行乘法运算</li>
     *   <li>返回计算结果</li>
     * </ol>
     * <P>
     * 参数说明：
     * <ul>
     *   <li>a: 第一个操作数，不能为空</li>
     *   <li>b: 第二个操作数，不能为空</li>
     * </ul>
     * <P>
     * 返回值说明：
     * <ul>
     *   <li>成功: 返回两个数的乘积结果</li>
     *   <li>失败: 抛出IllegalArgumentException异常</li>
     * </ul>
     * <P>
     * 异常情况：
     * <ul>
     *   <li>当任一操作数为null时，抛出IllegalArgumentException</li>
     * </ul>
     * 
     * @param a 第一个操作数
     * @param b 第二个操作数
     * @return 乘积结果
     */
    public static BigDecimal multiply(BigDecimal a, BigDecimal b) {
        if (a == null || b == null) {
            throw new IllegalArgumentException("操作数不能为null");
        }
        return a.multiply(b, MATH_CONTEXT);
    }
    
    /**
     * 安全的除法运算
     * <P>
     * 详细描述方法的功能、算法逻辑或业务流程
     * <P>
     * 处理逻辑：
     * <ol>
     *   <li>检查两个操作数是否为null</li>
     *   <li>如果任一操作数为null，抛出IllegalArgumentException</li>
     *   <li>检查除数是否为零</li>
     *   <li>如果除数为零，抛出ArithmeticException</li>
     *   <li>使用预设的数学上下文执行除法运算</li>
     *   <li>返回计算结果</li>
     * </ol>
     * <P>
     * 参数说明：
     * <ul>
     *   <li>a: 被除数，不能为空</li>
     *   <li>b: 除数，不能为空且不能为零</li>
     * </ul>
     * <P>
     * 返回值说明：
     * <ul>
     *   <li>成功: 返回两数相除的商结果</li>
     *   <li>失败: 抛出IllegalArgumentException或ArithmeticException异常</li>
     * </ul>
     * <P>
     * 异常情况：
     * <ul>
     *   <li>当任一操作数为null时，抛出IllegalArgumentException</li>
     *   <li>当除数为零时，抛出ArithmeticException</li>
     * </ul>
     * 
     * @param a 被除数
     * @param b 除数
     * @return 商结果
     */
    public static BigDecimal divide(BigDecimal a, BigDecimal b) {
        if (a == null || b == null) {
            throw new IllegalArgumentException("操作数不能为null");
        }
        if (b.signum() == 0) {
            throw new ArithmeticException("除数不能为零");
        }
        return a.divide(b, MATH_CONTEXT);
    }
    
    /**
     * 安全的加法运算
     * <P>
     * 详细描述方法的功能、算法逻辑或业务流程
     * <P>
     * 处理逻辑：
     * <ol>
     *   <li>检查两个操作数是否为null</li>
     *   <li>如果任一操作数为null，抛出IllegalArgumentException</li>
     *   <li>使用预设的数学上下文执行加法运算</li>
     *   <li>返回计算结果</li>
     * </ol>
     * <P>
     * 参数说明：
     * <ul>
     *   <li>a: 第一个操作数，不能为空</li>
     *   <li>b: 第二个操作数，不能为空</li>
     * </ul>
     * <P>
     * 返回值说明：
     * <ul>
     *   <li>成功: 返回两数相加的和结果</li>
     *   <li>失败: 抛出IllegalArgumentException异常</li>
     * </ul>
     * <P>
     * 异常情况：
     * <ul>
     *   <li>当任一操作数为null时，抛出IllegalArgumentException</li>
     * </ul>
     * 
     * @param a 第一个操作数
     * @param b 第二个操作数
     * @return 和结果
     */
    public static BigDecimal add(BigDecimal a, BigDecimal b) {
        if (a == null || b == null) {
            throw new IllegalArgumentException("操作数不能为null");
        }
        return a.add(b, MATH_CONTEXT);
    }
    
    /**
     * 安全的减法运算
     * <P>
     * 详细描述方法的功能、算法逻辑或业务流程
     * <P>
     * 处理逻辑：
     * <ol>
     *   <li>检查两个操作数是否为null</li>
     *   <li>如果任一操作数为null，抛出IllegalArgumentException</li>
     *   <li>使用预设的数学上下文执行减法运算</li>
     *   <li>返回计算结果</li>
     * </ol>
     * <P>
     * 参数说明：
     * <ul>
     *   <li>a: 被减数，不能为空</li>
     *   <li>b: 减数，不能为空</li>
     * </ul>
     * <P>
     * 返回值说明：
     * <ul>
     *   <li>成功: 返回两数相减的差结果</li>
     *   <li>失败: 抛出IllegalArgumentException异常</li>
     * </ul>
     * <P>
     * 异常情况：
     * <ul>
     *   <li>当任一操作数为null时，抛出IllegalArgumentException</li>
     * </ul>
     * 
     * @param a 被减数
     * @param b 减数
     * @return 差结果
     */
    public static BigDecimal subtract(BigDecimal a, BigDecimal b) {
        if (a == null || b == null) {
            throw new IllegalArgumentException("操作数不能为null");
        }
        return a.subtract(b, MATH_CONTEXT);
    }
    
    /**
     * 获取绝对值
     * <P>
     * 详细描述方法的功能、算法逻辑或业务流程
     * <P>
     * 处理逻辑：
     * <ol>
     *   <li>检查输入值是否为null</li>
     *   <li>如果输入值为null，抛出IllegalArgumentException</li>
     *   <li>使用预设的数学上下文计算绝对值</li>
     *   <li>返回计算结果</li>
     * </ol>
     * <P>
     * 参数说明：
     * <ul>
     *   <li>value: 输入值，不能为空</li>
     * </ul>
     * <P>
     * 返回值说明：
     * <ul>
     *   <li>成功: 返回输入值的绝对值</li>
     *   <li>失败: 抛出IllegalArgumentException异常</li>
     * </ul>
     * <P>
     * 异常情况：
     * <ul>
     *   <li>当输入值为null时，抛出IllegalArgumentException</li>
     * </ul>
     * 
     * @param value 输入值
     * @return 绝对值
     */
    public static BigDecimal abs(BigDecimal value) {
        if (value == null) {
            throw new IllegalArgumentException("输入值不能为null");
        }
        return value.abs(MATH_CONTEXT);
    }
}