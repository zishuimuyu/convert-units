package com.zishuimuyu.unitconvert.service;

import com.zishuimuyu.unitconvert.model.ConvertResult;
import com.zishuimuyu.unitconvert.model.ToBestOptions;
import com.zishuimuyu.unitconvert.model.UnitDescription;
import com.zishuimuyu.unitconvert.model.UnitEnum;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 单位转换服务实现类
 * 
 * 提供各种物理量单位之间的转换功能
 * 支持公制、英制等多种单位系统
 * 使用BigDecimal保证计算精度
 * 
 * 实现原理：
 * 1. 使用锚点单位(anchor unit)作为中介，将所有单位转换到统一的基准单位
 * 2. 通过转换因子(TO_ANCHOR_FACTORS)实现单位到锚点单位的转换
 * 3. 对于跨系统转换，使用SYSTEM_RATIOS进行系统间比率调整
 * 4. 温度等特殊单位使用专门的转换公式
 * 
 * @author 紫水木鱼
 * @version 1.0.0
 * @since 2026-04-24
 */
public class UnitConversionServiceImpl implements IUnitConversionService {

    /**
     * 转换到基准单位的因子映射
     * 
     * 存储每个单位到其所在测量类型基准单位的转换因子
     * 例如，对于长度单位，基准单位是米，那么这个映射存储的是各长度单位到米的转换因子
     * 使用BigDecimal确保高精度计算
     */
    private static final Map<UnitEnum, BigDecimal> TO_ANCHOR_FACTORS = new HashMap<>();
    
    /**
     * 系统间转换比率映射
     * 
     * 用于处理不同单位系统之间的转换
     * 例如：公制到英制的转换
     * 结构：测量类型 -> (系统间转换键 -> 转换比率)
     * 键格式："{源系统}_to_{目标系统}"
     */
    private static final Map<String, Map<String, BigDecimal>> SYSTEM_RATIOS = new HashMap<>();
    
    /**
     * 当前转换的源数值
     * 
     * 用于支持链式调用(.from().to())
     * 在from()方法中设置，在to()方法中使用
     */
    private BigDecimal currentValue;
    
    /**
     * 当前转换的源单位
     * 
     * 用于支持链式调用(.from().to())
     * 在from()方法中设置，在to()方法中使用
     */
    private UnitEnum currentOriginUnit;
    
    /**
     * 当前转换的目标单位
     * 
     * 用于检查操作顺序，确保先调用from()再调用to()
     * 在to()方法中设置
     */
    private UnitEnum currentDestinationUnit;

    /**
     * 静态初始化块，初始化转换因子
     * 
     * 在类加载时执行一次，初始化所有单位的转换因子
     */
    static {
        initializeFactors();
    }

