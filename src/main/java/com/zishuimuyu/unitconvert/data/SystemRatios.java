package com.zishuimuyu.unitconvert.data;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

/**
 * 系统间转换比率管理类
 * 
 * 管理不同单位系统之间的转换比率
 * 主要用于处理不同系统（如公制/英制）之间的单位转换
 * 
 * 设计原理：
 * - 每个测量类型（measureType）可以有多个单位系统
 * - 系统间转换比率用于将一个系统的锚点单位转换为另一个系统的锚点单位
 * - 在当前实现中，同一测量类型的所有系统都使用相同的锚点单位，所以比率通常为1
 * 
 * 使用场景：
 * - 当转换涉及不同系统间的单位时（如METRIC到IMPERIAL）
 * - 通过系统间比率实现跨系统转换
 * 
 * @author 紫水木鱼
 * @version 1.0.0
 * @since 2026-04-25
 */
public class SystemRatios {
    
    /**
     * 存储系统间转换比率
     * 
     * 结构：measureType -> ratioKey -> ratioValue
     * 例如：pressure -> "metric_to_imperial" -> BigDecimal.ONE
     */
    private static final Map<String, Map<String, BigDecimal>> SYSTEM_RATIOS = new HashMap<>();
    
    static {
        initializeSystemRatios();
    }
    
    /**
     * 私有构造函数，防止实例化
     */
    private SystemRatios() {
    }
    
    /**
     * 获取指定测量类型和比率键的转换比率
     * 
     * @param measureType 测量类型（如"pressure", "energy"等）
     * @param ratioKey 比率键（如"metric_to_imperial", "SI_to_nutrition"等）
     * @return 转换比率，如果不存在则返回null
     */
    public static BigDecimal getRatio(String measureType, String ratioKey) {
        Map<String, BigDecimal> ratios = SYSTEM_RATIOS.get(measureType);
        if (ratios == null) {
            return null;
        }
        return ratios.get(ratioKey);
    }
    
    /**
     * 检查是否存在指定的转换比率
     * 
     * @param measureType 测量类型
     * @param ratioKey 比率键
     * @return 如果存在返回true，否则返回false
     */
    public static boolean hasRatio(String measureType, String ratioKey) {
        Map<String, BigDecimal> ratios = SYSTEM_RATIOS.get(measureType);
        if (ratios == null) {
            return false;
        }
        return ratios.containsKey(ratioKey);
    }
    
    /**
     * 获取所有系统间转换比率的副本
     * 
     * @return 所有转换比率的深拷贝
     */
    public static Map<String, Map<String, BigDecimal>> getAllRatios() {
        Map<String, Map<String, BigDecimal>> result = new HashMap<>();
        for (Map.Entry<String, Map<String, BigDecimal>> entry : SYSTEM_RATIOS.entrySet()) {
            result.put(entry.getKey(), new HashMap<>(entry.getValue()));
        }
        return result;
    }
    
    /**
     * 初始化所有系统间转换比率
     */
    private static void initializeSystemRatios() {
        initializeLengthSystemRatios();
        initializeAreaSystemRatios();
        initializeVolumeSystemRatios();
        initializeMassSystemRatios();
        initializeSpeedSystemRatios();
        initializePressureSystemRatios();
        initializeEnergySystemRatios();
        initializeForceSystemRatios();
        initializeTorqueSystemRatios();
        initializeIlluminanceSystemRatios();
        initializePaceSystemRatios();
        initializeVolumeFlowRateSystemRatios();
    }
    
    /**
     * 初始化长度单位系统间转换比率
     * 
     * metric_to_imperial: 1 米 = 39.3701 英寸
     * imperial_to_metric: 1 英寸 = 2.54 厘米
     */
    private static void initializeLengthSystemRatios() {
        Map<String, BigDecimal> lengthRatios = new HashMap<>();
        
        lengthRatios.put("metric_to_imperial", new BigDecimal("0.393701"));
        lengthRatios.put("imperial_to_metric", new BigDecimal("2.54"));
        
        SYSTEM_RATIOS.put("length", lengthRatios);
    }
    
