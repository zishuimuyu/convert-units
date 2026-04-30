package com.zishuimuyu.unitconvert.model;

/**
 * 单位枚举类
 * <P>
 * 定义了所有支持的单位类型及其基本信息，包括单位缩写、所属测量类型和系统
 * <P>
 * 功能列表：
 * <ul>
 *   <li>定义所有支持的单位类型</li>
 *   <li>存储单位的基本信息（缩写、测量类型、系统、单复数形式）</li>
 *   <li>提供单位分类管理</li>
 *   <li>支持多种测量类型，如长度、质量、体积、温度等</li>
 *   <li>区分公制和英制系统</li>
 * </ul>
 * <P>
 * 设计原理：
 * <ol>
 *   <li>使用枚举模式确保单位类型的唯一性和类型安全性</li>
 *   <li>每个枚举项包含单位的完整信息</li>
 *   <li>按测量类型对单位进行逻辑分组</li>
 *   <li>提供访问器方法获取单位属性</li>
 * </ol>
 * <P>
 * 优势：
 * <ul>
 *   <li>类型安全：避免使用字符串常量可能出现的错误</li>
 *   <li>易于维护：所有单位集中管理，便于修改和扩展</li>
 *   <li>信息丰富：每个单位包含多种描述信息</li>
 *   <li>性能良好：枚举在运行时是单例，内存效率高</li>
 * </ul>
 * <P>
 * 使用场景：
 * <ol>
 *   <li>在单位转换服务中标识源单位和目标单位</li>
 *   <li>单位信息查询和展示</li>
 *   <li>单位分类和过滤</li>
 *   <li>单位验证和兼容性检查</li>
 * </ol>
 * <P>
 * 
 * @author 紫水木鱼
 * @version 1.0.0
 * @since 2026-04-24
 */
public enum UnitEnum {
    
    // 长度单位
    YM("ym", "length", "metric", "幺米", "幺米"),
    ZM("zm", "length", "metric", "仄米", "仄米"),
    AM("am", "length", "metric", "阿米", "阿米"),
    FM("fm", "length", "metric", "飞米", "飞米"),
    PM("pm", "length", "metric", "皮米", "皮米"),
    NANOMETER("nm", "length", "metric", "纳米", "纳米"),
    UM("μm", "length", "metric", "微米", "微米"),
    ANGSTROM("Å", "length", "metric", "埃格斯特朗", "埃格斯特朗"),
    MM("mm", "length", "metric", "毫米", "毫米"),
    CM("cm", "length", "metric", "厘米", "厘米"),
    DM("dm", "length", "metric", "分米", "分米"),
    M("m", "length", "metric", "米", "米"),
    KM("km", "length", "metric", "千米", "千米"),
    MM_METRIC("Mm", "length", "metric", "兆米", "兆米"),
    GM("Gm", "length", "metric", "吉米", "吉米"),
    MIL("mil", "length", "imperial", "密耳", "密耳"),
    IN("in", "length", "imperial", "英寸", "英寸"),
    FT_US("ft-us", "length", "imperial", "美制英尺", "美制英尺"),
    FT("ft", "length", "imperial", "英尺", "英尺"),
    HAND("hand", "length", "imperial", "手宽", "手宽"),
    YD("yd", "length", "imperial", "码", "码"),
    FATHOM("fathom", "length", "imperial", "英寻", "英寻"),
    FUR("fur", "length", "imperial", "跋", "跋"),
    MI("mi", "length", "imperial", "英里", "英里"),
    NMI("nMi", "length", "imperial", "海里", "海里"),
    AU("AU", "length", "astronomical", "天文单位", "天文单位"),
    LY("ly", "length", "astronomical", "光年", "光年"),
    PARSEC("pc", "length", "astronomical", "秒差距", "秒差距"),
    YM_METRIC("Ym", "length", "metric", "尧米", "尧米"),
    
    // 质量单位
    MCG("mcg", "mass", "metric", "微克", "微克"),
    MG("mg", "mass", "metric", "毫克", "毫克"),
    G("g", "mass", "metric", "克", "克"),
    KG("kg", "mass", "metric", "千克", "千克"),
    TONNE("t", "mass", "metric", "公吨", "公吨"),
    OZ("oz", "mass", "imperial", "盎司", "盎司"),
    LB("lb", "mass", "imperial", "磅", "磅"),
    ST("st", "mass", "imperial", "英石", "英石"),
    GR("gr", "mass", "imperial", "格令", "格令"),
    DR("dr", "mass", "imperial", "打兰", "打兰"),
    USTF("UStf", "mass", "imperial", "美吨", "美吨"),
    LT("lt", "mass", "imperial", "长吨", "长吨"),
    QIAN("qian", "mass", "chinese", "钱", "钱"),
    LIANG("liang", "mass", "chinese", "两", "两"),
    JIN("jin", "mass", "chinese", "斤", "斤"),
    DAN("dan", "mass", "chinese", "担", "担"),
    
