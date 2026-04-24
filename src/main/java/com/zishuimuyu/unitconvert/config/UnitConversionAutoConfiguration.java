package com.zishuimuyu.unitconvert.config;

import com.zishuimuyu.unitconvert.service.IUnitConversionService;
import com.zishuimuyu.unitconvert.service.UnitConversionServiceImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
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
public class UnitConversionAutoConfiguration {

    /**
     * 注册单位转换服务的Bean
     * 
     * 使用@ConditionalOnMissingBean注解，只有当容器中不存在相同类型的Bean时才创建
     * 这样允许用户自定义实现来覆盖默认实现
     * 
     * @return 单位转换服务实例
     */
    @Bean
    @ConditionalOnMissingBean(IUnitConversionService.class)
    public IUnitConversionService unitConversionService() {
        return new UnitConversionServiceImpl();
    }
}