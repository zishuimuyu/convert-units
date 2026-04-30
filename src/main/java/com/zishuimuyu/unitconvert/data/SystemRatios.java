package com.zishuimuyu.unitconvert.data;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

/**
 * 系统间转换比率管理类
 * <P>
 * 管理不同单位系统之间的转换比率
 * <P>
 * 功能列表：
 * <ul>
 *   <li>提供统一的系统间转换比率存储</li>
 *   <li>按测量类型管理转换比率</li>
 *   <li>支持快速查找和获取系统间转换比率</li>
 *   <li>维护不同系统间的数学关系</li>
 * </ul>
 * <P>
 * 设计原理：
 * <ol>
 *   <li>每个测量类型（measureType）可以有多个单位系统</li>
 *   <li>系统间转换比率用于将一个系统的锚点单位转换为另一个系统的锚点单位</li>
 *   <li>在当前实现中，同一测量类型的所有系统都使用相同的锚点单位，所以比率通常为1</li>
 * </ol>
 * <P>
 * 优势：
 * <ul>
 *   <li>减少存储空间：按测量类型分组存储转换比率</li>
 *   <li>提高转换精度：明确的系统间转换逻辑</li>
 *   <li>易于维护：集中管理所有系统间转换比率</li>
 * </ul>
 * <P>
 * 使用场景：
 * <ol>
 *   <li>当转换涉及不同系统间的单位时（如METRIC到IMPERIAL）</li>
 *   <li>通过系统间比率实现跨系统转换</li>
 *   <li>需要精确控制不同系统间的转换逻辑</li>
 * </ol>
 * <P>
 * 使用示例：
 * <pre>
 * // 获取压力单位的系统间转换比率
 * BigDecimal ratio = SystemRatios.getRatio("pressure", "metric_to_imperial");
 * 
 * // 检查是否存在特定的转换比率
 * boolean hasRatio = SystemRatios.hasRatio("pressure", "metric_to_imperial");
 * 
 * // 获取所有系统间转换比率
 * Map<String, Map<String, BigDecimal>> allRatios = SystemRatios.getAllRatios();
 * </pre>
 * <P>
 * 
 * @author 紫水木鱼
 * @version 1.0.0
 * @since 2026-04-25
 */
public class SystemRatios {
    
    /**
     * 存储系统间转换比率
     * <P>
     * 详细描述字段的用途、取值范围或特殊含义
     * <P>
     * 用途：存储所有测量类型的系统间转换比率
     * <P>
     * 取值范围：键为测量类型字符串，值为比率键到比率值的映射
     * <P>
     * 特殊含义：结构为measureType -> ratioKey -> ratioValue，例如pressure -> "metric_to_imperial" -> BigDecimal.ONE
     */
    private static final Map<String, Map<String, BigDecimal>> SYSTEM_RATIOS = new HashMap<>();
    
    static {
        initializeSystemRatios();
    }
    
    /**
     * 私有构造函数，防止实例化
     * <P>
     * 详细描述方法的功能、算法逻辑或业务流程
     * <P>
     * 处理逻辑：
     * <ol>
     *   <li>私有构造函数阻止外部直接实例化此类</li>
     *   <li>确保此类只能通过静态方法访问</li>
     *   <li>符合工具类的设计模式</li>
     * </ol>
     * <P>
     * 参数说明：
     * <ul>
     *   <li>无参数</li>
     * </ul>
     * <P>
     * 返回值说明：
     * <ul>
     *   <li>void: 无返回值</li>
     * </ul>
     * <P>
     * 异常情况：
     * <ul>
     *   <li>无异常情况</li>
     * </ul>
     */
    private SystemRatios() {
    }
    