    // 初始化转换因子
    private static void initializeFactors() {
        // 初始化系统间转换比率
        initializeSystemRatios();
        
        // 长度单位 - 基准单位：米
        TO_ANCHOR_FACTORS.put(UnitEnum.NANOMETER, new BigDecimal("1e-9"));      // 纳米
        TO_ANCHOR_FACTORS.put(UnitEnum.UM, new BigDecimal("1e-6"));      // 微米
        TO_ANCHOR_FACTORS.put(UnitEnum.MM, new BigDecimal("1e-3"));      // 毫米
        TO_ANCHOR_FACTORS.put(UnitEnum.CM, new BigDecimal("1e-2"));      // 厘米
        TO_ANCHOR_FACTORS.put(UnitEnum.DM, new BigDecimal("1e-1"));      // 分米
        TO_ANCHOR_FACTORS.put(UnitEnum.M, new BigDecimal("1"));          // 米
        TO_ANCHOR_FACTORS.put(UnitEnum.KM, new BigDecimal("1e3"));       // 千米
        TO_ANCHOR_FACTORS.put(UnitEnum.MIL, new BigDecimal("1").divide(new BigDecimal("12000"), 10, RoundingMode.HALF_UP)); // 密耳
        TO_ANCHOR_FACTORS.put(UnitEnum.IN, new BigDecimal("1").divide(new BigDecimal("12"), 10, RoundingMode.HALF_UP)); // 英寸
        TO_ANCHOR_FACTORS.put(UnitEnum.YD, new BigDecimal("3"));         // 码
        TO_ANCHOR_FACTORS.put(UnitEnum.FT_US, new BigDecimal("1200").divide(new BigDecimal("3937"), 10, RoundingMode.HALF_UP)); // 美制英尺
        TO_ANCHOR_FACTORS.put(UnitEnum.FT, new BigDecimal("1").divide(new BigDecimal("3"), 10, RoundingMode.HALF_UP)); // 英尺
        TO_ANCHOR_FACTORS.put(UnitEnum.FATHOM, new BigDecimal("6"));     // 英寻
        TO_ANCHOR_FACTORS.put(UnitEnum.MI, new BigDecimal("5280"));      // 英里
        TO_ANCHOR_FACTORS.put(UnitEnum.NMI, new BigDecimal("1852"));     // 海里

        // 质量单位 - 基准单位：克
        TO_ANCHOR_FACTORS.put(UnitEnum.MCG, new BigDecimal("1").divide(new BigDecimal("1e6"), 10, RoundingMode.HALF_UP)); // 微克
        TO_ANCHOR_FACTORS.put(UnitEnum.MG, new BigDecimal("1").divide(new BigDecimal("1e3"), 10, RoundingMode.HALF_UP)); // 毫克
        TO_ANCHOR_FACTORS.put(UnitEnum.G, new BigDecimal("1"));          // 克
        TO_ANCHOR_FACTORS.put(UnitEnum.KG, new BigDecimal("1e3"));       // 千克
        TO_ANCHOR_FACTORS.put(UnitEnum.MT, new BigDecimal("1e6"));       // 公吨
        TO_ANCHOR_FACTORS.put(UnitEnum.OZ, new BigDecimal("28.3495"));   // 盎司
        TO_ANCHOR_FACTORS.put(UnitEnum.LB, new BigDecimal("453.592"));   // 磅
        TO_ANCHOR_FACTORS.put(UnitEnum.ST, new BigDecimal("6350.29"));   // 英石
        TO_ANCHOR_FACTORS.put(UnitEnum.T, new BigDecimal("907185"));     // 吨

        // 体积单位 - 基准单位：升
        TO_ANCHOR_FACTORS.put(UnitEnum.MM3, new BigDecimal("1").divide(new BigDecimal("1e6"), 10, RoundingMode.HALF_UP)); // 立方毫米
        TO_ANCHOR_FACTORS.put(UnitEnum.CM3, new BigDecimal("1").divide(new BigDecimal("1e3"), 10, RoundingMode.HALF_UP)); // 立方厘米
        TO_ANCHOR_FACTORS.put(UnitEnum.DM3, new BigDecimal("1"));        // 立方分米 (1L = 1dm3)
        TO_ANCHOR_FACTORS.put(UnitEnum.ML, new BigDecimal("1").divide(new BigDecimal("1e3"), 10, RoundingMode.HALF_UP)); // 毫升
        TO_ANCHOR_FACTORS.put(UnitEnum.CL, new BigDecimal("1").divide(new BigDecimal("100"), 10, RoundingMode.HALF_UP)); // 厘升
        TO_ANCHOR_FACTORS.put(UnitEnum.DL, new BigDecimal("1").divide(new BigDecimal("10"), 10, RoundingMode.HALF_UP)); // 分升
        TO_ANCHOR_FACTORS.put(UnitEnum.L, new BigDecimal("1"));          // 升
        TO_ANCHOR_FACTORS.put(UnitEnum.KL, new BigDecimal("1e3"));       // 千升
        TO_ANCHOR_FACTORS.put(UnitEnum.ML_MEGA, new BigDecimal("1e6"));  // 兆升
        TO_ANCHOR_FACTORS.put(UnitEnum.ML_GIGA, new BigDecimal("1e9"));  // 吉升
        TO_ANCHOR_FACTORS.put(UnitEnum.M3, new BigDecimal("1e3"));       // 立方米
        TO_ANCHOR_FACTORS.put(UnitEnum.KM3, new BigDecimal("1e12"));     // 立方千米
        TO_ANCHOR_FACTORS.put(UnitEnum.KRM, new BigDecimal("0.005"));    // 瑞典茶匙
        TO_ANCHOR_FACTORS.put(UnitEnum.TSK, new BigDecimal("0.015"));    // 茶匙
        TO_ANCHOR_FACTORS.put(UnitEnum.MSK, new BigDecimal("0.03"));     // 汤匙
        TO_ANCHOR_FACTORS.put(UnitEnum.KKP, new BigDecimal("0.15"));     // 咖啡杯
        TO_ANCHOR_FACTORS.put(UnitEnum.GLAS, new BigDecimal("0.2"));     // 玻璃杯
        TO_ANCHOR_FACTORS.put(UnitEnum.KANNA, new BigDecimal("2.617"));  // 罐
        TO_ANCHOR_FACTORS.put(UnitEnum.TSP, new BigDecimal("1").divide(new BigDecimal("6"), 10, RoundingMode.HALF_UP)); // 茶匙
        TO_ANCHOR_FACTORS.put(UnitEnum.TBS, new BigDecimal("1").divide(new BigDecimal("2"), 10, RoundingMode.HALF_UP)); // 汤匙
        TO_ANCHOR_FACTORS.put(UnitEnum.IN3, new BigDecimal("0.55411"));  // 立方英寸
        TO_ANCHOR_FACTORS.put(UnitEnum.FLOZ, new BigDecimal("1"));       // 液盎司
        TO_ANCHOR_FACTORS.put(UnitEnum.CUP, new BigDecimal("8"));        // 杯
        TO_ANCHOR_FACTORS.put(UnitEnum.PNT, new BigDecimal("16"));       // 品脱
        TO_ANCHOR_FACTORS.put(UnitEnum.QT, new BigDecimal("32"));        // 夸脱
        TO_ANCHOR_FACTORS.put(UnitEnum.GAL, new BigDecimal("128"));      // 加仑
        TO_ANCHOR_FACTORS.put(UnitEnum.FT3, new BigDecimal("957.506"));  // 立方英尺
        TO_ANCHOR_FACTORS.put(UnitEnum.YD3, new BigDecimal("25852.7"));  // 立方码

        // 面积单位 - 基准单位：平方米
        TO_ANCHOR_FACTORS.put(UnitEnum.MM2, new BigDecimal("1e-6"));     // 平方毫米
        TO_ANCHOR_FACTORS.put(UnitEnum.CM2, new BigDecimal("1e-4"));     // 平方厘米
        TO_ANCHOR_FACTORS.put(UnitEnum.M2, new BigDecimal("1"));         // 平方米
        TO_ANCHOR_FACTORS.put(UnitEnum.HA, new BigDecimal("1e4"));       // 公顷
        TO_ANCHOR_FACTORS.put(UnitEnum.KM2, new BigDecimal("1e6"));      // 平方千米
        TO_ANCHOR_FACTORS.put(UnitEnum.IN2, new BigDecimal("1").divide(new BigDecimal("144"), 10, RoundingMode.HALF_UP)); // 平方英寸
        TO_ANCHOR_FACTORS.put(UnitEnum.FT2, new BigDecimal("1"));        // 平方英尺
        TO_ANCHOR_FACTORS.put(UnitEnum.YD2, new BigDecimal("9"));        // 平方码
        TO_ANCHOR_FACTORS.put(UnitEnum.MI2, new BigDecimal("27878400")); // 平方英里
        TO_ANCHOR_FACTORS.put(UnitEnum.ACRE, new BigDecimal("43560"));     // 英亩
        TO_ANCHOR_FACTORS.put(UnitEnum.ROD, new BigDecimal("272.25"));    // 杆

        // 时间单位 - 基准单位：秒
        TO_ANCHOR_FACTORS.put(UnitEnum.NS, new BigDecimal("1e-9"));      // 纳秒
        TO_ANCHOR_FACTORS.put(UnitEnum.US, new BigDecimal("1e-6"));      // 微秒
        TO_ANCHOR_FACTORS.put(UnitEnum.MU, new BigDecimal("1e-6"));      // 微秒 (mu)
        TO_ANCHOR_FACTORS.put(UnitEnum.MS, new BigDecimal("1e-3"));      // 毫秒
        TO_ANCHOR_FACTORS.put(UnitEnum.S, new BigDecimal("1"));          // 秒
        TO_ANCHOR_FACTORS.put(UnitEnum.MIN, new BigDecimal("60"));       // 分钟
        TO_ANCHOR_FACTORS.put(UnitEnum.H, new BigDecimal("3600"));       // 小时
        TO_ANCHOR_FACTORS.put(UnitEnum.D, new BigDecimal("86400"));      // 天
        TO_ANCHOR_FACTORS.put(UnitEnum.WEEK, new BigDecimal("604800"));  // 周
        TO_ANCHOR_FACTORS.put(UnitEnum.MONTH, new BigDecimal("2629800")); // 月（平均）
        TO_ANCHOR_FACTORS.put(UnitEnum.YEAR, new BigDecimal("31557600")); // 年（平均）

        // 温度单位 - 特殊处理
        // 温度转换有特殊的公式，不在TO_ANCHOR_FACTORS中处理
        // 添加兰金温标
        TO_ANCHOR_FACTORS.put(UnitEnum.R, new BigDecimal("1"));           // 兰金温标

        // 速度单位 - 基准单位：千米/小时(km/h) 对于metric系统，英里/小时(mph) 对于imperial系统
        TO_ANCHOR_FACTORS.put(UnitEnum.KM_H, new BigDecimal("1"));                  // 千米/小时 (metric系统基准单位)
        TO_ANCHOR_FACTORS.put(UnitEnum.M_S, new BigDecimal("3.6"));                 // 米/秒 (1 m/s = 3.6 km/h)
        TO_ANCHOR_FACTORS.put(UnitEnum.MM_S, new BigDecimal("0.0036"));              // 毫米/秒 (1 mm/s = 0.0036 km/h)
        TO_ANCHOR_FACTORS.put(UnitEnum.CM_S, new BigDecimal("0.036"));               // 厘米/秒 (1 cm/s = 0.036 km/h)
        TO_ANCHOR_FACTORS.put(UnitEnum.MM_H, new BigDecimal("1e-6"));                // 毫米/小时 (1 mm/h = 1e-6 km/h)
        TO_ANCHOR_FACTORS.put(UnitEnum.KM_S, new BigDecimal("3600"));               // 千米/秒 (1 km/s = 3600 km/h)
        TO_ANCHOR_FACTORS.put(UnitEnum.MPH, new BigDecimal("1"));                   // 英里/小时 (imperial系统基准单位)
        TO_ANCHOR_FACTORS.put(UnitEnum.KT, new BigDecimal("1.150779"));             // 节 (1 knot = 1.150779 mph)
        TO_ANCHOR_FACTORS.put(UnitEnum.FPS, new BigDecimal("0.681818"));            // 英尺/秒 (1 ft/s = 0.681818 mph)
        TO_ANCHOR_FACTORS.put(UnitEnum.FPM, new BigDecimal("0.0113636"));           // 英尺/分钟 (1 ft/min = 0.0113636 mph)
        TO_ANCHOR_FACTORS.put(UnitEnum.IN_H, new BigDecimal("1.578e-5"));           // 英寸/小时 (1 in/h = 1.578e-5 mph)

        // 加速度单位 - 基准单位：米/秒²
        TO_ANCHOR_FACTORS.put(UnitEnum.G_FORCE, new BigDecimal("9.80665")); // 重力加速度
        TO_ANCHOR_FACTORS.put(UnitEnum.M_S2, new BigDecimal("1"));       // 米/秒²
        TO_ANCHOR_FACTORS.put(UnitEnum.G0, new BigDecimal("9.80665"));   // 标准重力

        // 角度单位 - 基准单位：度
        TO_ANCHOR_FACTORS.put(UnitEnum.RAD, new BigDecimal("180").divide(new BigDecimal("3.141592653589793"), 10, RoundingMode.HALF_UP)); // 弧度
        TO_ANCHOR_FACTORS.put(UnitEnum.DEG, new BigDecimal("1"));        // 度
        TO_ANCHOR_FACTORS.put(UnitEnum.ARCMIN, new BigDecimal("1").divide(new BigDecimal("60"), 10, RoundingMode.HALF_UP)); // 弧分
        TO_ANCHOR_FACTORS.put(UnitEnum.ARCSEC, new BigDecimal("1").divide(new BigDecimal("3600"), 10, RoundingMode.HALF_UP)); // 弧秒

        // 功率单位 - 基准单位：瓦特
        TO_ANCHOR_FACTORS.put(UnitEnum.W, new BigDecimal("1"));          // 瓦特
        TO_ANCHOR_FACTORS.put(UnitEnum.MW, new BigDecimal("1e-3"));      // 毫瓦
        TO_ANCHOR_FACTORS.put(UnitEnum.KW, new BigDecimal("1e3"));       // 千瓦
        TO_ANCHOR_FACTORS.put(UnitEnum.MW_POWER, new BigDecimal("1e6")); // 兆瓦
        TO_ANCHOR_FACTORS.put(UnitEnum.GW, new BigDecimal("1e9"));       // 吉瓦
        TO_ANCHOR_FACTORS.put(UnitEnum.PS, new BigDecimal("735.49875")); // 公制马力
        TO_ANCHOR_FACTORS.put(UnitEnum.HP, new BigDecimal("745.7"));     // 马力

        // 压力单位 - 基准单位：帕斯卡(Pa)
        TO_ANCHOR_FACTORS.put(UnitEnum.PA, new BigDecimal("1"));                     // 帕斯卡
        TO_ANCHOR_FACTORS.put(UnitEnum.KPA, new BigDecimal("1000"));                 // 千帕
        TO_ANCHOR_FACTORS.put(UnitEnum.MPA, new BigDecimal("1000000"));              // 兆帕
        TO_ANCHOR_FACTORS.put(UnitEnum.HPa, new BigDecimal("100"));                  // 百帕
        TO_ANCHOR_FACTORS.put(UnitEnum.GPA, new BigDecimal("1000000000"));           // 吉帕
        TO_ANCHOR_FACTORS.put(UnitEnum.MBAR, new BigDecimal("0.1"));                 // 毫巴
        TO_ANCHOR_FACTORS.put(UnitEnum.BAR, new BigDecimal("100000"));               // 巴
        TO_ANCHOR_FACTORS.put(UnitEnum.TORR, new BigDecimal("133.32236842105263"));  // 托
        TO_ANCHOR_FACTORS.put(UnitEnum.MH2O, new BigDecimal("9806.65"));             // 米水柱
        TO_ANCHOR_FACTORS.put(UnitEnum.MMHG, new BigDecimal("133.322387415"));       // 毫米汞柱
        TO_ANCHOR_FACTORS.put(UnitEnum.PSI, new BigDecimal("6894.757293168361"));    // 磅每平方英寸

        // 数量单位(each类型) - 基准单位：无单位（计数）
        TO_ANCHOR_FACTORS.put(UnitEnum.EA, new BigDecimal("1"));           // 每个
        TO_ANCHOR_FACTORS.put(UnitEnum.SINGLE, new BigDecimal("1"));       // 个
        TO_ANCHOR_FACTORS.put(UnitEnum.DOZEN, new BigDecimal("12"));       // 打
        
        // 数量单位(pieces类型) - 基准单位：件
        TO_ANCHOR_FACTORS.put(UnitEnum.PCS, new BigDecimal("1"));          // 件
        TO_ANCHOR_FACTORS.put(UnitEnum.PAIR, new BigDecimal("2"));         // 双
        TO_ANCHOR_FACTORS.put(UnitEnum.SET, new BigDecimal("1"));          // 套

        // 电荷单位 - 基准单位：库仑
        TO_ANCHOR_FACTORS.put(UnitEnum.COULOMB, new BigDecimal("1"));       // 库仑
        TO_ANCHOR_FACTORS.put(UnitEnum.MC, new BigDecimal("1e-3"));      // 毫库仑
        TO_ANCHOR_FACTORS.put(UnitEnum.UC, new BigDecimal("1e-6"));      // 微库仑
        TO_ANCHOR_FACTORS.put(UnitEnum.NC, new BigDecimal("1e-9"));      // 纳库仑
        TO_ANCHOR_FACTORS.put(UnitEnum.PC, new BigDecimal("1e-12"));     // 皮库仑

        // 电流单位 - 基准单位：安培
        TO_ANCHOR_FACTORS.put(UnitEnum.A, new BigDecimal("1"));          // 安培
        TO_ANCHOR_FACTORS.put(UnitEnum.MA, new BigDecimal("1e-3"));      // 毫安
        TO_ANCHOR_FACTORS.put(UnitEnum.UA, new BigDecimal("1e-6"));      // 微安

        // 电压单位 - 基准单位：伏特
        TO_ANCHOR_FACTORS.put(UnitEnum.V, new BigDecimal("1"));          // 伏特
        TO_ANCHOR_FACTORS.put(UnitEnum.MV_VOLTAGE, new BigDecimal("1e-3"));      // 毫伏
        TO_ANCHOR_FACTORS.put(UnitEnum.UV, new BigDecimal("1e-6"));      // 微伏

        // 数字存储单位 - 基准单位：字节
        TO_ANCHOR_FACTORS.put(UnitEnum.BIT, new BigDecimal("1").divide(new BigDecimal("8"), 10, RoundingMode.HALF_UP)); // 比特
        TO_ANCHOR_FACTORS.put(UnitEnum.KBIT, new BigDecimal("1000").divide(new BigDecimal("8"), 10, RoundingMode.HALF_UP)); // 千比特
        TO_ANCHOR_FACTORS.put(UnitEnum.MBIT, new BigDecimal("1e6").divide(new BigDecimal("8"), 10, RoundingMode.HALF_UP)); // 兆比特
        TO_ANCHOR_FACTORS.put(UnitEnum.GBIT, new BigDecimal("1e9").divide(new BigDecimal("8"), 10, RoundingMode.HALF_UP)); // 吉比特
        TO_ANCHOR_FACTORS.put(UnitEnum.TBIT, new BigDecimal("1e12").divide(new BigDecimal("8"), 10, RoundingMode.HALF_UP)); // 太比特
        TO_ANCHOR_FACTORS.put(UnitEnum.BYTE, new BigDecimal("1"));       // 字节
        TO_ANCHOR_FACTORS.put(UnitEnum.KBYTE, new BigDecimal("1000"));      // 千字节
        TO_ANCHOR_FACTORS.put(UnitEnum.MBYTE, new BigDecimal("1e6"));       // 兆字节
        TO_ANCHOR_FACTORS.put(UnitEnum.GBYTE, new BigDecimal("1e9"));       // 吉字节
        TO_ANCHOR_FACTORS.put(UnitEnum.TBYTE, new BigDecimal("1e12"));      // 太字节
        TO_ANCHOR_FACTORS.put(UnitEnum.KIBYTE, new BigDecimal("1024"));      // 千位字节
        TO_ANCHOR_FACTORS.put(UnitEnum.MIBYTE, new BigDecimal("1024").multiply(new BigDecimal("1024"))); // 兆位字节
        TO_ANCHOR_FACTORS.put(UnitEnum.GIBYTE, new BigDecimal("1024").multiply(new BigDecimal("1024")).multiply(new BigDecimal("1024"))); // 吉位字节
        TO_ANCHOR_FACTORS.put(UnitEnum.TIBYTE, new BigDecimal("1024").multiply(new BigDecimal("1024")).multiply(new BigDecimal("1024")).multiply(new BigDecimal("1024"))); // 太位字节

        // 能量单位 - 基准单位：焦耳
        TO_ANCHOR_FACTORS.put(UnitEnum.WS, new BigDecimal("1"));          // 瓦特秒
        TO_ANCHOR_FACTORS.put(UnitEnum.WM, new BigDecimal("60"));         // 瓦特分钟
        TO_ANCHOR_FACTORS.put(UnitEnum.WH, new BigDecimal("3600"));       // 瓦特小时
        TO_ANCHOR_FACTORS.put(UnitEnum.MWH, new BigDecimal("3600"));      // 毫瓦特小时
        TO_ANCHOR_FACTORS.put(UnitEnum.KWH, new BigDecimal("3600000"));   // 千瓦时
        TO_ANCHOR_FACTORS.put(UnitEnum.MWH_MEGA, new BigDecimal("3.6e9")); // 兆瓦时
        TO_ANCHOR_FACTORS.put(UnitEnum.GWH, new BigDecimal("3.6e12"));    // 吉瓦时
        TO_ANCHOR_FACTORS.put(UnitEnum.J, new BigDecimal("1"));           // 焦耳
        TO_ANCHOR_FACTORS.put(UnitEnum.KJ, new BigDecimal("1e3"));        // 千焦
        TO_ANCHOR_FACTORS.put(UnitEnum.MJ, new BigDecimal("1e6"));        // 兆焦
        TO_ANCHOR_FACTORS.put(UnitEnum.GJ, new BigDecimal("1e9"));        // 吉焦
        TO_ANCHOR_FACTORS.put(UnitEnum.KCAL, new BigDecimal("4184"));     // 千卡
        TO_ANCHOR_FACTORS.put(UnitEnum.CAL, new BigDecimal("4.184"));     // 卡路里

        // 力单位 - 基准单位：牛顿
        TO_ANCHOR_FACTORS.put(UnitEnum.N, new BigDecimal("1"));           // 牛顿
        TO_ANCHOR_FACTORS.put(UnitEnum.KN, new BigDecimal("1e3"));        // 千牛
        TO_ANCHOR_FACTORS.put(UnitEnum.LBF, new BigDecimal("4.44822"));   // 磅力
        TO_ANCHOR_FACTORS.put(UnitEnum.KGF, new BigDecimal("9.80665"));   // 千克力

        // 扭矩单位 - 基准单位：牛·米
        TO_ANCHOR_FACTORS.put(UnitEnum.NM, new BigDecimal("1"));          // 牛·米
        TO_ANCHOR_FACTORS.put(UnitEnum.CNM, new BigDecimal("1e-2"));      // 厘牛·米
        TO_ANCHOR_FACTORS.put(UnitEnum.DNM, new BigDecimal("1e-1"));      // 分牛·米
        TO_ANCHOR_FACTORS.put(UnitEnum.KGM, new BigDecimal("9.80665"));   // 千克·米
        TO_ANCHOR_FACTORS.put(UnitEnum.KG_CM, new BigDecimal("9.80665e-2")); // 千克·厘米
        TO_ANCHOR_FACTORS.put(UnitEnum.LBF_FT, new BigDecimal("1.35582")); // 磅·英尺
        TO_ANCHOR_FACTORS.put(UnitEnum.LBF_IN, new BigDecimal("0.112985")); // 磅·英寸
        TO_ANCHOR_FACTORS.put(UnitEnum.OZF_IN, new BigDecimal("0.006377")); // 盎司·英寸

        // 频率单位 - 基准单位：赫兹
        TO_ANCHOR_FACTORS.put(UnitEnum.HZ, new BigDecimal("1"));          // 赫兹
        TO_ANCHOR_FACTORS.put(UnitEnum.KHZ, new BigDecimal("1e3"));       // 千赫兹
        TO_ANCHOR_FACTORS.put(UnitEnum.MHZ, new BigDecimal("1e6"));       // 兆赫兹
        TO_ANCHOR_FACTORS.put(UnitEnum.GHZ, new BigDecimal("1e9"));       // 吉赫兹
        TO_ANCHOR_FACTORS.put(UnitEnum.THZ, new BigDecimal("1e12"));      // 太赫兹
        TO_ANCHOR_FACTORS.put(UnitEnum.RPM, new BigDecimal("1").divide(new BigDecimal("60"), 10, RoundingMode.HALF_UP)); // 转/分钟
        TO_ANCHOR_FACTORS.put(UnitEnum.DEG_S, new BigDecimal("1").divide(new BigDecimal("360"), 10, RoundingMode.HALF_UP)); // 度/秒
        TO_ANCHOR_FACTORS.put(UnitEnum.RAD_S, new BigDecimal("1").divide(new BigDecimal("6.283185307179586"), 10, RoundingMode.HALF_UP)); // 弧度/秒

        // 照度单位 - 基准单位：勒克斯
        TO_ANCHOR_FACTORS.put(UnitEnum.LX, new BigDecimal("1"));          // 勒克斯
        TO_ANCHOR_FACTORS.put(UnitEnum.FOOTCANDLE, new BigDecimal("10.764")); // 英尺烛光

        // 视在功率单位 - 基准单位：伏安
        TO_ANCHOR_FACTORS.put(UnitEnum.VA, new BigDecimal("1"));         // 伏安
        TO_ANCHOR_FACTORS.put(UnitEnum.MVA, new BigDecimal("1e-3"));     // 毫伏安
        TO_ANCHOR_FACTORS.put(UnitEnum.KVA, new BigDecimal("1e3"));      // 千伏安
        TO_ANCHOR_FACTORS.put(UnitEnum.MVA_POWER, new BigDecimal("1e6")); // 兆伏安
        TO_ANCHOR_FACTORS.put(UnitEnum.GVA, new BigDecimal("1e9"));      // 吉伏安

        // 部分浓度单位 - 基准单位：无量纲
        TO_ANCHOR_FACTORS.put(UnitEnum.PPM, new BigDecimal("1e-6"));     // 百万分率
        TO_ANCHOR_FACTORS.put(UnitEnum.PPB, new BigDecimal("1e-9"));     // 十亿分率
        TO_ANCHOR_FACTORS.put(UnitEnum.PPT, new BigDecimal("1e-12"));    // 万亿分率
        TO_ANCHOR_FACTORS.put(UnitEnum.PPQ, new BigDecimal("1e-15"));    // 千万亿分率

        // 数量单位 - 基准单位：件
        TO_ANCHOR_FACTORS.put(UnitEnum.PCS_PIECES, new BigDecimal("1"));        // 件
        TO_ANCHOR_FACTORS.put(UnitEnum.BK_DOZ, new BigDecimal("12"));           // 烘焙 dozen
        TO_ANCHOR_FACTORS.put(UnitEnum.CP, new BigDecimal("2"));                // 对
        TO_ANCHOR_FACTORS.put(UnitEnum.DOZ_DOZ, new BigDecimal("144"));         // dozen dozen
        TO_ANCHOR_FACTORS.put(UnitEnum.DOZ, new BigDecimal("12"));              // dozen
        TO_ANCHOR_FACTORS.put(UnitEnum.GR_GR, new BigDecimal("20736"));         // gross gross
        TO_ANCHOR_FACTORS.put(UnitEnum.GROS, new BigDecimal("144"));            // gross
        TO_ANCHOR_FACTORS.put(UnitEnum.HALF_DOZEN, new BigDecimal("6"));        // 半打
        TO_ANCHOR_FACTORS.put(UnitEnum.LONG_HUNDRED, new BigDecimal("120"));     // 长百
        TO_ANCHOR_FACTORS.put(UnitEnum.REAM, new BigDecimal("500"));             // 令
        TO_ANCHOR_FACTORS.put(UnitEnum.SCORES, new BigDecimal("20"));            // 二十
        TO_ANCHOR_FACTORS.put(UnitEnum.SM_GR, new BigDecimal("120"));            // small gross
        TO_ANCHOR_FACTORS.put(UnitEnum.TRIO, new BigDecimal("3"));               // 三人组

        // 无功功率单位 - 基准单位：乏
        TO_ANCHOR_FACTORS.put(UnitEnum.VAR, new BigDecimal("1"));        // 乏
        TO_ANCHOR_FACTORS.put(UnitEnum.MVAR, new BigDecimal("1e-3"));    // 毫乏
        TO_ANCHOR_FACTORS.put(UnitEnum.KVAR, new BigDecimal("1e3"));     // 千乏
        TO_ANCHOR_FACTORS.put(UnitEnum.MVAR_POWER, new BigDecimal("1e6")); // 兆乏
        TO_ANCHOR_FACTORS.put(UnitEnum.GVAR, new BigDecimal("1e9"));     // 吉乏

        // 无功能量单位 - 基准单位：乏时
        TO_ANCHOR_FACTORS.put(UnitEnum.VARH, new BigDecimal("1"));       // 乏时
        TO_ANCHOR_FACTORS.put(UnitEnum.MVARH, new BigDecimal("1e-3"));   // 毫乏时
        TO_ANCHOR_FACTORS.put(UnitEnum.KVARH, new BigDecimal("1e3"));    // 千乏时
        TO_ANCHOR_FACTORS.put(UnitEnum.MVARH_POWER, new BigDecimal("1e6")); // 兆乏时
        TO_ANCHOR_FACTORS.put(UnitEnum.GVARH, new BigDecimal("1e9"));    // 吉乏时

        // 质量流量率单位 - 基准单位：千克/秒
        TO_ANCHOR_FACTORS.put(UnitEnum.KG_PER_S, new BigDecimal("1"));                    // 千克/秒
        TO_ANCHOR_FACTORS.put(UnitEnum.KG_PER_MIN, new BigDecimal("1").divide(new BigDecimal("60"), 10, RoundingMode.HALF_UP)); // 千克/分钟 (1/60)
        TO_ANCHOR_FACTORS.put(UnitEnum.KG_PER_H, new BigDecimal("1").divide(new BigDecimal("3600"), 10, RoundingMode.HALF_UP)); // 千克/小时 (1/3600)
        TO_ANCHOR_FACTORS.put(UnitEnum.MT_PER_H, new BigDecimal("1000").divide(new BigDecimal("3600"), 10, RoundingMode.HALF_UP)); // 公吨/小时 (1000/3600)
        TO_ANCHOR_FACTORS.put(UnitEnum.LB_PER_S, new BigDecimal("0.453592"));            // 磅/秒 (1磅=0.453592千克)
        TO_ANCHOR_FACTORS.put(UnitEnum.LB_PER_H, new BigDecimal("0.453592").divide(new BigDecimal("3600"), 10, RoundingMode.HALF_UP)); // 磅/小时 (0.453592/3600)

        // 步速单位 - 基准单位：秒/米 (s/m) 作为统一锚点
        TO_ANCHOR_FACTORS.put(UnitEnum.S_PER_M, new BigDecimal("1"));                   // 秒/米 (统一基准单位)
        TO_ANCHOR_FACTORS.put(UnitEnum.MIN_PER_KM, new BigDecimal("60").divide(new BigDecimal("1000"), 10, RoundingMode.HALF_UP)); // 分钟/公里 (60 sec/1000 m = 0.06 s/m)
        TO_ANCHOR_FACTORS.put(UnitEnum.S_PER_FT, new BigDecimal("1").divide(new BigDecimal("0.3048"), 10, RoundingMode.HALF_UP)); // 秒/英尺 (1 sec/0.3048m = 3.28084 s/m)
        TO_ANCHOR_FACTORS.put(UnitEnum.MIN_PER_MI, new BigDecimal("60").divide(new BigDecimal("1609.34"), 10, RoundingMode.HALF_UP)); // 分钟/英里 (60 sec/1609.34 m = 0.03728 s/m)

        // 体积流量率单位 - 基准单位：立方分米/秒 (dm3/s)，因为1 dm3 = 1 L
        TO_ANCHOR_FACTORS.put(UnitEnum.DM3_PER_S, new BigDecimal("1"));                  // 立方分米/秒 (基准单位)
        TO_ANCHOR_FACTORS.put(UnitEnum.MM3_PER_S, new BigDecimal("1").divide(new BigDecimal("1e6"), 10, RoundingMode.HALF_UP)); // 立方毫米/秒 (1/1e6)
        TO_ANCHOR_FACTORS.put(UnitEnum.CM3_PER_S, new BigDecimal("1").divide(new BigDecimal("1e3"), 10, RoundingMode.HALF_UP)); // 立方厘米/秒 (1/1e3)
        TO_ANCHOR_FACTORS.put(UnitEnum.DM3_PER_MIN, new BigDecimal("1").divide(new BigDecimal("60"), 10, RoundingMode.HALF_UP)); // 立方分米/分钟 (1/60)
        TO_ANCHOR_FACTORS.put(UnitEnum.DM3_PER_H, new BigDecimal("1").divide(new BigDecimal("3600"), 10, RoundingMode.HALF_UP)); // 立方分米/小时 (1/3600)
        TO_ANCHOR_FACTORS.put(UnitEnum.DM3_PER_D, new BigDecimal("1").divide(new BigDecimal("86400"), 10, RoundingMode.HALF_UP)); // 立方分米/天 (1/86400)
        TO_ANCHOR_FACTORS.put(UnitEnum.DM3_PER_A, new BigDecimal("1").divide(new BigDecimal("31557600"), 10, RoundingMode.HALF_UP)); // 立方分米/年 (1/31557600)
        TO_ANCHOR_FACTORS.put(UnitEnum.ML_PER_S, new BigDecimal("1").divide(new BigDecimal("1e3"), 10, RoundingMode.HALF_UP)); // 毫升/秒 (1/1e3)
        TO_ANCHOR_FACTORS.put(UnitEnum.CL_PER_S, new BigDecimal("1").divide(new BigDecimal("100"), 10, RoundingMode.HALF_UP)); // 厘升/秒 (1/100)
        TO_ANCHOR_FACTORS.put(UnitEnum.DL_PER_S, new BigDecimal("1").divide(new BigDecimal("10"), 10, RoundingMode.HALF_UP)); // 分升/秒 (1/10)
        TO_ANCHOR_FACTORS.put(UnitEnum.L_PER_S, new BigDecimal("1"));                    // 升/秒 (1 dm3 = 1 L)
        TO_ANCHOR_FACTORS.put(UnitEnum.L_PER_MIN, new BigDecimal("1").divide(new BigDecimal("60"), 10, RoundingMode.HALF_UP)); // 升/分钟 (1/60)
        TO_ANCHOR_FACTORS.put(UnitEnum.L_PER_H, new BigDecimal("1").divide(new BigDecimal("3600"), 10, RoundingMode.HALF_UP)); // 升/小时 (1/3600)
        TO_ANCHOR_FACTORS.put(UnitEnum.L_PER_D, new BigDecimal("1").divide(new BigDecimal("86400"), 10, RoundingMode.HALF_UP)); // 升/天 (1/86400)
        TO_ANCHOR_FACTORS.put(UnitEnum.L_PER_A, new BigDecimal("1").divide(new BigDecimal("31557600"), 10, RoundingMode.HALF_UP)); // 升/年 (1/31557600)
        TO_ANCHOR_FACTORS.put(UnitEnum.KL_PER_S, new BigDecimal("1e3"));                 // 千升/秒 (1e3)
        TO_ANCHOR_FACTORS.put(UnitEnum.KL_PER_MIN, new BigDecimal("1e3").divide(new BigDecimal("60"), 10, RoundingMode.HALF_UP)); // 千升/分钟 (1e3/60)
        TO_ANCHOR_FACTORS.put(UnitEnum.KL_PER_H, new BigDecimal("1e3").divide(new BigDecimal("3600"), 10, RoundingMode.HALF_UP)); // 千升/小时 (1e3/3600)
        TO_ANCHOR_FACTORS.put(UnitEnum.M3_PER_S, new BigDecimal("1e3"));                 // 立方米/秒 (1000 dm3)
        TO_ANCHOR_FACTORS.put(UnitEnum.M3_PER_MIN, new BigDecimal("1e3").divide(new BigDecimal("60"), 10, RoundingMode.HALF_UP)); // 立方米/分钟 (1000/60)
        TO_ANCHOR_FACTORS.put(UnitEnum.M3_PER_H, new BigDecimal("1e3").divide(new BigDecimal("3600"), 10, RoundingMode.HALF_UP)); // 立方米/小时 (1000/3600)
        TO_ANCHOR_FACTORS.put(UnitEnum.M3_PER_D, new BigDecimal("1e3").divide(new BigDecimal("86400"), 10, RoundingMode.HALF_UP)); // 立方米/天 (1000/86400)
        TO_ANCHOR_FACTORS.put(UnitEnum.M3_PER_A, new BigDecimal("1e3").divide(new BigDecimal("31557600"), 10, RoundingMode.HALF_UP)); // 立方米/年 (1000/31557600)
        TO_ANCHOR_FACTORS.put(UnitEnum.KM3_PER_S, new BigDecimal("1e12"));              // 立方千米/秒 (1e12 dm3)
        TO_ANCHOR_FACTORS.put(UnitEnum.TSP_PER_S, new BigDecimal("1").divide(new BigDecimal("6"), 10, RoundingMode.HALF_UP)); // 茶匙/秒 (从美制茶匙到fluid oz，然后转为dm3)
        TO_ANCHOR_FACTORS.put(UnitEnum.TBS_PER_S, new BigDecimal("1").divide(new BigDecimal("2"), 10, RoundingMode.HALF_UP)); // 汤匙/秒 (从美制汤匙到fluid oz，然后转为dm3)
        TO_ANCHOR_FACTORS.put(UnitEnum.IN3_PER_S, new BigDecimal("0.55411"));           // 立方英寸/秒 (0.55411 dm3)
        TO_ANCHOR_FACTORS.put(UnitEnum.IN3_PER_MIN, new BigDecimal("0.55411").divide(new BigDecimal("60"), 10, RoundingMode.HALF_UP)); // 立方英寸/分钟 (0.55411/60)
        TO_ANCHOR_FACTORS.put(UnitEnum.IN3_PER_H, new BigDecimal("0.55411").divide(new BigDecimal("3600"), 10, RoundingMode.HALF_UP)); // 立方英寸/小时 (0.55411/3600)
        TO_ANCHOR_FACTORS.put(UnitEnum.FLOZ_PER_S, new BigDecimal("1"));                 // 液盎司/秒 (基准单位 for imperial system)
        TO_ANCHOR_FACTORS.put(UnitEnum.FLOZ_PER_MIN, new BigDecimal("1").divide(new BigDecimal("60"), 10, RoundingMode.HALF_UP)); // 液盎司/分钟 (1/60)
        TO_ANCHOR_FACTORS.put(UnitEnum.FLOZ_PER_H, new BigDecimal("1").divide(new BigDecimal("3600"), 10, RoundingMode.HALF_UP)); // 液盎司/小时 (1/3600)
        TO_ANCHOR_FACTORS.put(UnitEnum.CUP_PER_S, new BigDecimal("8"));                 // 杯/秒 (8 fl-oz)
        TO_ANCHOR_FACTORS.put(UnitEnum.PNT_PER_S, new BigDecimal("16"));                // 品脱/秒 (16 fl-oz)
        TO_ANCHOR_FACTORS.put(UnitEnum.PNT_PER_MIN, new BigDecimal("16").divide(new BigDecimal("60"), 10, RoundingMode.HALF_UP)); // 品脱/分钟 (16/60)
        TO_ANCHOR_FACTORS.put(UnitEnum.PNT_PER_H, new BigDecimal("16").divide(new BigDecimal("3600"), 10, RoundingMode.HALF_UP)); // 品脱/小时 (16/3600)
        TO_ANCHOR_FACTORS.put(UnitEnum.QT_PER_S, new BigDecimal("32"));                 // 夸脱/秒 (32 fl-oz)
        TO_ANCHOR_FACTORS.put(UnitEnum.GAL_PER_S, new BigDecimal("128"));               // 加仑/秒 (128 fl-oz)
        TO_ANCHOR_FACTORS.put(UnitEnum.GAL_PER_MIN, new BigDecimal("128").divide(new BigDecimal("60"), 10, RoundingMode.HALF_UP)); // 加仑/分钟 (128/60)
        TO_ANCHOR_FACTORS.put(UnitEnum.GAL_PER_H, new BigDecimal("128").divide(new BigDecimal("3600"), 10, RoundingMode.HALF_UP)); // 加仑/小时 (128/3600)
        TO_ANCHOR_FACTORS.put(UnitEnum.FT3_PER_S, new BigDecimal("957.506"));           // 立方英尺/秒 (957.506 dm3)
    }
    
