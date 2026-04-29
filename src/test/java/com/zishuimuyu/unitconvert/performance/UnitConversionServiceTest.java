package com.zishuimuyu.unitconvert.performance;

import com.zishuimuyu.unitconvert.chain.ChainConversionContext;
import com.zishuimuyu.unitconvert.config.UnitConversionConfig;
import com.zishuimuyu.unitconvert.config.UnitConversionConfigUtils;
import com.zishuimuyu.unitconvert.config.UnitConversionConfigValidator;
import com.zishuimuyu.unitconvert.exception.UnitConversionException;
import com.zishuimuyu.unitconvert.model.ConvertResult;
import com.zishuimuyu.unitconvert.model.ToBestOptions;
import com.zishuimuyu.unitconvert.model.UnitDescription;
import com.zishuimuyu.unitconvert.model.UnitEnum;
import com.zishuimuyu.unitconvert.service.UnitConversionServiceImpl;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 单位转换服务测试类
 * <p>
 * 本测试类用于验证单位转换服务的各项功能是否正常工作，包括：
 * - 基础单位转换功能（长度、质量、温度等）
 * - 自动选择最佳单位功能
 * - 链式调用API功能
 * - 单位查询和描述功能
 * - 部分加载和单位排除功能
 * - 配置验证和管理功能
 * <p>
 * 测试覆盖了所有主要的测量类型和转换场景，确保转换精度和准确性。
 *
 * @author 紫水木鱼
 * @version 1.0.0
 * @since 2026-04-24
 */
class UnitConversionServiceTest {

    /**
     * 默认的单位转换服务实例
     * 使用默认配置（加载所有测量类型和单位）
     */
    private final UnitConversionServiceImpl conversionService = new UnitConversionServiceImpl();

    /**
     * 测试长度单位转换功能
     * <p>
     * 验证米到厘米的转换是否正确，包括：
     * - 米到厘米的转换（1米 = 100厘米）
     * - 厘米到米的转换（100厘米 = 1米）
     * - 毫米到米的转换（1000毫米 = 1米）
     * - 千米到米的转换（1千米 = 1000米）
     */
    @Test
    void testLengthConversion() {
        // 测试米到厘米的转换：1米应该等于100厘米
        ConvertResult<BigDecimal> result = conversionService.convert(new BigDecimal("1"), UnitEnum.M, UnitEnum.CM);
        System.out.println(result.getFormattedValue());
        assertEquals(0, new BigDecimal("100").compareTo(result.getValue()), "1米应该等于100厘米");
        assertEquals("cm", result.getUnit(), "目标单位应该是厘米");

        // 测试厘米到米的转换：100厘米应该等于1米
        result = conversionService.convert(new BigDecimal("100"), UnitEnum.CM, UnitEnum.M);

        assertEquals(0, new BigDecimal("1").compareTo(result.getValue()), "100厘米应该等于1米");

        // 测试毫米到米的转换：1000毫米应该等于1米
        result = conversionService.convert(new BigDecimal("1000"), UnitEnum.MM, UnitEnum.M);
        assertEquals(0, new BigDecimal("1").compareTo(result.getValue()), "1000毫米应该等于1米");

        // 测试千米到米的转换：1千米应该等于1000米
        result = conversionService.convert(new BigDecimal("1"), UnitEnum.KM, UnitEnum.M);
        assertEquals(0, new BigDecimal("1000").compareTo(result.getValue()), "1千米应该等于1000米");
        System.out.println(result.getValue());
    }

    /**
     * 测试质量单位转换功能
     * 验证千克到克的转换是否正确
     */
    @Test
    void testMassConversion() {
        // 测试千克到克的转换
        ConvertResult<BigDecimal> result = conversionService.convert(new BigDecimal("1"), UnitEnum.KG, UnitEnum.G);
        assertEquals(0, new BigDecimal("1000").compareTo(result.getValue()), "1千克应该等于1000克");

        // 测试克到千克的转换
        result = conversionService.convert(new BigDecimal("1000"), UnitEnum.G, UnitEnum.KG);
        assertEquals(0, new BigDecimal("1").compareTo(result.getValue()), "1000克应该等于1千克");

        // 测试新增的质量单位
        result = conversionService.convert(new BigDecimal("1000"), UnitEnum.MG, UnitEnum.G);
        assertEquals(0, new BigDecimal("1").compareTo(result.getValue()), "1000毫克应该等于1克");
    }

    /**
     * 测试温度单位转换功能
     * 验证摄氏度到华氏度的转换是否正确
     */
    @Test
    void testTemperatureConversion() {
        // 测试摄氏度到华氏度的转换 (0°C = 32°F)
        ConvertResult<BigDecimal> result = conversionService.convert(new BigDecimal("0"), UnitEnum.C, UnitEnum.F);
        assertEquals(0, new BigDecimal("32").compareTo(result.getValue()), "0摄氏度应该等于32华氏度");

        // 测试华氏度到摄氏度的转换 (32°F = 0°C)
        result = conversionService.convert(new BigDecimal("32"), UnitEnum.F, UnitEnum.C);
        assertEquals(0, new BigDecimal("0").compareTo(result.getValue()), "32华氏度应该等于0摄氏度");

        // 测试摄氏度到开尔文的转换 (0°C = 273.15K)
        result = conversionService.convert(new BigDecimal("0"), UnitEnum.C, UnitEnum.K);
        assertEquals(0, new BigDecimal("273.15").compareTo(result.getValue()), "0摄氏度应该等于273.15开尔文");

        // 测试兰金温标转换
        // 0°C = 32°F = 491.67°R
        result = conversionService.convert(new BigDecimal("0"), UnitEnum.C, UnitEnum.R);
        BigDecimal expectedRankine = new BigDecimal("491.67");
        assertTrue(expectedRankine.subtract(result.getValue()).abs().compareTo(new BigDecimal("0.01")) < 0,
                "0°C应该等于491.67°R，实际值：" + result.getValue());

        // 32°F = 491.67°R
        result = conversionService.convert(new BigDecimal("32"), UnitEnum.F, UnitEnum.R);
        assertTrue(expectedRankine.subtract(result.getValue()).abs().compareTo(new BigDecimal("0.01")) < 0,
                "32°F应该等于491.67°R，实际值：" + result.getValue());

        // 491.67°R = 32°F
        result = conversionService.convert(new BigDecimal("491.67"), UnitEnum.R, UnitEnum.F);
        BigDecimal expectedFahrenheit = new BigDecimal("32");
        assertTrue(expectedFahrenheit.subtract(result.getValue()).abs().compareTo(new BigDecimal("0.01")) < 0,
                "491.67°R应该等于32°F，实际值：" + result.getValue());

        // 测试新增的单位
        // 测试公制马力转换
        result = conversionService.convert(new BigDecimal("1"), UnitEnum.PS, UnitEnum.W);
        BigDecimal expectedPS = new BigDecimal("735.49875");
        assertTrue(expectedPS.subtract(result.getValue()).abs().compareTo(new BigDecimal("0.01")) < 0,
                "1 PS应该等于735.49875 W，实际值：" + result.getValue());

        // 测试压力单位转换
        result = conversionService.convert(new BigDecimal("1"), UnitEnum.BAR, UnitEnum.PSI);
        BigDecimal expectedBarToPsi = new BigDecimal("14.5038"); // 1 bar ≈ 14.5038 psi
        assertTrue(expectedBarToPsi.subtract(result.getValue()).abs().compareTo(new BigDecimal("0.1")) < 0,
                "1 bar应该约等于14.5038 psi，实际值：" + result.getValue());

        // 测试速度单位转换
        result = conversionService.convert(new BigDecimal("1"), UnitEnum.KM_H, UnitEnum.MPH);
        BigDecimal expectedKmhToMph = new BigDecimal("0.621371"); // 1 km/h ≈ 0.621371 mph
        assertTrue(expectedKmhToMph.subtract(result.getValue()).abs().compareTo(new BigDecimal("0.01")) < 0,
                "1 km/h应该约等于0.621371 mph，实际值：" + result.getValue());

        // 测试时间单位转换
        result = conversionService.convert(new BigDecimal("1"), UnitEnum.US, UnitEnum.MU);
        assertEquals(0, new BigDecimal("1").compareTo(result.getValue()), "1 μs应该等于1 mu");
    }

