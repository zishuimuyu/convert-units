package com.zishuimuyu.unitconvert;

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
 * 
 * 验证单位转换服务的各项功能是否正常工作
 * 
 * @author 紫水木鱼
 * @version 1.0.0
 * @since 2026-04-24
 */
class UnitConversionServiceTest {

    private final UnitConversionServiceImpl conversionService = new UnitConversionServiceImpl();

    /**
     * 测试长度单位转换功能
     * 验证米到厘米的转换是否正确
     */
    @Test
    void testLengthConversion() {
        // 测试米到厘米的转换
        ConvertResult<BigDecimal> result = conversionService.convert(new BigDecimal("1"), UnitEnum.M, UnitEnum.CM);
        assertEquals(0, new BigDecimal("100").compareTo(result.getVal()), "1米应该等于100厘米");
        assertEquals("cm", result.getUnit(), "目标单位应该是厘米");

        // 测试厘米到米的转换
        result = conversionService.convert(new BigDecimal("100"), UnitEnum.CM, UnitEnum.M);
        assertEquals(0, new BigDecimal("1").compareTo(result.getVal()), "100厘米应该等于1米");
        
        // 测试新增的长度单位
        result = conversionService.convert(new BigDecimal("1000"), UnitEnum.MM, UnitEnum.M);
        assertEquals(0, new BigDecimal("1").compareTo(result.getVal()), "1000毫米应该等于1米");
        
        result = conversionService.convert(new BigDecimal("1"), UnitEnum.KM, UnitEnum.M);
        assertEquals(0, new BigDecimal("1000").compareTo(result.getVal()), "1千米应该等于1000米");
    }

    /**
     * 测试质量单位转换功能
     * 验证千克到克的转换是否正确
     */
    @Test
    void testMassConversion() {
        // 测试千克到克的转换
        ConvertResult<BigDecimal> result = conversionService.convert(new BigDecimal("1"), UnitEnum.KG, UnitEnum.G);
        assertEquals(0, new BigDecimal("1000").compareTo(result.getVal()), "1千克应该等于1000克");

        // 测试克到千克的转换
        result = conversionService.convert(new BigDecimal("1000"), UnitEnum.G, UnitEnum.KG);
        assertEquals(0, new BigDecimal("1").compareTo(result.getVal()), "1000克应该等于1千克");
        
        // 测试新增的质量单位
        result = conversionService.convert(new BigDecimal("1000"), UnitEnum.MG, UnitEnum.G);
        assertEquals(0, new BigDecimal("1").compareTo(result.getVal()), "1000毫克应该等于1克");
    }

