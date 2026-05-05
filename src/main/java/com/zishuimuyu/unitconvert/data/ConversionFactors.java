package com.zishuimuyu.unitconvert.data;

import com.zishuimuyu.unitconvert.model.UnitEnum;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

/**
 * 单位转换因子类
 * 
 * 管理所有单位到其对应锚点单位的转换因子
 * 
 * 设计原理：
 * - 每个测量类型选择一个锚点单位（通常是国际标准单位）
 * - 所有其他单位都存储到锚点单位的转换因子
 * - 转换时：源单位 -> 锚点单位 -> 目标单位
 * 
 * 优势：
 * - 减少存储空间：n个单位只需要n个转换因子
 * - 提高转换精度：减少中间转换步骤
 * - 易于维护：添加新单位只需添加一个转换因子
 * 
 * 示例（长度单位）：
 * - 锚点单位：米
 * - 转换因子：1厘米 = 0.01米，1千米 = 1000米
 * - 转换1厘米到千米：1厘米 -> 0.01米 -> 0.00001千米
 * 
 * @author 紫水木鱼
 * @version 1.0.0
 * @since 2026-04-25
 */
public class ConversionFactors {
    
    /**
     * 单位到锚点单位的转换因子映射
     * 
     * 键：单位枚举
     * 值：该单位到锚点单位的转换因子
     * 
     * 例如：对于长度单位，锚点是米
     * - UnitEnum.CM -> 0.01 (1厘米 = 0.01米)
     * - UnitEnum.KM -> 1000 (1千米 = 1000米)
     */
    private static final Map<UnitEnum, BigDecimal> TO_ANCHOR_FACTORS = new HashMap<>();
    
    /**
     * 静态初始化块
     * 
     * 在类加载时初始化所有单位的转换因子
     * 确保转换因子在使用前已经准备好
     */
    static {
        initializeFactors();
    }
    
    /**
     * 私有构造函数
     * 
     * 这是一个工具类，不允许实例化
     */
    private ConversionFactors() {
    }
    
    /**
     * 获取指定单位的转换因子
     * 
     * @param unit 单位枚举
     * @return 该单位到锚点单位的转换因子，如果不存在返回null
     */
    public static BigDecimal getFactor(UnitEnum unit) {
        return TO_ANCHOR_FACTORS.get(unit);
    }
    
    /**
     * 检查是否存在指定单位的转换因子
     * 
     * @param unit 单位枚举
     * @return 如果存在转换因子返回true，否则返回false
     */
    public static boolean hasFactor(UnitEnum unit) {
        return TO_ANCHOR_FACTORS.containsKey(unit);
    }
    
    /**
     * 获取所有转换因子的副本
     * 
     * 返回的是副本，防止外部修改内部数据
     * 
     * @return 所有转换因子的映射
     */
    public static Map<UnitEnum, BigDecimal> getAllFactors() {
        return new HashMap<>(TO_ANCHOR_FACTORS);
    }
    
    /**
     * 初始化所有单位的转换因子
     * 
     * 按测量类型分组初始化，便于维护和查找
     */
    private static void initializeFactors() {
        initializeLengthFactors();
        initializeMassFactors();
        initializeVolumeFactors();
        initializeAreaFactors();
        initializeTimeFactors();
        initializeTemperatureFactors();
        initializeSpeedFactors();
        initializeAccelerationFactors();
        initializeAngleFactors();
        initializePowerFactors();
        initializePressureFactors();
        initializeQuantityFactors();
        initializeChargeFactors();
        initializeCurrentFactors();
        initializeVoltageFactors();
        initializeDigitalStorageFactors();
        initializeEnergyFactors();
        initializeForceFactors();
        initializeTorqueFactors();
        initializeFrequencyFactors();
        initializeIlluminanceFactors();
        initializeApparentPowerFactors();
        initializeConcentrationFactors();
        initializePiecesFactors();
        initializeReactivePowerFactors();
        initializeReactiveEnergyFactors();
        initializeMassFlowRateFactors();
        initializePaceFactors();
        initializeVolumeFlowRateFactors();
    }
    
    /**
     * 初始化长度单位的转换因子
     * 
     * 锚点单位：米
     * 
     * 转换因子说明：
     * - 幺米(ym)：1e-24米
     * - 仄米(zm)：1e-21米
     * - 阿米(am)：1e-18米
     * - 飞米(fm)：1e-15米
     * - 皮米(pm)：1e-12米
     * - 纳米(nm)：1e-9米
     * - 微米(μm)：1e-6米
     * - 埃格斯特朗(Å)：1e-10米
     * - 毫米(mm)：1e-3米
     * - 厘米(cm)：1e-2米
     * - 分米(dm)：1e-1米
     * - 米：1（锚点单位）
     * - 千米(km)：1e3米
     * - 兆米(Mm)：1e6米
     * - 吉米(Gm)：1e9米
     * - 手宽(hand)：0.1016米
     * - 跋(fur)：201.168米
     * - 天文单位(AU)：149,597,870,700米
     * - 光年(ly)：9,460,730,472,580,800米
     * - 秒差距(pc)：30,856,775,814,913,700米
     * - 尧米(Ym)：1e24米
     * - 英制单位通过精确的转换比例计算
     */
    private static void initializeLengthFactors() {
        TO_ANCHOR_FACTORS.put(UnitEnum.LENGTH_YM, new BigDecimal("1e-24"));
        TO_ANCHOR_FACTORS.put(UnitEnum.LENGTH_ZM, new BigDecimal("1e-21"));
        TO_ANCHOR_FACTORS.put(UnitEnum.LENGTH_AM, new BigDecimal("1e-18"));
        TO_ANCHOR_FACTORS.put(UnitEnum.LENGTH_FM, new BigDecimal("1e-15"));
        TO_ANCHOR_FACTORS.put(UnitEnum.LENGTH_PM, new BigDecimal("1e-12"));
        TO_ANCHOR_FACTORS.put(UnitEnum.LENGTH_NANOMETER, new BigDecimal("1e-9"));
        TO_ANCHOR_FACTORS.put(UnitEnum.LENGTH_UM, new BigDecimal("1e-6"));
        TO_ANCHOR_FACTORS.put(UnitEnum.LENGTH_ANGSTROM, new BigDecimal("1e-10"));
        TO_ANCHOR_FACTORS.put(UnitEnum.LENGTH_MM, new BigDecimal("1e-3"));
        TO_ANCHOR_FACTORS.put(UnitEnum.LENGTH_CM, new BigDecimal("1e-2"));
        TO_ANCHOR_FACTORS.put(UnitEnum.LENGTH_DM, new BigDecimal("1e-1"));
        TO_ANCHOR_FACTORS.put(UnitEnum.LENGTH_M, new BigDecimal("1"));
        TO_ANCHOR_FACTORS.put(UnitEnum.LENGTH_KM, new BigDecimal("1e3"));
        TO_ANCHOR_FACTORS.put(UnitEnum.LENGTH_MM_METRIC, new BigDecimal("1e6"));
        TO_ANCHOR_FACTORS.put(UnitEnum.LENGTH_GM, new BigDecimal("1e9"));
        TO_ANCHOR_FACTORS.put(UnitEnum.LENGTH_MIL, new BigDecimal("1").divide(new BigDecimal("12000"), 10, RoundingMode.HALF_UP));
        TO_ANCHOR_FACTORS.put(UnitEnum.LENGTH_IN, new BigDecimal("1").divide(new BigDecimal("12"), 10, RoundingMode.HALF_UP));
        TO_ANCHOR_FACTORS.put(UnitEnum.LENGTH_YD, new BigDecimal("3"));
        TO_ANCHOR_FACTORS.put(UnitEnum.LENGTH_FT_US, new BigDecimal("1200").divide(new BigDecimal("3937"), 10, RoundingMode.HALF_UP));
        TO_ANCHOR_FACTORS.put(UnitEnum.LENGTH_FT, new BigDecimal("1").divide(new BigDecimal("3"), 10, RoundingMode.HALF_UP));
        TO_ANCHOR_FACTORS.put(UnitEnum.LENGTH_HAND, new BigDecimal("0.1016"));
        TO_ANCHOR_FACTORS.put(UnitEnum.LENGTH_FATHOM, new BigDecimal("6"));
        TO_ANCHOR_FACTORS.put(UnitEnum.LENGTH_FUR, new BigDecimal("201.168"));
        TO_ANCHOR_FACTORS.put(UnitEnum.LENGTH_MI, new BigDecimal("5280"));
        TO_ANCHOR_FACTORS.put(UnitEnum.LENGTH_NMI, new BigDecimal("1852"));
        TO_ANCHOR_FACTORS.put(UnitEnum.LENGTH_AU, new BigDecimal("149597870700"));
        TO_ANCHOR_FACTORS.put(UnitEnum.LENGTH_LY, new BigDecimal("9460730472580800"));
        TO_ANCHOR_FACTORS.put(UnitEnum.LENGTH_PARSEC, new BigDecimal("30856775814913700"));
        TO_ANCHOR_FACTORS.put(UnitEnum.LENGTH_YM_METRIC, new BigDecimal("1e24"));
    }
    
