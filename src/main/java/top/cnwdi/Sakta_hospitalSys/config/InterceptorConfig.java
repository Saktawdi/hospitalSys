package top.cnwdi.Sakta_hospitalSys.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import top.cnwdi.Sakta_hospitalSys.interceptor.CorsinInterceptor;
import top.cnwdi.Sakta_hospitalSys.interceptor.LoginInterceptor;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Bean
    LoginInterceptor loginInterceptor(){
        return new LoginInterceptor();
    }
    @Bean
    CorsinInterceptor corsinInterceptor(){
        return new CorsinInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //跨流域配置
        registry.addInterceptor(corsinInterceptor()).addPathPatterns("/**");
        //登录token拦截器配置
        registry.addInterceptor(loginInterceptor()).addPathPatterns("/api/v1/pri/*/*/**")
                .excludePathPatterns("/api/v1/pri/user/login","/api/v1/pri/user/register","/swagger**/**");


        WebMvcConfigurer.super.addInterceptors(registry);
    }
}