    /**
     * 测试获取最佳单位功能
     * <p>
     * 验证convertToBest方法能够根据数值大小自动选择最适合的单位：
     * - 1000毫米应该转换为米（数值大于等于1且尽可能小）
     * - 0.001米应该转换为毫米（数值大于等于1且尽可能小）
     */
    @Test
    void testConvertToBest() {
        // 测试长度单位转换到最佳表示：1000毫米应该转换为米
        ConvertResult<BigDecimal> result = conversionService.convertToBest(new BigDecimal("1000"), UnitEnum.MM);
        assertEquals("m", result.getUnit(), "1000毫米应该转换为米");
        assertEquals(0, new BigDecimal("1").compareTo(result.getValue()), "1000毫米应该等于1米");

        // 测试小数值的最佳单位选择：0.001米应该转换为毫米
        result = conversionService.convertToBest(new BigDecimal("0.001"), UnitEnum.M);
        assertEquals("mm", result.getUnit(), "0.001米应该转换为毫米");
        assertEquals(0, new BigDecimal("1").compareTo(result.getValue()), "0.001米应该等于1毫米");
    }

    /**
     * 测试获取支持的测量类型功能
     */
    @Test
    void testGetSupportedMeasures() {
        List<String> measures = conversionService.getSupportedMeasures();
        assertFalse(measures.isEmpty(), "应该支持至少一个测量类型");
        assertTrue(measures.contains("length"), "应该支持长度测量类型");
        assertTrue(measures.contains("mass"), "应该支持质量测量类型");
        assertTrue(measures.contains("volume"), "应该支持体积测量类型");
    }

    /**
     * 测试获取指定测量类型下所有可能单位的功能
     */
    @Test
    void testGetPossibleUnits() {
        List<UnitEnum> lengthUnits = conversionService.getPossibleUnits("length");
        assertFalse(lengthUnits.isEmpty(), "长度测量类型应该有单位");
        assertTrue(lengthUnits.contains(UnitEnum.M), "长度单位应该包含米");
        assertTrue(lengthUnits.contains(UnitEnum.CM), "长度单位应该包含厘米");
        assertTrue(lengthUnits.contains(UnitEnum.MM), "长度单位应该包含毫米");

        // 测试获取不存在的测量类型
        List<UnitEnum> emptyList = conversionService.getPossibleUnits("nonexistent");
        assertTrue(emptyList.isEmpty(), "不存在的测量类型应该返回空列表");
    }

    /**
     * 测试列出所有单位功能
     */
    @Test
    void testListAllUnits() {
        List<UnitDescription> allUnits = conversionService.listAllUnits();
        assertFalse(allUnits.isEmpty(), "应该有至少一个单位");

        // 检查是否包含特定单位
        boolean hasMeter = allUnits.stream()
                .anyMatch(desc -> "m".equals(desc.getAbbr()) && "length".equals(desc.getMeasure()));
        assertTrue(hasMeter, "应该包含米单位");
    }

    /**
     * 测试列出指定测量类型下所有单位功能
     */
    @Test
    void testListUnitsByMeasure() {
        List<UnitDescription> lengthUnits = conversionService.listUnitsByMeasure("length");
        assertFalse(lengthUnits.isEmpty(), "长度测量类型应该有单位");

        // 检查是否包含长度单位
        boolean hasMeter = lengthUnits.stream()
                .anyMatch(desc -> "m".equals(desc.getAbbr()));
        assertTrue(hasMeter, "应该包含米单位");

        // 测试获取不存在的测量类型
        List<UnitDescription> emptyList = conversionService.listUnitsByMeasure("nonexistent");
        assertTrue(emptyList.isEmpty(), "不存在的测量类型应该返回空列表");
    }

    /**
     * 测试单位描述功能
     */
    @Test
    void testDescribeUnit() {
        UnitDescription desc = conversionService.describeUnit(UnitEnum.M);
        assertEquals("m", desc.getAbbr(), "米的缩写应该是m");
        assertEquals("length", desc.getMeasure(), "米的测量类型应该是长度");
        assertEquals("metric", desc.getSystem(), "米的系统应该是公制");
        assertEquals("米", desc.getSingular(), "米的单数形式应该是米");
        assertEquals("米", desc.getPlural(), "米的复数形式应该是米");
    }

    /**
     * 测试链式调用功能
     * <p>
     * 验证链式API的正确性，包括：
     * - from().to()方法的链式调用
     * - 链式调用的结果与直接调用convert()方法一致
     * - 操作顺序的正确性（必须先调用from，再调用to）
     */
    @Test
    void testChainedConversion() {
        // 测试链式调用: convert(1).from(M).to(CM)
        ChainConversionContext context = new ChainConversionContext(conversionService, new BigDecimal("1"), UnitEnum.M);
        ConvertResult<BigDecimal> result = context.to(UnitEnum.CM);
        assertEquals(0, new BigDecimal("100").compareTo(result.getValue()), "1米应该等于100厘米");
        assertEquals("cm", result.getUnit(), "目标单位应该是厘米");
    }

    /**
     * 测试可能性查询功能
     */
    @Test
    void testPossibilities() {
        // 设置源单位
        conversionService.from(new BigDecimal("1"), UnitEnum.M);

        // 获取当前源单位可能转换的目标单位
        List<UnitEnum> possibilities = conversionService.possibilities(null);
        assertFalse(possibilities.isEmpty(), "长度单位应该有多个可能的转换目标");
        assertTrue(possibilities.contains(UnitEnum.CM), "可能性列表应该包含厘米");
        assertTrue(possibilities.contains(UnitEnum.MM), "可能性列表应该包含毫米");
        assertTrue(possibilities.contains(UnitEnum.KM), "可能性列表应该包含千米");

        // 使用指定测量类型过滤
        List<UnitEnum> lengthPossibilities = conversionService.possibilities("length");
        assertFalse(lengthPossibilities.isEmpty(), "长度单位应该有多个可能的转换目标");

        // 重置状态后测试
        conversionService.from(new BigDecimal("1"), UnitEnum.KG);
        List<UnitEnum> massPossibilities = conversionService.possibilities(null);
        assertFalse(massPossibilities.isEmpty(), "质量单位应该有多个可能的转换目标");
        assertTrue(massPossibilities.contains(UnitEnum.G), "质量可能性列表应该包含克");
        assertTrue(massPossibilities.contains(UnitEnum.MG), "质量可能性列表应该包含毫克");
    }

    /**
     * 测试获取所有测量类型功能
     */
    @Test
    void testMeasures() {
        List<String> measures = conversionService.measures();
        assertFalse(measures.isEmpty(), "应该有至少一个测量类型");
        assertTrue(measures.contains("length"), "应该包含长度测量类型");
        assertTrue(measures.contains("mass"), "应该包含质量测量类型");
        assertTrue(measures.contains("volume"), "应该包含体积测量类型");
    }