    // 体积单位
    MM3("mm3", "volume", "metric", "立方毫米", "立方毫米"),
    CM3("cm3", "volume", "metric", "立方厘米", "立方厘米"),
    DM3("dm3", "volume", "metric", "立方分米", "立方分米"),
    ML("ml", "volume", "metric", "毫升", "毫升"),
    CL("cl", "volume", "metric", "厘升", "厘升"),
    DL("dl", "volume", "metric", "分升", "分升"),
    L("l", "volume", "metric", "升", "升"),
    KL("kl", "volume", "metric", "千升", "千升"),
    ML_MEGA("Ml", "volume", "metric", "兆升", "兆升"),
    ML_GIGA("Gl", "volume", "metric", "吉升", "吉升"),
    M3("m3", "volume", "metric", "立方米", "立方米"),
    KM3("km3", "volume", "metric", "立方千米", "立方千米"),
    KRM("krm", "volume", "metric", "瑞典茶匙", "瑞典茶匙"),
    TSK("tsk", "volume", "metric", "茶匙", "茶匙"),
    MSK("msk", "volume", "metric", "汤匙", "汤匙"),
    KKP("kkp", "volume", "metric", "咖啡杯", "咖啡杯"),
    GLAS("glas", "volume", "metric", "玻璃杯", "玻璃杯"),
    KANNA("kanna", "volume", "metric", "罐", "罐"),
    TSP("tsp", "volume", "imperial", "茶匙", "茶匙"),
    TBS("Tbs", "volume", "imperial", "汤匙", "汤匙"),
    IN3("in3", "volume", "imperial", "立方英寸", "立方英寸"),
    FLOZ("fl-oz", "volume", "imperial", "液盎司", "液盎司"),
    CUP("cup", "volume", "imperial", "杯", "杯"),
    PNT("pnt", "volume", "imperial", "品脱", "品脱"),
    QT("qt", "volume", "imperial", "夸脱", "夸脱"),
    GAL("gal", "volume", "imperial", "加仑", "加仑"),
    FT3("ft3", "volume", "imperial", "立方英尺", "立方英尺"),
    YD3("yd3", "volume", "imperial", "立方码", "立方码"),
    
    // 温度单位
    C("C", "temperature", "metric", "摄氏度", "摄氏度"),
    F("F", "temperature", "imperial", "华氏度", "华氏度"),
    K("K", "temperature", "metric", "开尔文", "开尔文"),
    RA("R", "temperature", "imperial", "兰金温标", "兰金温标"),  // 兰金温标 (Rankine)
    RE("Re", "temperature", "metric", "列氏度", "列氏度"),  // 列氏度 (Réaumur)
    RO("Rø", "temperature", "metric", "罗氏度", "罗氏度"),  // 罗氏度 (Rømer)
    NEWTON_SCALE("N", "temperature", "metric", "牛氏度", "牛氏度"),   // 牛氏度 (Newton Scale)
    WET_BULB("W", "temperature", "metric", "湿球温度", "湿球温度"), // 湿球温度 (Wet Bulb)
    DE("De", "temperature", "metric", "德氏度", "德氏度"),  // 德氏度 (Delisle)
    PLANCK_TEMP("TP", "temperature", "metric", "普朗克温度", "普朗克温度"), // 普朗克温度
    
    // 时间单位
    YS("ys", "time", "metric", "幺秒", "幺秒"),
    ZS("zs", "time", "metric", "仄秒", "仄秒"),
    AS("as", "time", "metric", "阿秒", "阿秒"),
    TP("tp", "time", "metric", "拍秒", "拍秒"),
    FS("fs", "time", "metric", "飞秒", "飞秒"),
    PS("ps", "time", "metric", "皮秒", "皮秒"),
    NS("ns", "time", "metric", "纳秒", "纳秒"),
    US("μs", "time", "metric", "微秒", "微秒"),
    MU("mu", "time", "metric", "微秒", "微秒"),
    MS("ms", "time", "metric", "毫秒", "毫秒"),
    S("s", "time", "metric", "秒", "秒"),
    MIN("min", "time", "metric", "分钟", "分钟"),
    H("h", "time", "metric", "小时", "小时"),
    DAY("day", "time", "metric", "天", "天"),
    WEEK("week", "time", "metric", "周", "周"),
    FORTNIGHT("fortnight", "time", "metric", "十四夜", "十四夜"),
    KE("ke", "time", "metric", "刻", "刻"),
    SHICHEN("shichen", "time", "chinese", "时辰", "时辰"),
    LUNARMONTH("lunarMonth", "time", "chinese", "农历月", "农历月"),
    MONTH("month", "time", "metric", "月", "月"),
    QUARTER("quarter", "time", "metric", "季度", "季度"),
    YEAR("year", "time", "metric", "年", "年"),
    CENTURY("century", "time", "metric", "世纪", "世纪"),
    MILLENNIUM("millennium", "time", "metric", "千年", "千年"),
    MEGAANNUM("megaannum", "time", "metric", "百万年", "百万年"),
    