    /**
     * 初始化质量单位的转换因子
     * 
     * 锚点单位：克
     * 
     * 转换因子说明：
     * - 微克(mcg)：1e-6克
     * - 毫克(mg)：1e-3克
     * - 克：1（锚点单位）
     * - 千克：1e3克
     * - 公吨：1e6克
     * - 英制质量单位使用精确的转换比例
     */
    private static void initializeMassFactors() {
        TO_ANCHOR_FACTORS.put(UnitEnum.MASS_MCG, new BigDecimal("1").divide(new BigDecimal("1e6"), 10, RoundingMode.HALF_UP));
        TO_ANCHOR_FACTORS.put(UnitEnum.MASS_MG, new BigDecimal("1").divide(new BigDecimal("1e3"), 10, RoundingMode.HALF_UP));
        TO_ANCHOR_FACTORS.put(UnitEnum.MASS_G, new BigDecimal("1"));
        TO_ANCHOR_FACTORS.put(UnitEnum.MASS_KG, new BigDecimal("1e3"));
        TO_ANCHOR_FACTORS.put(UnitEnum.MASS_TONNE, new BigDecimal("1e6"));
        TO_ANCHOR_FACTORS.put(UnitEnum.MASS_OZ, new BigDecimal("28.3495"));
        TO_ANCHOR_FACTORS.put(UnitEnum.MASS_LB, new BigDecimal("453.592"));
        TO_ANCHOR_FACTORS.put(UnitEnum.MASS_ST, new BigDecimal("6350.29"));
        TO_ANCHOR_FACTORS.put(UnitEnum.MASS_GR, new BigDecimal("0.0648"));
        TO_ANCHOR_FACTORS.put(UnitEnum.MASS_DR, new BigDecimal("1.77"));
        TO_ANCHOR_FACTORS.put(UnitEnum.MASS_USTF, new BigDecimal("907185"));
        TO_ANCHOR_FACTORS.put(UnitEnum.MASS_LT, new BigDecimal("1016047"));
        TO_ANCHOR_FACTORS.put(UnitEnum.MASS_QIAN, new BigDecimal("5.0"));
        TO_ANCHOR_FACTORS.put(UnitEnum.MASS_LIANG, new BigDecimal("50.0"));
        TO_ANCHOR_FACTORS.put(UnitEnum.MASS_JIN, new BigDecimal("500.0"));
        TO_ANCHOR_FACTORS.put(UnitEnum.MASS_DAN, new BigDecimal("50000.0"));
    }
    
    /**
     * 初始化体积单位的转换因子
     * 
     * 锚点单位：立方分米
     * 
     * 转换因子说明：
     * - 立方毫米(mm3)：1e-6立方分米
     * - 立方厘米(cm3)：1e-3立方分米
     * - 立方分米(dm3)：1（锚点单位）
     * - 毫升：1e-3立方分米
     * - 升：1立方分米
     * - 立方米：1e3立方分米
     * - 立方千米：1e9立方分米
     * - 瑞典单位和英制单位使用特定的转换比例
     */
    private static void initializeVolumeFactors() {
        TO_ANCHOR_FACTORS.put(UnitEnum.VOLUME_MM3, new BigDecimal("1").divide(new BigDecimal("1e6"), 10, RoundingMode.HALF_UP));
        TO_ANCHOR_FACTORS.put(UnitEnum.VOLUME_CM3, new BigDecimal("1").divide(new BigDecimal("1e3"), 10, RoundingMode.HALF_UP));
        TO_ANCHOR_FACTORS.put(UnitEnum.VOLUME_DM3, new BigDecimal("1"));
        TO_ANCHOR_FACTORS.put(UnitEnum.VOLUME_ML, new BigDecimal("1").divide(new BigDecimal("1e3"), 10, RoundingMode.HALF_UP));
        TO_ANCHOR_FACTORS.put(UnitEnum.VOLUME_CL, new BigDecimal("1").divide(new BigDecimal("100"), 10, RoundingMode.HALF_UP));
        TO_ANCHOR_FACTORS.put(UnitEnum.VOLUME_DL, new BigDecimal("1").divide(new BigDecimal("10"), 10, RoundingMode.HALF_UP));
        TO_ANCHOR_FACTORS.put(UnitEnum.VOLUME_L, new BigDecimal("1"));
        TO_ANCHOR_FACTORS.put(UnitEnum.VOLUME_KL, new BigDecimal("1e3"));
        TO_ANCHOR_FACTORS.put(UnitEnum.VOLUME_ML_MEGA, new BigDecimal("1e6"));
        TO_ANCHOR_FACTORS.put(UnitEnum.VOLUME_ML_GIGA, new BigDecimal("1e9"));
        TO_ANCHOR_FACTORS.put(UnitEnum.VOLUME_M3, new BigDecimal("1e3"));
        TO_ANCHOR_FACTORS.put(UnitEnum.VOLUME_KM3, new BigDecimal("1e12"));
        TO_ANCHOR_FACTORS.put(UnitEnum.VOLUME_KRM, new BigDecimal("0.005"));
        TO_ANCHOR_FACTORS.put(UnitEnum.VOLUME_TSK, new BigDecimal("0.015"));
        TO_ANCHOR_FACTORS.put(UnitEnum.VOLUME_MSK, new BigDecimal("0.03"));
        TO_ANCHOR_FACTORS.put(UnitEnum.VOLUME_KKP, new BigDecimal("0.15"));
        TO_ANCHOR_FACTORS.put(UnitEnum.VOLUME_GLAS, new BigDecimal("0.2"));
        TO_ANCHOR_FACTORS.put(UnitEnum.VOLUME_KANNA, new BigDecimal("2.617"));
        TO_ANCHOR_FACTORS.put(UnitEnum.VOLUME_TSP, new BigDecimal("1").divide(new BigDecimal("6"), 10, RoundingMode.HALF_UP));
        TO_ANCHOR_FACTORS.put(UnitEnum.VOLUME_TBS, new BigDecimal("1").divide(new BigDecimal("2"), 10, RoundingMode.HALF_UP));
        TO_ANCHOR_FACTORS.put(UnitEnum.VOLUME_IN3, new BigDecimal("0.55411"));
        TO_ANCHOR_FACTORS.put(UnitEnum.VOLUME_FLOZ, new BigDecimal("1"));
        TO_ANCHOR_FACTORS.put(UnitEnum.VOLUME_CUP, new BigDecimal("8"));
        TO_ANCHOR_FACTORS.put(UnitEnum.VOLUME_PNT, new BigDecimal("16"));
        TO_ANCHOR_FACTORS.put(UnitEnum.VOLUME_QT, new BigDecimal("32"));
        TO_ANCHOR_FACTORS.put(UnitEnum.VOLUME_GAL, new BigDecimal("128"));
        TO_ANCHOR_FACTORS.put(UnitEnum.VOLUME_FT3, new BigDecimal("957.506"));
        TO_ANCHOR_FACTORS.put(UnitEnum.VOLUME_YD3, new BigDecimal("25852.7"));
        TO_ANCHOR_FACTORS.put(UnitEnum.VOLUME_BBL, new BigDecimal("158.987"));
        TO_ANCHOR_FACTORS.put(UnitEnum.VOLUME_CU, new BigDecimal("0.000001"));
        TO_ANCHOR_FACTORS.put(UnitEnum.VOLUME_SHO, new BigDecimal("0.00001"));
        TO_ANCHOR_FACTORS.put(UnitEnum.VOLUME_HE, new BigDecimal("0.0001"));
        TO_ANCHOR_FACTORS.put(UnitEnum.VOLUME_TO, new BigDecimal("0.01"));
        TO_ANCHOR_FACTORS.put(UnitEnum.VOLUME_SEKI, new BigDecimal("0.1"));
    }
    