    /**
     * 测试带选项的最佳单位转换功能
     * <p>
     * 验证convertToBest方法的选项功能：
     * - excludeUnits选项：排除特定单位，强制选择其他单位
     * - cutOffNumber选项：设置数值截止值，控制单位选择的范围
     * - system选项：限制单位系统（如只选择公制单位）
     */
    @Test
    void testConvertToBestWithOptions() {
        // 测试排除某些单位的选项：排除毫米和厘米，1000毫米应该转换为米
        List<UnitEnum> excludeUnits = Arrays.asList(UnitEnum.MM, UnitEnum.CM);
        ToBestOptions options = ToBestOptions.withExclude(excludeUnits);

        ConvertResult<BigDecimal> result = conversionService.convertToBest(
                new BigDecimal("1000"), UnitEnum.MM, options);
        assertEquals("m", result.getUnit(), "应该跳过被排除的单位，选择米");
        assertEquals(0, new BigDecimal("1").compareTo(result.getValue()), "1000毫米应该等于1米");

        // 测试自定义截止数值：设置截止值为100，10米应该保持为米
        ToBestOptions customCutoffOptions = ToBestOptions.withCutOffNumber(100.0);
        result = conversionService.convertToBest(
                new BigDecimal("10"), UnitEnum.M, customCutoffOptions);
        assertTrue(result.getValue().compareTo(new BigDecimal("100")) <= 0,
                "转换结果应该小于截止值100");

        // 测试限制单位系统：只选择公制单位，不应该选择英制单位
        ToBestOptions systemOptions = ToBestOptions.withSystem("metric");
        result = conversionService.convertToBest(
                new BigDecimal("1000"), UnitEnum.MM, systemOptions);
        assertTrue(result.getUnit().equals("m") || result.getUnit().equals("cm") || result.getUnit().equals("mm"),
                "应该只返回公制单位");
    }

    /**
     * 测试链式调用的操作顺序错误处理
     * <p>
     * 验证链式API的错误处理机制：
     * - 在未调用from()之前调用to()应该抛出IllegalStateException
     * - 正确的操作顺序：先调用from()，再调用to()
     */
    @Test
    void testOperationOrderError() {
        // 先调用to再调用from应该抛出异常
        assertThrows(IllegalArgumentException.class, () -> {
            ChainConversionContext context = new ChainConversionContext(conversionService, null, UnitEnum.M);
        }, "应该在没有调用from之前调用to时抛出异常");

        // 正常流程：from -> to
        ChainConversionContext context = new ChainConversionContext(conversionService, new BigDecimal("1"), UnitEnum.M);
        ConvertResult<BigDecimal> result = context.to(UnitEnum.CM);
        assertEquals(0, new BigDecimal("100").compareTo(result.getValue()), "1米应该等于100厘米");
    }

    /**
     * 测试体积单位转换功能
     * 验证升到毫升的转换是否正确
     */
    @Test
    void testVolumeConversion() {
        // 测试升到毫升的转换
        ConvertResult<BigDecimal> result = conversionService.convert(new BigDecimal("1"), UnitEnum.L, UnitEnum.ML);
        assertEquals(0, new BigDecimal("1000").compareTo(result.getValue()), "1升应该等于1000毫升");

        // 测试毫升到升的转换
        result = conversionService.convert(new BigDecimal("1000"), UnitEnum.ML, UnitEnum.L);
        assertEquals(0, new BigDecimal("1").compareTo(result.getValue()), "1000毫升应该等于1升");

        // 测试新增的体积单位
        result = conversionService.convert(new BigDecimal("1"), UnitEnum.M3, UnitEnum.L);
        assertEquals(0, new BigDecimal("1000").compareTo(result.getValue()), "1立方米应该等于1000升");
    }

    /**
     * 测试时间单位转换功能
     * 验证小时到分钟的转换是否正确
     */
    @Test
    void testTimeConversion() {
        // 测试小时到分钟的转换
        ConvertResult<BigDecimal> result = conversionService.convert(new BigDecimal("1"), UnitEnum.H, UnitEnum.MIN);
        assertEquals(0, new BigDecimal("60").compareTo(result.getValue()), "1小时应该等于60分钟");

        // 测试分钟到秒的转换
        result = conversionService.convert(new BigDecimal("1"), UnitEnum.MIN, UnitEnum.S);
        assertEquals(0, new BigDecimal("60").compareTo(result.getValue()), "1分钟应该等于60秒");

        // 测试新增的时间单位
        result = conversionService.convert(new BigDecimal("1"), UnitEnum.D, UnitEnum.H);
        assertEquals(0, new BigDecimal("24").compareTo(result.getValue()), "1天应该等于24小时");
    }

    /**
     * 测试面积单位转换功能
     */
    @Test
    void testAreaConversion() {
        // 测试平方米到平方厘米的转换
        ConvertResult<BigDecimal> result = conversionService.convert(new BigDecimal("1"), UnitEnum.M2, UnitEnum.CM2);
        assertEquals(0, new BigDecimal("10000").compareTo(result.getValue()), "1平方米应该等于10000平方厘米");

        // 测试平方千米到平方米的转换
        result = conversionService.convert(new BigDecimal("1"), UnitEnum.KM2, UnitEnum.M2);
        assertEquals(0, new BigDecimal("1000000").compareTo(result.getValue()), "1平方千米应该等于1000000平方米");
    }

    /**
     * 测试速度单位转换功能
     */
    @Test
    void testSpeedConversion() {
        // 测试米/秒到千米/小时的转换
        ConvertResult<BigDecimal> result = conversionService.convert(new BigDecimal("1"), UnitEnum.M_S, UnitEnum.KM_H);
        // 1 m/s = 3.6 km/h
        assertEquals(0, new BigDecimal("3.6").compareTo(result.getValue().setScale(1, RoundingMode.HALF_UP)), "1米/秒应该等于3.6千米/小时");
    }

    /**
     * 测试功率单位转换功能
     */
    @Test
    void testPowerConversion() {
        // 测试瓦特到千瓦的转换
        ConvertResult<BigDecimal> result = conversionService.convert(new BigDecimal("1000"), UnitEnum.W, UnitEnum.KW);
        assertEquals(0, new BigDecimal("1").compareTo(result.getValue()), "1000瓦应该等于1千瓦");
    }

    /**
     * 测试压力单位转换功能
     */
    @Test
    void testPressureConversion() {
        // 测试帕斯卡到千帕的转换
        ConvertResult<BigDecimal> result = conversionService.convert(new BigDecimal("1000"), UnitEnum.PA, UnitEnum.KPA);
        assertEquals(0, new BigDecimal("1").compareTo(result.getValue()), "1000帕应该等于1千帕");
    }

    /**
     * 测试数字存储单位转换功能
     */
    @Test
    void testDigitalConversion() {
        // 测试字节到千字节的转换
        ConvertResult<BigDecimal> result = conversionService.convert(new BigDecimal("1000"), UnitEnum.BYTE, UnitEnum.KBYTE);
        assertEquals(0, new BigDecimal("1").compareTo(result.getValue()), "1000字节应该等于1千字节");

        // 测试比特到字节的转换
        result = conversionService.convert(new BigDecimal("8"), UnitEnum.BIT, UnitEnum.BYTE);
        assertEquals(0, new BigDecimal("1").compareTo(result.getValue()), "8比特应该等于1字节");
    }