    /**
     * 获取指定测量类型和比率键的转换比率
     * <P>
     * 详细描述方法的功能、算法逻辑或业务流程
     * <P>
     * 处理逻辑：
     * <ol>
     *   <li>根据测量类型从SYSTEM_RATIOS映射中获取对应的比率映射</li>
     *   <li>如果测量类型不存在，则返回null</li>
     *   <li>从比率映射中获取指定比率键对应的转换比率</li>
     *   <li>返回找到的转换比率，如果不存在则返回null</li>
     * </ol>
     * <P>
     * 参数说明：
     * <ul>
     *   <li>measureType: 测量类型（如"pressure", "energy"等），用于确定比率组</li>
     *   <li>ratioKey: 比率键（如"metric_to_imperial", "SI_to_nutrition"等），用于确定具体比率</li>
     * </ul>
     * <P>
     * 返回值说明：
     * <ul>
     *   <li>成功: 返回对应的转换比率（BigDecimal类型）</li>
     *   <li>失败: 返回null（当测量类型或比率键不存在时）</li>
     * </ul>
     * <P>
     * 异常情况：
     * <ul>
     *   <li>当measureType或ratioKey为null时，可能返回null</li>
     *   <li>当指定的比率不存在时，返回null</li>
     * </ul>
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
     * <P>
     * 详细描述方法的功能、算法逻辑或业务流程
     * <P>
     * 处理逻辑：
     * <ol>
     *   <li>根据测量类型从SYSTEM_RATIOS映射中获取对应的比率映射</li>
     *   <li>如果测量类型不存在，则返回false</li>
     *   <li>检查比率映射中是否包含指定的比率键</li>
     *   <li>返回检查结果（存在返回true，不存在返回false）</li>
     * </ol>
     * <P>
     * 参数说明：
     * <ul>
     *   <li>measureType: 测量类型，用于确定比率组</li>
     *   <li>ratioKey: 比率键，用于确定要检查的具体比率</li>
     * </ul>
     * <P>
     * 返回值说明：
     * <ul>
     *   <li>成功: 返回boolean值表示比率是否存在</li>
     *   <li>失败: 返回false（当测量类型或比率键不存在时）</li>
     * </ul>
     * <P>
     * 异常情况：
     * <ul>
     *   <li>当measureType或ratioKey为null时，可能返回false</li>
     *   <li>当指定的比率不存在时，返回false</li>
     * </ul>
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
     * <P>
     * 详细描述方法的功能、算法逻辑或业务流程
     * <P>
     * 处理逻辑：
     * <ol>
     *   <li>创建一个新的HashMap作为结果容器</li>
     *   <li>遍历SYSTEM_RATIOS中的所有条目</li>
     *   <li>对每个条目的值（内部Map）进行深拷贝</li>
     *   <li>将深拷贝后的条目放入结果容器</li>
     *   <li>返回包含所有转换比率副本的结果容器</li>
     * </ol>
     * <P>
     * 参数说明：
     * <ul>
     *   <li>无参数</li>
     * </ul>
     * <P>
     * 返回值说明：
     * <ul>
     *   <li>成功: 返回所有转换比率的深拷贝（Map<String, Map<String, BigDecimal>>类型）</li>
     *   <li>失败: 不会失败，但可能返回空的Map</li>
     * </ul>
     * <P>
     * 异常情况：
     * <ul>
     *   <li>无异常情况</li>
     * </ul>
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
     * <P>
     * 详细描述方法的功能、算法逻辑或业务流程
     * <P>
     * 处理逻辑：
     * <ol>
     *   <li>依次调用各个测量类型的系统间转换比率初始化方法</li>
     *   <li>包括长度、面积、体积、质量、速度、压力、能量、力、扭矩、照度、步速和体积流量</li>
     *   <li>确保所有测量类型的系统间转换比率都被正确初始化</li>
     * </ol>
     * <P>
     * 参数说明：
     * <ul>
     *   <li>无参数</li>
     * </ul>
     * <P>
     * 返回值说明：
     * <ul>
     *   <li>void: 无返回值</li>
     * </ul>
     * <P>
     * 异常情况：
     * <ul>
     *   <li>无异常情况</li>
     * </ul>
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
        initializeTimeSystemRatios();
        initializeAccelerationSystemRatios();
        initializePowerSystemRatios();
    }
    
    /**
     * 初始化功率单位系统间转换比率
     * <P>
     * 详细描述方法的功能、算法逻辑或业务流程
     * <P>
     * 处理逻辑：
     * <ol>
     *   <li>创建功率单位的系统间转换比率映射</li>
     *   <li>设置metric到英制的转换比率（metric_to_imperial）</li>
     *   <li>设置英制到公制的转换比率（imperial_to_metric）</li>
     *   <li>设置metric到CGS的转换比率（metric_to_cgs）</li>
     *   <li>设置CGS到公制的转换比率（cgs_to_metric）</li>
     *   <li>将功率单位的转换比率映射添加到主映射中</li>
     * </ol>
     * <P>
     * 转换因子说明：
     * <ul>
     *   <li>metric锚点单位是W(1)，imperial锚点单位是HP(1)</li>
     *   <li>1 HP = 745.7 W，所以转换比率为：</li>
     *   <li>metric_to_imperial: 1 W = ~0.001341 HP</li>
     *   <li>imperial_to_metric: 1 HP = 745.7 W</li>
     *   <li>cgs锚点单位是ERG_PER_S(1)，与metric锚点单位W(1)的关系：</li>
     *   <li>1 W = 10^7 erg/s，所以转换比率为：</li>
     *   <li>metric_to_cgs: 1 W = 10000000 erg/s</li>
     *   <li>cgs_to_metric: 1 erg/s = 0.0000001 W</li>
     * </ul>
     * <P>
     * 参数说明：
     * <ul>
     *   <li>无参数</li>
     * </ul>
     * <P>
     * 返回值说明：
     * <ul>
     *   <li>void: 无返回值</li>
     * </ul>
     * <P>
     * 异常情况：
     * <ul>
     *   <li>无异常情况</li>
     * </ul>
     */
    private static void initializePowerSystemRatios() {
        Map<String, BigDecimal> powerRatios = new HashMap<>();
        
        // metric与英制单位间的转换
        // metric锚点单位是W(1)，imperial锚点单位是HP(1)
        // 1 HP = 745.7 W，所以转换比率为：
        powerRatios.put("metric_to_imperial", new BigDecimal("1").divide(new BigDecimal("745.7"), 10, RoundingMode.HALF_UP));  // 1 W = ~0.001341 HP
        powerRatios.put("imperial_to_metric", new BigDecimal("745.7"));  // 1 HP = 745.7 W
        
        // metric与CGS单位间的转换
        // metric锚点单位是W(1)，cgs锚点单位是ERG_PER_S(1)
        // 1 W = 10^7 erg/s，所以转换比率为：
        powerRatios.put("metric_to_cgs", new BigDecimal("1e7"));  // 1 W = 10000000 erg/s
        powerRatios.put("cgs_to_metric", new BigDecimal("1e-7"));  // 1 erg/s = 0.0000001 W
        
        SYSTEM_RATIOS.put("power", powerRatios);
    }
    
