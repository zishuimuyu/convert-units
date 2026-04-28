package com.zishuimuyu.unitconvert.service;

import com.zishuimuyu.unitconvert.builder.ConversionBuilder;
import com.zishuimuyu.unitconvert.model.ConvertResult;
import com.zishuimuyu.unitconvert.model.ToBestOptions;
import com.zishuimuyu.unitconvert.model.UnitEnum;
import com.zishuimuyu.unitconvert.model.UnitDescription;

import java.math.BigDecimal;
import java.util.List;

/**
 * 单位转换服务接口
 * <P>
 * 定义了单位转换的基本操作规范
 * <P>
 * 功能列表：
 * <ul>
 *   <li>提供基本的单位转换功能</li>
 *   <li>支持单位兼容性检查</li>
 *   <li>提供单位信息查询功能</li>
 *   <li>支持最佳单位选择</li>
 *   <li>提供单位枚举和测量类型管理</li>
 *   <li>支持链式调用API</li>
 *   <li>提供流畅的转换构建器</li>
 * </ul>
 * <P>
 * 设计原理：
 * <ol>
 *   <li>采用接口设计模式，便于不同实现类的替换</li>
 *   <li>提供统一的单位转换操作规范</li>
 *   <li>支持多种转换模式和查询方式</li>
 *   <li>通过链式调用提高API可用性</li>
 * </ol>
 * <P>
 * 优势：
 * <ul>
 *   <li>统一性：提供标准化的单位转换接口</li>
 *   <li>灵活性：支持多种转换和查询操作</li>
 *   <li>易用性：提供链式调用和流畅API</li>
 *   <li>可扩展性：接口设计便于实现类的扩展</li>
 * </ul>
 * <P>
 * 使用场景：
 * <ol>
 *   <li>需要进行单位转换操作的场景</li>
 *   <li>需要查询单位信息的场景</li>
 *   <li>需要验证单位兼容性的场景</li>
 *   <li>需要查找最佳单位表示的场景</li>
 * </ol>
 * <P>
 * 
 * @author 紫水木鱼
 * @version 1.0.0
 * @since 2026-04-24
 */
public interface IUnitConversionService {
    
    /**
     * 将指定数值从源单位转换为目标单位
     * <P>
     * 详细描述方法的功能、算法逻辑或业务流程
     * <P>
     * 处理逻辑：
     * <ol>
     *   <li>验证输入参数的有效性</li>
     *   <li>检查源单位和目标单位是否兼容</li>
     *   <li>执行单位转换计算</li>
     *   <li>返回包含转换结果的对象</li>
     * </ol>
     * <P>
     * 参数说明：
     * <ul>
     *   <li>value: 待转换的数值，必须为有效的BigDecimal对象</li>
     *   <li>fromUnit: 源单位，表示当前数值的单位</li>
     *   <li>toUnit: 目标单位，表示要转换到的目标单位</li>
     * </ul>
     * <P>
     * 返回值说明：
     * <ul>
     *   <li>返回包含转换后数值和单位信息的ConvertResult对象</li>
     * </ul>
     * <P>
     * 异常情况：
     * <ul>
     *   <li>IllegalArgumentException: 当单位不兼容或参数无效时抛出</li>
     * </ul>
     * 
     * @param value 待转换的数值
     * @param fromUnit 源单位
     * @param toUnit 目标单位
     * @return 转换结果对象
     * @throws IllegalArgumentException 当单位不兼容或参数无效时抛出
     */
    ConvertResult<BigDecimal> convert(BigDecimal value, UnitEnum fromUnit, UnitEnum toUnit);
    
    /**
     * 检查两个单位是否可以相互转换
     * <P>
     * 详细描述方法的功能、算法逻辑或业务流程
     * <P>
     * 处理逻辑：
     * <ol>
     *   <li>获取源单位和目标单位的信息</li>
     *   <li>比较两个单位的测量类型是否相同</li>
     *   <li>判断是否存在转换路径</li>
     *   <li>返回兼容性检查结果</li>
     * </ol>
     * <P>
     * 参数说明：
     * <ul>
     *   <li>fromUnit: 源单位，需要检查的起始单位</li>
     *   <li>toUnit: 目标单位，需要检查的目标单位</li>
     * </ul>
     * <P>
     * 返回值说明：
     * <ul>
     *   <li>如果两个单位可以相互转换则返回true，否则返回false</li>
     * </ul>
     * <P>
     * 异常情况：
     * <ul>
     *   <li>无异常情况</li>
     * </ul>
     * 
     * @param fromUnit 源单位
     * @param toUnit 目标单位
     * @return 如果可以转换返回true，否则返回false
     */
    boolean canConvertBetween(UnitEnum fromUnit, UnitEnum toUnit);
    