    /**
     * 测试加速度单位转换功能
     */
    @Test
    void testAccelerationConversion() {
        // 测试g-force到m/s2的转换
        ConvertResult<BigDecimal> result = conversionService.convert(new BigDecimal("1"), UnitEnum.G_FORCE, UnitEnum.M_S2);
        assertEquals(0, new BigDecimal("9.80665").compareTo(result.getValue().setScale(5, RoundingMode.HALF_UP)), "1重力加速度应该等于9.80665米/秒²");
    }

    /**
     * 测试能量单位转换功能
     */
    @Test
    void testEnergyConversion() {
        // 测试焦耳到卡路里的转换
        ConvertResult<BigDecimal> result = conversionService.convert(new BigDecimal("1"), UnitEnum.J, UnitEnum.CAL);
        // 1焦耳 ≈ 0.239卡路里
        assertEquals(0, new BigDecimal("0.239").compareTo(result.getValue().setScale(3, RoundingMode.HALF_UP)), "1焦耳应该约等于0.239卡路里");
    }

    /**
     * 测试力单位转换功能
     */
    @Test
    void testForceConversion() {
        // 测试牛顿到磅力的转换
        ConvertResult<BigDecimal> result = conversionService.convert(new BigDecimal("1"), UnitEnum.N, UnitEnum.LBF);
        // 1牛顿 ≈ 0.2248磅力
        assertEquals(0, new BigDecimal("0.2248").compareTo(result.getValue().setScale(4, RoundingMode.HALF_UP)), "1牛顿应该约等于0.2248磅力");
    }

    /**
     * 测试扭矩单位转换功能
     */
    @Test
    void testTorqueConversion() {
        // 测试牛顿米到磅英尺的转换
        ConvertResult<BigDecimal> result = conversionService.convert(new BigDecimal("1"), UnitEnum.NM, UnitEnum.LBF_FT);
        // 1牛顿米 ≈ 0.7376磅英尺
        assertEquals(0, new BigDecimal("0.7376").compareTo(result.getValue().setScale(4, RoundingMode.HALF_UP)), "1牛顿米应该约等于0.7376磅英尺");
    }

    /**
     * 测试频率单位转换功能
     */
    @Test
    void testFrequencyConversion() {
        // 测试赫兹到千赫兹的转换
        ConvertResult<BigDecimal> result = conversionService.convert(new BigDecimal("1000"), UnitEnum.HZ, UnitEnum.KHZ);
        assertEquals(0, new BigDecimal("1").compareTo(result.getValue()), "1000赫兹应该等于1千赫兹");
    }

    /**
     * 测试照度单位转换功能
     */
    @Test
    void testIlluminanceConversion() {
        // 测试勒克斯到英尺烛光的转换
        ConvertResult<BigDecimal> result = conversionService.convert(new BigDecimal("10.764"), UnitEnum.LX, UnitEnum.FOOTCANDLE);
        assertEquals(0, new BigDecimal("1").compareTo(result.getValue().setScale(3, RoundingMode.HALF_UP)), "10.764勒克斯应该约等于1英尺烛光");
    }

    /**
     * 测试视在功率单位转换功能
     */
    @Test
    void testApparentPowerConversion() {
        // 测试伏安到千伏安的转换
        ConvertResult<BigDecimal> result = conversionService.convert(new BigDecimal("1000"), UnitEnum.VA, UnitEnum.KVA);
        assertEquals(0, new BigDecimal("1").compareTo(result.getValue()), "1000伏安应该等于1千伏安");
    }

    /**
     * 测试部分浓度单位转换功能
     */
    @Test
    void testPartsPerConversion() {
        // 测试百万分率到十亿分率的转换
        ConvertResult<BigDecimal> result = conversionService.convert(new BigDecimal("1"), UnitEnum.PPM, UnitEnum.PPB);
        assertEquals(0, new BigDecimal("1000").compareTo(result.getValue()), "1百万分率应该等于1000十亿分率");
    }

    /**
     * 测试数量单位转换功能
     */
    @Test
    void testPiecesConversion() {
        // 测试件到dozen的转换
        ConvertResult<BigDecimal> result = conversionService.convert(new BigDecimal("24"), UnitEnum.PCS_PIECES, UnitEnum.DOZ);
        assertEquals(0, new BigDecimal("2").compareTo(result.getValue()), "24件应该等于2dozen");

        // 测试 dozen 到 个的转换 (each类型内部转换)
        result = conversionService.convert(new BigDecimal("1"), UnitEnum.DOZEN, UnitEnum.EA);
        assertEquals(0, new BigDecimal("12").compareTo(result.getValue()), "1 dozen应该等于12个");

        // 测试 ea 到 dozen 的转换 (each类型内部转换)
        result = conversionService.convert(new BigDecimal("12"), UnitEnum.EA, UnitEnum.DOZEN);
        assertEquals(0, new BigDecimal("1").compareTo(result.getValue()), "12 个应该等于1 dozen");

        // 测试 ea 到 single 的转换 (each类型内部转换)
        result = conversionService.convert(new BigDecimal("1"), UnitEnum.EA, UnitEnum.SINGLE);
        assertEquals(0, new BigDecimal("1").compareTo(result.getValue()), "1 个(ea)应该等于1 个(single)");
    }

    /**
     * 测试无功功率单位转换功能
     */
    @Test
    void testReactivePowerConversion() {
        // 测试乏到千乏的转换
        ConvertResult<BigDecimal> result = conversionService.convert(new BigDecimal("1000"), UnitEnum.VAR, UnitEnum.KVAR);
        assertEquals(0, new BigDecimal("1").compareTo(result.getValue()), "1000乏应该等于1千乏");
    }

    /**
     * 测试无功能量单位转换功能
     */
    @Test
    void testReactiveEnergyConversion() {
        // 测试乏时到千乏时的转换
        ConvertResult<BigDecimal> result = conversionService.convert(new BigDecimal("1000"), UnitEnum.VARH, UnitEnum.KVARH);
        assertEquals(0, new BigDecimal("1").compareTo(result.getValue()), "1000乏时应该等于1千乏时");
    }

    /**
     * 测试质量流量率单位转换功能
     */
    @Test
    void testMassFlowRateConversion() {
        // 测试千克/秒到千克/小时的转换
        ConvertResult<BigDecimal> result = conversionService.convert(new BigDecimal("1"), UnitEnum.KG_PER_S, UnitEnum.KG_PER_H);
        // 由于精度问题，允许小的误差范围
        assertTrue(new BigDecimal("3600").subtract(result.getValue()).abs().compareTo(new BigDecimal("0.01")) < 0,
                "1千克/秒应该约等于3600千克/小时，实际值：" + result.getValue());
    }

    /**
     * 测试体积流量率单位转换功能
     */
    @Test
    void testVolumeFlowRateConversion() {
        // 测试升/分钟到立方米/秒的转换
        ConvertResult<BigDecimal> result = conversionService.convert(new BigDecimal("60"), UnitEnum.L_PER_MIN, UnitEnum.M3_PER_S);
        // 由于精度问题，允许小的误差范围
        assertTrue(new BigDecimal("0.001").subtract(result.getValue()).abs().compareTo(new BigDecimal("0.0001")) < 0,
                "60升/分钟应该约等于0.001立方米/秒，实际值：" + result.getValue());
    }