    private static void initializeAreaFactors() {
        TO_ANCHOR_FACTORS.put(UnitEnum.AREA_MM2, new BigDecimal("1e-6"));
        TO_ANCHOR_FACTORS.put(UnitEnum.AREA_CM2, new BigDecimal("1e-4"));
        TO_ANCHOR_FACTORS.put(UnitEnum.AREA_M2, new BigDecimal("1"));
        TO_ANCHOR_FACTORS.put(UnitEnum.AREA_HA, new BigDecimal("1e4"));
        TO_ANCHOR_FACTORS.put(UnitEnum.AREA_KM2, new BigDecimal("1e6"));
        TO_ANCHOR_FACTORS.put(UnitEnum.AREA_IN2, new BigDecimal("1").divide(new BigDecimal("144"), 10, RoundingMode.HALF_UP));
        TO_ANCHOR_FACTORS.put(UnitEnum.AREA_FT2, new BigDecimal("1"));
        TO_ANCHOR_FACTORS.put(UnitEnum.AREA_YD2, new BigDecimal("9"));
        TO_ANCHOR_FACTORS.put(UnitEnum.AREA_MI2, new BigDecimal("27878400"));
        TO_ANCHOR_FACTORS.put(UnitEnum.AREA_ACRE, new BigDecimal("43560"));
        TO_ANCHOR_FACTORS.put(UnitEnum.AREA_ROD, new BigDecimal("272.25"));
    }
    
    private static void initializeTimeFactors() {
        TO_ANCHOR_FACTORS.put(UnitEnum.TIME_YS, new BigDecimal("1e-24"));
        TO_ANCHOR_FACTORS.put(UnitEnum.TIME_ZS, new BigDecimal("1e-21"));
        TO_ANCHOR_FACTORS.put(UnitEnum.TIME_AS, new BigDecimal("1e-18"));
        TO_ANCHOR_FACTORS.put(UnitEnum.TIME_TP, new BigDecimal("1e12"));
        TO_ANCHOR_FACTORS.put(UnitEnum.TIME_FS, new BigDecimal("1e-15"));
        TO_ANCHOR_FACTORS.put(UnitEnum.TIME_PS, new BigDecimal("1e-12"));
        TO_ANCHOR_FACTORS.put(UnitEnum.TIME_NS, new BigDecimal("1e-9"));
        TO_ANCHOR_FACTORS.put(UnitEnum.TIME_US, new BigDecimal("1e-6"));
        TO_ANCHOR_FACTORS.put(UnitEnum.TIME_MU, new BigDecimal("1e-6"));
        TO_ANCHOR_FACTORS.put(UnitEnum.TIME_MS, new BigDecimal("1e-3"));
        TO_ANCHOR_FACTORS.put(UnitEnum.TIME_S, new BigDecimal("1"));
        TO_ANCHOR_FACTORS.put(UnitEnum.TIME_MIN, new BigDecimal("60"));
        TO_ANCHOR_FACTORS.put(UnitEnum.TIME_H, new BigDecimal("3600"));
        TO_ANCHOR_FACTORS.put(UnitEnum.TIME_DAY, new BigDecimal("86400"));
        TO_ANCHOR_FACTORS.put(UnitEnum.TIME_WEEK, new BigDecimal("604800"));
        TO_ANCHOR_FACTORS.put(UnitEnum.TIME_FORTNIGHT, new BigDecimal("1209600"));
        TO_ANCHOR_FACTORS.put(UnitEnum.TIME_KE, new BigDecimal("900"));
        TO_ANCHOR_FACTORS.put(UnitEnum.TIME_SHICHEN, new BigDecimal("7200"));
        TO_ANCHOR_FACTORS.put(UnitEnum.TIME_LUNARMONTH, new BigDecimal("2551443"));
        TO_ANCHOR_FACTORS.put(UnitEnum.TIME_MONTH, new BigDecimal("2629800"));
        TO_ANCHOR_FACTORS.put(UnitEnum.TIME_QUARTER, new BigDecimal("7889400"));
        TO_ANCHOR_FACTORS.put(UnitEnum.TIME_YEAR, new BigDecimal("31557600"));
        TO_ANCHOR_FACTORS.put(UnitEnum.TIME_CENTURY, new BigDecimal("3155760000"));
        TO_ANCHOR_FACTORS.put(UnitEnum.TIME_MILLENNIUM, new BigDecimal("31557600000"));
        TO_ANCHOR_FACTORS.put(UnitEnum.TIME_MEGAANNUM, new BigDecimal("31557600000000"));
    }
    
    private static void initializeTemperatureFactors() {
        // 保留兰金温标，但使用RA（兰氏度）代替原来的R
        // 注意：原来的R被重新定义为RA（兰氏度），所以不再需要此处的条目
        // 温度转换由TemperatureConversionStrategy单独处理
    }
    
