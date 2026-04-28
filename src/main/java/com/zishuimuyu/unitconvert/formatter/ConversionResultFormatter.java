package com.zishuimuyu.unitconvert.formatter;

import com.zishuimuyu.unitconvert.model.ConvertResult;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

/**
 * 单位转换结果格式化器
 * <P>
 * 提供灵活的结果格式化功能，支持多种格式化选项：
 * <P>
 * 功能列表：
 * <ul>
 *   <li>提供灵活的结果格式化功能，支持多种格式化选项</li>
 *   <li>支持自定义小数位数</li>
 *   <li>支持自定义舍入模式</li>
 *   <li>支持数字分组显示</li>
 *   <li>支持本地化格式</li>
 *   <li>支持自定义格式模式</li>
 *   <li>提供多种预设格式化器</li>
 * </ul>
 * <P>
 * 设计原理：
 * <ol>
 *   <li>采用建造者模式提供流畅的API</li>
 *   <li>通过可配置的参数实现灵活的格式化</li>
 *   <li>支持链式调用以简化配置过程</li>
 *   <li>提供多种预设格式化器以满足常见需求</li>
 * </ol>
 * <P>
 * 优势：
 * <ul>
 *   <li>灵活性：支持多种格式化选项和配置</li>
 *   <li>易用性：提供流畅的API和链式调用</li>
 *   <li>可扩展性：支持自定义格式模式</li>
 *   <li>本地化：支持多种区域设置</li>
 * </ul>
 * <P>
 * 使用场景：
 * <ol>
 *   <li>需要将转换结果格式化为字符串的场景</li>
 *   <li>需要根据不同需求调整数值显示格式的场景</li>
 *   <li>需要本地化数值显示的场景</li>
 * </ol>
 * <P>
 * 使用示例：
 * <pre>
 * // 使用默认格式化器
 * ConversionResultFormatter formatter = ConversionResultFormatter.createDefault();
 * String formatted = formatter.format(result);
 * 
 * // 使用科学计数法
 * ConversionResultFormatter scientificFormatter = ConversionResultFormatter.createScientific();
 * String scientific = scientificFormatter.format(result);
 * 
 * // 自定义格式化器
 * ConversionResultFormatter customFormatter = new ConversionResultFormatter()
 *     .withScale(4)
 *     .withRoundingMode(RoundingMode.HALF_UP)
 *     .withGrouping(true)
 *     .withLocale(Locale.US);
 * String custom = customFormatter.format(result);
 * </pre>
 * <P>
 * 
 * @author 紫水木鱼
 * @version 1.0.0
 * @since 2026-04-25
 */
public class ConversionResultFormatter {
    
    /**
     * 小数位数
     * <P>
     * 详细描述字段的用途、取值范围或特殊含义
     * <P>
     * 用途：控制格式化结果中小数点后保留的位数
     * <P>
     * 取值范围：非负整数
     * <P>
     * 特殊含义：决定数值的精度显示，影响最终格式化输出的精确度
     */
    private int scale = 10;
    
    /**
     * 舍入模式
     * <P>
     * 详细描述字段的用途、取值范围或特殊含义
     * <P>
     * 用途：控制数值舍入的方式
     * <P>
     * 取值范围：RoundingMode枚举值，如HALF_UP、HALF_DOWN、CEILING、FLOOR等
     * <P>
     * 特殊含义：决定如何处理超出指定位数的小数部分，影响数值的最终显示
     */
    private RoundingMode roundingMode = RoundingMode.HALF_UP;
    
    /**
     * 是否使用数字分组
     * <P>
     * 详细描述字段的用途、取值范围或特殊含义
     * <P>
     * 用途：控制是否在大数字中添加千位分隔符
     * <P>
     * 取值范围：布尔值，true表示使用分组，false表示不使用分组
     * <P>
     * 特殊含义：决定数字显示格式，如1234.56 vs 1,234.56，影响可读性
     */
    private boolean useGrouping = false;
    
    /**
     * 本地化设置
     * <P>
     * 详细描述字段的用途、取值范围或特殊含义
     * <P>
     * 用途：控制数字格式的区域特定显示方式
     * <P>
     * 取值范围：Locale对象，如Locale.US、Locale.CHINA等
     * <P>
     * 特殊含义：影响数字分隔符、小数点符号等区域特定的格式化行为
     */
    private Locale locale = Locale.getDefault();
    