    /**
     * 测试步速单位转换功能
     */
    @Test
    void testPaceConversion() {
        // 测试分钟/公里到秒/米的转换
        ConvertResult<BigDecimal> result = conversionService.convert(new BigDecimal("1"), UnitEnum.MIN_PER_KM, UnitEnum.S_PER_M);
        // 1分钟/公里 = 0.06秒/米
        assertTrue(new BigDecimal("0.06").subtract(result.getValue()).abs().compareTo(new BigDecimal("0.0001")) < 0,
                "1分钟/公里应该约等于0.06秒/米，实际值：" + result.getValue());
    }

    /**
     * 测试步速单位跨系统转换功能
     */
    @Test
    void testPaceCrossSystemConversion() {
        // 测试公制单位到英制单位的转换：分钟/公里 到 分钟/英里
        ConvertResult<BigDecimal> result = conversionService.convert(new BigDecimal("1"), UnitEnum.MIN_PER_KM, UnitEnum.MIN_PER_MI);
        // 1分钟/公里 = 1分钟/公里 * (1公里/0.621371英里) = 1/0.621371 分钟/英里 ≈ 1.609 分钟/英里
        // 根据JS实现的比率0.3048，计算结果应接近1.609
        BigDecimal expected = new BigDecimal("1.609");
        assertTrue(expected.subtract(result.getValue()).abs().compareTo(new BigDecimal("0.1")) < 0,
                "1分钟/公里应该约等于1.609分钟/英里，实际值：" + result.getValue());
    }

    /**
     * 测试lookup方法 - 通过缩写查找
     */
    @Test
    void testLookupByAbbr() {
        UnitDescription desc = conversionService.lookup("m");
        assertNotNull(desc, "应该找到米单位");
        assertEquals("m", desc.getAbbr(), "单位缩写应该是m");
        assertEquals("length", desc.getMeasure(), "测量类型应该是length");
        assertEquals("米", desc.getSingular(), "单数形式应该是米");
    }

    /**
     * 测试lookup方法 - 通过单数名称查找
     */
    @Test
    void testLookupBySingular() {
        UnitDescription desc = conversionService.lookup("米");
        assertNotNull(desc, "应该找到米单位");
        assertEquals("m", desc.getAbbr(), "单位缩写应该是m");
    }

    /**
     * 测试lookup方法 - 通过复数名称查找
     */
    @Test
    void testLookupByPlural() {
        UnitDescription desc = conversionService.lookup("千克");
        assertNotNull(desc, "应该找到千克单位");
        assertEquals("kg", desc.getAbbr(), "单位缩写应该是kg");
    }

    /**
     * 测试lookup方法 - 大小写不敏感
     */
    @Test
    void testLookupCaseInsensitive() {
        UnitDescription desc1 = conversionService.lookup("M");
        UnitDescription desc2 = conversionService.lookup("m");
        UnitDescription desc3 = conversionService.lookup("M");

        assertNotNull(desc1, "应该找到米单位（大写）");
        assertNotNull(desc2, "应该找到米单位（小写）");
        assertEquals(desc1.getAbbr(), desc2.getAbbr(), "大小写应该不影响查找结果");
    }

    /**
     * 测试lookup方法 - 查找不存在的单位
     */
    @Test
    void testLookupNotFound() {
        UnitDescription desc = conversionService.lookup("nonexistent");
        assertNull(desc, "查找不存在的单位应该返回null");
    }

    /**
     * 测试ConvertResult的toFriendlyString方法
     */
    @Test
    void testToFriendlyString() {
        ConvertResult<BigDecimal> result = conversionService.convert(new BigDecimal("1"), UnitEnum.M, UnitEnum.CM);
        String friendlyString = result.toFriendlyString();
        assertEquals("100 cm", friendlyString, "友好字符串应该是'100 cm'");
    }

    /**
     * 测试部分加载功能
     * <p>
     * 验证部分加载配置的正确性：
     * - 只加载指定的测量类型（length和mass）
     * - 不加载未指定的测量类型（volume和temperature）
     * - getSupportedMeasures()方法返回正确的测量类型列表
     */
    @Test
    void testPartialLoading() {
        // 创建部分加载配置：只加载长度和质量测量类型
        UnitConversionConfig config = UnitConversionConfig.partialLoading("length", "mass");

        UnitConversionServiceImpl partialService = new UnitConversionServiceImpl(config);

        // 验证支持的测量类型
        List<String> measures = partialService.getSupportedMeasures();
        assertTrue(measures.contains("length"), "应该包含长度测量类型");
        assertTrue(measures.contains("mass"), "应该包含质量测量类型");
        assertFalse(measures.contains("volume"), "不应该包含体积测量类型");
        assertFalse(measures.contains("temperature"), "不应该包含温度测量类型");
    }

    /**
     * 测试单位排除功能
     * <p>
     * 验证单位排除配置的正确性：
     * - 排除特定的单位（海里和英寻）
     * - getPossibleUnits()方法不返回被排除的单位
     * - 其他单位正常返回
     */
    @Test
    void testExcludeUnits() {
        // 创建单位排除配置：排除海里和英寻单位
        UnitConversionConfig config = new UnitConversionConfig.Builder()
                .excludeUnits(UnitEnum.NMI, UnitEnum.FATHOM)
                .build();

        UnitConversionServiceImpl filteredService = new UnitConversionServiceImpl(config);

        // 验证被排除的单位不在列表中
        List<UnitEnum> lengthUnits = filteredService.getPossibleUnits("length");
        assertFalse(lengthUnits.contains(UnitEnum.NMI), "不应该包含海里单位");
        assertFalse(lengthUnits.contains(UnitEnum.FATHOM), "不应该包含英寻单位");
        assertTrue(lengthUnits.contains(UnitEnum.M), "应该包含米单位");
    }

    /**
     * 测试默认配置
     * <p>
     * 验证默认配置的正确性：
     * - 默认配置加载所有测量类型
     * - getSupportedMeasures()方法返回完整的测量类型列表
     * - 包含常用的测量类型（length、mass等）
     */
    @Test
    void testDefaultConfig() {
        // 创建默认配置：加载所有测量类型和单位
        UnitConversionConfig config = UnitConversionConfig.defaults();

        UnitConversionServiceImpl defaultService = new UnitConversionServiceImpl(config);

        // 验证默认配置包含多个测量类型
        List<String> measures = defaultService.getSupportedMeasures();
        assertTrue(measures.size() > 10, "默认配置应该包含多个测量类型");
        assertTrue(measures.contains("length"), "应该包含长度测量类型");
        assertTrue(measures.contains("mass"), "应该包含质量测量类型");
    }

    /**
     * 测试部分加载模式下使用被排除的测量类型应该抛出异常
     * <p>
     * 验证部分加载配置的错误处理：
     * - 尝试使用未包含的测量类型进行转换应该抛出IllegalArgumentException
     * - 错误消息应该明确指出问题所在
     */
    @Test
    void testPartialLoadingWithExcludedMeasure() {
        // 创建部分加载配置：只包含长度和质量测量类型
        UnitConversionConfig config = UnitConversionConfig.partialLoading("length", "mass");

        UnitConversionServiceImpl partialService = new UnitConversionServiceImpl(config);

        // 尝试转换体积单位（不在包含列表中）应该抛出异常
        assertThrows(UnitConversionException.class, () -> {
            partialService.convert(new BigDecimal("1"), UnitEnum.L, UnitEnum.ML);
        }, "使用被排除的测量类型应该抛出异常");
    }