    /**
     * 初始化长度单位系统间转换比率
     * <P>
     * 详细描述方法的功能、算法逻辑或业务流程
     * <P>
     * 处理逻辑：
     * <ol>
     *   <li>创建长度单位的系统间转换比率映射</li>
     *   <li>设置公制到英制的转换比率（metric_to_imperial）</li>
     *   <li>设置英制到公制的转换比率（imperial_to_metric）</li>
     *   <li>将长度单位的转换比率映射添加到主映射中</li>
     * </ol>
     * <P>
     * 转换因子说明：
     * <ul>
     *   <li>metric_to_imperial: 1米 = 39.3701英寸，使用39.3701作为转换因子</li>
     *   <li>imperial_to_metric: 1英寸 = 0.0254米，使用0.0254作为转换因子</li>
     * </ul>
     * <P>
     * 参数说明：
     * <ul>
     *   <li>无参数</li>
     * </ul>
     * <P>
     * 返回值说明：
     * <ul>
     *   <li>void: 无返回值</li>
     * </ul>
     * <P>
     * 异常情况：
     * <ul>
     *   <li>无异常情况</li>
     * </ul>
     */
    private static void initializeLengthSystemRatios() {
        Map<String, BigDecimal> lengthRatios = new HashMap<>();
        
        lengthRatios.put("metric_to_imperial", new BigDecimal("39.3701"));
        lengthRatios.put("imperial_to_metric", new BigDecimal("0.0254"));
        
        SYSTEM_RATIOS.put("length", lengthRatios);
    }
    
    /**
     * 初始化面积单位系统间转换比率
     * <P>
     * 详细描述方法的功能、算法逻辑或业务流程
     * <P>
     * 处理逻辑：
     * <ol>
     *   <li>创建面积单位的系统间转换比率映射</li>
     *   <li>设置公制到英制的转换比率（metric_to_imperial）</li>
     *   <li>设置英制到公制的转换比率（imperial_to_metric）</li>
     *   <li>将面积单位的转换比率映射添加到主映射中</li>
     * </ol>
     * <P>
     * 转换因子说明：
     * <ul>
     *   <li>metric_to_imperial: 1平方米 = 1550平方英寸，使用1550作为转换因子</li>
     *   <li>imperial_to_metric: 1平方英寸 = 0.00064516平方米，使用0.00064516作为转换因子</li>
     * </ul>
     * <P>
     * 参数说明：
     * <ul>
     *   <li>无参数</li>
     * </ul>
     * <P>
     * 返回值说明：
     * <ul>
     *   <li>void: 无返回值</li>
     * </ul>
     * <P>
     * 异常情况：
     * <ul>
     *   <li>无异常情况</li>
     * </ul>
     */
    private static void initializeAreaSystemRatios() {
        Map<String, BigDecimal> areaRatios = new HashMap<>();
        
        areaRatios.put("metric_to_imperial", new BigDecimal("1550"));
        areaRatios.put("imperial_to_metric", new BigDecimal("0.00064516"));
        
        SYSTEM_RATIOS.put("area", areaRatios);
    }
    