    private static void initializeSpeedFactors() {
        // 设置M_S为metric系统锚点单位
        TO_ANCHOR_FACTORS.put(UnitEnum.SPEED_M_S, new BigDecimal("1"));              // 米/秒: 作为metric系统锚点单位
        TO_ANCHOR_FACTORS.put(UnitEnum.SPEED_MM_S, new BigDecimal("0.001"));          // 毫米/秒: 0.001m/s (相对于metric锚点)
        TO_ANCHOR_FACTORS.put(UnitEnum.SPEED_CM_S, new BigDecimal("0.01"));           // 厘米/秒: 0.01m/s (相对于metric锚点)
        TO_ANCHOR_FACTORS.put(UnitEnum.SPEED_DM_S, new BigDecimal("0.1"));            // 分米/秒: 0.1m/s (相对于metric锚点)
        TO_ANCHOR_FACTORS.put(UnitEnum.SPEED_DAM_S, new BigDecimal("10"));            // 十米/秒: 10m/s (相对于metric锚点)
        TO_ANCHOR_FACTORS.put(UnitEnum.SPEED_HM_S, new BigDecimal("100"));             // 百米/秒: 100m/s (相对于metric锚点)
        TO_ANCHOR_FACTORS.put(UnitEnum.SPEED_KM_S, new BigDecimal("1000"));            // 千米/秒: 1000m/s (相对于metric锚点)
        TO_ANCHOR_FACTORS.put(UnitEnum.SPEED_KM_H, new BigDecimal("0.2777777778"));    // 千米/时: 1/3.6 m/s (相对于metric锚点)
        TO_ANCHOR_FACTORS.put(UnitEnum.SPEED_KM_MIN, new BigDecimal("16.666666667"));  // 千米/分: 1000/60 m/s (相对于metric锚点)
        TO_ANCHOR_FACTORS.put(UnitEnum.SPEED_M_MIN, new BigDecimal("0.0166666667"));   // 米/分: 1/60 m/s (相对于metric锚点)
        TO_ANCHOR_FACTORS.put(UnitEnum.SPEED_CM_MIN, new BigDecimal("0.0001666667"));  // 厘米/分: 0.01/60 m/s (相对于metric锚点)
        TO_ANCHOR_FACTORS.put(UnitEnum.SPEED_MM_MIN, new BigDecimal("0.0000166667"));  // 毫米/分: 0.001/60 m/s (相对于metric锚点)
        TO_ANCHOR_FACTORS.put(UnitEnum.SPEED_MM_H, new BigDecimal("2.777777778e-7"));  // 毫米/时: 0.001/3600 m/s (相对于metric锚点)
        TO_ANCHOR_FACTORS.put(UnitEnum.SPEED_MACH, new BigDecimal("343"));               // 马赫: 343m/s (相对于metric锚点)
        TO_ANCHOR_FACTORS.put(UnitEnum.SPEED_LIGHT_SPEED, new BigDecimal("299792458"));          // 光速: 299792458m/s (相对于metric锚点)
        
        // 设置FPS为imperial系统锚点单位
        TO_ANCHOR_FACTORS.put(UnitEnum.SPEED_FPS, new BigDecimal("1"));                // 英尺/秒: 作为imperial系统锚点单位
        TO_ANCHOR_FACTORS.put(UnitEnum.SPEED_FPM, new BigDecimal("0.0166666667"));     // 英尺/分: 1/60 fps (相对于imperial锚点)
        TO_ANCHOR_FACTORS.put(UnitEnum.SPEED_IN_S, new BigDecimal("0.0833333333"));    // 英寸/秒: 1/12 fps (相对于imperial锚点)
        TO_ANCHOR_FACTORS.put(UnitEnum.SPEED_YD_S, new BigDecimal("3"));               // 码/秒: 3 fps (相对于imperial锚点)
        TO_ANCHOR_FACTORS.put(UnitEnum.SPEED_MPH, new BigDecimal("1.4666666667"));     // 英里/时: 5280/3600 fps (相对于imperial锚点)
        TO_ANCHOR_FACTORS.put(UnitEnum.SPEED_MI_S, new BigDecimal("5280"));            // 英里/秒: 5280 fps (相对于imperial锚点)
        TO_ANCHOR_FACTORS.put(UnitEnum.SPEED_MI_MIN, new BigDecimal("88"));            // 英里/分: 5280/60 fps (相对于imperial锚点)
        TO_ANCHOR_FACTORS.put(UnitEnum.SPEED_IN_H, new BigDecimal("2.314814815e-5"));  // 英寸/时: 1/(12*3600) fps (相对于imperial锚点)
        TO_ANCHOR_FACTORS.put(UnitEnum.SPEED_NMI_H, new BigDecimal("1.6878098571"));   // 海里/时: 6076.12/3600 fps (相对于imperial锚点)
        TO_ANCHOR_FACTORS.put(UnitEnum.SPEED_KT, new BigDecimal("1.6878098571"));      // 节: 6076.12/3600 fps (相对于imperial锚点)
    }
    
    private static void initializeAccelerationFactors() {
        TO_ANCHOR_FACTORS.put(UnitEnum.ACCELERATION_M_S2, new BigDecimal("1"));
        TO_ANCHOR_FACTORS.put(UnitEnum.ACCELERATION_CM_S2, new BigDecimal("0.01"));
        TO_ANCHOR_FACTORS.put(UnitEnum.ACCELERATION_MM_S2, new BigDecimal("0.001"));
        TO_ANCHOR_FACTORS.put(UnitEnum.ACCELERATION_IN_S2, new BigDecimal("0.0254").divide(new BigDecimal("0.3048"), 10, java.math.RoundingMode.HALF_UP));
        TO_ANCHOR_FACTORS.put(UnitEnum.ACCELERATION_FT_S2, new BigDecimal("1"));
        TO_ANCHOR_FACTORS.put(UnitEnum.ACCELERATION_G_FORCE, new BigDecimal("9.80665"));
        TO_ANCHOR_FACTORS.put(UnitEnum.ACCELERATION_G0, new BigDecimal("9.80665"));
        TO_ANCHOR_FACTORS.put(UnitEnum.ACCELERATION_GAL_ACC, new BigDecimal("0.01"));
    }
    
    private static void initializeAngleFactors() {
        TO_ANCHOR_FACTORS.put(UnitEnum.ANGLE_RAD, new BigDecimal("180").divide(new BigDecimal("3.141592653589793"), 10, RoundingMode.HALF_UP));
        TO_ANCHOR_FACTORS.put(UnitEnum.ANGLE_DEG, new BigDecimal("1"));
        TO_ANCHOR_FACTORS.put(UnitEnum.ANGLE_ARCMIN, new BigDecimal("1").divide(new BigDecimal("60"), 10, RoundingMode.HALF_UP));
        TO_ANCHOR_FACTORS.put(UnitEnum.ANGLE_ARCSEC, new BigDecimal("1").divide(new BigDecimal("3600"), 10, RoundingMode.HALF_UP));
        TO_ANCHOR_FACTORS.put(UnitEnum.ANGLE_GRAD, new BigDecimal("0.9"));
    }
    
    private static void initializePowerFactors() {
        TO_ANCHOR_FACTORS.put(UnitEnum.POWER_W, new BigDecimal("1"));
        TO_ANCHOR_FACTORS.put(UnitEnum.POWER_PW, new BigDecimal("1e-12"));
        TO_ANCHOR_FACTORS.put(UnitEnum.POWER_NW, new BigDecimal("1e-9"));
        TO_ANCHOR_FACTORS.put(UnitEnum.POWER_UW, new BigDecimal("1e-6"));
        TO_ANCHOR_FACTORS.put(UnitEnum.POWER_MW, new BigDecimal("1e-3"));
        TO_ANCHOR_FACTORS.put(UnitEnum.POWER_KW, new BigDecimal("1e3"));
        TO_ANCHOR_FACTORS.put(UnitEnum.POWER_MW_POWER, new BigDecimal("1e6"));
        TO_ANCHOR_FACTORS.put(UnitEnum.POWER_GW, new BigDecimal("1e9"));
        TO_ANCHOR_FACTORS.put(UnitEnum.POWER_TW, new BigDecimal("1e12"));
        TO_ANCHOR_FACTORS.put(UnitEnum.POWER_METRIC_HP, new BigDecimal("735.5"));
        TO_ANCHOR_FACTORS.put(UnitEnum.POWER_HP, new BigDecimal("1"));
        TO_ANCHOR_FACTORS.put(UnitEnum.POWER_BTU_PER_S, new BigDecimal("1055.06").divide(new BigDecimal("745.7"), 10, java.math.RoundingMode.HALF_UP));
        TO_ANCHOR_FACTORS.put(UnitEnum.POWER_CAL_PER_S, new BigDecimal("4.184"));
        TO_ANCHOR_FACTORS.put(UnitEnum.POWER_KCAL_PER_H, new BigDecimal("1.163"));
        TO_ANCHOR_FACTORS.put(UnitEnum.POWER_BTU_PER_H, new BigDecimal("0.2931").divide(new BigDecimal("745.7"), 10, java.math.RoundingMode.HALF_UP));
        TO_ANCHOR_FACTORS.put(UnitEnum.POWER_KGF_M_PER_S, new BigDecimal("9.80665"));
        TO_ANCHOR_FACTORS.put(UnitEnum.POWER_FTLBF_PER_S, new BigDecimal("1.35582").divide(new BigDecimal("745.7"), 10, java.math.RoundingMode.HALF_UP));
        TO_ANCHOR_FACTORS.put(UnitEnum.POWER_FTLBF_PER_MIN, new BigDecimal("0.022597").divide(new BigDecimal("745.7"), 10, java.math.RoundingMode.HALF_UP));
        TO_ANCHOR_FACTORS.put(UnitEnum.POWER_ERG_PER_S, new BigDecimal("1e-7"));
    }
    