    // 面积单位
    MM2("mm2", "area", "metric", "平方毫米", "平方毫米"),
    CM2("cm2", "area", "metric", "平方厘米", "平方厘米"),
    M2("m2", "area", "metric", "平方米", "平方米"),
    HA("ha", "area", "metric", "公顷", "公顷"),
    KM2("km2", "area", "metric", "平方千米", "平方千米"),
    IN2("in2", "area", "imperial", "平方英寸", "平方英寸"),
    FT2("ft2", "area", "imperial", "平方英尺", "平方英尺"),
    YD2("yd2", "area", "imperial", "平方码", "平方码"),
    MI2("mi2", "area", "imperial", "平方英里", "平方英里"),
    ACRE("acre", "area", "imperial", "英亩", "英亩"),
    ROD("rod", "area", "imperial", "杆", "杆"),
    
    // 速度单位
    MM_S("mm/s", "speed", "metric", "毫米每秒", "毫米每秒"),
    CM_S("cm/s", "speed", "metric", "厘米每秒", "厘米每秒"),
    M_S("m/s", "speed", "metric", "米每秒", "米每秒"),
    MM_H("mm/h", "speed", "metric", "毫米每小时", "毫米每小时"),
    KM_S("km/s", "speed", "metric", "千米每秒", "千米每秒"),
    KM_H("km/h", "speed", "metric", "千米每小时", "千米每小时"),
    FPS("ft/s", "speed", "imperial", "英尺每秒", "英尺每秒"),
    FPM("ft/min", "speed", "imperial", "英尺每分钟", "英尺每分钟"),
    IN_H("in/h", "speed", "imperial", "英寸每小时", "英寸每小时"),
    MPH("mph", "speed", "imperial", "英里每小时", "英里每小时"),
    KT("kt", "speed", "imperial", "节", "节"),
    
    // 加速度单位
    G_FORCE("g-force", "acceleration", "metric", "重力加速度", "重力加速度"),
    M_S2("m/s2", "acceleration", "metric", "米每二次方秒", "米每二次方秒"),
    G0("g0", "acceleration", "metric", "标准重力", "标准重力"),
    
    // 角度单位
    RAD("rad", "angle", "metric", "弧度", "弧度"),
    DEG("deg", "angle", "metric", "度", "度"),
    GRAD("grad", "angle", "metric", "梯度", "梯度"),
    ARCMIN("arcmin", "angle", "metric", "弧分", "弧分"),
    ARCSEC("arcsec", "angle", "metric", "弧秒", "弧秒"),
    
    // 功率单位
    W("W", "power", "metric", "瓦特", "瓦特"),
    MW("mW", "power", "metric", "毫瓦", "毫瓦"),
    KW("kW", "power", "metric", "千瓦", "千瓦"),
    MW_POWER("MW", "power", "metric", "兆瓦", "兆瓦"),
    GW("GW", "power", "metric", "吉瓦", "吉瓦"),
    METRIC_HP("PS", "power", "metric", "公制马力", "公制马力"),
    HP("hp", "power", "imperial", "马力", "马力"),
    
    // 压力单位
    PA("Pa", "pressure", "metric", "帕斯卡", "帕斯卡"),
    KPA("kPa", "pressure", "metric", "千帕", "千帕"),
    MPA("MPa", "pressure", "metric", "兆帕", "兆帕"),
    HPa("hPa", "pressure", "metric", "百帕", "百帕"),
    GPA("GPa", "pressure", "metric", "吉帕", "吉帕"),
    MBAR("mbar", "pressure", "metric", "毫巴", "毫巴"),
    BAR("bar", "pressure", "metric", "巴", "巴"),
    TORR("torr", "pressure", "metric", "托", "托"),
    MH2O("mH2O", "pressure", "metric", "米水柱", "米水柱"),
    MMHG("mmHg", "pressure", "metric", "毫米汞柱", "毫米汞柱"),
    ATM("atm", "pressure", "metric", "标准大气压", "标准大气压"),            // 101325Pa 科学标准、实验室
    KGF_PER_M2("Kgf/m2", "pressure", "metric", "千克力/平方米", "千克力/平方米"), // 9.80665Pa 工程技术、老旧文献
    LBF_PER_FT2("lbf/ft2", "pressure", "imperial", "磅力/平方英尺", "磅力/平方英尺"), // 47.8803Pa 美国工程标准
    LBF_PER_IN2("lbf/in2", "pressure", "imperial", "磅力/平方英寸", "磅力/平方英寸"), // 6894.76Pa 工业、轮胎压力（等同于psi）
    KSI("ksi", "pressure", "imperial", "千磅力/平方英寸", "千磅力/平方英寸"),       // ≈ 6.895×10⁶ Pa 材料工程、高强度钢
    PSI("psi", "pressure", "imperial", "磅每平方英寸", "磅每平方英寸"),              // 磅力/平方英寸的别名
    