    /**
     * 初始化体积单位系统间转换比率
     * <P>
     * 详细描述方法的功能、算法逻辑或业务流程
     * <P>
     * 处理逻辑：
     * <ol>
     *   <li>创建体积单位的系统间转换比率映射</li>
     *   <li>设置公制到英制的转换比率（metric_to_imperial）</li>
     *   <li>设置英制到公制的转换比率（imperial_to_metric）</li>
     *   <li>将体积单位的转换比率映射添加到主映射中</li>
     * </ol>
     * <P>
     * 转换因子说明：
     * <ul>
     *   <li>metric_to_imperial: 1立方分米 = 61.0237立方英寸，使用61.0237作为转换因子</li>
     *   <li>imperial_to_metric: 1立方英寸 = 0.0163871立方分米，使用0.0163871作为转换因子</li>
     * </ul>
     * <P>
     * 参数说明：
     * <ul>
     *   <li>无参数</li>
     * </ul>
     * <P>
     * 返回值说明：
     * <ul>
     *   <li>void: 无返回值</li>
     * </ul>
     * <P>
     * 异常情况：
     * <ul>
     *   <li>无异常情况</li>
     * </ul>
     */
    private static void initializeVolumeSystemRatios() {
        Map<String, BigDecimal> volumeRatios = new HashMap<>();
        
        volumeRatios.put("metric_to_imperial", new BigDecimal("61.0237"));
        volumeRatios.put("imperial_to_metric", new BigDecimal("0.0163871"));
        
        SYSTEM_RATIOS.put("volume", volumeRatios);
    }
    
    /**
     * 初始化质量单位系统间转换比率
     * <P>
     * 详细描述方法的功能、算法逻辑或业务流程
     * <P>
     * 处理逻辑：
     * <ol>
     *   <li>创建质量单位的系统间转换比率映射</li>
     *   <li>设置公制到英制的转换比率（metric_to_imperial）</li>
     *   <li>设置英制到公制的转换比率（imperial_to_metric）</li>
     *   <li>设置公制到中国单位的转换比率（metric_to_chinese）</li>
     *   <li>设置中国单位到公制的转换比率（chinese_to_metric）</li>
     *   <li>设置英制到中国单位的转换比率（imperial_to_chinese）</li>
     *   <li>设置中国单位到英制的转换比率（chinese_to_imperial）</li>
     *   <li>将质量单位的转换比率映射添加到主映射中</li>
     * </ol>
     * <P>
     * 转换因子说明：
     * <ul>
     *   <li>metric_to_imperial: 1克锚点 = 0.00220462磅锚点，使用0.00220462作为转换因子</li>
     *   <li>imperial_to_metric: 1磅锚点 = 453.592克锚点，使用453.592作为转换因子</li>
     *   <li>metric_to_chinese: 1克锚点 = 0.002斤锚点(1/500)，使用0.002作为转换因子</li>
     *   <li>chinese_to_metric: 1斤锚点 = 500克锚点，使用500作为转换因子</li>
     *   <li>imperial_to_chinese: 1磅锚点 = 0.907184斤锚点，使用0.907184作为转换因子</li>
     *   <li>chinese_to_imperial: 1斤锚点 = 1.10231磅锚点，使用1.10231作为转换因子</li>
     * </ul>
     * <P>
     * 参数说明：
     * <ul>
     *   <li>无参数</li>
     * </ul>
     * <P>
     * 返回值说明：
     * <ul>
     *   <li>void: 无返回值</li>
     * </ul>
     * <P>
     * 异常情况：
     * <ul>
     *   <li>无异常情况</li>
     * </ul>
     */
    private static void initializeMassSystemRatios() {
        Map<String, BigDecimal> massRatios = new HashMap<>();
        
        // 所有ConversionFactors都基于统一锚点单位（克）
        // 因此系统间比率应为1，如果锚点单位相同
        massRatios.put("metric_to_imperial", new BigDecimal("1"));
        massRatios.put("imperial_to_metric", new BigDecimal("1"));
        massRatios.put("metric_to_chinese", new BigDecimal("1"));
        massRatios.put("chinese_to_metric", new BigDecimal("1"));
        massRatios.put("imperial_to_chinese", new BigDecimal("1"));
        massRatios.put("chinese_to_imperial", new BigDecimal("1"));
        
        SYSTEM_RATIOS.put("mass", massRatios);
    }
    
