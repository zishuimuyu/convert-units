package com.zishuimuyu.unitconvert.extended;

import com.zishuimuyu.unitconvert.builder.ConversionBuilder;
import com.zishuimuyu.unitconvert.config.UnitConversionConfig;
import com.zishuimuyu.unitconvert.model.ConvertResult;
import com.zishuimuyu.unitconvert.model.ToBestOptions;
import com.zishuimuyu.unitconvert.model.UnitDescription;
import com.zishuimuyu.unitconvert.model.UnitEnum;
import com.zishuimuyu.unitconvert.service.IUnitConversionService;
import com.zishuimuyu.unitconvert.service.UnitConversionServiceImpl;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 扩展单位转换服务
 * <P>
 * 提供额外的高级功能，如批量转换、批量最佳单位选择、单位组转换等
 * <P>
 * 功能列表：
 * <ul>
 *   <li>提供额外的高级功能，如批量转换、批量最佳单位选择、单位组转换等</li>
 *   <li>基于装饰器模式扩展基础功能</li>
 *   <li>支持批量转换操作</li>
 *   <li>支持批量最佳单位选择</li>
 *   <li>支持单位组转换</li>
 *   <li>支持转换路径查询</li>
 * </ul>
 * <P>
 * 设计原理：
 * <ol>
 *   <li>采用装饰器模式包装基础转换服务</li>
 *   <li>提供批量操作功能以提高效率</li>
 *   <li>扩展基础服务的功能而不需要修改原有代码</li>
 *   <li>保持与基础服务接口的一致性</li>
 * </ol>
 * <P>
 * 优势：
 * <ul>
 *   <li>功能扩展：提供比基础服务更多的功能</li>
 *   <li>性能提升：支持批量操作以提高处理效率</li>
 *   <li>兼容性：完全兼容基础服务接口</li>
 * </ul>
 * <P>
 * 使用场景：
 * <ol>
 *   <li>需要批量处理多个转换任务的场景</li>
 *   <li>需要同时处理多个值的转换需求</li>
 *   <li>需要复杂转换逻辑的应用场景</li>
 * </ol>
 * <P>
 * 使用示例：
 * <pre>
 * // 创建扩展服务
 * ExtendedUnitConversionService extendedService = new ExtendedUnitConversionService();
 * 
 * // 批量转换
 * List&lt;BigDecimal&gt; values = Arrays.asList(
 *     new BigDecimal("10"),
 *     new BigDecimal("20"),
 *     new BigDecimal("30")
 * );
 * List&lt;ConvertResult&lt;BigDecimal&gt;&gt; results = extendedService.convertBatch(
 *     values, UnitEnum.M, UnitEnum.KM
 * );
 * 
 * // 批量最佳单位转换
 * List&lt;ConvertResult&lt;BigDecimal&gt;&gt; bestResults = extendedService.convertToBestBatch(
 *     values, UnitEnum.MM
 * );
 * </pre>
 * <P>
 * 
 * @author 紫水木鱼
 * @version 1.0.0
 * @since 2026-04-25
 */
public class ExtendedUnitConversionService implements IUnitConversionService {
    
    /**
     * 委托服务
     * <P>
     * 详细描述字段的用途、取值范围或特殊含义
     * <P>
     * 用途：被装饰的基础转换服务，用于执行实际的转换操作
     * <P>
     * 取值范围：实现了IUnitConversionService接口的服务实例
     * <P>
     * 特殊含义：通过委托模式复用基础服务功能，扩展服务在此基础上增加额外功能
     */
    private final IUnitConversionService delegate;

    /**
     * 默认构造函数
     * <P>
     * 详细描述方法的功能、算法逻辑或业务流程
     * <P>
     * 处理逻辑：
     * <ol>
     *   <li>创建默认的UnitConversionServiceImpl实例</li>
     *   <li>使用该实例初始化扩展服务</li>
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
     */
    public ExtendedUnitConversionService() {
        this(new UnitConversionServiceImpl());
    }
    