    /**
     * 获取指定单位的详细信息
     * <P>
     * 详细描述方法的功能、算法逻辑或业务流程
     * <P>
     * 处理逻辑：
     * <ol>
     *   <li>接收单位枚举参数</li>
     *   <li>从内部数据结构中查找对应的单位信息</li>
     *   <li>返回包含单位详细信息的对象</li>
     * </ol>
     * <P>
     * 参数说明：
     * <ul>
     *   <li>unit: 单位枚举，表示要查询的单位</li>
     * </ul>
     * <P>
     * 返回值说明：
     * <ul>
     *   <li>返回包含单位详细信息的UnitDescription对象</li>
     * </ul>
     * <P>
     * 异常情况：
     * <ul>
     *   <li>如果单位不存在，可能返回null或抛出异常</li>
     * </ul>
     * 
     * @param unit 单位枚举
     * @return 单位详细信息
     */
    UnitDescription describeUnit(UnitEnum unit);
    
    /**
     * 获取最适合的单位表示
     * <P>
     * 详细描述方法的功能、算法逻辑或业务流程
     * <P>
     * 处理逻辑：
     * <ol>
     *   <li>接收源数值和源单位</li>
     *   <li>确定源单位所属的测量类型</li>
     *   <li>遍历该测量类型下所有可能的单位</li>
     *   <li>将源数值转换到每个可能的单位</li>
     *   <li>根据预设规则选择最合适的单位表示</li>
     *   <li>返回包含最佳单位和相应数值的转换结果</li>
     * </ol>
     * <P>
     * 参数说明：
     * <ul>
     *   <li>value: 待转换的数值，表示原始数量</li>
     *   <li>fromUnit: 源单位，表示原始数值的单位</li>
     * </ul>
     * <P>
     * 返回值说明：
     * <ul>
     *   <li>返回包含最佳单位表示和相应数值的ConvertResult对象</li>
     * </ul>
     * <P>
     * 异常情况：
     * <ul>
     *   <li>如果无法找到合适的单位，可能返回原值或抛出异常</li>
     * </ul>
     * 
     * @param value 待转换的数值
     * @param fromUnit 源单位
     * @return 最佳单位转换结果
     */
    ConvertResult<BigDecimal> convertToBest(BigDecimal value, UnitEnum fromUnit);
    
    /**
     * 获取最适合的单位表示（带选项）
     * <P>
     * 详细描述方法的功能、算法逻辑或业务流程
     * <P>
     * 处理逻辑：
     * <ol>
     *   <li>接收源数值、源单位和选项配置</li>
     *   <li>确定源单位所属的测量类型</li>
     *   <li>根据选项配置过滤可能的单位列表</li>
     *   <li>将源数值转换到每个符合条件的单位</li>
     *   <li>根据选项配置中的规则选择最合适的单位表示</li>
     *   <li>返回包含最佳单位和相应数值的转换结果</li>
     * </ol>
     * <P>
     * 参数说明：
     * <ul>
     *   <li>value: 待转换的数值，表示原始数量</li>
     *   <li>fromUnit: 源单位，表示原始数值的单位</li>
     *   <li>options: 选项配置，包含选择最佳单位的规则和限制条件</li>
     * </ul>
     * <P>
     * 返回值说明：
     * <ul>
     *   <li>返回包含最佳单位表示和相应数值的ConvertResult对象</li>
     * </ul>
     * <P>
     * 异常情况：
     * <ul>
     *   <li>如果无法找到合适的单位，可能返回原值或抛出异常</li>
     * </ul>
     * 
     * @param value 待转换的数值
     * @param fromUnit 源单位
     * @param options 选项配置
     * @return 最佳单位转换结果
     */
    ConvertResult<BigDecimal> convertToBest(BigDecimal value, UnitEnum fromUnit, ToBestOptions options);
    
    /**
     * 获取所有支持的测量类型
     * <P>
     * 详细描述方法的功能、算法逻辑或业务流程
     * <P>
     * 处理逻辑：
     * <ol>
     *   <li>从内部数据结构中获取所有支持的测量类型</li>
     *   <li>将测量类型组织成列表形式</li>
     *   <li>返回测量类型列表</li>
     * </ol>
     * <P>
     * 参数说明：
     * <ul>
     *   <li>无参数</li>
     * </ul>
     * <P>
     * 返回值说明：
     * <ul>
     *   <li>返回包含所有支持测量类型的字符串列表</li>
     * </ul>
     * <P>
     * 异常情况：
     * <ul>
     *   <li>无异常情况</li>
     * </ul>
     * 
     * @return 支持的测量类型列表
     */
    List<String> getSupportedMeasures();
    