    private static void initializePressureFactors() {
        TO_ANCHOR_FACTORS.put(UnitEnum.PRESSURE_PA, new BigDecimal("1"));
        TO_ANCHOR_FACTORS.put(UnitEnum.PRESSURE_HPA, new BigDecimal("100"));
        TO_ANCHOR_FACTORS.put(UnitEnum.PRESSURE_KPA, new BigDecimal("1000"));
        TO_ANCHOR_FACTORS.put(UnitEnum.PRESSURE_MPA, new BigDecimal("1000000"));
        TO_ANCHOR_FACTORS.put(UnitEnum.PRESSURE_GPA, new BigDecimal("1000000000"));
        TO_ANCHOR_FACTORS.put(UnitEnum.PRESSURE_MBAR, new BigDecimal("100"));
        TO_ANCHOR_FACTORS.put(UnitEnum.PRESSURE_BAR, new BigDecimal("100000"));
        TO_ANCHOR_FACTORS.put(UnitEnum.PRESSURE_MMHG, new BigDecimal("133.322"));
        TO_ANCHOR_FACTORS.put(UnitEnum.PRESSURE_TORR, new BigDecimal("133.32236842105263"));
        TO_ANCHOR_FACTORS.put(UnitEnum.PRESSURE_ATM, new BigDecimal("101325"));
        TO_ANCHOR_FACTORS.put(UnitEnum.PRESSURE_KGF_PER_M2, new BigDecimal("9.80665"));
        TO_ANCHOR_FACTORS.put(UnitEnum.PRESSURE_LBF_PER_FT2, new BigDecimal("1").divide(new BigDecimal("144"), 10, java.math.RoundingMode.HALF_UP));
        TO_ANCHOR_FACTORS.put(UnitEnum.PRESSURE_LBF_PER_IN2, new BigDecimal("1"));
        TO_ANCHOR_FACTORS.put(UnitEnum.PRESSURE_KSI, new BigDecimal("1000"));
        TO_ANCHOR_FACTORS.put(UnitEnum.PRESSURE_MH2O, new BigDecimal("9806.65"));
        TO_ANCHOR_FACTORS.put(UnitEnum.PRESSURE_PSI, new BigDecimal("1"));
    }
    
    private static void initializeQuantityFactors() {
        TO_ANCHOR_FACTORS.put(UnitEnum.PIECES_EA, new BigDecimal("1"));
        TO_ANCHOR_FACTORS.put(UnitEnum.PIECES_SINGLE, new BigDecimal("1"));
        TO_ANCHOR_FACTORS.put(UnitEnum.PIECES_DOZEN, new BigDecimal("12"));
        TO_ANCHOR_FACTORS.put(UnitEnum.PIECES_PCS, new BigDecimal("1"));
        TO_ANCHOR_FACTORS.put(UnitEnum.PIECES_PAIR, new BigDecimal("2"));
        TO_ANCHOR_FACTORS.put(UnitEnum.PIECES_SET, new BigDecimal("1"));
    }
    
    private static void initializeChargeFactors() {
        TO_ANCHOR_FACTORS.put(UnitEnum.CHARGE_COULOMB, new BigDecimal("1"));
        TO_ANCHOR_FACTORS.put(UnitEnum.CHARGE_MC, new BigDecimal("1e-3"));
        TO_ANCHOR_FACTORS.put(UnitEnum.CHARGE_UC, new BigDecimal("1e-6"));
        TO_ANCHOR_FACTORS.put(UnitEnum.CHARGE_NC, new BigDecimal("1e-9"));
        TO_ANCHOR_FACTORS.put(UnitEnum.CHARGE_PC, new BigDecimal("1e-12"));
    }
    
    private static void initializeCurrentFactors() {
        TO_ANCHOR_FACTORS.put(UnitEnum.CURRENT_A, new BigDecimal("1"));
        TO_ANCHOR_FACTORS.put(UnitEnum.CURRENT_MA, new BigDecimal("1e-3"));
        TO_ANCHOR_FACTORS.put(UnitEnum.CURRENT_UA, new BigDecimal("1e-6"));
        TO_ANCHOR_FACTORS.put(UnitEnum.CURRENT_KA, new BigDecimal("1e3"));
        TO_ANCHOR_FACTORS.put(UnitEnum.CURRENT_MA_CURRENT, new BigDecimal("1e6"));
    }
    
    private static void initializeVoltageFactors() {
        TO_ANCHOR_FACTORS.put(UnitEnum.VOLTAGE_V, new BigDecimal("1"));
        TO_ANCHOR_FACTORS.put(UnitEnum.VOLTAGE_MV_VOLTAGE, new BigDecimal("1e-3"));
        TO_ANCHOR_FACTORS.put(UnitEnum.VOLTAGE_UV, new BigDecimal("1e-6"));
        TO_ANCHOR_FACTORS.put(UnitEnum.VOLTAGE_KV, new BigDecimal("1e3"));
        TO_ANCHOR_FACTORS.put(UnitEnum.VOLTAGE_MV_VOLTAGE_MEGA, new BigDecimal("1e6"));
    }
    
    private static void initializeDigitalStorageFactors() {
        TO_ANCHOR_FACTORS.put(UnitEnum.DIGITAL_BIT, new BigDecimal("1").divide(new BigDecimal("8"), 10, RoundingMode.HALF_UP));
        TO_ANCHOR_FACTORS.put(UnitEnum.DIGITAL_KBIT, new BigDecimal("1000").divide(new BigDecimal("8"), 10, RoundingMode.HALF_UP));
        TO_ANCHOR_FACTORS.put(UnitEnum.DIGITAL_MBIT, new BigDecimal("1e6").divide(new BigDecimal("8"), 10, RoundingMode.HALF_UP));
        TO_ANCHOR_FACTORS.put(UnitEnum.DIGITAL_GBIT, new BigDecimal("1e9").divide(new BigDecimal("8"), 10, RoundingMode.HALF_UP));
        TO_ANCHOR_FACTORS.put(UnitEnum.DIGITAL_TBIT, new BigDecimal("1e12").divide(new BigDecimal("8"), 10, RoundingMode.HALF_UP));
        TO_ANCHOR_FACTORS.put(UnitEnum.DIGITAL_BYTE, new BigDecimal("1"));
        TO_ANCHOR_FACTORS.put(UnitEnum.DIGITAL_KBYTE, new BigDecimal("1000"));
        TO_ANCHOR_FACTORS.put(UnitEnum.DIGITAL_MBYTE, new BigDecimal("1e6"));
        TO_ANCHOR_FACTORS.put(UnitEnum.DIGITAL_GBYTE, new BigDecimal("1e9"));
        TO_ANCHOR_FACTORS.put(UnitEnum.DIGITAL_TBYTE, new BigDecimal("1e12"));
        TO_ANCHOR_FACTORS.put(UnitEnum.DIGITAL_KIBYTE, new BigDecimal("1024"));
        TO_ANCHOR_FACTORS.put(UnitEnum.DIGITAL_MIBYTE, new BigDecimal("1024").multiply(new BigDecimal("1024")));
        TO_ANCHOR_FACTORS.put(UnitEnum.DIGITAL_GIBYTE, new BigDecimal("1024").multiply(new BigDecimal("1024")).multiply(new BigDecimal("1024")));
        TO_ANCHOR_FACTORS.put(UnitEnum.DIGITAL_TIBYTE, new BigDecimal("1024").multiply(new BigDecimal("1024")).multiply(new BigDecimal("1024")).multiply(new BigDecimal("1024")));
    }
    