    /**
     * 带配置的构造函数
     * <P>
     * 详细描述方法的功能、算法逻辑或业务流程
     * <P>
     * 处理逻辑：
     * <ol>
     *   <li>接收单位转换配置参数</li>
     *   <li>使用配置创建UnitConversionServiceImpl实例</li>
     *   <li>使用该实例初始化扩展服务</li>
     * </ol>
     * <P>
     * 参数说明：
     * <ul>
     *   <li>config: 单位转换配置</li>
     * </ul>
     * <P>
     * 返回值说明：
     * <ul>
     *   <li>void: 无返回值，仅初始化对象</li>
     * </ul>
     * <P>
     * 异常情况：
     * <ul>
     *   <li>当config为null时，可能导致后续操作失败</li>
     * </ul>
     * 
     * @param config 单位转换配置
     */
    public ExtendedUnitConversionService(UnitConversionConfig config) {
        this(new UnitConversionServiceImpl(config));
    }
    
    /**
     * 带委托服务的构造函数
     * <P>
     * 详细描述方法的功能、算法逻辑或业务流程
     * <P>
     * 处理逻辑：
     * <ol>
     *   <li>接收委托服务参数</li>
     *   <li>将委托服务赋值给内部字段</li>
     * </ol>
     * <P>
     * 参数说明：
     * <ul>
     *   <li>delegate: 委托服务，用于执行实际的转换操作</li>
     * </ul>
     * <P>
     * 返回值说明：
     * <ul>
     *   <li>void: 无返回值，仅初始化对象</li>
     * </ul>
     * <P>
     * 异常情况：
     * <ul>
     *   <li>当delegate为null时，可能导致后续操作失败</li>
     * </ul>
     * 
     * @param delegate 委托服务，用于执行实际的转换操作
     */
    public ExtendedUnitConversionService(IUnitConversionService delegate) {
        this.delegate = delegate;
    }

    /**
     * 批量转换
     * <P>
     * 详细描述方法的功能、算法逻辑或业务流程
     * <P>
     * 处理逻辑：
     * <ol>
     *   <li>接收待转换的值列表、源单位和目标单位</li>
     *   <li>对值列表中的每个值执行相同的单位转换</li>
     *   <li>收集所有转换结果</li>
     *   <li>返回转换结果列表</li>
     * </ol>
     * <P>
     * 参数说明：
     * <ul>
     *   <li>values: 待转换的值列表</li>
     *   <li>fromUnit: 源单位</li>
     *   <li>toUnit: 目标单位</li>
     * </ul>
     * <P>
     * 返回值说明：
     * <ul>
     *   <li>返回包含所有转换结果的列表</li>
     * </ul>
     * <P>
     * 异常情况：
     * <ul>
     *   <li>如果转换过程中出现错误，可能抛出相应的转换异常</li>
     * </ul>
     * 
     * @param values 待转换的值列表
     * @param fromUnit 源单位
     * @param toUnit 目标单位
     * @return 转换结果列表
     */
    public List<ConvertResult<BigDecimal>> convertBatch(List<BigDecimal> values, UnitEnum fromUnit, UnitEnum toUnit) {
        return values.stream()
            .map(value -> delegate.convert(value, fromUnit, toUnit))
            .collect(Collectors.toList());
    }
    