    /**
     * 获取指定测量类型下的所有可能单位
     * <P>
     * 详细描述方法的功能、算法逻辑或业务流程
     * <P>
     * 处理逻辑：
     * <ol>
     *   <li>接收测量类型参数</li>
     *   <li>从内部数据结构中查找对应测量类型的所有单位</li>
     *   <li>将单位组织成列表形式</li>
     *   <li>返回单位枚举列表</li>
     * </ol>
     * <P>
     * 参数说明：
     * <ul>
     *   <li>measure: 测量类型，表示要查询的测量类别</li>
     * </ul>
     * <P>
     * 返回值说明：
     * <ul>
     *   <li>返回指定测量类型下所有可能的单位枚举列表</li>
     * </ul>
     * <P>
     * 异常情况：
     * <ul>
     *   <li>如果测量类型不存在，可能返回空列表</li>
     * </ul>
     * 
     * @param measure 测量类型
     * @return 该测量类型下的所有单位
     */
    List<UnitEnum> getPossibleUnits(String measure);
    
    /**
     * 获取所有支持的单位
     * <P>
     * 详细描述方法的功能、算法逻辑或业务流程
     * <P>
     * 处理逻辑：
     * <ol>
     *   <li>从内部数据结构中获取所有支持的单位信息</li>
     *   <li>将每个单位信息包装成UnitDescription对象</li>
     *   <li>将所有UnitDescription对象组织成列表</li>
     *   <li>返回单位描述列表</li>
     * </ol>
     * <P>
     * 参数说明：
     * <ul>
     *   <li>无参数</li>
     * </ul>
     * <P>
     * 返回值说明：
     * <ul>
     *   <li>返回包含所有支持单位描述的列表</li>
     * </ul>
     * <P>
     * 异常情况：
     * <ul>
     *   <li>无异常情况</li>
     * </ul>
     * 
     * @return 所有支持的单位描述列表
     */
    List<UnitDescription> listAllUnits();
    
    /**
     * 获取指定测量类型下的所有单位描述
     * <P>
     * 详细描述方法的功能、算法逻辑或业务流程
     * <P>
     * 处理逻辑：
     * <ol>
     *   <li>接收测量类型参数</li>
     *   <li>从内部数据结构中查找对应测量类型的所有单位</li>
     *   <li>将每个单位信息包装成UnitDescription对象</li>
     *   <li>将所有UnitDescription对象组织成列表</li>
     *   <li>返回指定测量类型下的单位描述列表</li>
     * </ol>
     * <P>
     * 参数说明：
     * <ul>
     *   <li>measure: 测量类型，表示要查询的测量类别</li>
     * </ul>
     * <P>
     * 返回值说明：
     * <ul>
     *   <li>返回指定测量类型下所有单位的描述列表</li>
     * </ul>
     * <P>
     * 异常情况：
     * <ul>
     *   <li>如果测量类型不存在，可能返回空列表</li>
     * </ul>
     * 
     * @param measure 测量类型
     * @return 该测量类型下的所有单位描述
     */
    List<UnitDescription> listUnitsByMeasure(String measure);
    
    /**
     * 设置源单位，用于链式调用
     * <P>
     * 详细描述方法的功能、算法逻辑或业务流程
     * <P>
     * 处理逻辑：
     * <ol>
     *   <li>接收源数值和源单位参数</li>
     *   <li>将这些参数保存为内部状态</li>
     *   <li>返回当前服务实例以支持链式调用</li>
     * </ol>
     * <P>
     * 参数说明：
     * <ul>
     *   <li>value: 源数值，表示要转换的数量</li>
     *   <li>fromUnit: 源单位，表示源数值的单位</li>
     * </ul>
     * <P>
     * 返回值说明：
     * <ul>
     *   <li>返回当前服务实例，支持链式调用</li>
     * </ul>
     * <P>
     * 异常情况：
     * <ul>
     *   <li>如果参数无效，可能抛出IllegalArgumentException</li>
     * </ul>
     * 
     * @param value 源数值
     * @param fromUnit 源单位
     * @return 当前服务实例，支持链式调用
     */
    IUnitConversionService from(BigDecimal value, UnitEnum fromUnit);
    
