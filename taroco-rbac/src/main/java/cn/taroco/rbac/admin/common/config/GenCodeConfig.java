package cn.taroco.rbac.admin.common.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "gencode.source")
public class GenCodeConfig {
    private String basePackageName;
    private String modelPackageName;
    private String controllerPackageName;
    private String servicePackageName;
    private String daoPackageName;
    private String authorName;

}
