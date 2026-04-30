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
        TO_ANCHOR_FACTORS.put(UnitEnum.R, new BigDecimal("1"));
    }
    
    private static void initializeSpeedFactors() {
        TO_ANCHOR_FACTORS.put(UnitEnum.KM_H, new BigDecimal("1"));
        TO_ANCHOR_FACTORS.put(UnitEnum.M_S, new BigDecimal("3.6"));
        TO_ANCHOR_FACTORS.put(UnitEnum.MM_S, new BigDecimal("0.0036"));
        TO_ANCHOR_FACTORS.put(UnitEnum.CM_S, new BigDecimal("0.036"));
        TO_ANCHOR_FACTORS.put(UnitEnum.MM_H, new BigDecimal("1e-6"));
        TO_ANCHOR_FACTORS.put(UnitEnum.KM_S, new BigDecimal("3600"));
        TO_ANCHOR_FACTORS.put(UnitEnum.MPH, new BigDecimal("1"));
        TO_ANCHOR_FACTORS.put(UnitEnum.KT, new BigDecimal("1.150779"));
        TO_ANCHOR_FACTORS.put(UnitEnum.FPS, new BigDecimal("0.681818"));
        TO_ANCHOR_FACTORS.put(UnitEnum.FPM, new BigDecimal("0.0113636"));
        TO_ANCHOR_FACTORS.put(UnitEnum.IN_H, new BigDecimal("1.578e-5"));
    }
    
    private static void initializeAccelerationFactors() {
        TO_ANCHOR_FACTORS.put(UnitEnum.G_FORCE, new BigDecimal("9.80665"));
        TO_ANCHOR_FACTORS.put(UnitEnum.M_S2, new BigDecimal("1"));
        TO_ANCHOR_FACTORS.put(UnitEnum.G0, new BigDecimal("9.80665"));
    }
    
    private static void initializeAngleFactors() {
        TO_ANCHOR_FACTORS.put(UnitEnum.RAD, new BigDecimal("180").divide(new BigDecimal("3.141592653589793"), 10, RoundingMode.HALF_UP));
        TO_ANCHOR_FACTORS.put(UnitEnum.DEG, new BigDecimal("1"));
        TO_ANCHOR_FACTORS.put(UnitEnum.ARCMIN, new BigDecimal("1").divide(new BigDecimal("60"), 10, RoundingMode.HALF_UP));
        TO_ANCHOR_FACTORS.put(UnitEnum.ARCSEC, new BigDecimal("1").divide(new BigDecimal("3600"), 10, RoundingMode.HALF_UP));
        TO_ANCHOR_FACTORS.put(UnitEnum.GRAD, new BigDecimal("0.9"));
    }
    
    private static void initializePowerFactors() {
        TO_ANCHOR_FACTORS.put(UnitEnum.W, new BigDecimal("1"));
        TO_ANCHOR_FACTORS.put(UnitEnum.MW, new BigDecimal("1e-3"));
        TO_ANCHOR_FACTORS.put(UnitEnum.KW, new BigDecimal("1e3"));
        TO_ANCHOR_FACTORS.put(UnitEnum.MW_POWER, new BigDecimal("1e6"));
        TO_ANCHOR_FACTORS.put(UnitEnum.GW, new BigDecimal("1e9"));
        TO_ANCHOR_FACTORS.put(UnitEnum.METRIC_HP, new BigDecimal("735.49875"));
        TO_ANCHOR_FACTORS.put(UnitEnum.HP, new BigDecimal("745.7"));
    }
    
    private static void initializePressureFactors() {
        TO_ANCHOR_FACTORS.put(UnitEnum.PA, new BigDecimal("1"));
        TO_ANCHOR_FACTORS.put(UnitEnum.KPA, new BigDecimal("1000"));
        TO_ANCHOR_FACTORS.put(UnitEnum.MPA, new BigDecimal("1000000"));
        TO_ANCHOR_FACTORS.put(UnitEnum.HPa, new BigDecimal("100"));
        TO_ANCHOR_FACTORS.put(UnitEnum.GPA, new BigDecimal("1000000000"));
        TO_ANCHOR_FACTORS.put(UnitEnum.MBAR, new BigDecimal("0.1"));
        TO_ANCHOR_FACTORS.put(UnitEnum.BAR, new BigDecimal("100000"));
        TO_ANCHOR_FACTORS.put(UnitEnum.TORR, new BigDecimal("133.32236842105263"));
        TO_ANCHOR_FACTORS.put(UnitEnum.MH2O, new BigDecimal("9806.65"));
        TO_ANCHOR_FACTORS.put(UnitEnum.MMHG, new BigDecimal("133.322387415"));
        TO_ANCHOR_FACTORS.put(UnitEnum.PSI, new BigDecimal("6894.757293168361"));
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
        TO_ANCHOR_FACTORS.put(UnitEnum.J, new BigDecimal("1"));
        TO_ANCHOR_FACTORS.put(UnitEnum.KJ, new BigDecimal("1e3"));
        TO_ANCHOR_FACTORS.put(UnitEnum.MJ, new BigDecimal("1e6"));
        TO_ANCHOR_FACTORS.put(UnitEnum.GJ, new BigDecimal("1e9"));
        TO_ANCHOR_FACTORS.put(UnitEnum.KCAL, new BigDecimal("4184"));
        TO_ANCHOR_FACTORS.put(UnitEnum.CAL, new BigDecimal("4.184"));
    }
    
    private static void initializeForceFactors() {
        TO_ANCHOR_FACTORS.put(UnitEnum.N, new BigDecimal("1"));
        TO_ANCHOR_FACTORS.put(UnitEnum.KN, new BigDecimal("1e3"));
        TO_ANCHOR_FACTORS.put(UnitEnum.LBF, new BigDecimal("4.44822"));
        TO_ANCHOR_FACTORS.put(UnitEnum.KGF, new BigDecimal("9.80665"));
    }
    
    private static void initializeTorqueFactors() {
        TO_ANCHOR_FACTORS.put(UnitEnum.NM, new BigDecimal("1"));
        TO_ANCHOR_FACTORS.put(UnitEnum.CNM, new BigDecimal("1e-2"));
        TO_ANCHOR_FACTORS.put(UnitEnum.DNM, new BigDecimal("1e-1"));
        TO_ANCHOR_FACTORS.put(UnitEnum.KGM, new BigDecimal("9.80665"));
        TO_ANCHOR_FACTORS.put(UnitEnum.KG_CM, new BigDecimal("9.80665e-2"));
        TO_ANCHOR_FACTORS.put(UnitEnum.LBF_FT, new BigDecimal("1.35582"));
        TO_ANCHOR_FACTORS.put(UnitEnum.LBF_IN, new BigDecimal("0.112985"));
        TO_ANCHOR_FACTORS.put(UnitEnum.OZF_IN, new BigDecimal("0.006377"));
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
        TO_ANCHOR_FACTORS.put(UnitEnum.DM3_PER_S, new BigDecimal("1"));
        TO_ANCHOR_FACTORS.put(UnitEnum.MM3_PER_S, new BigDecimal("1").divide(new BigDecimal("1e6"), 10, RoundingMode.HALF_UP));
        TO_ANCHOR_FACTORS.put(UnitEnum.CM3_PER_S, new BigDecimal("1").divide(new BigDecimal("1e3"), 10, RoundingMode.HALF_UP));
        TO_ANCHOR_FACTORS.put(UnitEnum.M3_PER_S, new BigDecimal("1e3"));
        TO_ANCHOR_FACTORS.put(UnitEnum.KM3_PER_S, new BigDecimal("1e12"));
        TO_ANCHOR_FACTORS.put(UnitEnum.L_PER_MIN, new BigDecimal("1").divide(new BigDecimal("60"), 10, RoundingMode.HALF_UP));
        TO_ANCHOR_FACTORS.put(UnitEnum.KL_PER_MIN, new BigDecimal("1").divide(new BigDecimal("0.06"), 10, RoundingMode.HALF_UP));
        TO_ANCHOR_FACTORS.put(UnitEnum.DM3_PER_MIN, new BigDecimal("1").divide(new BigDecimal("60"), 10, RoundingMode.HALF_UP));
        TO_ANCHOR_FACTORS.put(UnitEnum.DM3_PER_H, new BigDecimal("1").divide(new BigDecimal("3600"), 10, RoundingMode.HALF_UP));
        TO_ANCHOR_FACTORS.put(UnitEnum.DM3_PER_D, new BigDecimal("1").divide(new BigDecimal("86400"), 10, RoundingMode.HALF_UP));
        TO_ANCHOR_FACTORS.put(UnitEnum.FT3_PER_S, new BigDecimal("28.3168"));
        TO_ANCHOR_FACTORS.put(UnitEnum.FT3_PER_MIN, new BigDecimal("28.3168").divide(new BigDecimal("60"), 10, RoundingMode.HALF_UP));
    }
}