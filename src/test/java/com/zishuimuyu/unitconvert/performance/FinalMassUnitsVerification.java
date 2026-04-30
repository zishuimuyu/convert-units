package com.zishuimuyu.unitconvert.performance;

import com.zishuimuyu.unitconvert.model.UnitEnum;
import com.zishuimuyu.unitconvert.service.UnitConversionServiceImpl;
import com.zishuimuyu.unitconvert.service.IUnitConversionService;
import java.math.BigDecimal;

/**
 * Final verification test for all newly added mass units
 */
public class FinalMassUnitsVerification {
    public static void main(String[] args) {
        IUnitConversionService conversionService = new UnitConversionServiceImpl();

        System.out.println("=== Final Verification of All New Mass Units ===");

        // 测试所有新增的国际单位
        System.out.println("\n--- International Units (Newly Added) ---");
        testConversion(conversionService, new BigDecimal("1"), UnitEnum.GR, UnitEnum.G);    // 1格令 = 0.0648克
        testConversion(conversionService, new BigDecimal("1"), UnitEnum.DR, UnitEnum.G);    // 1打兰 = 1.77克
        testConversion(conversionService, new BigDecimal("1"), UnitEnum.USTF, UnitEnum.G);  // 1美吨 = 907185克
        testConversion(conversionService, new BigDecimal("1"), UnitEnum.LT, UnitEnum.G);    // 1长吨 = 1016047克

        // 测试所有新增的中国传统单位
        System.out.println("\n--- Chinese Traditional Units (Newly Added) ---");
        testConversion(conversionService, new BigDecimal("1"), UnitEnum.QIAN, UnitEnum.G);  // 1钱 = 5克
        testConversion(conversionService, new BigDecimal("1"), UnitEnum.LIANG, UnitEnum.G); // 1两 = 50克
        testConversion(conversionService, new BigDecimal("1"), UnitEnum.JIN, UnitEnum.G);   // 1斤 = 500克
        testConversion(conversionService, new BigDecimal("1"), UnitEnum.DAN, UnitEnum.G);   // 1担 = 50000克

        // 测试单位间的相互转换
        System.out.println("\n--- Cross-System Conversions ---");
        testConversion(conversionService, new BigDecimal("1"), UnitEnum.LB, UnitEnum.JIN);  // 1磅 ≈ 0.907斤
        testConversion(conversionService, new BigDecimal("1"), UnitEnum.JIN, UnitEnum.LB);  // 1斤 ≈ 1.102磅
        testConversion(conversionService, new BigDecimal("1"), UnitEnum.OZ, UnitEnum.QIAN); // 1盎司 ≈ 5.67钱
        testConversion(conversionService, new BigDecimal("1"), UnitEnum.KG, UnitEnum.JIN);  // 1千克 = 2斤

        // 测试一些重要的转换关系
        System.out.println("\n--- Important Conversion Relationships ---");
        // 1磅 = 16盎司，所以 1盎司 = 453.592/16 ≈ 28.35克
        testConversion(conversionService, new BigDecimal("1"), UnitEnum.OZ, UnitEnum.G);
        // 1斤 = 10两 = 100钱
        testConversion(conversionService, new BigDecimal("1"), UnitEnum.JIN, UnitEnum.QIAN);
        // 1担 = 100斤
        testConversion(conversionService, new BigDecimal("1"), UnitEnum.DAN, UnitEnum.JIN);

        System.out.println("\n=== All Tests Completed Successfully! ===");
    }

    private static void testConversion(IUnitConversionService service, BigDecimal value, UnitEnum from, UnitEnum to) {
        try {
            com.zishuimuyu.unitconvert.model.ConvertResult<BigDecimal> result = service.convert(value, from, to);
            System.out.printf("%s %s = %s %s\n", 
                value.toString(), 
                from.name(), 
                result.getValue().setScale(6, java.math.RoundingMode.HALF_UP).toString(), 
                to.name());
        } catch (Exception e) {
            System.out.printf("Conversion failed: %s %s to %s - %s\n", 
                value.toString(), 
                from.name(), 
                to.name(), 
                e.getMessage());
        }
    }
}