    /**
     * 初始化系统间转换比率
     */
    private static void initializeSystemRatios() {
        // 步速单位系统间转换
        Map<String, BigDecimal> paceRatios = new HashMap<>();
        // Since both metric and imperial pace units use the same anchor (s/m), 
        // the system ratio between anchor units is 1
        paceRatios.put("metric_to_imperial", new BigDecimal("1")); // 从metric到imperial的比率 (same anchor unit s/m)
        paceRatios.put("imperial_to_metric", new BigDecimal("1")); // 从imperial到metric的比率 (same anchor unit s/m)
        SYSTEM_RATIOS.put("pace", paceRatios);
        
        // 力单位系统间转换 (SI和imperial) - 使用相同的锚点单位（牛顿）
        Map<String, BigDecimal> forceRatios = new HashMap<>();
        forceRatios.put("SI_to_imperial", new BigDecimal("1")); // 从SI到imperial的比率 (锚点单位相同)
        forceRatios.put("imperial_to_SI", new BigDecimal("1")); // 从imperial到SI的比率 (锚点单位相同)
        SYSTEM_RATIOS.put("force", forceRatios);
        
        // 扭矩单位系统间转换 (metric和imperial) - 使用相同的锚点单位（牛顿米）
        Map<String, BigDecimal> torqueRatios = new HashMap<>();
        torqueRatios.put("metric_to_imperial", new BigDecimal("1")); // 从metric到imperial的比率 (锚点单位相同)
        torqueRatios.put("imperial_to_metric", new BigDecimal("1")); // 从imperial到metric的比率 (锚点单位相同)
        SYSTEM_RATIOS.put("torque", torqueRatios);
        
        // 照度单位系统间转换 (metric和imperial) - 使用相同的锚点单位（勒克斯）
        Map<String, BigDecimal> illuminanceRatios = new HashMap<>();
        illuminanceRatios.put("metric_to_imperial", new BigDecimal("1")); // 从metric到imperial的比率 (锚点单位相同)
        illuminanceRatios.put("imperial_to_metric", new BigDecimal("1")); // 从imperial到metric的比率 (锚点单位相同)
        SYSTEM_RATIOS.put("illuminance", illuminanceRatios);
        
        // 速度单位系统间转换
        Map<String, BigDecimal> speedRatios = new HashMap<>();
        speedRatios.put("metric_to_imperial", new BigDecimal("1").divide(new BigDecimal("1.609344"), 10, RoundingMode.HALF_UP));  // 从metric到imperial的比率 (1km/h = 1/1.609344 mph)
        speedRatios.put("imperial_to_metric", new BigDecimal("1.609344"));   // 从imperial到metric的比率 (1mph = 1.609344 km/h)
        SYSTEM_RATIOS.put("speed", speedRatios);
        
        // 压力单位系统间转换 (metric和imperial) - 使用相同的锚点单位（帕斯卡）
        Map<String, BigDecimal> pressureRatios = new HashMap<>();
        pressureRatios.put("metric_to_imperial", new BigDecimal("1")); // 从metric到imperial的比率 (锚点单位相同)
        pressureRatios.put("imperial_to_metric", new BigDecimal("1")); // 从imperial到metric的比率 (锚点单位相同)
        SYSTEM_RATIOS.put("pressure", pressureRatios);
        
        // 能量单位系统间转换 (SI和nutrition) - 使用相同的锚点单位（焦耳）
        Map<String, BigDecimal> energyRatios = new HashMap<>();
        energyRatios.put("SI_to_nutrition", new BigDecimal("1")); // 从SI到nutrition的比率 (锚点单位相同)
        energyRatios.put("nutrition_to_SI", new BigDecimal("1")); // 从nutrition到SI的比率 (锚点单位相同)
        SYSTEM_RATIOS.put("energy", energyRatios);
    }
    
