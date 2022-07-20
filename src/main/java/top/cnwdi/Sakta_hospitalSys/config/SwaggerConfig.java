package top.cnwdi.Sakta_hospitalSys.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.HttpMethod;
import lombok.Data;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.RequestParameterBuilder;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.schema.ScalarType;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ParameterType;
import springfox.documentation.service.RequestParameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@EnableOpenApi
@EnableKnife4j
@Configuration
@ConfigurationProperties("swagger")
@Data
public class SwaggerConfig {
    /**
     * 是否启动丝袜哥
     */
    private Boolean enable;
    /**
     * 项目名称
     */
    private String applicationName;
    /**
     * 项目版本
     */
    private String applicationVersion;
    /**
     * 项目描绘信息
     */
    private String applicationDescription;

    @Bean
    public Docket docket(){
        return new Docket(DocumentationType.OAS_30)
                .pathMapping("/")
                .enable(enable)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("top.cnwdi.Sakta_hospitalSys.controller"))
                .paths(PathSelectors.any())
                .build()
                .globalRequestParameters(getGlobalRequestParameters());
    }


    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title(applicationName)
                .description(applicationDescription)
                .contact(new Contact("Sakta_wdi","cnwdi.top","1742804029@qq.com"))
                .version(applicationVersion)
                .build();
    }

    //生成全局通用参数
    private List<RequestParameter> getGlobalRequestParameters() {
        List<RequestParameter> parameters = new ArrayList<>();
        parameters.add(new RequestParameterBuilder()
                .name("token")
                .description("令牌，用户登录后标识，含用户基本信息")
                .required(false)
                .in(ParameterType.HEADER)
                .query(q -> q.model(m -> m.scalarModel(ScalarType.STRING)))
                .required(false)
                .build());
        return parameters;
    }
}