    /**
     * 批量最佳单位转换
     * <P>
     * 详细描述方法的功能、算法逻辑或业务流程
     * <P>
     * 处理逻辑：
     * <ol>
     *   <li>接收待转换的值列表和源单位</li>
     *   <li>对值列表中的每个值执行最佳单位选择转换</li>
     *   <li>收集所有转换结果</li>
     *   <li>返回转换结果列表</li>
     * </ol>
     * <P>
     * 参数说明：
     * <ul>
     *   <li>values: 待转换的值列表</li>
     *   <li>fromUnit: 源单位</li>
     * </ul>
     * <P>
     * 返回值说明：
     * <ul>
     *   <li>返回包含所有转换结果的列表</li>
     * </ul>
     * <P>
     * 异常情况：
     * <ul>
     *   <li>如果转换过程中出现错误，可能抛出相应的转换异常</li>
     * </ul>
     * 
     * @param values 待转换的值列表
     * @param fromUnit 源单位
     * @return 转换结果列表
     */
    public List<ConvertResult<BigDecimal>> convertToBestBatch(List<BigDecimal> values, UnitEnum fromUnit) {
        return values.stream()
            .map(value -> delegate.convertToBest(value, fromUnit))
            .collect(Collectors.toList());
    }
    
    /**
     * 批量最佳单位转换（带选项）
     * <P>
     * 详细描述方法的功能、算法逻辑或业务流程
     * <P>
     * 处理逻辑：
     * <ol>
     *   <li>接收待转换的值列表、源单位和转换选项</li>
     *   <li>对值列表中的每个值执行带选项的最佳单位选择转换</li>
     *   <li>收集所有转换结果</li>
     *   <li>返回转换结果列表</li>
     * </ol>
     * <P>
     * 参数说明：
     * <ul>
     *   <li>values: 待转换的值列表</li>
     *   <li>fromUnit: 源单位</li>
     *   <li>options: 转换选项，用于控制最佳单位选择的行为</li>
     * </ul>
     * <P>
     * 返回值说明：
     * <ul>
     *   <li>返回包含所有转换结果的列表</li>
     * </ul>
     * <P>
     * 异常情况：
     * <ul>
     *   <li>如果转换过程中出现错误，可能抛出相应的转换异常</li>
     * </ul>
     * 
     * @param values 待转换的值列表
     * @param fromUnit 源单位
     * @param options 转换选项
     * @return 转换结果列表
     */
    public List<ConvertResult<BigDecimal>> convertToBestBatch(List<BigDecimal> values, UnitEnum fromUnit, ToBestOptions options) {
        return values.stream()
            .map(value -> delegate.convertToBest(value, fromUnit, options))
            .collect(Collectors.toList());
    }
    
    /**
     * 单位组转换
     * <P>
     * 详细描述方法的功能、算法逻辑或业务流程
     * <P>
     * 处理逻辑：
     * <ol>
     *   <li>接收值-单位对列表和目标单位</li>
     *   <li>对每一对值和源单位执行转换到指定目标单位的操作</li>
     *   <li>收集所有转换结果</li>
     *   <li>返回转换结果列表</li>
     * </ol>
     * <P>
     * 参数说明：
     * <ul>
     *   <li>valueUnitPairs: 值-单位对列表，每个元素包含一个值和对应的源单位</li>
     *   <li>toUnit: 目标单位，所有值都将转换为此单位</li>
     * </ul>
     * <P>
     * 返回值说明：
     * <ul>
     *   <li>返回包含所有转换结果的列表</li>
     * </ul>
     * <P>
     * 异常情况：
     * <ul>
     *   <li>如果转换过程中出现错误，可能抛出相应的转换异常</li>
     * </ul>
     * 
     * @param valueUnitPairs 值-单位对列表
     * @param toUnit 目标单位
     * @return 转换结果列表
     */
    public List<ConvertResult<BigDecimal>> convertGroup(List<ValueUnitPair> valueUnitPairs, UnitEnum toUnit) {
        return valueUnitPairs.stream()
            .map(pair -> delegate.convert(pair.getValue(), pair.getFromUnit(), toUnit))
            .collect(Collectors.toList());
    }
    