    private static void initializeEnergyFactors() {
        TO_ANCHOR_FACTORS.put(UnitEnum.ENERGY_WS, new BigDecimal("1"));
        TO_ANCHOR_FACTORS.put(UnitEnum.ENERGY_WM, new BigDecimal("60"));
        TO_ANCHOR_FACTORS.put(UnitEnum.ENERGY_WH, new BigDecimal("3600"));
        TO_ANCHOR_FACTORS.put(UnitEnum.ENERGY_MWH, new BigDecimal("3600"));
        TO_ANCHOR_FACTORS.put(UnitEnum.ENERGY_KWH, new BigDecimal("3600000"));
        TO_ANCHOR_FACTORS.put(UnitEnum.ENERGY_MWH_MEGA, new BigDecimal("3.6e9"));
        TO_ANCHOR_FACTORS.put(UnitEnum.ENERGY_GWH, new BigDecimal("3.6e12"));
        TO_ANCHOR_FACTORS.put(UnitEnum.ENERGY_J, new BigDecimal("1"));                  // 焦耳: 1J (基本单位)
        TO_ANCHOR_FACTORS.put(UnitEnum.ENERGY_KJ, new BigDecimal("1e3"));               // 千焦: 10³J (一千焦)
        TO_ANCHOR_FACTORS.put(UnitEnum.ENERGY_MJ, new BigDecimal("1e6"));               // 兆焦: 10⁶J (一百万焦)
        TO_ANCHOR_FACTORS.put(UnitEnum.ENERGY_GJ, new BigDecimal("1e9"));               // 吉焦: 10⁹J
        TO_ANCHOR_FACTORS.put(UnitEnum.ENERGY_CAL, new BigDecimal("1"));                // 卡路里: 作为营养学系统锚点单位
        TO_ANCHOR_FACTORS.put(UnitEnum.ENERGY_KCAL, new BigDecimal("1000"));            // 千卡: 1000 cal
        TO_ANCHOR_FACTORS.put(UnitEnum.ENERGY_ERG, new BigDecimal("1"));                // 尔格: 作为CGS系统锚点单位
        TO_ANCHOR_FACTORS.put(UnitEnum.ENERGY_FTLBF, new BigDecimal("1").divide(new BigDecimal("778.169"), 10, java.math.RoundingMode.HALF_UP)); // 英尺·磅力: 1/778.169 BTU (因为1 BTU = 778.169 ft·lbf)
        TO_ANCHOR_FACTORS.put(UnitEnum.ENERGY_KGFM, new BigDecimal("1"));                // 克力·米: 作为metric系统锚点单位
        TO_ANCHOR_FACTORS.put(UnitEnum.ENERGY_BTU, new BigDecimal("1"));                // 英热单位: 作为英制系统锚点单位
    }
    
    private static void initializeForceFactors() {
        TO_ANCHOR_FACTORS.put(UnitEnum.FORCE_UN, new BigDecimal("1e-6"));             // 微牛顿: 10⁻⁶N
        TO_ANCHOR_FACTORS.put(UnitEnum.FORCE_MN, new BigDecimal("1e-3"));       // 毫牛顿: 10⁻³N
        TO_ANCHOR_FACTORS.put(UnitEnum.FORCE_DYN, new BigDecimal("1e-5"));            // 达因: 10⁻⁵N
        TO_ANCHOR_FACTORS.put(UnitEnum.FORCE_GF, new BigDecimal("9.80665e-3"));       // 克力: 9.80665×10⁻³N
        TO_ANCHOR_FACTORS.put(UnitEnum.FORCE_N, new BigDecimal("1"));                 // 牛顿: 1N (基本单位)
        TO_ANCHOR_FACTORS.put(UnitEnum.FORCE_KN, new BigDecimal("1e3"));              // 千牛: 10³N
        TO_ANCHOR_FACTORS.put(UnitEnum.FORCE_MN_MEGA, new BigDecimal("1e6"));   // 兆牛顿: 10⁶N
        TO_ANCHOR_FACTORS.put(UnitEnum.FORCE_LBF, new BigDecimal("1"));               // 磅力: 作为英制系统锚点单位
        TO_ANCHOR_FACTORS.put(UnitEnum.FORCE_KGF, new BigDecimal("9.80665"));         // 千克力: 9.80665N
        TO_ANCHOR_FACTORS.put(UnitEnum.FORCE_KIP, new BigDecimal("1000"));            // 千磅力: 1000lbf
        TO_ANCHOR_FACTORS.put(UnitEnum.FORCE_TF, new BigDecimal("1000"));             // 吨力: 1000kgf (近似)
        TO_ANCHOR_FACTORS.put(UnitEnum.FORCE_STF, new BigDecimal("2000"));            // 短吨力: 2000lbf
        TO_ANCHOR_FACTORS.put(UnitEnum.FORCE_USTF, new BigDecimal("2000"));     // 美吨力: 2000lbf
        TO_ANCHOR_FACTORS.put(UnitEnum.FORCE_OZF, new BigDecimal("0.0625"));          // 盎司力: 1/16lbf
    }
    
    private static void initializeTorqueFactors() {
        TO_ANCHOR_FACTORS.put(UnitEnum.TORQUE_NM, new BigDecimal("1"));              // 牛顿米: 作为metric系统锚点单位
        TO_ANCHOR_FACTORS.put(UnitEnum.TORQUE_CNM, new BigDecimal("0.01"));          // 厘牛顿米: 10⁻² N·m (相对于锚点单位)
        TO_ANCHOR_FACTORS.put(UnitEnum.TORQUE_DNM, new BigDecimal("0.1"));           // 分牛顿米: 10⁻¹ N·m (相对于锚点单位)
        TO_ANCHOR_FACTORS.put(UnitEnum.TORQUE_KGM, new BigDecimal("9.807"));         // 千克米: 9.807 N·m (相对于锚点单位)
        TO_ANCHOR_FACTORS.put(UnitEnum.TORQUE_KG_CM, new BigDecimal("0.098"));       // 千克厘米: 0.098 N·m (相对于锚点单位)
        TO_ANCHOR_FACTORS.put(UnitEnum.TORQUE_LBF_FT, new BigDecimal("1.356"));      // 磅英尺: 1.356 N·m (相对于锚点单位)
        TO_ANCHOR_FACTORS.put(UnitEnum.TORQUE_LBF_IN, new BigDecimal("0.113"));      // 磅英寸: 0.113 N·m (相对于锚点单位)
        TO_ANCHOR_FACTORS.put(UnitEnum.TORQUE_OZF_IN, new BigDecimal("0.007062"));   // 盎司英寸: 0.007062 N·m (相对于锚点单位)
    }
    
    private static void initializeFrequencyFactors() {
        TO_ANCHOR_FACTORS.put(UnitEnum.FREQUENCY_HZ, new BigDecimal("1"));
        TO_ANCHOR_FACTORS.put(UnitEnum.FREQUENCY_KHZ, new BigDecimal("1e3"));
        TO_ANCHOR_FACTORS.put(UnitEnum.FREQUENCY_MHZ, new BigDecimal("1e6"));
        TO_ANCHOR_FACTORS.put(UnitEnum.FREQUENCY_GHZ, new BigDecimal("1e9"));
        TO_ANCHOR_FACTORS.put(UnitEnum.FREQUENCY_THZ, new BigDecimal("1e12"));
        TO_ANCHOR_FACTORS.put(UnitEnum.FREQUENCY_RPM, new BigDecimal("1").divide(new BigDecimal("60"), 10, RoundingMode.HALF_UP));
        TO_ANCHOR_FACTORS.put(UnitEnum.FREQUENCY_DEG_S, new BigDecimal("1").divide(new BigDecimal("360"), 10, RoundingMode.HALF_UP));
        TO_ANCHOR_FACTORS.put(UnitEnum.FREQUENCY_RAD_S, new BigDecimal("1").divide(new BigDecimal("6.283185307179586"), 10, RoundingMode.HALF_UP));
    }
    
    private static void initializeIlluminanceFactors() {
        TO_ANCHOR_FACTORS.put(UnitEnum.ILLUMINANCE_LX, new BigDecimal("1"));
        TO_ANCHOR_FACTORS.put(UnitEnum.ILLUMINANCE_FOOTCANDLE, new BigDecimal("10.764"));
    }
    
    private static void initializeApparentPowerFactors() {
        TO_ANCHOR_FACTORS.put(UnitEnum.APPARENT_POWER_VA, new BigDecimal("1"));
        TO_ANCHOR_FACTORS.put(UnitEnum.APPARENT_POWER_MVA, new BigDecimal("1e-3"));
        TO_ANCHOR_FACTORS.put(UnitEnum.APPARENT_POWER_KVA, new BigDecimal("1e3"));
        TO_ANCHOR_FACTORS.put(UnitEnum.APPARENT_POWER_MVA_MEGA, new BigDecimal("1e6"));
        TO_ANCHOR_FACTORS.put(UnitEnum.APPARENT_POWER_GVA, new BigDecimal("1e9"));
    }
    
