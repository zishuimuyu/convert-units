package com.zishuimuyu.unitconvert.util;

import java.math.BigDecimal;
import java.math.MathContext;

/**
 * 高精度计算器
 * 
 * 提供高性能的BigDecimal运算操作
 * 通过预设精度和上下文优化计算性能
 * 
 * @author 紫水木鱼
 * @version 1.0.0
 * @since 2026-04-25
 */
public class PrecisionCalculator {
    
    /**
     * 预设的数学上下文，用于控制计算精度
     * 使用固定精度以提高性能
     */
    public static final MathContext MATH_CONTEXT = new MathContext(10, java.math.RoundingMode.HALF_UP);
    
    /**
     * 安全的乘法运算
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