    /**
     * 获取单位转换路径
     * <P>
     * 详细描述方法的功能、算法逻辑或业务流程
     * <P>
     * 处理逻辑：
     * <ol>
     *   <li>检查源单位和目标单位之间是否可以直接转换</li>
     *   <li>如果可以直接转换，则返回包含源单位和目标单位的简单路径</li>
     *   <li>如果不能直接转换，则返回null（当前实现未实现复杂路径查找）</li>
     * </ol>
     * <P>
     * 参数说明：
     * <ul>
     *   <li>fromUnit: 源单位</li>
     *   <li>toUnit: 目标单位</li>
     * </ul>
     * <P>
     * 返回值说明：
     * <ul>
     *   <li>如果可以直接转换，返回包含源单位和目标单位的路径列表</li>
     *   <li>如果无法转换，返回null</li>
     * </ul>
     * <P>
     * 异常情况：
     * <ul>
     *   <li>无异常情况</li>
     * </ul>
     * 
     * @param fromUnit 源单位
     * @param toUnit 目标单位
     * @return 转换路径，如果无法转换则返回null
     */
    public List<UnitEnum> getConversionPath(UnitEnum fromUnit, UnitEnum toUnit) {
        if (delegate.canConvertBetween(fromUnit, toUnit)) {
            return java.util.Arrays.asList(fromUnit, toUnit);
        }
        // 这里可以实现更复杂的路径查找算法
        return null;
    }
    
    /**
     * 值-单位对
     * <P>
     * 表示一个数值与其对应单位的组合，用于批量转换操作
     * <P>
     * 功能列表：
     * <ul>
     *   <li>表示一个数值与其对应单位的组合</li>
     *   <li>用于批量转换操作</li>
     *   <li>封装数值和单位的关联关系</li>
     * </ul>
     * <P>
     * 设计原理：
     * <ol>
     *   <li>使用不可变的数据结构封装数值和单位</li>
     *   <li>提供访问数值和单位的方法</li>
     *   <li>确保数据的完整性</li>
     * </ol>
     * <P>
     * 优势：
     * <ul>
     *   <li>简化批量转换操作的数据传递</li>
     *   <li>提供类型安全的数值-单位配对</li>
     *   <li>不可变性保证线程安全</li>
     * </ul>
     * <P>
     * 使用场景：
     * <ol>
     *   <li>批量转换操作中需要指定每个值对应的源单位</li>
     *   <li>需要将数值和单位作为一个整体进行传递</li>
     * </ol>
     * <P>
     * 使用示例：
     * <pre>
     * // 创建值-单位对
     * ValueUnitPair pair1 = new ValueUnitPair(new BigDecimal("10"), UnitEnum.M);
     * ValueUnitPair pair2 = new ValueUnitPair(new BigDecimal("5"), UnitEnum.KM);
     * 
     * // 使用值-单位对进行批量转换
     * List&lt;ValueUnitPair&gt; pairs = Arrays.asList(pair1, pair2);
     * List&lt;ConvertResult&lt;BigDecimal&gt;&gt; results = extendedService.convertGroup(pairs, UnitEnum.MM);
     * </pre>
     * <P>
     */
    public static class ValueUnitPair {
        /**
         * 转换值
         * <P>
         * 详细描述字段的用途、取值范围或特殊含义
         * <P>
         * 用途：存储待转换的数值
         * <P>
         * 取值范围：BigDecimal类型的数值
         * <P>
         * 特殊含义：表示需要进行单位转换的具体数值
         */
        private final BigDecimal value;
        
        /**
         * 源单位
         * <P>
         * 详细描述字段的用途、取值范围或特殊含义
         * <P>
         * 用途：表示转换值对应的源单位
         * <P>
         * 取值范围：UnitEnum枚举值
         * <P>
         * 特殊含义：与转换值配对的源单位，用于指定转换前的单位
         */
        private final UnitEnum fromUnit;
        