    // 数量单位 (each类型)
    EA("ea", "each", "metric", "每个", "每个"),
    SINGLE("single", "each", "metric", "个", "个"),
    DOZEN("doz", "each", "metric", "打", "打"),
    
    // 数量单位 (pieces类型)
    PCS("pcs", "pieces", "unit", "件", "件"),
    PAIR("pair", "pieces", "unit", "双", "双"),
    SET("set", "pieces", "unit", "套", "套"),
    
    // 电荷单位
    COULOMB("C", "charge", "metric", "库仑", "库仑"),
    MC("mC", "charge", "metric", "毫库仑", "毫库仑"),
    UC("μC", "charge", "metric", "微库仑", "微库仑"),
    NC("nC", "charge", "metric", "纳库仑", "纳库仑"),
    PC("pC", "charge", "metric", "皮库仑", "皮库仑"),
    
    // 电流单位
    A("A", "current", "metric", "安培", "安培"),
    MA("mA", "current", "metric", "毫安", "毫安"),
    UA("μA", "current", "metric", "微安", "微安"),
    KA("kA", "current", "metric", "千安", "千安"),
    MA_CURRENT("MA", "current", "metric", "兆安", "兆安"),

    // 电压单位
    V("V", "voltage", "metric", "伏特", "伏特"),
    MV_VOLTAGE("mV", "voltage", "metric", "毫伏", "毫伏"),
    UV("μV", "voltage", "metric", "微伏", "微伏"),
    KV("kV", "voltage", "metric", "千伏", "千伏"),
    MV_VOLTAGE_MEGA("MV", "voltage", "metric", "兆伏", "兆伏"),

    // 数字存储单位
    BIT("bit", "digital", "binary", "比特", "比特"),
    KBIT("kbit", "digital", "binary", "千比特", "千比特"),
    MBIT("Mbit", "digital", "binary", "兆比特", "兆比特"),
    GBIT("Gbit", "digital", "binary", "吉比特", "吉比特"),
    TBIT("Tbit", "digital", "binary", "太比特", "太比特"),
    BYTE("B", "digital", "binary", "字节", "字节"),
    KBYTE("kB", "digital", "binary", "千字节", "千字节"),
    MBYTE("MB", "digital", "binary", "兆字节", "兆字节"),
    GBYTE("GB", "digital", "binary", "吉字节", "吉字节"),
    TBYTE("TB", "digital", "binary", "太字节", "太字节"),
    KIBYTE("KiB", "digital", "binary", "千位字节", "千位字节"),
    MIBYTE("MiB", "digital", "binary", "兆位字节", "兆位字节"),
    GIBYTE("GiB", "digital", "binary", "吉位字节", "吉位字节"),
    TIBYTE("TiB", "digital", "binary", "太位字节", "太位字节"),

    // 能量单位
    WS("Ws", "energy", "SI", "瓦特秒", "瓦特秒"),
    WM("Wm", "energy", "SI", "瓦特分钟", "瓦特分钟"),
    WH("Wh", "energy", "SI", "瓦特小时", "瓦特小时"),
    MWH("mWh", "energy", "SI", "毫瓦特小时", "毫瓦特小时"),
    KWH("kWh", "energy", "SI", "千瓦时", "千瓦时"),
    MWH_MEGA("MWh", "energy", "SI", "兆瓦时", "兆瓦时"),
    GWH("GWh", "energy", "SI", "吉瓦时", "吉瓦时"),
    J("J", "energy", "SI", "焦耳", "焦耳"),
    KJ("kJ", "energy", "SI", "千焦", "千焦"),
    MJ("MJ", "energy", "SI", "兆焦", "兆焦"),
    GJ("GJ", "energy", "SI", "吉焦", "吉焦"),
    KCAL("kcal", "energy", "nutrition", "千卡", "千卡"),
    CAL("cal", "energy", "nutrition", "卡路里", "卡路里"),
    ERG("erg", "energy", "cgs", "尔格", "尔格"),              // 10⁻⁷J 历史物理文献、微观能量
    FTLBF("ftlbf", "energy", "imperial", "英尺·磅力", "英尺·磅力"), // 1.35582J 美国工程、机械功
    KGFM("Kgfm", "energy", "metric", "克力·米", "克力·米"),     // 9.80665J 工程技术、机械功
    BTU("Btu", "energy", "imperial", "英热单位", "英热单位"),    // 1055.06J 空调制冷、能源计量
    