    /**
     * 初始化面积单位系统间转换比率
     * 
     * metric_to_imperial: 1 平方米 = 10.764 平方英尺
     * imperial_to_metric: 1 平方英尺 = 0.092903 平方米
     */
    private static void initializeAreaSystemRatios() {
        Map<String, BigDecimal> areaRatios = new HashMap<>();
        
        areaRatios.put("metric_to_imperial", new BigDecimal("10.764"));
        areaRatios.put("imperial_to_metric", new BigDecimal("0.092903"));
        
        SYSTEM_RATIOS.put("area", areaRatios);
    }
    
    /**
     * 初始化体积单位系统间转换比率
     * 
     * metric_to_imperial: 1 毫升 = 0.033814 液盎司
     * imperial_to_metric: 1 液盎司 = 29.5735 毫升
     */
    private static void initializeVolumeSystemRatios() {
        Map<String, BigDecimal> volumeRatios = new HashMap<>();
        
        volumeRatios.put("metric_to_imperial", new BigDecimal("33.814"));
        volumeRatios.put("imperial_to_metric", new BigDecimal("0.0295735"));
        
        SYSTEM_RATIOS.put("volume", volumeRatios);
    }
    
    /**
     * 初始化质量单位系统间转换比率
     * 
     * metric_to_imperial: 1 克 = 0.00220462 磅
     * imperial_to_metric: 1 磅 = 453.592 克
     */
    private static void initializeMassSystemRatios() {
        Map<String, BigDecimal> massRatios = new HashMap<>();
        
        massRatios.put("metric_to_imperial", new BigDecimal("0.00220462"));
        massRatios.put("imperial_to_metric", new BigDecimal("453.592"));
        
        SYSTEM_RATIOS.put("mass", massRatios);
    }
    
    /**
     * 初始化速度单位系统间转换比率
     * 
     * metric_to_imperial: 1 公里/小时 = 0.621371 英里/小时
     * imperial_to_metric: 1 英里/小时 = 1.60934 公里/小时
     */
    private static void initializeSpeedSystemRatios() {
        Map<String, BigDecimal> speedRatios = new HashMap<>();
        
        speedRatios.put("metric_to_imperial", new BigDecimal("0.621371"));
        speedRatios.put("imperial_to_metric", new BigDecimal("1.60934"));
        
        SYSTEM_RATIOS.put("speed", speedRatios);
    }
    
    /**
     * 初始化压力单位系统间转换比率
     * 
     * 在当前实现中，所有压力单位都使用相同的锚点单位Pa，所以比率是1
     */
    private static void initializePressureSystemRatios() {
        Map<String, BigDecimal> pressureRatios = new HashMap<>();
        
        // 压力单位：所有系统都使用相同的锚点单位PA
        // metric_to_imperial: 1 metric锚点单位 = 1 imperial锚点单位 (因为都等于1 Pa)
        pressureRatios.put("metric_to_imperial", BigDecimal.ONE);
        // imperial_to_metric: 1 imperial锚点单位 = 1 metric锚点单位
        pressureRatios.put("imperial_to_metric", BigDecimal.ONE);
        
        SYSTEM_RATIOS.put("pressure", pressureRatios);
    }
    
    /**
     * 初始化能量单位系统间转换比率
     * 
     * 在当前实现中，所有能量单位都使用相同的锚点单位J，所以比率是1
     */
    private static void initializeEnergySystemRatios() {
        Map<String, BigDecimal> energyRatios = new HashMap<>();
        
        // 能量单位：所有系统都使用相同的锚点单位J
        // SI_to_nutrition: 1 SI锚点单位 = 1 nutrition锚点单位 (因为都等于1 J)
        energyRatios.put("SI_to_nutrition", BigDecimal.ONE);
        // nutrition_to_SI: 1 nutrition锚点单位 = 1 SI锚点单位
        energyRatios.put("nutrition_to_SI", BigDecimal.ONE);
        
        SYSTEM_RATIOS.put("energy", energyRatios);
    }
    
