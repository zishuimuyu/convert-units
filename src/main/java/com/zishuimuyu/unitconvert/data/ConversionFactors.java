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
        TO_ANCHOR_FACTORS.put(UnitEnum.YM, new BigDecimal("1e-24"));
        TO_ANCHOR_FACTORS.put(UnitEnum.ZM, new BigDecimal("1e-21"));
        TO_ANCHOR_FACTORS.put(UnitEnum.AM, new BigDecimal("1e-18"));
        TO_ANCHOR_FACTORS.put(UnitEnum.FM, new BigDecimal("1e-15"));
        TO_ANCHOR_FACTORS.put(UnitEnum.PM, new BigDecimal("1e-12"));
        TO_ANCHOR_FACTORS.put(UnitEnum.NANOMETER, new BigDecimal("1e-9"));
        TO_ANCHOR_FACTORS.put(UnitEnum.UM, new BigDecimal("1e-6"));
        TO_ANCHOR_FACTORS.put(UnitEnum.ANGSTROM, new BigDecimal("1e-10"));
        TO_ANCHOR_FACTORS.put(UnitEnum.MM, new BigDecimal("1e-3"));
        TO_ANCHOR_FACTORS.put(UnitEnum.CM, new BigDecimal("1e-2"));
        TO_ANCHOR_FACTORS.put(UnitEnum.DM, new BigDecimal("1e-1"));
        TO_ANCHOR_FACTORS.put(UnitEnum.M, new BigDecimal("1"));
        TO_ANCHOR_FACTORS.put(UnitEnum.KM, new BigDecimal("1e3"));
        TO_ANCHOR_FACTORS.put(UnitEnum.MM_METRIC, new BigDecimal("1e6"));
        TO_ANCHOR_FACTORS.put(UnitEnum.GM, new BigDecimal("1e9"));
        TO_ANCHOR_FACTORS.put(UnitEnum.MIL, new BigDecimal("1").divide(new BigDecimal("12000"), 10, RoundingMode.HALF_UP));
        TO_ANCHOR_FACTORS.put(UnitEnum.IN, new BigDecimal("1").divide(new BigDecimal("12"), 10, RoundingMode.HALF_UP));
        TO_ANCHOR_FACTORS.put(UnitEnum.YD, new BigDecimal("3"));
        TO_ANCHOR_FACTORS.put(UnitEnum.FT_US, new BigDecimal("1200").divide(new BigDecimal("3937"), 10, RoundingMode.HALF_UP));
        TO_ANCHOR_FACTORS.put(UnitEnum.FT, new BigDecimal("1").divide(new BigDecimal("3"), 10, RoundingMode.HALF_UP));
        TO_ANCHOR_FACTORS.put(UnitEnum.HAND, new BigDecimal("0.1016"));
        TO_ANCHOR_FACTORS.put(UnitEnum.FATHOM, new BigDecimal("6"));
        TO_ANCHOR_FACTORS.put(UnitEnum.FUR, new BigDecimal("201.168"));
        TO_ANCHOR_FACTORS.put(UnitEnum.MI, new BigDecimal("5280"));
        TO_ANCHOR_FACTORS.put(UnitEnum.NMI, new BigDecimal("1852"));
        TO_ANCHOR_FACTORS.put(UnitEnum.AU, new BigDecimal("149597870700"));
        TO_ANCHOR_FACTORS.put(UnitEnum.LY, new BigDecimal("9460730472580800"));
        TO_ANCHOR_FACTORS.put(UnitEnum.PARSEC, new BigDecimal("30856775814913700"));
        TO_ANCHOR_FACTORS.put(UnitEnum.YM_METRIC, new BigDecimal("1e24"));
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
        TO_ANCHOR_FACTORS.put(UnitEnum.MCG, new BigDecimal("1").divide(new BigDecimal("1e6"), 10, RoundingMode.HALF_UP));
        TO_ANCHOR_FACTORS.put(UnitEnum.MG, new BigDecimal("1").divide(new BigDecimal("1e3"), 10, RoundingMode.HALF_UP));
        TO_ANCHOR_FACTORS.put(UnitEnum.G, new BigDecimal("1"));
        TO_ANCHOR_FACTORS.put(UnitEnum.KG, new BigDecimal("1e3"));
        TO_ANCHOR_FACTORS.put(UnitEnum.TONNE, new BigDecimal("1e6"));
        TO_ANCHOR_FACTORS.put(UnitEnum.OZ, new BigDecimal("28.3495"));
        TO_ANCHOR_FACTORS.put(UnitEnum.LB, new BigDecimal("453.592"));
        TO_ANCHOR_FACTORS.put(UnitEnum.ST, new BigDecimal("6350.29"));
        TO_ANCHOR_FACTORS.put(UnitEnum.GR, new BigDecimal("0.0648"));  // 6.48×10⁻⁵ kg = 0.0648 g
        TO_ANCHOR_FACTORS.put(UnitEnum.DR, new BigDecimal("1.77"));     // 1.77×10⁻³ kg = 1.77 g
        TO_ANCHOR_FACTORS.put(UnitEnum.USTF, new BigDecimal("907185")); // 907.185 kg = 907185 g
        TO_ANCHOR_FACTORS.put(UnitEnum.LT, new BigDecimal("1016047"));  // 1016.047 kg = 1016047 g
        TO_ANCHOR_FACTORS.put(UnitEnum.QIAN, new BigDecimal("5.0"));    // 5.0×10⁻³ kg = 5.0 g
        TO_ANCHOR_FACTORS.put(UnitEnum.LIANG, new BigDecimal("50.0"));   // 5.0×10⁻² kg = 50.0 g
        TO_ANCHOR_FACTORS.put(UnitEnum.JIN, new BigDecimal("500.0"));   // 0.5 kg = 500.0 g
        TO_ANCHOR_FACTORS.put(UnitEnum.DAN, new BigDecimal("50000.0")); // 50 kg = 50000 g
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
        TO_ANCHOR_FACTORS.put(UnitEnum.MM3, new BigDecimal("1").divide(new BigDecimal("1e6"), 10, RoundingMode.HALF_UP));
        TO_ANCHOR_FACTORS.put(UnitEnum.CM3, new BigDecimal("1").divide(new BigDecimal("1e3"), 10, RoundingMode.HALF_UP));
        TO_ANCHOR_FACTORS.put(UnitEnum.DM3, new BigDecimal("1"));
        TO_ANCHOR_FACTORS.put(UnitEnum.ML, new BigDecimal("1").divide(new BigDecimal("1e3"), 10, RoundingMode.HALF_UP));
        TO_ANCHOR_FACTORS.put(UnitEnum.CL, new BigDecimal("1").divide(new BigDecimal("100"), 10, RoundingMode.HALF_UP));
        TO_ANCHOR_FACTORS.put(UnitEnum.DL, new BigDecimal("1").divide(new BigDecimal("10"), 10, RoundingMode.HALF_UP));
        TO_ANCHOR_FACTORS.put(UnitEnum.L, new BigDecimal("1"));
        TO_ANCHOR_FACTORS.put(UnitEnum.KL, new BigDecimal("1e3"));
        TO_ANCHOR_FACTORS.put(UnitEnum.ML_MEGA, new BigDecimal("1e6"));
        TO_ANCHOR_FACTORS.put(UnitEnum.ML_GIGA, new BigDecimal("1e9"));
        TO_ANCHOR_FACTORS.put(UnitEnum.M3, new BigDecimal("1e3"));
        TO_ANCHOR_FACTORS.put(UnitEnum.KM3, new BigDecimal("1e12"));
        TO_ANCHOR_FACTORS.put(UnitEnum.KRM, new BigDecimal("0.005"));
        TO_ANCHOR_FACTORS.put(UnitEnum.TSK, new BigDecimal("0.015"));
        TO_ANCHOR_FACTORS.put(UnitEnum.MSK, new BigDecimal("0.03"));
        TO_ANCHOR_FACTORS.put(UnitEnum.KKP, new BigDecimal("0.15"));
        TO_ANCHOR_FACTORS.put(UnitEnum.GLAS, new BigDecimal("0.2"));
        TO_ANCHOR_FACTORS.put(UnitEnum.KANNA, new BigDecimal("2.617"));
        TO_ANCHOR_FACTORS.put(UnitEnum.TSP, new BigDecimal("1").divide(new BigDecimal("6"), 10, RoundingMode.HALF_UP));
        TO_ANCHOR_FACTORS.put(UnitEnum.TBS, new BigDecimal("1").divide(new BigDecimal("2"), 10, RoundingMode.HALF_UP));
        TO_ANCHOR_FACTORS.put(UnitEnum.IN3, new BigDecimal("0.55411"));
        TO_ANCHOR_FACTORS.put(UnitEnum.FLOZ, new BigDecimal("1"));
        TO_ANCHOR_FACTORS.put(UnitEnum.CUP, new BigDecimal("8"));
        TO_ANCHOR_FACTORS.put(UnitEnum.PNT, new BigDecimal("16"));
        TO_ANCHOR_FACTORS.put(UnitEnum.QT, new BigDecimal("32"));
        TO_ANCHOR_FACTORS.put(UnitEnum.GAL, new BigDecimal("128"));
        TO_ANCHOR_FACTORS.put(UnitEnum.FT3, new BigDecimal("957.506"));
        TO_ANCHOR_FACTORS.put(UnitEnum.YD3, new BigDecimal("25852.7"));
        
        // 石油及中国传统体积单位
        TO_ANCHOR_FACTORS.put(UnitEnum.BBL, new BigDecimal("158.987"));          // 石油桶: 158.987 dm³ (相对于锚点单位dm³)
        TO_ANCHOR_FACTORS.put(UnitEnum.CU, new BigDecimal("0.000001"));          // 市撮: 10⁻⁶ m³ = 0.001 dm³ (相对于锚点单位dm³)
        TO_ANCHOR_FACTORS.put(UnitEnum.SHO, new BigDecimal("0.00001"));          // 市勺: 10⁻⁵ m³ = 0.01 dm³ (相对于锚点单位dm³)
        TO_ANCHOR_FACTORS.put(UnitEnum.HE, new BigDecimal("0.0001"));            // 市合: 10⁻⁴ m³ = 0.1 dm³ (相对于锚点单位dm³)
        TO_ANCHOR_FACTORS.put(UnitEnum.TO, new BigDecimal("0.01"));              // 市斗: 10⁻² m³ = 10 dm³ (相对于锚点单位dm³)
        TO_ANCHOR_FACTORS.put(UnitEnum.SEKI, new BigDecimal("0.1"));             // 市石: 10⁻¹ m³ = 100 dm³ (相对于锚点单位dm³)
    }
    
    private static void initializeAreaFactors() {
        TO_ANCHOR_FACTORS.put(UnitEnum.MM2, new BigDecimal("1e-6"));
        TO_ANCHOR_FACTORS.put(UnitEnum.CM2, new BigDecimal("1e-4"));
        TO_ANCHOR_FACTORS.put(UnitEnum.M2, new BigDecimal("1"));
        TO_ANCHOR_FACTORS.put(UnitEnum.HA, new BigDecimal("1e4"));
        TO_ANCHOR_FACTORS.put(UnitEnum.KM2, new BigDecimal("1e6"));
        TO_ANCHOR_FACTORS.put(UnitEnum.IN2, new BigDecimal("1").divide(new BigDecimal("144"), 10, RoundingMode.HALF_UP));
        TO_ANCHOR_FACTORS.put(UnitEnum.FT2, new BigDecimal("1"));
        TO_ANCHOR_FACTORS.put(UnitEnum.YD2, new BigDecimal("9"));
        TO_ANCHOR_FACTORS.put(UnitEnum.MI2, new BigDecimal("27878400"));
        TO_ANCHOR_FACTORS.put(UnitEnum.ACRE, new BigDecimal("43560"));
        TO_ANCHOR_FACTORS.put(UnitEnum.ROD, new BigDecimal("272.25"));
    }
    
    private static void initializeTimeFactors() {
        // 极短时间单位
        TO_ANCHOR_FACTORS.put(UnitEnum.YS, new BigDecimal("1e-24"));  // 1幺秒 = 10^-24秒
        TO_ANCHOR_FACTORS.put(UnitEnum.ZS, new BigDecimal("1e-21"));  // 1仄秒 = 10^-21秒
        TO_ANCHOR_FACTORS.put(UnitEnum.AS, new BigDecimal("1e-18"));  // 1阿秒 = 10^-18秒
        TO_ANCHOR_FACTORS.put(UnitEnum.TP, new BigDecimal("1e12"));   // 1拍秒 = 10^12秒
        TO_ANCHOR_FACTORS.put(UnitEnum.FS, new BigDecimal("1e-15"));  // 1飞秒 = 10^-15秒
        TO_ANCHOR_FACTORS.put(UnitEnum.PS, new BigDecimal("1e-12"));  // 1皮秒 = 10^-12秒
        
        // 短时间单位
        TO_ANCHOR_FACTORS.put(UnitEnum.NS, new BigDecimal("1e-9"));   // 1纳秒 = 10^-9秒
        TO_ANCHOR_FACTORS.put(UnitEnum.US, new BigDecimal("1e-6"));   // 1微秒 = 10^-6秒
        TO_ANCHOR_FACTORS.put(UnitEnum.MU, new BigDecimal("1e-6"));   // 1微秒 = 10^-6秒
        TO_ANCHOR_FACTORS.put(UnitEnum.MS, new BigDecimal("1e-3"));   // 1毫秒 = 10^-3秒
        
        // 基本时间单位
        TO_ANCHOR_FACTORS.put(UnitEnum.S, new BigDecimal("1"));       // 1秒 = 1秒
        TO_ANCHOR_FACTORS.put(UnitEnum.MIN, new BigDecimal("60"));    // 1分钟 = 60秒
        TO_ANCHOR_FACTORS.put(UnitEnum.H, new BigDecimal("3600"));    // 1小时 = 3600秒
        TO_ANCHOR_FACTORS.put(UnitEnum.DAY, new BigDecimal("86400")); // 1天 = 86400秒
        TO_ANCHOR_FACTORS.put(UnitEnum.WEEK, new BigDecimal("604800"));// 1周 = 604800秒
        TO_ANCHOR_FACTORS.put(UnitEnum.FORTNIGHT, new BigDecimal("1209600")); // 1十四夜 = 14天 = 1209600秒
        TO_ANCHOR_FACTORS.put(UnitEnum.KE, new BigDecimal("900"));    // 1刻 = 15分钟 = 900秒
        TO_ANCHOR_FACTORS.put(UnitEnum.SHICHEN, new BigDecimal("7200")); // 1时辰 = 2小时 = 7200秒
        TO_ANCHOR_FACTORS.put(UnitEnum.LUNARMONTH, new BigDecimal("2551443")); // 1农历月 ≈ 29.53天 ≈ 2551443秒
        TO_ANCHOR_FACTORS.put(UnitEnum.MONTH, new BigDecimal("2629800")); // 1月 ≈ 30.44天 ≈ 2629800秒
        TO_ANCHOR_FACTORS.put(UnitEnum.QUARTER, new BigDecimal("7889400")); // 1季度 ≈ 91.31天 ≈ 7889400秒
        TO_ANCHOR_FACTORS.put(UnitEnum.YEAR, new BigDecimal("31557600")); // 1年 ≈ 365.25天 ≈ 31557600秒
        TO_ANCHOR_FACTORS.put(UnitEnum.CENTURY, new BigDecimal("3155760000")); // 1世纪 = 100年 ≈ 3155760000秒
        TO_ANCHOR_FACTORS.put(UnitEnum.MILLENNIUM, new BigDecimal("31557600000")); // 1千年 = 1000年 ≈ 31557600000秒
        TO_ANCHOR_FACTORS.put(UnitEnum.MEGAANNUM, new BigDecimal("31557600000000")); // 1百万年 = 1000000年 ≈ 31557600000000秒
    }
    
    private static void initializeTemperatureFactors() {
        // 保留兰金温标，但使用RA（兰氏度）代替原来的R
        // 注意：原来的R被重新定义为RA（兰氏度），所以不再需要此处的条目
        // 温度转换由TemperatureConversionStrategy单独处理
    }
    
    private static void initializeSpeedFactors() {
        // 设置M_S为metric系统锚点单位
        TO_ANCHOR_FACTORS.put(UnitEnum.M_S, new BigDecimal("1"));              // 米/秒: 作为metric系统锚点单位
        TO_ANCHOR_FACTORS.put(UnitEnum.MM_S, new BigDecimal("0.001"));          // 毫米/秒: 0.001m/s (相对于metric锚点)
        TO_ANCHOR_FACTORS.put(UnitEnum.CM_S, new BigDecimal("0.01"));           // 厘米/秒: 0.01m/s (相对于metric锚点)
        TO_ANCHOR_FACTORS.put(UnitEnum.DM_S, new BigDecimal("0.1"));            // 分米/秒: 0.1m/s (相对于metric锚点)
        TO_ANCHOR_FACTORS.put(UnitEnum.DAM_S, new BigDecimal("10"));            // 十米/秒: 10m/s (相对于metric锚点)
        TO_ANCHOR_FACTORS.put(UnitEnum.HM_S, new BigDecimal("100"));             // 百米/秒: 100m/s (相对于metric锚点)
        TO_ANCHOR_FACTORS.put(UnitEnum.KM_S, new BigDecimal("1000"));            // 千米/秒: 1000m/s (相对于metric锚点)
        TO_ANCHOR_FACTORS.put(UnitEnum.KM_H, new BigDecimal("0.2777777778"));    // 千米/时: 1/3.6 m/s (相对于metric锚点)
        TO_ANCHOR_FACTORS.put(UnitEnum.KM_MIN, new BigDecimal("16.666666667"));  // 千米/分: 1000/60 m/s (相对于metric锚点)
        TO_ANCHOR_FACTORS.put(UnitEnum.M_MIN, new BigDecimal("0.0166666667"));   // 米/分: 1/60 m/s (相对于metric锚点)
        TO_ANCHOR_FACTORS.put(UnitEnum.CM_MIN, new BigDecimal("0.0001666667"));  // 厘米/分: 0.01/60 m/s (相对于metric锚点)
        TO_ANCHOR_FACTORS.put(UnitEnum.MM_MIN, new BigDecimal("0.0000166667"));  // 毫米/分: 0.001/60 m/s (相对于metric锚点)
        TO_ANCHOR_FACTORS.put(UnitEnum.MM_H, new BigDecimal("2.777777778e-7"));  // 毫米/时: 0.001/3600 m/s (相对于metric锚点)
        TO_ANCHOR_FACTORS.put(UnitEnum.MACH, new BigDecimal("343"));               // 马赫: 343m/s (相对于metric锚点)
        TO_ANCHOR_FACTORS.put(UnitEnum.LIGHT_SPEED, new BigDecimal("299792458"));          // 光速: 299792458m/s (相对于metric锚点)
        
        // 设置FPS为imperial系统锚点单位
        TO_ANCHOR_FACTORS.put(UnitEnum.FPS, new BigDecimal("1"));                // 英尺/秒: 作为imperial系统锚点单位
        TO_ANCHOR_FACTORS.put(UnitEnum.FPM, new BigDecimal("0.0166666667"));     // 英尺/分: 1/60 fps (相对于imperial锚点)
        TO_ANCHOR_FACTORS.put(UnitEnum.IN_S, new BigDecimal("0.0833333333"));    // 英寸/秒: 1/12 fps (相对于imperial锚点)
        TO_ANCHOR_FACTORS.put(UnitEnum.YD_S, new BigDecimal("3"));               // 码/秒: 3 fps (相对于imperial锚点)
        TO_ANCHOR_FACTORS.put(UnitEnum.MPH, new BigDecimal("1.4666666667"));     // 英里/时: 5280/3600 fps (相对于imperial锚点)
        TO_ANCHOR_FACTORS.put(UnitEnum.MI_S, new BigDecimal("5280"));            // 英里/秒: 5280 fps (相对于imperial锚点)
        TO_ANCHOR_FACTORS.put(UnitEnum.MI_MIN, new BigDecimal("88"));            // 英里/分: 5280/60 fps (相对于imperial锚点)
        TO_ANCHOR_FACTORS.put(UnitEnum.IN_H, new BigDecimal("2.314814815e-5"));  // 英寸/时: 1/(12*3600) fps (相对于imperial锚点)
        TO_ANCHOR_FACTORS.put(UnitEnum.NMI_H, new BigDecimal("1.6878098571"));   // 海里/时: 6076.12/3600 fps (相对于imperial锚点)
        TO_ANCHOR_FACTORS.put(UnitEnum.KT, new BigDecimal("1.6878098571"));      // 节: 6076.12/3600 fps (相对于imperial锚点)
    }
    
    private static void initializeAccelerationFactors() {
        TO_ANCHOR_FACTORS.put(UnitEnum.M_S2, new BigDecimal("1"));              // 米/秒²: 作为metric系统锚点单位
        TO_ANCHOR_FACTORS.put(UnitEnum.CM_S2, new BigDecimal("0.01"));          // 厘米/秒²: 0.01m/s² (相对于metric锚点)
        TO_ANCHOR_FACTORS.put(UnitEnum.MM_S2, new BigDecimal("0.001"));          // 毫米/秒²: 0.001m/s² (相对于metric锚点)
        TO_ANCHOR_FACTORS.put(UnitEnum.IN_S2, new BigDecimal("0.0254").divide(new BigDecimal("0.3048"), 10, java.math.RoundingMode.HALF_UP)); // 英寸/秒²: 相对ft/s²的比率 (0.0254/0.3048)
        TO_ANCHOR_FACTORS.put(UnitEnum.FT_S2, new BigDecimal("1"));              // 英尺/秒²: 作为imperial系统锚点单位
        TO_ANCHOR_FACTORS.put(UnitEnum.G_FORCE, new BigDecimal("9.80665"));      // 重力加速度: 9.80665m/s² (相对于metric锚点)
        TO_ANCHOR_FACTORS.put(UnitEnum.G0, new BigDecimal("9.80665"));           // 标准重力: 9.80665m/s² (相对于metric锚点)
        TO_ANCHOR_FACTORS.put(UnitEnum.GAL_ACC, new BigDecimal("0.01"));             // 伽: 0.01m/s² (相对于metric锚点)
    }
    
    private static void initializeAngleFactors() {
        TO_ANCHOR_FACTORS.put(UnitEnum.RAD, new BigDecimal("180").divide(new BigDecimal("3.141592653589793"), 10, RoundingMode.HALF_UP));
        TO_ANCHOR_FACTORS.put(UnitEnum.DEG, new BigDecimal("1"));
        TO_ANCHOR_FACTORS.put(UnitEnum.ARCMIN, new BigDecimal("1").divide(new BigDecimal("60"), 10, RoundingMode.HALF_UP));
        TO_ANCHOR_FACTORS.put(UnitEnum.ARCSEC, new BigDecimal("1").divide(new BigDecimal("3600"), 10, RoundingMode.HALF_UP));
        TO_ANCHOR_FACTORS.put(UnitEnum.GRAD, new BigDecimal("0.9"));
    }
    
    private static void initializePowerFactors() {
        TO_ANCHOR_FACTORS.put(UnitEnum.W, new BigDecimal("1"));                  // 瓦特: 1W (基本单位)
        TO_ANCHOR_FACTORS.put(UnitEnum.PW, new BigDecimal("1e-12"));            // 皮瓦: 10⁻¹²W
        TO_ANCHOR_FACTORS.put(UnitEnum.NW, new BigDecimal("1e-9"));             // 纳瓦: 10⁻⁹W
        TO_ANCHOR_FACTORS.put(UnitEnum.UW, new BigDecimal("1e-6"));             // 微瓦: 10⁻⁶W
        TO_ANCHOR_FACTORS.put(UnitEnum.MW, new BigDecimal("1e-3"));             // 毫瓦: 10⁻³W
        TO_ANCHOR_FACTORS.put(UnitEnum.KW, new BigDecimal("1e3"));              // 千瓦: 10³W
        TO_ANCHOR_FACTORS.put(UnitEnum.MW_POWER, new BigDecimal("1e6"));        // 兆瓦: 10⁶W
        TO_ANCHOR_FACTORS.put(UnitEnum.GW, new BigDecimal("1e9"));              // 吉瓦: 10⁹W
        TO_ANCHOR_FACTORS.put(UnitEnum.TW, new BigDecimal("1e12"));             // 太瓦: 10¹²W
        TO_ANCHOR_FACTORS.put(UnitEnum.METRIC_HP, new BigDecimal("735.5"));     // 公制马力: 735.5W
        TO_ANCHOR_FACTORS.put(UnitEnum.HP, new BigDecimal("1"));                // 马力: 作为英制系统锚点单位
        TO_ANCHOR_FACTORS.put(UnitEnum.BtuPerS, new BigDecimal("1055.06").divide(new BigDecimal("745.7"), 10, java.math.RoundingMode.HALF_UP)); // 英热单位/秒: 相对HP的比率 (1055.06/745.7)
        TO_ANCHOR_FACTORS.put(UnitEnum.CAL_PER_S, new BigDecimal("4.184"));     // 卡路里/秒: 4.184W
        TO_ANCHOR_FACTORS.put(UnitEnum.KCAL_PER_H, new BigDecimal("1.163"));    // 千卡/小时: 1.163W
        TO_ANCHOR_FACTORS.put(UnitEnum.BTU_PER_H, new BigDecimal("0.2931").divide(new BigDecimal("745.7"), 10, java.math.RoundingMode.HALF_UP)); // 英热单位/小时: 相对HP的比率 (0.2931/745.7)
        TO_ANCHOR_FACTORS.put(UnitEnum.KGF_M_PER_S, new BigDecimal("9.80665")); // 公斤力·米/秒: 9.80665W
        TO_ANCHOR_FACTORS.put(UnitEnum.FTLBF_PER_S, new BigDecimal("1.35582").divide(new BigDecimal("745.7"), 10, java.math.RoundingMode.HALF_UP)); // 英尺·磅力/秒: 相对HP的比率 (1.35582/745.7)
        TO_ANCHOR_FACTORS.put(UnitEnum.FTLBF_PER_MIN, new BigDecimal("0.022597").divide(new BigDecimal("745.7"), 10, java.math.RoundingMode.HALF_UP)); // 英尺·磅力/分钟: 相对HP的比率 (0.022597/745.7)
        TO_ANCHOR_FACTORS.put(UnitEnum.ERG_PER_S, new BigDecimal("1e-7"));      // 尔格/秒: 10⁻⁷W
    }
    
    private static void initializePressureFactors() {
        TO_ANCHOR_FACTORS.put(UnitEnum.PA, new BigDecimal("1"));                 // 帕斯卡: 1Pa (基本单位)
        TO_ANCHOR_FACTORS.put(UnitEnum.HPa, new BigDecimal("100"));              // 百帕: 10²Pa (一百帕)
        TO_ANCHOR_FACTORS.put(UnitEnum.KPA, new BigDecimal("1000"));             // 千帕: 10³Pa (一千帕)
        TO_ANCHOR_FACTORS.put(UnitEnum.MPA, new BigDecimal("1000000"));          // 兆帕: 10⁶Pa (一百万帕)
        TO_ANCHOR_FACTORS.put(UnitEnum.GPA, new BigDecimal("1000000000"));       // 吉帕: 10⁹Pa
        TO_ANCHOR_FACTORS.put(UnitEnum.MBAR, new BigDecimal("100"));             // 毫巴: 10²Pa (百帕)
        TO_ANCHOR_FACTORS.put(UnitEnum.BAR, new BigDecimal("100000"));           // 巴: 10⁵Pa (十万帕)
        TO_ANCHOR_FACTORS.put(UnitEnum.MMHG, new BigDecimal("133.322"));        // 毫米汞柱: 133.322Pa
        TO_ANCHOR_FACTORS.put(UnitEnum.TORR, new BigDecimal("133.32236842105263")); // 托: (101325/760) Pa ≈ 133.322 Pa
        TO_ANCHOR_FACTORS.put(UnitEnum.ATM, new BigDecimal("101325"));           // 标准大气压: 101325Pa
        TO_ANCHOR_FACTORS.put(UnitEnum.KGF_PER_M2, new BigDecimal("9.80665"));  // 千克力/平方米: 9.80665Pa
        TO_ANCHOR_FACTORS.put(UnitEnum.LBF_PER_FT2, new BigDecimal("1").divide(new BigDecimal("144"), 10, java.math.RoundingMode.HALF_UP)); // 磅力/平方英尺: 1/144 psi (因为1 ft² = 144 in²)
        TO_ANCHOR_FACTORS.put(UnitEnum.LBF_PER_IN2, new BigDecimal("1"));        // 磅力/平方英寸: 作为英制系统锚点单位
        TO_ANCHOR_FACTORS.put(UnitEnum.KSI, new BigDecimal("1000"));             // 千磅力/平方英寸: 1000 psi
        TO_ANCHOR_FACTORS.put(UnitEnum.MH2O, new BigDecimal("9806.65"));        // 米水柱
        TO_ANCHOR_FACTORS.put(UnitEnum.PSI, new BigDecimal("1"));                // psi (lbf/in²) 作为英制系统锚点单位
    }
    
    private static void initializeQuantityFactors() {
        TO_ANCHOR_FACTORS.put(UnitEnum.EA, new BigDecimal("1"));
        TO_ANCHOR_FACTORS.put(UnitEnum.SINGLE, new BigDecimal("1"));
        TO_ANCHOR_FACTORS.put(UnitEnum.DOZEN, new BigDecimal("12"));
        TO_ANCHOR_FACTORS.put(UnitEnum.PCS, new BigDecimal("1"));
        TO_ANCHOR_FACTORS.put(UnitEnum.PAIR, new BigDecimal("2"));
        TO_ANCHOR_FACTORS.put(UnitEnum.SET, new BigDecimal("1"));
    }
    
    private static void initializeChargeFactors() {
        TO_ANCHOR_FACTORS.put(UnitEnum.COULOMB, new BigDecimal("1"));
        TO_ANCHOR_FACTORS.put(UnitEnum.MC, new BigDecimal("1e-3"));
        TO_ANCHOR_FACTORS.put(UnitEnum.UC, new BigDecimal("1e-6"));
        TO_ANCHOR_FACTORS.put(UnitEnum.NC, new BigDecimal("1e-9"));
        TO_ANCHOR_FACTORS.put(UnitEnum.PC, new BigDecimal("1e-12"));
    }
    
    private static void initializeCurrentFactors() {
        TO_ANCHOR_FACTORS.put(UnitEnum.A, new BigDecimal("1"));
        TO_ANCHOR_FACTORS.put(UnitEnum.MA, new BigDecimal("1e-3"));
        TO_ANCHOR_FACTORS.put(UnitEnum.UA, new BigDecimal("1e-6"));
        TO_ANCHOR_FACTORS.put(UnitEnum.KA, new BigDecimal("1e3"));
        TO_ANCHOR_FACTORS.put(UnitEnum.MA_CURRENT, new BigDecimal("1e6"));
    }
    
    private static void initializeVoltageFactors() {
        TO_ANCHOR_FACTORS.put(UnitEnum.V, new BigDecimal("1"));
        TO_ANCHOR_FACTORS.put(UnitEnum.MV_VOLTAGE, new BigDecimal("1e-3"));
        TO_ANCHOR_FACTORS.put(UnitEnum.UV, new BigDecimal("1e-6"));
        TO_ANCHOR_FACTORS.put(UnitEnum.KV, new BigDecimal("1e3"));
        TO_ANCHOR_FACTORS.put(UnitEnum.MV_VOLTAGE_MEGA, new BigDecimal("1e6"));
    }
    
    private static void initializeDigitalStorageFactors() {
        TO_ANCHOR_FACTORS.put(UnitEnum.BIT, new BigDecimal("1").divide(new BigDecimal("8"), 10, RoundingMode.HALF_UP));
        TO_ANCHOR_FACTORS.put(UnitEnum.KBIT, new BigDecimal("1000").divide(new BigDecimal("8"), 10, RoundingMode.HALF_UP));
        TO_ANCHOR_FACTORS.put(UnitEnum.MBIT, new BigDecimal("1e6").divide(new BigDecimal("8"), 10, RoundingMode.HALF_UP));
        TO_ANCHOR_FACTORS.put(UnitEnum.GBIT, new BigDecimal("1e9").divide(new BigDecimal("8"), 10, RoundingMode.HALF_UP));
        TO_ANCHOR_FACTORS.put(UnitEnum.TBIT, new BigDecimal("1e12").divide(new BigDecimal("8"), 10, RoundingMode.HALF_UP));
        TO_ANCHOR_FACTORS.put(UnitEnum.BYTE, new BigDecimal("1"));
        TO_ANCHOR_FACTORS.put(UnitEnum.KBYTE, new BigDecimal("1000"));
        TO_ANCHOR_FACTORS.put(UnitEnum.MBYTE, new BigDecimal("1e6"));
        TO_ANCHOR_FACTORS.put(UnitEnum.GBYTE, new BigDecimal("1e9"));
        TO_ANCHOR_FACTORS.put(UnitEnum.TBYTE, new BigDecimal("1e12"));
        TO_ANCHOR_FACTORS.put(UnitEnum.KIBYTE, new BigDecimal("1024"));
        TO_ANCHOR_FACTORS.put(UnitEnum.MIBYTE, new BigDecimal("1024").multiply(new BigDecimal("1024")));
        TO_ANCHOR_FACTORS.put(UnitEnum.GIBYTE, new BigDecimal("1024").multiply(new BigDecimal("1024")).multiply(new BigDecimal("1024")));
        TO_ANCHOR_FACTORS.put(UnitEnum.TIBYTE, new BigDecimal("1024").multiply(new BigDecimal("1024")).multiply(new BigDecimal("1024")).multiply(new BigDecimal("1024")));
    }
    
    private static void initializeEnergyFactors() {
        TO_ANCHOR_FACTORS.put(UnitEnum.WS, new BigDecimal("1"));
        TO_ANCHOR_FACTORS.put(UnitEnum.WM, new BigDecimal("60"));
        TO_ANCHOR_FACTORS.put(UnitEnum.WH, new BigDecimal("3600"));
        TO_ANCHOR_FACTORS.put(UnitEnum.MWH, new BigDecimal("3600"));
        TO_ANCHOR_FACTORS.put(UnitEnum.KWH, new BigDecimal("3600000"));
        TO_ANCHOR_FACTORS.put(UnitEnum.MWH_MEGA, new BigDecimal("3.6e9"));
        TO_ANCHOR_FACTORS.put(UnitEnum.GWH, new BigDecimal("3.6e12"));
        TO_ANCHOR_FACTORS.put(UnitEnum.J, new BigDecimal("1"));                  // 焦耳: 1J (基本单位)
        TO_ANCHOR_FACTORS.put(UnitEnum.KJ, new BigDecimal("1e3"));               // 千焦: 10³J (一千焦)
        TO_ANCHOR_FACTORS.put(UnitEnum.MJ, new BigDecimal("1e6"));               // 兆焦: 10⁶J (一百万焦)
        TO_ANCHOR_FACTORS.put(UnitEnum.GJ, new BigDecimal("1e9"));               // 吉焦: 10⁹J
        TO_ANCHOR_FACTORS.put(UnitEnum.CAL, new BigDecimal("1"));                // 卡路里: 作为营养学系统锚点单位
        TO_ANCHOR_FACTORS.put(UnitEnum.KCAL, new BigDecimal("1000"));            // 千卡: 1000 cal
        TO_ANCHOR_FACTORS.put(UnitEnum.ERG, new BigDecimal("1"));                // 尔格: 作为CGS系统锚点单位
        TO_ANCHOR_FACTORS.put(UnitEnum.FTLBF, new BigDecimal("1").divide(new BigDecimal("778.169"), 10, java.math.RoundingMode.HALF_UP)); // 英尺·磅力: 1/778.169 BTU (因为1 BTU = 778.169 ft·lbf)
        TO_ANCHOR_FACTORS.put(UnitEnum.KGFM, new BigDecimal("1"));                // 克力·米: 作为metric系统锚点单位
        TO_ANCHOR_FACTORS.put(UnitEnum.BTU, new BigDecimal("1"));                // 英热单位: 作为英制系统锚点单位
    }
    
    private static void initializeForceFactors() {
        TO_ANCHOR_FACTORS.put(UnitEnum.UN, new BigDecimal("1e-6"));             // 微牛顿: 10⁻⁶N
        TO_ANCHOR_FACTORS.put(UnitEnum.MN_FORCE, new BigDecimal("1e-3"));       // 毫牛顿: 10⁻³N
        TO_ANCHOR_FACTORS.put(UnitEnum.DYN, new BigDecimal("1e-5"));            // 达因: 10⁻⁵N
        TO_ANCHOR_FACTORS.put(UnitEnum.GF, new BigDecimal("9.80665e-3"));       // 克力: 9.80665×10⁻³N
        TO_ANCHOR_FACTORS.put(UnitEnum.N, new BigDecimal("1"));                 // 牛顿: 1N (基本单位)
        TO_ANCHOR_FACTORS.put(UnitEnum.KN, new BigDecimal("1e3"));              // 千牛: 10³N
        TO_ANCHOR_FACTORS.put(UnitEnum.MN_FORCE_MEGA, new BigDecimal("1e6"));   // 兆牛顿: 10⁶N
        TO_ANCHOR_FACTORS.put(UnitEnum.LBF, new BigDecimal("1"));               // 磅力: 作为英制系统锚点单位
        TO_ANCHOR_FACTORS.put(UnitEnum.KGF, new BigDecimal("9.80665"));         // 千克力: 9.80665N
        TO_ANCHOR_FACTORS.put(UnitEnum.KIP, new BigDecimal("1000"));            // 千磅力: 1000lbf
        TO_ANCHOR_FACTORS.put(UnitEnum.TF, new BigDecimal("1000"));             // 吨力: 1000kgf (近似)
        TO_ANCHOR_FACTORS.put(UnitEnum.STF, new BigDecimal("2000"));            // 短吨力: 2000lbf
        TO_ANCHOR_FACTORS.put(UnitEnum.USTF_FORCE, new BigDecimal("2000"));     // 美吨力: 2000lbf
        TO_ANCHOR_FACTORS.put(UnitEnum.OZF, new BigDecimal("0.0625"));          // 盎司力: 1/16lbf
    }
    
    private static void initializeTorqueFactors() {
        TO_ANCHOR_FACTORS.put(UnitEnum.NM, new BigDecimal("1"));              // 牛顿米: 作为metric系统锚点单位
        TO_ANCHOR_FACTORS.put(UnitEnum.CNM, new BigDecimal("0.01"));          // 厘牛顿米: 10⁻² N·m (相对于锚点单位)
        TO_ANCHOR_FACTORS.put(UnitEnum.DNM, new BigDecimal("0.1"));           // 分牛顿米: 10⁻¹ N·m (相对于锚点单位)
        TO_ANCHOR_FACTORS.put(UnitEnum.KGM, new BigDecimal("9.807"));         // 千克米: 9.807 N·m (相对于锚点单位)
        TO_ANCHOR_FACTORS.put(UnitEnum.KG_CM, new BigDecimal("0.098"));       // 千克厘米: 0.098 N·m (相对于锚点单位)
        TO_ANCHOR_FACTORS.put(UnitEnum.LBF_FT, new BigDecimal("1.356"));      // 磅英尺: 1.356 N·m (相对于锚点单位)
        TO_ANCHOR_FACTORS.put(UnitEnum.LBF_IN, new BigDecimal("0.113"));      // 磅英寸: 0.113 N·m (相对于锚点单位)
        TO_ANCHOR_FACTORS.put(UnitEnum.OZF_IN, new BigDecimal("0.007062"));   // 盎司英寸: 0.007062 N·m (相对于锚点单位)
    }
    
    private static void initializeFrequencyFactors() {
        TO_ANCHOR_FACTORS.put(UnitEnum.HZ, new BigDecimal("1"));
        TO_ANCHOR_FACTORS.put(UnitEnum.KHZ, new BigDecimal("1e3"));
        TO_ANCHOR_FACTORS.put(UnitEnum.MHZ, new BigDecimal("1e6"));
        TO_ANCHOR_FACTORS.put(UnitEnum.GHZ, new BigDecimal("1e9"));
        TO_ANCHOR_FACTORS.put(UnitEnum.THZ, new BigDecimal("1e12"));
        TO_ANCHOR_FACTORS.put(UnitEnum.RPM, new BigDecimal("1").divide(new BigDecimal("60"), 10, RoundingMode.HALF_UP));
        TO_ANCHOR_FACTORS.put(UnitEnum.DEG_S, new BigDecimal("1").divide(new BigDecimal("360"), 10, RoundingMode.HALF_UP));
        TO_ANCHOR_FACTORS.put(UnitEnum.RAD_S, new BigDecimal("1").divide(new BigDecimal("6.283185307179586"), 10, RoundingMode.HALF_UP));
    }
    
    private static void initializeIlluminanceFactors() {
        TO_ANCHOR_FACTORS.put(UnitEnum.LX, new BigDecimal("1"));
        TO_ANCHOR_FACTORS.put(UnitEnum.FOOTCANDLE, new BigDecimal("10.764"));
    }
    
    private static void initializeApparentPowerFactors() {
        TO_ANCHOR_FACTORS.put(UnitEnum.VA, new BigDecimal("1"));
        TO_ANCHOR_FACTORS.put(UnitEnum.MVA, new BigDecimal("1e-3"));
        TO_ANCHOR_FACTORS.put(UnitEnum.KVA, new BigDecimal("1e3"));
        TO_ANCHOR_FACTORS.put(UnitEnum.MVA_POWER, new BigDecimal("1e6"));
        TO_ANCHOR_FACTORS.put(UnitEnum.GVA, new BigDecimal("1e9"));
    }
    
    private static void initializeConcentrationFactors() {
        TO_ANCHOR_FACTORS.put(UnitEnum.PPM, new BigDecimal("1e-6"));
        TO_ANCHOR_FACTORS.put(UnitEnum.PPB, new BigDecimal("1e-9"));
        TO_ANCHOR_FACTORS.put(UnitEnum.PPT, new BigDecimal("1e-12"));
        TO_ANCHOR_FACTORS.put(UnitEnum.PPQ, new BigDecimal("1e-15"));
    }
    
    private static void initializePiecesFactors() {
        TO_ANCHOR_FACTORS.put(UnitEnum.PCS_PIECES, new BigDecimal("1"));
        TO_ANCHOR_FACTORS.put(UnitEnum.BK_DOZ, new BigDecimal("12"));
        TO_ANCHOR_FACTORS.put(UnitEnum.CP, new BigDecimal("2"));
        TO_ANCHOR_FACTORS.put(UnitEnum.DOZ_DOZ, new BigDecimal("144"));
        TO_ANCHOR_FACTORS.put(UnitEnum.DOZ, new BigDecimal("12"));
        TO_ANCHOR_FACTORS.put(UnitEnum.GR_GR, new BigDecimal("20736"));
        TO_ANCHOR_FACTORS.put(UnitEnum.GROS, new BigDecimal("144"));
        TO_ANCHOR_FACTORS.put(UnitEnum.HALF_DOZEN, new BigDecimal("6"));
        TO_ANCHOR_FACTORS.put(UnitEnum.LONG_HUNDRED, new BigDecimal("120"));
        TO_ANCHOR_FACTORS.put(UnitEnum.REAM, new BigDecimal("500"));
        TO_ANCHOR_FACTORS.put(UnitEnum.SCORES, new BigDecimal("20"));
        TO_ANCHOR_FACTORS.put(UnitEnum.SM_GR, new BigDecimal("120"));
        TO_ANCHOR_FACTORS.put(UnitEnum.TRIO, new BigDecimal("3"));
    }
    
    private static void initializeReactivePowerFactors() {
        TO_ANCHOR_FACTORS.put(UnitEnum.VAR, new BigDecimal("1"));
        TO_ANCHOR_FACTORS.put(UnitEnum.MVAR, new BigDecimal("1e-3"));
        TO_ANCHOR_FACTORS.put(UnitEnum.KVAR, new BigDecimal("1e3"));
        TO_ANCHOR_FACTORS.put(UnitEnum.MVAR_POWER, new BigDecimal("1e6"));
        TO_ANCHOR_FACTORS.put(UnitEnum.GVAR, new BigDecimal("1e9"));
    }
    
    private static void initializeReactiveEnergyFactors() {
        TO_ANCHOR_FACTORS.put(UnitEnum.VARH, new BigDecimal("1"));
        TO_ANCHOR_FACTORS.put(UnitEnum.MVARH, new BigDecimal("1e-3"));
        TO_ANCHOR_FACTORS.put(UnitEnum.KVARH, new BigDecimal("1e3"));
        TO_ANCHOR_FACTORS.put(UnitEnum.MVARH_POWER, new BigDecimal("1e6"));
        TO_ANCHOR_FACTORS.put(UnitEnum.GVARH, new BigDecimal("1e9"));
    }
    
    private static void initializeMassFlowRateFactors() {
        TO_ANCHOR_FACTORS.put(UnitEnum.KG_PER_S, new BigDecimal("1"));
        TO_ANCHOR_FACTORS.put(UnitEnum.KG_PER_MIN, new BigDecimal("1").divide(new BigDecimal("60"), 10, RoundingMode.HALF_UP));
        TO_ANCHOR_FACTORS.put(UnitEnum.KG_PER_H, new BigDecimal("1").divide(new BigDecimal("3600"), 10, RoundingMode.HALF_UP));
        TO_ANCHOR_FACTORS.put(UnitEnum.MT_PER_H, new BigDecimal("1000").divide(new BigDecimal("3600"), 10, RoundingMode.HALF_UP));
        TO_ANCHOR_FACTORS.put(UnitEnum.LB_PER_S, new BigDecimal("0.453592"));
        TO_ANCHOR_FACTORS.put(UnitEnum.LB_PER_H, new BigDecimal("0.453592").divide(new BigDecimal("3600"), 10, RoundingMode.HALF_UP));
    }
    
    private static void initializePaceFactors() {
        TO_ANCHOR_FACTORS.put(UnitEnum.S_PER_M, new BigDecimal("1"));
        TO_ANCHOR_FACTORS.put(UnitEnum.MIN_PER_KM, new BigDecimal("60").divide(new BigDecimal("1000"), 10, RoundingMode.HALF_UP));
        TO_ANCHOR_FACTORS.put(UnitEnum.S_PER_FT, new BigDecimal("1").divide(new BigDecimal("0.3048"), 10, RoundingMode.HALF_UP));
        TO_ANCHOR_FACTORS.put(UnitEnum.MIN_PER_MI, new BigDecimal("60").divide(new BigDecimal("1609.34"), 10, RoundingMode.HALF_UP));
    }
    
    private static void initializeVolumeFlowRateFactors() {
        TO_ANCHOR_FACTORS.put(UnitEnum.DM3_PER_S, new BigDecimal("1")); // 锚点单位(=1 L/s)
        
        // metric volume flow rates
        TO_ANCHOR_FACTORS.put(UnitEnum.MM3_PER_S, new BigDecimal("1e-6"));
        TO_ANCHOR_FACTORS.put(UnitEnum.CM3_PER_S, new BigDecimal("1e-3"));
        TO_ANCHOR_FACTORS.put(UnitEnum.M3_PER_S, new BigDecimal("1e3"));
        TO_ANCHOR_FACTORS.put(UnitEnum.KM3_PER_S, new BigDecimal("1e12"));
        TO_ANCHOR_FACTORS.put(UnitEnum.ML_PER_S, new BigDecimal("1e-3"));
        TO_ANCHOR_FACTORS.put(UnitEnum.CL_PER_S, new BigDecimal("1e-2"));
        TO_ANCHOR_FACTORS.put(UnitEnum.DL_PER_S, new BigDecimal("1e-1"));
        TO_ANCHOR_FACTORS.put(UnitEnum.L_PER_S, new BigDecimal("1"));
        TO_ANCHOR_FACTORS.put(UnitEnum.KL_PER_S, new BigDecimal("1e3"));
        TO_ANCHOR_FACTORS.put(UnitEnum.L_PER_MIN, new BigDecimal("1").divide(new BigDecimal("60"), 10, RoundingMode.HALF_UP));
        TO_ANCHOR_FACTORS.put(UnitEnum.L_PER_H, new BigDecimal("1").divide(new BigDecimal("3600"), 10, RoundingMode.HALF_UP));
        TO_ANCHOR_FACTORS.put(UnitEnum.L_PER_D, new BigDecimal("1").divide(new BigDecimal("86400"), 10, RoundingMode.HALF_UP));
        TO_ANCHOR_FACTORS.put(UnitEnum.L_PER_A, new BigDecimal("1").divide(new BigDecimal("31536000"), 10, RoundingMode.HALF_UP)); // 365*24*3600
        TO_ANCHOR_FACTORS.put(UnitEnum.KL_PER_MIN, new BigDecimal("1e3").divide(new BigDecimal("60"), 10, RoundingMode.HALF_UP));
        TO_ANCHOR_FACTORS.put(UnitEnum.KL_PER_H, new BigDecimal("1e3").divide(new BigDecimal("3600"), 10, RoundingMode.HALF_UP));
        TO_ANCHOR_FACTORS.put(UnitEnum.KL_PER_S, new BigDecimal("1e3"));
        TO_ANCHOR_FACTORS.put(UnitEnum.DM3_PER_MIN, new BigDecimal("1").divide(new BigDecimal("60"), 10, RoundingMode.HALF_UP));
        TO_ANCHOR_FACTORS.put(UnitEnum.DM3_PER_H, new BigDecimal("1").divide(new BigDecimal("3600"), 10, RoundingMode.HALF_UP));
        TO_ANCHOR_FACTORS.put(UnitEnum.DM3_PER_D, new BigDecimal("1").divide(new BigDecimal("86400"), 10, RoundingMode.HALF_UP));
        TO_ANCHOR_FACTORS.put(UnitEnum.DM3_PER_A, new BigDecimal("1").divide(new BigDecimal("31536000"), 10, RoundingMode.HALF_UP)); // 365*24*3600
        TO_ANCHOR_FACTORS.put(UnitEnum.M3_PER_MIN, new BigDecimal("1e3").divide(new BigDecimal("60"), 10, RoundingMode.HALF_UP));
        TO_ANCHOR_FACTORS.put(UnitEnum.M3_PER_H, new BigDecimal("1e3").divide(new BigDecimal("3600"), 10, RoundingMode.HALF_UP));
        TO_ANCHOR_FACTORS.put(UnitEnum.M3_PER_D, new BigDecimal("1e3").divide(new BigDecimal("86400"), 10, RoundingMode.HALF_UP));
        TO_ANCHOR_FACTORS.put(UnitEnum.M3_PER_A, new BigDecimal("1e3").divide(new BigDecimal("31536000"), 10, RoundingMode.HALF_UP)); // 365*24*3600
        
        // imperial volume flow rates
        TO_ANCHOR_FACTORS.put(UnitEnum.TSP_PER_S, new BigDecimal("0.00492892")); // 1 tsp = 4.92892 ml
        TO_ANCHOR_FACTORS.put(UnitEnum.TBS_PER_S, new BigDecimal("0.0147868")); // 1 tbsp = 3 tsp = 14.7868 ml
        TO_ANCHOR_FACTORS.put(UnitEnum.IN3_PER_S, new BigDecimal("0.0163871")); // 1 in³ = 16.3871 ml
        TO_ANCHOR_FACTORS.put(UnitEnum.IN3_PER_MIN, new BigDecimal("0.0163871").divide(new BigDecimal("60"), 10, RoundingMode.HALF_UP));
        TO_ANCHOR_FACTORS.put(UnitEnum.IN3_PER_H, new BigDecimal("0.0163871").divide(new BigDecimal("3600"), 10, RoundingMode.HALF_UP));
        TO_ANCHOR_FACTORS.put(UnitEnum.FLOZ_PER_S, new BigDecimal("0.0295735")); // 1 fl oz = 29.5735 ml
        TO_ANCHOR_FACTORS.put(UnitEnum.FLOZ_PER_MIN, new BigDecimal("0.0295735").divide(new BigDecimal("60"), 10, RoundingMode.HALF_UP));
        TO_ANCHOR_FACTORS.put(UnitEnum.FLOZ_PER_H, new BigDecimal("0.0295735").divide(new BigDecimal("3600"), 10, RoundingMode.HALF_UP));
        TO_ANCHOR_FACTORS.put(UnitEnum.CUP_PER_S, new BigDecimal("0.236588")); // 1 cup = 8 fl oz = 236.588 ml
        TO_ANCHOR_FACTORS.put(UnitEnum.PNT_PER_S, new BigDecimal("0.473176")); // 1 pint = 2 cups = 473.176 ml
        TO_ANCHOR_FACTORS.put(UnitEnum.PNT_PER_MIN, new BigDecimal("0.473176").divide(new BigDecimal("60"), 10, RoundingMode.HALF_UP));
        TO_ANCHOR_FACTORS.put(UnitEnum.PNT_PER_H, new BigDecimal("0.473176").divide(new BigDecimal("3600"), 10, RoundingMode.HALF_UP));
        TO_ANCHOR_FACTORS.put(UnitEnum.QT_PER_S, new BigDecimal("0.946353")); // 1 quart = 2 pints = 946.353 ml
        TO_ANCHOR_FACTORS.put(UnitEnum.GAL_PER_S, new BigDecimal("3.78541")); // 1 gallon = 4 quarts = 3785.41 ml
        TO_ANCHOR_FACTORS.put(UnitEnum.GAL_PER_MIN, new BigDecimal("3.78541").divide(new BigDecimal("60"), 10, RoundingMode.HALF_UP));
        TO_ANCHOR_FACTORS.put(UnitEnum.GAL_PER_H, new BigDecimal("3.78541").divide(new BigDecimal("3600"), 10, RoundingMode.HALF_UP));
        TO_ANCHOR_FACTORS.put(UnitEnum.FT3_PER_S, new BigDecimal("28.3168")); // 1 ft³ = 28.3168 L
        TO_ANCHOR_FACTORS.put(UnitEnum.FT3_PER_MIN, new BigDecimal("28.3168").divide(new BigDecimal("60"), 10, RoundingMode.HALF_UP));
        TO_ANCHOR_FACTORS.put(UnitEnum.FT3_PER_H, new BigDecimal("28.3168").divide(new BigDecimal("3600"), 10, RoundingMode.HALF_UP));
        TO_ANCHOR_FACTORS.put(UnitEnum.YD3_PER_S, new BigDecimal("764.555")); // 1 yd³ = 27 ft³ = 764.555 L
        TO_ANCHOR_FACTORS.put(UnitEnum.YD3_PER_MIN, new BigDecimal("764.555").divide(new BigDecimal("60"), 10, RoundingMode.HALF_UP));
        TO_ANCHOR_FACTORS.put(UnitEnum.YD3_PER_H, new BigDecimal("764.555").divide(new BigDecimal("3600"), 10, RoundingMode.HALF_UP));
    }
}