    /**
     * 初始化速度单位系统间转换比率
     * <P>
     * 详细描述方法的功能、算法逻辑或业务流程
     * <P>
     * 处理逻辑：
     * <ol>
     *   <li>创建速度单位的系统间转换比率映射</li>
     *   <li>设置公制到英制的转换比率（metric_to_imperial）</li>
     *   <li>设置英制到公制的转换比率（imperial_to_metric）</li>
     *   <li>将速度单位的转换比率映射添加到主映射中</li>
     * </ol>
     * <P>
     * 转换因子说明：
     * <ul>
     *   <li>metric_to_imperial: 1米/秒 = 3.28084英尺/秒，使用3.28084作为转换因子</li>
     *   <li>imperial_to_metric: 1英尺/秒 = 0.3048米/秒，使用0.3048作为转换因子</li>
     * </ul>
     * <P>
     * 参数说明：
     * <ul>
     *   <li>无参数</li>
     * </ul>
     * <P>
     * 返回值说明：
     * <ul>
     *   <li>void: 无返回值</li>
     * </ul>
     * <P>
     * 异常情况：
     * <ul>
     *   <li>无异常情况</li>
     * </ul>
     */
    private static void initializeSpeedSystemRatios() {
        Map<String, BigDecimal> speedRatios = new HashMap<>();
        
        speedRatios.put("metric_to_imperial", new BigDecimal("3.28084"));
        speedRatios.put("imperial_to_metric", new BigDecimal("0.3048"));
        
        SYSTEM_RATIOS.put("speed", speedRatios);
    }
    
    /**
     * 初始化压力单位系统间转换比率
     * <P>
     * 详细描述方法的功能、算法逻辑或业务流程
     * <P>
     * 处理逻辑：
     * <ol>
     *   <li>创建压力单位的系统间转换比率映射</li>
     *   <li>设置公制到英制的转换比率（metric_to_imperial）</li>
     *   <li>设置英制到公制的转换比率（imperial_to_metric）</li>
     *   <li>将压力单位的转换比率映射添加到主映射中</li>
     * </ol>
     * <P>
     * 转换因子说明：
     * <ul>
     *   <li>metric_to_imperial: 1帕斯卡 = 0.000145038磅/平方英寸，使用0.000145038作为转换因子</li>
     *   <li>imperial_to_metric: 1磅/平方英寸 = 6894.76帕斯卡，使用6894.76作为转换因子</li>
     * </ul>
     * <P>
     * 参数说明：
     * <ul>
     *   <li>无参数</li>
     * </ul>
     * <P>
     * 返回值说明：
     * <ul>
     *   <li>void: 无返回值</li>
     * </ul>
     * <P>
     * 异常情况：
     * <ul>
     *   <li>无异常情况</li>
     * </ul>
     */
    private static void initializePressureSystemRatios() {
        Map<String, BigDecimal> pressureRatios = new HashMap<>();
        
        pressureRatios.put("metric_to_imperial", new BigDecimal("0.000145038"));
        pressureRatios.put("imperial_to_metric", new BigDecimal("6894.76"));
        
        SYSTEM_RATIOS.put("pressure", pressureRatios);
    }
    
    /**
     * 初始化能量单位系统间转换比率
     * <P>
     * 详细描述方法的功能、算法逻辑或业务流程
     * <P>
     * 处理逻辑：
     * <ol>
     *   <li>创建能量单位的系统间转换比率映射</li>
     *   <li>设置SI到营养单位的转换比率（SI_to_nutrition）</li>
     *   <li>设置营养到SI单位的转换比率（nutrition_to_SI）</li>
     *   <li>将能量单位的转换比率映射添加到主映射中</li>
     * </ol>
     * <P>
     * 转换因子说明：
     * <ul>
     *   <li>SI_to_nutrition: 1焦耳 = 0.239006卡路里，使用0.239006作为转换因子</li>
     *   <li>nutrition_to_SI: 1卡路里 = 4.184焦耳，使用4.184作为转换因子</li>
     * </ul>
     * <P>
     * 参数说明：
     * <ul>
     *   <li>无参数</li>
     * </ul>
     * <P>
     * 返回值说明：
     * <ul>
     *   <li>void: 无返回值</li>
     * </ul>
     * <P>
     * 异常情况：
     * <ul>
     *   <li>无异常情况</li>
     * </ul>
     */
    private static void initializeEnergySystemRatios() {
        Map<String, BigDecimal> energyRatios = new HashMap<>();
        
        // SI与营养学单位间的转换
        // nutrition锚点单位是CAL(1)，SI锚点单位是J(1)
        // 1 CAL = 4.184 J，所以转换比率为：
        energyRatios.put("SI_to_nutrition", new BigDecimal("1").divide(new BigDecimal("4.184"), 10, RoundingMode.HALF_UP));  // 1 J = ~0.239 CAL
        energyRatios.put("nutrition_to_SI", new BigDecimal("4.184"));  // 1 CAL = 4.184 J
        
        // SI与英制单位间的转换
        // SI锚点单位是J(1)，imperial锚点单位是BTU(1)
        // 1 BTU = 1055.06 J，所以转换比率为：
        energyRatios.put("SI_to_imperial", new BigDecimal("1").divide(new BigDecimal("1055.06"), 10, RoundingMode.HALF_UP));  // 1 J = ~0.0009478 BTU
        energyRatios.put("imperial_to_SI", new BigDecimal("1055.06"));  // 1 BTU = 1055.06 J
        
        // SI与CGS单位间的转换
        // SI锚点单位是J(1)，cgs锚点单位是ERG(1)
        // 1 J = 10^7 erg，所以转换比率为：
        energyRatios.put("SI_to_cgs", new BigDecimal("1e7"));  // 1 SI锚点(J) = 10000000 cgs锚点(erg)
        energyRatios.put("cgs_to_SI", new BigDecimal("1e-7"));  // 1 cgs锚点(erg) = 0.0000001 SI锚点(J)
        
        // SI与metric单位间的转换 (用于KGFM等单位)
        // SI锚点单位是J(1)，metric锚点单位是KGFM(1)
        // 1 KGFM = 9.80665 J，所以转换比率为：
        energyRatios.put("SI_to_metric", new BigDecimal("1").divide(new BigDecimal("9.80665"), 10, RoundingMode.HALF_UP));  // 1 SI锚点(J) = ~0.102 metric锚点(KGFM)
        energyRatios.put("metric_to_SI", new BigDecimal("9.80665"));  // 1 metric锚点(KGFM) = 9.80665 SI锚点(J)
        
        SYSTEM_RATIOS.put("energy", energyRatios);
    }
    