    /**
     * 自定义格式模式
     * <P>
     * 详细描述字段的用途、取值范围或特殊含义
     * <P>
     * 用途：允许用户指定自定义的数字格式模式
     * <P>
     * 取值范围：字符串格式，遵循DecimalFormat模式规则，如"0.00", "#,##0.00", "0.##########E0"
     * <P>
     * 特殊含义：当此字段不为null时，将覆盖其他格式设置，使用指定的自定义模式进行格式化
     */
    private String customPattern = null;
    
    /**
     * 默认构造函数
     * <P>
     * 详细描述方法的功能、算法逻辑或业务流程
     * <P>
     * 处理逻辑：
     * <ol>
     *   <li>创建一个新的格式化器实例</li>
     *   <li>使用预设的默认值初始化内部字段</li>
     * </ol>
     * <P>
     * 参数说明：
     * <ul>
     *   <li>无参数</li>
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
     * 创建一个使用默认设置的格式化器：
     * - 小数位数：10
     * - 舍入模式：HALF_UP
     * - 数字分组：false
     * - 本地化：系统默认
     */
    public ConversionResultFormatter() {
    }
    
    /**
     * 设置小数位数
     * <P>
     * 详细描述方法的功能、算法逻辑或业务流程
     * <P>
     * 处理逻辑：
     * <ol>
     *   <li>接收小数位数参数</li>
     *   <li>将参数赋值给内部字段</li>
     *   <li>返回当前实例以支持链式调用</li>
     * </ol>
     * <P>
     * 参数说明：
     * <ul>
     *   <li>scale: 要保留的小数位数，必须大于等于0</li>
     * </ul>
     * <P>
     * 返回值说明：
     * <ul>
     *   <li>返回当前格式化器实例，支持链式调用</li>
     * </ul>
     * <P>
     * 异常情况：
     * <ul>
     *   <li>无异常情况</li>
     * </ul>
     * 
     * @param scale 要保留的小数位数，必须大于等于0
     * @return 当前格式化器实例，支持链式调用
     */
    public ConversionResultFormatter withScale(int scale) {
        this.scale = scale;
        return this;
    }
    
    /**
     * 设置舍入模式
     * <P>
     * 详细描述方法的功能、算法逻辑或业务流程
     * <P>
     * 处理逻辑：
     * <ol>
     *   <li>接收舍入模式参数</li>
     *   <li>将参数赋值给内部字段</li>
     *   <li>返回当前实例以支持链式调用</li>
     * </ol>
     * <P>
     * 参数说明：
     * <ul>
     *   <li>roundingMode: 舍入模式，如RoundingMode.HALF_UP（四舍五入）</li>
     * </ul>
     * <P>
     * 返回值说明：
     * <ul>
     *   <li>返回当前格式化器实例，支持链式调用</li>
     * </ul>
     * <P>
     * 异常情况：
     * <ul>
     *   <li>无异常情况</li>
     * </ul>
     * 
     * @param roundingMode 舍入模式，如RoundingMode.HALF_UP（四舍五入）
     * @return 当前格式化器实例，支持链式调用
     */
    public ConversionResultFormatter withRoundingMode(RoundingMode roundingMode) {
        this.roundingMode = roundingMode;
        return this;
    }
    
    /**
     * 设置是否使用数字分组
     * <P>
     * 详细描述方法的功能、算法逻辑或业务流程
     * <P>
     * 处理逻辑：
     * <ol>
     *   <li>接收数字分组参数</li>
     *   <li>将参数赋值给内部字段</li>
     *   <li>返回当前实例以支持链式调用</li>
     * </ol>
     * <P>
     * 参数说明：
     * <ul>
     *   <li>useGrouping: true表示使用分组（如1,234.56），false表示不使用（如1234.56）</li>
     * </ul>
     * <P>
     * 返回值说明：
     * <ul>
     *   <li>返回当前格式化器实例，支持链式调用</li>
     * </ul>
     * <P>
     * 异常情况：
     * <ul>
     *   <li>无异常情况</li>
     * </ul>
     * 
     * @param useGrouping true表示使用分组（如1,234.56），false表示不使用（如1234.56）
     * @return 当前格式化器实例，支持链式调用
     */
    public ConversionResultFormatter withGrouping(boolean useGrouping) {
        this.useGrouping = useGrouping;
        return this;
    }
    
