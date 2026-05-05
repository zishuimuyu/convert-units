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
    
    // 1 长度单位
    LENGTH_YM("ym", "length", "metric", "幺米", "幺米"),
    LENGTH_ZM("zm", "length", "metric", "仄米", "仄米"),
    LENGTH_AM("am", "length", "metric", "阿米", "阿米"),
    LENGTH_FM("fm", "length", "metric", "飞米", "飞米"),
    LENGTH_PM("pm", "length", "metric", "皮米", "皮米"),
    LENGTH_NANOMETER("nm", "length", "metric", "纳米", "纳米"),
    LENGTH_UM("μm", "length", "metric", "微米", "微米"),
    LENGTH_ANGSTROM("Å", "length", "metric", "埃格斯特朗", "埃格斯特朗"),
    LENGTH_MM("mm", "length", "metric", "毫米", "毫米"),
    LENGTH_CM("cm", "length", "metric", "厘米", "厘米"),
    LENGTH_DM("dm", "length", "metric", "分米", "分米"),
    LENGTH_M("m", "length", "metric", "米", "米"),
    LENGTH_KM("km", "length", "metric", "千米", "千米"),
    LENGTH_MM_METRIC("Mm", "length", "metric", "兆米", "兆米"),
    LENGTH_GM("Gm", "length", "metric", "吉米", "吉米"),
    LENGTH_MIL("mil", "length", "imperial", "密耳", "密耳"),
    LENGTH_IN("in", "length", "imperial", "英寸", "英寸"),
    LENGTH_FT_US("ft-us", "length", "imperial", "美制英尺", "美制英尺"),
    LENGTH_FT("ft", "length", "imperial", "英尺", "英尺"),
    LENGTH_HAND("hand", "length", "imperial", "手宽", "手宽"),
    LENGTH_YD("yd", "length", "imperial", "码", "码"),
    LENGTH_FATHOM("fathom", "length", "imperial", "英寻", "英寻"),
    LENGTH_FUR("fur", "length", "imperial", "跋", "跋"),
    LENGTH_MI("mi", "length", "imperial", "英里", "英里"),
    LENGTH_NMI("nMi", "length", "imperial", "海里", "海里"),
    LENGTH_AU("AU", "length", "astronomical", "天文单位", "天文单位"),
    LENGTH_LY("ly", "length", "astronomical", "光年", "光年"),
    LENGTH_PARSEC("pc", "length", "astronomical", "秒差距", "秒差距"),
    LENGTH_YM_METRIC("Ym", "length", "metric", "尧米", "尧米"),
    
    // 2 质量单位
    MASS_MCG("mcg", "mass", "metric", "微克", "微克"),
    MASS_MG("mg", "mass", "metric", "毫克", "毫克"),
    MASS_G("g", "mass", "metric", "克", "克"),
    MASS_KG("kg", "mass", "metric", "千克", "千克"),
    MASS_TONNE("t", "mass", "metric", "公吨", "公吨"),
    MASS_OZ("oz", "mass", "imperial", "盎司", "盎司"),
    MASS_LB("lb", "mass", "imperial", "磅", "磅"),
    MASS_ST("st", "mass", "imperial", "英石", "英石"),
    MASS_GR("gr", "mass", "imperial", "格令", "格令"),
    MASS_DR("dr", "mass", "imperial", "打兰", "打兰"),
    MASS_USTF("UStf", "mass", "imperial", "美吨", "美吨"),
    MASS_LT("lt", "mass", "imperial", "长吨", "长吨"),
    MASS_QIAN("qian", "mass", "chinese", "钱", "钱"),
    MASS_LIANG("liang", "mass", "chinese", "两", "两"),
    MASS_JIN("jin", "mass", "chinese", "斤", "斤"),
    MASS_DAN("dan", "mass", "chinese", "担", "担"),
    
    // 3、体积单位
    VOLUME_MM3("mm3", "volume", "metric", "立方毫米", "立方毫米"),
    VOLUME_CM3("cm3", "volume", "metric", "立方厘米", "立方厘米"),
    VOLUME_DM3("dm3", "volume", "metric", "立方分米", "立方分米"),
    VOLUME_ML("ml", "volume", "metric", "毫升", "毫升"),
    VOLUME_CL("cl", "volume", "metric", "厘升", "厘升"),
    VOLUME_DL("dl", "volume", "metric", "分升", "分升"),
    VOLUME_L("l", "volume", "metric", "升", "升"),
    VOLUME_KL("kl", "volume", "metric", "千升", "千升"),
    VOLUME_ML_MEGA("Ml", "volume", "metric", "兆升", "兆升"),
    VOLUME_ML_GIGA("Gl", "volume", "metric", "吉升", "吉升"),
    VOLUME_M3("m3", "volume", "metric", "立方米", "立方米"),
    VOLUME_KM3("km3", "volume", "metric", "立方千米", "立方千米"),
    VOLUME_KRM("krm", "volume", "metric", "瑞典茶匙", "瑞典茶匙"),
    VOLUME_TSK("tsk", "volume", "metric", "茶匙", "茶匙"),
    VOLUME_MSK("msk", "volume", "metric", "汤匙", "汤匙"),
    VOLUME_KKP("kkp", "volume", "metric", "咖啡杯", "咖啡杯"),
    VOLUME_GLAS("glas", "volume", "metric", "玻璃杯", "玻璃杯"),
    VOLUME_KANNA("kanna", "volume", "metric", "罐", "罐"),
    VOLUME_TSP("tsp", "volume", "imperial", "茶匙", "茶匙"),
    VOLUME_TBS("Tbs", "volume", "imperial", "汤匙", "汤匙"),
    VOLUME_IN3("in3", "volume", "imperial", "立方英寸", "立方英寸"),
    VOLUME_FLOZ("fl-oz", "volume", "imperial", "液盎司", "液盎司"),
    VOLUME_CUP("cup", "volume", "imperial", "杯", "杯"),
    VOLUME_PNT("pnt", "volume", "imperial", "品脱", "品脱"),
    VOLUME_QT("qt", "volume", "imperial", "夸脱", "夸脱"),
    VOLUME_GAL("gal", "volume", "imperial", "加仑", "加仑"),
    VOLUME_FT3("ft3", "volume", "imperial", "立方英尺", "立方英尺"),
    VOLUME_YD3("yd3", "volume", "imperial", "立方码", "立方码"),
    VOLUME_BBL("bbl", "volume", "imperial", "石油桶", "石油桶"),
    VOLUME_CU("撮", "volume", "chinese", "市撮", "市撮"),
    VOLUME_SHO("勺", "volume", "chinese", "市勺", "市勺"),
    VOLUME_HE("合", "volume", "chinese", "市合", "市合"),
    VOLUME_TO("斗", "volume", "chinese", "市斗", "市斗"),
    VOLUME_SEKI("石", "volume", "chinese", "市石", "市石"),
    
    // 4、温度单位
    TEMPERATURE_C("°C", "temperature", "metric", "摄氏度", "摄氏度"),
    TEMPERATURE_F("F", "temperature", "imperial", "华氏度", "华氏度"),
    TEMPERATURE_K("K", "temperature", "metric", "开尔文", "开尔文"),
    TEMPERATURE_RA("R", "temperature", "imperial", "兰金温标", "兰金温标"),
    TEMPERATURE_RE("Re", "temperature", "metric", "列氏度", "列氏度"),
    TEMPERATURE_RO("Rø", "temperature", "metric", "罗氏度", "罗氏度"),
    TEMPERATURE_NEWTON_SCALE("N", "temperature", "metric", "牛氏度", "牛氏度"),
    TEMPERATURE_WET_BULB("W", "temperature", "metric", "湿球温度", "湿球温度"),
    TEMPERATURE_DE("De", "temperature", "metric", "德氏度", "德氏度"),
    TEMPERATURE_PLANCK_TEMP("TP", "temperature", "metric", "普朗克温度", "普朗克温度"),
    
    // 5、时间单位
    TIME_YS("ys", "time", "metric", "幺秒", "幺秒"),
    TIME_ZS("zs", "time", "metric", "仄秒", "仄秒"),
    TIME_AS("as", "time", "metric", "阿秒", "阿秒"),
    TIME_TP("tp", "time", "metric", "拍秒", "拍秒"),
    TIME_FS("fs", "time", "metric", "飞秒", "飞秒"),
    TIME_PS("ps", "time", "metric", "皮秒", "皮秒"),
    TIME_NS("ns", "time", "metric", "纳秒", "纳秒"),
    TIME_US("μs", "time", "metric", "微秒", "微秒"),
    TIME_MU("mu", "time", "metric", "微秒", "微秒"),
    TIME_MS("ms", "time", "metric", "毫秒", "毫秒"),
    TIME_S("s", "time", "metric", "秒", "秒"),
    TIME_MIN("min", "time", "metric", "分钟", "分钟"),
    TIME_H("h", "time", "metric", "小时", "小时"),
    TIME_DAY("day", "time", "metric", "天", "天"),
    TIME_WEEK("week", "time", "metric", "周", "周"),
    TIME_FORTNIGHT("fortnight", "time", "metric", "十四夜", "十四夜"),
    TIME_KE("ke", "time", "metric", "刻", "刻"),
    TIME_SHICHEN("shichen", "time", "chinese", "时辰", "时辰"),
    TIME_LUNARMONTH("lunarMonth", "time", "chinese", "农历月", "农历月"),
    TIME_MONTH("month", "time", "metric", "月", "月"),
    TIME_QUARTER("quarter", "time", "metric", "季度", "季度"),
    TIME_YEAR("year", "time", "metric", "年", "年"),
    TIME_CENTURY("century", "time", "metric", "世纪", "世纪"),
    TIME_MILLENNIUM("millennium", "time", "metric", "千年", "千年"),
    TIME_MEGAANNUM("megaannum", "time", "metric", "百万年", "百万年"),
    
    // 6、面积单位
    AREA_MM2("mm2", "area", "metric", "平方毫米", "平方毫米"),
    AREA_CM2("cm2", "area", "metric", "平方厘米", "平方厘米"),
    AREA_M2("m2", "area", "metric", "平方米", "平方米"),
    AREA_HA("ha", "area", "metric", "公顷", "公顷"),
    AREA_KM2("km2", "area", "metric", "平方千米", "平方千米"),
    AREA_IN2("in2", "area", "imperial", "平方英寸", "平方英寸"),
    AREA_FT2("ft2", "area", "imperial", "平方英尺", "平方英尺"),
    AREA_YD2("yd2", "area", "imperial", "平方码", "平方码"),
    AREA_MI2("mi2", "area", "imperial", "平方英里", "平方英里"),
    AREA_ACRE("acre", "area", "imperial", "英亩", "英亩"),
    AREA_ROD("rod", "area", "imperial", "杆", "杆"),
    
    // 7、速度单位
    SPEED_MM_S("mm/s", "speed", "metric", "毫米每秒", "毫米每秒"),
    SPEED_CM_S("cm/s", "speed", "metric", "厘米每秒", "厘米每秒"),
    SPEED_M_S("m/s", "speed", "metric", "米每秒", "米每秒"),
    SPEED_MM_H("mm/h", "speed", "metric", "毫米每小时", "毫米每小时"),
    SPEED_KM_S("km/s", "speed", "metric", "千米每秒", "千米每秒"),
    SPEED_KM_H("km/h", "speed", "metric", "千米每小时", "千米每小时"),
    SPEED_FPS("ft/s", "speed", "imperial", "英尺每秒", "英尺每秒"),
    SPEED_FPM("ft/min", "speed", "imperial", "英尺每分钟", "英尺每分钟"),
    SPEED_IN_H("in/h", "speed", "imperial", "英寸每小时", "英寸每小时"),
    SPEED_MPH("mph", "speed", "imperial", "英里每小时", "英里每小时"),
    SPEED_KT("kt", "speed", "imperial", "节", "节"),
    SPEED_DM_S("dm/s", "speed", "metric", "分米每秒", "分米每秒"),
    SPEED_DAM_S("dam/s", "speed", "metric", "十米每秒", "十米每秒"),
    SPEED_HM_S("hm/s", "speed", "metric", "百米每秒", "百米每秒"),
    SPEED_KM_MIN("km/min", "speed", "metric", "千米每分", "千米每分"),
    SPEED_IN_S("in/s", "speed", "imperial", "英寸每秒", "英寸每秒"),
    SPEED_YD_S("yd/s", "speed", "imperial", "码每秒", "码每秒"),
    SPEED_MI_S("mi/s", "speed", "imperial", "英里每秒", "英里每秒"),
    SPEED_MI_MIN("mi/min", "speed", "imperial", "英里每分", "英里每分"),
    SPEED_NMI_H("nmi/h", "speed", "imperial", "海里每时", "海里每时"),
    SPEED_MACH("Ma", "speed", "metric", "马赫", "马赫"),
    SPEED_LIGHT_SPEED("c", "speed", "metric", "光速", "光速"),
    SPEED_M_MIN("m/min", "speed", "metric", "米每分", "米每分"),
    SPEED_CM_MIN("cm/min", "speed", "metric", "厘米每分", "厘米每分"),
    SPEED_MM_MIN("mm/min", "speed", "metric", "毫米每分", "毫米每分"),
    
    // 8、加速度单位
    ACCELERATION_G_FORCE("g-force", "acceleration", "metric", "重力加速度", "重力加速度"),
    ACCELERATION_M_S2("m/s2", "acceleration", "metric", "米每二次方秒", "米每二次方秒"),
    ACCELERATION_G0("g0", "acceleration", "metric", "标准重力", "标准重力"),
    ACCELERATION_CM_S2("cm/s²", "acceleration", "metric", "厘米/秒²", "厘米/秒²"),
    ACCELERATION_MM_S2("mm/s²", "acceleration", "metric", "毫米/秒²", "毫米/秒²"),
    ACCELERATION_IN_S2("in/s²", "acceleration", "imperial", "英寸/秒²", "英寸/秒²"),
    ACCELERATION_FT_S2("ft/s²", "acceleration", "imperial", "英尺/秒²", "英尺/秒²"),
    ACCELERATION_GAL_ACC("Gal", "acceleration", "cgs", "伽", "伽"),
    
    // 9、角度单位
    ANGLE_RAD("rad", "angle", "metric", "弧度", "弧度"),
    ANGLE_DEG("deg", "angle", "metric", "度", "度"),
    ANGLE_GRAD("grad", "angle", "metric", "梯度", "梯度"),
    ANGLE_ARCMIN("arcmin", "angle", "metric", "弧分", "弧分"),
    ANGLE_ARCSEC("arcsec", "angle", "metric", "弧秒", "弧秒"),
    
    // 10、功率单位
    POWER_W("W", "power", "metric", "瓦特", "瓦特"),
    POWER_MW("mW", "power", "metric", "毫瓦", "毫瓦"),
    POWER_KW("kW", "power", "metric", "千瓦", "千瓦"),
    POWER_MW_POWER("MW", "power", "metric", "兆瓦", "兆瓦"),
    POWER_GW("GW", "power", "metric", "吉瓦", "吉瓦"),
    POWER_METRIC_HP("PS", "power", "metric", "公制马力", "公制马力"),
    POWER_HP("hp", "power", "imperial", "马力", "马力"),
    POWER_PW("pW", "power", "metric", "皮瓦", "皮瓦"),
    POWER_NW("nW", "power", "metric", "纳瓦", "纳瓦"),
    POWER_UW("uW", "power", "metric", "微瓦", "微瓦"),
    POWER_BTU_PER_S("Btu/s", "power", "imperial", "英热单位/秒", "英热单位/秒"),
    POWER_CAL_PER_S("cal/s", "power", "metric", "卡路里/秒", "卡路里/秒"),
    POWER_KCAL_PER_H("kcal/h", "power", "metric", "千卡/小时", "千卡/小时"),
    POWER_BTU_PER_H("Btu/h", "power", "imperial", "英热单位/小时", "英热单位/小时"),
    POWER_KGF_M_PER_S("kgf·m/s", "power", "metric", "公斤力·米/秒", "公斤力·米/秒"),
    POWER_FTLBF_PER_S("ft·lbf/s", "power", "imperial", "英尺·磅力/秒", "英尺·磅力/秒"),
    POWER_FTLBF_PER_MIN("ft·lbf/min", "power", "imperial", "英尺·磅力/分钟", "英尺·磅力/分钟"),
    POWER_ERG_PER_S("erg/s", "power", "cgs", "尔格/秒", "尔格/秒"),
    POWER_TW("TW", "power", "metric", "太瓦", "太瓦"),
    
    // 11、压强单位
    PRESSURE_PA("Pa", "pressure", "metric", "帕斯卡", "帕斯卡"),
    PRESSURE_KPA("kPa", "pressure", "metric", "千帕", "千帕"),
    PRESSURE_MPA("MPa", "pressure", "metric", "兆帕", "兆帕"),
    PRESSURE_HPA("hPa", "pressure", "metric", "百帕", "百帕"),
    PRESSURE_GPA("GPa", "pressure", "metric", "吉帕", "吉帕"),
    PRESSURE_MBAR("mbar", "pressure", "metric", "毫巴", "毫巴"),
    PRESSURE_BAR("bar", "pressure", "metric", "巴", "巴"),
    PRESSURE_TORR("torr", "pressure", "metric", "托", "托"),
    PRESSURE_MH2O("mH2O", "pressure", "metric", "米水柱", "米水柱"),
    PRESSURE_MMHG("mmHg", "pressure", "metric", "毫米汞柱", "毫米汞柱"),
    PRESSURE_ATM("atm", "pressure", "metric", "标准大气压", "标准大气压"),
    PRESSURE_KGF_PER_M2("Kgf/m2", "pressure", "metric", "千克力/平方米", "千克力/平方米"),
    PRESSURE_LBF_PER_FT2("lbf/ft2", "pressure", "imperial", "磅力/平方英尺", "磅力/平方英尺"),
    PRESSURE_LBF_PER_IN2("lbf/in2", "pressure", "imperial", "磅力/平方英寸", "磅力/平方英寸"),
    PRESSURE_KSI("ksi", "pressure", "imperial", "千磅力/平方英寸", "千磅力/平方英寸"),
    PRESSURE_PSI("psi", "pressure", "imperial", "磅每平方英寸", "磅每平方英寸"),
    
    // 12、数量单位 (pieces类型)
    PIECES_PCS("pcs", "pieces", "unit", "件", "件"),
    PIECES_EA("ea", "each", "metric", "每个", "每个"),
    PIECES_SINGLE("single", "each", "metric", "个", "个"),
    PIECES_DOZEN("doz", "each", "metric", "打", "打"),
    PIECES_PAIR("pair", "pieces", "unit", "双", "双"),
    PIECES_SET("set", "pieces", "unit", "套", "套"),
    PIECES_PCS_PIECES("pcs", "pieces", "unit", "件", "件"),
    PIECES_BK_DOZ("bk-doz", "pieces", "unit", "烘焙 dozen", "烘焙 dozen"),
    PIECES_CP("cp", "pieces", "unit", "对", "对"),
    PIECES_DOZ_DOZ("doz-doz", "pieces", "unit", "dozen dozen", "dozen dozen"),
    PIECES_DOZ("doz", "pieces", "unit", "dozen", "dozen"),
    PIECES_GR_GR("gr-gr", "pieces", "unit", "gross gross", "gross gross"),
    PIECES_GROS("gros", "pieces", "unit", "gross", "gross"),
    PIECES_HALF_DOZEN("half-dozen", "pieces", "unit", "半打", "半打"),
    PIECES_LONG_HUNDRED("long-hundred", "pieces", "unit", "长百", "长百"),
    PIECES_REAM("ream", "pieces", "unit", "令", "令"),
    PIECES_SCORES("scores", "pieces", "unit", "二十", "二十"),
    PIECES_SM_GR("sm-gr", "pieces", "unit", "small gross", "small gross"),
    PIECES_TRIO("trio", "pieces", "unit", "三人组", "三人组"),

    // 13、电荷单位,
    CHARGE_COULOMB("C", "charge", "metric", "库仑", "库仑"),
    CHARGE_MC("mC", "charge", "metric", "毫库仑", "毫库仑"),
    CHARGE_UC("μC", "charge", "metric", "微库仑", "微库仑"),
    CHARGE_NC("nC", "charge", "metric", "纳库仑", "纳库仑"),
    CHARGE_PC("pC", "charge", "metric", "皮库仑", "皮库仑"),

    // 14、电流单位
    CURRENT_A("A", "current", "metric", "安培", "安培"),
    CURRENT_MA("mA", "current", "metric", "毫安", "毫安"),
    CURRENT_UA("μA", "current", "metric", "微安", "微安"),
    CURRENT_KA("kA", "current", "metric", "千安", "千安"),
    CURRENT_MA_CURRENT("MA", "current", "metric", "兆安", "兆安"),

    // 15、电压单位
    VOLTAGE_V("V", "voltage", "metric", "伏特", "伏特"),
    VOLTAGE_MV_VOLTAGE("mV", "voltage", "metric", "毫伏", "毫伏"),
    VOLTAGE_UV("μV", "voltage", "metric", "微伏", "微伏"),
    VOLTAGE_KV("kV", "voltage", "metric", "千伏", "千伏"),
    VOLTAGE_MV_VOLTAGE_MEGA("MV", "voltage", "metric", "兆伏", "兆伏"),

    // 16、数字存储单位
    DIGITAL_BIT("bit", "digital", "binary", "比特", "比特"),
    DIGITAL_KBIT("kbit", "digital", "binary", "千比特", "千比特"),
    DIGITAL_MBIT("Mbit", "digital", "binary", "兆比特", "兆比特"),
    DIGITAL_GBIT("Gbit", "digital", "binary", "吉比特", "吉比特"),
    DIGITAL_TBIT("Tbit", "digital", "binary", "太比特", "太比特"),
    DIGITAL_BYTE("B", "digital", "binary", "字节", "字节"),
    DIGITAL_KBYTE("kB", "digital", "binary", "千字节", "千字节"),
    DIGITAL_MBYTE("MB", "digital", "binary", "兆字节", "兆字节"),
    DIGITAL_GBYTE("GB", "digital", "binary", "吉字节", "吉字节"),
    DIGITAL_TBYTE("TB", "digital", "binary", "太字节", "太字节"),
    DIGITAL_KIBYTE("KiB", "digital", "binary", "千位字节", "千位字节"),
    DIGITAL_MIBYTE("MiB", "digital", "binary", "兆位字节", "兆位字节"),
    DIGITAL_GIBYTE("GiB", "digital", "binary", "吉位字节", "吉位字节"),
    DIGITAL_TIBYTE("TiB", "digital", "binary", "太位字节", "太位字节"),

    // 17、能量单位
    ENERGY_WS("Ws", "energy", "SI", "瓦特秒", "瓦特秒"),
    ENERGY_WM("Wm", "energy", "SI", "瓦特分钟", "瓦特分钟"),
    ENERGY_WH("Wh", "energy", "SI", "瓦特小时", "瓦特小时"),
    ENERGY_MWH("mWh", "energy", "SI", "毫瓦特小时", "毫瓦特小时"),
    ENERGY_KWH("kWh", "energy", "SI", "千瓦时", "千瓦时"),
    ENERGY_MWH_MEGA("MWh", "energy", "SI", "兆瓦时", "兆瓦时"),
    ENERGY_GWH("GWh", "energy", "SI", "吉瓦时", "吉瓦时"),
    ENERGY_J("J", "energy", "SI", "焦耳", "焦耳"),
    ENERGY_KJ("kJ", "energy", "SI", "千焦", "千焦"),
    ENERGY_MJ("MJ", "energy", "SI", "兆焦", "兆焦"),
    ENERGY_GJ("GJ", "energy", "SI", "吉焦", "吉焦"),
    ENERGY_KCAL("kcal", "energy", "nutrition", "千卡", "千卡"),
    ENERGY_CAL("cal", "energy", "nutrition", "卡路里", "卡路里"),
    ENERGY_ERG("erg", "energy", "cgs", "尔格", "尔格"),
    ENERGY_FTLBF("ftlbf", "energy", "imperial", "英尺·磅力", "英尺·磅力"),
    ENERGY_KGFM("Kgfm", "energy", "metric", "克力·米", "克力·米"),
    ENERGY_BTU("Btu", "energy", "imperial", "英热单位", "英热单位"),
    
    // 18、力单位
    FORCE_UN("uN", "force", "SI", "微牛顿", "微牛顿"),
    FORCE_MN("mN", "force", "SI", "毫牛顿", "毫牛顿"),
    FORCE_DYN("dyn", "force", "SI", "达因", "达因"),
    FORCE_GF("gf", "force", "SI", "克力", "克力"),
    FORCE_N("N", "force", "SI", "牛顿", "牛顿"),
    FORCE_KN("kN", "force", "SI", "千牛", "千牛"),
    FORCE_MN_MEGA("MN", "force", "SI", "兆牛顿", "兆牛顿"),
    FORCE_LBF("lbf", "force", "imperial", "磅力", "磅力"),
    FORCE_KGF("kgf", "force", "SI", "千克力", "千克力"),
    FORCE_KIP("kip", "force", "imperial", "千磅力", "千磅力"),
    FORCE_TF("tf", "force", "SI", "吨力", "吨力"),
    FORCE_STF("STf", "force", "imperial", "短吨力", "短吨力"),
    FORCE_USTF("UStf", "force", "imperial", "美吨力", "美吨力"),
    FORCE_OZF("ozf", "force", "imperial", "盎司力", "盎司力"),

    // 19、扭矩单位
    TORQUE_NM("Nm", "torque", "metric", "牛顿米", "牛顿米"),
    TORQUE_CNM("cNm", "torque", "metric", "厘牛顿米", "厘牛顿米"),
    TORQUE_DNM("dNm", "torque", "metric", "分牛顿米", "分牛顿米"),
    TORQUE_KGM("kgm", "torque", "metric", "千克米", "千克米"),
    TORQUE_KG_CM("kg-cm", "torque", "metric", "千克厘米", "千克厘米"),
    TORQUE_LBF_FT("lbf-ft", "torque", "imperial", "磅英尺", "磅英尺"),
    TORQUE_LBF_IN("lbf-in", "torque", "imperial", "磅英寸", "磅英寸"),
    TORQUE_OZF_IN("ozf-in", "torque", "imperial", "盎司英寸", "盎司英寸"),

    // 20、照度单位
    ILLUMINANCE_LX("lx", "illuminance", "metric", "勒克斯", "勒克斯"),
    ILLUMINANCE_FOOTCANDLE("fc", "illuminance", "imperial", "英尺烛光", "英尺烛光"),

    // 21、视在功率单位
    APPARENT_POWER_VA("VA", "apparentPower", "SI", "伏安", "伏安"),
    APPARENT_POWER_MVA("mVA", "apparentPower", "SI", "毫伏安", "毫伏安"),
    APPARENT_POWER_KVA("kVA", "apparentPower", "SI", "千伏安", "千伏安"),
    APPARENT_POWER_MVA_MEGA("MVA", "apparentPower", "SI", "兆伏安", "兆伏安"),
    APPARENT_POWER_GVA("GVA", "apparentPower", "SI", "吉伏安", "吉伏安"),

    // 22、无功功率单位
    REACTIVE_POWER_VAR("VAR", "reactivePower", "SI", "乏", "乏"),
    REACTIVE_POWER_MVAR("mVAR", "reactivePower", "SI", "毫乏", "毫乏"),
    REACTIVE_POWER_KVAR("kVAR", "reactivePower", "SI", "千乏", "千乏"),
    REACTIVE_POWER_MVAR_MEGA("MVAR", "reactivePower", "SI", "兆乏", "兆乏"),
    REACTIVE_POWER_GVAR("GVAR", "reactivePower", "SI", "吉乏", "吉乏"),

    // 23、无功能量单位
    REACTIVE_ENERGY_VARH("VARh", "reactiveEnergy", "SI", "乏时", "乏时"),
    REACTIVE_ENERGY_MVARH("mVARh", "reactiveEnergy", "SI", "毫乏时", "毫乏时"),
    REACTIVE_ENERGY_KVARH("kVARh", "reactiveEnergy", "SI", "千乏时", "千乏时"),
    REACTIVE_ENERGY_MVARH_MEGA("MVARh", "reactiveEnergy", "SI", "兆乏时", "兆乏时"),
    REACTIVE_ENERGY_GVARH("GVARh", "reactiveEnergy", "SI", "吉乏时", "吉乏时"),

    // 24、部分浓度单位
    PARTS_PER_PPM("ppm", "partsPer", "SI", "百万分率", "百万分率"),
    PARTS_PER_PPB("ppb", "partsPer", "SI", "十亿分率", "十亿分率"),
    PARTS_PER_PPT("ppt", "partsPer", "SI", "万亿分率", "万亿分率"),
    PARTS_PER_PPQ("ppq", "partsPer", "SI", "千万亿分率", "千万亿分率"),

    // 25、质量流量率单位
    MASS_FLOW_RATE_KG_PER_S("kg/s", "massFlowRate", "metric", "千克/秒", "千克/秒"),
    MASS_FLOW_RATE_KG_PER_MIN("kg/min", "massFlowRate", "metric", "千克/分钟", "千克/分钟"),
    MASS_FLOW_RATE_KG_PER_H("kg/h", "massFlowRate", "metric", "千克/小时", "千克/小时"),
    MASS_FLOW_RATE_MT_PER_H("mt/h", "massFlowRate", "metric", "公吨/小时", "公吨/小时"),
    MASS_FLOW_RATE_LB_PER_S("lb/s", "massFlowRate", "imperial", "磅/秒", "磅/秒"),
    MASS_FLOW_RATE_LB_PER_H("lb/h", "massFlowRate", "imperial", "磅/小时", "磅/小时"),


    // 26、步速单位
    PACE_MIN_PER_KM("min/km", "pace", "metric", "分钟/公里", "分钟/公里"),
    PACE_S_PER_M("s/m", "pace", "metric", "秒/米", "秒/米"),
    PACE_MIN_PER_MI("min/mi", "pace", "imperial", "分钟/英里", "分钟/英里"),
    PACE_S_PER_FT("s/ft", "pace", "imperial", "秒/英尺", "秒/英尺"),

    // 27体积流量率单位
    VOLUME_FLOW_RATE_MM3_PER_S("mm3/s", "volumeFlowRate", "metric", "立方毫米/秒", "立方毫米/秒"),
    VOLUME_FLOW_RATE_CM3_PER_S("cm3/s", "volumeFlowRate", "metric", "立方厘米/秒", "立方厘米/秒"),
    VOLUME_FLOW_RATE_DM3_PER_S("dm3/s", "volumeFlowRate", "metric", "立方分米/秒", "立方分米/秒"),
    VOLUME_FLOW_RATE_DM3_PER_MIN("dm3/min", "volumeFlowRate", "metric", "立方分米/分钟", "立方分米/分钟"),
    VOLUME_FLOW_RATE_DM3_PER_H("dm3/h", "volumeFlowRate", "metric", "立方分米/小时", "立方分米/小时"),
    VOLUME_FLOW_RATE_DM3_PER_D("dm3/d", "volumeFlowRate", "metric", "立方分米/天", "立方分米/天"),
    VOLUME_FLOW_RATE_DM3_PER_A("dm3/a", "volumeFlowRate", "metric", "立方分米/年", "立方分米/年"),
    VOLUME_FLOW_RATE_ML_PER_S("ml/s", "volumeFlowRate", "metric", "毫升/秒", "毫升/秒"),
    VOLUME_FLOW_RATE_CL_PER_S("cl/s", "volumeFlowRate", "metric", "厘升/秒", "厘升/秒"),
    VOLUME_FLOW_RATE_DL_PER_S("dl/s", "volumeFlowRate", "metric", "分升/秒", "分升/秒"),
    VOLUME_FLOW_RATE_L_PER_S("l/s", "volumeFlowRate", "metric", "升/秒", "升/秒"),
    VOLUME_FLOW_RATE_L_PER_MIN("l/min", "volumeFlowRate", "metric", "升/分钟", "升/分钟"),
    VOLUME_FLOW_RATE_L_PER_H("l/h", "volumeFlowRate", "metric", "升/小时", "升/小时"),
    VOLUME_FLOW_RATE_L_PER_D("l/d", "volumeFlowRate", "metric", "升/天", "升/天"),
    VOLUME_FLOW_RATE_L_PER_A("l/a", "volumeFlowRate", "metric", "升/年", "升/年"),
    VOLUME_FLOW_RATE_KL_PER_S("kl/s", "volumeFlowRate", "metric", "千升/秒", "千升/秒"),
    VOLUME_FLOW_RATE_KL_PER_MIN("kl/min", "volumeFlowRate", "metric", "千升/分钟", "千升/分钟"),
    VOLUME_FLOW_RATE_KL_PER_H("kl/h", "volumeFlowRate", "metric", "千升/小时", "千升/小时"),
    VOLUME_FLOW_RATE_M3_PER_S("m3/s", "volumeFlowRate", "metric", "立方米/秒", "立方米/秒"),
    VOLUME_FLOW_RATE_M3_PER_MIN("m3/min", "volumeFlowRate", "metric", "立方米/分钟", "立方米/分钟"),
    VOLUME_FLOW_RATE_M3_PER_H("m3/h", "volumeFlowRate", "metric", "立方米/小时", "立方米/小时"),
    VOLUME_FLOW_RATE_M3_PER_D("m3/d", "volumeFlowRate", "metric", "立方米/天", "立方米/天"),
    VOLUME_FLOW_RATE_M3_PER_A("m3/a", "volumeFlowRate", "metric", "立方米/年", "立方米/年"),
    VOLUME_FLOW_RATE_KM3_PER_S("km3/s", "volumeFlowRate", "metric", "立方千米/秒", "立方千米/秒"),
    VOLUME_FLOW_RATE_TSP_PER_S("tsp/s", "volumeFlowRate", "imperial", "茶匙/秒", "茶匙/秒"),
    VOLUME_FLOW_RATE_TBS_PER_S("Tbs/s", "volumeFlowRate", "imperial", "汤匙/秒", "汤匙/秒"),
    VOLUME_FLOW_RATE_IN3_PER_S("in3/s", "volumeFlowRate", "imperial", "立方英寸/秒", "立方英寸/秒"),
    VOLUME_FLOW_RATE_IN3_PER_MIN("in3/min", "volumeFlowRate", "imperial", "立方英寸/分钟", "立方英寸/分钟"),
    VOLUME_FLOW_RATE_IN3_PER_H("in3/h", "volumeFlowRate", "imperial", "立方英寸/小时", "立方英寸/小时"),
    VOLUME_FLOW_RATE_FLOZ_PER_S("fl-oz/s", "volumeFlowRate", "imperial", "液盎司/秒", "液盎司/秒"),
    VOLUME_FLOW_RATE_FLOZ_PER_MIN("fl-oz/min", "volumeFlowRate", "imperial", "液盎司/分钟", "液盎司/分钟"),
    VOLUME_FLOW_RATE_FLOZ_PER_H("fl-oz/h", "volumeFlowRate", "imperial", "液盎司/小时", "液盎司/小时"),
    VOLUME_FLOW_RATE_CUP_PER_S("cup/s", "volumeFlowRate", "imperial", "杯/秒", "杯/秒"),
    VOLUME_FLOW_RATE_PNT_PER_S("pnt/s", "volumeFlowRate", "imperial", "品脱/秒", "品脱/秒"),
    VOLUME_FLOW_RATE_PNT_PER_MIN("pnt/min", "volumeFlowRate", "imperial", "品脱/分钟", "品脱/分钟"),
    VOLUME_FLOW_RATE_PNT_PER_H("pnt/h", "volumeFlowRate", "imperial", "品脱/小时", "品脱/小时"),
    VOLUME_FLOW_RATE_QT_PER_S("qt/s", "volumeFlowRate", "imperial", "夸脱/秒", "夸脱/秒"),
    VOLUME_FLOW_RATE_GAL_PER_S("gal/s", "volumeFlowRate", "imperial", "加仑/秒", "加仑/秒"),
    VOLUME_FLOW_RATE_GAL_PER_MIN("gal/min", "volumeFlowRate", "imperial", "加仑/分钟", "加仑/分钟"),
    VOLUME_FLOW_RATE_GAL_PER_H("gal/h", "volumeFlowRate", "imperial", "加仑/小时", "加仑/小时"),
    VOLUME_FLOW_RATE_FT3_PER_S("ft3/s", "volumeFlowRate", "imperial", "立方英尺/秒", "立方英尺/秒"),
    VOLUME_FLOW_RATE_FT3_PER_MIN("ft3/min", "volumeFlowRate", "imperial", "立方英尺/分钟", "立方英尺/分钟"),
    VOLUME_FLOW_RATE_FT3_PER_H("ft3/h", "volumeFlowRate", "imperial", "立方英尺/小时", "立方英尺/小时"),
    VOLUME_FLOW_RATE_YD3_PER_S("yd3/s", "volumeFlowRate", "imperial", "立方码/秒", "立方码/秒"),
    VOLUME_FLOW_RATE_YD3_PER_MIN("yd3/min", "volumeFlowRate", "imperial", "立方码/分钟", "立方码/分钟"),
    VOLUME_FLOW_RATE_YD3_PER_H("yd3/h", "volumeFlowRate", "imperial", "立方码/小时", "立方码/小时"),

    // 28、频率单位
    FREQUENCY_HZ("Hz", "frequency", "metric", "赫兹", "赫兹"),
    FREQUENCY_KHZ("kHz", "frequency", "metric", "千赫", "千赫"),
    FREQUENCY_MHZ("MHz", "frequency", "metric", "兆赫", "兆赫"),
    FREQUENCY_GHZ("GHz", "frequency", "metric", "吉赫", "吉赫"),
    FREQUENCY_THZ("THz", "frequency", "metric", "太赫", "太赫"),
    FREQUENCY_RPM("rpm", "frequency", "metric", "转每分钟", "转每分钟"),
    FREQUENCY_DEG_S("deg/s", "frequency", "metric", "度/秒", "度/秒"),
    FREQUENCY_RAD_S("rad/s", "frequency", "metric", "弧度/秒", "弧度/秒"),
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
     * 详细描述方法的功能、算法逻辑或业务流程
     * 处理逻辑：
     * <ol>
     *   <li>接收单位的各项属性参数</li>
     *   <li>将参数分别赋值给对应的私有字段</li>
     *   <li>完成枚举项的初始化</li>
     * </ol>
     * 参数说明：
     * <ul>
     *   <li>abbr: 单位缩写，用于标识单位的简短形式</li>
     *   <li>measure: 测量类型，标识单位所属的物理量类型</li>
     *   <li>system: 单位系统，标识单位所属的度量系统</li>
     *   <li>singular: 单数形式，单位的单数表达形式</li>
     *   <li>plural: 复数形式，单位的复数表达形式</li>
     * </ul>
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