package com.zishuimuyu.unitconvert.performance;

import com.zishuimuyu.unitconvert.model.UnitEnum;
import com.zishuimuyu.unitconvert.service.UnitConversionServiceImpl;
import com.zishuimuyu.unitconvert.service.IUnitConversionService;
import java.math.BigDecimal;

/**
 * Test the newly added mass units conversion functionality
 */
public class TestMassUnits {
    public static void main(String[] args) {
        IUnitConversionService conversionService = new UnitConversionServiceImpl();

        System.out.println("=== Testing New Mass Units Conversion ===");

        // Test Imperial units
        System.out.println("\n--- Imperial Units Test ---");
        testConversion(conversionService, new BigDecimal("1"), UnitEnum.GR, UnitEnum.G);  // 1 grain = ? grams
        testConversion(conversionService, new BigDecimal("1"), UnitEnum.DR, UnitEnum.G);  // 1 dram = ? grams
        testConversion(conversionService, new BigDecimal("1"), UnitEnum.USTF, UnitEnum.KG); // 1 US ton = ? kilograms
        testConversion(conversionService, new BigDecimal("1"), UnitEnum.LT, UnitEnum.KG);  // 1 long ton = ? kilograms

        // Test Chinese traditional units
        System.out.println("\n--- Chinese Traditional Units Test ---");
        testConversion(conversionService, new BigDecimal("1"), UnitEnum.QIAN, UnitEnum.G);  // 1 qian = ? grams
        testConversion(conversionService, new BigDecimal("1"), UnitEnum.LIANG, UnitEnum.G);  // 1 liang = ? grams
        testConversion(conversionService, new BigDecimal("1"), UnitEnum.JIN, UnitEnum.G);   // 1 jin = ? grams
        testConversion(conversionService, new BigDecimal("1"), UnitEnum.DAN, UnitEnum.KG);  // 1 dan = ? kilograms

        // Test conversion with existing units
        System.out.println("\n--- Conversion with Existing Units Test ---");
        testConversion(conversionService, new BigDecimal("1"), UnitEnum.KG, UnitEnum.JIN);  // 1 kilogram = ? jin
        testConversion(conversionService, new BigDecimal("1"), UnitEnum.LB, UnitEnum.JIN);  // 1 pound = ? jin
        testConversion(conversionService, new BigDecimal("10"), UnitEnum.JIN, UnitEnum.LIANG); // 10 jin = ? liang
    }

    private static void testConversion(IUnitConversionService service, BigDecimal value, UnitEnum from, UnitEnum to) {
        try {
            com.zishuimuyu.unitconvert.model.ConvertResult<BigDecimal> result = service.convert(value, from, to);
            System.out.printf("%s %s = %s %s\n", value.toString(), from.name(), result.getValue().toString(), to.name());
        } catch (Exception e) {
            System.out.printf("Conversion failed: %s %s to %s - %s\n", value.toString(), from.name(), to.name(), e.getMessage());
        }
    }
}