    /**
     * 执行跨系统转换
     *
     * 实现不同单位系统之间的转换（如公制到英制）
     * 转换过程分为三个步骤：
     * 1. 将源值转换为源系统的锚点单位
     * 2. 通过系统间比率转换到目标系统的锚点单位
     * 3. 从目标系统的锚点单位转换到目标单位
     *
     * @param value 待转换的数值
     * @param fromUnit 源单位
     * @param toUnit 目标单位
     * @return 转换后的值
     * @throws IllegalArgumentException 当不支持的跨系统转换或未定义的转换比率时抛出
     */
    private BigDecimal performCrossSystemConversion(BigDecimal value, UnitEnum fromUnit, UnitEnum toUnit) {
        String measureType = fromUnit.getMeasure(); // 假设fromUnit和toUnit属于同一测量类型
        Map<String, BigDecimal> ratios = SYSTEM_RATIOS.get(measureType);
        
        if (ratios == null) {
            throw new IllegalArgumentException("不支持的跨系统转换: " + measureType);
        }
        
        // 获取系统间转换比率
        String ratioKey = fromUnit.getSystem() + "_to_" + toUnit.getSystem();
        BigDecimal systemRatio = ratios.get(ratioKey);
        
        if (systemRatio == null) {
            throw new IllegalArgumentException("未定义的系统间转换比率: " + ratioKey);
        }
        
        // 转换步骤：
        // 1. 将源值转换为源系统的锚点单位
        BigDecimal fromFactor = TO_ANCHOR_FACTORS.get(fromUnit);
        BigDecimal anchorValue = value.multiply(fromFactor);
        
        // 2. 通过系统间比率转换到目标系统的锚点单位
        BigDecimal convertedAnchorValue = anchorValue.multiply(systemRatio);
        
        // 3. 从目标系统的锚点单位转换到目标单位
        BigDecimal toFactor = TO_ANCHOR_FACTORS.get(toUnit);
        return convertedAnchorValue.divide(toFactor, 10, RoundingMode.HALF_UP);
    }

