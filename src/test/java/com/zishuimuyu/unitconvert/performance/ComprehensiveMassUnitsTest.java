package com.zishuimuyu.unitconvert.performance;

import com.zishuimuyu.unitconvert.model.UnitEnum;
import com.zishuimuyu.unitconvert.service.UnitConversionServiceImpl;
import com.zishuimuyu.unitconvert.service.IUnitConversionService;
import java.math.BigDecimal;

/**
 * Comprehensive test for the newly added mass units conversion functionality
 */
public class ComprehensiveMassUnitsTest {
    public static void main(String[] args) {
        IUnitConversionService conversionService = new UnitConversionServiceImpl();

        System.out.println("=== Comprehensive Mass Units Conversion Test ===");

        // 测试中国传统单位之间的转换
        System.out.println("\n--- Chinese Traditional Units Internal Conversions ---");
        testConversion(conversionService, new BigDecimal("1"), UnitEnum.MASS_JIN, UnitEnum.MASS_LIANG);   // 1 jin = 10 liang
        testConversion(conversionService, new BigDecimal("1"), UnitEnum.MASS_LIANG, UnitEnum.MASS_QIAN);  // 1 liang = 10 qian
        testConversion(conversionService, new BigDecimal("10"), UnitEnum.MASS_QIAN, UnitEnum.MASS_LIANG); // 10 qian = 1 liang
        testConversion(conversionService, new BigDecimal("10"), UnitEnum.MASS_LIANG, UnitEnum.MASS_JIN);  // 10 liang = 1 jin
        testConversion(conversionService, new BigDecimal("1"), UnitEnum.MASS_DAN, UnitEnum.MASS_JIN);    // 1 dan = 100 jin

        // 测试中国传统单位与国际单位的转换
        System.out.println("\n--- Chinese to Metric Conversions ---");
        testConversion(conversionService, new BigDecimal("1"), UnitEnum.MASS_JIN, UnitEnum.MASS_G);      // 1 jin = 500 g
        testConversion(conversionService, new BigDecimal("1"), UnitEnum.MASS_JIN, UnitEnum.MASS_KG);     // 1 jin = 0.5 kg
        testConversion(conversionService, new BigDecimal("1"), UnitEnum.MASS_LIANG, UnitEnum.MASS_G);   // 1 liang = 50 g
        testConversion(conversionService, new BigDecimal("1"), UnitEnum.MASS_QIAN, UnitEnum.MASS_G);    // 1 qian = 5 g
        testConversion(conversionService, new BigDecimal("1"), UnitEnum.MASS_DAN, UnitEnum.MASS_KG);    // 1 dan = 50 kg

        // 测试英制单位与国际单位的转换
        System.out.println("\n--- Imperial to Metric Conversions ---");
        testConversion(conversionService, new BigDecimal("1"), UnitEnum.MASS_OZ, UnitEnum.MASS_G);      // 1 oz = 28.3495 g
        testConversion(conversionService, new BigDecimal("1"), UnitEnum.MASS_LB, UnitEnum.MASS_G);      // 1 lb = 453.592 g
        testConversion(conversionService, new BigDecimal("1"), UnitEnum.MASS_GR, UnitEnum.MASS_G);      // 1 gr = 0.0648 g
        testConversion(conversionService, new BigDecimal("1"), UnitEnum.MASS_DR, UnitEnum.MASS_G);      // 1 dr = 1.77 g

        // 测试英制单位与中国传统单位的转换
        System.out.println("\n--- Imperial to Chinese Conversions ---");
        testConversion(conversionService, new BigDecimal("1"), UnitEnum.MASS_LB, UnitEnum.MASS_JIN);    // 1 lb ≈ 2.2 jin
        testConversion(conversionService, new BigDecimal("1"), UnitEnum.MASS_OZ, UnitEnum.MASS_QIAN);   // 1 oz ≈ 5.6 qian

        // 测试所有新增单位的反向转换
        System.out.println("\n--- Reverse Conversions ---");
        testConversion(conversionService, new BigDecimal("500"), UnitEnum.MASS_G, UnitEnum.MASS_JIN);   // 500 g = 1 jin
        testConversion(conversionService, new BigDecimal("0.5"), UnitEnum.MASS_KG, UnitEnum.MASS_JIN);  // 0.5 kg = 1 jin
        testConversion(conversionService, new BigDecimal("50"), UnitEnum.MASS_G, UnitEnum.MASS_LIANG);  // 50 g = 1 liang
        testConversion(conversionService, new BigDecimal("5"), UnitEnum.MASS_G, UnitEnum.MASS_QIAN);    // 5 g = 1 qian
        testConversion(conversionService, new BigDecimal("50"), UnitEnum.MASS_KG, UnitEnum.MASS_DAN);   // 50 kg = 1 dan
    }

    private static void testConversion(IUnitConversionService service, BigDecimal value, UnitEnum from, UnitEnum to) {
        try {
            com.zishuimuyu.unitconvert.model.ConvertResult<BigDecimal> result = service.convert(value, from, to);
            System.out.printf("%s %s = %s %s\n", value.toString(), from.name(), result.getValue().setScale(6, java.math.RoundingMode.HALF_UP).toString(), to.name());
        } catch (Exception e) {
            System.out.printf("Conversion failed: %s %s to %s - %s\n", value.toString(), from.name(), to.name(), e.getMessage());
        }
    }
}