    /**
     * 初始化力单位系统间转换比率
     * <P>
     * 详细描述方法的功能、算法逻辑或业务流程
     * <P>
     * 处理逻辑：
     * <ol>
     *   <li>创建力单位的系统间转换比率映射</li>
     *   <li>设置SI到英制的转换比率（SI_to_imperial）</li>
     *   <li>设置英制到SI单位的转换比率（imperial_to_SI）</li>
     *   <li>将力单位的转换比率映射添加到主映射中</li>
     * </ol>
     * <P>
     * 转换因子说明：
     * <ul>
     *   <li>SI_to_imperial: 1牛顿 = 0.224809磅力，使用0.224809作为转换因子</li>
     *   <li>imperial_to_SI: 1磅力 = 4.44822牛顿，使用4.44822作为转换因子</li>
     * </ul>
     * <P>
     * 参数说明：
     * <ul>
     *   <li>无参数</li>
     * </ul>
     * <P>
     * 返回值说明：
     * <ul>
     *   <li>void: 无返回值</li>
     * </ul>
     * <P>
     * 异常情况：
     * <ul>
     *   <li>无异常情况</li>
     * </ul>
     */
    private static void initializeForceSystemRatios() {
        Map<String, BigDecimal> forceRatios = new HashMap<>();
        
        forceRatios.put("SI_to_imperial", new BigDecimal("0.224809"));
        forceRatios.put("imperial_to_SI", new BigDecimal("4.44822"));
        
        SYSTEM_RATIOS.put("force", forceRatios);
    }
    
    /**
     * 初始化扭矩单位系统间转换比率
     * <P>
     * 详细描述方法的功能、算法逻辑或业务流程
     * <P>
     * 处理逻辑：
     * <ol>
     *   <li>创建扭矩单位的系统间转换比率映射</li>
     *   <li>设置公制到英制的转换比率（metric_to_imperial）</li>
     *   <li>设置英制到公制的转换比率（imperial_to_metric）</li>
     *   <li>将扭矩单位的转换比率映射添加到主映射中</li>
     * </ol>
     * <P>
     * 转换因子说明：
     * <ul>
     *   <li>metric_to_imperial: 1牛顿米 = 8.85075磅英寸，使用8.85075作为转换因子</li>
     *   <li>imperial_to_metric: 1磅英寸 = 0.112985牛顿米，使用0.112985作为转换因子</li>
     * </ul>
     * <P>
     * 参数说明：
     * <ul>
     *   <li>无参数</li>
     * </ul>
     * <P>
     * 返回值说明：
     * <ul>
     *   <li>void: 无返回值</li>
     * </ul>
     * <P>
     * 异常情况：
     * <ul>
     *   <li>无异常情况</li>
     * </ul>
     */
    private static void initializeTorqueSystemRatios() {
        Map<String, BigDecimal> torqueRatios = new HashMap<>();
        
        torqueRatios.put("metric_to_imperial", new BigDecimal("8.85075"));
        torqueRatios.put("imperial_to_metric", new BigDecimal("0.112985"));
        
        SYSTEM_RATIOS.put("torque", torqueRatios);
    }
    
