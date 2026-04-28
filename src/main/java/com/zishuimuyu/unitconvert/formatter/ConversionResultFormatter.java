package com.zishuimuyu.unitconvert.formatter;

import com.zishuimuyu.unitconvert.model.ConvertResult;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class ConversionResultFormatter {
    
    private int scale = 10;
    private RoundingMode roundingMode = RoundingMode.HALF_UP;
    private boolean useGrouping = false;
    private Locale locale = Locale.getDefault();
    private String customPattern = null;
    
    public ConversionResultFormatter() {
    }
    
    public ConversionResultFormatter withScale(int scale) {
        this.scale = scale;
        return this;
    }
    
    public ConversionResultFormatter withRoundingMode(RoundingMode roundingMode) {
        this.roundingMode = roundingMode;
        return this;
    }
    
    public ConversionResultFormatter withGrouping(boolean useGrouping) {
        this.useGrouping = useGrouping;
        return this;
    }
    
    public ConversionResultFormatter withLocale(Locale locale) {
        this.locale = locale;
        return this;
    }
    
    public ConversionResultFormatter withPattern(String pattern) {
        this.customPattern = pattern;
        return this;
    }
    
    public String format(ConvertResult<BigDecimal> result) {
        if (result == null) {
            return "null";
        }
        
        BigDecimal value = result.getValue();
        BigDecimal scaledValue = value.setScale(scale, roundingMode);
        
        if (customPattern != null) {
            DecimalFormat df = new DecimalFormat(customPattern, DecimalFormatSymbols.getInstance(locale));
            df.setRoundingMode(roundingMode);
            return df.format(scaledValue) + " " + result.getUnit();
        }
        
        DecimalFormat df = (DecimalFormat) DecimalFormat.getNumberInstance(locale);
        df.setRoundingMode(roundingMode);
        df.setGroupingUsed(useGrouping);
        df.setMaximumFractionDigits(scale);
        df.setMinimumFractionDigits(scale);
        
        return df.format(scaledValue) + " " + result.getUnit();
    }
    
    public String formatValue(BigDecimal value) {
        if (value == null) {
            return "null";
        }
        
        BigDecimal scaledValue = value.setScale(scale, roundingMode);
        
        if (customPattern != null) {
            DecimalFormat df = new DecimalFormat(customPattern, DecimalFormatSymbols.getInstance(locale));
            df.setRoundingMode(roundingMode);
            return df.format(scaledValue);
        }
        
        DecimalFormat df = (DecimalFormat) DecimalFormat.getNumberInstance(locale);
        df.setRoundingMode(roundingMode);
        df.setGroupingUsed(useGrouping);
        df.setMaximumFractionDigits(scale);
        df.setMinimumFractionDigits(scale);
        
        return df.format(scaledValue);
    }
    
    public static ConversionResultFormatter createDefault() {
        return new ConversionResultFormatter()
            .withScale(2)
            .withRoundingMode(RoundingMode.HALF_UP)
            .withGrouping(true);
    }
    
    public static ConversionResultFormatter createScientific() {
        return new ConversionResultFormatter()
            .withPattern("0.##########E0")
            .withRoundingMode(RoundingMode.HALF_UP);
    }
    
    public static ConversionResultFormatter createCompact() {
        return new ConversionResultFormatter()
            .withScale(0)
            .withRoundingMode(RoundingMode.HALF_UP)
            .withGrouping(false);
    }
}