    // 力单位
    UN("uN", "force", "SI", "微牛顿", "微牛顿"),           // 10⁻⁶N 微观粒子研究、纳米技术、生物分子力测量
    MN_FORCE("mN", "force", "SI", "毫牛顿", "毫牛顿"),      // 10⁻³N 精密仪器、微型机械、生物医学工程
    DYN("dyn", "force", "SI", "达因", "达因"),             // 10⁻⁵N 微观力测量、分子间作用力
    GF("gf", "force", "SI", "克力", "克力"),               // 9.80665×10⁻³N 实验室精密测量、小型机械
    N("N", "force", "SI", "牛顿", "牛顿"),                 // 1N (基本单位) 国际单位制力的基本单位
    KN("kN", "force", "SI", "千牛", "千牛"),               // 10³N (一千牛顿) 结构工程、大型机械
    MN_FORCE_MEGA("MN", "force", "SI", "兆牛顿", "兆牛顿"), // 10⁶N (一百万牛顿) 大型结构工程、桥梁设计、重型机械
    LBF("lbf", "force", "imperial", "磅力", "磅力"),        // 4.44822N 工程力学、航空航天
    KGF("kgf", "force", "SI", "千克力", "千克力"),          // 9.80665N 工程技术、起重设备
    KIP("kip", "force", "imperial", "千磅力", "千磅力"),    // 4448.22N 美国土木工程、建筑结构
    TF("tf", "force", "SI", "吨力", "吨力"),               // 9806.65N 重型机械、起重能力
    STF("STf", "force", "imperial", "短吨力", "短吨力"),    // 8896.44N 美国工程标准、重型机械、建筑结构
    USTF_FORCE("UStf", "force", "imperial", "美吨力", "美吨力"),  // 8896.44N 美国工程标准
    OZF("ozf", "force", "imperial", "盎司力", "盎司力"),    // 0.278014N 轻型机械、精密仪器、美国工程标准

    // 扭矩单位
    NM("Nm", "torque", "metric", "牛顿米", "牛顿米"),
    CNM("cNm", "torque", "metric", "厘牛顿米", "厘牛顿米"),
    DNM("dNm", "torque", "metric", "分牛顿米", "分牛顿米"),
    KGM("kgm", "torque", "metric", "千克米", "千克米"),
    KG_CM("kg-cm", "torque", "metric", "千克厘米", "千克厘米"),
    LBF_FT("lbf-ft", "torque", "imperial", "磅英尺", "磅英尺"),
    LBF_IN("lbf-in", "torque", "imperial", "磅英寸", "磅英寸"),
    OZF_IN("ozf-in", "torque", "imperial", "盎司英寸", "盎司英寸"),

    // 频率单位
    HZ("Hz", "frequency", "metric", "赫兹", "赫兹"),
    KHZ("kHz", "frequency", "metric", "千赫", "千赫"),
    MHZ("MHz", "frequency", "metric", "兆赫", "兆赫"),
    GHZ("GHz", "frequency", "metric", "吉赫", "吉赫"),
    THZ("THz", "frequency", "metric", "太赫", "太赫"),
    RPM("rpm", "frequency", "metric", "转每分钟", "转每分钟"),
    DEG_S("deg/s", "frequency", "metric", "度/秒", "度/秒"),
    RAD_S("rad/s", "frequency", "metric", "弧度/秒", "弧度/秒"),

    // 照度单位
    LX("lx", "illuminance", "metric", "勒克斯", "勒克斯"),
    FOOTCANDLE("fc", "illuminance", "imperial", "英尺烛光", "英尺烛光"),

    // 视在功率单位
    VA("VA", "apparentPower", "SI", "伏安", "伏安"),
    MVA("mVA", "apparentPower", "SI", "毫伏安", "毫伏安"),
    KVA("kVA", "apparentPower", "SI", "千伏安", "千伏安"),
    MVA_POWER("MVA", "apparentPower", "SI", "兆伏安", "兆伏安"),
    GVA("GVA", "apparentPower", "SI", "吉伏安", "吉伏安"),

    // 部分浓度单位
    PPM("ppm", "partsPer", "SI", "百万分率", "百万分率"),
    PPB("ppb", "partsPer", "SI", "十亿分率", "十亿分率"),
    PPT("ppt", "partsPer", "SI", "万亿分率", "万亿分率"),
    PPQ("ppq", "partsPer", "SI", "千万亿分率", "千万亿分率"),

    // 数量单位
    PCS_PIECES("pcs", "pieces", "unit", "件", "件"),
    BK_DOZ("bk-doz", "pieces", "unit", "烘焙 dozen", "烘焙 dozen"),
    CP("cp", "pieces", "unit", "对", "对"),
    DOZ_DOZ("doz-doz", "pieces", "unit", "dozen dozen", "dozen dozen"),
    DOZ("doz", "pieces", "unit", "dozen", "dozen"),
    GR_GR("gr-gr", "pieces", "unit", "gross gross", "gross gross"),
    GROS("gros", "pieces", "unit", "gross", "gross"),
    HALF_DOZEN("half-dozen", "pieces", "unit", "半打", "半打"),
    LONG_HUNDRED("long-hundred", "pieces", "unit", "长百", "长百"),
    REAM("ream", "pieces", "unit", "令", "令"),
    SCORES("scores", "pieces", "unit", "二十", "二十"),
    SM_GR("sm-gr", "pieces", "unit", "small gross", "small gross"),
    TRIO("trio", "pieces", "unit", "三人组", "三人组"),