    private static void initializeConcentrationFactors() {
        TO_ANCHOR_FACTORS.put(UnitEnum.PARTS_PER_PPM, new BigDecimal("1e-6"));
        TO_ANCHOR_FACTORS.put(UnitEnum.PARTS_PER_PPB, new BigDecimal("1e-9"));
        TO_ANCHOR_FACTORS.put(UnitEnum.PARTS_PER_PPT, new BigDecimal("1e-12"));
        TO_ANCHOR_FACTORS.put(UnitEnum.PARTS_PER_PPQ, new BigDecimal("1e-15"));
    }
    
    private static void initializePiecesFactors() {
        TO_ANCHOR_FACTORS.put(UnitEnum.PIECES_PCS_PIECES, new BigDecimal("1"));
        TO_ANCHOR_FACTORS.put(UnitEnum.PIECES_BK_DOZ, new BigDecimal("12"));
        TO_ANCHOR_FACTORS.put(UnitEnum.PIECES_CP, new BigDecimal("2"));
        TO_ANCHOR_FACTORS.put(UnitEnum.PIECES_DOZ_DOZ, new BigDecimal("144"));
        TO_ANCHOR_FACTORS.put(UnitEnum.PIECES_DOZ, new BigDecimal("12"));
        TO_ANCHOR_FACTORS.put(UnitEnum.PIECES_GR_GR, new BigDecimal("20736"));
        TO_ANCHOR_FACTORS.put(UnitEnum.PIECES_GROS, new BigDecimal("144"));
        TO_ANCHOR_FACTORS.put(UnitEnum.PIECES_HALF_DOZEN, new BigDecimal("6"));
        TO_ANCHOR_FACTORS.put(UnitEnum.PIECES_LONG_HUNDRED, new BigDecimal("120"));
        TO_ANCHOR_FACTORS.put(UnitEnum.PIECES_REAM, new BigDecimal("500"));
        TO_ANCHOR_FACTORS.put(UnitEnum.PIECES_SCORES, new BigDecimal("20"));
        TO_ANCHOR_FACTORS.put(UnitEnum.PIECES_SM_GR, new BigDecimal("120"));
        TO_ANCHOR_FACTORS.put(UnitEnum.PIECES_TRIO, new BigDecimal("3"));
    }
    
    private static void initializeReactivePowerFactors() {
        TO_ANCHOR_FACTORS.put(UnitEnum.REACTIVE_POWER_VAR, new BigDecimal("1"));
        TO_ANCHOR_FACTORS.put(UnitEnum.REACTIVE_POWER_MVAR, new BigDecimal("1e-3"));
        TO_ANCHOR_FACTORS.put(UnitEnum.REACTIVE_POWER_KVAR, new BigDecimal("1e3"));
        TO_ANCHOR_FACTORS.put(UnitEnum.REACTIVE_POWER_MVAR_MEGA, new BigDecimal("1e6"));
        TO_ANCHOR_FACTORS.put(UnitEnum.REACTIVE_POWER_GVAR, new BigDecimal("1e9"));
    }
    
    private static void initializeReactiveEnergyFactors() {
        TO_ANCHOR_FACTORS.put(UnitEnum.REACTIVE_ENERGY_VARH, new BigDecimal("1"));
        TO_ANCHOR_FACTORS.put(UnitEnum.REACTIVE_ENERGY_MVARH, new BigDecimal("1e-3"));
        TO_ANCHOR_FACTORS.put(UnitEnum.REACTIVE_ENERGY_KVARH, new BigDecimal("1e3"));
        TO_ANCHOR_FACTORS.put(UnitEnum.REACTIVE_ENERGY_MVARH_MEGA, new BigDecimal("1e6"));
        TO_ANCHOR_FACTORS.put(UnitEnum.REACTIVE_ENERGY_GVARH, new BigDecimal("1e9"));
    }
    
    private static void initializeMassFlowRateFactors() {
        TO_ANCHOR_FACTORS.put(UnitEnum.MASS_FLOW_RATE_KG_PER_S, new BigDecimal("1"));
        TO_ANCHOR_FACTORS.put(UnitEnum.MASS_FLOW_RATE_KG_PER_MIN, new BigDecimal("1").divide(new BigDecimal("60"), 10, RoundingMode.HALF_UP));
        TO_ANCHOR_FACTORS.put(UnitEnum.MASS_FLOW_RATE_KG_PER_H, new BigDecimal("1").divide(new BigDecimal("3600"), 10, RoundingMode.HALF_UP));
        TO_ANCHOR_FACTORS.put(UnitEnum.MASS_FLOW_RATE_MT_PER_H, new BigDecimal("1000").divide(new BigDecimal("3600"), 10, RoundingMode.HALF_UP));
        TO_ANCHOR_FACTORS.put(UnitEnum.MASS_FLOW_RATE_LB_PER_S, new BigDecimal("0.453592"));
        TO_ANCHOR_FACTORS.put(UnitEnum.MASS_FLOW_RATE_LB_PER_H, new BigDecimal("0.453592").divide(new BigDecimal("3600"), 10, RoundingMode.HALF_UP));
    }
    
    private static void initializePaceFactors() {
        TO_ANCHOR_FACTORS.put(UnitEnum.PACE_S_PER_M, new BigDecimal("1"));
        TO_ANCHOR_FACTORS.put(UnitEnum.PACE_MIN_PER_KM, new BigDecimal("60").divide(new BigDecimal("1000"), 10, RoundingMode.HALF_UP));
        TO_ANCHOR_FACTORS.put(UnitEnum.PACE_S_PER_FT, new BigDecimal("1").divide(new BigDecimal("0.3048"), 10, RoundingMode.HALF_UP));
        TO_ANCHOR_FACTORS.put(UnitEnum.PACE_MIN_PER_MI, new BigDecimal("60").divide(new BigDecimal("1609.34"), 10, RoundingMode.HALF_UP));
    }
    
