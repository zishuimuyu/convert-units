package com.zishuimuyu.unitconvert.config;

import com.zishuimuyu.unitconvert.service.IUnitConversionService;
import com.zishuimuyu.unitconvert.service.UnitConversionServiceImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 单位转换服务自动配置类
 * 
 * 该配置类用于在Spring Boot应用中自动装配单位转换服务
 * 当项目被其他Spring Boot项目依赖时，会自动配置相关Bean
 * 
 * @author 紫水木鱼
 * @version 1.0.0
 * @since 2026-04-24
 */
@Configuration
@EnableConfigurationProperties(UnitConversionProperties.class)
public class UnitConversionAutoConfiguration {

    /**
     * 注册单位转换服务的Bean（默认配置）
     * 
     * 使用@ConditionalOnMissingBean注解，只有当容器中不存在相同类型的Bean时才创建
     * 这样允许用户自定义实现来覆盖默认实现
     * 
     * @return 单位转换服务实例
     */
    @Bean
    @ConditionalOnMissingBean(IUnitConversionService.class)
    @ConditionalOnProperty(prefix = "unitconvert", name = "partial-loading", havingValue = "false", matchIfMissing = true)
    public IUnitConversionService unitConversionService(UnitConversionProperties properties) {
        UnitConversionConfig config = properties.toUnitConversionConfig();
        return new UnitConversionServiceImpl(config);
    }
    
    /**
     * 注册部分加载模式的单位转换服务Bean
     * 
     * 当配置文件中设置了unitconvert.partial-loading=true时，
     * 创建部分加载的服务实例
     * 
     * @param properties 单位转换配置属性
     * @return 单位转换服务实例
     */
    @Bean
    @ConditionalOnMissingBean(IUnitConversionService.class)
    @ConditionalOnProperty(prefix = "unitconvert", name = "partial-loading", havingValue = "true")
    public IUnitConversionService partialLoadingUnitConversionService(UnitConversionProperties properties) {
        UnitConversionConfig config = properties.toUnitConversionConfig();
        return new UnitConversionServiceImpl(config);
    }
    
    /**
     * 创建默认的单位转换配置Bean
     * 
     * 当用户没有提供自定义配置时，使用默认配置
     * 
     * @param properties 单位转换配置属性
     * @return 单位转换配置实例
     */
    @Bean
    @ConditionalOnMissingBean(UnitConversionConfig.class)
    public UnitConversionConfig unitConversionConfig(UnitConversionProperties properties) {
        return properties.toUnitConversionConfig();
    }
}