    /**
     * 检查两个单位是否可以相互转换
     * 
     * @param fromUnit 源单位
     * @param toUnit 目标单位
     * @return 如果可以转换返回true，否则返回false
     */
    @Override
    public boolean canConvertBetween(UnitEnum fromUnit, UnitEnum toUnit) {
        // 同一测量类型才能转换
        return fromUnit.getMeasure().equals(toUnit.getMeasure());
    }

    /**
     * 将指定数值从源单位转换为目标单位
     * 
     * 转换逻辑：
     * 1. 参数验证：检查输入参数是否为空
     * 2. 兼容性检查：验证两个单位是否属于同一测量类型
     * 3. 特殊处理：温度等特殊单位使用专门的转换方法
     * 4. 一般转换：根据是否同属一个系统采用不同的转换策略
     *    - 同系统：直接使用转换因子进行计算
     *    - 跨系统：使用performCrossSystemConversion方法处理
     * 
     * @param value 待转换的数值
     * @param fromUnit 源单位
     * @param toUnit 目标单位
     * @return 转换结果对象
     * @throws IllegalArgumentException 当单位不兼容或参数无效时抛出
     */
    @Override
    public ConvertResult<BigDecimal> convert(BigDecimal value, UnitEnum fromUnit, UnitEnum toUnit) {
        if (value == null || fromUnit == null || toUnit == null) {
            throw new IllegalArgumentException("参数不能为空");
        }

        if (!canConvertBetween(fromUnit, toUnit)) {
            throw new IllegalArgumentException(
                String.format("无法在不同测量类型之间转换：%s (%s) 和 %s (%s)", 
                    fromUnit.getAbbr(), fromUnit.getMeasure(), 
                    toUnit.getAbbr(), toUnit.getMeasure())
            );
        }

        // 特殊处理温度转换
        if ("temperature".equals(fromUnit.getMeasure())) {
            return handleTemperatureConversion(value, fromUnit, toUnit);
        }

        // 一般转换逻辑
        BigDecimal fromFactor = TO_ANCHOR_FACTORS.get(fromUnit);
        BigDecimal toFactor = TO_ANCHOR_FACTORS.get(toUnit);

        if (fromFactor == null || toFactor == null) {
            throw new IllegalArgumentException("未知的单位");
        }

        // 检查是否需要系统间转换
        BigDecimal result;
        if (fromUnit.getSystem().equals(toUnit.getSystem())) {
            // 同一系统内的转换
            // 计算公式：结果 = (原值 × 源单位转换因子) ÷ 目标单位转换因子
            result = value.multiply(fromFactor).divide(toFactor, 10, RoundingMode.HALF_UP);
        } else {
            // 系统间转换，需要考虑系统间的比率
            result = performCrossSystemConversion(value, fromUnit, toUnit);
        }

        return new ConvertResult<>(result, toUnit.getAbbr(), toUnit.getSingular(), toUnit.getPlural());
    }