    /**
     * 初始化照度单位系统间转换比率
     * <P>
     * 详细描述方法的功能、算法逻辑或业务流程
     * <P>
     * 处理逻辑：
     * <ol>
     *   <li>创建照度单位的系统间转换比率映射</li>
     *   <li>设置公制到英制的转换比率（metric_to_imperial）</li>
     *   <li>设置英制到公制的转换比率（imperial_to_metric）</li>
     *   <li>将照度单位的转换比率映射添加到主映射中</li>
     * </ol>
     * <P>
     * 转换因子说明：
     * <ul>
     *   <li>metric_to_imperial: 1勒克斯 = 0.092903英尺烛光，使用0.092903作为转换因子</li>
     *   <li>imperial_to_metric: 1英尺烛光 = 10.7639勒克斯，使用10.7639作为转换因子</li>
     * </ul>
     * <P>
     * 参数说明：
     * <ul>
     *   <li>无参数</li>
     * </ul>
     * <P>
     * 返回值说明：
     * <ul>
     *   <li>void: 无返回值</li>
     * </ul>
     * <P>
     * 异常情况：
     * <ul>
     *   <li>无异常情况</li>
     * </ul>
     */
    private static void initializeIlluminanceSystemRatios() {
        Map<String, BigDecimal> illuminanceRatios = new HashMap<>();
        
        illuminanceRatios.put("metric_to_imperial", new BigDecimal("0.092903"));
        illuminanceRatios.put("imperial_to_metric", new BigDecimal("10.7639"));
        
        SYSTEM_RATIOS.put("illuminance", illuminanceRatios);
    }
    
    /**
     * 初始化步速单位系统间转换比率
     * <P>
     * 详细描述方法的功能、算法逻辑或业务流程
     * <P>
     * 处理逻辑：
     * <ol>
     *   <li>创建步速单位的系统间转换比率映射</li>
     *   <li>设置公制到英制的转换比率（metric_to_imperial）</li>
     *   <li>设置英制到公制的转换比率（imperial_to_metric）</li>
     *   <li>将步速单位的转换比率映射添加到主映射中</li>
     * </ol>
     * <P>
     * 转换因子说明：
     * <ul>
     *   <li>metric_to_imperial: 1分钟/公里 = 1.60934分钟/英里，使用1.60934作为转换因子</li>
     *   <li>imperial_to_metric: 1分钟/英里 = 0.621371分钟/公里，使用0.621371作为转换因子</li>
     * </ul>
     * <P>
     * 参数说明：
     * <ul>
     *   <li>无参数</li>
     * </ul>
     * <P>
     * 返回值说明：
     * <ul>
     *   <li>void: 无返回值</li>
     * </ul>
     * <P>
     * 异常情况：
     * <ul>
     *   <li>无异常情况</li>
     * </ul>
     */
    private static void initializePaceSystemRatios() {
        Map<String, BigDecimal> paceRatios = new HashMap<>();
        
        paceRatios.put("metric_to_imperial", new BigDecimal("1.60934"));
        paceRatios.put("imperial_to_metric", new BigDecimal("0.621371"));
        
        SYSTEM_RATIOS.put("pace", paceRatios);
    }
    
    /**
     * 初始化体积流量率单位系统间转换比率
     * <P>
     * 详细描述方法的功能、算法逻辑或业务流程
     * <P>
     * 处理逻辑：
     * <ol>
     *   <li>创建体积流量率单位的系统间转换比率映射</li>
     *   <li>设置公制到英制的转换比率（metric_to_imperial）</li>
     *   <li>设置英制到公制的转换比率（imperial_to_metric）</li>
     *   <li>将体积流量率单位的转换比率映射添加到主映射中</li>
     * </ol>
     * <P>
     * 转换因子说明：
     * <ul>
     *   <li>metric_to_imperial: 1 dm³/s = 1/28.3168 ft³/s，使用1/28.3168作为转换因子</li>
     *   <li>imperial_to_metric: 1 ft³/s = 28.3168 dm³/s，使用28.3168作为转换因子</li>
     * </ul>
     * <P>
     * 参数说明：
     * <ul>
     *   <li>无参数</li>
     * </ul>
     * <P>
     * 返回值说明：
     * <ul>
     *   <li>void: 无返回值</li>
     * </ul>
     * <P>
     * 异常情况：
     * <ul>
     *   <li>无异常情况</li>
     * </ul>
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
    
    /**
     * 初始化时间单位系统间转换比率
     * <P>
     * 详细描述方法的功能、算法逻辑或业务流程
     * <P>
     * 处理逻辑：
     * <ol>
     *   <li>创建时间单位的系统间转换比率映射</li>
     *   <li>设置公制到英制的转换比率（metric_to_imperial）</li>
     *   <li>设置英制到公制的转换比率（imperial_to_metric）</li>
     *   <li>设置公制到中国传统的转换比率（metric_to_chinese）</li>
     *   <li>设置中国传统到公制的转换比率（chinese_to_metric）</li>
     *   <li>将时间单位的转换比率映射添加到主映射中</li>
     * </ol>
     * <P>
     * 转换因子说明：
     * <ul>
     *   <li>所有时间单位都基于统一锚点（秒），因此系统间比率应为1</li>
     * </ul>
     * <P>
     * 参数说明：
     * <ul>
     *   <li>无参数</li>
     * </ul>
     * <P>
     * 返回值说明：
     * <ul>
     *   <li>void: 无返回值</li>
     * </ul>
     * <P>
     * 异常情况：
     * <ul>
     *   <li>无异常情况</li>
     * </ul>
     */
    private static void initializeTimeSystemRatios() {
        Map<String, BigDecimal> timeRatios = new HashMap<>();
        
        // 所有ConversionFactors都基于统一锚点单位（秒）
        // 因此系统间比率应为1，如果锚点单位相同
        timeRatios.put("metric_to_imperial", new BigDecimal("1"));
        timeRatios.put("imperial_to_metric", new BigDecimal("1"));
        timeRatios.put("metric_to_chinese", new BigDecimal("1"));
        timeRatios.put("chinese_to_metric", new BigDecimal("1"));
        timeRatios.put("imperial_to_chinese", new BigDecimal("1"));
        timeRatios.put("chinese_to_imperial", new BigDecimal("1"));
        
        SYSTEM_RATIOS.put("time", timeRatios);
    }
    