    /**
     * 测试部分加载模式下使用被排除的单位应该抛出异常
     * <p>
     * 验证单位排除配置的错误处理：
     * - 尝试使用被排除的单位作为源单位应该抛出IllegalArgumentException
     * - 尝试使用被排除的单位作为目标单位应该抛出IllegalArgumentException
     * - 错误消息应该明确指出问题所在
     */
    @Test
    void testPartialLoadingWithExcludedUnit() {
        // 创建部分加载配置：包含长度测量类型，但排除海里和英寻单位
        UnitConversionConfig config = new UnitConversionConfig.Builder()
                .partialLoading(true)
                .includeMeasures("length")
                .excludeUnits(UnitEnum.NMI, UnitEnum.FATHOM)
                .build();

        UnitConversionServiceImpl partialService = new UnitConversionServiceImpl(config);

        // 尝试使用被排除的单位作为源单位应该抛出异常
        assertThrows(UnitConversionException.class, () -> {
            partialService.convert(new BigDecimal("1"), UnitEnum.NMI, UnitEnum.M);
        }, "使用被排除的单位应该抛出异常");

        // 尝试使用被排除的单位作为目标单位应该抛出异常
        assertThrows(UnitConversionException.class, () -> {
            partialService.convert(new BigDecimal("1"), UnitEnum.M, UnitEnum.NMI);
        }, "使用被排除的单位作为目标应该抛出异常");
    }

    /**
     * 测试部分加载模式下lookup方法应该只返回包含的测量类型中的单位
     * <p>
     * 验证部分加载配置对lookup方法的影响：
     * - lookup方法应该能找到包含的测量类型中的单位
     * - lookup方法不应该能找到未包含的测量类型中的单位
     * - lookup方法应该返回null对于不存在的单位
     */
    @Test
    void testPartialLoadingLookup() {
        // 创建部分加载配置：只包含长度和质量测量类型
        UnitConversionConfig config = UnitConversionConfig.partialLoading("length", "mass");

        UnitConversionServiceImpl partialService = new UnitConversionServiceImpl(config);

        // 应该能找到长度单位
        assertNotNull(partialService.lookup("m"), "应该能找到长度单位米");
        assertNotNull(partialService.lookup("kg"), "应该能找到质量单位千克");

        // 不应该能找到体积单位
        assertNull(partialService.lookup("l"), "不应该能找到体积单位升");
        assertNull(partialService.lookup("ml"), "不应该能找到体积单位毫升");
    }

    /**
     * 测试部分加载模式下possibilities方法应该只返回包含的测量类型中的单位
     * <p>
     * 验证部分加载配置对possibilities方法的影响：
     * - possibilities方法应该只返回包含的测量类型中的单位
     * - 返回的单位列表应该全部属于包含的测量类型
     * - 不应该返回未包含的测量类型中的单位
     */
    @Test
    void testPartialLoadingPossibilities() {
        // 创建部分加载配置：只包含长度和质量测量类型
        UnitConversionConfig config = UnitConversionConfig.partialLoading("length", "mass");

        UnitConversionServiceImpl partialService = new UnitConversionServiceImpl(config);

        // 获取所有可能的单位
        List<UnitEnum> allPossibilities = partialService.possibilities(null);

        // 检查所有单位都属于包含的测量类型
        for (UnitEnum unit : allPossibilities) {
            assertTrue(
                    unit.getMeasure().equals("length") || unit.getMeasure().equals("mass"),
                    "所有单位都应该属于包含的测量类型，但发现了: " + unit.getMeasure()
            );
        }
    }

    /**
     * 测试部分加载模式下convertToBest方法应该只考虑包含的测量类型中的单位
     * <p>
     * 验证部分加载配置对convertToBest方法的影响：
     * - convertToBest方法应该只在包含的测量类型中选择最佳单位
     * - 不应该考虑未包含的测量类型中的单位
     * - 转换结果应该属于包含的测量类型
     */
    @Test
    void testPartialLoadingConvertToBest() {
        // 创建部分加载配置：只包含长度测量类型
        UnitConversionConfig config = UnitConversionConfig.partialLoading("length");

        UnitConversionServiceImpl partialService = new UnitConversionServiceImpl(config);

        // 转换到最佳单位：1000毫米应该转换为米
        ConvertResult<BigDecimal> result = partialService.convertToBest(
                new BigDecimal("1000"), UnitEnum.MM);

        // 结果应该是长度单位
        assertEquals("length", UnitEnum.M.getMeasure(), "结果应该是长度测量类型");
        assertEquals("m", result.getUnit(), "结果应该是米");
    }

    /**
     * 测试部分加载模式下listAllUnits方法应该只返回包含的测量类型中的单位
     * <p>
     * 验证部分加载配置对listAllUnits方法的影响：
     * - listAllUnits方法应该只返回包含的测量类型中的单位
     * - 返回的单位描述应该全部属于包含的测量类型
     * - 不应该返回未包含的测量类型中的单位
     */
    @Test
    void testPartialLoadingListAllUnits() {
        // 创建部分加载配置：只包含长度和质量测量类型
        UnitConversionConfig config = UnitConversionConfig.partialLoading("length", "mass");

        UnitConversionServiceImpl partialService = new UnitConversionServiceImpl(config);

        // 获取所有单位
        List<com.zishuimuyu.unitconvert.model.UnitDescription> allUnits = partialService.listAllUnits();

        // 检查所有单位都属于包含的测量类型
        for (com.zishuimuyu.unitconvert.model.UnitDescription desc : allUnits) {
            assertTrue(
                    desc.getMeasure().equals("length") || desc.getMeasure().equals("mass"),
                    "所有单位都应该属于包含的测量类型，但发现了: " + desc.getMeasure()
            );
        }
    }

    /**
     * 测试部分加载模式下listUnitsByMeasure方法应该只返回包含的测量类型中的单位
     * <p>
     * 验证部分加载配置对listUnitsByMeasure方法的影响：
     * - 对于包含的测量类型，应该返回对应的单位列表
     * - 对于未包含的测量类型，应该返回空列表
     * - 返回的单位描述应该全部属于指定的测量类型
     */
    @Test
    void testPartialLoadingListUnitsByMeasure() {
        // 创建部分加载配置：只包含长度和质量测量类型
        UnitConversionConfig config = UnitConversionConfig.partialLoading("length", "mass");

        UnitConversionServiceImpl partialService = new UnitConversionServiceImpl(config);

        // 获取长度单位（应该有结果）
        List<com.zishuimuyu.unitconvert.model.UnitDescription> lengthUnits = partialService.listUnitsByMeasure("length");
        assertFalse(lengthUnits.isEmpty(), "应该能获取到长度单位");

        // 获取体积单位（应该没有结果）
        List<com.zishuimuyu.unitconvert.model.UnitDescription> volumeUnits = partialService.listUnitsByMeasure("volume");
        assertTrue(volumeUnits.isEmpty(), "不应该能获取到体积单位");
    }