    /**
     * 获取最适合的单位表示
     * 
     * 根据数值大小自动选择最适合的单位，使得转换后的数值大于等于1且尽可能小
     * 使用默认选项配置进行转换
     * 
     * @param value 待转换的数值
     * @param fromUnit 源单位
     * @return 最佳单位转换结果
     */
    @Override
    public ConvertResult<BigDecimal> convertToBest(BigDecimal value, UnitEnum fromUnit) {
        return convertToBest(value, fromUnit, ToBestOptions.defaults());
    }

    /**
     * 获取最适合的单位表示（带选项）
     * 
     * 算法逻辑：
     * 1. 遍历同一测量类型下的所有可能单位
     * 2. 根据选项配置过滤单位（排除列表、系统限制等）
     * 3. 对每个候选单位进行转换计算
     * 4. 根据数值大小和截止值选择最合适的单位
     *    - 对于正数：选择大于等于截止值且最接近截止值的单位
     *    - 对于负数：选择小于等于截止值且最接近截止值的单位
     * 5. 返回最佳匹配的转换结果
     * 
     * @param value 待转换的数值
     * @param fromUnit 源单位
     * @param options 选项配置
     * @return 最佳单位转换结果
     */
    @Override
    public ConvertResult<BigDecimal> convertToBest(BigDecimal value, UnitEnum fromUnit, ToBestOptions options) {
        if (value == null || fromUnit == null) {
            throw new IllegalArgumentException("参数不能为空");
        }

        // 获取相同测量类型的所有单位
        List<UnitEnum> possibleUnits = getPossibleUnits(fromUnit.getMeasure());

        // 排除原始单位，寻找更适合的单位
        ConvertResult<BigDecimal> bestResult = null;
        BigDecimal bestValue = null;
        
        // 确定正负性和截止值
        boolean isNegative = value.compareTo(BigDecimal.ZERO) < 0;
        BigDecimal cutoff = (options != null && options.getCutOffNumber() != null) 
            ? new BigDecimal(options.getCutOffNumber().toString()) 
            : (isNegative ? new BigDecimal("-1") : new BigDecimal("1"));

        for (UnitEnum unit : possibleUnits) {
            // 检查是否需要排除此单位
            if (options != null && options.getExclude() != null && options.getExclude().contains(unit)) {
                continue;
            }
            
            // 检查是否限制了单位系统
            if (options != null && options.getSystem() != null && !options.getSystem().equals(unit.getSystem())) {
                continue;
            }

            try {
                ConvertResult<BigDecimal> result = convert(value, fromUnit, unit);
                
                // 跳过数值过大或过小的情况（避免选择不合适的大单位）
                if (isNegative) {
                    if (result.getVal().compareTo(cutoff) > 0) {
                        continue; // 跳过值太大的情况
                    }
                } else {
                    if (result.getVal().compareTo(cutoff) < 0) {
                        continue; // 跳过值太小的情况
                    }
                }

                // 选择最接近cutoff值的单位（但仍然大于等于cutoff）
                if (bestValue == null) {
                    bestValue = result.getVal();
                    bestResult = result;
                } else {
                    // 检查是否当前结果更接近cutoff
                    if (isNegative) {
                        // 负数情况下，更大的值更接近-1
                        if (result.getVal().compareTo(bestValue) > 0) {
                            bestValue = result.getVal();
                            bestResult = result;
                        }
                    } else {
                        // 正数情况下，更小的值更接近1（但仍然>=1）
                        if (result.getVal().compareTo(bestValue) < 0) {
                            bestValue = result.getVal();
                            bestResult = result;
                        }
                    }
                }
            } catch (Exception e) {
                // 如果转换失败，跳过此单位
                continue;
            }
        }

        // 如果没有找到更好的单位，返回原始单位的结果
        if (bestResult == null) {
            return convert(value, fromUnit, fromUnit);
        }

        return bestResult;
    }