    // 无功功率单位
    VAR("VAR", "reactivePower", "SI", "乏", "乏"),
    MVAR("mVAR", "reactivePower", "SI", "毫乏", "毫乏"),
    KVAR("kVAR", "reactivePower", "SI", "千乏", "千乏"),
    MVAR_POWER("MVAR", "reactivePower", "SI", "兆乏", "兆乏"),
    GVAR("GVAR", "reactivePower", "SI", "吉乏", "吉乏"),

    // 无功能量单位
    VARH("VARh", "reactiveEnergy", "SI", "乏时", "乏时"),
    MVARH("mVARh", "reactiveEnergy", "SI", "毫乏时", "毫乏时"),
    KVARH("kVARh", "reactiveEnergy", "SI", "千乏时", "千乏时"),
    MVARH_POWER("MVARh", "reactiveEnergy", "SI", "兆乏时", "兆乏时"),
    GVARH("GVARh", "reactiveEnergy", "SI", "吉乏时", "吉乏时"),

    // 质量流量率单位
    KG_PER_S("kg/s", "massFlowRate", "metric", "千克/秒", "千克/秒"),
    KG_PER_MIN("kg/min", "massFlowRate", "metric", "千克/分钟", "千克/分钟"),
    KG_PER_H("kg/h", "massFlowRate", "metric", "千克/小时", "千克/小时"),
    MT_PER_H("mt/h", "massFlowRate", "metric", "公吨/小时", "公吨/小时"),
    LB_PER_S("lb/s", "massFlowRate", "imperial", "磅/秒", "磅/秒"),
    LB_PER_H("lb/h", "massFlowRate", "imperial", "磅/小时", "磅/小时"),

    // 步速单位
    MIN_PER_KM("min/km", "pace", "metric", "分钟/公里", "分钟/公里"),
    S_PER_M("s/m", "pace", "metric", "秒/米", "秒/米"),
    MIN_PER_MI("min/mi", "pace", "imperial", "分钟/英里", "分钟/英里"),
    S_PER_FT("s/ft", "pace", "imperial", "秒/英尺", "秒/英尺"),

    // 体积流量率单位
    MM3_PER_S("mm3/s", "volumeFlowRate", "metric", "立方毫米/秒", "立方毫米/秒"),
    CM3_PER_S("cm3/s", "volumeFlowRate", "metric", "立方厘米/秒", "立方厘米/秒"),
    DM3_PER_S("dm3/s", "volumeFlowRate", "metric", "立方分米/秒", "立方分米/秒"),
    DM3_PER_MIN("dm3/min", "volumeFlowRate", "metric", "立方分米/分钟", "立方分米/分钟"),
    DM3_PER_H("dm3/h", "volumeFlowRate", "metric", "立方分米/小时", "立方分米/小时"),
    DM3_PER_D("dm3/d", "volumeFlowRate", "metric", "立方分米/天", "立方分米/天"),
    DM3_PER_A("dm3/a", "volumeFlowRate", "metric", "立方分米/年", "立方分米/年"),
    ML_PER_S("ml/s", "volumeFlowRate", "metric", "毫升/秒", "毫升/秒"),
    CL_PER_S("cl/s", "volumeFlowRate", "metric", "厘升/秒", "厘升/秒"),
    DL_PER_S("dl/s", "volumeFlowRate", "metric", "分升/秒", "分升/秒"),
    L_PER_S("l/s", "volumeFlowRate", "metric", "升/秒", "升/秒"),
    L_PER_MIN("l/min", "volumeFlowRate", "metric", "升/分钟", "升/分钟"),
    L_PER_H("l/h", "volumeFlowRate", "metric", "升/小时", "升/小时"),
    L_PER_D("l/d", "volumeFlowRate", "metric", "升/天", "升/天"),
    L_PER_A("l/a", "volumeFlowRate", "metric", "升/年", "升/年"),
    KL_PER_S("kl/s", "volumeFlowRate", "metric", "千升/秒", "千升/秒"),
    KL_PER_MIN("kl/min", "volumeFlowRate", "metric", "千升/分钟", "千升/分钟"),
    KL_PER_H("kl/h", "volumeFlowRate", "metric", "千升/小时", "千升/小时"),
    M3_PER_S("m3/s", "volumeFlowRate", "metric", "立方米/秒", "立方米/秒"),
    M3_PER_MIN("m3/min", "volumeFlowRate", "metric", "立方米/分钟", "立方米/分钟"),
    M3_PER_H("m3/h", "volumeFlowRate", "metric", "立方米/小时", "立方米/小时"),
    M3_PER_D("m3/d", "volumeFlowRate", "metric", "立方米/天", "立方米/天"),
    M3_PER_A("m3/a", "volumeFlowRate", "metric", "立方米/年", "立方米/年"),
    KM3_PER_S("km3/s", "volumeFlowRate", "metric", "立方千米/秒", "立方千米/秒"),
    TSP_PER_S("tsp/s", "volumeFlowRate", "imperial", "茶匙/秒", "茶匙/秒"),
    TBS_PER_S("Tbs/s", "volumeFlowRate", "imperial", "汤匙/秒", "汤匙/秒"),
    IN3_PER_S("in3/s", "volumeFlowRate", "imperial", "立方英寸/秒", "立方英寸/秒"),
    IN3_PER_MIN("in3/min", "volumeFlowRate", "imperial", "立方英寸/分钟", "立方英寸/分钟"),
    IN3_PER_H("in3/h", "volumeFlowRate", "imperial", "立方英寸/小时", "立方英寸/小时"),
    FLOZ_PER_S("fl-oz/s", "volumeFlowRate", "imperial", "液盎司/秒", "液盎司/秒"),
    FLOZ_PER_MIN("fl-oz/min", "volumeFlowRate", "imperial", "液盎司/分钟", "液盎司/分钟"),
    FLOZ_PER_H("fl-oz/h", "volumeFlowRate", "imperial", "液盎司/小时", "液盎司/小时"),
    CUP_PER_S("cup/s", "volumeFlowRate", "imperial", "杯/秒", "杯/秒"),
    PNT_PER_S("pnt/s", "volumeFlowRate", "imperial", "品脱/秒", "品脱/秒"),
    PNT_PER_MIN("pnt/min", "volumeFlowRate", "imperial", "品脱/分钟", "品脱/分钟"),
    PNT_PER_H("pnt/h", "volumeFlowRate", "imperial", "品脱/小时", "品脱/小时"),
    QT_PER_S("qt/s", "volumeFlowRate", "imperial", "夸脱/秒", "夸脱/秒"),
    GAL_PER_S("gal/s", "volumeFlowRate", "imperial", "加仑/秒", "加仑/秒"),
    GAL_PER_MIN("gal/min", "volumeFlowRate", "imperial", "加仑/分钟", "加仑/分钟"),
    GAL_PER_H("gal/h", "volumeFlowRate", "imperial", "加仑/小时", "加仑/小时"),
    FT3_PER_S("ft3/s", "volumeFlowRate", "imperial", "立方英尺/秒", "立方英尺/秒"),
    FT3_PER_MIN("ft3/min", "volumeFlowRate", "imperial", "立方英尺/分钟", "立方英尺/分钟"),
    FT3_PER_H("ft3/h", "volumeFlowRate", "imperial", "立方英尺/小时", "立方英尺/小时"),
    YD3_PER_S("yd3/s", "volumeFlowRate", "imperial", "立方码/秒", "立方码/秒"),
    YD3_PER_MIN("yd3/min", "volumeFlowRate", "imperial", "立方码/分钟", "立方码/分钟"),
    YD3_PER_H("yd3/h", "volumeFlowRate", "imperial", "立方码/小时", "立方码/小时"),
    ;
    
