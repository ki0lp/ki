package com.ki0.swagger2;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * swagger2 启动类
 * 启动条件：
 * 1. 配置文件中ki0.swagger.enabled=true
 * 2. 配置文件中不存在ki0.swagger.enabled 值（默认值为true）
 */
@Configuration
@EnableSwagger2
@ConditionalOnProperty(name = "ki0.swagger.enabled", havingValue = "true", matchIfMissing = true)
@ComponentScan(
        basePackages = {
                "com.github.xiaoymin.knife4j.spring.plugin",
                "com.github.xiaoymin.knife4j.spring.web"
        }
)
@Import({BeanValidatorPluginsConfiguration.class})
public class Swagger2Configuration implements WebMvcConfigurer {
        /**
         * 这个地方要重新注入一下资源文件，不然不会注入资源的，也没有注入requestHandlerMapping,相当于xml配置的
         * <!--swagger资源配置-->
         * <mvc:resources location="classpath:/META-INF/resources/" mapping="swagger-ui.html"/>
         * <mvc:resources location="classpath:/META-INF/resources/webjars/" mapping="/webjars/**"/>
         * 不知道为什么，这也是spring boot的一个缺点（菜鸟觉得的）
         *
         * @param registry
         */
        @Override
        public void addResourceHandlers(ResourceHandlerRegistry registry) {
                registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
                registry.addResourceHandler("doc.html").addResourceLocations("classpath:/META-INF/resources/");
                registry.addResourceHandler("/webjars*")
                        .addResourceLocations("classpath:/META-INF/resources/webjars/");
        }
}