    /**
     * 初始化力单位系统间转换比率
     * 
     * 在当前实现中，所有力单位都使用相同的锚点单位N，所以比率是1
     */
    private static void initializeForceSystemRatios() {
        Map<String, BigDecimal> forceRatios = new HashMap<>();
        
        // 力单位：所有系统都使用相同的锚点单位N
        // SI_to_imperial: 1 SI锚点单位 = 1 imperial锚点单位 (因为都等于1 N)
        forceRatios.put("SI_to_imperial", BigDecimal.ONE);
        // imperial_to_SI: 1 imperial锚点单位 = 1 SI锚点单位
        forceRatios.put("imperial_to_SI", BigDecimal.ONE);
        
        SYSTEM_RATIOS.put("force", forceRatios);
    }
    
    /**
     * 初始化扭矩单位系统间转换比率
     * 
     * 在当前实现中，所有扭矩单位都使用相同的锚点单位Nm，所以比率是1
     */
    private static void initializeTorqueSystemRatios() {
        Map<String, BigDecimal> torqueRatios = new HashMap<>();
        
        // 扭矩单位：所有系统都使用相同的锚点单位Nm
        // metric_to_imperial: 1 metric锚点单位 = 1 imperial锚点单位 (因为都等于1 Nm)
        torqueRatios.put("metric_to_imperial", BigDecimal.ONE);
        // imperial_to_metric: 1 imperial锚点单位 = 1 metric锚点单位
        torqueRatios.put("imperial_to_metric", BigDecimal.ONE);
        
        SYSTEM_RATIOS.put("torque", torqueRatios);
    }
    
    /**
     * 初始化照度单位系统间转换比率
     * 
     * 在当前实现中，所有照度单位都使用相同的锚点单位LX，所以比率是1
     */
    private static void initializeIlluminanceSystemRatios() {
        Map<String, BigDecimal> illuminanceRatios = new HashMap<>();
        
        // 照度单位：所有系统都使用相同的锚点单位LX
        // metric_to_imperial: 1 metric锚点单位 = 1 imperial锚点单位 (因为都等于1 LX)
        illuminanceRatios.put("metric_to_imperial", BigDecimal.ONE);
        // imperial_to_metric: 1 imperial锚点单位 = 1 metric锚点单位
        illuminanceRatios.put("imperial_to_metric", BigDecimal.ONE);
        
        SYSTEM_RATIOS.put("illuminance", illuminanceRatios);
    }
    
    /**
     * 初始化步速单位系统间转换比率
     * 
     * 在当前实现中，所有步速单位都使用相同的锚点单位S_PER_M，所以比率是1
     */
    private static void initializePaceSystemRatios() {
        Map<String, BigDecimal> paceRatios = new HashMap<>();
        
        // 步速单位：所有系统都使用相同的锚点单位S_PER_M
        // metric_to_imperial: 1 metric锚点单位 = 1 imperial锚点单位 (因为都等于1 S_PER_M)
        paceRatios.put("metric_to_imperial", BigDecimal.ONE);
        // imperial_to_metric: 1 imperial锚点单位 = 1 metric锚点单位
        paceRatios.put("imperial_to_metric", BigDecimal.ONE);
        
        SYSTEM_RATIOS.put("pace", paceRatios);
    }
    
    /**
     * 初始化体积流量单位系统间转换比率
     * 
     * metric_to_imperial: 1 dm³/s = 1/28.3168 ft³/s
     * imperial_to_metric: 1 ft³/s = 28.3168 dm³/s
     */
    private static void initializeVolumeFlowRateSystemRatios() {
        Map<String, BigDecimal> volumeFlowRateRatios = new HashMap<>();
        
        // 体积流量单位：metric锚点是DM3_PER_S(1)，imperial锚点是FT3_PER_S(28.3168)
        // metric_to_imperial: 将metric锚点转换为imperial锚点
        volumeFlowRateRatios.put("metric_to_imperial", new BigDecimal("1").divide(new BigDecimal("28.3168"), 10, RoundingMode.HALF_UP));
        // imperial_to_metric: 将imperial锚点转换为metric锚点
        volumeFlowRateRatios.put("imperial_to_metric", new BigDecimal("28.3168").divide(new BigDecimal("1"), 10, RoundingMode.HALF_UP));
        
        SYSTEM_RATIOS.put("volumeFlowRate", volumeFlowRateRatios);
    }
}