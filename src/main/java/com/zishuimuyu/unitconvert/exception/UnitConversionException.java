package com.zishuimuyu.unitconvert.exception;

import java.util.Map;

/**
 * 单位转换异常类
 * 
 * 提供详细的错误信息和错误代码，便于调试和错误处理
 * 
 * @author 紫水木鱼
 * @version 1.0.0
 * @since 2026-04-25
 */
public class UnitConversionException extends RuntimeException {
    
    private final String errorCode;
    private final Map<String, Object> details;
    
    public UnitConversionException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
        this.details = null;
    }
    
    public UnitConversionException(String message, String errorCode, Map<String, Object> details) {
        super(message);
        this.errorCode = errorCode;
        this.details = details;
    }
    
    public UnitConversionException(String message, String errorCode, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
        this.details = null;
    }
    
    public UnitConversionException(String message, String errorCode, Map<String, Object> details, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
        this.details = details;
    }
    
    /**
     * 获取错误代码
     * 
     * @return 错误代码
     */
    public String getErrorCode() {
        return errorCode;
    }
    
    /**
     * 获取错误详情
     * 
     * @return 错误详情映射
     */
    public Map<String, Object> getDetails() {
        return details;
    }
}