    /**
     * 测试温度单位转换功能
     * 验证摄氏度到华氏度的转换是否正确
     */
    @Test
    void testTemperatureConversion() {
        // 测试摄氏度到华氏度的转换 (0°C = 32°F)
        ConvertResult<BigDecimal> result = conversionService.convert(new BigDecimal("0"), UnitEnum.C, UnitEnum.F);
        assertEquals(0, new BigDecimal("32").compareTo(result.getVal()), "0摄氏度应该等于32华氏度");

        // 测试华氏度到摄氏度的转换 (32°F = 0°C)
        result = conversionService.convert(new BigDecimal("32"), UnitEnum.F, UnitEnum.C);
        assertEquals(0, new BigDecimal("0").compareTo(result.getVal()), "32华氏度应该等于0摄氏度");

        // 测试摄氏度到开尔文的转换 (0°C = 273.15K)
        result = conversionService.convert(new BigDecimal("0"), UnitEnum.C, UnitEnum.K);
        assertEquals(0, new BigDecimal("273.15").compareTo(result.getVal()), "0摄氏度应该等于273.15开尔文");
        
        // 测试兰金温标转换
        // 0°C = 32°F = 491.67°R
        result = conversionService.convert(new BigDecimal("0"), UnitEnum.C, UnitEnum.R);
        BigDecimal expectedRankine = new BigDecimal("491.67");
        assertTrue(expectedRankine.subtract(result.getVal()).abs().compareTo(new BigDecimal("0.01")) < 0, 
            "0°C应该等于491.67°R，实际值：" + result.getVal());
        
        // 32°F = 491.67°R
        result = conversionService.convert(new BigDecimal("32"), UnitEnum.F, UnitEnum.R);
        assertTrue(expectedRankine.subtract(result.getVal()).abs().compareTo(new BigDecimal("0.01")) < 0, 
            "32°F应该等于491.67°R，实际值：" + result.getVal());
        
        // 491.67°R = 32°F
        result = conversionService.convert(new BigDecimal("491.67"), UnitEnum.R, UnitEnum.F);
        BigDecimal expectedFahrenheit = new BigDecimal("32");
        assertTrue(expectedFahrenheit.subtract(result.getVal()).abs().compareTo(new BigDecimal("0.01")) < 0, 
            "491.67°R应该等于32°F，实际值：" + result.getVal());
        
        // 测试新增的单位
        // 测试公制马力转换
        result = conversionService.convert(new BigDecimal("1"), UnitEnum.PS, UnitEnum.W);
        BigDecimal expectedPS = new BigDecimal("735.49875");
        assertTrue(expectedPS.subtract(result.getVal()).abs().compareTo(new BigDecimal("0.01")) < 0, 
            "1 PS应该等于735.49875 W，实际值：" + result.getVal());
        
        // 测试压力单位转换
        result = conversionService.convert(new BigDecimal("1"), UnitEnum.BAR, UnitEnum.PSI);
        BigDecimal expectedBarToPsi = new BigDecimal("14.5038"); // 1 bar ≈ 14.5038 psi
        assertTrue(expectedBarToPsi.subtract(result.getVal()).abs().compareTo(new BigDecimal("0.1")) < 0, 
            "1 bar应该约等于14.5038 psi，实际值：" + result.getVal());
        
        // 测试速度单位转换
        result = conversionService.convert(new BigDecimal("1"), UnitEnum.KM_H, UnitEnum.MPH);
        BigDecimal expectedKmhToMph = new BigDecimal("0.621371"); // 1 km/h ≈ 0.621371 mph
        assertTrue(expectedKmhToMph.subtract(result.getVal()).abs().compareTo(new BigDecimal("0.01")) < 0, 
            "1 km/h应该约等于0.621371 mph，实际值：" + result.getVal());
        
        // 测试时间单位转换
        result = conversionService.convert(new BigDecimal("1"), UnitEnum.US, UnitEnum.MU);
        assertEquals(0, new BigDecimal("1").compareTo(result.getVal()), "1 μs应该等于1 mu");
    }