    /**
     * 初始化加速度单位系统间转换比率
     * <P>
     * 详细描述方法的功能、算法逻辑或业务流程
     * <P>
     * 处理逻辑：
     * <ol>
     *   <li>创建加速度单位的系统间转换比率映射</li>
     *   <li>设置metric到英制的转换比率（metric_to_imperial）</li>
     *   <li>设置英制到公制的转换比率（imperial_to_metric）</li>
     *   <li>设置metric到CGS的转换比率（metric_to_cgs）</li>
     *   <li>设置CGS到公制的转换比率（cgs_to_metric）</li>
     *   <li>将加速度单位的转换比率映射添加到主映射中</li>
     * </ol>
     * <P>
     * 转换因子说明：
     * <ul>
     *   <li>metric锚点单位是M_S2(1)，imperial锚点单位是FT_S2(1)</li>
     *   <li>1 ft/s² = 0.3048 m/s²，所以转换比率为：</li>
     *   <li>metric_to_imperial: 1 m/s² = ~3.28084 ft/s²</li>
     *   <li>imperial_to_metric: 1 ft/s² = 0.3048 m/s²</li>
     *   <li>cgs锚点单位是GAL(1)，与metric锚点单位M_S2(1)的关系：</li>
     *   <li>1 m/s² = 100 Gal，所以转换比率为：</li>
     *   <li>metric_to_cgs: 1 m/s² = 100 Gal</li>
     *   <li>cgs_to_metric: 1 Gal = 0.01 m/s²</li>
     * </ul>
     * <P>
     * 参数说明：
     * <ul>
     *   <li>无参数</li>
     * </ul>
     * <P>
     * 返回值说明：
     * <ul>
     *   <li>void: 无返回值</li>
     * </ul>
     * <P>
     * 异常情况：
     * <ul>
     *   <li>无异常情况</li>
     * </ul>
     */
    private static void initializeAccelerationSystemRatios() {
        Map<String, BigDecimal> accelerationRatios = new HashMap<>();
        
        // metric与英制单位间的转换
        // metric锚点单位是M_S2(1)，imperial锚点单位是FT_S2(1)
        // 1 ft/s² = 0.3048 m/s²，所以转换比率为：
        accelerationRatios.put("metric_to_imperial", new BigDecimal("1").divide(new BigDecimal("0.3048"), 10, RoundingMode.HALF_UP));  // 1 m/s² = ~3.28084 ft/s²
        accelerationRatios.put("imperial_to_metric", new BigDecimal("0.3048"));  // 1 ft/s² = 0.3048 m/s²
        
        // metric与CGS单位间的转换
        // metric锚点单位是M_S2(1)，cgs锚点单位是GAL_ACC(1)
        // 1 m/s² = 100 Gal，所以转换比率为：
        accelerationRatios.put("metric_to_cgs", new BigDecimal("100"));  // 1 m/s² = 100 Gal
        accelerationRatios.put("cgs_to_metric", new BigDecimal("0.01"));  // 1 Gal = 0.01 m/s²
        
        SYSTEM_RATIOS.put("acceleration", accelerationRatios);
    }
}