    private static void initializeVolumeFlowRateFactors() {
        TO_ANCHOR_FACTORS.put(UnitEnum.VOLUME_FLOW_RATE_DM3_PER_S, new BigDecimal("1")); // 锚点单位(=1 L/s)
        
        // metric volume flow rates
        TO_ANCHOR_FACTORS.put(UnitEnum.VOLUME_FLOW_RATE_MM3_PER_S, new BigDecimal("1e-6"));
        TO_ANCHOR_FACTORS.put(UnitEnum.VOLUME_FLOW_RATE_CM3_PER_S, new BigDecimal("1e-3"));
        TO_ANCHOR_FACTORS.put(UnitEnum.VOLUME_FLOW_RATE_M3_PER_S, new BigDecimal("1e3"));
        TO_ANCHOR_FACTORS.put(UnitEnum.VOLUME_FLOW_RATE_KM3_PER_S, new BigDecimal("1e12"));
        TO_ANCHOR_FACTORS.put(UnitEnum.VOLUME_FLOW_RATE_ML_PER_S, new BigDecimal("1e-3"));
        TO_ANCHOR_FACTORS.put(UnitEnum.VOLUME_FLOW_RATE_CL_PER_S, new BigDecimal("1e-2"));
        TO_ANCHOR_FACTORS.put(UnitEnum.VOLUME_FLOW_RATE_DL_PER_S, new BigDecimal("1e-1"));
        TO_ANCHOR_FACTORS.put(UnitEnum.VOLUME_FLOW_RATE_L_PER_S, new BigDecimal("1"));
        TO_ANCHOR_FACTORS.put(UnitEnum.VOLUME_FLOW_RATE_KL_PER_S, new BigDecimal("1e3"));
        TO_ANCHOR_FACTORS.put(UnitEnum.VOLUME_FLOW_RATE_L_PER_MIN, new BigDecimal("1").divide(new BigDecimal("60"), 10, RoundingMode.HALF_UP));
        TO_ANCHOR_FACTORS.put(UnitEnum.VOLUME_FLOW_RATE_L_PER_H, new BigDecimal("1").divide(new BigDecimal("3600"), 10, RoundingMode.HALF_UP));
        TO_ANCHOR_FACTORS.put(UnitEnum.VOLUME_FLOW_RATE_L_PER_D, new BigDecimal("1").divide(new BigDecimal("86400"), 10, RoundingMode.HALF_UP));
        TO_ANCHOR_FACTORS.put(UnitEnum.VOLUME_FLOW_RATE_L_PER_A, new BigDecimal("1").divide(new BigDecimal("31536000"), 10, RoundingMode.HALF_UP)); // 365*24*3600
        TO_ANCHOR_FACTORS.put(UnitEnum.VOLUME_FLOW_RATE_KL_PER_MIN, new BigDecimal("1e3").divide(new BigDecimal("60"), 10, RoundingMode.HALF_UP));
        TO_ANCHOR_FACTORS.put(UnitEnum.VOLUME_FLOW_RATE_KL_PER_H, new BigDecimal("1e3").divide(new BigDecimal("3600"), 10, RoundingMode.HALF_UP));
        TO_ANCHOR_FACTORS.put(UnitEnum.VOLUME_FLOW_RATE_DM3_PER_MIN, new BigDecimal("1").divide(new BigDecimal("60"), 10, RoundingMode.HALF_UP));
        TO_ANCHOR_FACTORS.put(UnitEnum.VOLUME_FLOW_RATE_DM3_PER_H, new BigDecimal("1").divide(new BigDecimal("3600"), 10, RoundingMode.HALF_UP));
        TO_ANCHOR_FACTORS.put(UnitEnum.VOLUME_FLOW_RATE_DM3_PER_D, new BigDecimal("1").divide(new BigDecimal("86400"), 10, RoundingMode.HALF_UP));
        TO_ANCHOR_FACTORS.put(UnitEnum.VOLUME_FLOW_RATE_DM3_PER_A, new BigDecimal("1").divide(new BigDecimal("31536000"), 10, RoundingMode.HALF_UP)); // 365*24*3600
        TO_ANCHOR_FACTORS.put(UnitEnum.VOLUME_FLOW_RATE_M3_PER_MIN, new BigDecimal("1e3").divide(new BigDecimal("60"), 10, RoundingMode.HALF_UP));
        TO_ANCHOR_FACTORS.put(UnitEnum.VOLUME_FLOW_RATE_M3_PER_H, new BigDecimal("1e3").divide(new BigDecimal("3600"), 10, RoundingMode.HALF_UP));
        TO_ANCHOR_FACTORS.put(UnitEnum.VOLUME_FLOW_RATE_M3_PER_D, new BigDecimal("1e3").divide(new BigDecimal("86400"), 10, RoundingMode.HALF_UP));
        TO_ANCHOR_FACTORS.put(UnitEnum.VOLUME_FLOW_RATE_M3_PER_A, new BigDecimal("1e3").divide(new BigDecimal("31536000"), 10, RoundingMode.HALF_UP)); // 365*24*3600
        
        // imperial volume flow rates
        TO_ANCHOR_FACTORS.put(UnitEnum.VOLUME_FLOW_RATE_TSP_PER_S, new BigDecimal("0.00492892")); // 1 tsp = 4.92892 ml
        TO_ANCHOR_FACTORS.put(UnitEnum.VOLUME_FLOW_RATE_TBS_PER_S, new BigDecimal("0.0147868")); // 1 tbsp = 3 tsp = 14.7868 ml
        TO_ANCHOR_FACTORS.put(UnitEnum.VOLUME_FLOW_RATE_IN3_PER_S, new BigDecimal("0.0163871")); // 1 in³ = 16.3871 ml
        TO_ANCHOR_FACTORS.put(UnitEnum.VOLUME_FLOW_RATE_IN3_PER_MIN, new BigDecimal("0.0163871").divide(new BigDecimal("60"), 10, RoundingMode.HALF_UP));
        TO_ANCHOR_FACTORS.put(UnitEnum.VOLUME_FLOW_RATE_IN3_PER_H, new BigDecimal("0.0163871").divide(new BigDecimal("3600"), 10, RoundingMode.HALF_UP));
        TO_ANCHOR_FACTORS.put(UnitEnum.VOLUME_FLOW_RATE_FLOZ_PER_S, new BigDecimal("0.0295735")); // 1 fl oz = 29.5735 ml
        TO_ANCHOR_FACTORS.put(UnitEnum.VOLUME_FLOW_RATE_FLOZ_PER_MIN, new BigDecimal("0.0295735").divide(new BigDecimal("60"), 10, RoundingMode.HALF_UP));
        TO_ANCHOR_FACTORS.put(UnitEnum.VOLUME_FLOW_RATE_FLOZ_PER_H, new BigDecimal("0.0295735").divide(new BigDecimal("3600"), 10, RoundingMode.HALF_UP));
        TO_ANCHOR_FACTORS.put(UnitEnum.VOLUME_FLOW_RATE_CUP_PER_S, new BigDecimal("0.236588")); // 1 cup = 8 fl oz = 236.588 ml
        TO_ANCHOR_FACTORS.put(UnitEnum.VOLUME_FLOW_RATE_PNT_PER_S, new BigDecimal("0.473176")); // 1 pint = 2 cups = 473.176 ml
        TO_ANCHOR_FACTORS.put(UnitEnum.VOLUME_FLOW_RATE_PNT_PER_MIN, new BigDecimal("0.473176").divide(new BigDecimal("60"), 10, RoundingMode.HALF_UP));
        TO_ANCHOR_FACTORS.put(UnitEnum.VOLUME_FLOW_RATE_PNT_PER_H, new BigDecimal("0.473176").divide(new BigDecimal("3600"), 10, RoundingMode.HALF_UP));
        TO_ANCHOR_FACTORS.put(UnitEnum.VOLUME_FLOW_RATE_QT_PER_S, new BigDecimal("0.946353")); // 1 quart = 2 pints = 946.353 ml
        TO_ANCHOR_FACTORS.put(UnitEnum.VOLUME_FLOW_RATE_GAL_PER_S, new BigDecimal("3.78541")); // 1 gallon = 4 quarts = 3785.41 ml
        TO_ANCHOR_FACTORS.put(UnitEnum.VOLUME_FLOW_RATE_GAL_PER_MIN, new BigDecimal("3.78541").divide(new BigDecimal("60"), 10, RoundingMode.HALF_UP));
        TO_ANCHOR_FACTORS.put(UnitEnum.VOLUME_FLOW_RATE_GAL_PER_H, new BigDecimal("3.78541").divide(new BigDecimal("3600"), 10, RoundingMode.HALF_UP));
        TO_ANCHOR_FACTORS.put(UnitEnum.VOLUME_FLOW_RATE_FT3_PER_S, new BigDecimal("28.3168")); // 1 ft³ = 28.3168 L
        TO_ANCHOR_FACTORS.put(UnitEnum.VOLUME_FLOW_RATE_FT3_PER_MIN, new BigDecimal("28.3168").divide(new BigDecimal("60"), 10, RoundingMode.HALF_UP));
        TO_ANCHOR_FACTORS.put(UnitEnum.VOLUME_FLOW_RATE_FT3_PER_H, new BigDecimal("28.3168").divide(new BigDecimal("3600"), 10, RoundingMode.HALF_UP));
        TO_ANCHOR_FACTORS.put(UnitEnum.VOLUME_FLOW_RATE_YD3_PER_S, new BigDecimal("764.555")); // 1 yd³ = 27 ft³ = 764.555 L
        TO_ANCHOR_FACTORS.put(UnitEnum.VOLUME_FLOW_RATE_YD3_PER_MIN, new BigDecimal("764.555").divide(new BigDecimal("60"), 10, RoundingMode.HALF_UP));
        TO_ANCHOR_FACTORS.put(UnitEnum.VOLUME_FLOW_RATE_YD3_PER_H, new BigDecimal("764.555").divide(new BigDecimal("3600"), 10, RoundingMode.HALF_UP));
    }
}