    /**
     * 测试获取最佳单位功能
     */
    @Test
    void testConvertToBest() {
        // 测试长度单位转换到最佳表示
        ConvertResult<BigDecimal> result = conversionService.convertToBest(new BigDecimal("1000"), UnitEnum.MM);
        assertEquals("m", result.getUnit(), "1000毫米应该转换为米");
        assertEquals(0, new BigDecimal("1").compareTo(result.getVal()), "1000毫米应该等于1米");
        
        // 测试小数值的最佳单位选择
        result = conversionService.convertToBest(new BigDecimal("0.001"), UnitEnum.M);
        assertEquals("mm", result.getUnit(), "0.001米应该转换为毫米");
        assertEquals(0, new BigDecimal("1").compareTo(result.getVal()), "0.001米应该等于1毫米");
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
     */
    @Test
    void testChainedConversion() {
        // 测试链式调用: convert(1).from(M).to(CM)
        ConvertResult<BigDecimal> result = conversionService.from(new BigDecimal("1"), UnitEnum.M).to(UnitEnum.CM);
        assertEquals(0, new BigDecimal("100").compareTo(result.getVal()), "1米应该等于100厘米");
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
     */
    @Test
    void testConvertToBestWithOptions() {
        // 测试排除某些单位的选项
        List<UnitEnum> excludeUnits = Arrays.asList(UnitEnum.MM, UnitEnum.CM);
        ToBestOptions options = ToBestOptions.withExclude(excludeUnits);
        
        // 1000毫米，但排除毫米和厘米，应该得到米
        ConvertResult<BigDecimal> result = conversionService.convertToBest(
            new BigDecimal("1000"), UnitEnum.MM, options);
        assertEquals("m", result.getUnit(), "应该跳过被排除的单位，选择米");
        assertEquals(0, new BigDecimal("1").compareTo(result.getVal()), "1000毫米应该等于1米");
        
        // 测试自定义截止数值
        ToBestOptions customCutoffOptions = ToBestOptions.withCutOffNumber(100.0);
        result = conversionService.convertToBest(
            new BigDecimal("10"), UnitEnum.M, customCutoffOptions);
        // 10米应该保持为米，因为小于截止值100
        assertTrue(result.getVal().compareTo(new BigDecimal("100")) <= 0, 
            "转换结果应该小于截止值100");
        
        // 测试限制单位系统
        ToBestOptions systemOptions = ToBestOptions.withSystem("metric");
        result = conversionService.convertToBest(
            new BigDecimal("1000"), UnitEnum.MM, systemOptions);
        // 应该只在公制系统内选择，不会选择英制单位
        assertTrue(result.getUnit().equals("m") || result.getUnit().equals("cm") || result.getUnit().equals("mm"),
            "应该只返回公制单位");
    }

    /**
     * 测试链式调用的操作顺序错误处理
     */
    @Test
    void testOperationOrderError() {
        // 先调用to再调用from应该抛出异常
        assertThrows(IllegalStateException.class, () -> {
            conversionService.to(UnitEnum.CM); // 没有先调用from，应该抛出异常
        }, "应该在没有调用from之前调用to时抛出异常");
        
        // 正常流程：from -> to
        ConvertResult<BigDecimal> result = conversionService
            .from(new BigDecimal("1"), UnitEnum.M)
            .to(UnitEnum.CM);
        assertEquals(0, new BigDecimal("100").compareTo(result.getVal()), "1米应该等于100厘米");
    }

    /**
     * 测试体积单位转换功能
     * 验证升到毫升的转换是否正确
     */
    @Test
    void testVolumeConversion() {
        // 测试升到毫升的转换
        ConvertResult<BigDecimal> result = conversionService.convert(new BigDecimal("1"), UnitEnum.L, UnitEnum.ML);
        assertEquals(0, new BigDecimal("1000").compareTo(result.getVal()), "1升应该等于1000毫升");

        // 测试毫升到升的转换
        result = conversionService.convert(new BigDecimal("1000"), UnitEnum.ML, UnitEnum.L);
        assertEquals(0, new BigDecimal("1").compareTo(result.getVal()), "1000毫升应该等于1升");
        
        // 测试新增的体积单位
        result = conversionService.convert(new BigDecimal("1"), UnitEnum.M3, UnitEnum.L);
        assertEquals(0, new BigDecimal("1000").compareTo(result.getVal()), "1立方米应该等于1000升");
    }

    /**
     * 测试时间单位转换功能
     * 验证小时到分钟的转换是否正确
     */
    @Test
    void testTimeConversion() {
        // 测试小时到分钟的转换
        ConvertResult<BigDecimal> result = conversionService.convert(new BigDecimal("1"), UnitEnum.H, UnitEnum.MIN);
        assertEquals(0, new BigDecimal("60").compareTo(result.getVal()), "1小时应该等于60分钟");

        // 测试分钟到秒的转换
        result = conversionService.convert(new BigDecimal("1"), UnitEnum.MIN, UnitEnum.S);
        assertEquals(0, new BigDecimal("60").compareTo(result.getVal()), "1分钟应该等于60秒");
        
        // 测试新增的时间单位
        result = conversionService.convert(new BigDecimal("1"), UnitEnum.D, UnitEnum.H);
        assertEquals(0, new BigDecimal("24").compareTo(result.getVal()), "1天应该等于24小时");
    }

    /**
     * 测试面积单位转换功能
     */
    @Test
    void testAreaConversion() {
        // 测试平方米到平方厘米的转换
        ConvertResult<BigDecimal> result = conversionService.convert(new BigDecimal("1"), UnitEnum.M2, UnitEnum.CM2);
        assertEquals(0, new BigDecimal("10000").compareTo(result.getVal()), "1平方米应该等于10000平方厘米");
        
        // 测试平方千米到平方米的转换
        result = conversionService.convert(new BigDecimal("1"), UnitEnum.KM2, UnitEnum.M2);
        assertEquals(0, new BigDecimal("1000000").compareTo(result.getVal()), "1平方千米应该等于1000000平方米");
    }

    /**
     * 测试速度单位转换功能
     */
    @Test
    void testSpeedConversion() {
        // 测试米/秒到千米/小时的转换
        ConvertResult<BigDecimal> result = conversionService.convert(new BigDecimal("1"), UnitEnum.M_S, UnitEnum.KM_H);
        // 1 m/s = 3.6 km/h
        assertEquals(0, new BigDecimal("3.6").compareTo(result.getVal().setScale(1, RoundingMode.HALF_UP)), "1米/秒应该等于3.6千米/小时");
    }

    /**
     * 测试功率单位转换功能
     */
    @Test
    void testPowerConversion() {
        // 测试瓦特到千瓦的转换
        ConvertResult<BigDecimal> result = conversionService.convert(new BigDecimal("1000"), UnitEnum.W, UnitEnum.KW);
        assertEquals(0, new BigDecimal("1").compareTo(result.getVal()), "1000瓦应该等于1千瓦");
    }

    /**
     * 测试压力单位转换功能
     */
    @Test
    void testPressureConversion() {
        // 测试帕斯卡到千帕的转换
        ConvertResult<BigDecimal> result = conversionService.convert(new BigDecimal("1000"), UnitEnum.PA, UnitEnum.KPA);
        assertEquals(0, new BigDecimal("1").compareTo(result.getVal()), "1000帕应该等于1千帕");
    }

    /**
     * 测试数字存储单位转换功能
     */
    @Test
    void testDigitalConversion() {
        // 测试字节到千字节的转换
        ConvertResult<BigDecimal> result = conversionService.convert(new BigDecimal("1000"), UnitEnum.BYTE, UnitEnum.KBYTE);
        assertEquals(0, new BigDecimal("1").compareTo(result.getVal()), "1000字节应该等于1千字节");
        
        // 测试比特到字节的转换
        result = conversionService.convert(new BigDecimal("8"), UnitEnum.BIT, UnitEnum.BYTE);
        assertEquals(0, new BigDecimal("1").compareTo(result.getVal()), "8比特应该等于1字节");
    }



    /**
     * 测试加速度单位转换功能
     */
    @Test
    void testAccelerationConversion() {
        // 测试g-force到m/s2的转换
        ConvertResult<BigDecimal> result = conversionService.convert(new BigDecimal("1"), UnitEnum.G_FORCE, UnitEnum.M_S2);
        assertEquals(0, new BigDecimal("9.80665").compareTo(result.getVal().setScale(5, RoundingMode.HALF_UP)), "1重力加速度应该等于9.80665米/秒²");
    }

    /**
     * 测试能量单位转换功能
     */
    @Test
    void testEnergyConversion() {
        // 测试焦耳到卡路里的转换
        ConvertResult<BigDecimal> result = conversionService.convert(new BigDecimal("1"), UnitEnum.J, UnitEnum.CAL);
        // 1焦耳 ≈ 0.239卡路里
        assertEquals(0, new BigDecimal("0.239").compareTo(result.getVal().setScale(3, RoundingMode.HALF_UP)), "1焦耳应该约等于0.239卡路里");
    }

    /**
     * 测试力单位转换功能
     */
    @Test
    void testForceConversion() {
        // 测试牛顿到磅力的转换
        ConvertResult<BigDecimal> result = conversionService.convert(new BigDecimal("1"), UnitEnum.N, UnitEnum.LBF);
        // 1牛顿 ≈ 0.2248磅力
        assertEquals(0, new BigDecimal("0.2248").compareTo(result.getVal().setScale(4, RoundingMode.HALF_UP)), "1牛顿应该约等于0.2248磅力");
    }

    /**
     * 测试扭矩单位转换功能
     */
    @Test
    void testTorqueConversion() {
        // 测试牛顿米到磅英尺的转换
        ConvertResult<BigDecimal> result = conversionService.convert(new BigDecimal("1"), UnitEnum.NM, UnitEnum.LBF_FT);
        // 1牛顿米 ≈ 0.7376磅英尺
        assertEquals(0, new BigDecimal("0.7376").compareTo(result.getVal().setScale(4, RoundingMode.HALF_UP)), "1牛顿米应该约等于0.7376磅英尺");
    }

    /**
     * 测试频率单位转换功能
     */
    @Test
    void testFrequencyConversion() {
        // 测试赫兹到千赫兹的转换
        ConvertResult<BigDecimal> result = conversionService.convert(new BigDecimal("1000"), UnitEnum.HZ, UnitEnum.KHZ);
        assertEquals(0, new BigDecimal("1").compareTo(result.getVal()), "1000赫兹应该等于1千赫兹");
    }

    /**
     * 测试照度单位转换功能
     */
    @Test
    void testIlluminanceConversion() {
        // 测试勒克斯到英尺烛光的转换
        ConvertResult<BigDecimal> result = conversionService.convert(new BigDecimal("10.764"), UnitEnum.LX, UnitEnum.FOOTCANDLE);
        assertEquals(0, new BigDecimal("1").compareTo(result.getVal().setScale(3, RoundingMode.HALF_UP)), "10.764勒克斯应该约等于1英尺烛光");
    }

    /**
     * 测试视在功率单位转换功能
     */
    @Test
    void testApparentPowerConversion() {
        // 测试伏安到千伏安的转换
        ConvertResult<BigDecimal> result = conversionService.convert(new BigDecimal("1000"), UnitEnum.VA, UnitEnum.KVA);
        assertEquals(0, new BigDecimal("1").compareTo(result.getVal()), "1000伏安应该等于1千伏安");
    }

    /**
     * 测试部分浓度单位转换功能
     */
    @Test
    void testPartsPerConversion() {
        // 测试百万分率到十亿分率的转换
        ConvertResult<BigDecimal> result = conversionService.convert(new BigDecimal("1"), UnitEnum.PPM, UnitEnum.PPB);
        assertEquals(0, new BigDecimal("1000").compareTo(result.getVal()), "1百万分率应该等于1000十亿分率");
    }

    /**
     * 测试数量单位转换功能
     */
    @Test
    void testPiecesConversion() {
        // 测试件到dozen的转换
        ConvertResult<BigDecimal> result = conversionService.convert(new BigDecimal("24"), UnitEnum.PCS_PIECES, UnitEnum.DOZ);
        assertEquals(0, new BigDecimal("2").compareTo(result.getVal()), "24件应该等于2dozen");
        
        // 测试 dozen 到 个的转换 (each类型内部转换)
         result = conversionService.convert(new BigDecimal("1"), UnitEnum.DOZEN, UnitEnum.EA);
         assertEquals(0, new BigDecimal("12").compareTo(result.getVal()), "1 dozen应该等于12个");
         
         // 测试 ea 到 dozen 的转换 (each类型内部转换)
         result = conversionService.convert(new BigDecimal("12"), UnitEnum.EA, UnitEnum.DOZEN);
         assertEquals(0, new BigDecimal("1").compareTo(result.getVal()), "12 个应该等于1 dozen");
         
         // 测试 ea 到 single 的转换 (each类型内部转换)
         result = conversionService.convert(new BigDecimal("1"), UnitEnum.EA, UnitEnum.SINGLE);
         assertEquals(0, new BigDecimal("1").compareTo(result.getVal()), "1 个(ea)应该等于1 个(single)");
    }

    /**
     * 测试无功功率单位转换功能
     */
    @Test
    void testReactivePowerConversion() {
        // 测试乏到千乏的转换
        ConvertResult<BigDecimal> result = conversionService.convert(new BigDecimal("1000"), UnitEnum.VAR, UnitEnum.KVAR);
        assertEquals(0, new BigDecimal("1").compareTo(result.getVal()), "1000乏应该等于1千乏");
    }

    /**
     * 测试无功能量单位转换功能
     */
    @Test
    void testReactiveEnergyConversion() {
        // 测试乏时到千乏时的转换
        ConvertResult<BigDecimal> result = conversionService.convert(new BigDecimal("1000"), UnitEnum.VARH, UnitEnum.KVARH);
        assertEquals(0, new BigDecimal("1").compareTo(result.getVal()), "1000乏时应该等于1千乏时");
    }

    /**
     * 测试质量流量率单位转换功能
     */
    @Test
    void testMassFlowRateConversion() {
        // 测试千克/秒到千克/小时的转换
        ConvertResult<BigDecimal> result = conversionService.convert(new BigDecimal("1"), UnitEnum.KG_PER_S, UnitEnum.KG_PER_H);
        // 由于精度问题，允许小的误差范围
        assertTrue(new BigDecimal("3600").subtract(result.getVal()).abs().compareTo(new BigDecimal("0.01")) < 0, 
            "1千克/秒应该约等于3600千克/小时，实际值：" + result.getVal());
    }

    /**
     * 测试体积流量率单位转换功能
     */
    @Test
    void testVolumeFlowRateConversion() {
        // 测试升/分钟到立方米/秒的转换
        ConvertResult<BigDecimal> result = conversionService.convert(new BigDecimal("60"), UnitEnum.L_PER_MIN, UnitEnum.M3_PER_S);
        // 由于精度问题，允许小的误差范围
        assertTrue(new BigDecimal("0.001").subtract(result.getVal()).abs().compareTo(new BigDecimal("0.0001")) < 0, 
            "60升/分钟应该约等于0.001立方米/秒，实际值：" + result.getVal());
    }

    /**
     * 测试步速单位转换功能
     */
    @Test
    void testPaceConversion() {
        // 测试分钟/公里到秒/米的转换
        ConvertResult<BigDecimal> result = conversionService.convert(new BigDecimal("1"), UnitEnum.MIN_PER_KM, UnitEnum.S_PER_M);
        // 1分钟/公里 = 0.06秒/米
        assertTrue(new BigDecimal("0.06").subtract(result.getVal()).abs().compareTo(new BigDecimal("0.0001")) < 0, 
            "1分钟/公里应该约等于0.06秒/米，实际值：" + result.getVal());
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
        assertTrue(expected.subtract(result.getVal()).abs().compareTo(new BigDecimal("0.1")) < 0, 
            "1分钟/公里应该约等于1.609分钟/英里，实际值：" + result.getVal());
    }
}