    /**
     * 设置本地化设置
     * <P>
     * 详细描述方法的功能、算法逻辑或业务流程
     * <P>
     * 处理逻辑：
     * <ol>
     *   <li>接收本地化参数</li>
     *   <li>将参数赋值给内部字段</li>
     *   <li>返回当前实例以支持链式调用</li>
     * </ol>
     * <P>
     * 参数说明：
     * <ul>
     *   <li>locale: 区域设置，如Locale.US、Locale.CHINA等</li>
     * </ul>
     * <P>
     * 返回值说明：
     * <ul>
     *   <li>返回当前格式化器实例，支持链式调用</li>
     * </ul>
     * <P>
     * 异常情况：
     * <ul>
     *   <li>无异常情况</li>
     * </ul>
     * 
     * @param locale 区域设置，如Locale.US、Locale.CHINA等
     * @return 当前格式化器实例，支持链式调用
     */
    public ConversionResultFormatter withLocale(Locale locale) {
        this.locale = locale;
        return this;
    }
    
    /**
     * 设置自定义格式模式
     * <P>
     * 详细描述方法的功能、算法逻辑或业务流程
     * <P>
     * 处理逻辑：
     * <ol>
     *   <li>接收自定义格式模式参数</li>
     *   <li>将参数赋值给内部字段</li>
     *   <li>返回当前实例以支持链式调用</li>
     * </ol>
     * <P>
     * 参数说明：
     * <ul>
     *   <li>pattern: DecimalFormat格式模式，如"0.00"、"#,##0.00"、"0.##########E0"</li>
     * </ul>
     * <P>
     * 返回值说明：
     * <ul>
     *   <li>返回当前格式化器实例，支持链式调用</li>
     * </ul>
     * <P>
     * 异常情况：
     * <ul>
     *   <li>无异常情况</li>
     * </ul>
     * 
     * @param pattern DecimalFormat格式模式，如"0.00"、"#,##0.00"、"0.##########E0"
     * @return 当前格式化器实例，支持链式调用
     */
    public ConversionResultFormatter withPattern(String pattern) {
        this.customPattern = pattern;
        return this;
    }
    
    /**
     * 格式化转换结果
     * <P>
     * 详细描述方法的功能、算法逻辑或业务流程
     * <P>
     * 处理逻辑：
     * <ol>
     *   <li>检查输入结果是否为null</li>
     *   <li>如果为null，返回"null"字符串</li>
     *   <li>获取转换结果中的数值</li>
     *   <li>根据设置的小数位数和舍入模式对数值进行缩放</li>
     *   <li>如果设置了自定义格式模式，则使用自定义模式进行格式化</li>
     *   <li>否则使用默认的数字格式化器进行格式化</li>
     *   <li>将格式化后的数值与单位组合成最终字符串</li>
     *   <li>返回格式化后的字符串</li>
     * </ol>
     * <P>
     * 参数说明：
     * <ul>
     *   <li>result: 要格式化的转换结果</li>
     * </ul>
     * <P>
     * 返回值说明：
     * <ul>
     *   <li>返回格式化后的字符串，格式为"数值 单位"</li>
     * </ul>
     * <P>
     * 异常情况：
     * <ul>
     *   <li>无异常情况</li>
     * </ul>
     * 
     * @param result 要格式化的转换结果
     * @return 格式化后的字符串，格式为"数值 单位"
     */
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
    