    /**
     * 获取指定测量类型下的所有可能单位
     * 
     * 根据提供的测量类型名称，返回该类型下所有支持的单位枚举
     * 
     * @param measure 测量类型名称（如：length, mass, volume等）
     * @return 该测量类型下所有可能的单位列表
     * @throws IllegalArgumentException 当测量类型为空时抛出
     */
    @Override
    public java.util.List<UnitEnum> getPossibleUnits(String measure) {
        if (measure == null) {
            throw new IllegalArgumentException("测量类型不能为空");
        }

        java.util.List<UnitEnum> result = new java.util.ArrayList<>();
        
        for (UnitEnum unit : UnitEnum.values()) {
            if (measure.equals(unit.getMeasure())) {
                result.add(unit);
            }
        }
        
        return result;
    }

    /**
     * 获取所有支持的测量类型
     * 
     * 返回系统中所有支持的测量类型名称列表，如长度、质量、体积等
     * 
     * @return 所有支持的测量类型名称列表
     */
    @Override
    public java.util.List<String> getSupportedMeasures() {
        java.util.Set<String> measures = new java.util.HashSet<>();
        
        for (UnitEnum unit : UnitEnum.values()) {
            measures.add(unit.getMeasure());
        }
        
        return new java.util.ArrayList<>(measures);
    }