    /**
     * 单位缩写
     * <P>
     * 详细描述字段的用途、取值范围或特殊含义
     * <P>
     * 用途：存储单位的简短表示形式，通常为国际通用的标准缩写
     * <P>
     * 取值范围：字符串，如"m"、"kg"、"s"等
     * <P>
     * 特殊含义：作为单位的主要标识符，在转换过程中用于识别和显示单位
     */
    private final String abbr;
    
    /**
     * 测量类型
     * <P>
     * 详细描述字段的用途、取值范围或特殊含义
     * <P>
     * 用途：标识单位所属的物理量类型
     * <P>
     * 取值范围：字符串，如"length"、"mass"、"time"、"temperature"等
     * <P>
     * 特殊含义：用于单位兼容性检查，只有相同测量类型的单位才能相互转换
     */
    private final String measure;
    
    /**
     * 单位系统
     * <P>
     * 详细描述字段的用途、取值范围或特殊含义
     * <P>
     * 用途：标识单位所属的度量系统
     * <P>
     * 取值范围：字符串，如"metric"、"imperial"、"binary"等
     * <P>
     * 特殊含义：用于跨系统转换计算，区分公制、英制等不同度量系统
     */
    private final String system;
    
    /**
     * 单数形式
     * <P>
     * 详细描述字段的用途、取值范围或特殊含义
     * <P>
     * 用途：存储单位的单数表达形式
     * <P>
     * 取值范围：字符串，如"米"、"kilogram"等
     * <P>
     * 特殊含义：用于结果展示和用户界面显示，提供更友好的单位名称
     */
    private final String singular;
    
    /**
     * 复数形式
     * <P>
     * 详细描述字段的用途、取值范围或特殊含义
     * <P>
     * 用途：存储单位的复数表达形式
     * <P>
     * 取值范围：字符串，如"米"、"kilograms"等
     * <P>
     * 特殊含义：用于结果展示和用户界面显示，提供更友好的单位名称
     */
    private final String plural;
    