    /**
     * 获取当前源单位可以转换到的所有可能单位
     * <P>
     * 详细描述方法的功能、算法逻辑或业务流程
     * <P>
     * 处理逻辑：
     * <ol>
     *   <li>基于之前设置的源单位确定测量类型</li>
     *   <li>如果提供了测量类型过滤器，则应用过滤</li>
     *   <li>获取所有与源单位兼容的单位列表</li>
     *   <li>返回可能的单位列表</li>
     * </ol>
     * <P>
     * 参数说明：
     * <ul>
     *   <li>measure: 可选的测量类型过滤器，用于限制返回的单位范围</li>
     * </ul>
     * <P>
     * 返回值说明：
     * <ul>
     *   <li>返回当前源单位可以转换到的所有可能单位列表</li>
     * </ul>
     * <P>
     * 异常情况：
     * <ul>
     *   <li>无异常情况</li>
     * </ul>
     * 
     * @param measure 可选的测量类型过滤器
     * @return 可能的单位列表
     */
    List<UnitEnum> possibilities(String measure);
    
    /**
     * 获取所有支持的测量类型
     * <P>
     * 详细描述方法的功能、算法逻辑或业务流程
     * <P>
     * 处理逻辑：
     * <ol>
     *   <li>从内部数据结构中获取所有支持的测量类型</li>
     *   <li>将测量类型组织成列表形式</li>
     *   <li>返回测量类型列表</li>
     * </ol>
     * <P>
     * 参数说明：
     * <ul>
     *   <li>无参数</li>
     * </ul>
     * <P>
     * 返回值说明：
     * <ul>
     *   <li>返回所有支持的测量类型列表</li>
     * </ul>
     * <P>
     * 异常情况：
     * <ul>
     *   <li>无异常情况</li>
     * </ul>
     * 
     * @return 测量类型列表
     */
    List<String> measures();
    
    /**
     * 通过单位的缩写、单数或复数名称查询单位
     * <P>
     * 详细描述方法的功能、算法逻辑或业务流程
     * <P>
     * 处理逻辑：
     * <ol>
     *   <li>接收单位名称参数</li>
     *   <li>在内部数据结构中搜索匹配的单位</li>
     *   <li>搜索范围包括缩写、单数形式和复数形式</li>
     *   <li>如果找到匹配项，返回单位的详细信息</li>
     *   <li>如果未找到匹配项，返回null</li>
     * </ol>
     * <P>
     * 参数说明：
     * <ul>
     *   <li>unitName: 单位名称，可以是缩写、单数或复数形式</li>
     * </ul>
     * <P>
     * 返回值说明：
     * <ul>
     *   <li>返回单位详细信息，如果找不到则返回null</li>
     * </ul>
     * <P>
     * 异常情况：
     * <ul>
     *   <li>无异常情况</li>
     * </ul>
     * 
     * 此方法支持通过单位的不同名称形式来查询单位信息，
     * 包括缩写(abbr)、单数形式(singular)和复数形式(plural)
     * 
     * @param unitName 单位名称，可以是缩写、单数或复数形式
     * @return 单位详细信息，如果找不到则返回null
     */
    UnitDescription lookup(String unitName);
    
    /**
     * 创建转换构建器
     * <P>
     * 详细描述方法的功能、算法逻辑或业务流程
     * <P>
     * 处理逻辑：
     * <ol>
     *   <li>接收源数值和源单位参数</li>
     *   <li>创建一个新的转换构建器实例</li>
     *   <li>将当前服务实例和参数传递给构建器</li>
     *   <li>返回构建器实例以支持流畅的API调用</li>
     * </ol>
     * <P>
     * 参数说明：
     * <ul>
     *   <li>value: 源数值，表示要转换的数量</li>
     *   <li>fromUnit: 源单位，表示源数值的单位</li>
     * </ul>
     * <P>
     * 返回值说明：
     * <ul>
     *   <li>返回转换构建器实例，支持流畅的API调用</li>
     * </ul>
     * <P>
     * 异常情况：
     * <ul>
     *   <li>如果参数无效，可能抛出IllegalArgumentException</li>
     * </ul>
     * 
     * 提供流畅的API用于构建单位转换操作，线程安全
     * 
     * @param value 源数值
     * @param fromUnit 源单位
     * @return 转换构建器实例
     */
    ConversionBuilder buildConversion(BigDecimal value, UnitEnum fromUnit);
}