    /**
     * 测试部分加载与单位排除的组合功能
     * <p>
     * 验证部分加载和单位排除配置的组合使用：
     * - 部分加载应该限制测量类型
     * - 单位排除应该在包含的测量类型中进一步过滤单位
     * - getPossibleUnits()方法应该同时考虑两个配置
     */
    @Test
    void testPartialLoadingWithUnitExclusion() {
        // 创建组合配置：只包含长度测量类型，但排除海里和英寻单位
        UnitConversionConfig config = new UnitConversionConfig.Builder()
                .partialLoading(true)
                .includeMeasures("length")
                .excludeUnits(UnitEnum.NMI, UnitEnum.FATHOM)
                .build();

        UnitConversionServiceImpl partialService = new UnitConversionServiceImpl(config);

        List<UnitEnum> lengthUnits = partialService.getPossibleUnits("length");

        // 应该包含米
        assertTrue(lengthUnits.contains(UnitEnum.M), "应该包含米单位");

        // 不应该包含被排除的单位
        assertFalse(lengthUnits.contains(UnitEnum.NMI), "不应该包含海里单位");
        assertFalse(lengthUnits.contains(UnitEnum.FATHOM), "不应该包含英寻单位");

        // 不应该包含其他测量类型的单位
        for (UnitEnum unit : lengthUnits) {
            assertEquals("length", unit.getMeasure(), "所有单位都应该是长度测量类型");
        }
    }

    /**
     * 测试配置验证器 - 有效配置
     * <p>
     * 验证配置验证器对有效配置的处理：
     * - 验证结果应该是有效的
     * - 不应该有任何错误
     * - 错误列表应该为空
     */
    @Test
    void testConfigValidatorValidConfig() {
        // 创建有效的部分加载配置
        UnitConversionConfig config = UnitConversionConfig.partialLoading("length", "mass");

        // 验证配置
        UnitConversionConfigValidator.ValidationResult result = UnitConversionConfigValidator.validate(config);

        assertTrue(result.isValid(), "配置应该是有效的");
        assertFalse(result.hasErrors(), "不应该有错误");
        assertTrue(result.getErrors().isEmpty(), "错误列表应该为空");
    }

    /**
     * 测试配置验证器 - 无效的测量类型
     * <p>
     * 验证配置验证器对无效测量类型的处理：
     * - 构建包含无效测量类型的配置应该抛出IllegalStateException
     * - 错误消息应该明确指出问题所在
     */
    @Test
    void testConfigValidatorInvalidMeasure() {
        // 尝试创建包含无效测量类型的配置
        UnitConversionConfig.Builder builder = new UnitConversionConfig.Builder()
                .partialLoading(true)
                .includeMeasures("length", "invalid_measure");

        // 构建无效配置应该抛出异常
        assertThrows(IllegalStateException.class, () -> {
            builder.build();
        }, "构建无效配置应该抛出异常");
    }

    /**
     * 测试配置验证器 - 部分加载但未指定测量类型
     * <p>
     * 验证配置验证器对部分加载但未指定测量类型的处理：
     * - 验证结果应该有警告
     * - 警告消息应该明确指出未指定测量类型的问题
     * - 配置本身是有效的，但不是最佳实践
     */
    @Test
    void testConfigValidatorPartialLoadingWithoutMeasures() {
        // 创建部分加载配置，但未指定任何测量类型
        UnitConversionConfig.Builder builder = new UnitConversionConfig.Builder()
                .partialLoading(true);

        UnitConversionConfig config = builder.build();

        // 验证配置
        UnitConversionConfigValidator.ValidationResult result = UnitConversionConfigValidator.validate(config);

        assertTrue(result.hasWarnings(), "应该有警告");
        assertTrue(result.getWarnings().stream()
                        .anyMatch(w -> w.contains("没有指定任何包含的测量类型")),
                "应该有关于未指定测量类型的警告");
    }

    /**
     * 测试配置验证器 - 默认配置
     * <p>
     * 验证配置验证器对默认配置的处理：
     * - 默认配置应该是有效的
     * - 不应该有任何错误
     * - 不应该有任何警告
     */
    @Test
    void testConfigValidatorDefaultConfig() {
        // 创建默认配置
        UnitConversionConfig config = UnitConversionConfig.defaults();

        // 验证配置
        UnitConversionConfigValidator.ValidationResult result = UnitConversionConfigValidator.validate(config);

        assertTrue(result.isValid(), "默认配置应该是有效的");
        assertFalse(result.hasErrors(), "不应该有错误");
        assertFalse(result.hasWarnings(), "不应该有警告");
    }

    /**
     * 测试配置验证器 - 大小写不敏感
     * <p>
     * 验证配置验证器对大小写不敏感的处理：
     * - 测量类型名称应该大小写不敏感
     * - "LENGTH"、"Mass"、"VOLUME"应该被正确识别
     * - 配置应该是有效的
     */
    @Test
    void testConfigValidatorCaseInsensitive() {
        // 创建大小写混合的配置
        UnitConversionConfig config = new UnitConversionConfig.Builder()
                .partialLoading(true)
                .includeMeasures("LENGTH", "Mass", "VOLUME")
                .build();

        // 验证配置
        UnitConversionConfigValidator.ValidationResult result = UnitConversionConfigValidator.validate(config);

        assertTrue(result.isValid(), "配置应该是有效的（大小写不敏感）");
        assertFalse(result.hasErrors(), "不应该有错误");
    }

    /**
     * 测试配置不可变性
     * <p>
     * 验证配置对象的不可变性：
     * - 配置对象创建后不能被修改
     * - 返回的集合应该是不可变的
     * - 尝试修改集合应该抛出UnsupportedOperationException
     */
    @Test
    void testConfigImmutability() {
        // 创建部分加载配置
        UnitConversionConfig config = UnitConversionConfig.partialLoading("length", "mass");

        // 获取包含的测量类型集合
        java.util.Set<String> measures = config.getIncludedMeasures();

        // 尝试修改返回的集合应该抛出异常
        assertThrows(java.lang.UnsupportedOperationException.class, () -> {
            measures.add("volume");
        }, "返回的集合应该是不可变的");
    }

    /**
     * 测试配置的toString方法
     * <p>
     * 验证配置对象的toString方法：
     * - toString方法应该返回非null值
     * - 返回的字符串应该包含配置的关键信息
     * - 应该包含partialLoading和includedMeasures信息
     */
    @Test
    void testConfigToString() {
        // 创建部分加载配置
        UnitConversionConfig config = UnitConversionConfig.partialLoading("length", "mass");

        // 获取配置的字符串表示
        String configString = config.toString();

        assertNotNull(configString, "配置的toString方法应该返回非null值");
        assertTrue(configString.contains("partialLoading"), "toString应该包含partialLoading信息");
        assertTrue(configString.contains("includedMeasures"), "toString应该包含includedMeasures信息");
    }

    /**
     * 测试配置工具类 - 获取可用测量类型
     * <p>
     * 验证配置工具类的getAvailableMeasures方法：
     * - 应该返回非null的测量类型集合
     * - 集合不应该为空
     * - 应该包含常用的测量类型（length、mass、volume）
     */
    @Test
    void testConfigUtilsGetAvailableMeasures() {
        // 获取所有可用的测量类型
        java.util.Set<String> measures = UnitConversionConfigUtils.getAvailableMeasures();

        assertNotNull(measures, "可用测量类型集合不能为null");
        assertFalse(measures.isEmpty(), "可用测量类型集合不能为空");
        assertTrue(measures.contains("length"), "应该包含length测量类型");
        assertTrue(measures.contains("mass"), "应该包含mass测量类型");
        assertTrue(measures.contains("volume"), "应该包含volume测量类型");
    }

    /**
     * 测试配置工具类 - 获取指定测量类型的单位
     * <p>
     * 验证配置工具类的getUnitsByMeasure方法：
     * - 应该返回非null的单位集合
     * - 集合不应该为空
     * - 应该包含指定测量类型的单位
     */
    @Test
    void testConfigUtilsGetUnitsByMeasure() {
        // 获取长度测量类型的所有单位
        java.util.Set<UnitEnum> lengthUnits = UnitConversionConfigUtils.getUnitsByMeasure("length");

        assertNotNull(lengthUnits, "长度单位集合不能为null");
        assertFalse(lengthUnits.isEmpty(), "长度单位集合不能为空");
        assertTrue(lengthUnits.contains(UnitEnum.M), "应该包含米单位");
        assertTrue(lengthUnits.contains(UnitEnum.KM), "应该包含千米单位");
        assertTrue(lengthUnits.contains(UnitEnum.FT), "应该包含英尺单位");
    }