        /**
         * 构造函数
         * <P>
         * 详细描述方法的功能、算法逻辑或业务流程
         * <P>
         * 处理逻辑：
         * <ol>
         *   <li>接收转换值和源单位参数</li>
         *   <li>将参数赋值给内部字段</li>
         * </ol>
         * <P>
         * 参数说明：
         * <ul>
         *   <li>value: 转换值</li>
         *   <li>fromUnit: 源单位</li>
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
         * @param value 转换值
         * @param fromUnit 源单位
         */
        public ValueUnitPair(BigDecimal value, UnitEnum fromUnit) {
            this.value = value;
            this.fromUnit = fromUnit;
        }
        
        /**
         * 获取转换值
         * <P>
         * 详细描述方法的功能、算法逻辑或业务流程
         * <P>
         * 处理逻辑：
         * <ol>
         *   <li>返回内部存储的转换值</li>
         * </ol>
         * <P>
         * 参数说明：
         * <ul>
         *   <li>无参数</li>
         * </ul>
         * <P>
         * 返回值说明：
         * <ul>
         *   <li>返回转换值</li>
         * </ul>
         * <P>
         * 异常情况：
         * <ul>
         *   <li>无异常情况</li>
         * </ul>
         * 
         * @return 转换值
         */
        public BigDecimal getValue() {
            return value;
        }
        
        /**
         * 获取源单位
         * <P>
         * 详细描述方法的功能、算法逻辑或业务流程
         * <P>
         * 处理逻辑：
         * <ol>
         *   <li>返回内部存储的源单位</li>
         * </ol>
         * <P>
         * 参数说明：
         * <ul>
         *   <li>无参数</li>
         * </ul>
         * <P>
         * 返回值说明：
         * <ul>
         *   <li>返回源单位</li>
         * </ul>
         * <P>
         * 异常情况：
         * <ul>
         *   <li>无异常情况</li>
         * </ul>
         * 
         * @return 源单位
         */
        public UnitEnum getFromUnit() {
            return fromUnit;
        }
    }

    @Override
    public ConvertResult<BigDecimal> convert(BigDecimal value, UnitEnum fromUnit, UnitEnum toUnit) {
        return delegate.convert(value, fromUnit, toUnit);
    }

    @Override
    public ConvertResult<BigDecimal> convertToBest(BigDecimal value, UnitEnum fromUnit) {
        return delegate.convertToBest(value, fromUnit);
    }

    @Override
    public ConvertResult<BigDecimal> convertToBest(BigDecimal value, UnitEnum fromUnit, ToBestOptions options) {
        return delegate.convertToBest(value, fromUnit, options);
    }

    @Override
    public boolean canConvertBetween(UnitEnum fromUnit, UnitEnum toUnit) {
        return delegate.canConvertBetween(fromUnit, toUnit);
    }

    @Override
    public List<String> getSupportedMeasures() {
        return delegate.getSupportedMeasures();
    }

    @Override
    public List<UnitEnum> getPossibleUnits(String measure) {
        return delegate.getPossibleUnits(measure);
    }

    @Override
    public List<UnitDescription> listAllUnits() {
        return delegate.listAllUnits();
    }

    @Override
    public List<UnitDescription> listUnitsByMeasure(String measure) {
        return delegate.listUnitsByMeasure(measure);
    }

    @Override
    public UnitDescription describeUnit(UnitEnum unit) {
        return delegate.describeUnit(unit);
    }

    @Override
    public IUnitConversionService from(BigDecimal value, UnitEnum fromUnit) {
        return delegate.from(value, fromUnit);
    }

    @Override
    public List<UnitEnum> possibilities(String measure) {
        return delegate.possibilities(measure);
    }

    @Override
    public List<String> measures() {
        return delegate.measures();
    }

    @Override
    public UnitDescription lookup(String unitName) {
        return delegate.lookup(unitName);
    }

    @Override
    public ConversionBuilder buildConversion(BigDecimal value, UnitEnum fromUnit) {
        return delegate.buildConversion(value, fromUnit);
    }
}