    /**
     * 格式化数值
     * <P>
     * 详细描述方法的功能、算法逻辑或业务流程
     * <P>
     * 处理逻辑：
     * <ol>
     *   <li>检查输入数值是否为null</li>
     *   <li>如果为null，返回"null"字符串</li>
     *   <li>根据设置的小数位数和舍入模式对数值进行缩放</li>
     *   <li>如果设置了自定义格式模式，则使用自定义模式进行格式化</li>
     *   <li>否则使用默认的数字格式化器进行格式化</li>
     *   <li>返回格式化后的数值字符串</li>
     * </ol>
     * <P>
     * 参数说明：
     * <ul>
     *   <li>value: 要格式化的数值</li>
     * </ul>
     * <P>
     * 返回值说明：
     * <ul>
     *   <li>返回格式化后的数值字符串</li>
     * </ul>
     * <P>
     * 异常情况：
     * <ul>
     *   <li>无异常情况</li>
     * </ul>
     * 
     * @param value 要格式化的数值
     * @return 格式化后的数值字符串
     */
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
    
    /**
     * 创建默认格式化器
     * <P>
     * 详细描述方法的功能、算法逻辑或业务流程
     * <P>
     * 处理逻辑：
     * <ol>
     *   <li>创建一个新的格式化器实例</li>
     *   <li>设置小数位数为2</li>
     *   <li>设置舍入模式为HALF_UP</li>
     *   <li>启用数字分组</li>
     *   <li>返回配置好的格式化器实例</li>
     * </ol>
     * <P>
     * 参数说明：
     * <ul>
     *   <li>无参数</li>
     * </ul>
     * <P>
     * 返回值说明：
     * <ul>
     *   <li>返回默认配置的格式化器实例</li>
     * </ul>
     * <P>
     * 异常情况：
     * <ul>
     *   <li>无异常情况</li>
     * </ul>
     * 
     * 默认设置：
     * - 小数位数：2
     * - 舍入模式：HALF_UP
     * - 数字分组：true
     * 
     * 适用于大多数日常使用场景
     * 
     * @return 默认格式化器实例
     */
    public static ConversionResultFormatter createDefault() {
        return new ConversionResultFormatter()
            .withScale(2)
            .withRoundingMode(RoundingMode.HALF_UP)
            .withGrouping(true);
    }
    
    /**
     * 创建科学计数法格式化器
     * <P>
     * 详细描述方法的功能、算法逻辑或业务流程
     * <P>
     * 处理逻辑：
     * <ol>
     *   <li>创建一个新的格式化器实例</li>
     *   <li>设置科学计数法格式模式"0.##########E0"</li>
     *   <li>设置舍入模式为HALF_UP</li>
     *   <li>返回配置好的科学计数法格式化器实例</li>
     * </ol>
     * <P>
     * 参数说明：
     * <ul>
     *   <li>无参数</li>
     * </ul>
     * <P>
     * 返回值说明：
     * <ul>
     *   <li>返回科学计数法格式化器实例</li>
     * </ul>
     * <P>
     * 异常情况：
     * <ul>
     *   <li>无异常情况</li>
     * </ul>
     * 
     * 使用科学计数法格式化数值，适用于极大或极小的数值
     * 
     * @return 科学计数法格式化器实例
     */
    public static ConversionResultFormatter createScientific() {
        return new ConversionResultFormatter()
            .withPattern("0.##########E0")
            .withRoundingMode(RoundingMode.HALF_UP);
    }
    
    /**
     * 创建紧凑格式化器
     * <P>
     * 详细描述方法的功能、算法逻辑或业务流程
     * <P>
     * 处理逻辑：
     * <ol>
     *   <li>创建一个新的格式化器实例</li>
     *   <li>设置小数位数为0（不保留小数部分）</li>
     *   <li>设置舍入模式为HALF_UP</li>
     *   <li>禁用数字分组</li>
     *   <li>返回配置好的紧凑格式化器实例</li>
     * </ol>
     * <P>
     * 参数说明：
     * <ul>
     *   <li>无参数</li>
     * </ul>
     * <P>
     * 返回值说明：
     * <ul>
     *   <li>返回紧凑格式化器实例</li>
     * </ul>
     * <P>
     * 异常情况：
     * <ul>
     *   <li>无异常情况</li>
     * </ul>
     * 
     * 不保留小数部分，不使用分组，适用于需要简洁显示的场景
     * 
     * @return 紧凑格式化器实例
     */
    public static ConversionResultFormatter createCompact() {
        return new ConversionResultFormatter()
            .withScale(0)
            .withRoundingMode(RoundingMode.HALF_UP)
            .withGrouping(false);
    }
}