    /**
     * 处理温度转换的特殊情况
     * 
     * 温度转换需要特殊的偏移计算，不能直接使用比例因子
     * 转换策略：以摄氏度为中间单位，实现任意温度单位间的转换
     * 
     * 温度转换公式：
     * - 摄氏度 ↔ 华氏度: C = (F - 32) × 5/9, F = C × 9/5 + 32
     * - 摄氏度 ↔ 开尔文: K = C + 273.15, C = K - 273.15
     * - 摄氏度 ↔ 兰金温标: R = (C + 273.15) × 9/5, C = (R - 491.67) × 5/9
     * 
     * @param value 待转换的数值
     * @param fromUnit 源温度单位
     * @param toUnit 目标温度单位
     * @return 转换结果对象
     * @throws IllegalArgumentException 当遇到未知温度单位时抛出
     */
    private ConvertResult<BigDecimal> handleTemperatureConversion(BigDecimal value, UnitEnum fromUnit, UnitEnum toUnit) {
        if (fromUnit == toUnit) {
            return new ConvertResult<>(value, toUnit.getAbbr(), toUnit.getSingular(), toUnit.getPlural());
        }

        // 从任意温度单位转换到摄氏度（使用统一的中间单位）
        BigDecimal celsiusValue = value;
        switch (fromUnit) {
            case C:
                // 摄氏度到摄氏度，无需转换
                celsiusValue = value;
                break;
            case F:
                // 华氏度转摄氏度: C = (F - 32) × 5/9
                celsiusValue = value.subtract(new BigDecimal("32")).multiply(new BigDecimal("5")).divide(new BigDecimal("9"), 10, RoundingMode.HALF_UP);
                break;
            case K:
                // 开尔文转摄氏度: C = K - 273.15
                celsiusValue = value.subtract(new BigDecimal("273.15"));
                break;
            case R:
                // 兰金温标转摄氏度: 先转为华氏度(R - 459.67)，再转为摄氏度
                BigDecimal fahrenheitValue = value.subtract(new BigDecimal("459.67"));
                celsiusValue = fahrenheitValue.subtract(new BigDecimal("32")).multiply(new BigDecimal("5")).divide(new BigDecimal("9"), 10, RoundingMode.HALF_UP);
                break;
            default:
                throw new IllegalArgumentException("未知的温度单位: " + fromUnit.getAbbr());
        }

        // 从摄氏度转换到目标温度单位
        BigDecimal result;
        switch (toUnit) {
            case C:
                // 摄氏度到摄氏度，无需转换
                result = celsiusValue;
                break;
            case F:
                // 摄氏度转华氏度: F = C × 9/5 + 32
                result = celsiusValue.multiply(new BigDecimal("9")).divide(new BigDecimal("5"), 10, RoundingMode.HALF_UP).add(new BigDecimal("32"));
                break;
            case K:
                // 摄氏度转开尔文: K = C + 273.15
                result = celsiusValue.add(new BigDecimal("273.15"));
                break;
            case R:
                // 摄氏度转兰金温标: 先转为华氏度，然后再加上兰金温标的偏移量
                BigDecimal fahrenheitValue = celsiusValue.multiply(new BigDecimal("9")).divide(new BigDecimal("5"), 10, RoundingMode.HALF_UP).add(new BigDecimal("32"));
                result = fahrenheitValue.add(new BigDecimal("459.67"));
                break;
            default:
                throw new IllegalArgumentException("未知的温度单位: " + toUnit.getAbbr());
        }

        return new ConvertResult<>(result, toUnit.getAbbr(), toUnit.getSingular(), toUnit.getPlural());
    }

    /**
     * 获取指定单位的详细信息
     * 
     * @param unit 单位枚举
     * @return 单位详细信息
     */
    @Override
    public UnitDescription describeUnit(UnitEnum unit) {
        if (unit == null) {
            return new UnitDescription("unknown", "unknown", "unknown", "未知单位", "未知单位");
        }
        
        return UnitDescription.fromUnitEnum(unit);
    }

    /**
     * 获取所有支持的单位
     * 
     * @return 所有支持的单位描述列表
     */
    @Override
    public List<UnitDescription> listAllUnits() {
        List<UnitDescription> allUnits = new ArrayList<>();
        for (UnitEnum unit : UnitEnum.values()) {
            allUnits.add(describeUnit(unit));
        }
        return allUnits;
    }

    /**
     * 获取指定测量类型下的所有单位描述
     * 
     * @param measure 测量类型
     * @return 该测量类型下的所有单位描述
     */
    @Override
    public List<UnitDescription> listUnitsByMeasure(String measure) {
        if (measure == null) {
            return new ArrayList<>();
        }
        
        List<UnitDescription> units = new ArrayList<>();
        for (UnitEnum unit : UnitEnum.values()) {
            if (measure.equals(unit.getMeasure())) {
                units.add(describeUnit(unit));
            }
        }
        return units;
    }

    /**
     * 设置源单位和数值，用于链式调用
     * 
     * 此方法用于构建链式调用序列，例如：convertService.from(1, M).to(CM)
     * 该方法设置转换的源数值和源单位，并返回服务实例本身以支持进一步的方法链调用
     * 
     * @param value 源数值
     * @param fromUnit 源单位
     * @return 当前服务实例，支持链式调用
     * @throws IllegalStateException 当在.to()方法之后调用此方法时抛出
     * @throws IllegalArgumentException 当源数值或源单位为空时抛出
     */
    @Override
    public IUnitConversionService from(BigDecimal value, UnitEnum fromUnit) {
        if (this.currentDestinationUnit != null) {
            throw new IllegalStateException(".from must be called before .to");
        }
        if (value == null || fromUnit == null) {
            throw new IllegalArgumentException("源数值和源单位不能为空");
        }
        this.currentValue = value;
        this.currentOriginUnit = fromUnit;
        // 重置目标单位，允许重新开始链式调用
        this.currentDestinationUnit = null;
        return this;
    }

    /**
     * 执行单位转换，将已设置的源单位转换为目标单位
     * 
     * 此方法使用之前在.from()方法中设置的源数值和源单位，
     * 将其转换为指定的目标单位。
     * 
     * @param toUnit 目标单位
     * @return 转换结果对象
     * @throws IllegalStateException 当未先调用.from()方法时抛出
     * @throws IllegalArgumentException 当目标单位为空时抛出
     */
    @Override
    public ConvertResult<BigDecimal> to(UnitEnum toUnit) {
        if (this.currentOriginUnit == null) {
            throw new IllegalStateException("必须先调用from方法设置源单位");
        }
        if (toUnit == null) {
            throw new IllegalArgumentException("目标单位不能为空");
        }
        // 执行转换
        ConvertResult<BigDecimal> result = convert(this.currentValue, this.currentOriginUnit, toUnit);
        // 记录目标单位，但不立即重置状态，以支持后续操作
        this.currentDestinationUnit = toUnit;
        return result;
    }

    /**
     * 获取当前源单位可以转换到的所有可能单位
     * 
     * 如果指定了测量类型，则返回该类型下所有可能的单位；
     * 否则返回系统中所有支持的单位。
     * 
     * @param measure 可选的测量类型过滤器，如果为null则返回所有单位
     * @return 可能的单位列表
     */
    @Override
    public List<UnitEnum> possibilities(String measure) {
        // 如果指定了测量类型，则返回该类型下所有可能的单位
        if (measure != null) {
            return getPossibleUnits(measure);
        } else {
            // 返回所有可能的单位
            List<UnitEnum> allUnits = new ArrayList<>();
            for (UnitEnum unit : UnitEnum.values()) {
                allUnits.add(unit);
            }
            return allUnits;
        }
    }

    /**
     * 获取所有支持的测量类型
     * 
     * 返回系统中所有支持的测量类型名称列表，与getSupportedMeasures()方法功能相同
     * 
     * @return 所有支持的测量类型名称列表
     */
    @Override
    public List<String> measures() {
        return getSupportedMeasures();
    }
    
    /**
     * 重置当前转换状态
     */
    public void reset() {
        this.currentValue = null;
        this.currentOriginUnit = null;
        this.currentDestinationUnit = null;
    }
}