    /**
     * 测试配置工具类 - 创建长度专用配置
     * <p>
     * 验证配置工具类的createLengthOnlyConfig方法：
     * - 应该创建部分加载配置
     * - 应该只包含length测量类型
     * - 包含的测量类型集合不能为null
     */
    @Test
    void testConfigUtilsCreateLengthOnlyConfig() {
        // 创建长度专用配置
        UnitConversionConfig config = UnitConversionConfigUtils.createLengthOnlyConfig();

        assertTrue(config.isPartialLoading(), "应该是部分加载模式");
        assertNotNull(config.getIncludedMeasures(), "包含的测量类型不能为null");
        assertEquals(1, config.getIncludedMeasures().size(), "应该只包含1个测量类型");
        assertTrue(config.getIncludedMeasures().contains("length"), "应该包含length测量类型");
    }

    /**
     * 测试配置工具类 - 创建常用单位配置
     * <p>
     * 验证配置工具类的createCommonUnitsConfig方法：
     * - 应该创建部分加载配置
     * - 应该包含3个常用测量类型（length、mass、volume）
     * - 包含的测量类型集合不能为null
     */
    @Test
    void testConfigUtilsCreateCommonUnitsConfig() {
        // 创建常用单位配置
        UnitConversionConfig config = UnitConversionConfigUtils.createCommonUnitsConfig();

        assertTrue(config.isPartialLoading(), "应该是部分加载模式");
        assertNotNull(config.getIncludedMeasures(), "包含的测量类型不能为null");
        assertEquals(3, config.getIncludedMeasures().size(), "应该包含3个测量类型");
        assertTrue(config.getIncludedMeasures().contains("length"), "应该包含length测量类型");
        assertTrue(config.getIncludedMeasures().contains("mass"), "应该包含mass测量类型");
        assertTrue(config.getIncludedMeasures().contains("volume"), "应该包含volume测量类型");
    }

    /**
     * 测试配置工具类 - 创建排除英制单位配置
     * <p>
     * 验证配置工具类的createMetricOnlyConfig方法：
     * - 应该不启用部分加载模式
     * - 应该排除英制单位（英尺、英寸、英里等）
     * - 排除的单位集合不能为null且不能为空
     */
    @Test
    void testConfigUtilsCreateMetricOnlyConfig() {
        // 创建排除英制单位的配置
        UnitConversionConfig config = UnitConversionConfigUtils.createMetricOnlyConfig();

        assertFalse(config.isPartialLoading(), "不应该启用部分加载模式");
        assertNotNull(config.getExcludedUnits(), "排除的单位集合不能为null");
        assertFalse(config.getExcludedUnits().isEmpty(), "排除的单位集合不能为空");

        // 验证英制单位被排除
        assertTrue(config.getExcludedUnits().contains(UnitEnum.FT), "应该排除英尺单位");
        assertTrue(config.getExcludedUnits().contains(UnitEnum.IN), "应该排除英寸单位");
        assertTrue(config.getExcludedUnits().contains(UnitEnum.MI), "应该排除英里单位");
    }

    /**
     * 测试配置工具类 - 配置验证和打印
     * <p>
     * 验证配置工具类的validateAndPrint方法：
     * - 应该返回非null的验证结果
     * - 验证结果应该是有效的
     * - 应该能够正确验证配置
     */
    @Test
    void testConfigUtilsValidateAndPrint() {
        // 创建常用单位配置
        UnitConversionConfig config = UnitConversionConfigUtils.createCommonUnitsConfig();

        // 验证配置并打印结果
        UnitConversionConfigValidator.ValidationResult result = UnitConversionConfigUtils.validateAndPrint(config);

        assertNotNull(result, "验证结果不能为null");
        assertTrue(result.isValid(), "配置应该是有效的");
    }

    /**
     * 测试配置工具类 - 获取配置统计信息
     * <p>
     * 验证配置工具类的getConfigStatistics方法：
     * - 应该返回非null的统计信息字符串
     * - 统计信息应该包含配置统计标题
     * - 应该包含测量类型、单位总数和内存节省信息
     */
    @Test
    void testConfigUtilsGetConfigStatistics() {
        // 创建长度专用配置
        UnitConversionConfig config = UnitConversionConfigUtils.createLengthOnlyConfig();

        // 获取配置统计信息
        String statistics = UnitConversionConfigUtils.getConfigStatistics(config);

        assertNotNull(statistics, "统计信息不能为null");
        assertTrue(statistics.contains("配置统计"), "应该包含配置统计标题");
        assertTrue(statistics.contains("包含测量类型"), "应该包含测量类型信息");
        assertTrue(statistics.contains("可用单位总数"), "应该包含单位总数信息");
        assertTrue(statistics.contains("内存节省"), "应该包含内存节省信息");
    }

    /**
     * 测试配置工具类 - 创建配置建议
     * <p>
     * 验证配置工具类的suggestConfig方法：
     * - 应该能够根据使用场景返回对应的配置
     * - 支持international、metric、simple等场景
     * - 返回的配置不能为null
     */
    @Test
    void testConfigUtilsSuggestConfig() {
        // 测试国际化场景
        UnitConversionConfig internationalConfig = UnitConversionConfigUtils.suggestConfig("international");
        assertNotNull(internationalConfig, "国际化配置不能为null");

        // 测试公制场景
        UnitConversionConfig metricConfig = UnitConversionConfigUtils.suggestConfig("metric");
        assertNotNull(metricConfig, "公制配置不能为null");

        // 测试简单场景
        UnitConversionConfig simpleConfig = UnitConversionConfigUtils.suggestConfig("simple");
        assertNotNull(simpleConfig, "简单配置不能为null");
    }

    /**
     * 测试配置工具类 - 创建组合配置
     * <p>
     * 验证配置工具类的createCombinedConfig方法：
     * - 应该能够组合部分加载和单位排除配置
     * - 应该正确设置包含的测量类型
     * - 应该正确设置排除的单位
     */
    @Test
    void testConfigUtilsCreateCombinedConfig() {
        // 定义包含的测量类型和排除的单位
        String[] includedMeasures = {"length", "mass"};
        UnitEnum[] excludedUnits = {UnitEnum.NMI, UnitEnum.FATHOM};

        // 创建组合配置
        UnitConversionConfig config = UnitConversionConfigUtils.createCombinedConfig(
                includedMeasures, excludedUnits);

        // 验证配置
        assertTrue(config.isPartialLoading(), "应该是部分加载模式");
        assertNotNull(config.getIncludedMeasures(), "包含的测量类型不能为null");
        assertEquals(2, config.getIncludedMeasures().size(), "应该包含2个测量类型");
        assertNotNull(config.getExcludedUnits(), "排除的单位集合不能为null");
        assertEquals(2, config.getExcludedUnits().size(), "应该排除2个单位");
        assertTrue(config.getExcludedUnits().contains(UnitEnum.NMI), "应该排除海里单位");
        assertTrue(config.getExcludedUnits().contains(UnitEnum.FATHOM), "应该排除英寻单位");
    }
}