    /**
     * 构造函数，初始化单位枚举项
     * <P>
     * 详细描述方法的功能、算法逻辑或业务流程
     * <P>
     * 处理逻辑：
     * <ol>
     *   <li>接收单位的各项属性参数</li>
     *   <li>将参数分别赋值给对应的私有字段</li>
     *   <li>完成枚举项的初始化</li>
     * </ol>
     * <P>
     * 参数说明：
     * <ul>
     *   <li>abbr: 单位缩写，用于标识单位的简短形式</li>
     *   <li>measure: 测量类型，标识单位所属的物理量类型</li>
     *   <li>system: 单位系统，标识单位所属的度量系统</li>
     *   <li>singular: 单数形式，单位的单数表达形式</li>
     *   <li>plural: 复数形式，单位的复数表达形式</li>
     * </ul>
     * <P>
     * 返回值说明：
     * <ul>
     *   <li>void: 无返回值，仅初始化对象</li>
     * </ul>
     * <P>
     * 异常情况：
     * <ul>
     *   <li>无异常情况</li>
     * </ul>
     * 
     * @param abbr 单位缩写
     * @param measure 测量类型
     * @param system 单位系统
     * @param singular 单数形式
     * @param plural 复数形式
     */
    UnitEnum(String abbr, String measure, String system, String singular, String plural) {
        this.abbr = abbr;
        this.measure = measure;
        this.system = system;
        this.singular = singular;
        this.plural = plural;
    }
    
    /**
     * 获取单位缩写
     * <P>
     * 详细描述方法的功能、算法逻辑或业务流程
     * <P>
     * 处理逻辑：
     * <ol>
     *   <li>直接返回已初始化的单位缩写字段</li>
     * </ol>
     * <P>
     * 参数说明：
     * <ul>
     *   <li>无参数</li>
     * </ul>
     * <P>
     * 返回值说明：
     * <ul>
     *   <li>返回单位的缩写字符串</li>
     * </ul>
     * <P>
     * 异常情况：
     * <ul>
     *   <li>无异常情况</li>
     * </ul>
     * 
     * @return 单位缩写
     */
    public String getAbbr() {
        return abbr;
    }
    
    /**
     * 获取测量类型
     * <P>
     * 详细描述方法的功能、算法逻辑或业务流程
     * <P>
     * 处理逻辑：
     * <ol>
     *   <li>直接返回已初始化的测量类型字段</li>
     * </ol>
     * <P>
     * 参数说明：
     * <ul>
     *   <li>无参数</li>
     * </ul>
     * <P>
     * 返回值说明：
     * <ul>
     *   <li>返回单位的测量类型字符串</li>
     * </ul>
     * <P>
     * 异常情况：
     * <ul>
     *   <li>无异常情况</li>
     * </ul>
     * 
     * @return 测量类型
     */
    public String getMeasure() {
        return measure;
    }
    
    /**
     * 获取单位系统
     * <P>
     * 详细描述方法的功能、算法逻辑或业务流程
     * <P>
     * 处理逻辑：
     * <ol>
     *   <li>直接返回已初始化的单位系统字段</li>
     * </ol>
     * <P>
     * 参数说明：
     * <ul>
     *   <li>无参数</li>
     * </ul>
     * <P>
     * 返回值说明：
     * <ul>
     *   <li>返回单位所属的系统字符串</li>
     * </ul>
     * <P>
     * 异常情况：
     * <ul>
     *   <li>无异常情况</li>
     * </ul>
     * 
     * @return 单位系统
     */
    public String getSystem() {
        return system;
    }
    
    /**
     * 获取单数形式
     * <P>
     * 详细描述方法的功能、算法逻辑或业务流程
     * <P>
     * 处理逻辑：
     * <ol>
     *   <li>直接返回已初始化的单数形式字段</li>
     * </ol>
     * <P>
     * 参数说明：
     * <ul>
     *   <li>无参数</li>
     * </ul>
     * <P>
     * 返回值说明：
     * <ul>
     *   <li>返回单位的单数形式字符串</li>
     * </ul>
     * <P>
     * 异常情况：
     * <ul>
     *   <li>无异常情况</li>
     * </ul>
     * 
     * @return 单数形式
     */
    public String getSingular() {
        return singular;
    }
    
    /**
     * 获取复数形式
     * <P>
     * 详细描述方法的功能、算法逻辑或业务流程
     * <P>
     * 处理逻辑：
     * <ol>
     *   <li>直接返回已初始化的复数形式字段</li>
     * </ol>
     * <P>
     * 参数说明：
     * <ul>
     *   <li>无参数</li>
     * </ul>
     * <P>
     * 返回值说明：
     * <ul>
     *   <li>返回单位的复数形式字符串</li>
     * </ul>
     * <P>
     * 异常情况：
     * <ul>
     *   <li>无异常情况</li>
     * </ul>
     * 
     * @return 复数形式
     */
    public String getPlural() {
        return plural;
    }
    
    /**
     * 根据缩写查找单位枚举
     * 
     * @param abbr 单位缩写
     * @return 匹配的单位枚举，如果未找到则返回null
     */
    public static UnitEnum getByAbbr(String abbr) {
        for (UnitEnum unit : values()) {
            if (unit.abbr.equalsIgnoreCase(abbr)) {
                return unit;
            }
        }
        return null;
    }
}